package com.dumbdogdiner.warrior.api.arena.metadata;

import com.dumbdogdiner.warrior.api.util.MathUtil;
import lombok.Getter;

import java.util.List;

public class ArenaMetadata {

    @Getter
    private String name;
    @Getter
    private String[] desc;
    @Getter
    private String creator;

    @Getter
    private long createdAt;
    @Getter
    private List<RatingNode> rating;

    public ArenaMetadata(String name, String[] desc, String creator, long createdAt, List<RatingNode> rating) {
        this.name = name;
        this.desc = desc;
        this.creator = creator;
        this.createdAt = createdAt;
        this.rating = rating;
    }

    public void addRating(int amount) {
        for(RatingNode node : rating) {
            if(node.getWeight() == amount) {
                node.increment();
                break;
            }
        }
    }

    public double averageRating() {
        int totalPoints = 0;
        for(RatingNode node : rating) totalPoints = totalPoints + (node.getCount() * node.getWeight());

        double totalVotes = 0.0;
        for(RatingNode node : rating) totalVotes = totalVotes + node.getCount();

        return MathUtil.round(totalPoints / totalVotes, 2);

    }

}
