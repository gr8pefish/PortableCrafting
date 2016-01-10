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
     * if par2 has more items than par1, onCrafting(item,countIncrease) is called
     *
     * @param p_75220_1_
     * @param p_75220_2_
     */
    @Override
    public void onSlotChange(ItemStack p_75220_1_, ItemStack p_75220_2_) {
        super.onSlotChange(p_75220_1_, p_75220_2_);
        craftingMatrix.onGuiSaved(player);
    }
}
