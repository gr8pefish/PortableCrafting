package com.gr8pefish.portablecrafting.inventory;

import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.util.NBTHelper;
import com.gr8pefish.portablecrafting.util.PortableCraftingHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;

import java.util.ArrayList;

//@InventoryContainer
//@Optional.Interface(iface = "codechicken.nei.invtweaks.INEIGuiHandler", modid = "NotEnoughItems")
public class PortableCrafterContainer extends Container {

    private final EntityPlayer player;
    private final World world;

    public InventoryPortableCrafting craftingMatrix;
    public InventoryCraftResult craftingResult;
    public InventoryTrash inventoryTrash;

    public PortableCrafterContainer(EntityPlayer player, InventoryPortableCrafting PortableCraftingInventory) {
        this.player = player;
        this.world = player.worldObj;

        this.craftingMatrix = PortableCraftingInventory;
        this.craftingMatrix.setEventHandler(this);
        this.craftingResult = new InventoryCraftResult();

        this.inventoryTrash = new InventoryTrash();

        // PortableCrafting output slot
        this.addSlotToContainer(new SlotPortableCraftingResult(player, craftingMatrix, craftingResult, 0, 136, 35));

        // PortableCrafting Grid
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                this.addSlotToContainer(new SlotPortableCrafting(player, craftingMatrix, j + i * 3, 42 + j * 18, 17 + i * 18));
            }
        }

        bindPlayerInventory(player.inventory);

        this.addSlotToContainer(new SlotTrash(inventoryTrash, 0, 179 + 12, 58));

        this.onCraftMatrixChanged(craftingMatrix);
    }


    protected void bindPlayerInventory(InventoryPlayer inventoryPlayer) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 9; j++) {
                addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9,
                        20 + j * 18, 84 + i * 18));
            }
        }

        for (int i = 0; i < 9; i++) {
            addSlotToContainer(new Slot(inventoryPlayer, i, 20 + i * 18, 142));
        }
    }

    /**
     * Callback for when the crafting matrix is changed.
     *
     * @param p_75130_1_
     */
    @Override
    public void onCraftMatrixChanged(IInventory p_75130_1_) {
        craftingResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(craftingMatrix, world));
    }


    /**
     * Called when the container is closed.
     *
     * @param player
     */
    @Override
    public void onContainerClosed(EntityPlayer player) {
        super.onContainerClosed(player);

        if (!player.worldObj.isRemote) {
            this.craftingMatrix.onGuiSaved(player);
        }
    }

    @Override
    public ItemStack slotClick(int slotIndex, int par2, int par3, EntityPlayer entityPlayer) {
        if (slotIndex >= 0 && slotIndex <= inventoryItemStacks.size()) {
            ItemStack clickedStack = (ItemStack) inventoryItemStacks.get(slotIndex);
            if (clickedStack != null && clickedStack.getItem() instanceof ItemPortableCrafter && NBTHelper.hasUUID(clickedStack) && NBTHelper.getUUID(clickedStack).equals(NBTHelper.getUUID(craftingMatrix.parent))) {
                return null;
            }
        }

        return super.slotClick(slotIndex, par2, par3, entityPlayer);
    }

    /**
     * Called when a player shift-clicks on a slot. You must override this or you will crash when someone does that.
     *
     * @param entityPlayer
     * @param slotIndex
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer entityPlayer, int slotIndex) {
        ItemStack itemStack = null;
        Slot fromSlot = (Slot) this.inventorySlots.get(slotIndex);

        // getHasStack - has stuff and things in it
        if (fromSlot != null && fromSlot.getHasStack()) {
            ItemStack itemStack1 = fromSlot.getStack();
            itemStack = itemStack1.copy();

            // If we are taking the crafting result...
            if (slotIndex == 0) {
                // I guess this is true if there isnt space in the players inventory for the result
                if (!this.mergeItemStack(itemStack1, 10, 46, false)) {
                    return null;
                }
                // I dont really understand what this is doing, at this point the stacks should be identical
                fromSlot.onSlotChange(itemStack1, itemStack);
            }

            // Move from the matrix into the inventory
            else if (slotIndex >= 1 && slotIndex <= 9) {
                if (!this.mergeItemStack(itemStack1, 10, 46, false)) {
                    fromSlot.onSlotChanged();
                    return null;
                }
            }

            // Now we should try to move from the inventory to the crafting matrix
            else if (slotIndex >= 10 && slotIndex < 46) {
                if (!this.mergeItemStack(itemStack1, 1, 10, false)) {
                    return null;
                }
            }

            // So at this point, itemstack1 only contains items which could not fit when moved
            if (itemStack1.stackSize == 0) {
                // Empty the from slot if all the items were moved out
                fromSlot.putStack((ItemStack) null);
            } else {
                // Still some items in the stack, let the slot know it has changed
                fromSlot.onSlotChanged();
            }

            // If stack1 and stack are the same size, then nothing could be moved
            if (itemStack.stackSize == itemStack1.stackSize) {
                return null;
            }

            // Not sure what this does either, seems to just mark the slot as dirty
            fromSlot.onPickupFromSlot(player, itemStack1);
        }
        return itemStack;
    }

    public EntityPlayer getPlayer() {
        return player;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return true;
    }


    public void balanceMatrix() {
        boolean[] balancedSlots = new boolean[9];

        // Go through all the slots, if the stack in the slots matches stacks in any other slots, split the total between all matching slots
        for (int i = 0; i < 9; i++) {
            ItemStack currentStack = craftingMatrix.getStackInSlot(i);
            ArrayList<Integer> matchingSlotIndexes = new ArrayList<Integer>();

            // This stack has not been balanced yet
            if (balancedSlots[i] == false && currentStack != null && currentStack.isStackable()) {
                int matchingStacks = 1;
                int totalItems = currentStack.stackSize;
                matchingSlotIndexes.add(i);

                // It is possible to balance this stack if other stacks are the same type, ignore previous currentStacks
                for (int j = i + 1; j < 9; j++) {
                    // Now we find how many stacks are stackable with the current one
                    ItemStack tStack = craftingMatrix.getStackInSlot(j);

                    if (tStack != null && PortableCraftingHelper.stacksMatch(currentStack, tStack)) {
                        matchingSlotIndexes.add(j);
                        matchingStacks++;
                        totalItems += tStack.stackSize;
                        balancedSlots[j] = true;
                    }
                }

                int balancedItemSize = totalItems / matchingStacks;
                int remainingItemSize = totalItems % matchingStacks;

                for (Integer index : matchingSlotIndexes) {
                    craftingMatrix.getStackInSlot(index).stackSize = balancedItemSize;
                    if (remainingItemSize > 0) {
                        craftingMatrix.getStackInSlot(index).stackSize += 1;
                        remainingItemSize--;
                    }
                }

                balancedSlots[i] = true;
            }
        }
    }

    public void spinMatrix() {
        this.spinMatrix(false);
    }

    public void spinMatrix(boolean reverse) {
        ArrayList<ItemStack> tempStacks = new ArrayList<ItemStack>(9);
        for (int i = 0; i < 9; i++) {
            tempStacks.add(craftingMatrix.getStackInSlot(i));
        }

        int[] original = new int[]{0, 1, 2, 3, 5, 6, 7, 8};
        int[] rotated;

        if (!reverse) {
            rotated = new int[]{3, 0, 1, 6, 2, 7, 8, 5};
        } else {
            rotated = new int[]{1, 2, 5, 0, 8, 3, 6, 7};
        }

        for (int i = 0; i < original.length; i++) {
            craftingMatrix.setInventorySlotContents(original[i], tempStacks.get(rotated[i]));
        }
    }


    public void clearMatrix() {
        for (int i = 1; i <= 9; i++) {
            transferStackInSlot(player, i);
        }
    }

//    @ContainerSectionCallback
//    @SideOnly(Side.CLIENT)
//    public Map<ContainerSection, List<Slot>> getContainerSections() {
//        HashMap<ContainerSection, List<Slot>> map = new HashMap<ContainerSection, List<Slot>>();
//        map.put(ContainerSection.INVENTORY, inventorySlots.subList(10, 46));
//        return map;
//    }
}
