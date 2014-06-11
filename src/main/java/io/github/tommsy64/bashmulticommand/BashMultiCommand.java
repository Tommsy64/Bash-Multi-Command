package io.github.tommsy64.bashmulticommand;

import io.github.tommsy64.bashmulticommand.command.BMCCommandExecutor;
import io.github.tommsy64.bashmulticommand.config.Config;
import io.github.tommsy64.bashmulticommand.config.SLAPI;
import io.github.tommsy64.bashmulticommand.listeners.ChatListener;
import io.github.tommsy64.bashmulticommand.listeners.LoginListener;
import io.github.tommsy64.bashmulticommand.locale.Strings;
import io.github.tommsy64.bashmulticommand.uuid.UUIDManager;

import java.io.File;
import java.util.HashMap;
import java.util.UUID;

import net.gravitydevelopment.updater.Updater;
import net.gravitydevelopment.updater.Updater.UpdateType;

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class BashMultiCommand extends JavaPlugin {

	// Plugin instance
	public static BashMultiCommand plugin;

	public Strings strings;
	public BMCCommandExecutor commandExecutor;
	// Updater
	public Updater updater;

	private boolean enabled = false;
	private final String enabledDataPath = getDataFolder() + File.separator
			+ "enabledData.bin";

	// Listeners
	private LoginListener loginListener;
	private ChatListener chatListener;

	@Override
	public void onEnable() {
		plugin = this;
		// Load configs and locale
		try {
			// Must load configs in this order!
			Config.loadConfig();
			strings = new Strings();
			UUIDManager.loadCache();

			HashMap<UUID, Boolean> playerEnables = SLAPI
					.loadFile(enabledDataPath);
			if (playerEnables != null)
				PlayerManager.playerEnables = playerEnables;
		} catch (Exception e) {
			getLogger()
					.severe(ChatColor.DARK_RED
							+ "Error loading config and/or localization! BashMultiCommand will now disable!");
			e.printStackTrace();
			getServer().getPluginManager().disablePlugin(this);
			return;
		}

		// Setup the listeners
		chatListener = new ChatListener();
		if (loginListener == null)
			loginListener = new LoginListener();

		// Load updater
		if (Config.autoUpdateEnabled)
			if (Config.autoDownload)
				loadUpdator(Updater.UpdateType.DEFAULT);
			else
				loadUpdator(Updater.UpdateType.NO_DOWNLOAD);

		// Load commands
		commandExecutor = new BMCCommandExecutor();
		commandExecutor.loadCommands();
		getCommand("BashMultiCommand").setExecutor(commandExecutor);

		enabled = true;
	}

	public void loadUpdator(UpdateType updateType) {
		if (Config.autoUpdateEnabled)
			this.updater = new Updater(BashMultiCommand.plugin, 80942,
					BashMultiCommand.plugin.getFile(), updateType, false);
	}

	@Override
	public void onDisable() {
		if (enabled) {
			SLAPI.saveObject(PlayerManager.playerEnables, enabledDataPath);
			UUIDManager.saveCache();
			HandlerList.unregisterAll(chatListener);
			enabled = false;
		}
	}

	public boolean togglePlugin() {
		if (enabled) {
			onDisable();
			return false;
		} else {
			onEnable();
			return true;
		}
	}
}
