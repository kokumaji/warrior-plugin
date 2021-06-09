package me.kokumaji.warrior.api.util.json;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.List;
import java.util.Random;

public class MathUtil {

    public static final Random random = new Random();

    public static int rInt(int max) {
        return random.nextInt(max);
    }

    public static int rInt(int min, int max) {
        return min + rInt(1 + max - min);
    }

    public static double rDouble(double max) {
        return max * random.nextDouble();
    }

    public static double rDouble(double min, double max) {
        return min + (max - min) * random.nextDouble();
    }

    public static double offset(Location pointA, Location pointB) {
        return offset(pointA.toVector(), pointB.toVector());
    }

    public static double offset(Vector pointA, Vector pointB) {
        return pointA.clone().subtract(pointB).length();
    }

    public static double offset2d(Location pointA, Location pointB) {
        return offset2d(pointA.toVector(), pointB.toVector());
    }

    public static double offset2d(Vector pointA, Vector pointB) {
        pointA.setY(0);
        pointB.setY(0);
        return offset(pointA, pointB);
    }

    public static <T> T randomElement(T[] array) {
        if(array.length < 1) return null;
        return array[rInt(array.length)];
    }

    public static <T> T randomElement(List<T> list) {
        if(list.size() < 1) return null;
        return list.get(rInt(list.size()));
    }

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isOdd(int number) {
        return !isEven(number);
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
