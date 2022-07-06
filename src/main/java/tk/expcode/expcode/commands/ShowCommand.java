package tk.expcode.expcode.commands;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class ShowCommand implements ExpExecutor {

    private final ExpCode main;

    public ShowCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        if (main.isShowInPlugins()) {
            main.setShowInPlugins(false);
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&aNicht in Plugins angezeigt!"));
            return;
        }
        main.setShowInPlugins(true);
        player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                main.getMessagePrefix() + "&aIn Plugins angezeigt!"));
    }
}
