package com.trafalcraft.meleGeneral.util;

import com.trafalcraft.client.api.SocketPlugin;
import com.trafalcraft.meleGeneral.Controler.ArenaControle;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;
import com.trafalcraft.meleGeneral.file.FileControler;

public class SocketPerso implements SocketPlugin {

	@Override
	public boolean checkJoin(String arg0, String arg1) {
		if(ArenaControle.contains(arg0)){
			if(ArenaControle.getArena(arg0).getStatus().equalsIgnoreCase("lobby")){
				if(ArenaControle.getArena(arg0).getNbrJoueur() < FileControler.getArena(arg0).getInt("maxplayer")){
					return true;
				}else{
					return false;
				}
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	@Override
	public void playerJoin(String arg0, String arg1, String arg2) {
		PlayerControle.addPlayer(arg0, arg1, true);
	}

}
