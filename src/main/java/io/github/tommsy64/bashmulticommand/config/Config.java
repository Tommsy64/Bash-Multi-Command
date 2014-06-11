package io.github.tommsy64.bashmulticommand.config;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static String separator;
	public static String permission;
	public static String language;
	public static boolean autoUpdate;

	public static final FileConfiguration config = BashMultiCommand.plugin
			.getConfig();

	public static void loadConfig() {
		BashMultiCommand.plugin.saveDefaultConfig();
		BashMultiCommand.plugin.reloadConfig();

		separator = config.getString("separator");
		permission = config.getString("permission");
		language = config.getString("language");
		autoUpdate = config.getBoolean("autoupdate") == true;
	}
}
