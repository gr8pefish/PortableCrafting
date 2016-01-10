package com.gr8pefish.portablecrafting.util;

import net.minecraft.item.ItemStack;

public class StackHelper {
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
