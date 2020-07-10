package fr.lycoon.cameractrl;

import fr.lycoon.cameractrl.command.LocCommand;
import fr.lycoon.cameractrl.network.GamemodeChangePacket;
import fr.lycoon.cameractrl.network.GamemodeChangePacket.HandlerServerGamemode;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

/* 
 * ########################## CameraCTRL mod ##########################
 * 		                    Author: Hugo BOIS
 * 				           Started: 17/05/2020
 * 
 * 			     Mod under Creative Commons BY-NC-SA 2020
 * #####################################################################
*/

@Mod(modid = MainCameraCTRL.MODID, name = MainCameraCTRL.NAME, version = MainCameraCTRL.VERSION)
public class MainCameraCTRL
{
	@SidedProxy(clientSide="fr.lycoon.cameractrl.ClientProxy")
	public static CommonProxy proxy;
	public static SimpleNetworkWrapper network;
	
    public static final String MODID = "cameractrl";
    public static final String NAME = "CameraCTRL";
    public static final String VERSION = "1.1";

	@EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        System.out.println("[PRE-INIT] CameraCTRL mod");
        proxy.preInit(event);
        
        // Event bus
        MinecraftForge.EVENT_BUS.register(new KeyInputHandler());
        
        // Packets
        network = NetworkRegistry.INSTANCE.newSimpleChannel("cameraCTRL");
        network.registerMessage(HandlerServerGamemode.class, GamemodeChangePacket.class, 0, Side.SERVER);
    }

    @EventHandler
    public void init(FMLInitializationEvent event)
    {
        System.out.println("[INIT] CameraCTRL mod");
        proxy.init(event);
    }
    
    @EventHandler
    public void postInit(FMLPostInitializationEvent event)
    {
    	System.out.println("[POST-INIT] CameraCTRL mod");
        proxy.postInit(event);
    }
    
    @EventHandler
    public void init(FMLServerStartingEvent event)
    {
      event.registerServerCommand(new LocCommand());
    }
}
