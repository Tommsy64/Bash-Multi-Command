package io.github.tommsy64.bashmulticommand.command;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.PlayerManager;
import io.github.tommsy64.bashmulticommand.Utils;
import io.github.tommsy64.bashmulticommand.command.subcommand.About;
import io.github.tommsy64.bashmulticommand.command.subcommand.Help;
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
			if (PlayerManager.hasPermission(sender,
					"bashmulticommand.command.help"))
				sender.sendMessage(getHelpMessage());
			else
				sender.sendMessage(BashMultiCommand.strings.get("noPermission"));
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

		if (unkownCommand
				&& PlayerManager.hasPermission(sender,
						"bashmulticommand.command"))
			sender.sendMessage(BashMultiCommand.strings.get("unknownCommand")
					.replaceAll("%command%", args[0]));
		return true;
	}

	public void loadCommands() {
		commands.put("commandHelp", new Help());
		commands.put("commandToggle", new Toggle());
		commands.put("commandReload", new Reload());
		commands.put("commandAbout", new About());
	}

	public String[] getHelpMessage() {

		if (this.helpMessage != null)
			return this.helpMessage;
		else {
			List<String> newHelpMessage = new ArrayList<String>();

			newHelpMessage.add(BashMultiCommand.strings.get("chatTitle"));

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
		// TODO Auto-generated method stub
		return null;
	}

}
