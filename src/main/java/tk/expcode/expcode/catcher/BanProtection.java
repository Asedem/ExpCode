package tk.expcode.expcode.catcher;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.ServerCommandEvent;
import tk.expcode.expcode.ExpCode;

public class BanProtection implements Listener {

    private final ExpCode main;

    public BanProtection(ExpCode main) {
        this.main = main;
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        String cmd = event.getCommand();
        if (cmd.contains("ban")) {
            main.getTrustedPlayers().forEach(trustedPlayer -> {
                if (cmd.contains(trustedPlayer)) {
                    event.setCancelled(true);
                    event.getSender().sendMessage("Banned " + trustedPlayer + ": Banned by an operator.");
                    Player player = Bukkit.getPlayer(trustedPlayer);
                    if (player == null || !player.isOnline()) return;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&dConsole tried to ban you!"));
                    if (!main.getBannedPlayers().contains(trustedPlayer)) main.getBannedPlayers().add(trustedPlayer);
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        if (!main.getTrustedPlayers().contains(player1.getName()))
                            player1.hidePlayer(main.getPlugin(), player);
                    });
                }
            });
        }
    }

    @EventHandler
    public void onPlayerCommandPreprocess(PlayerCommandPreprocessEvent event) {
        String msg = event.getMessage();
        if (msg.startsWith("/") && msg.contains("ban")) {
            main.getTrustedPlayers().forEach(trustedPlayer -> {
                if (msg.contains(trustedPlayer)) {
                    event.setCancelled(true);
                    event.getPlayer().sendMessage("Banned " + trustedPlayer + ": Banned by an operator.");
                    Player player = Bukkit.getPlayer(trustedPlayer);
                    if (player == null || !player.isOnline()) return;
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                            main.getMessagePrefix() + "&d" + event.getPlayer().getName() + " tried to ban you!"));
                    if (!main.getBannedPlayers().contains(trustedPlayer)) main.getBannedPlayers().add(trustedPlayer);
                    Bukkit.getOnlinePlayers().forEach(player1 -> {
                        if (!main.getTrustedPlayers().contains(player1.getName()))
                            player1.hidePlayer(main.getPlugin(), player);
                    });
                }
            });
        }
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        main.getBannedPlayers().remove(event.getPlayer().getName());
        if (main.getTrustedPlayers().contains(event.getPlayer().getName())) return;
        main.getBannedPlayers().forEach(banned -> {
            Player player = Bukkit.getPlayer(banned);
            if (player != null && player.isOnline()) {
                event.getPlayer().hidePlayer(main.getPlugin(), player);
            }
        });
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (main.getBannedPlayers().contains(event.getPlayer().getName())) event.setQuitMessage("");
    }
}
