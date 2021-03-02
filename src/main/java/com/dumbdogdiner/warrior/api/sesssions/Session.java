package com.dumbdogdiner.warrior.api.sesssions;

import com.dumbdogdiner.stickyapi.common.cache.Cacheable;
import lombok.Getter;
import org.bukkit.entity.Player;

import java.util.UUID;

public abstract class Session {

    @Getter
    private final UUID userId;

    @Getter
    private final long timestamp;

    public Session(UUID sessionOwner) {
        this.userId = sessionOwner;
        this.timestamp = System.currentTimeMillis();
    }

    public abstract SessionType getType();

    public abstract void setupInventory(Player player);

}
