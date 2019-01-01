package net.exodon.security.utils;

public enum Permissions {
	
	AUTHENTIFIATION("exodonsecurity.authentification"), 
	LOGIN("exodonsecurity.login"), 
	PIN_COMMAND("exodonsecurity.command.pin"),
	BLACKLIST_COMMAND("exodonsecurity.command.blacklist"),
	IPRESET_COMMAND("exodonsecurity.command.ipreset");
	
	private String permission;

	private Permissions(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
}
