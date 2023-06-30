package vlad00x.skywars.World.Arena;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.title.Title;
import org.bukkit.GameMode;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

import java.time.Duration;

public class ArenaEvents implements Listener {

    public ArenaEvents(){
        //
    }

    @EventHandler
    public void PlayerDeathEvent(PlayerDeathEvent event){
        Player pl = event.getEntity();
        pl.setGameMode(GameMode.SPECTATOR);

        Entity killer = pl.getKiller();
        String killer_name;
        if(killer.getType() == EntityType.PLAYER) {
            killer_name = killer.getName();
        }else{
            killer_name = killer.getType().toString();
        }

        pl.showTitle(get_death_title(killer_name));

        World world = pl.getWorld();
        for (ItemStack item : pl.getInventory().getContents()) {
            if (item != null) {
                ItemStack droppedItem = new ItemStack(item.getType());
                droppedItem.setItemMeta(item.getItemMeta());
                droppedItem.setAmount(item.getAmount());
                droppedItem.setDurability(item.getDurability());
                droppedItem.addEnchantments(item.getEnchantments());

                world.dropItemNaturally(pl.getLocation(), droppedItem);
            }
        }
        pl.getInventory().clear();

        event.setCancelled(true);
    }

    private Title get_death_title(String killer){
        return Title.title(
                Component.text("U are dead"),
                Component.text("Killed by " + killer),
                Title.Times.times(Duration.ofMillis(1000), Duration.ofMillis(4000), Duration.ofMillis(1000))
        );
    }
}
