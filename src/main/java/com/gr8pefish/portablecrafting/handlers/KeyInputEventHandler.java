package com.gr8pefish.portablecrafting.handlers;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.network.PacketHandler;
import com.gr8pefish.portablecrafting.network.message.PortableCrafterMessage;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.PortableCraftingHelper;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.FMLClientHandler;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class KeyInputEventHandler {

    public static KeyBinding openCrafter;

    public void init() {
        openCrafter = new KeyBinding("key.open_portable_crafter.desc", Keyboard.KEY_X, "Portable Crafting");
        ClientRegistry.registerKeyBinding(openCrafter);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void handleKeyInputEvent(InputEvent.KeyInputEvent event) {
        if (openCrafter.isPressed()) {
            EntityPlayer player = FMLClientHandler.instance().getClientPlayerEntity();

            if (PortableCraftingHelper.getPortableCrafter(player) != null) {
                player.openGui(PortableCrafting.instance, Reference.GUI_ENUM.PORTABLE_CRAFTER.ordinal(), player.getEntityWorld(), (int)player.posX, (int)player.posY, (int)player.posZ);
                PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.OPEN_CRAFTER));
            }
        }
    }
}
