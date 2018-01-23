package com.gr8pefish.portablecrafting;

import com.gr8pefish.portablecrafting.items.craftingBenches.ItemPortableCrafter;
import com.gr8pefish.portablecrafting.items.craftingItems.ItemCraftingCore;
import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Registry for all the items and blocks, and their corresponding models.
 */
@Mod.EventBusSubscriber
@GameRegistry.ObjectHolder(Reference.MOD.MODID) //ObjectHolder annotations will be prepended with modid
public class RegistrarPortableCrafting {

    //Items
    @GameRegistry.ObjectHolder(ItemPortableCrafter.PC_NAME) //Static final instance populated through the registry event, with this name (prepended by modid)
    public static final Item PORTABLE_CRAFTER = new ItemPortableCrafter(); //Instantiate
    @GameRegistry.ObjectHolder(ItemCraftingCore.CC_NAME) //Static final instance populated through the registry event, with this name (prepended by modid)
    public static final Item CRAFTING_CORE = new ItemCraftingCore(); //Instantiate


    /**
     * Registers the Items (and ItemBlocks)
     */
    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        //Items
        event.getRegistry().register(PORTABLE_CRAFTER.setRegistryName(ItemPortableCrafter.PC_NAME));
        event.getRegistry().register(CRAFTING_CORE.setRegistryName(ItemCraftingCore.CC_NAME));
    }

    /**
     * Registers the textures/models for each item/block
     */
    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {

        //Items

        //Uses assets/testmod/models/item/strawberry.json to reference testmod/items/strawberry.png
        ModelLoader.setCustomModelResourceLocation(PORTABLE_CRAFTER, 0, new ModelResourceLocation(PORTABLE_CRAFTER.getRegistryName(), "inventory"));
        ModelLoader.setCustomModelResourceLocation(CRAFTING_CORE, 0, new ModelResourceLocation(CRAFTING_CORE.getRegistryName(), "inventory"));
    }


}
