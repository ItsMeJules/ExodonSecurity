package net.exodon.security.manager;

import net.exodon.security.ExodonSecurity;
import net.exodon.security.commands.BlacklistCommand;
import net.exodon.security.commands.IPResetCommand;
import net.exodon.security.commands.PinCommand;

public class CommandManager {
	
	private ExodonSecurity plugin;

	public CommandManager(ExodonSecurity plugin) {
		this.plugin = plugin;
	}
	
	public void registerCommands() {
		plugin.getCommand("blacklist").setExecutor(new BlacklistCommand());
		plugin.getCommand("ipreset").setExecutor(new IPResetCommand());
		plugin.getCommand("pin").setExecutor(new PinCommand());
	}

}
