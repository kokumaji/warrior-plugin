/*
 * Copyright (c) 2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information.
 */
package com.dumbdogdiner.warrior

import com.dumbdogdiner.stickyapi.StickyAPI
import listeners.BlockBreakListener
import com.dumbdogdiner.warrior.objects.flags.BlockBreakFlag
import com.dumbdogdiner.warrior.objects.flags.GameFlag
import java.util.concurrent.Executors
import kr.entree.spigradle.annotations.PluginMain
import lombok.Getter
import org.bukkit.Bukkit
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class Warrior : JavaPlugin(), WarriorAPI {

    val pool = Executors.newFixedThreadPool(3)

    @Getter
    var worldFlags = ArrayList<GameFlag<*, *>>()

    override fun onLoad() {
        StickyAPI.setPool(pool)
        worldFlags.add(BlockBreakFlag(false))
    }

    override fun onEnable() {
        Bukkit.getServer().pluginManager.registerEvents(BlockBreakListener(), this)
    }

    override fun getProvider(): Plugin {
        return this
    }

    override fun getWelcomeMessage(): String {
        return "Hewwo~ wowwd owo!"
    }
}
