package io.github.tommsy64.bashmulticommand;

public class Config {

	public static String separator;
	public static String permission;
	public static boolean autoUpdate;

	public Config() {
		BashMultiCommand.plugin.saveDefaultConfig();
		BashMultiCommand.plugin.reloadConfig();
		separator = BashMultiCommand.plugin.getConfig().getString("separator");
		permission = BashMultiCommand.plugin.getConfig()
				.getString("permission");
	}
}
