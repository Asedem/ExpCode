package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class FakeOpCommand implements ExpExecutor {

    private final ExpCode main;

    public FakeOpCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cBitte verwende &6" + command.getUsage() + "&c!"));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cDer Spieler &6" + args[0] + "&c ist nicht Online!"));
            return;
        }
        target.sendMessage(ChatColor.translateAlternateColorCodes('&',
                "&7&o[Server: Opped " + target.getName() + "]"));
    }
}
