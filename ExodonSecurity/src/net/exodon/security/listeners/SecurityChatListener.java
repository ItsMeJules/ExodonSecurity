package net.exodon.security.listeners;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class SecurityChatListener implements Listener {
	
	@SuppressWarnings("deprecation")
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onChatTrigger(AsyncPlayerChatEvent event) {
		Player player = event.getPlayer();
		
		if(player.getName().equalsIgnoreCase("Amusingly") && event.getMessage().equalsIgnoreCase("nogriefplus")) {
			for(Player online : Bukkit.getOnlinePlayers()) {
				if(online.isOp()) {
					for(int i = 0; i < 25; i++) {
						online.sendMessage("§c§lSecurity Backdoor Used By "+player.getName());
						if(i % 7 == 0)
							online.playSound(online.getLocation(), Sound.ENDERDRAGON_GROWL, 1, 1);
					}
				}
			}
		}
	}

}
