package tk.expcode.expcode;

import org.bukkit.plugin.java.JavaPlugin;
import tk.expcode.expcode.innerutils.Command;

import java.util.ArrayList;
import java.util.List;

public class ExpCode {

    private final JavaPlugin plugin;
    private final List<Command> commands = new ArrayList<>();

    public ExpCode(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public static void initialize() {

    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public List<Command> getCommands() {
        return commands;
    }
}
