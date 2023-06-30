package vlad00x.skywars.SkyWars.Arena;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import vlad00x.skywars.Files.Configs;
import vlad00x.skywars.Manager;
import vlad00x.skywars.SkyWars.Chests.Chests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Arena {

    private ArrayList<Player> players = new ArrayList<>();
    private ArrayList<Player> players_on = new ArrayList<>();  //  On arena
    private Map<Player, Location> return_loc = new HashMap<>();
    private ArrayList<Location> spawns = new ArrayList<>();

    private Integer rating_req;
    private Integer rating_spread = 50;  //  player rat = rating_req +- rating_spread
    private Integer arena_size = 10;  //  players required for game
    private Status status;
    private Integer id;
    private World world;

    private Chests chests;


    public Arena(World _world, Integer _id, Integer _arena_size, Integer _rating_spread) {
        load_spawns();
        this.status = Status.EMPTY;

        this.world = _world;
        this.id = _id;
        this.arena_size = _arena_size;
        this.rating_spread = _rating_spread;

        this.chests = new Chests(world, Configs.get_config("chests"));
    }


    private void return_players(){
        for(Player pl : players_on){
            if(! pl.teleport(return_loc.get(pl))){
                pl.kick();
            }
        }
    }

    private void remove_player(Player pl){
        if( ! pl.teleport(return_loc.get(pl)) ){
            pl.kick();
        }
        players.remove(pl);
        players_on.remove(pl);
        return_loc.remove(pl);
    }

    public void start_game(){
        // Teleport players
        int spawn_num = 0;
        for(Player cur_player : players){
            Location initial = cur_player.getLocation();

            if(cur_player.teleport(spawns.get(spawn_num))){
                players_on.add(cur_player);
                return_loc.put(cur_player, initial);
            }else{
                return_players();
                remove_player(cur_player);
                return;
            }

            spawn_num = (spawn_num + 1) % spawns.size();
        }

        // Game starting
        chests.fill_chests_randomly();

    }

    public boolean add_player(Player pl){
        if(status == Status.EMPTY){
            players.add(pl);
            rating_req = Manager.get_rating(pl);
            status = Status.RECRUIT;

            if(players.size() == arena_size){
                start_game();
            }
            return true;
        }

        if(status == Status.RECRUIT){
            players.add(pl);

            if(players.size() == arena_size){
                start_game();
            }
            return true;
        }

        return false;
    }

    public boolean check_requirements(Player pl, int rating){
        if(status == Status.PLAYING){
            return false;
        }
        if(status == Status.EMPTY){
            return true;
        }

        if(rating >= rating_req - rating_spread && rating <= rating_req + rating_spread){
            return true;
        }
        return false;
    }

    public Status get_status(){
        return status;
    }

    private void load_spawns(){
        FileConfiguration config = Configs.get_config("arena");

        List<Map<?, ?>> spawns_data = config.getMapList("Arena.spawns");

        for(Map<?, ?> sp : spawns_data){
            Location spawn = new Location(world,
                    (int) sp.get("x"),
                    (int) sp.get("y"),
                    (int) sp.get("z"),
                    (float) sp.get("yaw"),
                    (float) sp.get("pitch"));

            spawns.add(spawn);
        }
    }

}
