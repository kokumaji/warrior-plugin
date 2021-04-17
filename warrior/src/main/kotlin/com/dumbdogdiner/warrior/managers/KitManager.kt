package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.api.managers.WarriorKitManager
import com.dumbdogdiner.warrior.api.kit.BaseKit
import com.dumbdogdiner.warrior.api.kit.kits.WarriorKit
import com.dumbdogdiner.warrior.api.kit.kits.ArcherKit
import com.dumbdogdiner.warrior.api.kit.kits.TankKit
import com.dumbdogdiner.warrior.api.translation.Placeholders
import com.dumbdogdiner.warrior.util.DefaultMessages
import java.util.HashMap
import com.dumbdogdiner.warrior.Warrior
import java.io.FileReader
import com.dumbdogdiner.warrior.api.kit.kits.CustomKit
import com.dumbdogdiner.warrior.api.util.json.models.CustomKitModel
import com.dumbdogdiner.warrior.util.JSONUtil
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import org.bukkit.Material
import java.io.File
import java.io.IOException
import java.util.ArrayList

class KitManager : WarriorKitManager {
    private val kits = ArrayList<BaseKit>()

    fun registerKits() {
        kits.add(WarriorKit("Warrior", 0, "warrior.kit.warrior", Material.IRON_SWORD))
        kits.add(ArcherKit("Archer", 0, "warrior.kit.archer", Material.BOW))
        kits.add(TankKit("Tank", 0, "warrior.kit.tank", Material.IRON_CHESTPLATE))
        loadFiles()
    }

    /**
     * Load kits from plugin configuration.
     */
    private fun loadFiles() {
        val dataFolder = File(JSONUtil.KIT_DATA_PATH)
        val files = dataFolder.listFiles() ?: return
        var i = 0

        for (f in files) {
            addKit(f)
            i++
        }

        val msg = Placeholders.applyPlaceholders(DefaultMessages.LOADED_OBJECT, object : HashMap<String?, String?>() {
            init {
                put("AMOUNT", i.toString())
                put("TYPE", "kit")
            }
        })

        Warrior.pluginLogger.info(msg)
    }

    override fun addKit(f: File) {
        if (!f.canRead()) return

        try {
            JsonReader(FileReader(f)).use { reader ->
                val model = Gson().fromJson<CustomKitModel>(reader, CustomKitModel::class.java)
                kits.add(CustomKit(model))
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun addKit(kit: CustomKit) {
        kits.add(kit)
    }

    override fun get(n: String): BaseKit {
        for (k in kits) {
            if (k.name.equals(n, ignoreCase = true)) return k
        }
        return kits[0]
    }

    override fun getKits(): List<BaseKit> {
        return kits
    }
}