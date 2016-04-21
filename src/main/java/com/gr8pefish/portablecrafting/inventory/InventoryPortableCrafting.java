package com.gr8pefish.portablecrafting.inventory;

import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.UUID;

public class InventoryPortableCrafting extends InventoryCrafting {

    public ItemStack parent;
    public EntityPlayer player;

    protected ItemStack[] inventory;
    private Container eventHandler;

    private ItemStackHandler handler;

    // This class gets instantiated when we right click a clipboard. Called from the GuiHandler
    public InventoryPortableCrafting(EntityPlayer player, ItemStack itemStack) {
        super(null, 3, 3);

        this.parent = itemStack;
        IItemHandler iitem = parent.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (iitem != null){
            try{
                System.out.println("Successful loading");
                this.handler = (ItemStackHandler) iitem;
            }catch (Exception e){
                System.out.println("Unsucessful loading");
                this.handler = new ItemStackHandler(9);
                System.out.println(e.toString());
            }
        }
        this.player = player;

        this.inventory = new ItemStack[this.getSizeInventory()];

        readFromNBT(parent.getTagCompound());
    }

    public void setEventHandler(Container eventHandler) {
        this.eventHandler = eventHandler;
    }

    /**
     * Sets the given item stack to the specified slot in the inventory (can be crafting or armor sections).
     * @param slotIndex - the slot to check
     * @param itemStack - the itemstack to check
     */
    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        handler.setStackInSlot(slotIndex, itemStack); //just eats my items :( I click on the slot and they disappear immediately
//        inventory[slotIndex] = itemStack;
//        if (itemStack != null && itemStack.stackSize > getInventoryStackLimit()) {
//            itemStack.stackSize = getInventoryStackLimit();
//        }
        eventHandler.onCraftMatrixChanged(this);
    }

    /**
     * Returns the itemstack in the slot specified (Top left is 0, 0). Args: row, column
     * @param row - the row
     * @param col - the column
     */
    @Override
    public ItemStack getStackInRowAndColumn(int row, int col) {
        if (row >= 0 && row < 3) {
            int k = row + col * 3;
            return handler.getStackInSlot(k);
            //return this.getStackInSlot(k);
        } else {
            return null;
        }
    }

    /**
     * Returns the stack in slot i
     * @param slotIndex - the slot to check
     */
    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return handler.getStackInSlot(slotIndex);
        //return slotIndex >= this.getSizeInventory() ? null : this.inventory[slotIndex];
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return handler.getSlots();
        //return 9;
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     * @param slotIndex - the slot to take from
     * @param amount - the amount to decrement
     */
    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        return handler.extractItem(slotIndex, amount, false);
//        if (this.inventory[slotIndex] != null) {
//            ItemStack itemstack;
//
//            if (this.inventory[slotIndex].stackSize <= amount) {
//                itemstack = this.inventory[slotIndex];
//                this.inventory[slotIndex] = null;
//                this.eventHandler.onCraftMatrixChanged(this);
//                return itemstack;
//            } else {
//                itemstack = this.inventory[slotIndex].splitStack(amount);
//
//                if (this.inventory[slotIndex].stackSize == 0) {
//                    this.inventory[slotIndex] = null;
//                }
//
//                this.eventHandler.onCraftMatrixChanged(this);
//                return itemstack;
//            }
//        } else {
//            return null;
//        }
    }

    /**
     * Returns the name of the inventory
     */
    @Override
    public String getName() {
        return "crafting.inventory";
    }

    /**
     * Returns the maximum stack size for a inventory slot.
     */
    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    /**
     * For tile entities, ensures the chunk containing the tile entity is saved to disk later - the game won't think it
     * hasn't changed and skip it.
     */
    @Override
    public void markDirty() {
        //nothing
    }

    @Override
    public void openInventory(EntityPlayer player) {
        //nothing
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        //nothing
    }

    //Methods to make sure the stack is unique

    public void onGuiSaved(EntityPlayer entityPlayer) {
        if (parent != null)
            save();
    }

    public ItemStack findParentItemStack(EntityPlayer entityPlayer) {
        if (NBTHelper.hasUUID(parent)) {
            UUID parentUUID = new UUID(parent.getTagCompound().getLong(Reference.NBT.MOST_SIG_UUID), parent.getTagCompound().getLong(Reference.NBT.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++) {
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (itemStack != null && itemStack.getItem() instanceof ItemPortableCrafter && NBTHelper.hasUUID(itemStack)) {
                    if (itemStack.getTagCompound().getLong(Reference.NBT.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() &&
                            itemStack.getTagCompound().getLong(Reference.NBT.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()) {
                        return itemStack;
                    }
                }
            }
        }
        return null;
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound) {
        parent = findParentItemStack(player);

        if (parent != null) {
            nbtTagCompound = parent.getTagCompound();

            if (nbtTagCompound != null && nbtTagCompound.hasKey(Reference.NBT.SAVED_INVENTORY_TAG)) {
                NBTTagList tagList = nbtTagCompound.getTagList(Reference.NBT.SAVED_INVENTORY_TAG, 10);
                this.inventory = new ItemStack[this.getSizeInventory()];

                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    int j = stackTag.getByte("Slot");
                    if (i >= 0 && i <= inventory.length) {
                        this.inventory[j] = ItemStack.loadItemStackFromNBT(stackTag);
                    }
                }
            }
        }
    }

    //Methods to save the data (the inventory, or the items in the crafting grid)

    public void save() {
        NBTTagCompound nbtTagCompound = parent.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        writeToNBT(nbtTagCompound);
        parent.setTagCompound(nbtTagCompound);
    }


    public void writeToNBT(NBTTagCompound nbtTagCompound) {
        nbtTagCompound = findParentItemStack(player).getTagCompound();

        // Write the ItemStacks in the inventory to NBT
        NBTTagList tagList = new NBTTagList();

        for (int i = 0; i < inventory.length; i++) {
            if (inventory[i] != null) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                inventory[i].writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag(Reference.NBT.SAVED_INVENTORY_TAG, tagList);
    }
}
