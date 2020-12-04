package ru.dseymo.youtubeStream;

import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main main;
	
	public void onEnable() {
		
		main = this;
		
		this.getLogger().info("Started!");
		
	}
	
}
