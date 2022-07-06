package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class SudoCommand implements ExpExecutor {

    private final ExpCode main;

    public SudoCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (args.length < 2) {
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
        StringBuilder builder = new StringBuilder();
        for (int i = 1; i < args.length; i++) builder.append(args[i]).append(" ");
        target.chat(builder.toString());
    }
}
