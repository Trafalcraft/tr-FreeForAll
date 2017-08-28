package com.trafalcraft.meleGeneral.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.trafalcraft.meleGeneral.Leave;
import com.trafalcraft.meleGeneral.Main;
import com.trafalcraft.meleGeneral.Controler.ArenaControle;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;

public class BungeeCord implements PluginMessageListener, Listener {
	
	private void move(String somedata){
		String nPlayer = somedata.split(">")[0];
		String nArene = somedata.split(">")[2];
		//Player p = Bukkit.getPlayer(nPlayer);
		
		PlayerControle.addPlayer(nPlayer, nArene, true);
	}
	
	@Override
	public void onPluginMessageReceived(String channel, Player arg1, byte[] arg2) {
		ByteArrayDataInput in = ByteStreams.newDataInput(arg2);
		String subChannel = in.readUTF();
		String somedata = "";
		if(subChannel.equalsIgnoreCase("tr-minigames.tominigames")){
			//System.out.println("[Debug]>"+"Reception message pour un mini-jeux");
			short len = in.readShort();
			byte[] msgbytes = new byte[len];
			in.readFully(msgbytes);

			DataInputStream msgin = new DataInputStream(new ByteArrayInputStream(msgbytes));

			try{
				somedata = msgin.readUTF();
				System.out.println("[Debug]>"+"somedata:"+somedata);
				if(CheckJoin(somedata) == true){
						final String someDataScheduled = somedata;
						Bukkit.getScheduler().runTaskLater(Main.getPlugin(), new Runnable() {
							
							@Override
							public void run() {
								move(someDataScheduled);
							}
						}, 40L);
					}else{
						ByteArrayDataOutput out = ByteStreams.newDataOutput();//initialisation "out"
						out.writeUTF("Forward");
						out.writeUTF("ALL");
						out.writeUTF("tr-minigames.tolobby");
						
						ByteArrayOutputStream msgbytes2 = new ByteArrayOutputStream();//util pour envoyé les message 
						DataOutputStream msgout = new DataOutputStream(msgbytes2);// util pour envoyé les message
						String info="";
						if(somedata.split(">").length>=3){
							info = "false:"+somedata.split(">")[1]+">"+somedata.split(">")[2];//message
						}else{
							Bukkit.getLogger().warning("Erreur: "+somedata);
						}
						msgout.writeUTF(info);//ajout du message  ( je sais pas comment ^^)
						
						
						out.writeShort(msgbytes2.toByteArray().length); // taille du texte a envoyé
						out.write(msgbytes2.toByteArray());	//conversion en list byt (surement pour reduire la taille)
						Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());//envoie du message a l'autre serveur 
						//( il faut un joueur de connecter sur le serveur de reception mais sa marche si le joueur est send juste après
						
						return;
					}
			}catch(IOException e){
				ByteArrayDataOutput out = ByteStreams.newDataOutput();//initialisation "out"
				out.writeUTF("Forward");
				out.writeUTF("ALL");
				out.writeUTF("tr-minigames.tolobby");
				
				ByteArrayOutputStream msgbytes2 = new ByteArrayOutputStream();//util pour envoyé les message 
				DataOutputStream msgout = new DataOutputStream(msgbytes2);// util pour envoyé les message
				String info="";
				if(somedata.split(">").length>=3){
					info = "false:"+somedata.split(">")[1]+">"+somedata.split(">")[2];//message
				}else{
					Bukkit.getLogger().warning("Erreur: "+somedata);
				}
				try {
					msgout.writeUTF(info);
				} catch (IOException e1) {
					e1.printStackTrace();
				}//ajout du message  ( je sais pas comment ^^)

				
				out.writeShort(msgbytes2.toByteArray().length); // taille du texte a envoyé
				out.write(msgbytes2.toByteArray());	//conversion en list byt (surement pour reduire la taille)
				Bukkit.getServer().sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());//envoie du message a l'autre serveur 
				//( il faut un joueur de connecter sur le serveur de reception mais sa marche si le joueur est send juste après
				e.printStackTrace();
				
				return;
			}
			
		}
	}
	
	private boolean CheckJoin(String someData){
		if(someData.split(">").length >= 3){
			try{
				//String nPlayer = someData.split(">")[0];
				String nArene = someData.split(">")[2];
				if(ArenaControle.contains(nArene)){
					PlayerControle.addPlayer(null, nArene,true);
					return true;
				}else{
					return false;
					//p.sendMessage("§3§lSpleef> §b" + "vous devez rentrer le nom d'un arene existante");
				}
				
			}catch(ArrayIndexOutOfBoundsException e){
				return false;
			}
		}else{
			return false;
		}
	}
	
	public static void sendPlayerToHub(Player p) {
		System.out.println("success");
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
		out.writeUTF("jeux");
		if (Bukkit.getOnlinePlayers().size() > 0) {
			p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
		}
		Leave.leave(p);
	}
	
	public static void sendOtherPlayerToHub(String name, Player p) {
		final ByteArrayDataOutput out = ByteStreams.newDataOutput();
		out.writeUTF("Connect");
//		out.writeUTF(name);
		out.writeUTF("jeux");
		if (Bukkit.getOnlinePlayers().size() > 0) {
			p.sendPluginMessage(Main.getPlugin(), "BungeeCord", out.toByteArray());
		}
	}
}
