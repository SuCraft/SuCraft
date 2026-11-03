package org.sucraft.sushi.datageneration.common;

import org.sucraft.sushi.gamecontent.item.SuCraftItems;

public final class CommonItemAdder {

    private CommonItemAdder() {
        throw new UnsupportedOperationException();
    }

    public static void initialize() {
        // Make sure all items are registered
        SuCraftItems.valuesNMS();
    }

    public static void add(CollectedFactories factories) {
        for (var type : SuCraftItems.types()) {
            if (type.definedByCode) {
                type.applyDataGeneration(factories);
            }
        }
    }

}
