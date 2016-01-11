package com.gr8pefish.portablecrafting.items;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.items.craftingItems.ItemSubCrafting;
import com.gr8pefish.portablecrafting.plugins.jei.JEIPlugin;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.InventoryRenderHelper;
import mezz.jei.api.INbtIgnoreList;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ItemRegistry {

    public static ItemPortableCrafter portableCrafter;
    public static ItemSubCrafting subCrafting;

    public static void registerItems() {
        portableCrafter = (ItemPortableCrafter)registerItem(new ItemPortableCrafter());
        subCrafting = (ItemSubCrafting)registerItem(new ItemSubCrafting());
    }

    public static void registerRenders() {
        InventoryRenderHelper helper = PortableCrafting.proxy.getRenderHelper();

        helper.itemRender(portableCrafter, "portable_crafter");
        helper.itemRender(subCrafting, 0);
        helper.itemRender(subCrafting, 1);
    }

    public static void initRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.subCrafting, 1, 0),
                "a",
                "w",
                "c",
                'a', Items.apple, 'w', Blocks.crafting_table, 'c', Blocks.chest);

        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.subCrafting, 1, 1),
                "lll",
                "lbl",
                "lll",
                'l', new ItemStack(Items.dye, 1, 4), 'b', Items.book);

        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.portableCrafter, 1),
                "ibi",
                "gsg",
                "ici",
                'i', Items.iron_ingot, 'g', Items.gold_ingot, 'b', new ItemStack(ItemRegistry.subCrafting, 1, 0), 'c', new ItemStack(ItemRegistry.subCrafting, 1, 1), 's', Blocks.glass_pane);

        //ignore nbt data of portable crafter so it shows in JEI
        INbtIgnoreList ignoreList = JEIPlugin.jeiHelpers.getNbtIgnoreList();
        ignoreList.ignoreNbtTagNames(Reference.NBT.SAVED_INVENTORY_TAG);
        ignoreList.ignoreNbtTagNames(Reference.NBT.LEAST_SIG_UUID);
        ignoreList.ignoreNbtTagNames(Reference.NBT.MOST_SIG_UUID);
    }

    private static Item registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
        return item;
    }

    private static Item registerItem(Item item) {
        return registerItem(item, item.getClass().getSimpleName());
    }

}

