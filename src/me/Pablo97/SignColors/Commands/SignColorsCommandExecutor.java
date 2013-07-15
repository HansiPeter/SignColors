package me.Pablo97.SignColors.Commands;

import me.Pablo97.SignColors.SignColors;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SignColorsCommandExecutor implements CommandExecutor{
	
	private SignColors plugin; 
	
	public SignColorsCommandExecutor(SignColors instance)
	{
		this.plugin = instance;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args)
	{
	  if (args.length == 0)
	  {
			if (!sender.hasPermission("signcolors.info"))
			{
				sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.nocmd));
				return true;
			}
			sender.sendMessage(ChatColor.GOLD + "+--------------------[" + ChatColor.DARK_AQUA + "SignColors" + ChatColor.GOLD + "]--------------------+");
			sender.sendMessage(ChatColor.GOLD + SignColors.replaceColors(this.plugin.sciauthor) + ChatColor.GREEN + " Pablo97");
			sender.sendMessage(ChatColor.GOLD + "Version: " + ChatColor.GREEN + this.plugin.getDescription().getVersion());
			sender.sendMessage(ChatColor.GOLD + SignColors.replaceColors(this.plugin.scicmd) + ChatColor.GREEN + " " + SignColors.replaceColors(this.plugin.scicmdh));
			sender.sendMessage(ChatColor.GOLD + "+--------------------------------------------------+");
			return true;
	  }
	  if (args.length == 2)
	  {
		  if (args[0].equalsIgnoreCase("colorsymbol"))
		  {
			  if (!sender.hasPermission("signcolors.colorsymbol"))
			  {
				  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.nocmd));
				  return true;
			  }
			  if (args[1].length() == 1)
			  {
				  
				  this.plugin.getConfig().set("colorsymbol", args[1]);
				  this.plugin.saveConfig();
				  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.csch) + " " + ChatColor.RED + args[1]);
				  return true;
			  }
			  else
			  {
				  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.cschtma));
				  return true;
			  }
		  } else
		  {
			  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.uncmd));
			  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.uncmdh));
			  return true;
		  }
	  } else if (args.length == 1)
	  {
			  if (args[0].equalsIgnoreCase("reload"))
			  {
				  if (!sender.hasPermission("signcolors.reload"))
				  {
					  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.nocmd));
					  return true;
				  }
				  this.plugin.reloadConfig();
				  this.plugin.startMetrics();
				  this.plugin.loadLanguages();
				  this.plugin.craftSigns();
				  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.configre));
				  return true;
			  }
			  else if (args[0].equalsIgnoreCase("help"))
			  {
				  if (!sender.hasPermission("signcolors.help"))
				  {
					  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.nocmd));
					  return true;
				  }
				  showCommands(sender);
				  return true;
			  }
			  else
			  {
				  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.uncmd));
				  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.uncmdh));
				  return true;
			  }
		  }
		  else
		  {
			  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.uncmd));
			  sender.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.uncmdh));
			  return true;
		  }
	  }
	
	  public void showCommands(CommandSender sender)
	  {
			sender.sendMessage(ChatColor.GOLD + "+--------------------[" + ChatColor.DARK_AQUA + "SignColors" + ChatColor.GOLD + "]--------------------+");
			sender.sendMessage(ChatColor.GOLD + "/sc" + ChatColor.GREEN + " --- " + SignColors.replaceColors(this.plugin.sc));
			sender.sendMessage(ChatColor.GOLD + "/sc help" + ChatColor.GREEN + " --- " + SignColors.replaceColors(this.plugin.schelp));
			sender.sendMessage(ChatColor.GOLD + "/sc reload" + ChatColor.GREEN + " --- " + SignColors.replaceColors(this.plugin.scre));
			sender.sendMessage(ChatColor.GOLD + "/sc colorsymbol [symbol]" + ChatColor.GREEN + " --- " + SignColors.replaceColors(this.plugin.sccs));
			sender.sendMessage(ChatColor.GOLD + "+--------------------------------------------------+");
			return;
	  }
}