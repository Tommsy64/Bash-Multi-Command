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

import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class BashMultiCommand extends JavaPlugin {

	// Public static variables
	public static BashMultiCommand plugin;
	public static Strings strings;
	public static BMCCommandExecutor commandExecutor;

	// Private variables
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

		// Load Vault stuff
		if (!checkForVault())
			getLogger().warning(strings.get("vaultNotFound"));
		else
			PlayerManager.setUpPermissions();

		// Setup the listeners
		chatListener = new ChatListener();
		if (loginListener == null)
			loginListener = new LoginListener();

		// Load commands
		commandExecutor = new BMCCommandExecutor();
		commandExecutor.loadCommands();
		getCommand("BashMultiCommand").setExecutor(commandExecutor);

		enabled = true;
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

	private boolean checkForVault() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		return true;
	}
}
