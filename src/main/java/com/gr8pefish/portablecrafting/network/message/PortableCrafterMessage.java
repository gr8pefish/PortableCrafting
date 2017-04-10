package com.gr8pefish.portablecrafting.network.message;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
import com.gr8pefish.portablecrafting.reference.Reference;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PortableCrafterMessage implements IMessage {

    int action;

    public static final int CLEAR_MATRIX = 1;
    public static final int SPIN_MATRIX = 2;
    public static final int BALANCE_MATRIX = 3;
    public static final int SPIN_MATRIX_LEFT = 4;
    public static final int OPEN_CRAFTER = 5;

    // MUST ALWAYS HAVE DEFAULT CONSTUCTOR OR WE WILL CRASH, IDIOT!
    public PortableCrafterMessage() { }

    public PortableCrafterMessage(int action) {
        this.action = action;
    }

    /**
     * Convert from the supplied buffer into your specific message type
     * @param buf - the input
     */
    @Override
    public void fromBytes(ByteBuf buf) {
        action = ByteBufUtils.readVarShort(buf);
    }

    /**
     * Deconstruct your message into the supplied byte buffer
     * @param buf - the output
     */
    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeVarShort(buf, action);
    }


    public static class Handler implements IMessageHandler<PortableCrafterMessage, IMessage> {
        /**
         * Called when a message is received of the appropriate type. You can optionally return a reply message, or null if no reply
         * is needed.
         * @param message The message
         * @param ctx - the context
         * @return - an optional return message (null means no reply)
         */
        @Override
        public IMessage onMessage(PortableCrafterMessage message, MessageContext ctx) {
            PortableCrafterContainer container;
            switch (message.action) {
                case PortableCrafterMessage.BALANCE_MATRIX:
                    container = (PortableCrafterContainer)ctx.getServerHandler().playerEntity.openContainer;
                    container.balanceMatrix();
                    break;
                case PortableCrafterMessage.SPIN_MATRIX:
                    container = (PortableCrafterContainer)ctx.getServerHandler().playerEntity.openContainer;
                    container.spinMatrix();
                    break;
                case PortableCrafterMessage.SPIN_MATRIX_LEFT:
                    container = (PortableCrafterContainer)ctx.getServerHandler().playerEntity.openContainer;
                    container.spinMatrix(true);
                    break;
                case PortableCrafterMessage.CLEAR_MATRIX:
                    container = (PortableCrafterContainer)ctx.getServerHandler().playerEntity.openContainer;
                    container.clearMatrix();
                    break;
                case PortableCrafterMessage.OPEN_CRAFTER:
                    EntityPlayer player = ctx.getServerHandler().playerEntity;
                    player.openGui(PortableCrafting.instance, Reference.GUI_ENUM.PORTABLE_CRAFTER.ordinal(), player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
                    break;
            }

            return null;
        }
    }

}
