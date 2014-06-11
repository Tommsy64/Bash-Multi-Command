package io.github.tommsy64.bashmulticommand.config;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

public class Config {

	public static String separator;
	public static String language;
	public static boolean autoDownload;
	public static boolean autoUpdateEnabled;

	public static void loadConfig() {
		BashMultiCommand.plugin.saveDefaultConfig();
		BashMultiCommand.plugin.reloadConfig();

		separator = BashMultiCommand.plugin.getConfig().getString("separator");
		language = BashMultiCommand.plugin.getConfig().getString("language");
		autoDownload = BashMultiCommand.plugin.getConfig().getBoolean(
				"updater.download") == true;
		autoUpdateEnabled = BashMultiCommand.plugin.getConfig().getBoolean(
				"updater.enabled") == true;
	}
}
