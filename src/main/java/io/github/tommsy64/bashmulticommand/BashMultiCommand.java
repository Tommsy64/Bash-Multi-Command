package io.github.tommsy64.bashmulticommand;

import io.github.tommsy64.bashmulticommand.command.BMCCommandExecutor;
import io.github.tommsy64.bashmulticommand.config.Config;
import io.github.tommsy64.bashmulticommand.listeners.ChatListener;
import io.github.tommsy64.bashmulticommand.listeners.LoginListener;
import io.github.tommsy64.bashmulticommand.locale.Strings;

import org.bukkit.event.HandlerList;
import org.bukkit.plugin.java.JavaPlugin;

public final class BashMultiCommand extends JavaPlugin {

	// Public static variables
	public static BashMultiCommand plugin;
	public static Strings strings;

	// Private variables
	private boolean enabled = false;

	// Listeners
	private LoginListener loginListener;
	private ChatListener chatListener;

	@Override
	public void onEnable() {
		plugin = this;

		// Load Vault stuff
		if (!checkForVault())
			getLogger().warning(strings.get("vaultNotFound"));
		PlayerManager.setUpPermissions();

		// Load configs and locale
		Config.loadConfig();
		strings = new Strings();

		// Setup the listeners
		chatListener = new ChatListener();
		if (loginListener != null)
			loginListener = new LoginListener();

		// Load commands
		BMCCommandExecutor commandExecutor = new BMCCommandExecutor();
		commandExecutor.loadCommands();
		getCommand("BashMultiCommand").setExecutor(commandExecutor);

		enabled = true;
	}

	@Override
	public void onDisable() {
		Config.saveEnabledStates();
		HandlerList.unregisterAll(chatListener);
		enabled = false;
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
