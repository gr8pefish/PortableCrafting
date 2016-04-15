package com.gr8pefish.portablecrafting.plugins.jei;

import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
import com.gr8pefish.portablecrafting.items.ItemRegistry;
import com.gr8pefish.portablecrafting.reference.Reference;
import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        //add Shift-clicking from [+] into grid
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(PortableCrafterContainer.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);

        //ignore nbt data of portable crafter so it shows in JEI
        INbtIgnoreList ignoreList = jeiHelpers.getNbtIgnoreList();
        ignoreList.ignoreNbtTagNames(ItemRegistry.portableCrafter, Reference.NBT.SAVED_INVENTORY_TAG, Reference.NBT.LEAST_SIG_UUID, Reference.NBT.MOST_SIG_UUID);
    }
}