package tk.expcode.expcode.commands;

import net.md_5.bungee.api.chat.ClickEvent;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;
import tk.expcode.expcode.builders.TextComponentBuilder;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.ExpExecutor;

public class HelpCommand implements ExpExecutor {

    private final ExpCode main;

    public HelpCommand(ExpCode main) {
        this.main = main;
    }

    @Override
    public void onCommand(Player player, Command command, String[] args) {
        main.getCommands().forEach(command1 -> new TextComponentBuilder(ChatColor
                .translateAlternateColorCodes('&',
                        "&c" + command1.getCommand() + " &e" + main.getCommandPrefix() + ' ' + command1.getUsage()))
                .setClickEvent(ClickEvent.Action.SUGGEST_COMMAND,
                        main.getCommandPrefix() + ' ' + command1.getCommand())
                .showToPlayer(player));
    }
}
