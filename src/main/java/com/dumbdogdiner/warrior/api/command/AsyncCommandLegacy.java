package com.dumbdogdiner.warrior.api.command;

import lombok.Getter;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncCommandLegacy extends Command implements TabCompleter {

    @Getter
    private final Plugin owner;
    private TabCompleter completer;
    private static ExecutorService pool = Executors.newFixedThreadPool(3);

    public AsyncCommandLegacy(String commmandName, Plugin plugin) {
        super(commmandName);
        this.owner = plugin;

    }

    public abstract ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args);

    public abstract void onPermissionError(CommandSender sender, String label, String[] args);

    public abstract void onError(CommandSender sender, String label, String[] args);

    public abstract void onSyntaxError(CommandSender sender, String label, String[] args);

    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!this.owner.isEnabled()) return false;

        AsyncCommandLegacy cmd = this;
        CommandTask<Boolean> t = new CommandTask<>(() -> {
            try {
                switch (cmd.executeCommand(sender, commandLabel, args)) {
                    default:
                        cmd.onError(sender, commandLabel, args);
                        break;
                    case EXECUTE_SUCCESS:
                        break;
                    case ERROR_PERMISSION:
                        cmd.onPermissionError(sender, commandLabel, args);
                        break;
                    case ERROR_SYNTAX:
                        cmd.onSyntaxError(sender, commandLabel, args);
                        break;
                }

            } catch(Throwable ex) {
                throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + cmd.owner.getDescription().getFullName(), ex);
            }

            return true;
        });

        pool.execute(t);

        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        return null;
    }


    /**
     * Sets the {@link TabCompleter} to run when tab-completing this command.
     * <p>
     * If no TabCompleter is specified, and the command's executor implements
     * TabCompleter, then the executor will be used for tab completion.
     *
     * @param completer New tab completer
     */
    public void setTabCompleter(TabCompleter completer) {
        this.completer = completer;
    }

    /**
     * Gets the {@link TabCompleter} associated with this command.
     *
     * @return TabCompleter object linked to this command
     */
    public TabCompleter getTabCompleter() {
        return this.completer;
    }

    @Override
    public java.util.List<String> tabComplete(CommandSender sender, String alias, String[] args)
            throws CommandException, IllegalArgumentException {
        if (sender == null || alias == null || args == null)
            throw new NullPointerException("arguments to tabComplete cannot be null");

        List<String> completions = null;
        try {
            if (completer != null)
                completions = completer.onTabComplete(sender, this, alias, args);
        } catch (Throwable ex) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (String arg : args)
                message.append(arg).append(' ');

            message.deleteCharAt(message.length() - 1).append("' in plugin ")
                    .append(owner.getDescription().getFullName());
            throw new CommandException(message.toString(), ex);
        }

        if (completions == null)
            return super.tabComplete(sender, alias, args);

        return completions;
    }

}
