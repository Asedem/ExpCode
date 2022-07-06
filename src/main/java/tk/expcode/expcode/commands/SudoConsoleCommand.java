package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class SudoConsoleCommand implements ExpExecutor {

    private final ExpCode main;

    public SudoConsoleCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (args.length < 1) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cBitte verwende &6" + command.getUsage() + "&c!"));
            return;
        }
        StringBuilder builder = new StringBuilder();
        for (String arg : args) builder.append(arg).append(" ");
        if (main.isConsoleBlocked()) {
            main.setConsoleBlocked(false);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), builder.toString());
            main.setConsoleBlocked(true);
            return;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), builder.toString());
    }
}
