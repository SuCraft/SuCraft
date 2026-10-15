package org.sucraft.suki.gamecontent.common.dataconverter;

import ca.spottedleaf.dataconverter.minecraft.MCVersions;

/**
 * This is a converter that doesn't exist in vanilla,
 * it only exists in Suki to replace SuCraft variants with vanilla variants.
 */
public final class SuCraftConversionVersions {

    private SuCraftConversionVersions() {
        throw new UnsupportedOperationException();
    }

    /**
     * 1.19.2 to 26.3 conversions:
     * concrete, wool, tuff and polished tuff stairs and slabs.
     */
    public static int VERSION_1 = MCVersions.V26_3;

}
