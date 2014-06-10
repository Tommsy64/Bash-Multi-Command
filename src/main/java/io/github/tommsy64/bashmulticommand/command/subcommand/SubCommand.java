package io.github.tommsy64.bashmulticommand.command.subcommand;

import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

public abstract class SubCommand {
	public final String commandName;
	public final String shortDescription;
	public final String longDescription;
	public String[] aliases;

	public SubCommand(String commandName, String shortDescription,
			String longDescription) {
		this.commandName = commandName;
		this.shortDescription = shortDescription;
		this.longDescription = longDescription;
		this.aliases = new String[0];
	}

	public abstract void onCommand(CommandSender sender, Command cmd,
			String alias, String[] args);

	public abstract List<String> onTabComplete(CommandSender sender,
			Command cmd, String alias, String[] args);

	public boolean matchesAlias(String alias) {
		for (String string : aliases) {
			if (string.equalsIgnoreCase(alias))
				return true;
		}
		return false;
	}
}
