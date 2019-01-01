package net.exodon.security.commands;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.exodon.security.options.Options;
import net.exodon.security.utils.Permissions;

public class IPResetCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cThis command is only executable by players.");
			return true;
		}
		
		if(!sender.hasPermission(Permissions.IPRESET_COMMAND.getPermission())) return true;
		
		Player player = (Player) sender;

		if(args.length != 1) {
			player.sendMessage("§cWrong usage: /ipreset <IGN>");
			return true;
		} else if(args.length == 1) {
			OfflinePlayer offline = Bukkit.getOfflinePlayer(args[0]);
			
			if(offline == null) {
				player.sendMessage("§cThe player "+args[0]+" doesn't exist!");
				return true;
			}
			
			player.sendMessage("§aYou have reset "+offline.getName()+"'s IP!");
			Options.getAuthOptions().getResetQueue().add(offline.getName());
		}
		return false;
	}

}
