package org.sucraft.sushi.gamecontent.common;

import it.unimi.dsi.fastutil.booleans.Boolean2ObjectFunction;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.jspecify.annotations.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

/**
 * A family of resources, each defined by a particular base value.
 */
public class Family<K, V> {

    private final Map<K ,V> map;

    private Family(Map<K ,V> map, List<K> keys, Function<K, V> constructor) {
        this.map = map;
        for (var key : keys) {
            map.put(key, constructor.apply(key));
        }
    }

    public V get(K key) {
        return Objects.requireNonNull(this.map.get(key));
    }

    public Collection<V> all() {
        return map.values();
    }

    public @Nullable V getNullable(K key) {
        return this.map.get(key);
    }

    public static <K extends Enum<K>, V> Family<K, V> forEnum(Class<K> type, Function<K, V> constructor) {
        return new Family<>(new EnumMap<>(type), Arrays.asList(type.getEnumConstants()), constructor);
    }

    public static <V> DyeColor<V> forDyeColor(Function<net.minecraft.world.item.DyeColor, V> constructor) {
        return new DyeColor<>(constructor);
    }

    public static <V> Family<Boolean, V> forBoolean(Boolean2ObjectFunction<V> constructor) {
        return new Family<>(new Object2ObjectArrayMap<>(2), List.of(false, true), constructor);
    }

    public static class DyeColor<V> extends Family<net.minecraft.world.item.DyeColor, V> {

        private DyeColor(Function<net.minecraft.world.item.DyeColor, V> constructor) {
            super(new EnumMap<>(net.minecraft.world.item.DyeColor.class), Arrays.asList(net.minecraft.world.item.DyeColor.values()), constructor);
        }

        public V black() {
            return this.get(net.minecraft.world.item.DyeColor.BLACK);
        }

        public V blue() {
            return this.get(net.minecraft.world.item.DyeColor.BLUE);
        }

        public V brown() {
            return this.get(net.minecraft.world.item.DyeColor.BROWN);
        }

        public V cyan() {
            return this.get(net.minecraft.world.item.DyeColor.CYAN);
        }

        public V gray() {
            return this.get(net.minecraft.world.item.DyeColor.GRAY);
        }

        public V green() {
            return this.get(net.minecraft.world.item.DyeColor.GREEN);
        }

        public V lightBlue() {
            return this.get(net.minecraft.world.item.DyeColor.LIGHT_BLUE);
        }

        public V lightGray() {
            return this.get(net.minecraft.world.item.DyeColor.LIGHT_GRAY);
        }

        public V lime() {
            return this.get(net.minecraft.world.item.DyeColor.LIME);
        }

        public V magenta() {
            return this.get(net.minecraft.world.item.DyeColor.MAGENTA);
        }

        public V orange() {
            return this.get(net.minecraft.world.item.DyeColor.ORANGE);
        }

        public V pink() {
            return this.get(net.minecraft.world.item.DyeColor.PINK);
        }

        public V purple() {
            return this.get(net.minecraft.world.item.DyeColor.PURPLE);
        }

        public V red() {
            return this.get(net.minecraft.world.item.DyeColor.RED);
        }

        public V white() {
            return this.get(net.minecraft.world.item.DyeColor.WHITE);
        }

        public V yellow() {
            return this.get(net.minecraft.world.item.DyeColor.YELLOW);
        }

    }

}
