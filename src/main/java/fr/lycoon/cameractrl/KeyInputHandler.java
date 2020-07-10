package fr.lycoon.cameractrl;

import java.io.IOException;

import fr.lycoon.cameractrl.network.GamemodeChangePacket;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent.KeyInputEvent;

public class KeyInputHandler 
{
	public static boolean cameraToggled = false;
	
    @SubscribeEvent
    public void onKeyInput(KeyInputEvent event) throws IOException
    {
        if (ClientProxy.toggle.isPressed())
        {
        	EntityPlayerSP player = Minecraft.getMinecraft().player;
        	GameSettings options = Minecraft.getMinecraft().gameSettings;
        	cameraToggled = !cameraToggled;
        	
            if (cameraToggled)
            {
            	// Settings set to saved camera
            	SettingsFileHandler.saveSettings(options, "old.cfg");
            	
            	// If no camera.cfg, then no settings to define
            	if (!SettingsFileHandler.applySettings(options, player, "camera.cfg"))
            	{
            		cameraToggled = false;
            		player.sendMessage(new TextComponentString(TextFormatting.RED+ 
            				"No camera settings have been copied yet.\n"+
            				TextFormatting.ITALIC+ "Use '" +ClientProxy.copy.getDisplayName()+ "' to first copy your camera settings."));
            		return;
            	}
            }
            else
            {
            	// Retrieving old settings when exiting camera mode
            	if (!SettingsFileHandler.applySettings(options, player, "old.cfg"))
            	{
            		player.sendMessage(new TextComponentString(TextFormatting.RED+ 
            				"The old.cfg file has been deleted. Previous settings could not have been retrieved."));
            		return;
            	}
            }
            
        	player.sendMessage(new TextComponentString("Saved camera settings " + 
        			(cameraToggled ? TextFormatting.GREEN+ "applied" : TextFormatting.RED+ "disabled") + "."));
        	
        	MainCameraCTRL.network.sendToServer(new GamemodeChangePacket(cameraToggled));
            options.hideGUI = cameraToggled;
            options.smoothCamera = cameraToggled;
        }
        else if (ClientProxy.copy.isPressed())
        {
        	EntityPlayerSP player = Minecraft.getMinecraft().player;
        	player.sendMessage(new TextComponentString(TextFormatting.GREEN+ 
        			"Current camera settings copied. Use '" +ClientProxy.toggle.getDisplayName()+ "' to apply them."));
            
            // Saving current settings
        	GameSettings options = Minecraft.getMinecraft().gameSettings;
            SettingsFileHandler.saveSettings(options, "camera.cfg");
        }
    }
}
