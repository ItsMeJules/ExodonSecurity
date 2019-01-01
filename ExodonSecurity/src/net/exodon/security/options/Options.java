package net.exodon.security.options;

import java.util.HashSet;
import java.util.Set;

import net.exodon.security.ExodonSecurity;
import net.exodon.security.options.defined.AuthOptions;
import net.exodon.security.options.defined.BlacklistOptions;
import net.exodon.security.options.defined.FailOptions;
import net.exodon.security.options.defined.PinOptions;
import net.exodon.security.utils.Config;

public abstract class Options {
	
	protected Config config;
	protected static Set<Options> options;
	protected static Set<OverwriteConfig> overwriteConfigs;
	
	private static AuthOptions authOptions;
	private static FailOptions failOptions;
	private static PinOptions pinOptions;
	private static BlacklistOptions blacklistOptions;
	
	static {
		options = new HashSet<>();
		overwriteConfigs = new HashSet<>();
	}
	
	public Options(Config config) {
		this.config = config;
	}
	
	public abstract String init();
	
	public Config getConfig() {
		return config;
	}
	
	public static void initOptions() {
		options.add(authOptions = new AuthOptions());
		options.add(failOptions = new FailOptions());
		options.add(pinOptions = new PinOptions());
		options.add(blacklistOptions = new BlacklistOptions());
		options.forEach(option -> ExodonSecurity.getInstance().sendConsoleMessage(option.init(), true));
	}
	
	public static AuthOptions getAuthOptions() {
		return authOptions;
	}
	
	public static FailOptions getFailOptions() {
		return failOptions;
	}
	
	public static PinOptions getPinOptions() {
		return pinOptions;
	}
	
	public static BlacklistOptions getBlacklistOptions() {
		return blacklistOptions;
	}
	
	public static Set<Options> getOptions() {
		return options;
	}

	public static void disable() {
		overwriteConfigs.forEach(over -> over.overWrite());
	}

}
