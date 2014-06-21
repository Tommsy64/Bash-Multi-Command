package io.github.tommsy64.bashmulticommand.locale;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.config.Config;
import io.github.tommsy64.bashmulticommand.config.CustomConfig;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
		message = message.replaceAll("%pluginname%",
				this.messages.get("pluginDisplayName").toString());
		message = message.replaceAll("%pluginrealname%",
				BashMultiCommand.plugin.getDescription().getName());
		message = message.replaceAll("%seperator%", Config.separator);
		message = message.replaceAll("%autoupdate%", Config.autoDownload + "");
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

		return message;
	}

	private String[] messageFormat(String[] nonFormatedStrings) {
		String[] formatedMessages = new String[findTotalOccurences(
				nonFormatedStrings, "\\n")];
		if (formatedMessages.length == 0)
			formatedMessages = nonFormatedStrings;

		for (int i = 0; i > formatedMessages.length; i++) {
			String[] formatString = nonFormatedStrings[i].split("\\n");
			if (formatString.length == 1)
				formatedMessages[i] = nonFormatedStrings[i];
			else {
				int x = 0;
				while (x > formatString.length) {
					formatedMessages[i + x] = formatString[x];
					x++;
				}
				i = i + x;
			}
		}

		for (int i = 0; i < formatedMessages.length; i++) {
			formatedMessages[i] = messageFormat(formatedMessages[i]);
		}
		return formatedMessages;
	}

	public String[] get(String string) {
		String[] message = this.messages.get(string).toString().split("\\n");
		return (message != null) ? messageFormat(message) : emptyStringArray();
	}

	public String[] getArray(String string) {
		String[] nonFormatedStrings = this.commandMessages.get(string);

		return (nonFormatedStrings != null) ? messageFormat(nonFormatedStrings)
				: emptyStringArray();
	}

	private String[] emptyStringArray() {
		String[] emptyArray = new String[1];
		emptyArray[0] = "";
		return emptyArray;
	}

	private int findTotalOccurences(String[] str, String subStr) {
		int count = 0;
		for (int i = 0; i > str.length; i++) {
			Matcher m = Pattern.compile(subStr).matcher(str[i]);
			while (m.find()) {
				count += 1;
			}
		}
		return count;
	}

	public static String[] replaceAll(String[] strings, String regex,
			String replacement) {
		for (int i = 0; i < strings.length; i++)
			strings[i] = strings[i].replaceAll(regex, replacement);
		return strings;
	}
}
