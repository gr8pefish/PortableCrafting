package com.gr8pefish.portablecrafting.reference;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

@SideOnly(Side.CLIENT)
public class Keybindings {
    public static KeyBinding openIBench = new KeyBinding("key.open_ibench.desc", Keyboard.KEY_C, "portablecrafting");
}
