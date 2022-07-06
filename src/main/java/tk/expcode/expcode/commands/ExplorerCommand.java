package tk.expcode.expcode.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

import java.io.File;

public class ExplorerCommand implements ExpExecutor {

    private final ExpCode main;

    public ExplorerCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (args.length == 1 && args[0].equals("switch")) {
            main.setLinux(!main.isLinux());
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aLinux is now " + main.isLinux()));
            return;
        }
        File folder = main.getPlugin().getDataFolder().getParentFile();
        main.getExplorerFile().put(player.getName(), folder);
        if (folder.isDirectory()) {
            main.getFileInventory().openFolder(player, folder, 1);
        } else player.sendMessage("You opened a File!");
    }
}
