package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class Help extends SubCommand {

	public Help() {
		super(BashMultiCommand.strings.get("commandHelp"),
				BashMultiCommand.strings.get("commandHelpShortDescription"),
				BashMultiCommand.strings.get("commandHelpLongDescription"));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!sender.hasPermission("bashmulticommand.command.help")) {
			sender.sendMessage(BashMultiCommand.strings.get("noPermission"));
			return;
		}

		if (args.length != 0)
			commandHelp(sender, args);
		else
			sender.sendMessage(BashMultiCommand.commandExecutor
					.getHelpMessage());
	}

	private void commandHelp(CommandSender sender, String[] args) {
		sender.sendMessage(BashMultiCommand.strings.get("chatTitle"));

		String command = getCommand(args[0]);
		if (command != null)
			sender.sendMessage(BashMultiCommand.commandExecutor.commands
					.get(command).longDescription);
		else
			sender.sendMessage(BashMultiCommand.strings.get("unknownCommand")
					.replaceAll("%command%", args[0]));
	}

	private String getCommand(String inputCommand) {
		for (String command : BashMultiCommand.commandExecutor.commands
				.keySet().toArray(new String[0])) {
			if (inputCommand.equalsIgnoreCase(BashMultiCommand.strings
					.get(command)))
				return command;
		}
		return null;
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		ArrayList<String> matchedSubCommands = new ArrayList<String>();
		for (SubCommand command : BashMultiCommand.commandExecutor.commands
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
