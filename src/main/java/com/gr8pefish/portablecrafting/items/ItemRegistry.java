package com.gr8pefish.portablecrafting.items;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.items.craftingItems.ItemBlueprints;
import com.gr8pefish.portablecrafting.items.craftingItems.ItemCraftingCore;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.InventoryRenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

    public static ItemPortableCrafter portableCrafter;
    public static ItemBlueprints blueprints;
    public static ItemCraftingCore craftingCore;

    public static void registerItems() {
        portableCrafter = new ItemPortableCrafter();
        GameRegistry.registerItem(portableCrafter, "portable_crafter");

        blueprints = new ItemBlueprints();
        GameRegistry.registerItem(blueprints, "blueprints");

        craftingCore = new ItemCraftingCore();
        GameRegistry.registerItem(craftingCore, "crafting_core");
    }


    public static void registerRenders() {
        InventoryRenderHelper helper = PortableCrafting.proxy.getRenderHelper();

        helper.itemRender(portableCrafter, "portable_crafter");
        helper.itemRender(blueprints, "blueprints");
        helper.itemRender(craftingCore, "crafting_core");
    }

    public static void initRecipes() {

        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.craftingCore),
                "a",
                "w",
                "c",
                'a', Items.apple, 'w', Blocks.crafting_table, 'c', Blocks.chest);

        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.blueprints),
                "lll",
                "lbl",
                "lll",
                'l', new ItemStack(Items.dye, 1, 4), 'b', Items.book);

        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.portableCrafter, 1),
                "ibi",
                "gsg",
                "ici",
                'i', Items.iron_ingot, 'g', Items.gold_ingot, 'b', ItemRegistry.blueprints, 'c', new ItemStack(ItemRegistry.craftingCore, 1, 0), 's', Blocks.glass_pane);

    }

//    public static void initRenderers(){
//        InventoryRenderHelper renderHelper = PortableCrafting.proxy.getRenderHelper();
//    }

}

