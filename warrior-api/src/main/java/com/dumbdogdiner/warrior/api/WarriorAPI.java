package com.dumbdogdiner.warrior.api;

import com.dumbdogdiner.warrior.api.translation.Translator;
import com.dumbdogdiner.warrior.api.util.TranslationUtil;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Logger;

/**
 * Provides a way to access plugin instance methods, without direct access to the Warrior plugin class.
 */
public interface WarriorAPI {
	/**
	 * Represents an exception thrown when an illegal service registration is found.
	 */
	class IllegalServiceException extends RuntimeException {
		/**
		 * The error message wrapped by this error.
		 */
		@Getter
		private final String message;
		public IllegalServiceException(String message) {
			this.message = message;
		}
	}

	/**
	 * Register the Warrior service.
	 * @param plugin The plugin registering the service
	 * @param service The plugin's implementation of the service
	 */
	static void registerService(JavaPlugin plugin, WarriorAPI service) {
		Bukkit.getServicesManager().register(WarriorAPI.class, service, plugin, ServicePriority.Lowest);
	}

	/**
	 * Fetch the instantiated API service.
	 * @return The {@link WarriorAPI} API object.
	 */
	@NotNull
	static WarriorAPI getService() {
		var provider = Bukkit.getServicesManager().getRegistration(WarriorAPI.class);
		// just in case someone tries something wacky.
		if (provider == null) {
			throw new IllegalServiceException("Failed to fetch the WarriorAPI - existing service is invalid!");
		}
		return provider.getProvider();
	}

	/**
	 * @return The {@link JavaPlugin} implementing the Warrior API.
	 */
	JavaPlugin getInstance();

	/**
	 * @return The {@link Configuration} of the plugin implementing the Warrior API.
	 */
	default Configuration getConfig() {
		return this.getInstance().getConfig();
	}

	/**
	 * @return The {@link Logger} attached to the plugin implementing the Warrior API.
	 */
	WarriorLogger getLogger();

	/**
	 * @return A {@link String} containing the header for Warrior commands.
	 */
	default String getCommandHeader() {
		return "&8" + TranslationUtil.HL(15) + " &8[ &3&l" + this.getInstance().getName() + " &8] " + TranslationUtil.HL(15);
	}

	/**
	 * @return The {@link Translator} associated with this API implementation.
	 */
	Translator getTranslator();

	/**
	 * @return The {@link PlayerManager} associated with this API implementation.
	 */
	PlayerManager getPlayerManager();
}
