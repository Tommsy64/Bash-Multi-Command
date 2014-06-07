package io.github.tommsy64.bashmulticommand;

import net.milkbowl.vault.permission.Permission;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class BashMultiCommand extends JavaPlugin {

	public static BashMultiCommand plugin;
	private static Permission perm;
	public static Config config;

	@Override
	public void onEnable() {
		plugin = this;

		if (checkForVault())
			setUpPermissions();
		else
			getLogger().warning(
					"Vault not found! Using built in permission checks.");

		config = new Config();
		new ChatListener();
	}

	private boolean checkForVault() {
		if (getServer().getPluginManager().getPlugin("Vault") == null)
			return false;
		return true;
	}

	private void setUpPermissions() {
		RegisteredServiceProvider<Permission> rsp = BashMultiCommand.plugin
				.getServer().getServicesManager()
				.getRegistration(Permission.class);
		if (rsp != null)
			perm = rsp.getProvider();
	}

	public static boolean hasPermission(Player player, String permission) {
		if (perm != null)
			return perm.has(player, permission);
		else
			return player.hasPermission(permission);
	}

	public static boolean hasPermission(CommandSender sender, String permission) {
		if (!(sender instanceof Player))
			return true;
		else
			return hasPermission((Player) sender, permission);
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label,
			String[] args) {
		if (args[0].equalsIgnoreCase("reload")
				&& hasPermission(sender, "bashmulticommand.reload")) {
			sender.sendMessage(ChatColor.YELLOW + "Batch " + ChatColor.WHITE
					+ "Multi Command " + ChatColor.WHITE + "- Relaoded");
			onDisable();
			onEnable();
			return true;
		}

		if (args[0].equalsIgnoreCase("about")
				&& hasPermission(sender, "bashmulticommand.reload")) {
			sender.sendMessage("----- " + ChatColor.YELLOW + "Bash "
					+ ChatColor.DARK_AQUA + "Multi Command " + ChatColor.RESET
					+ "-----");
			sender.sendMessage(ChatColor.DARK_AQUA + "Seperator "
					+ ChatColor.WHITE + " - " + ChatColor.RED
					+ Config.separator);
			sender.sendMessage(ChatColor.DARK_AQUA + "Permission "
					+ ChatColor.WHITE + " - " + ChatColor.RED
					+ Config.permission);
			sender.sendMessage(ChatColor.DARK_AQUA + "AutoUpdate "
					+ ChatColor.WHITE + " - " + ChatColor.RED
					+ Config.autoUpdate);
			sender.sendMessage(ChatColor.DARK_AQUA + "Version "
					+ ChatColor.WHITE + "- Do /version " + ChatColor.DARK_AQUA
					+ getDescription().getName() + ChatColor.WHITE
					+ " for more info");
			return true;
		}

		sender.sendMessage("----- " + ChatColor.YELLOW + "Bash "
				+ ChatColor.DARK_AQUA + "Multi Command " + ChatColor.RESET
				+ "-----");
		// sender.sendMessage(ChatColor.AQUA + "/bmc help" + ChatColor.WHITE
		// +
		// "- Display more info for that command.");
		sender.sendMessage(ChatColor.DARK_AQUA + "/bmc " + ChatColor.GRAY
				+ "reload" + ChatColor.WHITE + "- Reloads " + ChatColor.YELLOW
				+ "Bash " + ChatColor.WHITE + "Multi Command");
		sender.sendMessage(ChatColor.DARK_AQUA + "/bmc " + ChatColor.GRAY
				+ "update" + ChatColor.WHITE + "- Updates " + ChatColor.YELLOW
				+ "Bash " + ChatColor.WHITE
				+ "Multi Command if new version is found");
		sender.sendMessage(ChatColor.DARK_AQUA + "/bmc about" + ChatColor.WHITE
				+ "- Displays some basic info");
		return true;
	}
}
