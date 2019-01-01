package net.exodon.security;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import net.exodon.security.manager.CommandManager;
import net.exodon.security.manager.ConfigManager;
import net.exodon.security.manager.ListenerManager;
import net.exodon.security.options.Options;

public class ExodonSecurity extends JavaPlugin {
	
	private static ExodonSecurity instance;
	private List<String> logginginPlayers; 
	
	@Override
	public void onEnable() {
		long started = System.currentTimeMillis();
		sendConsoleMessage("====================§6[§3ENABLING§6]§r====================", true);
		registerManager();
		sendConsoleMessage("===============§6[§3ENABLE DONE ("+(System.currentTimeMillis()-started)+"ms)§6]§r===============", true);
		
	}

	@Override
	public void onDisable() {
		Options.disable();
	}
	
	public void registerManager() {
		instance = this;
		ConfigManager.loadConfigs();
		Options.initOptions();
		logginginPlayers = new ArrayList<>();
		new CommandManager(this).registerCommands();
		new ListenerManager(this).registerListener();
	}
	
	public void sendConsoleMessage(String message, boolean prefix) {
		Bukkit.getConsoleSender().sendMessage(message = prefix == true ? "§e[§dExodonSecurity§e]§r "+message : message);
	}
	
	public static ExodonSecurity getInstance() {
		return instance;
	}

	public String getIP(Player player) {
		String ip = player.getAddress().getHostName().replace("/", "");
		
		return ip; 
	}

	public List<String> getLogginginPlayers() {
		return logginginPlayers;
	}
	
	
}
