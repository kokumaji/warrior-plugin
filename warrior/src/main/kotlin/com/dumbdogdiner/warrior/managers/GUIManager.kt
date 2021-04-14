package com.dumbdogdiner.warrior.managers

import com.dumbdogdiner.warrior.api.managers.WarriorGUIManager
import java.util.HashMap
import com.dumbdogdiner.stickyapi.bukkit.gui.GUI
import com.dumbdogdiner.warrior.gui.ArenaGUI
import com.dumbdogdiner.warrior.gui.KitGUI
import com.dumbdogdiner.warrior.gui.DeathSoundGUI
import com.dumbdogdiner.warrior.gui.settings.SettingsGUI
import com.dumbdogdiner.warrior.gui.settings.LanguageGUI
import com.dumbdogdiner.warrior.gui.settings.NotificationsGUI
import com.dumbdogdiner.warrior.gui.settings.PrivacyGUI
import com.dumbdogdiner.warrior.gui.ParticleTrailGUI

class GUIManager : WarriorGUIManager {
    private val map = HashMap<Class<*>, GUI>()

    fun registerGUIs() {
        map[ArenaGUI::class.java] = ArenaGUI()
        map[KitGUI::class.java] = KitGUI()
        map[DeathSoundGUI::class.java] = DeathSoundGUI()
        map[SettingsGUI::class.java] = SettingsGUI()
        map[LanguageGUI::class.java] = LanguageGUI()
        map[NotificationsGUI::class.java] = NotificationsGUI()
        map[PrivacyGUI::class.java] = PrivacyGUI()
        map[ParticleTrailGUI::class.java] = ParticleTrailGUI()
    }

    override fun <T> get(guiClass: Class<T>): T {
        return guiClass.cast(map[guiClass])
    }

}