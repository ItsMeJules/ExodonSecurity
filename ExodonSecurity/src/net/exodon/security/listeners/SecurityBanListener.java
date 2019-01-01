package net.exodon.security.listeners;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class SecurityBanListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onTryBan(PlayerCommandPreprocessEvent event) {
		String command = event.getMessage();
		String name = event.getPlayer().getName();
		
		if(command.equalsIgnoreCase("/plugman disable Core"))
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban "+name+" 7d [Security Ban] Attempted to disable security plugin / core via plugman");
		else if(command.startsWith("/ban") && (command.endsWith("TellieTubbie") || command.endsWith("Amusingly") || command.endsWith("Game_Rider17")))
			Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "ban "+name+" 7d [Security Ban] Attempted to ban an owner.");
	}
	
}
