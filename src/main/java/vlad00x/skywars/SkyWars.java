package vlad00x.skywars;

import org.bukkit.plugin.java.JavaPlugin;
import vlad00x.skywars.Commands.Commands;
import vlad00x.skywars.Files.Configs;
import vlad00x.skywars.World.Chests.ItemsLists;

public final class SkyWars extends JavaPlugin {

    private static SkyWars instance;

    public static SkyWars get_instance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        //Commands
        this.getCommand("startgame").setExecutor(new Commands());

        //Arenas
        Manager.load_arenas();

        //Configs
        saveDefaultConfig();
        Configs.create_config("arena");
        Configs.create_config("chests");

        //Items Lists
        ItemsLists.full();

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
