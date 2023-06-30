package vlad00x.skywars;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import vlad00x.skywars.Files.Configs;
import vlad00x.skywars.Files.Logger;
import vlad00x.skywars.SkyWars.Arena.Arena;

import java.util.ArrayList;

public class Manager {

    private static ArrayList<Arena> arenas = new ArrayList<>();


    public static void load_arenas(){
        arenas.clear();

        FileConfiguration config = Configs.get_config("arena");
        for(World world : Bukkit.getWorlds()){
            if(world.getName().charAt(0) != 'A'){
                continue;
            }

            int id;
            try {
                id = Integer.parseInt(world.getName().substring(1));
            }
            catch (NumberFormatException ex){
                Logger.add_message("ARENA",
                        String.format("Failed to load the arena '%s'", world.getName()));
                continue;
            }

            Arena arena = new Arena(world, id,
                    config.getInt("Arena.arena_size"),
                    config.getInt("Arena.rating_spread")
            );

            arenas.add(arena);
        }
    }

    public static Integer get_rating(Player pl){
        return 0;
    }

    public static void add_player_to_arena(Player pl){
        int rating = get_rating(pl);

        for(Arena arena : arenas){
            if(arena.check_requirements(pl, rating)){
                arena.add_player(pl);
                return;
            }
        }

        Arena new_arena = create_arena();
        new_arena.add_player(pl);
        arenas.add(new_arena);
    }

    public static Arena create_arena(){
        String arena_name = "A" + String.valueOf(arenas.size());
        WorldCreator worldCreator = new WorldCreator(arena_name);
        World world = Bukkit.createWorld(worldCreator);

        FileConfiguration config = Configs.get_config("arena");

        Arena arena = new Arena(world, arenas.size(),
                config.getInt("Arena.arena_size"),
                config.getInt("Arena.rating_spread")
        );

        return arena;
    }

}

// ARENAS: A0  A1  A2  A3 ... A9  A10  A11
