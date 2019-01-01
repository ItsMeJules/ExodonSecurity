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

public class FailOptions extends Options implements OverwriteConfig {
	
	private FileConfiguration config;

	private Map<String, Integer> fails;
	
	public FailOptions() {
		super(ConfigManager.getFailsConfig());
		overwriteConfigs.add(this);
		config = super.config.getConfig();
	}

	@Override
	public String init() {
		long started = System.currentTimeMillis();
		
		fails = new HashMap<>();
		
		for(String entry : config.getStringList("fails")) {
			String[] detail = entry.split(";");
			fails.put(detail[0], Integer.valueOf(detail[1]));
		}
		
		return "§aThe file 'fails.yml' has been successfully loaded in "+(System.currentTimeMillis()-started)+"ms";
	}

	@Override
	public void overWrite() {
		List<String> fails = new ArrayList<>();
		
		for(Entry<String, Integer> entry : this.fails.entrySet())
			fails.add(entry.getKey()+';'+entry.getValue());
	
		config.set("fails", fails);
		super.config.saveConfig();
	}

	public Map<String, Integer> getFails() {
		return fails;
	}

}
