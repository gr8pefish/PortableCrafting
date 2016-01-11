package com.gr8pefish.portablecrafting.reference;

import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public class Reference {

    public static final class MOD {
        public static final String VERSION = "@VERSION@";
        public static final String MODID = "portablecrafting";
        public static final String MOD_NAME = "Portable Crafting";
        public static final String DOMAIN = MODID.toLowerCase(Locale.ENGLISH) + ":";

        public static final String CLIENT_PROXY = "com.gr8pefish.portablecrafting.proxy.ClientProxy";
        public static final String COMMON_PROXY = "com.gr8pefish.portablecrafting.proxy.CommonProxy";

        public static final String GUI_FACTORY_CLASS = "com.gr8pefish.portablecrafting.client.gui.GuiFactory";
    }

    public enum GUI_ENUM {
        PORTABLE_CRAFTER
    }

    public static final class NBT {
        public static final String SAVED_INVENTORY_TAG = "Items";
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";
    }

    public static final class GUI_TEXTURE {
        public static final ResourceLocation WIDGETS = new ResourceLocation(MOD.MODID, "textures/gui/widgets.png");
        public static final ResourceLocation PORTABLE_CRAFTER = new ResourceLocation(MOD.MODID, "textures/gui/portable_crafter.png");
    }
}
