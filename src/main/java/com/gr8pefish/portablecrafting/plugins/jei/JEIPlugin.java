package com.gr8pefish.portablecrafting.plugins.jei;

import com.gr8pefish.portablecrafting.client.gui.inventory.PortableCrafterGui;
import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.recipe.VanillaRecipeCategoryUid;

@mezz.jei.api.JEIPlugin
public class JEIPlugin extends BlankModPlugin {

    @Override
    public void register(IModRegistry registry) {
        IJeiHelpers jeiHelpers = registry.getJeiHelpers();

        //add Shift-clicking from [+] into grid
        registry.getRecipeTransferRegistry().addRecipeTransferHandler(PortableCrafterContainer.class, VanillaRecipeCategoryUid.CRAFTING, 1, 9, 10, 36);

        //add in clickable (see recipes) arrow
        registry.addRecipeClickArea(PortableCrafterGui.class, 102, 34, 21, 16, VanillaRecipeCategoryUid.CRAFTING);

    }
}