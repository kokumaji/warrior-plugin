package com.dumbdogdiner.warrior.kits

import com.dumbdogdiner.warrior.api.kit.Kit
import com.dumbdogdiner.warrior.api.kit.KitContainer

class KitContainer: KitContainer {

    private val kitCache: MutableMap<Class<*>, Kit> = HashMap()

    fun registerDefaults() {
        registerKit(WarriorKit())
    }

    override fun <T : Kit> registerKit(kit: T): Boolean {
        // dont register a kit twice
        if(kitCache.containsValue(kit)) return false

        kitCache[kit.javaClass] = kit
        return true
    }

    override fun <T : Kit> get(kitClass: Class<T>): T {
        return kitClass.cast(kitCache[kitClass]!!)
    }
}