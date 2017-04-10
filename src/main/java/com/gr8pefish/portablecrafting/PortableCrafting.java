package com.gr8pefish.portablecrafting;

import com.gr8pefish.portablecrafting.handlers.GuiHandler;
import com.gr8pefish.portablecrafting.items.ItemRegistry;
import com.gr8pefish.portablecrafting.network.PacketHandler;
import com.gr8pefish.portablecrafting.proxy.CommonProxy;
import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;

@Mod(modid = com.gr8pefish.portablecrafting.reference.Reference.MOD.MODID, name = Reference.MOD.MOD_NAME, version = Reference.MOD.VERSION, guiFactory = Reference.MOD.GUI_FACTORY_CLASS)
public class PortableCrafting {

    //Proxies
    @SidedProxy(clientSide = Reference.MOD.CLIENT_PROXY, serverSide = Reference.MOD.COMMON_PROXY)
    public static CommonProxy proxy;

    //Creative Tab
    public static final CreativeTabs creativeTab = new CreativeTabs(Reference.MOD.MODID) {
        @Override
        public ItemStack getTabIconItem() {
            return new ItemStack(ItemRegistry.portableCrafter);
        }
    };

    //Mod Instance
    @Mod.Instance
    public static PortableCrafting instance;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        //packets
        PacketHandler.init();

        //items
        ItemRegistry.registerItems();

        //init keybindings and renderers
        proxy.preInit();
    }


    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {

        //Gui Handler
        NetworkRegistry.INSTANCE.registerGuiHandler(instance, new GuiHandler());

        //recipes
        ItemRegistry.initRecipes();
    }


    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }


}
