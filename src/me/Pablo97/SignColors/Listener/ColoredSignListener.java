package me.Pablo97.SignColors.Listener;

import me.Pablo97.SignColors.SignColors;

import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;

public class ColoredSignListener implements Listener {
	
	private SignColors plugin;
	
	public ColoredSignListener(SignColors instance)
	{
		this.plugin = instance;
		this.plugin.getServer().getPluginManager().registerEvents(this, this.plugin);
	}

	@EventHandler(priority = EventPriority.NORMAL)
	  public void onSignChange(SignChangeEvent event)
	{
	    Player p = event.getPlayer();
	    if (!p.hasPermission("signcolors.colors"))
	    {
	      return;
	    }
	    if (this.plugin.signcrafting == true)
	    {
	    	if(!p.hasPermission("signcolors.craftsign.bypass"))
	    	{
			    if (p.getItemInHand().getItemMeta().hasLore())
			    {
			    	for (int i = 0; i <= 3; i++)
			    	{
			  	      String cc = this.plugin.getConfig().getString("colorsymbol");
			  	      String linie = event.getLine(i);
			  	      linie = linie.replace(cc, "§");
			  	      linie = linie.replace(cc, "§");
			  	      event.setLine(i, linie);
			  	    }
			  	}
	    	} else
	    	{
	    		for (int i = 0; i <= 3; i++)
		    	{
		  	      String cc = this.plugin.getConfig().getString("colorsymbol");
		  	      String linie = event.getLine(i);
		  	      linie = linie.replace(cc, "§");
		  	      linie = linie.replace(cc, "§");
		  	      event.setLine(i, linie);
		  	    }
	    	}
	    } else if(this.plugin.signcrafting == false)
	    {
	    	for (int i = 0; i <= 3; i++) {
		  	      String cc = this.plugin.getConfig().getString("colorsymbol");
		  	      String linie = event.getLine(i);
		  	      linie = linie.replace(cc, "§");
		  	      linie = linie.replace(cc, "§");
		  	      event.setLine(i, linie);
		  	    }
	    }
	  }

	  @EventHandler(priority = EventPriority.NORMAL)
	  public void signChange(SignChangeEvent sign)
	  {
	    Player p = sign.getPlayer();
	    if ((sign.getLine(0).equalsIgnoreCase("[SignColors]")))
	    {
	      sign.getLine(1).toString();
	      sign.getLine(2).toString();
	      if (p.hasPermission("signcolors.sign.create"))
	      {
	        sign.setLine(0, ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "SC" + ChatColor.GOLD + "]");
	        sign.setLine(1, SignColors.replaceColors(this.plugin.slone));
	        sign.setLine(2, SignColors.replaceColors(this.plugin.sltwo) + this.plugin.getConfig().getInt("signamount") + SignColors.replaceColors(this.plugin.sltwob));
	        sign.setLine(3, SignColors.replaceColors(this.plugin.slthree)  + this.plugin.getConfig().getDouble("price") + " $");
	      } else
	      {
	        p.sendMessage(SignColors.replaceColors(SignColors.replaceColors(this.plugin.sclogo)) + SignColors.replaceColors(this.plugin.noaction));
	      }
	    }
	  }
	    
	  @SuppressWarnings("deprecation")
      @EventHandler(priority = EventPriority.NORMAL)
	  public void onPlayerInteract(PlayerInteractEvent e)
	  {
	   Player p = e.getPlayer();
	   double price = this.plugin.getConfig().getDouble("price");
	   if(e.getAction() == Action.RIGHT_CLICK_BLOCK)
	   {
		    if(e.getClickedBlock().getState() instanceof Sign)
		    {
		    	Sign s = (Sign) e.getClickedBlock().getState();
		    	if(s.getLine(0).equalsIgnoreCase(ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "SC" + ChatColor.GOLD + "]"))
		    	{
				    if(p.hasPermission("signcolors.sign.use"))
				    {
				    	if (SignColors.eco.getBalance(p.getName()) < price)
				    	{
				    	      p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.notenmoney));
				    	      return;
				    	}
				    	    if (p.getInventory().firstEmpty() == -1)
				    	    {
				    	      p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.notenspace));
				    	      return;
				    	    }
				    	    SignColors.eco.withdrawPlayer(p.getName(), price);
				    		p.getInventory().addItem(new ItemStack[] {this.plugin.i});
				    		p.updateInventory();
				    	    p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + ChatColor.GOLD + "-" + SignColors.eco.format(price) + ChatColor.GREEN +  " --->>> " + ChatColor.GOLD + SignColors.eco.format(SignColors.eco.getBalance(p.getName())));
				    		p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.signmsg)  + this.plugin.getConfig().getInt("signamount") + SignColors.replaceColors(this.plugin.signmsgb));
				    	  }
				    	else
				    {
				    	p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.noaction));
				    }
		    	}
		    }
	   }  
	   }
	  
      @EventHandler(priority = EventPriority.NORMAL)
	  public void onPlayerJoin(PlayerJoinEvent e)
	  {
		  Player p = e.getPlayer();
		  if(this.plugin.updatePlayerMsg == true && p.hasPermission("signcolors.updatemsg"))
		  {
			  p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + SignColors.replaceColors(this.plugin.updatemsg) + " (v" + this.plugin.updateversion + "):");
			  p.sendMessage(SignColors.replaceColors(this.plugin.sclogo) + ChatColor.DARK_GREEN + this.plugin.updatelink);
		  } else
		  {
			  return;
		  }
	  }
}
