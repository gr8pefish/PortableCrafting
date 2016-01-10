package com.gr8pefish.portablecrafting.proxy;

import com.gr8pefish.portablecrafting.handlers.KeyInputEventHandler;
import com.gr8pefish.portablecrafting.items.ItemRegistry;
import com.gr8pefish.portablecrafting.reference.Reference;
import com.gr8pefish.portablecrafting.util.InventoryRenderHelper;
import net.minecraftforge.client.model.obj.OBJLoader;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy {

    public static KeyInputEventHandler keyInputEventHandler;

    private InventoryRenderHelper renderHelper;

    @Override
    public InventoryRenderHelper getRenderHelper() {
        return renderHelper;
    }

    @Override
    public void preInit() {
        super.preInit();

        keyInputEventHandler = new KeyInputEventHandler();
        keyInputEventHandler.init();

        renderHelper = new InventoryRenderHelper(Reference.DOMAIN);

        OBJLoader.instance.addDomain(Reference.MODID);

        ItemRegistry.registerRenders();
    }

}
