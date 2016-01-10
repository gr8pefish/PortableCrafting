package com.gr8pefish.portablecrafting.util;

import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PortableCraftingHelper {

    public static boolean playerHasPortableCrafter(EntityPlayer player) {
        for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
            if (player.inventory.getStackInSlot(i).getItem() instanceof ItemPortableCrafter) {
                return true;
            }
        }
        return false;
    }

    public static ItemStack getPortableCrafter(EntityPlayer player) {
        ItemStack crafter = null;

        if (player.getHeldItem() != null && player.getHeldItem().getItem() instanceof ItemPortableCrafter) {
            crafter = player.getHeldItem();
        } else {
            for (int i = 0; i < player.inventory.getSizeInventory(); i++) {
                ItemStack stack = player.inventory.getStackInSlot(i);

                if (stack != null && stack.getItem() != null && stack.getItem() instanceof ItemPortableCrafter) {
                    crafter = player.inventory.getStackInSlot(i);
                }
            }
        }


        if (!player.worldObj.isRemote && crafter != null) {
            NBTHelper.setUUID(crafter);
        }

        return crafter;
    }
}
