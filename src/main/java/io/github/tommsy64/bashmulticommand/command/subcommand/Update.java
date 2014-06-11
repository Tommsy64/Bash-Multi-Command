package io.github.tommsy64.bashmulticommand.command.subcommand;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import net.gravitydevelopment.updater.Updater;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

public class Update extends SubCommand {

	public Update() {
		super(BashMultiCommand.plugin.strings.get("commandUpdate"),
				BashMultiCommand.plugin.strings
						.get("commandUpdateShortDescription"),
				BashMultiCommand.plugin.strings
						.get("commandUpdateLongDescription"));
	}

	@Override
	public void onCommand(CommandSender sender, Command cmd, String alias,
			String[] args) {
		if (!sender.hasPermission("bashmulticommand.update")) {
			sender.sendMessage(BashMultiCommand.plugin.strings
					.get("noPermission"));
			return;
		}
		if (args.length < 1 || args[0].equalsIgnoreCase(this.commandName)) {
			BashMultiCommand.plugin.loadUpdator(Updater.UpdateType.DEFAULT);
			if (BashMultiCommand.plugin.updater.getResult() == Updater.UpdateResult.SUCCESS)
				sender.sendMessage(BashMultiCommand.plugin.strings
						.get("downloadSuccess"));
			else
				sender.sendMessage(BashMultiCommand.plugin.strings
						.get("downloadFailed"));
			return;
		}

		if (args[0].equalsIgnoreCase(BashMultiCommand.plugin.strings
				.get("check"))) {
			BashMultiCommand.plugin.loadUpdator(Updater.UpdateType.NO_DOWNLOAD);
			if (BashMultiCommand.plugin.updater.getResult() == Updater.UpdateResult.UPDATE_AVAILABLE)
				sender.sendMessage(BashMultiCommand.plugin.strings
						.get("newUpdate"));
			else
				sender.sendMessage(BashMultiCommand.plugin.strings
						.get("noNewUpdate"));
		} else
			sender.sendMessage(BashMultiCommand.plugin.strings.get(
					"incorrectArguments").replaceAll("%command%",
					this.commandName));
	}

	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd,
			String alias, String[] args) {
		ArrayList<String> matchedArgs = new ArrayList<String>();

		if (args[0].equalsIgnoreCase(this.commandName)
				|| args[0].equalsIgnoreCase(BashMultiCommand.plugin.strings
						.get("check")))
			return new ArrayList<String>();

		if (StringUtil.startsWithIgnoreCase(this.commandName, args[0])
				|| args.length < 1)
			matchedArgs.add(this.commandName);
		if (StringUtil.startsWithIgnoreCase(
				BashMultiCommand.plugin.strings.get("check"), args[0])
				|| args.length < 1)
			matchedArgs.add(BashMultiCommand.plugin.strings.get("check"));

		Collections.sort(matchedArgs, String.CASE_INSENSITIVE_ORDER);
		return matchedArgs;
	}
}
