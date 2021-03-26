package com.dumbdogdiner.warrior.api.translation.conditional;

import com.dumbdogdiner.warrior.api.user.WarriorUser;
import lombok.Getter;
import org.jetbrains.annotations.ApiStatus;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@ApiStatus.Experimental
public class Condition {

    @Getter
    private String raw;

    @Getter
    private String message;
    private String argument;
    private String condition;

    private static final Pattern conditionPattern = Pattern.compile("^((?<condition>send if (?<argument>.*)):)?(?<message>.*)");
    private final List<String> numberOperants = Arrays.asList("==", "equals", ">", "<", ">=", "<=", "!=");

    public Condition(String str) {
        this.raw = str;

        Matcher m = conditionPattern.matcher(str);
        if(m.find()) {
            this.condition = m.group("condition");
            this.argument = m.group("argument");
            this.message = m.group("message");
        }
    }

    public boolean test(WarriorUser user) {
        if(condition == null) return true;

        String[] split = argument.split(" ");
        if(split.length != 3) return false;

        String strategy = split[1].replace(" ", "");
        if(numberOperants.contains(strategy)) {
            Double first = doubleFromString(split[0], user);
            Double second = Double.parseDouble(split[2]);

            switch(strategy) {
                case "==":
                case "equals":
                    return first.equals(second);
                case ">":
                    return first > second;
                case "<":
                    return first < second;
                case ">=":
                    return first >= second;
                case "<=":
                    return first <= second;
                case "!=":
                    return !first.equals(second);
            }
        }

        return false;

    }

    private Double doubleFromString(String s, WarriorUser user) {
        switch(s.toLowerCase()) {
            default:
                return 0.0;
            case "kills":
                return (double) user.getKills();
            case "deaths":
                return (double) user.getDeaths();
        }
    }

}
