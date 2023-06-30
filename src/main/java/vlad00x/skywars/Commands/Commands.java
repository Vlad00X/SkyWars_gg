package vlad00x.skywars.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vlad00x.skywars.Manager;
import vlad00x.skywars.Plugin;

public class Commands implements CommandExecutor {

    /*
    public Commands(){
        Plugin.get_instance().getCommand("start_game").setExecutor(this);
    }*/

    @Override
    public boolean onCommand(@NotNull CommandSender commandSender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
        if(commandSender instanceof Player){
            Manager.add_player_to_arena((Player) commandSender);
        }

        return true;
    }
}
