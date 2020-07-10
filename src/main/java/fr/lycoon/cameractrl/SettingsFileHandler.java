package fr.lycoon.cameractrl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.player.EntityPlayer;

public class SettingsFileHandler
{
	public final static String path = "config/CameraCTRL/";
	
	public static void saveSettings(GameSettings options, String file) throws IOException
	{
		// Creating first config folder if non-existent
		File configFolder = new File(path);
		if (!configFolder.exists())
			configFolder.mkdir();
		
        File camera = new File(path + file);
    	try
    	{
			FileWriter fw = new FileWriter(camera, false);
			fw.append(String.valueOf(options.fovSetting) +"\n"+
					String.valueOf(options.mouseSensitivity));
			fw.close();
		}
    	catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	public static boolean applySettings(GameSettings options, EntityPlayer player, String file) throws FileNotFoundException
	{
		File settings = new File(path + file);
    	if (!settings.exists())
    		return false;
		
    	Scanner read = new Scanner(settings);
    	while (read.hasNextLine())
    	{
    		options.fovSetting = Float.parseFloat(read.nextLine());
    		options.mouseSensitivity = Float.parseFloat(read.nextLine());
    	}
    	read.close();
    	
    	return true;
	}
}
