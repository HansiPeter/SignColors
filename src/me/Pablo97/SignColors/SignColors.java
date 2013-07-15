package me.Pablo97.SignColors;

/*
 * SignColors v0.4
 * @author: Pablo97
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import me.Pablo97.Metrics.Metrics;
import me.Pablo97.SignColors.Commands.SignColorsCommandExecutor;
import me.Pablo97.SignColors.Listener.ColoredSignListener;
import net.milkbowl.vault.economy.Economy;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class SignColors extends JavaPlugin implements Listener {
	
	public String sclogo;	
	public String nocmd;
	public String noaction;
	public String sciauthor;
	public String scicmd;
	public String scicmdh;
	public String csch;
	public String cschtma;
	public String uncmd;
	public String uncmdh;
	public String configre;
	public String sc;
	public String schelp;
	public String scre;
	public String sccs;
	public String slone;
	public String sltwo;
	public String sltwob;
	public String slthree;
	public String signmsg;
	public String signmsgb;
	public String updatelink;
	public String updateversion;
	public String notenmoney;
	public String notenspace;
	public String updatemsg;
    
	public ItemStack i;
	public ItemMeta im;
	
	public static Economy eco = null;
	
	private List<String> lores = new ArrayList<>();
	
	public boolean languageEN;
	public boolean signcrafting;
	public boolean updatePlayerMsg;
	
	public Logger log = Logger.getLogger("Minecraft");
	protected UpdateChecker updateChecker;
	
    public File en = new File("plugins" + File.separator + "SignColors" + File.separator + "languages" + File.separator + "en.yml");
    public File de = new File("plugins" + File.separator + "SignColors" + File.separator + "languages" + File.separator + "de.yml");

    public FileConfiguration cfgen = YamlConfiguration.loadConfiguration(en);
    public FileConfiguration cfgde = YamlConfiguration.loadConfiguration(de);

    @Override
    public void onDisable()
    {
    	saveConfig();
        PluginDescriptionFile plugin = getDescription();
        log.info("Version " + plugin.getVersion() + " by Pablo97 disabled.");
    }

    @Override
    public void onEnable()
    {
    	this.log = this.getLogger();
    	loadConfig();
    	craftSigns();
    	new ColoredSignListener(this);
    	getCommands();
    	languageFileEN();
    	languageFileDE();
    	if (!setupEconomy() )
    	{
            log.info("Some features won't work, because no Vault dependency found!");
            return;
        }
    	loadLanguages();
    	checkUpdates();
    	startMetrics();
        PluginDescriptionFile plugin = getDescription();
        log.info("Version " + plugin.getVersion() + " by Pablo97 enabled.");
    }
    
    private void getCommands()
    {
    	SignColorsCommandExecutor exe = new SignColorsCommandExecutor(this);
    	getCommand("sc").setExecutor(exe);
	}

    private void loadConfig()
    {
    	if (new File("plugins/SignColors/config.yml").exists())
    	{
        	  FileConfiguration cfg = this.getConfig();
    	      cfg.options().copyDefaults(true);
    	    } else
    	    {
    	      saveDefaultConfig();
    	      FileConfiguration cfg = this.getConfig();
    	      cfg.options().copyDefaults(true);
    	    }
    }
    
    public void loadLanguages()
    {
		if(getConfig().getString("language").equalsIgnoreCase("en"))
		{
    		languageEN = true;
    	} else
    	{
    		languageEN = false;
    	}
		if(this.languageEN == true)
		{
			nocmd = cfgen.getString("EN.ENNOCMDACCESS");
			noaction = cfgen.getString("EN.ENNOACTION");
			sciauthor = cfgen.getString("EN.ENSCINFOAUTHOR");
			scicmd = cfgen.getString("EN.ENSCINFOCMD");
			scicmdh = cfgen.getString("EN.ENSCINFOCMDH");
			csch = cfgen.getString("EN.ENCOLORSYMBOLCH");
			cschtma = cfgen.getString("EN.ENCOLORSYMBOLTMA");
			uncmd = cfgen.getString("EN.ENUNKNOWNCMD");
			uncmdh = cfgen.getString("EN.ENUNKNOWNCMDH");
			configre = cfgen.getString("EN.ENCONFREL");
			sc = cfgen.getString("EN.ENSC");
			schelp = cfgen.getString("EN.ENSCHELP");
			scre = cfgen.getString("EN.ENSCRE");
			sccs = cfgen.getString("EN.ENSCCS");
			slone = cfgen.getString("EN.ENSLONE");
			sltwo = cfgen.getString("EN.ENSLTWO");
			sltwob = cfgen.getString("EN.ENSLTWOB");
			slthree = cfgen.getString("EN.ENSLTHREE");
			signmsg = cfgen.getString("EN.ENSIGNMSG");
			signmsgb = cfgen.getString("EN.ENSIGNMSGB");
			notenmoney = cfgen.getString("EN.ENNOTENMONEY");
			notenspace = cfgen.getString("EN.ENNOTENSPACE");
			updatemsg = cfgen.getString("EN.ENUPDATEMSG");
			sclogo = cfgen.getString("EN.TAG");
		} else
		{
			nocmd = cfgde.getString("DE.DENOCMDACCESS");
			noaction = cfgde.getString("DE.DENOACTION");
			sciauthor = cfgde.getString("DE.DESCINFOAUTHOR");
			scicmd = cfgde.getString("DE.DESCINFOCMD");
			scicmdh = cfgde.getString("DE.DESCINFOCMDH");
			csch = cfgde.getString("DE.DECOLORSYMBOLCH");
			cschtma = cfgde.getString("DE.DECOLORSYMBOLTMA");
			uncmd = cfgde.getString("DE.DEUNKNOWNCMD");
			uncmdh = cfgde.getString("DE.DEUNKNOWNCMDH");
			configre = cfgde.getString("DE.DECONFREL");
			sc = cfgde.getString("DE.DESC");
			schelp = cfgde.getString("DE.DESCHELP");
			scre = cfgde.getString("DE.DESCRE");
			sccs = cfgde.getString("DE.DESCCS");
			slone = cfgde.getString("DE.DESLONE");
			sltwo = cfgde.getString("DE.DESLTWO");
			sltwob = cfgde.getString("DE.DESLTWOB");
			slthree = cfgde.getString("DE.DESLTHREE");
			signmsg = cfgde.getString("DE.DESIGNMSG");
			signmsgb = cfgde.getString("DE.DESIGNMSGB");
			notenmoney = cfgde.getString("DE.DENOTENMONEY");
			notenspace = cfgde.getString("DE.DENOTENSPACE");
			updatemsg = cfgde.getString("DE.DEUPDATEMSG");
			sclogo = cfgde.getString("DE.TAG");
		}
	}
    
    public void craftSigns()
    {
    	i = new ItemStack(Material.SIGN, this.getConfig().getInt("signamount"));
    	im = i.getItemMeta();
    	lores.clear();
    	lores.add(ChatColor.GRAY + "Sign with Colors");
		im.setDisplayName(ChatColor.GOLD + "[" + ChatColor.DARK_AQUA + "SignColors" + ChatColor.GOLD + "]");
		im.setLore(lores);
		i.setItemMeta(im);
    	if(getConfig().getBoolean("signcrafting") == true)
    	{
    		Bukkit.resetRecipes();
    	    int ingredia = this.getConfig().getInt("ingredientA");
    	    Material ingredimata = Material.getMaterial(ingredia);
    	    int ingredib = this.getConfig().getInt("ingredientB");
    	    Material ingredimatb = Material.getMaterial(ingredib);
    		ShapelessRecipe sr = new ShapelessRecipe(i);
    		sr.removeIngredient(ingredimata);
    		sr.removeIngredient(ingredimatb);
    		sr.addIngredient(ingredimata);
    		sr.addIngredient(ingredimatb);
    		Bukkit.addRecipe(sr);
    		signcrafting = true;
    	} else
    	{
    		Bukkit.resetRecipes();
    		signcrafting = false;
    	}
    	return;
    }
    
    private boolean setupEconomy()
    {
        if (getServer().getPluginManager().getPlugin("Vault") == null)
        {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null)
        {
            return false;
        }
        eco = rsp.getProvider();
        return eco != null;
    }
    
    public void startMetrics()
    {
    	if(this.getConfig().getBoolean("metrics") == true)
    	{
        	Metrics metrics;
    		try
    		{
    			metrics = new Metrics(this);
    	    	metrics.start();
    		} catch (IOException e)
    		{
    			log.info("Metrics: Failed to submit the stats :-(");
    			e.printStackTrace();
    		}
    	} else
    	{
        	return;
    	}
    }
    
    public void checkUpdates()
    {
    	if(getConfig().getBoolean("updatecheck") == true)
    	{
    		this.updateChecker = new UpdateChecker(this, "http://dev.bukkit.org/server-mods/signcolors/files.rss");
        	if (this.updateChecker.updateNeeded())
        	{
        		this.log.info("A new version is available: " + this.updateChecker.getVersion());
        		this.log.info("Get it from: " + this.updateChecker.getLink());
        		updatePlayerMsg = true;
        		updatelink = this.updateChecker.getLink();
        		updateversion = this.updateChecker.getVersion();
        	}
    	} else
    	{
    		return;
    	}
    }
    
    public void languageFileEN() {
    	if(en.exists())
    	{
    		return;
    	} else
    	{
  		  cfgen.addDefault("EN.TAG", "&6[&3SignColors&6] ");
    	  cfgen.addDefault("EN.ENNOCMDACCESS", "&cYou do not have access to this command!");
      	  cfgen.addDefault("EN.ENNOACTION", "&cYou are not allowed to do this!");
      	  cfgen.addDefault("EN.ENSCINFOAUTHOR", "&6Developed by:");
      	  cfgen.addDefault("EN.ENSCINFOCMD", "&6Commands:");
      	  cfgen.addDefault("EN.ENSCINFOCMDH", "&aUse /sc help to get a list of commands.");
      	  cfgen.addDefault("EN.ENCOLORSYMBOLCH", "&aThe colorsymbol has successfully been set to:");
      	  cfgen.addDefault("EN.ENCOLORSYMBOLTMA", "&cToo many arguments!");
      	  cfgen.addDefault("EN.ENUNKNOWNCMD", "&cUnknown command!");
      	  cfgen.addDefault("EN.ENUNKNOWNCMDH", "&aType /sc help for a list of commands.");
      	  cfgen.addDefault("EN.ENCONFREL", "&aSuccessfully reloaded config.yml.");
      	  cfgen.addDefault("EN.ENSC", "&aShows info about SignColors.");
      	  cfgen.addDefault("EN.ENSCCS", "&aChanges the color symbol.");
      	  cfgen.addDefault("EN.ENSCRE", "&aReloads the config.yml.");
      	  cfgen.addDefault("EN.ENSCHELP", "&aShows a list of commands.");
      	  cfgen.addDefault("EN.ENSLONE", "&4*Click me*");
      	  cfgen.addDefault("EN.ENSLTWO", "&b< ");
      	  cfgen.addDefault("EN.ENSLTWOB", " signs >");
      	  cfgen.addDefault("EN.ENSLTHREE", "&ePrice: ");
      	  cfgen.addDefault("EN.ENSIGNMSG", "&aHere are ");
      	  cfgen.addDefault("EN.ENSIGNMSGB", " &asigns.");
      	  cfgen.addDefault("EN.ENNOTENMONEY", "&cYou don't have enough money to buy signs!");
		  cfgen.addDefault("EN.ENNOTENSPACE", "&cYou don't have enough space for signs!");
		  cfgen.addDefault("EN.ENUPDATEMSG", "&aAn update is available download it here");
      	  
    	  cfgen.options().copyDefaults(true);
    	  try
    	  {
    	    cfgen.save(en);
    	  } catch (IOException e)
    	  {
    	    e.printStackTrace();
    	  }
    	}
    }
    
    public void languageFileDE() {
      if(de.exists())
      {
    	  return;
      } else
      {
		  cfgde.addDefault("DE.TAG", "&6[&3FarbigeSchilder&6] ");
    	  cfgde.addDefault("DE.DENOCMDACCESS", "&cDu hast keinen Zugriff auf diesen Befehl!");
      	  cfgde.addDefault("DE.DENOACTION", "&cDu hast keine Rechte dies zu tun!");
      	  cfgde.addDefault("DE.DESCINFOAUTHOR", "&6Entwickelt von:");
      	  cfgde.addDefault("DE.DESCINFOCMD", "&6Befehle:");
      	  cfgde.addDefault("DE.DESCINFOCMDH", "&aBenutze /sc help fuer eine Liste von Befehlen.");
      	  cfgde.addDefault("DE.DECOLORSYMBOLCH", "&aDas Farbsymbol wurde erfolgreich geaendert zu:");
      	  cfgde.addDefault("DE.DECOLORSYMBOLTMA", "&cZu viele Argumente!");
      	  cfgde.addDefault("DE.DEUNKNOWNCMD", "&cUnbekannter Befehl!");
      	  cfgde.addDefault("DE.DEUNKNOWNCMDH", "&aGebe /sc help fuer eine Liste von Befehlen ein.");
      	  cfgde.addDefault("DE.DECONFREL", "&aDie config.yml wurde erfolgreich neugeladen.");
      	  cfgde.addDefault("DE.DESC", "&aZeigt Infos ueber SignColors.");
      	  cfgde.addDefault("DE.DESCCS", "&aAendert das Farbsymbol.");
      	  cfgde.addDefault("DE.DESCRE", "&aLaedt die config.yml neu.");
      	  cfgde.addDefault("DE.DESCHELP", "&aZeigt eine Liste von Befehlen.");
      	  cfgde.addDefault("DE.DESLONE", "&4*Klick mich*");
      	  cfgde.addDefault("DE.DESLTWO", "&b<");
      	  cfgde.addDefault("DE.DESLTWOB", " Schilder>");
      	  cfgde.addDefault("DE.DESLTHREE", "&ePreis: ");
      	  cfgde.addDefault("DE.DESIGNMSG", "&aHier sind ");
      	  cfgde.addDefault("DE.DESIGNMSGB", " &aSchilder.");
      	  cfgde.addDefault("DE.DENOTENMONEY", "&cDu hast nicht genug Geld für farbige Schilder!");
		  cfgde.addDefault("DE.DENOTENSPACE", "&cDu hast nicht genug Platz im Inventar!");
		  cfgde.addDefault("DE.DEUPDATEMSG", "&aEin Update ist verfügbar downloade es hier");

      	  cfgde.options().copyDefaults(true);
      	  try
      	  {
      	    cfgde.save(de);
      	  } catch (IOException e)
      	  {
      	    e.printStackTrace();
      	  }
      }
  }
    public static String replaceColors(String message)
    {
    	 
    	return ChatColor.translateAlternateColorCodes('&', message);
    	 
    }
}