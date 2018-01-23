package com.gr8pefish.portablecrafting.client.gui.button;

import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.util.ResourceLocation;

public class PortableCrafterButton extends GuiButton {

    public static final int SPIN = 0;
    public static final int BALANCE = 1;
    public static final int EMPTY = 2;

    public static final ResourceLocation widgetTextures = Reference.GUI_TEXTURE.WIDGETS;

    private int iconOffsetX = 0;
    private int iconOffsetY = 0;

    public PortableCrafterButton(int id, int xPos, int yPos, int width, int height, int type) {
        super(id, xPos, yPos, width, height, "");

        if (type == SPIN) {
            iconOffsetX = 64;
            iconOffsetY = 16;
        } else if (type == BALANCE) {
            iconOffsetX = 64;
            iconOffsetY = 32;
        } else if (type == EMPTY) {
            iconOffsetX = 64;
            iconOffsetY = 0;
        }
    }

    /**
     * Draws this button to the screen.
     *
     * @param minecraft - the minecraft to draw it to
     * @param mPosX - the x position of the mouse
     * @param mPosY - the y position of the mouse
     */
    @Override
    public void drawButton(Minecraft minecraft, int mPosX, int mPosY, float partialTicks) {
        if (this.visible) {
            minecraft.getTextureManager().bindTexture(widgetTextures);

            boolean hover = mPosX >= this.x && mPosY >= this.y && mPosX < this.x + this.width && mPosY < this.y + this.height;
            int h = getHoverState(hover);

            int fromLeft = iconOffsetX + (h-1) * 16;

            this.drawTexturedModalRect(this.x, this.y, fromLeft, iconOffsetY, 16, 16);
        }
    }
    
}
