package com.gr8pefish.portablecrafting.handlers;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.network.PacketHandler;
import com.gr8pefish.portablecrafting.network.message.PortableCrafterMessage;
import com.gr8pefish.portablecrafting.reference.Keybindings;
import com.gr8pefish.portablecrafting.reference.Misc;
import com.gr8pefish.portablecrafting.util.PortableCraftingHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInputEventHandler {

    public void init() {
        ClientRegistry.registerKeyBinding(Keybindings.openIBench);
        FMLCommonHandler.instance().bus().register(this);
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (Keybindings.openIBench.isPressed()) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            if (PortableCraftingHelper.getPortableCrafter(player) != null) {
                player.openGui(PortableCrafting.instance, Misc.GUIs.PORTABLE_CRAFTER.ordinal(), player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);
                PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.OPEN_IBENCH));
            }
        }
    }
}
