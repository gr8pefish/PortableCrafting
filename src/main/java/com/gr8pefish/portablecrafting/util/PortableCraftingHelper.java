package com.gr8pefish.portablecrafting.util;

import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class PortableCraftingHelper {

    //Various helper methods specific to this mod

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

    //Stack Helper Methods

    public static boolean stacksMatch(ItemStack stack1, ItemStack stack2) {
        if (stack1 == null && stack2 == null)
            return true;

        // Modified line from Container.java:623
        if (neitherNull(stack1, stack2) && itemsMatch(stack1, stack2) && (!stack1.getHasSubtypes() && !stack2.getHasSubtypes() || sameDamage(stack1, stack2)) && ItemStack.areItemStackTagsEqual(stack1, stack2))
            return true;
        return false;
    }

    private static boolean sameDamage(ItemStack stack1, ItemStack stack2) {
        return stack1.getItemDamage() == stack2.getItemDamage();
    }

    private static boolean neitherNull(ItemStack stack1, ItemStack stack2) {
        return stack1 != null && stack2 != null;
    }

    private static boolean itemsMatch(ItemStack stack1, ItemStack stack2) {
        return stack1.getItem() == stack2.getItem();
    }
}
