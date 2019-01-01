package net.exodon.security.manager;

import net.exodon.security.utils.Config;

public class ConfigManager {
	
	private static Config pinConfig, failsConfig, notesConfig, reclaimConfig, blacklistConfig, authConfig;
	
	public static void loadConfigs() {
		pinConfig = new Config("pin.yml", true);
		failsConfig = new Config("fails.yml", true);
		notesConfig = new Config("notes.yml", true);
		reclaimConfig = new Config("reclaim.yml", true);
		blacklistConfig = new Config("blacklist.yml", true);
		authConfig = new Config("auth.yml", true);
		
		
		pinConfig.saveConfig();
		failsConfig.saveConfig();
		notesConfig.saveConfig();
		reclaimConfig.saveConfig();
		blacklistConfig.saveConfig();
		authConfig.saveConfig();
	}
	
	public static Config getPinConfig() {
		return pinConfig;
	}

	public static Config getFailsConfig() {
		return failsConfig;
	}

	public static Config getNotesConfig() {
		return notesConfig;
	}

	public static Config getReclaimConfig() {
		return reclaimConfig;
	}
	
	public static Config getBlacklistConfig() {
		return blacklistConfig;
	}
	
	public static Config getAuthConfig() {
		return authConfig;
	}

}
