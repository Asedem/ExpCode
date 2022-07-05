package tk.expcode.expcode.innerutils;

import org.bukkit.entity.Player;

public interface ExpExecutor {

    void onCommand(Player player, Command command, String[] args);
}
