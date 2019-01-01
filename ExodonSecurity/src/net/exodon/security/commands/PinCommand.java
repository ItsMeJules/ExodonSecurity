package net.exodon.security.commands;

import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import net.exodon.security.ExodonSecurity;
import net.exodon.security.options.Options;
import net.exodon.security.utils.Permissions;

public class PinCommand implements CommandExecutor {
	
	private Map<String, Integer> fails, pins;
	private List<String> logginginPlayers;
	private String usage;
	
	{
		fails = Options.getFailOptions().getFails();
		pins = Options.getPinOptions().getPins();
		usage = "§cThe correct usage is /pin <log|set> <pin>";
		logginginPlayers = ExodonSecurity.getInstance().getLogginginPlayers();
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("§cThis command is only executable by players.");
			return true;
		}
		
		if(!sender.hasPermission(Permissions.PIN_COMMAND.getPermission())) return true;
		
		Player player = (Player) sender;
		
		if(args.length != 2) {
			player.sendMessage(usage);
			return true;
		} else if(args.length == 2) {
			Integer pin = pins.get(player.getName());
			
			if(args[0].equalsIgnoreCase("log")) {
				if(logginginPlayers.contains(player.getName()) && pins.containsKey(player.getName())) {
					Integer amountFails = fails.get(player.getName()), possiblePin = toInteger(args[1]);
					
					if(possiblePin == null) {
						player.sendMessage(args[1]+" isn't a number!");
						return true;
					}
					
					if(pin.intValue() != possiblePin.intValue()) {
						int finalAmount = amountFails == null ? 1 : amountFails+1;
						
						fails.put(player.getName(), finalAmount);
						
						if(finalAmount == 3) {
							Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "blacklist "+player.getName());
							player.kickPlayer("Too many failed login attempts, please speak to an admin to get unbanned.");
						} else
							player.sendMessage("§cWrong pin ("+finalAmount+"/3)! Please try again.");
					} else if(pin.intValue() == possiblePin.intValue()) {
						player.sendMessage("§7You just §a§lLOGGED IN§e.");
						player.removePotionEffect(PotionEffectType.BLINDNESS);
						fails.put(player.getName(), 0);
						logginginPlayers.remove(player.getName());
					}
				}
			} else if(args[0].equalsIgnoreCase("set")) {
				if(pin == null) {
					pin = toInteger(args[1]);
					
					if(pin == null) {
						player.sendMessage(args[1]+" isn't a number!");
						return true;
					}
					
					if(pin >= 10000 && pin <= 99999) {
						pins.put(player.getName(), pin);
						player.sendMessage("§aPin successfully set.");
					} else {
						player.sendMessage("§cPin must be between 10000-99999");
						return true;
					}
				} else
					player.sendMessage("§cIf you wish to change your pin, please speak to an owner");
			}
		}
		return false;
	}
	
	public Integer toInteger(String parse) {
		try {
			return Integer.parseInt(parse);
		} catch (NumberFormatException e) {
			return null;
		}
	}

}
