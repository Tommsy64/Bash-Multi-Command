package io.github.tommsy64.bashmulticommand.locale;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.config.Config;
import io.github.tommsy64.bashmulticommand.config.CustomConfig;

import java.util.HashMap;
import java.util.Map;

public final class Strings {

	private CustomConfig localeConfig;

	private Map<String, Object> messages = new HashMap<>();
	private Map<String, String[]> commandMessages = new HashMap<>();

	public Strings() {
		this.localeConfig = new CustomConfig(Config.language, "localization");
		localeConfig.loadConfig();

		this.messages = localeConfig.config.getConfigurationSection("messages")
				.getValues(false);
		loadCommandMessages();
	}

	private void loadCommandMessages() {
		Map<String, Object> map = localeConfig.config.getConfigurationSection(
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

	private String messageFormat(String message) {
		if (message == null)
			return null;
		message = message.replaceAll("%pluginname%",
				this.messages.get("pluginDisplayName").toString());
		message = message.replaceAll("%pluginrealname%",
				BashMultiCommand.plugin.getDescription().getName());
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
		if (strings != null)
			return messageFormat(strings);
		else
			return new String[0];
	}
}
