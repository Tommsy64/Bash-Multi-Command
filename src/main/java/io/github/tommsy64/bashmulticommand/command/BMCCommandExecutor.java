package io.github.tommsy64.bashmulticommand.command;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.Utils;
import io.github.tommsy64.bashmulticommand.command.subcommand.About;
import io.github.tommsy64.bashmulticommand.command.subcommand.Reload;
import io.github.tommsy64.bashmulticommand.command.subcommand.SubCommand;
import io.github.tommsy64.bashmulticommand.command.subcommand.Toggle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;

public class BMCCommandExecutor implements TabExecutor {

	private Map<String, SubCommand> commands = new HashMap<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		boolean unkownCommand = true;
		
		if (!alias.equalsIgnoreCase(cmd.getAliases().get(0))) {
			args = new String[1];
			args[0] = "";
		}

		if (args.length == 0) {
			sender.sendMessage(getHelpMessage());
			return true;
		}

		for (String command : commands.keySet().toArray(new String[0])) {
			if (args[0].equalsIgnoreCase(BashMultiCommand.strings.get(command))
					|| commands.get(command).matchesAlias(alias)) {
				commands.get(command).onCommand(sender, cmd, alias,
						Utils.removeFirst(args));
				unkownCommand = false;
			}
		}

		if (unkownCommand)
			sender.sendMessage(BashMultiCommand.strings.get("unknownCommand")
					.replaceAll("%command%", args[0]));
		return true;
	}

	public void loadCommands() {
		commands.put("commandAbout", new About());
		commands.put("commandReload", new Reload());
		commands.put("commandToggle", new Toggle());
	}

	public String[] getHelpMessage() {
		List<String> helpMessage = new ArrayList<String>();

		helpMessage.add(BashMultiCommand.strings.get("chatTitle"));

		for (String command : commands.keySet().toArray(new String[0])) {
			helpMessage.add(ChatColor.DARK_AQUA + "/bmc " + ChatColor.GRAY
					+ commands.get(command).commandName + ChatColor.WHITE
					+ " - " + commands.get(command).shortDescription);
		}
		return helpMessage.toArray(new String[0]);
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		// TODO Auto-generated method stub
		return null;
	}

}
