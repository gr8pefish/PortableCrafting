package com.gr8pefish.portablecrafting.plugins.jei;

import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
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

    }

    @Override
    public void onRecipeRegistryAvailable(IRecipeRegistry recipeRegistry) {}
}