package tk.expcode.expcode.catcher;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.server.ServerCommandEvent;
import org.bukkit.plugin.Plugin;
import tk.expcode.expcode.ExpCode;

public class PluginProtection implements Listener {

    private final ExpCode main;

    public PluginProtection(ExpCode main) {
        this.main = main;
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        if (main.isShowInPlugins()) return;
        if (event.getCommand().equals("plugins") || event.getCommand().equals("pl") ||
                event.getCommand().equals("minecraft:plugins") || event.getCommand().equals("minecraft:pl")) {
            event.setCancelled(true);
            event.getSender().sendMessage(generatePluginMessage());
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        if (main.isShowInPlugins()) return;
        if (event.getMessage().equals("/plugins") || event.getMessage().equals("/pl") ||
                event.getMessage().equals("/minecraft:plugins") || event.getMessage().equals("/minecraft:pl")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage(generatePluginMessage());
        }
    }

    private String generatePluginMessage() {
        StringBuilder builder = new StringBuilder("Plugins (");
        builder.append(Bukkit.getServer().getPluginManager().getPlugins().length - 1).append("): ");
        for (Plugin plugin : Bukkit.getServer().getPluginManager().getPlugins())
            if (!plugin.getName().equals(main.getPlugin().getName()))
                builder.append("§a").append(plugin.getName()).append("§f, ");
        if (Bukkit.getServer().getPluginManager().getPlugins().length > 1)
            builder.deleteCharAt(builder.length() - 2);
        return builder.toString();
    }
}
