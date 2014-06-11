package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class Reload extends SubCommand {

	public Reload() {
		super(BashMultiCommand.plugin.strings.get("commandReload"),
				BashMultiCommand.plugin.strings
						.get("commandReloadShortDescription"),
				BashMultiCommand.plugin.strings
						.get("commandReloadLongDescription"));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!sender.hasPermission("bashmulticommand.reload")) {
			sender.sendMessage(BashMultiCommand.plugin.strings
					.get("noPermission"));
			return;
		}

		BashMultiCommand.plugin.onDisable();
		BashMultiCommand.plugin.onEnable();
		sender.sendMessage(BashMultiCommand.plugin.strings.get("reloaded"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		return new ArrayList<String>();
	}
}
