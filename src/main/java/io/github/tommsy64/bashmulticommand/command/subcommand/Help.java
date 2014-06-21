package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.locale.Strings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class Help extends SubCommand {

	public Help() {
		super(BashMultiCommand.plugin.strings.get("commandHelp")[0],
				BashMultiCommand.plugin.strings
						.get("commandHelpShortDescription")[0],
				BashMultiCommand.plugin.strings
						.get("commandHelpLongDescription"));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!sender.hasPermission("bashmulticommand.command.help")) {
			sender.sendMessage(BashMultiCommand.plugin.strings
					.get("noPermission"));
			return;
		}

		if (args.length != 0)
			commandHelp(sender, args);
		else
			sender.sendMessage(BashMultiCommand.plugin.commandExecutor
					.getHelpMessage());
	}

	private void commandHelp(CommandSender sender, String[] args) {
		sender.sendMessage(BashMultiCommand.plugin.strings.get("chatTitle"));

		String command = getCommand(args[0]);
		if (command != null)
			sender.sendMessage(BashMultiCommand.plugin.commandExecutor.commands
					.get(command).longDescription);
		else
			sender.sendMessage(Strings.replaceAll(
					BashMultiCommand.plugin.strings.get("unknownCommand"),
					"%command%", args[0]));
	}

	private String getCommand(String inputCommand) {
		for (String command : BashMultiCommand.plugin.commandExecutor.commands
				.keySet().toArray(new String[0])) {
			if (inputCommand.equalsIgnoreCase(BashMultiCommand.plugin.strings
					.get(command)[0]))
				return command;
		}
		return null;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		ArrayList<String> matchedSubCommands = new ArrayList<String>();
		for (SubCommand command : BashMultiCommand.plugin.commandExecutor.commands
				.values().toArray(new SubCommand[0])) {
			if (StringUtil.startsWithIgnoreCase(command.toString(), args[0])
					|| args.length < 0) {
				matchedSubCommands.add(command.toString());
			}
		}

		Collections.sort(matchedSubCommands, String.CASE_INSENSITIVE_ORDER);
		return matchedSubCommands;
	}

}
