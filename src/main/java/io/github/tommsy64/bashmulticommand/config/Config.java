package io.github.tommsy64.bashmulticommand.config;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.PlayerManager;

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

	public static void loadConfig() {
		BashMultiCommand.plugin.saveDefaultConfig();
		BashMultiCommand.plugin.reloadConfig();

		separator = config.getString("separator");
		permission = config.getString("permission");
		language = config.getString("language");
		autoUpdate = config.getBoolean("autoupdate") == true;

		loadEnabledSates();
	}

	public static void loadEnabledSates() {
		if (!new File(dataFile).exists())
			return;
		try {
			PlayerManager.playerEnables = SLAPI.load(dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveEnabledStates() {
		try {
			SLAPI.save(PlayerManager.playerEnables, dataFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
