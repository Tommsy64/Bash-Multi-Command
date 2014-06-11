package io.github.tommsy64.bashmulticommand.config;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.Utils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class CustomConfig {
	public FileConfiguration config;
	private File file;
	private final String fullPath;
	private final String path;
	private final String configName;

	public CustomConfig(String configName, String customPath) {
		this.configName = configName + ".yml";
		this.path = customPath;
		this.fullPath = BashMultiCommand.plugin.getDataFolder() + ""
				+ File.separator + this.path;
	}

	public void saveConfig() {
		try {
			loadFile();
			this.config.save(this.file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("deprecation")
	public void loadConfig() {
		loadFile();
		config = YamlConfiguration.loadConfiguration(file);

		// Look for defaults in the jar
		InputStream defConfigStream = BashMultiCommand.plugin
				.getResource(this.path + File.separator + this.configName);

		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			config.setDefaults(defConfig);
		}
	}

	public void loadFile() {
		if (file == null) {
			Utils.createDirectory(fullPath);
			file = new File(this.fullPath, this.configName);
			if (!this.file.exists())
				try {
					BashMultiCommand.plugin.saveResource(this.path
							+ File.separator + this.configName, false);
				} catch (Exception e) {
					BashMultiCommand.plugin.getLogger().fine(
							"Resource " + this.configName + " was not found!");
				}
		}
	}
}
