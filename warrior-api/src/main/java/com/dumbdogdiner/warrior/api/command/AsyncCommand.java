package com.dumbdogdiner.warrior.api.command;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.translation.Constants;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public abstract class AsyncCommand extends Command implements TabCompleter {

    @Getter
    private final HashMap<String, SubCommand> subCommands;

    @Getter
    private final Plugin owner;
    private TabCompleter completer;
    private static ExecutorService pool = Executors.newFixedThreadPool(3);
    @Getter @Setter
    private CommandType type;

    public AsyncCommand(String commmandName, Plugin plugin, CommandType type) {
        super(commmandName);
        this.owner = plugin;

        subCommands = new HashMap<>();
        this.type = type;
    }

    public AsyncCommand(String commmandName, Plugin plugin) {
        super(commmandName);
        this.owner = plugin;

        subCommands = new HashMap<>();
        this.type = CommandType.ANY;
    }


    public AsyncCommand addSubCommand(SubCommand command) {
        if(subCommands.containsKey(command.getAlias()))
            throw new IllegalStateException("Subcommand " + command.getAlias() + " has already been registered!");

        subCommands.put(command.getAlias(), command);
        return this;
    }

    public abstract ExitStatus executeCommand(CommandSender sender, String commandLabel, String[] args);

    public abstract void onPermissionError(CommandSender sender, String label, String[] args);

    public abstract void onError(CommandSender sender, String label, String[] args);

    public abstract void onSyntaxError(CommandSender sender, String label, String[] args);

    @Override
    public final boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if(!this.owner.isEnabled()) return false;
        AsyncCommand cmd = this;

        if(!canExecute(sender)) {
            cmd.onError(sender, commandLabel, args);
            return true;
        } else if(getPermission() != null && !sender.hasPermission(getPermission())) {
            cmd.onPermissionError(sender, commandLabel, args);
            return true;
        }

        CommandTask<Boolean> t = new CommandTask<>(() -> {
            try {
                if(args.length == 0) {
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
                } else {
                    String arg = args[0].toLowerCase();
                    if(getSubCommands().get(arg) != null) {
                        SubCommand subCommand = getSubCommands().get(arg);
                        if(subCommand.getPermission() != null && !sender.hasPermission(subCommand.getPermission())) {
                            cmd.onPermissionError(sender, commandLabel, args);
                            return true;
                        }

                        if(!subCommand.execute(sender, commandLabel, args)) {
                            Player p = (Player) sender;
                            String msg = WarriorAPI.getService().getTranslator().translate(Constants.Lang.ERROR_SUBCMD, new HashMap<>() {
                                {
                                    put("sub_syntax", subCommand.getSyntax());
                                }
                            }, PlayerManager.get(p.getUniqueId()));

                            p.sendMessage(TranslationUtil.prettyMessage(msg));
                            p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, 0.25f, 0.8f);

                            return true;
                        }
                    } else {
                        cmd.onSyntaxError(sender, commandLabel, args);
                        return true;
                    }
                }

            } catch(Throwable ex) {
                throw new CommandException("Unhandled exception executing command '" + commandLabel + "' in plugin " + cmd.owner.getDescription().getFullName(), ex);
            }

            return true;
        });

        pool.execute(t);

        return true;
    }

    public boolean canExecute(CommandSender sender) {
        switch (type) {
            case PLAYER_ONLY:
                return sender instanceof Player;
            case CONSOLE_ONLY:
                return !(sender instanceof Player);
            case ANY:
                return true;
        }

        return false;
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
    public List<String> tabComplete(CommandSender sender, String alias, String[] args)
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
