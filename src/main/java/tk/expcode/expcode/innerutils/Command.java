package tk.expcode.expcode.innerutils;

import org.bukkit.entity.Player;
import tk.expcode.expcode.ExpCode;

import java.util.Comparator;

public class Command implements Comparator<Command> {

    private final ExpCode main;
    private final String command;
    private String usage;
    private ExpExecutor executor;

    public Command(String command, ExpCode main) {
        this.main = main;
        this.command = command;
    }

    public Command setExecutor(ExpExecutor executor) {
        this.executor = executor;
        return this;
    }

    public void setUsage(String usage) {
        this.usage = usage;
        this.main.getCommands().add(this);
    }

    public void run(Player player, String[] args) {
        this.executor.onCommand(player, this, args);
    }

    public String getCommand() {
        return command;
    }

    public String getUsage() {
        return usage;
    }

    @Override
    public int compare(Command o1, Command o2) {
        return o1.getCommand().compareTo(o2.getCommand());
    }
}
