package tk.expcode.expcode.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class FinishCommand implements ExpExecutor {

    private final ExpCode main;

    public FinishCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&aServer bereinigt: " + main.getPluginFile().delete()));
        Bukkit.getServer().reload();
    }
}
