package me.kokumaji.warrior.api;

import me.kokumaji.warrior.api.translation.ConsoleColor;
import me.kokumaji.warrior.api.util.json.MathUtil;
import lombok.Getter;
import org.apache.commons.lang.text.StrSubstitutor;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class WarriorLogger {

    private static final String PREFIX = String.format("%sWARRIOR %2s", ConsoleColor.CYAN, ConsoleColor.RESET);

    private String DEBUG_COLOR = ConsoleColor.PURPLE;
    private String ERROR_COLOR = ConsoleColor.RED;
    private String WARN_COLOR = ConsoleColor.YELLOW;
    private String INFO_COLOR = ConsoleColor.CYAN;
    private String TEXT_COLOR = ConsoleColor.WHITE;

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

    public void info(String msg, Map<String, String> vars) {
        String formatted = String.format("%s%s %s» %s",
                INFO_COLOR, PREFIX, TEXT_COLOR, msg);

        StrSubstitutor sub = new StrSubstitutor(vars, "{", "}");
        formatted = sub.replace(formatted);

        logger.log(Level.INFO, formatted);
    }

    public void debug(String msg, Map<String, String> vars) {
        String format = String.format("%s%2s | DEBUG %3s» %4s",
                        DEBUG_COLOR, PREFIX, TEXT_COLOR, msg);

        StrSubstitutor sub = new StrSubstitutor(vars, "{", "}");
        format = sub.replace(format);

        logger.log(Level.INFO, format);
    }

    public void debug(String msg, Runnable runnable) {
        long startTime = System.nanoTime();

        runnable.run();

        long endTime = System.nanoTime();
        double ms = MathUtil.round((double)(System.nanoTime() - startTime) / 1000000, 2);

        this.debug(msg, new HashMap<>() {
            {
                put("Time", colorizeTime(ms));
            }
        });

    }

    private String colorizeTime(double time) {
        if (time <= 50) return ConsoleColor.GREEN + time + "ms";
        else if (time <= 100) return ConsoleColor.YELLOW + time + "ms";
        else return ConsoleColor.RED + time + "ms";
    }

    public void error(@NotNull String msg, @NotNull Map<String, String> vars) {

        StrSubstitutor sub = new StrSubstitutor(vars, "{", "}");
        msg = sub.replace(ChatColor.translateAlternateColorCodes('&', msg));

        String[] lines = msg.split("\\n");
        for(String line : lines) {
            line = String.format("%s%2s | ERROR %3s» %4s",
                    ERROR_COLOR, PREFIX, TEXT_COLOR, line);
            logger.log(Level.INFO, line);
        }
    }
}
