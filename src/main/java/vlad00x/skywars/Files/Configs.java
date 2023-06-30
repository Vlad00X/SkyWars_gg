package vlad00x.skywars.Files;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import vlad00x.skywars.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Configs {
    private static Map<String, FileConfiguration> configs = new HashMap<>();  // All configs


    public static void create_config(String name){
        File file = new File(Plugin.get_instance().getDataFolder(), name + ".yml");
        if (! file.exists()){
            file.getParentFile().mkdirs();
            Plugin.get_instance().saveResource(name + ".yml", false);
        }

        FileConfiguration config = new YamlConfiguration();
        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException e) {
            e.printStackTrace();
        }

        configs.put(name, config);
    }

    public static FileConfiguration get_config(String name){
        FileConfiguration config = configs.get(name);

        if(config == null){
            return Plugin.get_instance().getConfig();  // Return default config
        }

        return config;
    }
}
