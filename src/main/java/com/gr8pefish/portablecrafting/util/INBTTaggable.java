package com.gr8pefish.portablecrafting.util;

import net.minecraft.nbt.NBTTagCompound;

public interface INBTTaggable {

    void readFromNBT(NBTTagCompound nbtTagCompound);

    void writeToNBT(NBTTagCompound nbtTagCompound);
}
