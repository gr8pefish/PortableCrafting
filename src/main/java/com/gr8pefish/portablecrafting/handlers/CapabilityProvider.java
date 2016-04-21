package com.gr8pefish.portablecrafting.handlers;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class CapabilityProvider implements ICapabilityProvider {

    @Override
    public boolean hasCapability(Capability<?> cap, EnumFacing side) {
        return cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY;
    }

    @Override
    public <T> T getCapability(Capability<T> cap, EnumFacing side) {
        if(cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) new ItemStackHandler(9);
        return null;
    }
}
