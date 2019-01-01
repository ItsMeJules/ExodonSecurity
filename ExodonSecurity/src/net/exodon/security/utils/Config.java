package net.exodon.security.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.exodon.security.ExodonSecurity;
import net.minecraft.util.org.apache.commons.io.FileUtils;

public class Config {

	private File file;
	private FileConfiguration config;
	private String path, fileName;
	private ExodonSecurity plugin;
	
	public static final String DEFAULT_PATH;
	private static Set<Config> configs;
	
	{
		plugin = ExodonSecurity.getInstance();
	}
	
	static { 
		DEFAULT_PATH = ExodonSecurity.getInstance().getDataFolder().toString()+"/";
		configs = new HashSet<>();
	}

	public Config(String fileName, boolean create) {
		this("", fileName, create);
	}
	
	public Config(String path, String fileName, boolean create) {
		this.path = DEFAULT_PATH+path;
		this.fileName = fileName;
		if(create)
			create(true);
	}
	
	public Config(File file) {
		path = file.getPath();
		fileName = file.getName();
		this.file = file;
		
		config = YamlConfiguration.loadConfiguration(file);
		configs.add(this);
	}

	public void create(boolean yaml) {
		file = new File(path, fileName);
		configs.add(this);
		
		if(yaml)
			config = YamlConfiguration.loadConfiguration(file);
	}
	
    public void setDefaultContent(String resourcePath, boolean overwrite, String outPutFile, boolean saveOutPutFile) {
        if(resourcePath == null || resourcePath.equals("")) throw new IllegalArgumentException("ResourcePath cannot be null or empty");

        resourcePath = resourcePath.replace('\\', '/');
        InputStream in = plugin.getResource(resourcePath);

        if(in == null) throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + file);

        file = new File(path, resourcePath);
        int lastIndex = resourcePath.lastIndexOf('/');
        File outDir = new File(path, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));

        if(!outDir.exists()) outDir.mkdirs();

        try {
            if(!file.exists())
            	write(in, file);
            else if(overwrite) {
            	if(saveOutPutFile)
            		FileUtils.copyFile(file, new File(path, outPutFile));
            	write(in, file);
            }
        } catch(IOException ex) {
        	plugin.getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + file, ex);
        }
    }
    
    private void write(InputStream in, File file) throws FileNotFoundException {
        try {
        	OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            
            int len;
            while((len = in.read(buf)) > 0) 
                out.write(buf, 0, len);
            
            out.close();
            in.close();
        } catch (IOException e) {
        	plugin.getLogger().log(Level.SEVERE, "Could not save " + file.getName() + " to " + file, e);
		}
    }
	
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
	}
	
	public void saveConfig() {
		try {
			config.save(file);
			configs.add(this);
		} catch (IOException e) {
			plugin.getLogger().log(Level.SEVERE, "Impossible to save "+file.getName(), e);
		}
	}
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	public boolean exists() {
		return file.exists();
	}
	
	public String getName() {
		return fileName;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean isFileCreated() {
		return file != null;
	}
	
	public boolean isYamlCreated() {
		return config != null;
	}
	
	public void remove(String key, boolean save) {
		config.set(key, null);
		if(save) {
			saveConfig();
			reloadConfig();
		}
	}

	public static Config fromFile(File file) {
		for(Config config : configs) {
			if(config.getFile().equals(file))
				return config;
		}
		return new Config(file);
	}
	
	public static Config fromName(String name) {
		for(Config config : configs) {
			if(config.getName().equalsIgnoreCase(name))
				return config;
		}
		return null;
	}

	public static Set<Config> getConfigs() {
		return configs;
	}
}
