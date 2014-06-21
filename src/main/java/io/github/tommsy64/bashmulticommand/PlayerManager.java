package io.github.tommsy64.bashmulticommand;

import io.github.tommsy64.bashmulticommand.locale.Strings;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;

public class PlayerManager {

	public static HashMap<UUID, Boolean> playerEnables = new HashMap<>();

	public static void togglePluginState(Player player) {
		UUID playerUUID = player.getUniqueId();
		if (playerEnables.containsKey(playerUUID)) {
			if (playerEnables.get(playerUUID)) {
				playerEnables.put(playerUUID, false);
				player.sendMessage(Strings.replaceAll(
						BashMultiCommand.plugin.strings
								.get("pluginPersonalDisabled"),
						"for %playername%", ""));
			} else {
				playerEnables.put(playerUUID, true);
				player.sendMessage(Strings.replaceAll(
						BashMultiCommand.plugin.strings
								.get("pluginPersonalEnabled"),
						"for %playername%", ""));
			}
		} else {
			playerEnables.put(playerUUID, true);
			player.sendMessage(Strings.replaceAll(
					BashMultiCommand.plugin.strings
							.get("pluginPersonalEnabled"), "for %playername%",
					""));
		}
	}

	public static boolean togglePluginState(UUID playerUUID) {
		if (playerEnables.containsKey(playerUUID)) {
			if (playerEnables.get(playerUUID)) {
				playerEnables.put(playerUUID, false);
				return false;
			} else {
				playerEnables.put(playerUUID, true);
				return true;
			}
		} else {
			playerEnables.put(playerUUID, true);
			return true;
		}
	}

	public static boolean isEnabled(UUID playerUUID) {
		if (playerEnables.get(playerUUID) != null)
			return playerEnables.get(playerUUID) == true;
		else
			return true;
	}
}
