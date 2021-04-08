package com.dumbdogdiner.warrior;

import java.util.logging.Logger;

/**
 * Utility interface for quickly accessing plugin fields.
 */
public interface WithWarriorPlugin {
	/**
	 * @return The {@link Warrior} plugin instance.
	 */
	default Warrior getPlugin() {
		return Warrior.getInstance();
	}

	/**
	 * @return The plugin {@link Logger} provided by Bukkit.
	 */
	default Logger getLogger() {
		return this.getPlugin().getLogger();
	}
}
