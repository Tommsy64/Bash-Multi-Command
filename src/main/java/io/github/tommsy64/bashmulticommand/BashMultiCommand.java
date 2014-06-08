package io.github.tommsy64.bashmulticommand;

import io.github.tommsy64.bashmulticommand.uuid.UUIDManager;

import java.util.UUID;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class BashMultiCommand extends JavaPlugin {

	public static BashMultiCommand plugin;
	private static Permission perm;
	public static Config config;
	public static Strings strings;
	private static Boolean enabled = false;

	private LoginListener loginListener;
	private ChatListener chatListener;

	@Override
	public void onEnable() {
		plugin = this;

		if (checkForVault())
			setUpPermissions();
		else
			getLogger().warning(strings.get("vaultNotFound"));

		config = new Config();
		strings = new Strings();
		chatListener = new ChatListener();
		if (loginListener != null)
			loginListener = new LoginListener();
		enabled = true;
	}

	@Override
	public void onDisable() {
		Config.saveEnabledStates();
		HandlerList.unregisterAll(chatListener);
		enabled = false;
	}

	private boolean checkForVault() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		return true;
	}

	private void setUpPermissions() {
		RegisteredServiceProvider<Permission> rsp = this.getServer()
				.getServicesManager().getRegistration(Permission.class);
		if (rsp != null)
			perm = rsp.getProvider();
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

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if ((args.length == 0 && label.equalsIgnoreCase("bmc"))
				|| args[0].equalsIgnoreCase("help")) {
			sender.sendMessage(strings.getArray("help"));
			return true;
		}

		if (label.equalsIgnoreCase("bmct")
				|| args[0].equalsIgnoreCase("toggle")
				&& hasPermission(sender, "bashmulticommand.toggle")) {

			if (args.length == 1 || label.equalsIgnoreCase("bmct")
					&& hasPermission(sender, "bashmulticommand.toggle")) {
				if (sender instanceof Player)
					PlayerManager.togglePluginState((Player) sender);
				else
					sender.sendMessage(strings.get("playerOnly"));
				return true;
			}

			if (args[1].equalsIgnoreCase("-g")
					&& hasPermission(sender, "bashmulticommand.toggle.global")) {
				if (enabled) {
					onDisable();
					sender.sendMessage(strings.get("pluginGlobalDisabled"));
				} else {
					onEnable();
					sender.sendMessage(strings.get("pluginGlobalEnabled"));
				}
				return true;
			}

			if (args[1] != null
					&& hasPermission(sender, "bashmulticommand.toggle.others")) {

				UUID uuid = UUIDManager.getUUIDFromPlayer(args[1]);

				if (uuid == null) {
					sender.sendMessage(strings.get("playerNotFound")
							.replaceAll("%player%", args[1]));
					return true;
				}

				OfflinePlayer oPlayer = Bukkit.getServer().getOfflinePlayer(
						uuid);
				if (oPlayer != null) {
					String oPlayerName;
					if (oPlayer.getName() != null)
						oPlayerName = oPlayer.getName();
					else
						oPlayerName = args[1];

					if (PlayerManager.togglePluginState(oPlayer.getUniqueId()))
						sender.sendMessage(strings.get("pluginPersonalEnabled")
								+ " for " + ChatColor.RED + oPlayerName);
					else
						sender.sendMessage(strings
								.get("pluginPersonalDisabled")
								+ " for "
								+ ChatColor.RED + oPlayerName);
				}
			}

			return true;
		}

		if (args[0].equalsIgnoreCase("reload")
				&& hasPermission(sender, "bashmulticommand.reload")) {
			onDisable();
			onEnable();
			sender.sendMessage(strings.get("reloaded"));
			return true;
		}

		if (args[0].equalsIgnoreCase("about")
				&& hasPermission(sender, "bashmulticommand.about")) {
			sender.sendMessage(strings.getArray("about"));
			return true;
		}

		sender.sendMessage(strings.get("unkownCommand"));
		return true;
	}
}
