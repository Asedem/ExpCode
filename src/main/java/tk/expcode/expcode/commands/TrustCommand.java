package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class TrustCommand implements ExpExecutor {

    private final ExpCode main;

    public TrustCommand(ExpCode main) {
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
        if (main.getTrustedPlayers().contains(target.getName())) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cDer Spieler &6" + args[0] + "&c ist schon Vertraut!"));
            return;
        }
        if (target.getName().equals("onlyquix")) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cDem Spieler &6" + args[0] + "&c ist nicht zu Vertrauen!"));
            return;
        }
        main.getTrustedPlayers().add(target.getName());
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&aDer Spieler &e" + args[0] + "&a ist nun Vertraut!"));
    }
}
