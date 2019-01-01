package net.exodon.security.listeners;

import java.util.List;
import java.util.Map;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerMoveEvent;

import net.exodon.security.ExodonSecurity;
import net.exodon.security.options.Options;

public class SecurityLogListener implements Listener {
	
	private Map<String, Integer> pins;
	private List<String> logginginPlayers;
	
	{
		pins = Options.getPinOptions().getPins();
		logginginPlayers = ExodonSecurity.getInstance().getLogginginPlayers();
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogInMove(PlayerMoveEvent event) {
		Player player = event.getPlayer();
		Location from = event.getFrom(), to = event.getTo();
		
		if(from.getBlockX() != to.getBlockX() || from.getBlockY() != to.getBlockY() || from.getBlockZ() != to.getBlockZ()) {
			if(logginginPlayers.contains(player.getName())) {
				event.setTo(from);
				if(!pins.containsKey(player.getName()))
					player.sendMessage("§7You must set a §a§lPIN§e. §7§o(/pin set <pin>)");
				else
					player.sendMessage("§cYou must login using /pin log <password>");
			}
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onLogInCommand(PlayerCommandPreprocessEvent event) {
		Player player = event.getPlayer();
		String command = event.getMessage();
		
		if(!command.startsWith("/pin") && logginginPlayers.contains(player.getName())) {
			event.setCancelled(true);
			if(!pins.containsKey(player.getName()))
				player.sendMessage("§7You must set a §a§lPIN§e. §7§o(/pin set <pin>)");
			else
				player.sendMessage("§cYou must login using /pin log <password>");
		}
	}

}
