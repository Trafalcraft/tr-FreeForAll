package com.trafalcraft.meleGeneral.file;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;

import com.google.common.collect.Maps;
import com.trafalcraft.meleGeneral.Main;

public class FileControler {
	
	private final static Map<String, YamlConfiguration> arenaList = Maps.newHashMap();
	
	public static void addFile(File DataFolder , String name){
		if(!arenaList.containsKey(name)){
			File file = new File( DataFolder.getPath()+"//arene//" , name+".yml");
			try {
				file.createNewFile();
				YamlConfiguration yc = YamlConfiguration.loadConfiguration(file);
			
				arenaList.put(name, yc);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void loadAllFile(){
		File f2 = new File(Main.getPlugin().getDataFolder()+"//arene//");
		for(String sF : f2.list()){
			if(sF.endsWith(".yml")){
				File f = new File(Main.getPlugin().getDataFolder()+"//arene//" , sF);
				arenaList.put(f.getName().replace(".yml", ""), YamlConfiguration.loadConfiguration(f));
				Bukkit.getLogger().info("Le fichier "+sF+" a bien été chargé");
			}
		}
	}
	
	public static boolean contains(String name){
		if(arenaList.containsKey(name)){
			return true;
		}
		return false;
	}
	
	public static void removeFile(String name){
		if(arenaList.containsKey(name)){
			File file = new File( Main.getPlugin().getDataFolder().getPath()+"//arene//" , name+".yml");
			file.delete();
			arenaList.remove(name);
			
		}
	}
	
	public static Map<String, YamlConfiguration> getListFile(){
		return arenaList;
	}
	
	public static Collection<YamlConfiguration> getAllFile(){
		return arenaList.values();
	}
	
	public static Collection<String> getAllName(){
		return arenaList.keySet();
	}
	
	public static YamlConfiguration getArena(String name){
			return arenaList.get(name);
	}
	
	public static void saveArena(String name){
		
		File file = new File(Main.getPlugin().getDataFolder().getPath()+"//arene", name+".yml");
		
		try {
			getArena(name).save(file);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}	
	
	public static void clear(){
		arenaList.clear();
	}
}
