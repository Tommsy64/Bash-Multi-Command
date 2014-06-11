package io.github.tommsy64.bashmulticommand.listeners;

import io.github.tommsy64.bashmulticommand.BashMultiCommand;
import io.github.tommsy64.bashmulticommand.uuid.UUIDManager;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class LoginListener implements Listener {

	public LoginListener() {
		BashMultiCommand.plugin.getServer().getPluginManager()
				.registerEvents(this, BashMultiCommand.plugin);
	}

	@EventHandler(priority = EventPriority.LOWEST)
	public void UUIDCache(PlayerLoginEvent event) {
		UUIDManager.addCachedPlayer(event.getPlayer());
		BashMultiCommand.plugin.getLogger().finer(
				"Added " + event.getPlayer().getName()
						+ " to the UUID cache with UUID "
						+ event.getPlayer().getUniqueId().toString());
	}
}
