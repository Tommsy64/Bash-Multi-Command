package io.github.tommsy64.bashmulticommand.config;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.ConfigurationSection;

public class Config {

	// General
	public static String separator;
	public static String language;

	// Auto Update
	public static boolean autoDownload;
	public static boolean autoUpdateEnabled;

	// Permissions
	public static Map<String, Integer> permissionsChat = new HashMap<>();
	public static Map<String, Integer> permissionsCommand = new HashMap<>();

	public static void loadConfig() {
		BashMultiCommand.plugin.saveDefaultConfig();
		BashMultiCommand.plugin.reloadConfig();

		separator = BashMultiCommand.plugin.getConfig().getString("separator");
		language = BashMultiCommand.plugin.getConfig().getString("language");

		autoDownload = BashMultiCommand.plugin.getConfig().getBoolean(
				"updater.download") == true;
		autoUpdateEnabled = BashMultiCommand.plugin.getConfig().getBoolean(
				"updater.enabled") == true;

		ConfigurationSection chatSection = BashMultiCommand.plugin.getConfig()
				.getConfigurationSection("maximumchat");
		for (String key : chatSection.getKeys(false)) {
			permissionsChat.put(key, chatSection.getInt(key));
		}

		ConfigurationSection commandSection = BashMultiCommand.plugin
				.getConfig().getConfigurationSection("maximumcommand");
		for (String key : commandSection.getKeys(false)) {
			permissionsCommand.put(key, commandSection.getInt(key));
		}
	}
}
