package org.sucraft.sushi.gamecontent.common;

import it.unimi.dsi.fastutil.booleans.Boolean2ObjectFunction;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * A family of resources, each defined by a particular base value.
 */
public class Family<K, V> implements Iterable<V> {

    protected final Map<K, V> map;

    public Family(Map<K, V> map, List<K> keys, Function<K, V> constructor) {
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

    @Override
    public @NonNull Iterator<V> iterator() {
        return this.all().iterator();
    }

    public Stream<V> stream() {
        return this.all().stream();
    }

    public @Nullable V getNullable(K key) {
        return this.map.get(key);
    }

    public static class Hashed<K, V> extends Family<K, V> {

        public Hashed(List<K> keys, Function<K, V> constructor) {
            super(new HashMap<>(keys.size()), keys, constructor);
        }

    }

    public static class Boolean<V> extends Family<java.lang.Boolean, V> {

        public Boolean(Boolean2ObjectFunction<V> constructor) {
            super(new Object2ObjectArrayMap<>(2), BooleanList.of(false, true), constructor);
        }

    }

    public static class Enum<K extends java.lang.Enum<K>, V> extends Family<K, V> {

        public Enum(Class<K> type, Function<K, V> constructor) {
            super(new EnumMap<>(type), Arrays.asList(type.getEnumConstants()), constructor);
        }

    }

    public static class DyeColor<V> extends Enum<net.minecraft.world.item.DyeColor, V> {

        public DyeColor(Function<net.minecraft.world.item.DyeColor, V> constructor) {
            super(net.minecraft.world.item.DyeColor.class, constructor);
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

    public static class WoodType<V> extends Hashed<net.minecraft.world.level.block.state.properties.WoodType, V> {

        public WoodType(List<net.minecraft.world.level.block.state.properties.WoodType> keys, Function<net.minecraft.world.level.block.state.properties.WoodType, V> constructor) {
            super(keys, constructor);
        }

        public static <V> WoodType<V> forVanillaWoodTypes(Function<net.minecraft.world.level.block.state.properties.WoodType, V> constructor) {
            return new WoodType<>(net.minecraft.world.level.block.state.properties.WoodType.values().toList(), constructor);
        }

        public V acacia() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.ACACIA);
        }

        public V bamboo() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.BAMBOO);
        }

        public V birch() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.BIRCH);
        }

        public V cherry() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.CHERRY);
        }

        public V crimson() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.CRIMSON);
        }

        public V darkOak() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.DARK_OAK);
        }

        public V jungle() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.JUNGLE);
        }

        public V mangrove() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.MANGROVE);
        }

        public V oak() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.OAK);
        }

        public V spruce() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.SPRUCE);
        }

        public V warped() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.WARPED);
        }

        public V paleOak() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.PALE_OAK);
        }

        public V poplar() {
            return this.get(net.minecraft.world.level.block.state.properties.WoodType.POPLAR);
        }

    }

}
