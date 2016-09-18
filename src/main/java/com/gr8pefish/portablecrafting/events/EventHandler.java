package com.gr8pefish.portablecrafting.events;


import com.gr8pefish.portablecrafting.handlers.CapabilityProvider;
import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import java.util.Map;

public class EventHandler {

    @SubscribeEvent
    public void AttachCapEvent(AttachCapabilitiesEvent.Item e) { //WHATS GOING ON HERE? DUN DUNDUNDUNDUN
        if (e.getItem()!= null && e.getItem() instanceof ItemPortableCrafter) { //if my item
            Map<ResourceLocation, ICapabilityProvider> map = e.getCapabilities();
            if (e.getItemStack().hasCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null)) System.out.println("has cap");
            for (Map.Entry<ResourceLocation,ICapabilityProvider> entry : map.entrySet()) { //empty always it seems
                ResourceLocation key = entry.getKey();
                ICapabilityProvider value = entry.getValue();
                System.out.println("KEY: "+key.toString());
                System.out.println("VALUE: "+value.toString());
            }
            if (e.getCapabilities().isEmpty()) { //add capability if nonexistent //TODO, this is occurring every time for some reason
                e.getItem().initCapabilities(e.getItemStack(), e.getItemStack().serializeNBT()); //what does this do?
                System.out.println("adding cap");
                e.addCapability(new ResourceLocation(Reference.MOD.MODID+".portableCrafter"), new CapabilityProvider());
            }else{
                CapabilityProvider provider = (CapabilityProvider)e.getCapabilities().get(new ResourceLocation(Reference.MOD.MODID));
                ItemStackHandler handler = (ItemStackHandler)provider.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
                System.out.println("setting to apple");
                handler.setStackInSlot(1, new ItemStack(Items.APPLE));
            }
        }
    }
}
