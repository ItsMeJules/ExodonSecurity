package net.exodon.security.manager;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;

import net.exodon.security.ExodonSecurity;
import net.exodon.security.listeners.SecurityBanListener;
import net.exodon.security.listeners.SecurityChatListener;
import net.exodon.security.listeners.SecurityJoinListener;
import net.exodon.security.listeners.SecurityLogListener;

public class ListenerManager {
	
	private ExodonSecurity plugin;
	private PluginManager pm;
	
	public ListenerManager(ExodonSecurity plugin) {
		this.plugin = plugin;
		this.pm = Bukkit.getPluginManager();
	}
	
	public void registerListener() {
		pm.registerEvents(new SecurityBanListener(), plugin);
		pm.registerEvents(new SecurityChatListener(), plugin);
		pm.registerEvents(new SecurityJoinListener(), plugin);
		pm.registerEvents(new SecurityLogListener(), plugin);
	}

}
