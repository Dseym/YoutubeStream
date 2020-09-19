package streamInfoYoutube;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

	public static Main plugin;
	
	public void onEnable() {
		
		plugin = this;
		
		File config = new File(this.getDataFolder() + File.separator + "config.yml");
		
		if(!config.exists()) {
			
			try {
				
				getDataFolder().mkdirs();
				config.createNewFile();
				
			} catch (IOException e) {}
			
		}
		FileConfiguration configFile = (FileConfiguration)YamlConfiguration.loadConfiguration(config);
		if(configFile.contains("api"))
			YouTube.api = configFile.getString("api");
		else
			getLogger().warning("ERROR: Укажите API в конфиге");
		
		this.getLogger().info("Started!");
		
	}
	
}
