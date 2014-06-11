package io.github.tommsy64.bashmulticommand.command;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.Utils;
import io.github.tommsy64.bashmulticommand.command.subcommand.About;
import io.github.tommsy64.bashmulticommand.command.subcommand.Help;
import io.github.tommsy64.bashmulticommand.command.subcommand.Reload;
import io.github.tommsy64.bashmulticommand.command.subcommand.SubCommand;
import io.github.tommsy64.bashmulticommand.command.subcommand.Toggle;
import io.github.tommsy64.bashmulticommand.command.subcommand.Update;
import io.github.tommsy64.bashmulticommand.config.Config;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.util.StringUtil;

public class BMCCommandExecutor implements TabExecutor {

	public final Map<String, SubCommand> commands = new HashMap<>();

	private String[] helpMessage;

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		boolean unkownCommand = true;
		if (!alias.equalsIgnoreCase(cmd.getAliases().get(0))) {
			args = new String[1];
			args[0] = "";
		}

		if (args.length == 0) {
			if (sender.hasPermission("bashmulticommand.command.help"))
				sender.sendMessage(getHelpMessage());
			else
				sender.sendMessage(BashMultiCommand.plugin.strings
						.get("noPermission"));
			return true;
		}

		for (String command : commands.keySet().toArray(new String[0])) {
			if (args[0].equalsIgnoreCase(BashMultiCommand.plugin.strings
					.get(command)) || commands.get(command).matchesAlias(alias)) {
				commands.get(command).onCommand(sender, cmd, alias,
						Utils.removeFirst(args));
				unkownCommand = false;
			}
		}

		if (unkownCommand)
			if (sender.hasPermission("bashmulticommand.command"))
				sender.sendMessage(BashMultiCommand.plugin.strings.get(
						"unknownCommand").replaceAll("%command%", args[0]));
			else
				sender.sendMessage(BashMultiCommand.plugin.strings
						.get("noPermission"));
		return true;
	}

	public void loadCommands() {
		commands.put("commandHelp", new Help());
		commands.put("commandToggle", new Toggle());
		commands.put("commandReload", new Reload());
		commands.put("commandAbout", new About());
		if (Config.autoUpdateEnabled)
			commands.put("commandUpdate", new Update());
	}

	public String[] getHelpMessage() {

		if (this.helpMessage != null)
			return this.helpMessage;
		else {
			List<String> newHelpMessage = new ArrayList<String>();

			newHelpMessage
					.add(BashMultiCommand.plugin.strings.get("chatTitle"));

			for (String command : commands.keySet().toArray(new String[0])) {
				newHelpMessage.add(ChatColor.DARK_AQUA + "/bmc "
						+ ChatColor.GRAY + commands.get(command).commandName
						+ ChatColor.WHITE + " - "
						+ commands.get(command).shortDescription);
			}
			this.helpMessage = newHelpMessage.toArray(new String[0]);
			return this.helpMessage;
		}
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		ArrayList<String> matchedSubCommands = new ArrayList<String>();
		for (SubCommand command : commands.values().toArray(new SubCommand[0])) {
			if (args[0].equalsIgnoreCase(command.toString()))
				return command.onTabComplete(sender, cmd, alias,
						Utils.removeFirst(args));

			if (StringUtil.startsWithIgnoreCase(command.toString(), args[0])
					|| args.length < 0) {
				matchedSubCommands.add(command.toString());
			}
		}

		Collections.sort(matchedSubCommands, String.CASE_INSENSITIVE_ORDER);
		return matchedSubCommands;
	}
}
