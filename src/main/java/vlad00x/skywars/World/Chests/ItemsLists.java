package vlad00x.skywars.World.Chests;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class ItemsLists {

    private static Map<Integer, ArrayList<ItemStack>> lists = new HashMap<>();
    private static Random random = new Random();

    public static void full(){
        // #1
        // #2
        // #3
    }

    private ItemStack create_item(Material material, int q, EnchC[] enchantments){
        ItemStack item = new ItemStack(material, q);

        for(EnchC ench : enchantments){
            item.getItemMeta().addEnchant(ench.ench, ench.level, true);
        }

        return item;
    }

    public static ItemStack get_random_item(int rarity){
        return lists.get(rarity).get(random.nextInt(lists.get(rarity).size()));
    }

    public static ArrayList<ItemStack> get_list(int rarity){
        return lists.get(rarity);
    }
}

class EnchC {  // EnchantmentComplete
    public Enchantment ench;
    public int level;

    public EnchC(Enchantment ench, int level) {
        this.ench = ench;
        this.level = level;
    }
}
