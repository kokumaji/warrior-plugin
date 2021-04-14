package com.dumbdogdiner.warrior.models.metadata;

import lombok.Getter;

import java.util.Arrays;
import java.util.List;

public class RatingNode {

    public static final List<RatingNode> DEFAULT_RATING = Arrays.asList(
            new RatingNode(1, 0),
            new RatingNode(2, 0),
            new RatingNode(3, 0),
            new RatingNode(4, 0),
            new RatingNode(5, 0)
    );

    @Getter
    private int weight;

    @Getter
    private int count;

    public RatingNode(int weight, int count) {
        this.count = count;
        this.weight = weight;
    }

    protected void increment() {
        this.count++;
    }


    protected void decrement() {
        this.count--;
    }

}
