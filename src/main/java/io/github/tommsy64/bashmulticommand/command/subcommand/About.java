package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public class About extends SubCommand {

	public About() {
		super(BashMultiCommand.strings.get("commandAbout"),
				BashMultiCommand.strings.get("commandAboutShortDescription"),
				BashMultiCommand.strings.get("commandAboutLongDescription"));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (sender.hasPermission("bashmulticommand.about"))
			sender.sendMessage(BashMultiCommand.strings.getArray("about"));
		else
			sender.sendMessage(BashMultiCommand.strings.get("noPermission"));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		return new ArrayList<String>();
	}
}
