package com.gr8pefish.portablecrafting.items.craftingBenches;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.items.ItemBase;
import com.gr8pefish.portablecrafting.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPortableCrafter extends ItemBase {//implements ISmartItemModel {

    public ItemPortableCrafter() {
        super("portable_crafter");
        setMaxStackSize(1);
        setHasSubtypes(true);
    }

    /**
     * Should disable the item bobbing up and down when the NBT data is saved.
     * @param oldStack - the old instance of this item
     * @param newStack - the new one
     * @param slotChanged - the clot to check
     * @return - if it should do the animation
     */
    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return false;
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
     * @param itemStack - the stack clicked
     * @param world - the world of the event
     * @param player - the player doign the clicking
     */
    @Override
    public ActionResult<ItemStack> onItemRightClick(ItemStack itemStack, World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            NBTHelper.setUUID(itemStack);

            player.openGui(PortableCrafting.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return ActionResult.newResult(EnumActionResult.SUCCESS, itemStack);
    }

    /**
     * Allows items to add custom lines of information to the mouseover description
     *
     * @param itemStack - the itemstack to modify
     * @param player - the player
     * @param list - the information to add
     * @param advanced - to add advanced info
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced) {
        TextFormatting hl = TextFormatting.WHITE;
        TextFormatting rst = TextFormatting.GRAY;
        
        list.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode()) + rst + " key to balance stacks");
        list.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode()) + rst + " key to clear the grid");
        list.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode()) + rst + " and " + hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode()) + rst + " keys to spin");

        if (itemStack.getItemDamage() == 1)
            list.add(TextFormatting.ITALIC + "Bendy");
    }

    //TODO: all past here

//    @Override
//    public IBakedModel handleItemState(ItemStack stack) {
//        return Minecraft.getMinecraft().getRenderItem().getItemModelMesher().getItemModel(stack);
//    }
//
//    @Override
//    public List<BakedQuad> getFaceQuads(EnumFacing p_177551_1_) {
//        return null;
//    }
//
//    @Override
//    public List<BakedQuad> getGeneralQuads() {
//        return null;
//    }
//
//    @Override
//    public boolean isAmbientOcclusion() {
//        return false;
//    }
//
//    @Override
//    public boolean isGui3d() {
//        return false;
//    }
//
//    @Override
//    public boolean isBuiltInRenderer() {
//        return false;
//    }
//
//    @Override
//    public TextureAtlasSprite getParticleTexture() {
//        return null;
//    }
//
//    @Override
//    public ItemCameraTransforms getItemCameraTransforms() {
//        return null;
//    }
}
