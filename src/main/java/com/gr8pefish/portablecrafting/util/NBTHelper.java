package com.gr8pefish.portablecrafting.util;

import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.UUID;

public class NBTHelper {

    public static boolean hasUUID(ItemStack itemStack) {
        return has_tag(itemStack, Reference.NBT.MOST_SIG_UUID) && has_tag(itemStack, Reference.NBT.LEAST_SIG_UUID);
    }

    public static void setUUID(ItemStack itemStack) {
        initNBTCompound(itemStack);

        if (!hasUUID(itemStack)) {
            UUID itemUUID = UUID.randomUUID();

            setLong(itemStack, Reference.NBT.MOST_SIG_UUID, itemUUID.getMostSignificantBits());
            setLong(itemStack, Reference.NBT.LEAST_SIG_UUID, itemUUID.getLeastSignificantBits());
        }
    }

    public static UUID getUUID(ItemStack itemStack) {
        initNBTCompound(itemStack);

        if (hasUUID(itemStack)) {
            return new UUID(itemStack.getTagCompound().getLong(Reference.NBT.MOST_SIG_UUID),
                    itemStack.getTagCompound().getLong(Reference.NBT.LEAST_SIG_UUID));
        }
        return null;
    }

    public static void initNBTCompound(ItemStack itemStack) {
        if (itemStack.getTagCompound() == null) {
            itemStack.setTagCompound(new NBTTagCompound());
        }
    }

    public static boolean has_tag(ItemStack itemStack, String tag) {
        return itemStack != null && itemStack.hasTagCompound() && itemStack.getTagCompound().hasKey(tag);
    }

    public static void setLong(ItemStack itemStack, String tag, Long value) {
        initNBTCompound(itemStack);
        NBTTagCompound tagCompound = itemStack.getTagCompound();
        tagCompound.setLong(tag, value);
    }
}
