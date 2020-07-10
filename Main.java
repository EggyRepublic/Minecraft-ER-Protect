package com.eggyrepublic.protect;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
	
	@Override
	public void onEnable() {
		getLogger().info("loading protection program, created by EggyRepublic!");
		getServer().getPluginManager().registerEvents(this, this);
		loadConfiguration();
		getLogger().info("successfully loaded ER protect!");
	}
	
	@Override
	public void onDisable() {
		
	}
	
    public void loadConfiguration() {
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
    }
    
    
    public void logToFile(String message) {
    	//generate necessary file structure
		try { 
	    	File dataFolder = getDataFolder();
	        if(!dataFolder.exists())
	        {
	            dataFolder.mkdir();
	        }
	        File saveTo = new File(getDataFolder(), "ER_protect_logs.txt");
	        if(!saveTo.exists()) {
	            try {
	                saveTo.createNewFile();
	            } catch (Exception e) {
	                e.printStackTrace();
	            }
	        }
	        
	        FileWriter fw = new FileWriter(saveTo, true);
	        PrintWriter pw = new PrintWriter(fw);
	        pw.println(message);
	        pw.flush();
	        pw.close();
    	}
        catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    @EventHandler
    public void click(PlayerInteractEvent event) {
    	Player player = event.getPlayer();
    	ItemStack stack1 = player.getInventory().getItemInMainHand();
    	ItemStack stack2 = player.getInventory().getItemInOffHand();
    	if (
    			stack1.getType().equals(Material.LAVA_BUCKET) 
    			|| stack1.getType().equals(Material.TNT)
    			|| stack1.getType().equals(Material.FLINT_AND_STEEL)
    			|| stack1.getType().equals(Material.TNT_MINECART)
    			|| stack1.getType().equals(Material.CREEPER_SPAWN_EGG)
    			|| stack2.getType().equals(Material.LAVA_BUCKET) 
    			|| stack2.getType().equals(Material.TNT)
    			|| stack2.getType().equals(Material.FLINT_AND_STEEL)
    			|| stack2.getType().equals(Material.TNT_MINECART)
    			|| stack2.getType().equals(Material.CREEPER_SPAWN_EGG)) {
    		Location location = player.getLocation();
    		String loc = location.getBlock().toString();
    		String message = player.getDisplayName() + " may have placed " + stack1.getType().name()
    				+ " or " + stack2.getType().name() + " at " + loc;
    		Bukkit.getConsoleSender().sendMessage(message);
    		Bukkit.broadcastMessage(message);
    		logToFile(message);
    	}
    	
    	
    }
}
