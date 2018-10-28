package br.com.finalcraft.fancychat.config;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class Config {
	
	File file;
	FileConfiguration config;
	private static Random random = new Random();


	/**
	 * Creates a new Config Object for the config.yml File of
	 * the specified Plugin
	 *
	 * @param  plugin The Instance of the Plugin, the config.yml is referring to
	 */
	public Config(Plugin plugin) {
		this.file = new File("plugins/" + plugin.getDescription().getName().replace(" ", "_") + "/config.yml");
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}

	/**
	 * Creates a new Directory if doest not exist at the targed directory
	 *
	 * @param  pathFolder The pathFolder of the newFile with the filename on it
	 */
	public void createDirectory(String pathFolder){

		if (!pathFolder.contains("/")){
			return;
		}

		String[] allFolders = pathFolder.split("/");
		pathFolder = allFolders[0];
		for (int i=1; i < allFolders.length - 1; i++){

			pathFolder = pathFolder + "/" + allFolders[i];

		}

		if (! (new File(pathFolder).exists())) {
			new File(pathFolder).mkdirs();
		}
	}

	/**
	 * Creates a new Config Object for the configName.yml File of
	 * the specified Plugin + copy default configs if asked to +
	 * a header information about EverNife Config Manager
	 *
	 * @param  plugin The Instance of the Plugin, the name.yml is referring to
	 */
	public Config(Plugin plugin, String configName, boolean copyDefaults, boolean header) {

		if (!new File("plugins/" + plugin.getDescription().getName().replace(" ", "_")).exists()) new File("plugins/" + plugin.getDescription().getName().replace(" ", "_")).mkdirs();

		String pathToFile = "plugins/" + plugin.getDescription().getName().replace(" ", "_") + "/" + configName;
		createDirectory(pathToFile);
		this.file = new File(pathToFile);
		this.config = YamlConfiguration.loadConfiguration(this.file);
		config.options().copyDefaults(true);
		if (header){
			this.config.options().header("--------------------------------------------------------" +
					"\n      ______ _             _ _____            __ _   " +
					"\n      |  ___(_)           | /  __ \\          / _| |  " +
					"\n      | |_   _ _ __   __ _| | /  \\/_ __ __ _| |_| |_ " +
					"\n      |  _| | | '_ \\ / _` | | |   | '__/ _` |  _| __|" +
					"\n      | |   | | | | | (_| | | \\__/\\ | | (_| | | | |_ " +
					"\n      \\_|   |_|_| |_|\\__,_|_|\\____/_|  \\__,_|_|  \\__|" +
					"\n  " +
					"\n  " +
					"\n  " +
					"\n              EverNife's Config Manager" +
					"\n" +
					"\n Plugin: " + plugin.getName().replace("EverNife","") +
					"\n Author: " + (plugin.getDescription().getAuthors().size() > 0 ? plugin.getDescription().getAuthors().get(0) : "Desconhecido") +
					"\n" +
					"\n N\u00E3o edite esse arquivo caso voc\u00EA n\u00E3o saiba usa-lo!" +
					"\n-------------------------------------------------------");
		}
		if (copyDefaults){
			config.options().copyDefaults(true);
		}
	}

	public Config(Plugin plugin, String configName) {
		this(plugin,configName,true,true);
	}
	public Config(Plugin plugin, String configName, boolean copyDefaults) {
		this(plugin,configName,copyDefaults,true);
	}
	
	/**
	 * Creates a new Config Object for the specified File
	 *
	 * @param  file The File for which the Config object is created for
	 */
	public Config(File file) {
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}
	
	/**
	 * Creates a new Config Object for the specified File and FileConfiguration
	 *
	 * @param  file The File to save to
	 * @param  config The FileConfiguration
	 */
	public Config(File file, FileConfiguration config) {
		this.file=file;
		this.config=config;
	}
	
	/**
	 * Creates a new Config Object for the File with in
	 * the specified FCLocationController
	 *
	 * @param  path The Path of the File which the Config object is created for
	 */
	public Config(String path) {
		this.file = new File(path);
		this.config = YamlConfiguration.loadConfiguration(this.file);
	}
	
	/**
	 * Returns the File the Config is handling
	 *
	 * @return      The File this Config is handling
	 */ 
	public File getFile() {
		return this.file;
	}
	
	/**
	 * Converts this Config Object into a plain FileConfiguration Object
	 *
	 * @return      The converted FileConfiguration Object
	 */ 
	public FileConfiguration getConfiguration() {
		return this.config;
	}
	
	protected void store(String path, Object value) {
		this.config.set(path, value);
	}
	
	/**
	 * Sets the Value for the specified Path
	 *
	 * @param  path The path in the Config File
	 * @param  value The Value for that Path
	 */
	public void setValue(String path, Object value) {
		if (value == null) {
			this.store(path, value);
			this.store(path + "_extra", null);
		}
		else if (value instanceof Long) {
			this.store(path, String.valueOf(value));
		}
		else if (value instanceof World) {
			this.store(path, ((World) value).getName());
		}
		else this.store(path, value);
	}
	
	/**
	 * Saves the Config Object to its File
	 */ 
	public void save() {
		try {
			config.save(file);
		} catch (IOException e) {
		}
	}
	
	/**
	 * Saves the Config Object to a File
	 * 
	 * @param  file The File you are saving this Config to
	 */ 
	public void save(File file) {
		try {
			config.save(file);
		} catch (IOException e) {
		}
	}

	/**
	 * Sets the Value for the specified Path 
	 * (IF the Path does not yet exist)
	 *
	 * @param  path The path in the Config File
	 * @param  value The Value for that Path
	 */
	public void setDefaultValue(String path, Object value) {
		if (!contains(path)) setValue(path, value);
	}

	/**
	 * Checks whether the Config contains the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      True/false
	 */ 
	public boolean contains(String path) {
		return config.contains(path);
	}
	
	/**
	 * Returns the Object at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Value at that Path
	 */ 
	public Object getValue(String path) {
		return config.get(path);
	}

	/**
	 * Returns the String at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The String at that Path
	 */
	public String getString(String path) {
		return config.getString(path);
	}
	public String getString(String path, String def) {
		return config.getString(path,def);
	}

	/**
	 * Returns the Integer at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Integer at that Path
	 */
	public int getInt(String path) {
		return config.getInt(path);
	}
	public int getInt(String path, int def) {
		return config.getInt(path,def);
	}

	/**
	 * Returns the Boolean at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Boolean at that Path
	 */
	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}
	public boolean getBoolean(String path,boolean def) {
		return config.getBoolean(path,def);
	}

	/**
	 * Returns the StringList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The StringList at that Path
	 */
	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	/**
	 * Returns the IntegerList at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The IntegerList at that Path
	 */ 
	public List<Integer> getIntList(String path) {
		return config.getIntegerList(path);
	}

	/**
	 * Returns the Float at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Float at that Path
	 */ 
	public Float getFloat(String path) {
		return Float.valueOf(String.valueOf(getValue(path)));
	}
	
	/**
	 * Returns the Long at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Long at that Path
	 */ 
	public Long getLong(String path) {
		return Long.valueOf(String.valueOf(getValue(path)));
	}
	public Long getLong(String path, long def) {
		if (!contains(path)){
			return def;
		}
		return getLong(path);
	}

	/**
	 * Returns the World at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The World at that Path
	 */ 
	public World getWorld(String path) {
		return Bukkit.getWorld(getString(path));
	}
	
	/**
	 * Returns the Double at the specified Path
	 *
	 * @param  path The path in the Config File
	 * @return      The Double at that Path
	 */
	public Double getDouble(String path) {
		return config.getDouble(path);
	}
	public Double getDouble(String path, double def) {
		return config.getDouble(path,def);
	}

	/**
	 * Returns all Sub-Paths in this Config
	 *
	 * @param  path The path in the Config File
	 * @return      All Sub-Paths of the specified Path
	 */ 
	public Set<String> getKeys(String path) {
		if (contains(path)){
			return config.getConfigurationSection(path).getKeys(false);
		}
		return Collections.emptySet();
	}
}
