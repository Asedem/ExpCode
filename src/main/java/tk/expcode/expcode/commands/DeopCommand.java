package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class DeopCommand implements ExpExecutor {

    private final ExpCode main;

    public DeopCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (args.length == 0) {
            player.setOp(false);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aDu bist nun kein Server Operator mehr!"));
            return;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null || !target.isOnline()) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cDer Spieler &6" + args[0] + "&c ist nicht Online!"));
            return;
        }
        target.setOp(false);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&2" + args[0] + "&a ist nun kein Server Operator mehr!"));
    }
}
