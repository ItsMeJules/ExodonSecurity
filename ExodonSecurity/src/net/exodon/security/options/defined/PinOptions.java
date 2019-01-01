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

public class PinOptions extends Options implements OverwriteConfig {
	
	private FileConfiguration config;
	
	private Map<String, Integer> pins;

	public PinOptions() {
		super(ConfigManager.getPinConfig());
		overwriteConfigs.add(this);
		config = super.config.getConfig();
	}

	@Override
	public String init() {
		long started = System.currentTimeMillis();
		
		pins = new HashMap<>();
		
		for(String entry : config.getStringList("pins")) {
			String[] detail = entry.split(";");
			pins.put(detail[0], Integer.valueOf(detail[1]));
		}
		
		return "§aThe file 'pins.yml' has been successfully loaded in "+(System.currentTimeMillis()-started)+"ms";
	}
	
	@Override
	public void overWrite() {
		List<String> pins = new ArrayList<>();
		
		for(Entry<String, Integer> entry : this.pins.entrySet())
			pins.add(entry.getKey()+';'+entry.getValue());
		
		config.set("pins", pins);
		
		super.config.saveConfig();
	}

	public Map<String, Integer> getPins() {
		return pins;
	}

}
