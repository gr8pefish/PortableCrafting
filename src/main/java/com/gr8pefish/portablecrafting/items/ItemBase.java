package com.gr8pefish.portablecrafting.items;

import com.gr8pefish.portablecrafting.PortableCrafting;
import com.gr8pefish.portablecrafting.reference.Reference;
import net.minecraft.item.Item;

public class ItemBase extends Item {

    public ItemBase(String unlocName){
        super();
        setCreativeTab(PortableCrafting.creativeTab);
        setUnlocalizedName(Reference.MODID +":" + unlocName);
    }

}
