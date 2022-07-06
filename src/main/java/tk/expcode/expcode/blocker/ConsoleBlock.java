package tk.expcode.expcode.blocker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerCommandEvent;
import tk.expcode.expcode.ExpCode;

public class ConsoleBlock implements Listener {

    private final ExpCode main;

    public ConsoleBlock(ExpCode main) {
        this.main = main;
    }

    @EventHandler
    public void onServerCommand(ServerCommandEvent event) {
        event.setCancelled(main.isConsoleBlocked());
    }
}
