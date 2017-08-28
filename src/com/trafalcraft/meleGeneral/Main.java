package com.trafalcraft.meleGeneral;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.inventivetalent.bossbar.BossBarAPI;
import org.inventivetalent.bossbar.BossBarAPI.Color;
import org.inventivetalent.bossbar.BossBarAPI.Property;
import org.inventivetalent.bossbar.BossBarAPI.Style;

import com.trafalcraft.client.api.SocketApi;
import com.trafalcraft.meleGeneral.Controler.ArenaControle;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;
import com.trafalcraft.meleGeneral.file.FileControler;
import com.trafalcraft.meleGeneral.pannel.CreateAreneList;
import com.trafalcraft.meleGeneral.pannel.InventoryClickListener;
import com.trafalcraft.meleGeneral.util.BungeeCord;
import com.trafalcraft.meleGeneral.util.SocketPerso;

import net.md_5.bungee.api.chat.TextComponent;

public class Main extends JavaPlugin implements Listener{
	
	private static JavaPlugin plugin;
	
	public void onEnable(){
		plugin = this;
		
		Bukkit.getServer().getPluginManager().registerEvents(new PlayerListener(),this);
		Bukkit.getServer().getPluginManager().registerEvents(new InventoryClickListener(),this);

		SocketApi.registerPlugin("mg", new SocketPerso());
		
		//instance pour envoyé des messages
		this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		//instance pour recevoir des messages
		this.getServer().getMessenger().registerIncomingPluginChannel(this, "BungeeCord", new BungeeCord());

		getPlugin().getConfig().options().copyDefaults(true);
		
		File d = new File(getPlugin().getDataFolder().getPath()+"//arene");
		
		if(!(d.exists())){
			d.mkdir();
			d.mkdirs();
		}
		
		FileControler.loadAllFile();
		
		for(String s : FileControler.getAllName()){
			if(FileControler.getArena(s).getString("statut").equalsIgnoreCase("on")){
				if(SecureConfig.securiteConfig(s) == true){
					ArenaControle.addArene(s);
					Bukkit.getLogger().info("arene " + s + " créer");
				}else{
					Bukkit.getLogger().warning("l'arene " + s + " est mal configurér");
				}
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		Player p = (Player) sender;
		
		if(command.getName().equalsIgnoreCase("melegeneral")){
			if(args.length != 0){
//----------------------------------------------------------------------------------------------------------------------------------------------------				
				if(args[0].equalsIgnoreCase("create")){
					if(p.isOp()){
						if(!(PlayerControle.contains(p.getName()))){
							if(args.length == 2){
								if(!(FileControler.contains(args[1]))){
									FileControler.addFile(getPlugin().getDataFolder(), args[1]);
									FileControler.getArena(args[1]).set("statut", "off");
									FileControler.getArena(args[1]).set("name", args[1]);
									FileControler.getArena(args[1]).set("world", p.getWorld().getName());
									FileControler.saveArena(args[1]);
									p.sendMessage("§3§lPVP> §b" + "arène créée");
								}else{
									p.sendMessage("§3§lPVP> §b" + "cette arène existe deja");
								}
							}else{
								p.sendMessage("§3§lPVP> §b" + "commande incorrecte");
							}
						}else{
							p.sendMessage("§3§lPVP> §b" + "vous ne pouvez pas configurer une arène pendant une game");
						}
					}else{
						p.sendMessage("§3§lPVP> §b" + "vous n'avez pas le droit d'utiliser cette commande");
					}					
				}else if(FileControler.contains(args[0])){
					if(p.isOp()){
						if(args.length == 4){
							if(args[1].equalsIgnoreCase("set")){
								if(!(PlayerControle.contains(p.getName()))){
//----------------------------------------------------------------------------------------------------------------------------------------------------								
									if(args[2].equalsIgnoreCase("maxplayer")){
										try{
										
											Integer max1 = Integer.valueOf(args[3]);
											FileControler.getArena(args[0]).set("maxplayer", max1);
											FileControler.saveArena(args[0]);
											p.sendMessage("§3§lPVP> §b" + "nombre de joueurs maximum enregistré");
										
										}catch(NumberFormatException e){
											p.sendMessage("§3§lPVP> §b" + "vous devez entrer un nombre");
										}
//----------------------------------------------------------------------------------------------------------------------------------------------------
									}else if(args[2].equalsIgnoreCase("time")){
										try{
										
											Integer temps = Integer.valueOf(args[3]);
											FileControler.getArena(args[0]).set("temps", temps);
											FileControler.saveArena(args[0]);
											p.sendMessage("§3§lPVP> §b" + "temps maximum enregistré");
										
										}catch(NumberFormatException e){
											p.sendMessage("§3§lPVP> §b" + "vous devez entrer un nombre");
										}
//----------------------------------------------------------------------------------------------------------------------------------------------------
									}else if(args[2].equalsIgnoreCase("maxheal")){
										try{
										
											Integer vie = Integer.valueOf(args[3]);
											FileControler.getArena(args[0]).set("nbrvie", vie);
											FileControler.saveArena(args[0]);
											p.sendMessage("§3§lPVP> §b" + "nombre maximum de vie enregistré");
										
										}catch(NumberFormatException e){
											p.sendMessage("§3§lPVP> §b" + "vous devez entrer un nombre");
										}
//----------------------------------------------------------------------------------------------------------------------------------------------------
									}else if(args[2].equalsIgnoreCase("statut")){
										if(args[3].equalsIgnoreCase("on") || args[3].equalsIgnoreCase("off")){
											if(SecureConfig.securiteConfig(args[0]) == true){
												FileControler.getArena(args[0]).set("statut", args[3]);
												FileControler.saveArena(args[0]);
											
												ArenaControle.addArene(args[0]);
												
												p.sendMessage("statut enregistré");
											}else{
												p.sendMessage("§3§lPVP> §b" + "la partie est mal configurée");
											}	
										
										}else{
											p.sendMessage("§3§lPVP> §b" + "vous devez entrer on ou off");
										}
									}else{
										p.sendMessage("argument invalide");
									}
								}else{
									p.sendMessage("§3§lPVP> §b" + "vous ne pouvez pas configurer une arène pendant une game");
								}
							}else if(args[1].equalsIgnoreCase("delete")){
//----------------------------------------------------------------------------------------------------------------------------------------------------
								if(!(PlayerControle.contains(p.getName()))){
									if(args[2].equalsIgnoreCase("classe")){
										if(getConfig().contains("classe." + args[3])){
											FileControler.getArena(args[0]).set("classe." + args[3], null);
										}else{
											p.sendMessage("§3§lPVP> §b" + "cette classe n'existe pas");
										}
									}else{
										p.sendMessage("§3§lPVP> §b" + "commande invalide");
									}
								}else{
									p.sendMessage("§3§lPVP> §b" + "vous ne pouvez pas configurer une arène pendant une game");
								}
							}else{
								p.sendMessage("§3§lPVP> §b" + "commande incorrecte");
							}
						}else if(args.length == 3){
							
							if(args[1].equalsIgnoreCase("set")){
								if(!(PlayerControle.contains(p.getName()))){
//----------------------------------------------------------------------------------------------------------------------------------------------------
									if(args[2].equalsIgnoreCase("spawn")){
									
										FileControler.getArena(args[0]).set("nbrspawn", FileControler.getArena(args[0]).getInt("nbrspawn") + 1);
										int nbrspawn = FileControler.getArena(args[0]).getInt("nbrspawn");
										FileControler.getArena(args[0]).set("spawn." + nbrspawn + ".x", p.getLocation().getX());
										FileControler.getArena(args[0]).set("spawn." + nbrspawn + ".y", p.getLocation().getY());
										FileControler.getArena(args[0]).set("spawn." + nbrspawn + ".z", p.getLocation().getZ());
										FileControler.getArena(args[0]).set("spawn." + nbrspawn + ".yaw", p.getLocation().getYaw());
										FileControler.getArena(args[0]).set("spawn." + nbrspawn + ".pitch", p.getLocation().getPitch());
										FileControler.saveArena(args[0]);
									
										p.sendMessage("§3§lPVP> §b" + "spawn numero " + nbrspawn + " enregistré");
//----------------------------------------------------------------------------------------------------------------------------------------------------
									}else if(args[2].equalsIgnoreCase("lobby")){
									
										FileControler.getArena(args[0]).set("lobby.x", p.getLocation().getX());
										FileControler.getArena(args[0]).set("lobby.y", p.getLocation().getY());
										FileControler.getArena(args[0]).set("lobby.z", p.getLocation().getZ());
										FileControler.getArena(args[0]).set("lobby.yaw", p.getLocation().getYaw());
										FileControler.getArena(args[0]).set("lobby.pitch", p.getLocation().getPitch());
										FileControler.saveArena(args[0]);
									
										p.sendMessage("§3§lPVP> §b" + "lobby enregisté");
//----------------------------------------------------------------------------------------------------------------------------------------------------
									}else if(args[2].equalsIgnoreCase("spec")){
										FileControler.getArena(args[0]).set("spec.x", p.getLocation().getX());
										FileControler.getArena(args[0]).set("spec.y", p.getLocation().getY());
										FileControler.getArena(args[0]).set("spec.z", p.getLocation().getZ());
										FileControler.getArena(args[0]).set("spec.yaw", p.getLocation().getYaw());
										FileControler.getArena(args[0]).set("spec.pitch", p.getLocation().getPitch());
										FileControler.saveArena(args[0]);
									
										p.sendMessage("§3§lPVP> §b" + "point d'apparition des spectateurs enregistré");
									}else if(args[2].equalsIgnoreCase("eventlobby")){
										
										FileControler.getArena(args[0]).set("eventlobby.x", p.getLocation().getX());
										FileControler.getArena(args[0]).set("eventlobby.y", p.getLocation().getY());
										FileControler.getArena(args[0]).set("eventlobby.z", p.getLocation().getZ());
										FileControler.getArena(args[0]).set("eventlobby.yaw", p.getLocation().getYaw());
										FileControler.getArena(args[0]).set("eventlobby.pitch", p.getLocation().getPitch());
										FileControler.saveArena(args[0]);
										
										p.sendMessage("§3§lPVP> §b" + "le lobby des events de l'arène " + args[0] + " a bien été enregistré");
									}else{
										p.sendMessage("§3§lPVP> §b" + "commande invalide");
									}
								}else{
									p.sendMessage("§3§lPVP> §b" + "vous ne pouvez pas configurer une arène pendant une game");
								}
							}else if(args[1].equalsIgnoreCase("delete")){
//----------------------------------------------------------------------------------------------------------------------------------------------------
								if(args[2].equalsIgnoreCase("spawn")){
									if(!(PlayerControle.contains(p.getName()))){
									
										int nbrspawn = FileControler.getArena(args[0]).getInt("nbrspawn");
									
										if(nbrspawn > 0){
										
											FileControler.getArena(args[0]).set("spawn." + nbrspawn , null);
											FileControler.getArena(args[0]).set("nbrspawn", (nbrspawn - 1));
											FileControler.saveArena(args[0]);
											p.sendMessage("§3§lPVP> §b" + "spawn numero " + nbrspawn + " detruit");
									
										}else{
											p.sendMessage("§3§lPVP> §b" + "il n'y a pas de spawn configuré");
										}	
									}else{
										p.sendMessage("§3§lPVP> §b" + "vous ne pouvez pas configurer une arène pendant une game");
									}
								}else{
									p.sendMessage("§3§lPVP> §b" + "commande invalide");
								}
								
							}else{
								p.sendMessage("§3§lPVP> §b" + "commande invalide veuillez entrer set ou delete");
							}
						}else{
							p.sendMessage("§3§lPVP> §b" + "la commande est incomplète");
						}
					}else{
						p.sendMessage("§3§lPVP> §b" + "vous n'avez pas le droit d'utiliser cette commande");
					}
//----------------------------------------------------------------------------------------------------------------------------------------------------
				}else if(args[0].equalsIgnoreCase("join")){
					if(args.length == 2){
						if(ArenaControle.contains(args[1])){
							PlayerControle.addPlayer(p.getName(), args[1], false);
						}
					}else{
						p.sendMessage("§3§lPVP> §b" + "comande incomplète");
					}
				}else if(args[0].equalsIgnoreCase("leave")){
					BungeeCord.sendPlayerToHub(p);
				}else if(args[0].equalsIgnoreCase("fstart")){
					if(PlayerControle.contains(p.getName())){
						
						String arene = PlayerControle.getJoueur(p.getName()).getArene();
						
						if(ArenaControle.getArena(arene).getStatus().equalsIgnoreCase("lobby")){
							if(ArenaControle.getArena(arene).getTaskLobby() != 0){
								
								ArenaControle.getArena(arene).setTemps(5);
								
								for(Player pl : ArenaControle.getArena(arene).getPlayerList()){
									BossBarAPI.removeAllBars(pl);
									BossBarAPI.addBar(pl, new TextComponent("§b" + "mélée génerale"), Color.BLUE, Style.PROGRESS, (float)5/30, 100, 1, Property.PLAY_MUSIC);
								}
							
							}else{
								p.sendMessage("§3§lDAC> §b" + "le temps avant le lancement de la partie n'a pas encore commencé");
							}
						}else{
							p.sendMessage("§3§lDAC> §b" + "la partie de l'arène " + arene + " est déjà en cours");
						}
					}else{
						p.sendMessage("§3§lDAC> §b" + "vous n'êtes dans aucune arène vous ne pouvez donc pas utiliser cette commande");
					}
				}else if(args[0].equalsIgnoreCase("start")){
					if(args.length == 3 || args.length == 5){
						if(args[1].equalsIgnoreCase("event")){
							if(ArenaControle.contains(args[2])){
								if(args.length == 5){
									if(args[3].equalsIgnoreCase("admin")){
										if(args[4].equalsIgnoreCase("on")){
											
											ArenaControle.getArena(args[2]).setEvent(true);
											
											for(Player pl : Bukkit.getServer().getOnlinePlayers()){
												PlayerControle.addPlayer(pl.getName(), args[2], false);
											}
											
										}else if(args[4].equalsIgnoreCase("off")){
											
											ArenaControle.getArena(args[2]).setEvent(true);
											
											for(Player pl : Bukkit.getServer().getOnlinePlayers()){
												if(!pl.isOp()){
													PlayerControle.addPlayer(pl.getName(), args[2], false);
												}
											}
											
										}else{
											p.sendMessage("§3§lPVP> §b" + "argument " + args[4] + " invalide veuillez entrer on ou off");
										}
									}else{
										p.sendMessage("§3§lPVP> §b" + "argument " + args[3] + " invalide veuillez entrer admin");
									}
								}else{
									ArenaControle.getArena(args[2]).setEvent(true);
									
									for(Player pl : Bukkit.getServer().getOnlinePlayers()){
										PlayerControle.addPlayer(pl.getName(), args[2], false);
									}
								}
							}else{
								p.sendMessage("§3§lPVP> §b" + "cette arène n'existe pas ou est mal configurée");
							}
						}else{
							p.sendMessage("§3§lPVP> §b" + "argument " + args[1] + " invalide veuillez entrer event");
						}
					}else{
						p.sendMessage("§3§lPVP> §b" + "la commande est incomplète");
					}
					
				}else if(args[0].equalsIgnoreCase("pannel")){
					if(p.isOp()){
						CreateAreneList.loadInventoryArena(p);
					}else{
						p.sendMessage("§3§lPVP> §b" + "vous n'avez pas le droit d'utiliser cette commande");
					}
				}else{
					p.sendMessage("§3§lPVP> §b" +  "argument " + args[0] + " invalide veuillez entrer create, <nom d'une arène>, join, leave ou start");
				}
			}else{
				p.sendMessage("§3§lPVP> §b" + "la commande est incomplète");
			}
			
		}
		return false;
	}
	
	public static JavaPlugin getPlugin(){
		return plugin;
	}
}
