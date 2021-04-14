package com.dumbdogdiner.warrior;

import com.dumbdogdiner.warrior.api.WarriorAPI;
import com.dumbdogdiner.warrior.api.WarriorLogger;
import com.dumbdogdiner.warrior.api.managers.WarriorArenaManager;
import com.dumbdogdiner.warrior.api.managers.WarriorGUIManager;
import com.dumbdogdiner.warrior.api.managers.WarriorGameBarManager;
import com.dumbdogdiner.warrior.api.managers.WarriorKitManager;
import com.dumbdogdiner.warrior.api.managers.WarriorLevelManager;
import com.dumbdogdiner.warrior.api.managers.WarriorLobbyManager;
import com.dumbdogdiner.warrior.api.managers.WarriorNotificationManager;
import com.dumbdogdiner.warrior.api.managers.WarriorPlayerManager;
import com.dumbdogdiner.warrior.api.translation.Translator;
import org.bukkit.plugin.java.JavaPlugin;

public class ApiProvider implements WarriorAPI {
	@Override
	public JavaPlugin getInstance() {
		return Warrior.getInstance();
	}

	@Override
	public WarriorLogger getLogger() {
		return Warrior.getPluginLogger();
	}

	@Override
	public Translator getTranslator() {
		return Warrior.getTranslator();
	}

	@Override
	public WarriorArenaManager getArenaManager() {
		return Warrior.getInstance().getArenaManager();
	}

	@Override
	public WarriorGameBarManager getGameBarManager() {
		return Warrior.getInstance().getGameBarManager();
	}

	@Override
	public WarriorGUIManager getGUIManager() {
		return Warrior.getInstance().getGuiManager();
	}

	@Override
	public WarriorKitManager getKitManager() {
		return Warrior.getInstance().getKitManager();
	}

	@Override
	public WarriorLevelManager getLevelManager() {
		return Warrior.getInstance().getLevelManager();
	}

	@Override
	public WarriorLobbyManager getLobbyManager() {
		return Warrior.getInstance().getLobbyManager();
	}

	@Override
	public WarriorNotificationManager getNotificationManager() {
		return Warrior.getInstance().getNotificationManager();
	}

	@Override
	public WarriorPlayerManager getPlayerManager() {
		return Warrior.getInstance().getPlayerManager();
	}
}
