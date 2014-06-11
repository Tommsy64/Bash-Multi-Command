package io.github.tommsy64.bashmulticommand.listeners;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.PlayerManager;
import io.github.tommsy64.bashmulticommand.config.Config;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class ChatListener implements Listener {

	public ChatListener() {
		BashMultiCommand.plugin.getServer().getPluginManager()
				.registerEvents(this, BashMultiCommand.plugin);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onAsyncChat(AsyncPlayerChatEvent event) {
		if (!event.getMessage().contains(Config.separator)
				|| !event.getPlayer().hasPermission("bashmulticommand.use")
				|| !PlayerManager.isEnabled(event.getPlayer().getUniqueId()))
			return;
		event.setCancelled(true);
		splitSend(event.getPlayer(), event.getMessage());
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPreCommand(PlayerCommandPreprocessEvent event) {
		if (!event.getMessage().contains(Config.separator)
				|| !event.getPlayer().hasPermission("bashmulticommand.use")
				|| !PlayerManager.isEnabled(event.getPlayer().getUniqueId()))
			return;
		event.setCancelled(true);
		splitSend(event.getPlayer(), event.getMessage());
	}

	private void splitSend(Player player, String message) {
		for (String part : message.split(Config.separator)) {
			player.chat(part);
		}
	}
}