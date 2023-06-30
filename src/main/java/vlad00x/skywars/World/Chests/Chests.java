package vlad00x.skywars.World.Chests;

import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Chests {

    private ArrayList<GameChest> chests = new ArrayList<>();
    private World world;

    public Chests(World world, FileConfiguration config){
        this.world = world;

        List<Map<?, ?>> chests_data = config.getMapList("Chests");

        for(Map<?, ?> chest : chests_data){
            GameChest gameChest = new GameChest(
                    world,
                    (int) chest.get("x"),
                    (int) chest.get("y"),
                    (int) chest.get("z"),
                    (int) chest.get("rare"),
                    (int) chest.get("capacity"));

            chests.add(gameChest);
        }
    }

    public void fill_chests_randomly(){
        for(GameChest ch : chests){
            ch.fill_randomly();
        }
    }

}
