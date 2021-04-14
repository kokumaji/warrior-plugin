package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.api.managers.WarriorLobbyManager
import com.dumbdogdiner.warrior.Warrior
import java.io.FileReader
import com.dumbdogdiner.warrior.models.LobbyDataModel
import com.dumbdogdiner.warrior.util.JSONUtil
import com.google.gson.Gson
import com.google.gson.stream.JsonReader
import org.bukkit.Bukkit
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import java.io.File
import java.io.IOException
import java.util.*

class LobbyManager : WarriorLobbyManager {

    private var lobbySpawn: Location? = null

    override fun getLobbySpawn(): Location? {
        return lobbySpawn
    }


    /**
     * Load lobby spawn data from plugin configuration.
     */
    fun loadData() {
        val c: FileConfiguration = Warrior.instance.config

        if (c.getBoolean("lobby-settings.custom-spawn")) {
            val lobbyData = File(JSONUtil.LOBBY_DATA_PATH)
            if (!lobbyData.exists()) {
                lobbySpawn = getVanillaSpawn()
                return
            }
            try {
                JsonReader(FileReader(lobbyData)).use { reader ->
                    val lobbyDataModel = Gson().fromJson<LobbyDataModel>(reader, LobbyDataModel::class.java)
                    val spawnJson = lobbyDataModel.spawn
                    lobbySpawn = Location(
                        Bukkit.getWorld(spawnJson.world), spawnJson.x
                            .toDouble(), spawnJson.y.toDouble(), spawnJson.z.toDouble(), spawnJson.yaw, spawnJson.pitch
                    )
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        } else {
            lobbySpawn = getVanillaSpawn()
        }
    }

    fun getVanillaSpawn(): Location {
        val c: FileConfiguration = Warrior.instance.config
        val world = c.getString("lobby-settings.lobby-world")
        return if (world == null) {
            Bukkit.getWorlds()[0].spawnLocation
        } else {
            Objects.requireNonNull(Bukkit.getWorld(world))!!.spawnLocation
        }
    }

    override fun updateLocation(location: Location) {
        lobbySpawn = location
        JSONUtil.saveSpawn()
    }
}