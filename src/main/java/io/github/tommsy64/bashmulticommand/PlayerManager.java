package io.github.tommsy64.bashmulticommand;

import java.util.HashMap;
import java.util.UUID;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class PlayerManager {

	public static HashMap<UUID, Boolean> playerEnables = new HashMap<>();

	private static Permission perm;

	public static void setUpPermissions() {
		RegisteredServiceProvider<Permission> rsp = BashMultiCommand.plugin
				.getServer().getServicesManager()
				.getRegistration(Permission.class);
		if (rsp != null)
			perm = rsp.getProvider();
	}

	public static void togglePluginState(Player player) {
		UUID playerUUID = player.getUniqueId();
		if (playerEnables.containsKey(playerUUID)) {
			if (playerEnables.get(playerUUID)) {
				playerEnables.put(playerUUID, false);
				player.sendMessage(BashMultiCommand.strings
						.get("pluginPersonalDisabled"));
			} else {
				playerEnables.put(playerUUID, true);
				player.sendMessage(BashMultiCommand.strings
						.get("pluginPersonalEnabled"));
			}
		} else {
			playerEnables.put(playerUUID, true);
			player.sendMessage(BashMultiCommand.strings
					.get("pluginPersonalEnabled"));
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
		return false;
	}

	public static boolean hasPermission(Player player, String permission) {
		if (perm != null)
			return perm.has(player, permission);
		else
			return player.hasPermission(permission);
	}

	public static boolean hasPermission(CommandSender sender, String permission) {
		if (!(sender instanceof Player))
			return true;
		else
			return hasPermission((Player) sender, permission);
	}
}
