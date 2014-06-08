package io.github.tommsy64.bashmulticommand;

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

	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = false)
	public void onLogin(PlayerLoginEvent event) {
		UUIDManager.addCachedPlayer(event.getPlayer());
	}
}
