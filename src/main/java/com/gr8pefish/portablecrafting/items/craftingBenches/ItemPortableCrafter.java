package com.gr8pefish.portablecrafting.items.craftingBenches;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.items.ItemBase;
import com.gr8pefish.portablecrafting.util.NBTHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemPortableCrafter extends ItemBase {

    public ItemPortableCrafter() {
        super("portable_crafter");
        setMaxStackSize(1);
        setHasSubtypes(true);
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
     * @param itemStack
     * @param world
     * @param player
     */
    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (!world.isRemote) {
            NBTHelper.setUUID(itemStack);

            player.openGui(PortableCrafting.instance, 0, world, (int) player.posX, (int) player.posY, (int) player.posZ);
        }
        return itemStack;
    }

//    /**
//     * 1.8 Specific model rendering for items with different damage values
//     */
//    @SideOnly(Side.CLIENT)
//    public void initModel() {
//        final ModelResourceLocation basicModel = new ModelResourceLocation(getRegistryName(), "inventory"); //"inventory" is used to indicate the model as it should render in the inventory itself
//        final ModelResourceLocation plusModel = new ModelResourceLocation(getRegistryName() + "_plus", "inventory");
//        System.out.println("REGNAME: "+getRegistryName());
//        portablecrafting.logger.info("REGNAME: "+getRegistryName());
//
//        ModelBakery.registerItemVariants(this, basicModel, plusModel);
//
//        ModelLoader.setCustomMeshDefinition(this, new ItemMeshDefinition() {
//            @Override
//            public ModelResourceLocation getModelLocation(ItemStack stack) {
//                if (stack.getItemDamage() == 0) {
//                    return basicModel;
//                } else {
//                    return plusModel;
//                }
//            }
//        });
//    }

    /**
     * allows items to add custom lines of information to the mouseover description
     *
     * @param itemStack
     * @param player
     * @param list
     * @param par4
     */
    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        EnumChatFormatting hl = EnumChatFormatting.WHITE;
        EnumChatFormatting rst = EnumChatFormatting.GRAY;
        
        list.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindForward.getKeyCode()) + rst + " key to balance stacks");
        list.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindBack.getKeyCode()) + rst + " key to clear the grid");
        list.add(hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindLeft.getKeyCode()) + rst + " and " + hl + GameSettings.getKeyDisplayString(Minecraft.getMinecraft().gameSettings.keyBindRight.getKeyCode()) + rst + " keys to spin");

        if (itemStack.getItemDamage() == 1)
            list.add(EnumChatFormatting.ITALIC + "Bendy");
    }

}
