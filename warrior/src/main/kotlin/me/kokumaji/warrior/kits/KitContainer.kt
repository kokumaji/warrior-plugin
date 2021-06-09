package me.kokumaji.warrior.kits

import me.kokumaji.warrior.api.kit.Kit
import me.kokumaji.warrior.api.kit.KitContainer
import me.kokumaji.warrior.user.User

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

    fun <T : Kit> applyKit(kitClass: Class<T>, user: User) {
        if(user.hasKit()) return
        val kit = get(kitClass)

        user.kit = kit.kitEnum
        kit.setup(user)

    }

    fun removeKit(user: User): Boolean {
        if(!user.hasKit()) return false

        user.kit = KitEnum.NONE
        return true
    }

}