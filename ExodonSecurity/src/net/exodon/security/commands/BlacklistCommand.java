package net.exodon.security.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.exodon.security.options.Options;
import net.exodon.security.utils.Permissions;

public class BlacklistCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cThis command is only executable by players.");
			return true;
		}
		
		if(!sender.hasPermission(Permissions.BLACKLIST_COMMAND.getPermission())) return true;
		
		Player player = (Player) sender;
		
		if(args.length != 1) {
			player.sendMessage("§c/blacklist <player>");
			return true;
		} else if(args.length == 1) {
			OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
			
			if(offline == null) {
				player.sendMessage("§cThe player "+args[0]+" doesn't exist!");
				return true;
			}
			
			Options.getBlacklistOptions().getBlacklist().add(offline.getUniqueId().toString());
			Bukkit.broadcastMessage("§4"+player.getName()+" §chas blacklisted §4"+offline.getName()+" §cfrom the network.");
			player.sendMessage("§4*§c Blacklisted by: §4 "+sender.getName());
			player.sendMessage("§4*§c Reason: Blacklisted");
			player.sendMessage("§4*§c Blacklisted user has been saved to the database §7(Contant owner for unban)");
			
			if(offline.isOnline())
				((Player) offline).kickPlayer("§cYour account is blacklisted from the Exodon Network. This type of punishment cannot be appealed.");

		}
		return false;
	}

}
