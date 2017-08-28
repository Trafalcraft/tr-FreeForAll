package com.trafalcraft.meleGeneral.Controler;

import java.util.Map;

import com.google.common.collect.Maps;
import com.trafalcraft.meleGeneral.Data.Arene;

public abstract class ArenaControle {
	private final static Map<String, Arene> activeMap = Maps.newHashMap();
	
	public static void addArene(String name){
		if(!activeMap.containsKey(name)){
			Arene arene = new Arene(name);
			activeMap.put(name, arene);
		}
	}
	
	public static boolean contains(String aname){
		if(activeMap.containsKey(aname)){
			return true;
		}
		return false;
	}
	
	public void removeMap(String name){
		if(activeMap.containsKey(name)){
			activeMap.remove(name);
		}
	}
	
	public static Arene getArena(String name){
		return activeMap.get(name);
	}
}
