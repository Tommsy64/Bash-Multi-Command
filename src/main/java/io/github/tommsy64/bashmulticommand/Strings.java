package io.github.tommsy64.bashmulticommand;

import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public final class Strings {

	private Map<String, Object> messages = new HashMap<>();
	private Map<String, String[]> commandMessages = new HashMap<>();

	private FileConfiguration localizationConfig = null;
	private File localizationFile = null;

	private final String localizationPath = BashMultiCommand.plugin
			.getDataFolder() + File.separator + "localization";

	public Strings() {
		this.reloadLocalization();
		this.messages = localizationConfig.getConfigurationSection("messages")
				.getValues(false);

		Map<String, Object> map = localizationConfig.getConfigurationSection(
				"commandmessages").getValues(false);
		Object[] keys = map.keySet().toArray();

		int i = 0;
		while (i < keys.length) {
			String stringMap = map.get(keys[i]).toString();
			commandMessages.put(
					keys[i].toString(),
					stringMap.substring(1, stringMap.lastIndexOf(']')).split(
							","));
			i++;
		}

	}

	private void createDirectory() {
		File f = new File(localizationPath);
		if (f.exists() && f.isDirectory())
			return;
		else {
			f.delete();
			f.mkdirs();
		}
	}

	@SuppressWarnings("deprecation")
	public void reloadLocalization() {
		if (localizationFile == null) {
			createDirectory();
			localizationFile = new File(localizationPath, Config.language
					+ ".yml");
			if (!this.localizationFile.exists())
				BashMultiCommand.plugin.saveResource("localization"
						+ File.separator + Config.language + ".yml", false);
		}

		localizationConfig = YamlConfiguration
				.loadConfiguration(localizationFile);

		// Look for defaults in the jar
		InputStream defConfigStream = BashMultiCommand.plugin
				.getResource("localization" + File.separator + Config.language
						+ ".yml");
		if (defConfigStream != null) {
			YamlConfiguration defConfig = YamlConfiguration
					.loadConfiguration(defConfigStream);
			localizationConfig.setDefaults(defConfig);
		}
	}

	private String messageFormat(String message) {
		if (message == null)
			return null;
		message = message.replaceAll("%pluginname%",
				this.messages.get("pluginDisplayName").toString());
		message = message.replaceAll("%seperator%", Config.separator);
		message = message.replaceAll("%permission%", Config.permission);
		message = message.replaceAll("%autoupdate%", Config.autoUpdate + "");
		message = message.replaceAll("%reloadCommand%",
				this.messages.get("reloadCommand").toString());
		message = message.replaceAll("%toggleCommand%",
				this.messages.get("toggleCommand").toString());
		message = message.replaceAll("%updateCommand%",
				this.messages.get("updateCommand").toString());
		message = message.replaceAll("%helpCommand%",
				this.messages.get("helpCommand").toString());
		message = message.replaceAll("%aboutCommand%",
				this.messages.get("aboutCommand").toString());
		message = message.replaceAll("&([0-9a-f])", "\u00A7$1");
		message = message.replaceAll(",", "\n");
		message = message.replaceAll("%comma%", ",");

		return message;
	}

	private String[] messageFormat(String[] messages) {
		if (messages == null)
			return null;

		String[] formatedMessages = new String[messages.length];
		for (int i = 0; i < messages.length; i++) {
			messages[i] = messages[i].replace(',', '\n');
			formatedMessages[i] = messageFormat(messages[i]);
		}
		return formatedMessages;
	}

	public String get(String string) {
		String message = messageFormat(this.messages.get(string).toString());
		if (message != null)
			return message;
		else
			return "";
	}

	public String[] getArray(String string) {
		String[] strings = this.commandMessages.get(string);
		return messageFormat(strings);
	}
}
