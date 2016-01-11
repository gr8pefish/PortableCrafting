package com.gr8pefish.portablecrafting.network;

import com.gr8pefish.portablecrafting.network.message.PortableCrafterMessage;
import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static SimpleNetworkWrapper network;
    private static int nextPacketId = 0;

    //initializes the wrapper and then the messages
    public static void init() {
        network = NetworkRegistry.INSTANCE.newSimpleChannel(Reference.MOD.MODID.toUpperCase());
        registerMessage(PortableCrafterMessage.Handler.class, PortableCrafterMessage.class, Side.SERVER);
    }

    @SuppressWarnings({"unchecked"})
    private static void registerMessage(Class packet, Class message, Side side) {
        network.registerMessage(packet, message, nextPacketId, side);
        nextPacketId++;
    }
}