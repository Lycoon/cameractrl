package fr.lycoon.cameractrl;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

	public static KeyBinding toggle;
	public static KeyBinding copy;
	
    @Override
    public void preInit(FMLPreInitializationEvent e) 
    {
        super.preInit(e);
        registerKeybinds();
    }

    @Override
    public void init(FMLInitializationEvent e)
    {
        super.init(e);
    }

    @Override
    public void postInit(FMLPostInitializationEvent e) 
    {
        super.postInit(e);
    }
    
    public static void registerKeybinds()
    {
    	copy = new KeyBinding("camera.copy", Keyboard.KEY_Y, "key.categories.cameractrl");
    	toggle = new KeyBinding("camera.toggle", Keyboard.KEY_R, "key.categories.cameractrl");
    	ClientRegistry.registerKeyBinding(copy);
    	ClientRegistry.registerKeyBinding(toggle);
    }
}