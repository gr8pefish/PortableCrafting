package com.gr8pefish.portablecrafting.items.craftingBenches;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.items.ItemBase;
import com.gr8pefish.portablecrafting.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;

public class ItemPortableCrafter extends ItemBase {//implements ISmartItemModel {

    public static final String PC_NAME = "portable_crafter";

    public ItemPortableCrafter() {
        super(PC_NAME);
        setMaxStackSize(1);
//        setHasSubtypes(true);
    }


    /**
     * Should disable the item bobbing up and down when the NBT data is saved.
     * @param oldStack - the old instance of this item
     * @param newStack - the new one
     * @param slotChanged - the slot to check
     * @return - if it should do the animation
     */
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return !oldStack.getItem().equals(newStack.getItem());
    }

    /**
     * If this function returns true (or the item is damageable), the ItemStack's NBT tag will be sent to the client.
     */
    @Override
    public boolean getShareTag() {
        return true;
    }

    /**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     *
     * @param world - the world of the event
     * @param player - the player doing the clicking
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        ItemStack itemStack = player.getHeldItem(handIn);

        if (!world.isRemote) {
            NBTHelper.setUUID(itemStack);

            player.openGui(PortableCrafting.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    /**
     * Allows items to add custom lines of information to the mouseover description
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        TextFormatting hl = TextFormatting.WHITE;
        TextFormatting rst = TextFormatting.GRAY;
        
        tooltip.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode()) + rst + " key to balance stacks");
        tooltip.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode()) + rst + " key to clear the grid");
        tooltip.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode()) + rst + " and " + hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode()) + rst + " keys to spin");

    }

}
