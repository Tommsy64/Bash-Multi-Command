package io.github.tommsy64.bashmulticommand.listeners;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.PlayerManager;
import io.github.tommsy64.bashmulticommand.config.Config;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

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
		int max = checkPermission(event.getPlayer(), Config.permissionsChat);
		if (max == 0 || !event.getMessage().contains(Config.separator)
				|| !PlayerManager.isEnabled(event.getPlayer().getUniqueId()))
			return;
		event.setCancelled(true);
		splitSend(event.getPlayer(), event.getMessage(), max);
	}

	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGHEST)
	public void onPreCommand(PlayerCommandPreprocessEvent event) {
		int max = checkPermission(event.getPlayer(), Config.permissionsCommand);
		if (max == 0 || !event.getMessage().contains(Config.separator)
				|| !PlayerManager.isEnabled(event.getPlayer().getUniqueId()))
			return;
		event.setCancelled(true);
		splitSend(event.getPlayer(), event.getMessage(), max);
	}

	private int checkPermission(Player player, Map<String, Integer> perms) {
		int max = 0;
		Iterator<Entry<String, Integer>> itr = perms.entrySet().iterator();
		while (itr.hasNext()) {
			Entry<String, Integer> pairs = itr.next();
			if (player.hasPermission("bashmulticommand.use." + pairs.getKey()))
				if (max < pairs.getValue())
					max = pairs.getValue();
		}
		return max;
	}

	private void splitSend(Player player, String message, int max) {
		int i = 0;
		for (String part : message.split(Config.separator)) {
			if (i < max)
				player.chat(part);
			else
				return;
			i++;
		}
	}
}