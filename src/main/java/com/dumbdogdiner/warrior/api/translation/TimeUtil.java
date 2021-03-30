package com.dumbdogdiner.warrior.api.translation;

import com.dumbdogdiner.warrior.api.translation.enums.DateFormat;
import com.dumbdogdiner.warrior.api.translation.enums.LanguageCode;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

public class TimeUtil {

    public static String formatDate(long timestamp) {
        return formatDate(DateFormat.EN_US, timestamp);
    }

    public static String formatDate(LanguageCode format, long timestamp) {
        return formatDate(DateFormat.valueOf(format.name()), timestamp);
    }

    public static String formatDate(DateFormat format, long timestamp) {
        Date date = new Date(timestamp);
        java.text.DateFormat formatter = new SimpleDateFormat(format.getFormatString());
        return formatter.format(date);
    }

    public static String formatDuration(long timeSpan) {
        Duration d = Duration.ofMillis(timeSpan);
        return  d.toString()
                .substring(2)
                .replaceAll("(\\d[HMS])(?!$)", "$1 ")
                .replaceAll("\\.\\d+", "")
                .toLowerCase();
    }

    public static String timeAgo(long lastJoin) {
        return timeAgo(lastJoin, false);
    }

    public static String timeAgo(long lastJoin, boolean approximate) {
        long deltaTime = System.currentTimeMillis() - lastJoin;
        Duration durationSeconds = Duration.ofSeconds(deltaTime / 1000);

        String timeString = formatDuration(deltaTime);
        if(approximate) {
            if(durationSeconds.toDays() > 14) {
                timeString = "a while";
            } else if(durationSeconds.toDays() > 3) {
                timeString = "a few days";
            } else return "recently";

        } else {
            if(durationSeconds.toDays() > 30) {
                timeString = "> 1 month";
            } else if(durationSeconds.toDays() > 14) {
                timeString = String.format("%d weeks", durationSeconds.toDays() / 7);
            } else if(durationSeconds.toDays() > 1) {
                timeString = String.format("%d days", durationSeconds.toDays());
            }

        }

        return String.format("%s ago", timeString);
    }

    public static long now() {
        return System.currentTimeMillis();
    }

}
