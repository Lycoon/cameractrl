package fr.lycoon.cameractrl.command;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Scanner;

import fr.lycoon.cameractrl.SettingsFileHandler;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.fml.common.FMLCommonHandler;

public class LocCommand extends CommandBase
{
	public static BlockPos getLocation(ICommandSender sender, String name) throws FileNotFoundException
	{
		File configFolder = new File(SettingsFileHandler.path);
		if (!configFolder.exists())
			configFolder.mkdir();

		File settings = new File(SettingsFileHandler.path + "locations.cfg");
    	if (!settings.exists())
    	{
    		sender.sendMessage(new TextComponentString(TextFormatting.RED+ 
    				"No location had been set yet.\n"+
    				TextFormatting.ITALIC+ "Use '/loc add <name>' to add a new location."));
    		return null;
    	}
    	
    	Scanner read = new Scanner(settings);
    	while (read.hasNextLine())
    	{
    		String[] coord = read.nextLine().split(" ", 4);
    		if (coord[0].equals(name))
    		{
    			BlockPos pos = new BlockPos(
    					Double.valueOf(coord[1]), 
    					Double.valueOf(coord[2]), 
    					Double.valueOf(coord[3]));
    			read.close();
    			return pos;
    		}
    	}
    	read.close();
    	
		sender.sendMessage(new TextComponentString(TextFormatting.RED+ "No location named '" +name+ "' has been found."));
		return null;
	}
	
	public static void addLoc(ICommandSender sender, String name) throws IOException
	{
		File configFolder = new File(SettingsFileHandler.path);
		if (!configFolder.exists())
			configFolder.mkdir();

		File settings = new File(SettingsFileHandler.path + "locations.cfg");
    	try
    	{
			PrintWriter output = new PrintWriter(new FileOutputStream(settings, true));
			output.print(name+ " " +sender.getPosition().getX()+ " " +sender.getPosition().getY()+ 
					" " +sender.getPosition().getZ()+ "\r\n");
			output.close();
		}
    	catch (FileNotFoundException e) {e.printStackTrace();}
    	
    	sender.sendMessage(new TextComponentString(TextFormatting.GREEN+ "Added location '" +name+ "' successfully."));
	}
	
	public static void removeLoc(ICommandSender sender, String name) throws IOException
	{
		File configFolder = new File(SettingsFileHandler.path);
		if (!configFolder.exists())
			configFolder.mkdir();

		File settings = new File(SettingsFileHandler.path + "locations.cfg");
    	try
    	{
    		boolean found = false;
    		ArrayList<String> config = new ArrayList<String>(Files.readAllLines(settings.toPath(), StandardCharsets.UTF_8));
    		for (int i=0; i < config.size(); i++)
    		{
    			if (config.get(i).split(" ", 4)[0].equals(name) && !found)
    			{
    				config.remove(i);
    				found = true;;
    			}
    		}
    		Files.write(settings.toPath(), config, StandardCharsets.UTF_8);
    		
    		if (!found)
    			sender.sendMessage(new TextComponentString(TextFormatting.RED+ "No location named '" +name+ "' has been found."));
    		else
    			sender.sendMessage(new TextComponentString(TextFormatting.GREEN+ "Removed location '" +name+ "' successfully."));
    			
		}
    	catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	public static void goLoc(ICommandSender sender, String name) throws FileNotFoundException
	{
		BlockPos pos = getLocation(sender, name);
		if (pos != null)
		{
			MinecraftServer s = FMLCommonHandler.instance().getMinecraftServerInstance();
			s.getCommandManager().executeCommand(sender, "/tp " +sender.getDisplayName().getUnformattedText()+ " " +pos.getX()+ " " +pos.getY()+ " " +pos.getZ());
		}
	}
	
	public static void printList(ICommandSender sender) throws FileNotFoundException
	{
		File configFolder = new File(SettingsFileHandler.path);
		if (!configFolder.exists())
			configFolder.mkdir();

		File settings = new File(SettingsFileHandler.path + "locations.cfg");
    	if (!settings.exists())
    	{
    		sender.sendMessage(new TextComponentString(TextFormatting.RED+ 
    				"No location had been set yet.\n"+
    				TextFormatting.ITALIC+ "Use '/loc add <name>' to add a new location."));
    		return;
    	}
    	
    	Scanner read = new Scanner(settings);
    	if (read.hasNextLine())
    	{
    		sender.sendMessage(new TextComponentString(TextFormatting.BOLD+ "Locations list"));
        	while (read.hasNextLine())
        	{
        		String[] coord = read.nextLine().split(" ", 4);
        		sender.sendMessage(new TextComponentString(TextFormatting.AQUA +coord[0]+ 
        				TextFormatting.RESET +" (" +coord[1]+ ", " +coord[2]+ ", " +coord[3]+ ")"));
        	}
        	read.close();
    	}
    	else
    		sender.sendMessage(new TextComponentString(TextFormatting.RED+ 
    				"No location had been set yet.\n"+
    				TextFormatting.ITALIC+ "Use '/loc add <name>' to add a new location."));
	}
	
	public static void printHelp(ICommandSender sender)
	{
		sender.sendMessage(new TextComponentString(TextFormatting.BOLD+ "Help for /loc command"));
		sender.sendMessage(new TextComponentString(TextFormatting.AQUA+ "/loc add <name>" + TextFormatting.RESET+ 
				" - adds your current location to the list"));
		sender.sendMessage(new TextComponentString(TextFormatting.AQUA+ "/loc remove <name>" + TextFormatting.RESET+ 
				" - removes the specified location"));
		sender.sendMessage(new TextComponentString(TextFormatting.AQUA+ "/loc go <name>" + TextFormatting.RESET+ 
				" - teleports you to the specified location"));
		sender.sendMessage(new TextComponentString(TextFormatting.AQUA+ "/loc list" + TextFormatting.RESET+ 
				" - prints all the saved locations"));
	}
	
	@Override
	public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException
	{
		if (params != null)
		{
			try 
			{
				if (params.length == 2)
				{
					switch (params[0]){
						case "go": goLoc(sender, params[1]);
							return;
						case "add": addLoc(sender, params[1]);
							return;
						case "remove": removeLoc(sender, params[1]);
							return;
					}
				}
				else if (params.length == 1)
				{
					switch (params[0]){
						case "list": printList(sender);
							return;
						case "help": printHelp(sender);
							return;
					}
				}
				
	    		sender.sendMessage(new TextComponentString(TextFormatting.RED+ 
	    				"Wrong usage. Try '/loc help' to get help."));
				
			} catch (IOException e) {e.printStackTrace();}
		}
	}
	
	@Override
	public String getName()
	{
		return "loc";
	}
	
	@Override
	public String getUsage(ICommandSender sender)
	{
		return "command.loc.usage";
	}
}