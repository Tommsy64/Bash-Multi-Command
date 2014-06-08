package io.github.tommsy64.bashmulticommand;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;

public class Config {

	public static String separator;
	public static String permission;
	public static String language;
	public static boolean autoUpdate;

	public static final String dataFile = BashMultiCommand.plugin
			.getDataFolder() + File.separator + "enabledData.bin";

	public static final FileConfiguration config = BashMultiCommand.plugin
			.getConfig();

	public Config() {
		BashMultiCommand.plugin.saveDefaultConfig();
		BashMultiCommand.plugin.reloadConfig();
		separator = config.getString("separator");
		permission = config.getString("permission");
		language = config.getString("language");
		loadEnabledSates();
	}

	private void loadEnabledSates() {
		if (!new File(dataFile).exists())
			return;
		try {
			PlayerManager.playerEnables = Loader.load(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveEnabledStates() {
		try {
			Loader.save(PlayerManager.playerEnables, dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
