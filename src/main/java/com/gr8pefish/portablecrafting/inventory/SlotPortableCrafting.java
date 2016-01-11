package com.gr8pefish.portablecrafting.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotPortableCrafting extends Slot {

    private InventoryPortableCrafting craftingMatrix;
    private EntityPlayer player;

    public SlotPortableCrafting(EntityPlayer player, InventoryPortableCrafting craftingMatrix, int slotIndex, int xPos, int yPos) {
        super(craftingMatrix, slotIndex, xPos, yPos);
        this.craftingMatrix = craftingMatrix;
        this.player = player;
    }

    /**
     * If stack1 has more items than stack2, onCrafting(item,countIncrease) is called
     * @param stack1 - initial stack
     * @param stack2 - second stack
     */
    @Override
    public void onSlotChange(ItemStack stack1, ItemStack stack2) {
        super.onSlotChange(stack1, stack2);
        craftingMatrix.onGuiSaved(player);
    }
}
