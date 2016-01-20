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
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry {

    public static ItemPortableCrafter portableCrafter;
    public static ItemSubCrafting subCrafting;

    /**
     * Register Items
     */
    public static void registerItems() {
        portableCrafter = (ItemPortableCrafter)registerItem(new ItemPortableCrafter(), "ItemPortableCrafter");
        subCrafting = (ItemSubCrafting)registerItem(new ItemSubCrafting(), "ItemSubCrafting");
    }

    /**
     * Register item rendering
     */
    public static void registerRenders() {
        InventoryRenderHelper helper = PortableCrafting.proxy.getRenderHelper();

        helper.itemRender(portableCrafter, "ItemPortableCrafter");
        helper.itemRender(subCrafting, 0);
        helper.itemRender(subCrafting, 1);
    }

    /**
     * Register recipes
     */
    public static void initRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.subCrafting, 1, 0),
                "a",
                "w",
                "c",
                'a', Items.apple, 'w', Blocks.crafting_table, 'c', Blocks.chest);

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.subCrafting, 1, 1),
                "lll",
                "lbl",
                "lll",
                'l', "gemLapis", 'b', Items.book));

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.portableCrafter, 1),
                "ibi",
                "iri",
                "ici",
                'i', "logWood", 'b', new ItemStack(ItemRegistry.subCrafting, 1, 0),
                'c', new ItemStack(ItemRegistry.subCrafting, 1, 1), 'r', "ingotIron"));
    }

    //Helper methods for registration

    private static Item registerItem(Item item, String name) {
        GameRegistry.registerItem(item, name);
        return item;
    }

}

