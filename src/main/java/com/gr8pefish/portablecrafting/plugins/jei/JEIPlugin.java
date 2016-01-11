package com.gr8pefish.portablecrafting.plugins.jei;

import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
import com.gr8pefish.portablecrafting.items.ItemRegistry;
import com.gr8pefish.portablecrafting.reference.Reference;
import mezz.jei.api.*;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@mezz.jei.api.JEIPlugin
public class JEIPlugin implements IModPlugin {

    public static IJeiHelpers jeiHelpers;

    @Override
    public void onJeiHelpersAvailable(IJeiHelpers jeiHelpers) {
        JEIPlugin.jeiHelpers = jeiHelpers;
    }

    @Override
    public void onItemRegistryAvailable(IItemRegistry itemRegistry) {}

    @Override
    public void register(IModRegistry registry) {
        IGuiHelper guiHelper = jeiHelpers.getGuiHelper();

        //add shiftclicking
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(PortableCrafterContainer.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);

        //ignore nbt data of portable crafter so it shows in JEI
        INbtIgnoreList ignoreList = jeiHelpers.getNbtIgnoreList();
        ignoreList.ignoreNbtTagNames(ItemRegistry.portableCrafter, Reference.NBT.SAVED_INVENTORY_TAG, Reference.NBT.LEAST_SIG_UUID, Reference.NBT.MOST_SIG_UUID);
    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}
}