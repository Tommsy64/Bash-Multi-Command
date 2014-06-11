package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.PlayerManager;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommand {

	public Reload() {
		super(BashMultiCommand.strings.get("commandReload"),
				BashMultiCommand.strings.get("commandReloadShortDescription"),
				BashMultiCommand.strings.get("commandReloadLongDescription"));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!PlayerManager.hasPermission(sender, "bashmulticommand.reload")) {
			sender.sendMessage(BashMultiCommand.strings.get("noPermission"));
			return;
		}

		BashMultiCommand.plugin.onDisable();
		BashMultiCommand.plugin.onEnable();
		sender.sendMessage(BashMultiCommand.strings.get("reloaded"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		return new ArrayList<String>();
	}
}
