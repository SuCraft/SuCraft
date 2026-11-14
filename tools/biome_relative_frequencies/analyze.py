#!/usr/bin/env python3
import argparse
import json
from pathlib import Path

import numpy as np

DEFAULT_DIR = "../../minecraft-server-software/suki-server/src/minecraft/resources/data/minecraft/worldgen/biome"


def smooth(x):
    return 3 * x * x - 2 * x * x * x


def load_points(directory):
    names, points = [], []
    for path in sorted(Path(directory).glob("*.json")):
        with open(path) as f:
            data = json.load(f)
        names.append(path.stem)
        points.append((smooth(data["downfall"]), smooth(data["temperature"])))
    return names, np.array(points)


def voronoi_areas(points, resolution):
    """Estimate the area of each Voronoi cell inside the unit square by sampling
    a resolution x resolution grid of cell centers. Points that are exactly tied
    (e.g. biomes with identical values) share the area equally."""
    areas = np.zeros(len(points))
    coords = (np.arange(resolution) + 0.5) / resolution
    for y in coords:  # one row at a time to keep memory small
        row = np.stack([coords, np.full(resolution, y)], axis=1)           # (R, 2)
        dist = np.linalg.norm(row[:, None, :] - points[None, :, :], axis=2)  # (R, N)
        nearest = dist == dist.min(axis=1, keepdims=True)                   # (R, N)
        areas += (nearest / nearest.sum(axis=1, keepdims=True)).sum(axis=0)
    return areas / resolution**2


def main():
    parser = argparse.ArgumentParser(description="Voronoi cell areas of biomes in downfall/temperature space.")
    parser.add_argument("directory", nargs="?", default=DEFAULT_DIR)
    parser.add_argument("--resolution", type=int, default=2000, help="grid samples per axis (default 2000)")
    args = parser.parse_args()

    names, points = load_points(args.directory)
    areas = voronoi_areas(points, args.resolution)

    for name, area in sorted(zip(names, areas), key=lambda t: -t[1]):
        print(f"{name:40s} {area:.6f}")


if __name__ == "__main__":
    main()
