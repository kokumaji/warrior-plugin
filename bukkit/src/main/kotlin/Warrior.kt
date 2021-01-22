/*
 * Copyright (c) 2021 DumbDogDiner <dumbdogdiner.com>. All rights reserved.
 * Licensed under the MIT license, see LICENSE for more information.
 */
package com.dumbdogdiner.warrior

import com.dumbdogdiner.stickyapi.StickyAPI
import java.util.concurrent.Executors
import kr.entree.spigradle.annotations.PluginMain
import org.bukkit.plugin.Plugin
import org.bukkit.plugin.java.JavaPlugin

@PluginMain
class Warrior : JavaPlugin(), WarriorAPI {

    val pool = Executors.newFixedThreadPool(3)

    override fun onLoad() {
        StickyAPI.setPool(pool)
    }

    override fun onEnable() {
    }

    override fun getProvider(): Plugin {
        return this
    }

    override fun getWelcomeMessage(): String {
        return "Hewwo~ wowwd owo!"
    }
}
