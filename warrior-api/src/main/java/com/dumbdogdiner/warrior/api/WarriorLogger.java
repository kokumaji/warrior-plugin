package com.dumbdogdiner.warrior.api;

import com.dumbdogdiner.warrior.Warrior;
import com.dumbdogdiner.warrior.api.translation.ConsoleColor;
import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.logging.Level;
import java.util.logging.Logger;

public class WarriorLogger {

    private static final String PREFIX = String.format("%sWARRIOR %2s", ConsoleColor.CYAN, ConsoleColor.RESET);

    private String DEBUG_COLOR = ConsoleColor.PURPLE;
    private String ERROR_COLOR = ConsoleColor.RED;
    private String WARN_COLOR = ConsoleColor.YELLOW;
    private String INFO_COLOR = ConsoleColor.CYAN;
    private ChatColor TEXT_COLOR = ChatColor.GRAY;

    @Getter
    private final WarriorAPI owner;

    private Logger logger;

    public WarriorLogger(WarriorAPI warrior) {
        this.logger = Bukkit.getLogger();
        this.owner = warrior;
    }

    public void debug(String msg) {
        String format = String.format("%s%2s | DEBUG %3s» %4s",
                        DEBUG_COLOR, PREFIX, TEXT_COLOR, msg);

        logger.log(Level.INFO, format);
    }

    public void error(String msg) {
        String format = String.format("%s%2s | ERROR %3s» %4s",
                ERROR_COLOR, PREFIX, TEXT_COLOR, msg);

        logger.log(Level.INFO, format);
    }

    public void error(Exception exception) {
        String format = String.format("%s%2s | ERROR %3s» GOT EXCEPTION %4s",
                ERROR_COLOR, PREFIX, TEXT_COLOR, exception.getMessage());

        logger.log(Level.INFO, format);
        exception.printStackTrace();
    }


    public void warn(String msg) {
        String format = String.format("%s%s | WARNING %s» %s",
                WARN_COLOR, PREFIX, TEXT_COLOR, msg);

        logger.log(Level.WARNING, format);
    }

    public void info(String msg) {
        String formatted = String.format("%s%s %s» %s",
                INFO_COLOR, PREFIX, TEXT_COLOR, msg);

        logger.log(Level.INFO, formatted);
    }

}
