package com.gr8pefish.portablecrafting.client.gui.inventory;

import com.gr8pefish.portablecrafting.client.gui.button.PortableCrafterButton;
import com.gr8pefish.portablecrafting.inventory.PortableCrafterContainer;
import com.gr8pefish.portablecrafting.network.PacketHandler;
import com.gr8pefish.portablecrafting.network.message.PortableCrafterMessage;
import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class PortableCrafterGui extends GuiContainer {

    private PortableCrafterContainer container;
    private EntityPlayer player;

    private PortableCrafterButton balanceButton;
    private PortableCrafterButton spinButton;
    private PortableCrafterButton clearButton;

    public PortableCrafterGui(PortableCrafterContainer container) {
        super(container);
        this.player = container.getPlayer();
        this.container = container;
        xSize = 201;
        ySize = 166;
    }

    @Override
    public void initGui() {
        super.initGui();

        int xStart = ((width - xSize) / 2) + 12;
        int yStart = (height - ySize) / 2;

        this.buttonList.add(this.balanceButton = new PortableCrafterButton(1, xStart + 10, yStart + 16, 11, 16, PortableCrafterButton.BALANCE));
        this.buttonList.add(this.spinButton = new PortableCrafterButton(2, xStart + 10, yStart + 32, 11, 11, PortableCrafterButton.SPIN));
        this.buttonList.add(this.clearButton = new PortableCrafterButton(3, xStart + 10, yStart + 48, 11, 11, PortableCrafterButton.EMPTY));
    }


    @Override
    protected void drawGuiContainerForegroundLayer(int x, int y) {
        fontRendererObj.drawString(StatCollector.translateToLocal("crafting.portablecrafting"), 41, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 20, 73, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float opacity, int x, int y) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(Reference.GUI_TEXTURE.PORTABLE_CRAFTER);

        int xStart = (width - xSize) / 2;
        int yStart = (height - ySize) / 2;
        this.drawTexturedModalRect(xStart + 12, yStart, 0, 0, xSize, ySize);
    }

    /**
     * Fired when a key is typed. This is the equivalent of KeyListener.keyTyped(KeyEvent e).
     *
     * @param p_73869_1_
     * @param p_73869_2_
     */
    @Override
    protected void keyTyped(char p_73869_1_, int p_73869_2_) throws IOException {
        if (p_73869_2_ == this.mc.gameSettings.keyBindBack.getKeyCode()) {
            this.container.clearMatrix();
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.CLEAR_MATRIX));
        }
        if (p_73869_2_ == this.mc.gameSettings.keyBindRight.getKeyCode()) {
            this.container.spinMatrix();
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.SPIN_MATRIX));
        }
        if (p_73869_2_ == this.mc.gameSettings.keyBindLeft.getKeyCode()) {
            this.container.spinMatrix(true);
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.SPIN_MATRIX_LEFT));
        }

        if (p_73869_2_ == this.mc.gameSettings.keyBindForward.getKeyCode()) {
            this.container.balanceMatrix();
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.BALANCE_MATRIX));
        }

        super.keyTyped(p_73869_1_, p_73869_2_);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == balanceButton) {
            this.container.balanceMatrix();
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.BALANCE_MATRIX));
        } else if (button == spinButton) {
            this.container.spinMatrix();
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.SPIN_MATRIX));
        } else if (button == clearButton) {
            this.container.clearMatrix();
            PacketHandler.network.sendToServer(new PortableCrafterMessage(PortableCrafterMessage.CLEAR_MATRIX));
        }
    }
}
