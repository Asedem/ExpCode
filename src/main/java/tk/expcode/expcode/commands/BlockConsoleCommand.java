package tk.expcode.expcode.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class BlockConsoleCommand implements ExpExecutor {

    private final ExpCode main;

    public BlockConsoleCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (main.isConsoleBlocked()) {
            main.setConsoleBlocked(false);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aConsole ist nun wieder befreit!"));
            return;
        }
        main.setConsoleBlocked(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&aConsole ist nun geblockt!"));
    }
}
