package vlad00x.skywars.SkyWars.Chests;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import vlad00x.skywars.Files.Logger;

import java.util.Random;

public class GameChest {

    private Location loc;
    private int rarity;
    private int capacity;
    private Chest chest;
    private static Random random = new Random();

    public GameChest(World world, int x, int y, int z, int _rarity, int _capacity){
        this.loc = new Location(world, x, y, z);
        this.rarity = _rarity;
        this.capacity = _capacity;

        if(! (world.getBlockAt(loc) instanceof Chest)){
            this.chest = null;
            Logger.add_message("CHEST",
                    String.format("Failed to get chest (%d, %d, %d) in the %s", x, y, z, world.getName()));
            return;
        }
        this.chest = (Chest) world.getBlockAt(loc);
    }

    public void add_item(ItemStack item){
        Inventory inv = chest.getInventory();

        int ind = random.nextInt(inv.getSize());
        while(inv.getItem(ind) == null){
            ind = (ind + 1) % inv.getSize();
        }

        inv.setItem(ind, item);
        chest.update();
    }

    public void fill_randomly(){
        chest.getInventory().clear();

        for(int i = 0; i < capacity; i++){
            add_item(ItemsLists.get_random_item(rarity));
        }
    }
}
