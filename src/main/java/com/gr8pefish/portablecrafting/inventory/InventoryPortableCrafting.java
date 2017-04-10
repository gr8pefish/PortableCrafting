package com.gr8pefish.portablecrafting.inventory;

import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.NBTHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraftforge.items.IItemHandler;

import java.util.UUID;

public class InventoryPortableCrafting extends InventoryCrafting { //ToDo: look at forestry, chisel and bits, etc, and switch to IItemHandler

    public ItemStack parent;
    public EntityPlayer player;
    private final NonNullList<ItemStack> stackList;
    protected ItemStack[] inventory;
    private Container eventHandler;

    // This class gets instantiated when we right click a clipboard. Called from the GuiHandler
    public InventoryPortableCrafting(EntityPlayer player, ItemStack itemStack) {
        super (new PortableCrafterContainer(player), 3,3); //ToDo: Error is here, w/ instantiation, as Container.getInv() for the listeners has null stacks (or stack overflow as-is)
//        super(NonNullList.<ItemStack>withSize(width * height, ItemStack.EMPTY);, 3, 3);
        this.parent = itemStack;
        this.player = player;
        this.stackList = NonNullList.withSize(3 * 3, ItemStack.EMPTY); //width * height
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
        this.stackList.set(slotIndex, itemStack);
//        inventory[slotIndex] = itemStack;
//        if (itemStack != null && itemStack.getCount() > getInventoryStackLimit()) {
//            itemStack.setCount(getInventoryStackLimit());
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
            return this.getStackInSlot(k);
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
//        return slotIndex >= this.getSizeInventory() ? null : this.inventory[slotIndex];
        return slotIndex >= this.getSizeInventory() ? null : this.stackList.get(slotIndex);
    }

    /**
     * Returns the number of slots in the inventory.
     */
    @Override
    public int getSizeInventory() {
        return stackList.size();
    }

    /**
     * Removes from an inventory slot (first arg) up to a specified number (second arg) of items and returns them in a
     * new stack.
     * @param slotIndex - the slot to take from
     * @param amount - the amount to decrement
     */
    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (!this.stackList.get(slotIndex).isEmpty()) {
            ItemStack itemstack;

            if (this.stackList.get(slotIndex).getCount() <= amount) {
                itemstack = this.stackList.get(slotIndex);
                this.stackList.set(slotIndex, ItemStack.EMPTY);
                this.eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            } else {
                itemstack = this.stackList.get(slotIndex).splitStack(amount);

                if (this.stackList.get(slotIndex).getCount() == 0) {
                    this.stackList.set(slotIndex, ItemStack.EMPTY);
                }

                this.eventHandler.onCraftMatrixChanged(this);
                return itemstack;
            }
        } else {
            return null;
        }
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
//                this.inventory = new ItemStack[this.getSizeInventory()];

                for (int i = 0; i < tagList.tagCount(); i++) {
                    NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                    int j = stackTag.getByte("Slot");
                    if (i >= 0 && i <= this.getSizeInventory()) {
                        this.stackList.set(j, new ItemStack(stackTag));
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

        for (int i = 0; i < this.getSizeInventory(); i++) {
            if (!this.stackList.get(i).isEmpty()) {
                NBTTagCompound tagCompound = new NBTTagCompound();
                tagCompound.setByte("Slot", (byte) i);
                this.stackList.get(i).writeToNBT(tagCompound);
                tagList.appendTag(tagCompound);
            }
        }
        nbtTagCompound.setTag(Reference.NBT.SAVED_INVENTORY_TAG, tagList);
    }
}
