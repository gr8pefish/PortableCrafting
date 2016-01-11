package com.gr8pefish.portablecrafting.handlers;

import com.gr8pefish.portablecrafting.client.gui.inventory.PortableCrafterGui;
import com.gr8pefish.portablecrafting.inventory.InventoryPortableCrafting;
import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.PortableCraftingHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {
    /**
     * Returns a Server side Container to be displayed to the user.
     *
     * @param ID     The Gui ID Number
     * @param player The player viewing the Gui
     * @param world  The current world
     * @param x      X Position
     * @param y      Y Position
     * @param z      Z Position
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Reference.GUI_ENUM.PORTABLE_CRAFTER.ordinal()) {
            return new PortableCrafterContainer(player, new InventoryPortableCrafting(player, PortableCraftingHelper.getPortableCrafter(player)));
        }
        return null;
    }

    /**
     * Returns a Container to be displayed to the user. On the client side, this
     * needs to return a instance of GuiScreen On the server side, this needs to
     * return a instance of Container
     *
     * @param ID     The Gui ID Number
     * @param player The player viewing the Gui
     * @param world  The current world
     * @param x      X Position
     * @param y      Y Position
     * @param z      Z Position
     * @return A GuiScreen/Container to be displayed to the user, null if none.
     */
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == Reference.GUI_ENUM.PORTABLE_CRAFTER.ordinal()) {
            return new PortableCrafterGui(new PortableCrafterContainer(player, new InventoryPortableCrafting(player, PortableCraftingHelper.getPortableCrafter(player))));
        }
        return null;
    }
}
