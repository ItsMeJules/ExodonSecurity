package net.exodon.security.options.defined;

import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import net.exodon.security.manager.ConfigManager;
import net.exodon.security.options.Options;
import net.exodon.security.options.OverwriteConfig;

public class BlacklistOptions extends Options implements OverwriteConfig {

	private FileConfiguration config;
	private List<String> blacklist;
	
	public BlacklistOptions() {
		super(ConfigManager.getBlacklistConfig());
		overwriteConfigs.add(this);
		config = super.config.getConfig();
	}

	@Override
	public String init() {
		long started = System.currentTimeMillis();
		
		blacklist = config.getStringList("blacklist");
		
		return "§aThe file 'blacklist.yml' has been successfully loaded in "+(System.currentTimeMillis()-started)+"ms";
	}
	
	@Override
	public void overWrite() {
		config.set("blacklist", blacklist);
		super.config.saveConfig();
	}
	
	public List<String> getBlacklist() {
		return blacklist;
	}

}
