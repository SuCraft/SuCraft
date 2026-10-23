package org.sucraft.sushi.gamecontent.item;

import net.minecraft.world.item.Item;
import org.bukkit.inventory.ItemType;
import org.sucraft.sushi.gamecontent.block.SuCraftBlockType;import org.sucraft.sushi.gamecontent.block.SuCraftBlocks;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

public final class SuCraftItems {

    private SuCraftItems() {
        throw new UnsupportedOperationException();
    }

    private static SuCraftItemType itemType(String path) {
        return new SuCraftItemType(path);
    }

    private static ItemType[] values;
    private static Item[] valuesNMS;
    private static Item[] valuesForDataGeneration;
    private static SuCraftItemType[] types;

    public static ItemType[] values() {
        if (values == null) {
            values = Arrays.stream(types())
                .map(SuCraftItemType::get).toArray(ItemType[]::new);
        }
        return values;
    }

    public static Item[] valuesNMS() {
        if (valuesNMS == null) {
            valuesNMS = Arrays.stream(types())
                .map(SuCraftItemType::getNMS).toArray(Item[]::new);
        }
        return valuesNMS;
    }

    public static Item[] valuesForDataGeneration() {
        if (valuesForDataGeneration == null) {
            valuesForDataGeneration = Arrays.stream(types())
                .map(SuCraftItemType::getForDataGeneration).toArray(Item[]::new);
        }
        return valuesForDataGeneration;
    }

    public static SuCraftItemType[] types() {
        if (types == null) {
            Stream<SuCraftItemType> declared = Arrays.stream(SuCraftItems.class.getDeclaredFields())
                .filter(field ->
                    Modifier.isPublic(field.getModifiers()) &&
                        Modifier.isStatic(field.getModifiers()) &&
                        Modifier.isFinal(field.getModifiers()) &&
                        field.getType() == SuCraftItemType.class)
                .map(field -> {
                    field.trySetAccessible();
                    try {
                        return (SuCraftItemType) field.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
            Stream<SuCraftItemType> forBlocks = Arrays.stream(SuCraftBlocks.types())
                .map(SuCraftBlockType::itemType).filter(Objects::nonNull);
            types = Stream.concat(declared, forBlocks).toArray(SuCraftItemType[]::new);
        }
        return types;
    }

}
