package org.sucraft.sushi.gamecontent.common;

import it.unimi.dsi.fastutil.booleans.Boolean2ObjectFunction;
import it.unimi.dsi.fastutil.booleans.BooleanList;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.Identifier;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
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
public interface Family<K, V> extends Iterable<V> {

    V get(K key);

    Collection<V> all();

    Stream<V> stream();

    @Nullable V getNullable(K key);

    class Base<K, V> implements Family<K, V> {

        protected final Map<K, V> map;

        public Base(Map<K, V> map, List<K> keys, Function<K, V> constructor) {
            this.map = map;
            for (var key : keys) {
                map.put(key, constructor.apply(key));
            }
        }

        @Override
        public V get(K key) {
            return Objects.requireNonNull(this.map.get(key));
        }

        @Override
        public Collection<V> all() {
            return map.values();
        }

        @Override
        public @NonNull Iterator<V> iterator() {
            return this.all().iterator();
        }

        @Override
        public Stream<V> stream() {
            return this.all().stream();
        }

        @Override
        public @Nullable V getNullable(K key) {
            return this.map.get(key);
        }

    }

    class Hashed<K, V> extends Base<K, V> {

        public Hashed(List<K> keys, Function<K, V> constructor) {
            super(new HashMap<>(keys.size()), keys, constructor);
        }

    }

    class Boolean<V> extends Base<java.lang.Boolean, V> {

        public Boolean(Boolean2ObjectFunction<V> constructor) {
            super(new Object2ObjectArrayMap<>(2), BooleanList.of(false, true), constructor);
        }

    }

    class Enum<K extends java.lang.Enum<K>, V> extends Base<K, V> {

        public Enum(Class<K> type, Function<K, V> constructor) {
            super(new EnumMap<>(type), Arrays.asList(type.getEnumConstants()), constructor);
        }

    }

    class DyeColor<V> extends Enum<net.minecraft.world.item.DyeColor, V> {

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

    interface WoodType<V> extends Family<net.minecraft.world.level.block.state.properties.WoodType, V> {

        interface WithAcacia<V> extends WoodType<V> {

            default V acacia() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.ACACIA);
            }

        }

        interface WithBamboo<V> extends WoodType<V> {

            default V bamboo() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.BAMBOO);
            }

        }

        interface WithBirch<V> extends WoodType<V> {

            default V birch() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.BIRCH);
            }

        }

        interface WithCherry<V> extends WoodType<V> {

            default V cherry() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.CHERRY);
            }

        }

        interface WithCrimson<V> extends WoodType<V> {

            default V crimson() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.CRIMSON);
            }

        }

        interface WithDarkOak<V> extends WoodType<V> {

            default V darkOak() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.DARK_OAK);
            }

        }

        interface WithJungle<V> extends WoodType<V> {

            default V jungle() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.JUNGLE);
            }

        }

        interface WithMangrove<V> extends WoodType<V> {

            default V mangrove() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.MANGROVE);
            }

        }

        interface WithOak<V> extends WoodType<V> {

            default V oak() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.OAK);
            }

        }

        interface WithSpruce<V> extends WoodType<V> {

            default V spruce() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.SPRUCE);
            }

        }

        interface WithWarped<V> extends WoodType<V> {

            default V warped() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.WARPED);
            }

        }

        interface WithPaleOak<V> extends WoodType<V> {

            default V paleOak() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.PALE_OAK);
            }

        }

        interface WithPoplar<V> extends WoodType<V> {

            default V poplar() {
                return this.get(net.minecraft.world.level.block.state.properties.WoodType.POPLAR);
            }

        }

        interface WithVanillaHasLogOrStem<V> extends WithAcacia<V>,
            WithBirch<V>,
            WithCherry<V>,
            WithCrimson<V>,
            WithDarkOak<V>,
            WithJungle<V>,
            WithMangrove<V>,
            WithOak<V>,
            WithSpruce<V>,
            WithWarped<V>,
            WithPaleOak<V>,
            WithPoplar<V> {
        }

        interface WithVanilla<V> extends WithVanillaHasLogOrStem<V>, WithBamboo<V> {
        }

        class Base<V> extends Hashed<net.minecraft.world.level.block.state.properties.WoodType, V> implements WithVanilla<V> {

            public Base(List<net.minecraft.world.level.block.state.properties.WoodType> keys, Function<net.minecraft.world.level.block.state.properties.WoodType, V> constructor) {
                super(keys, constructor);
            }

        }

        static <V> WoodType.WithVanillaHasLogOrStem<V> withVanillaHasLogOrStem(Function<net.minecraft.world.level.block.state.properties.WoodType, V> constructor) {
            return new Base<>(net.minecraft.world.level.block.state.properties.WoodType.values().filter(type -> {
                var log = BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(type.name() + "_log"));
                if (log == null || log == Blocks.AIR) {
                    log = BuiltInRegistries.BLOCK.getValue(Identifier.withDefaultNamespace(type.name() + "_stem"));
                }
                return log instanceof RotatedPillarBlock && log.properties().instrument == NoteBlockInstrument.BASS;
            }).toList(), constructor);
        }

        static <V> WoodType.WithVanilla<V> withVanilla(Function<net.minecraft.world.level.block.state.properties.WoodType, V> constructor) {
            return new Base<>(net.minecraft.world.level.block.state.properties.WoodType.values().toList(), constructor);
        }

    }

}
