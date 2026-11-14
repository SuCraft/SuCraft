#!/usr/bin/env python3
"""
Frost Leaves - procedural 16x16 Minecraft-style leaf texture generator.

Techniques used:
  * Toroidal (wrap-around) drawing: every stamp is written with modulo
    coordinates, so the texture is seamless by construction.
  * Mitchell's best-candidate placement on a torus: crystals are spread
    evenly (blue-noise-like) instead of clumping, with wrapped distances.
  * Hand-designed pixel stamps (hexagon crystals, hollow hex rings,
    six-armed snowflakes, tiny diamonds) layered big -> small, like the
    leaf shapes in vanilla leaf textures.
  * Seed search: many candidate textures are generated and scored on
    transparency ratio, absence of big holes, and absence of stray pixels.
  * Palette is restricted to near-white shades with faint cyan/gray tints.

Usage:
    python frost_leaves.py                  # writes the textures
    python frost_leaves.py --seed 7 --tries 3000
    python frost_leaves.py --show           # also opens the tiled preview
"""

import argparse
import os
import random
from PIL import Image

SIZE = 16

# ---------------------------------------------------------------------------
# Palette: all near-white. 'd' (darkest) is still a very light cyan-gray.
# ---------------------------------------------------------------------------
PALETTE = {
    "d": (203, 222, 232, 255),  # faint icy shadow
    "m": (221, 235, 242, 255),  # light cyan-gray
    "l": (238, 247, 251, 255),  # almost white
    "w": (255, 255, 255, 255),  # pure white highlight
}
SHADE_ORDER = "dmlw"  # dark -> bright, used for brightness jitter

# ---------------------------------------------------------------------------
# Pixel stamps ('.' = leave untouched)
# ---------------------------------------------------------------------------
HEX_SOLID = [
    ".mlm.",
    "mllwm",
    "lwwwm",
    "mlwld",
    ".mdd.".ljust(5, "."),
]

HEX_RING = [  # hollow crystal -> creates see-through holes
    ".mlm.",
    "m...d",
    "l.w.m",
    "m...d",
    ".mdd.",
]

SNOWFLAKE = [  # six-armed look (cross + diagonals)
    "m.l.m",
    ".lll.",
    "lwwwl",
    ".ldl.",
    "m.d.m",
]

HEX_SMALL = [
    ".l.",
    "mwl",
    ".d.",
]

SPARKLE = [
    "w",
]

# (stamp, how many to place) -- placed in this order, big first
STAMP_PLAN = [
    # (HEX_SOLID, 3),
    # (HEX_RING, 2),
    (SNOWFLAKE, 0),
    (HEX_SMALL, 25)
]


# ---------------------------------------------------------------------------
# Geometry helpers (toroidal)
# ---------------------------------------------------------------------------
def torus_dist2(a, b, size=SIZE):
    dx = abs(a[0] - b[0])
    dy = abs(a[1] - b[1])
    dx = min(dx, size - dx)
    dy = min(dy, size - dy)
    return dx * dx + dy * dy


def best_candidate(rng, existing, k=32):
    """Mitchell's best-candidate: sample k points, keep the one farthest
    (wrapped distance) from all existing points."""
    best, best_d = None, -1
    for _ in range(k):
        p = (rng.randrange(SIZE), rng.randrange(SIZE))
        d = min((torus_dist2(p, e) for e in existing), default=10 ** 9)
        if d > best_d:
            best, best_d = p, d
    return best


def stamp(grid, pattern, cx, cy, shade_shift=0):
    """Draw a pattern centered at (cx, cy) with wrap-around."""
    h = len(pattern)
    w = len(pattern[0])
    for dy, row in enumerate(pattern):
        for dx, ch in enumerate(row):
            if ch == ".":
                continue
            if shade_shift:
                i = SHADE_ORDER.index(ch) + shade_shift
                ch = SHADE_ORDER[max(0, min(len(SHADE_ORDER) - 1, i))]
            x = (cx + dx - w // 2) % SIZE
            y = (cy + dy - h // 2) % SIZE
            grid[y][x] = ch


# ---------------------------------------------------------------------------
# Generation
# ---------------------------------------------------------------------------
def generate_grid(seed):
    rng = random.Random(seed)
    grid = [[None] * SIZE for _ in range(SIZE)]
    centers = []

    for pattern, count in STAMP_PLAN:
        for _ in range(count):
            cx, cy = best_candidate(rng, centers)
            centers.append((cx, cy))
            # occasional brightness jitter so crystals differ a bit
            shift = rng.choice([0, 0, 0, 1, -1])
            stamp(grid, pattern, cx, cy, shift)

    # sprinkle a few white sparkles on already-opaque pixels
    opaque = [(x, y) for y in range(SIZE) for x in range(SIZE) if grid[y][x]]
    rng.shuffle(opaque)
    for x, y in opaque[:4]:
        grid[y][x] = "w"
    return grid


def score_grid(grid):
    """Lower is better."""
    opaque = [[c is not None for c in row] for row in grid]
    total = SIZE * SIZE
    frac = sum(sum(r) for r in opaque) / total

    # leaves in Minecraft are ~75-85% opaque
    score = abs(frac - 0.80) * 10

    # penalty: any fully transparent 3x3 window (wrapped) = ugly big hole
    for y in range(SIZE):
        for x in range(SIZE):
            if not any(
                opaque[(y + j) % SIZE][(x + i) % SIZE]
                for j in range(3)
                for i in range(3)
            ):
                score += 1.0

    # penalty: isolated opaque pixels (no 4-neighbour) look like noise
    for y in range(SIZE):
        for x in range(SIZE):
            if opaque[y][x] and not any(
                opaque[(y + dy) % SIZE][(x + dx) % SIZE]
                for dx, dy in ((1, 0), (-1, 0), (0, 1), (0, -1))
            ):
                score += 0.3
    return score


def grid_to_image(grid):
    img = Image.new("RGBA", (SIZE, SIZE), (0, 0, 0, 0))
    px = img.load()
    for y in range(SIZE):
        for x in range(SIZE):
            c = grid[y][x]
            if c:
                px[x, y] = PALETTE[c]
    return img


def generate_texture(seed=0, tries=2000):
    """Search `tries` seeds starting at `seed`, return the best 16x16 image."""
    best_grid, best_score, best_seed = None, float("inf"), seed
    for s in range(seed, seed + tries):
        g = generate_grid(s)
        sc = score_grid(g)
        if sc < best_score:
            best_grid, best_score, best_seed = g, sc, s
    print(f"Best seed: {best_seed} (score {best_score:.2f})")
    return grid_to_image(best_grid)


# ---------------------------------------------------------------------------
# Inspection helpers
# ---------------------------------------------------------------------------
def tile_image(img, nx=4, ny=4):
    """Return the texture tiled nx by ny (4x4 -> 64x64 for a 16x16 input)."""
    out = Image.new("RGBA", (img.width * nx, img.height * ny), (0, 0, 0, 0))
    for j in range(ny):
        for i in range(nx):
            out.paste(img, (i * img.width, j * img.height))
    return out


def preview(img, scale=10, bg=(70, 105, 70)):
    """White-on-transparent is invisible in most viewers, so composite onto
    a background color (default: dim green, like a forest backdrop) and
    upscale with nearest-neighbour so pixels stay crisp."""
    backdrop = Image.new("RGBA", img.size, bg + (255,))
    backdrop.alpha_composite(img)
    return backdrop.resize(
        (img.width * scale, img.height * scale), Image.NEAREST
    )


def validate(img):
    """Sanity check: only transparent or near-white pixels."""
    for r, g, b, a in img.getdata():
        if a == 0:
            continue
        assert min(r, g, b) >= 195, "pixel too dark"
        assert abs(r - b) <= 60 and abs(g - b) <= 40, "pixel not whitish"
    print("Palette validation passed.")


# ---------------------------------------------------------------------------
def main():
    ap = argparse.ArgumentParser()
    ap.add_argument("--seed", type=int, default=random.randint(- (2 << 31), (2 << 31) - 1))
    ap.add_argument("--tries", type=int, default=20000)
    ap.add_argument("--show", action="store_true")
    args = ap.parse_args()

    tex = generate_texture(args.seed, args.tries)
    validate(tex)

    if not os.path.exists("run"):
        os.mkdir("run")
    tex.save("run/frost_leaves.png")  # 16x16, use in game
    tiled = tile_image(tex, 4, 4)
    tiled.save("run/frost_leaves_tiled_4x4.png")  # 64x64 raw
    preview(tex, 20).save("run/frost_leaves_preview.png")  # 320x320 single
    big = preview(tiled, 10)
    big.save("run/frost_leaves_tiled_preview.png")  # 640x640 tiled view
    print("Saved: frost_leaves.png, frost_leaves_tiled_4x4.png, "
          "frost_leaves_preview.png, frost_leaves_tiled_preview.png")

    if args.show:
        big.show()


if __name__ == "__main__":
    main()
