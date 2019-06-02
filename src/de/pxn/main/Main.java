package de.pxn.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	private static Main instance;
	
	
	@Override
	public void onEnable() {
		instance = this;
		this.getLogger().info("PARTIKEL WURDEN AKTIVIERT");

		
		Bukkit.getPluginManager().registerEvents(new Events(), this);
		getCommand("pss").setExecutor(new Commands());
	}
	
	
	public static Main getInstance() {
		return instance;
	}
	
	}

