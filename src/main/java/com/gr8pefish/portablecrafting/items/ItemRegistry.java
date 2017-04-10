package com.gr8pefish.portablecrafting.items;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.items.craftingItems.ItemSubCrafting;
import com.gr8pefish.portablecrafting.util.InventoryRenderHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ItemRegistry {

    public static ItemPortableCrafter portableCrafter;
    public static ItemSubCrafting subCrafting;

    /**
     * Register Items
     */
    public static void registerItems() {
        portableCrafter = (ItemPortableCrafter)registerItem(new ItemPortableCrafter(), "portable_crafter");
        subCrafting = (ItemSubCrafting)registerItem(new ItemSubCrafting(), "crafting_core");
    }

    /**
     * Register item rendering
     */
    public static void registerRenders() {
        InventoryRenderHelper helper = PortableCrafting.proxy.getRenderHelper();

        helper.itemRender(portableCrafter, "portable_crafter");
        helper.itemRender(subCrafting, "crafting_core");
    }

    /**
     * Register recipes
     */
    public static void initRecipes() {
        GameRegistry.addShapedRecipe(new ItemStack(ItemRegistry.subCrafting, 1, 0),
                "a",
                "w",
                "c",
                'a', Items.APPLE, 'w', Blocks.CRAFTING_TABLE, 'c', Blocks.CHEST);

        GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(ItemRegistry.portableCrafter, 1),
                "ibi",
                "iri",
                "ici",
                'i', "logWood", 'b', new ItemStack(ItemRegistry.subCrafting, 1, 0),
                'c', Items.WRITABLE_BOOK, 'r', "ingotIron"));
    }

    //Helper methods for registration

    private static Item registerItem(Item item, String name) {
        item.setRegistryName(name);
        GameRegistry.register(item);
        return item;
    }

}

