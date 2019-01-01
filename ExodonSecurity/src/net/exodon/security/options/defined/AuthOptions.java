package net.exodon.security.options.defined;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.bukkit.configuration.file.FileConfiguration;

import net.exodon.security.manager.ConfigManager;
import net.exodon.security.options.Options;
import net.exodon.security.options.OverwriteConfig;

public class AuthOptions extends Options implements OverwriteConfig {
	
	private FileConfiguration config;
	
	private Map<String, String> ips;
	private List<String> resetQueue;

	public AuthOptions() {
		super(ConfigManager.getAuthConfig());
		overwriteConfigs.add(this);
		config = super.config.getConfig();
	}

	@Override
	public String init() {
		long started = System.currentTimeMillis();
		
		ips = new HashMap<>();
		
		for(String entry : config.getStringList("ips")) {
			String[] detail = entry.split(";");
			ips.put(detail[0], detail[1]);
		}
		
		resetQueue = config.getStringList("reset-queue");
		
		return "§aThe file 'auth.yml' has been successfully loaded in "+(System.currentTimeMillis()-started)+"ms";
	}

	@Override
	public void overWrite() {
		List<String> ips = new ArrayList<>();
		
		for(Entry<String, String> entry : this.ips.entrySet())
			ips.add(entry.getKey()+';'+entry.getValue());
		
		config.set("ips", ips);
		config.set("reset-queue", resetQueue);
		
		super.config.saveConfig();
	}

	public Map<String, String> getIPs() {
		return ips;
	}

	public List<String> getResetQueue() {
		return resetQueue;
	}

}
