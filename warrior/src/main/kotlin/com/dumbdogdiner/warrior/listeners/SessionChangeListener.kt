package com.dumbdogdiner.warrior.listeners

import com.dumbdogdiner.warrior.WithWarriorPlugin
import com.dumbdogdiner.warrior.api.events.SessionChangeEvent
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class SessionChangeListener : WithWarriorPlugin, Listener {

    @EventHandler
    fun onSessionChange(e: SessionChangeEvent) {
        // TODO: Add debug logging or other session change stuff
    }

}