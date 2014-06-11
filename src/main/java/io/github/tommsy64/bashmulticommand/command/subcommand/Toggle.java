package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.PlayerManager;
import io.github.tommsy64.bashmulticommand.Utils;
import io.github.tommsy64.bashmulticommand.uuid.UUIDManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

public class Toggle extends SubCommand {

	public Toggle() {
		super(BashMultiCommand.strings.get("commandToggle"),
				BashMultiCommand.strings.get("commandToggleShortDescription"),
				BashMultiCommand.strings.get("commandToggleLongDescription"));

		// This gets the aliases of the command in the plugin.yml file without
		// the bmc alias
		this.aliases = Utils.remove(
				"bmc",
				BashMultiCommand.plugin
						.getCommand(
								BashMultiCommand.plugin.getDescription()
										.getCommands().keySet()
										.toArray(new String[0])[0])
						.getAliases().toArray(new String[0]));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {

		if (!sender.hasPermission("bashmulticommand.toggle")) {
			sender.sendMessage(BashMultiCommand.strings.get("noPermission"));
			return;
		}

		// Toggle for sender
		if (args.length == 0 && sender.hasPermission("bashmulticommand.toggle")) {
			if (sender instanceof Player)
				PlayerManager.togglePluginState((Player) sender);
			else
				sender.sendMessage(BashMultiCommand.strings.get("playerOnly"));
			return;
		}

		// Toggle globally
		if (args[0].equalsIgnoreCase("-g")) {
			if (!sender.hasPermission("bashmulticommand.toggle.global")) {
				sender.sendMessage(BashMultiCommand.strings
						.get("noPermissionToggleGlobal"));
				return;
			}
			if (BashMultiCommand.plugin.togglePlugin())
				sender.sendMessage(BashMultiCommand.strings
						.get("pluginGlobalEnabled"));
			else
				sender.sendMessage(BashMultiCommand.strings
						.get("pluginGlobalDisabled"));
			return;
		}

		// Toggle for others
		if (args[0] != null
				&& sender.hasPermission("bashmulticommand.toggle.others"))
			toggleOtherPlayer(sender, args[0]);
		else
			sender.sendMessage(BashMultiCommand.strings
					.get("noPermissionToggleOthers"));
	}

	private void toggleOtherPlayer(CommandSender sender, String player) {
		UUID uuid = UUIDManager.getUUIDFromPlayer(player);

		if (uuid == null) {
			sender.sendMessage(BashMultiCommand.strings.get("playerNotFound")
					.replaceAll("%player%", player));
			return;
		}

		OfflinePlayer oPlayer = Bukkit.getServer().getOfflinePlayer(uuid);

		String oPlayerName;
		if (oPlayer.isOnline())
			oPlayerName = Bukkit.getServer().getPlayer(uuid).getDisplayName();
		else
			oPlayerName = oPlayer.getName();
		if (oPlayerName == null)
			oPlayerName = player;

		if (PlayerManager.togglePluginState(oPlayer.getUniqueId()))
			sender.sendMessage(BashMultiCommand.strings.get(
					"pluginPersonalEnabled").replaceAll("%playername%",
					ChatColor.RED + oPlayerName));
		else
			sender.sendMessage(BashMultiCommand.strings.get(
					"pluginPersonalDisabled").replaceAll("%playername%",
					ChatColor.RED + oPlayerName));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		ArrayList<String> matchedPlayers = new ArrayList<String>();
		for (OfflinePlayer oPlayer : sender.getServer().getOfflinePlayers()) {
			if (StringUtil.startsWithIgnoreCase(oPlayer.getName(), args[0])
					|| args.length < 0 || args[0].length() == 0) {
				matchedPlayers.add(oPlayer.getName());
			}
		}
		Collections.sort(matchedPlayers, String.CASE_INSENSITIVE_ORDER);
		return matchedPlayers;
	}

}
