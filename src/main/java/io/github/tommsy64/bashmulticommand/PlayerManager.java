package io.github.tommsy64.bashmulticommand;

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
				player.sendMessage(BashMultiCommand.strings.get(
						"pluginPersonalDisabled").replaceAll(
						"for %playername%", ""));
			} else {
				playerEnables.put(playerUUID, true);
				player.sendMessage(BashMultiCommand.strings.get(
						"pluginPersonalEnabled").replaceAll("for %playername%",
						""));
			}
		} else {
			playerEnables.put(playerUUID, true);
			player.sendMessage(BashMultiCommand.strings.get(
					"pluginPersonalEnabled").replaceAll("for %playername%", ""));
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
