package com.trafalcraft.meleGeneral;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.block.Sign;

import com.trafalcraft.meleGeneral.Controler.ArenaControle;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;
import com.trafalcraft.meleGeneral.Data.ScoreBoard;
import com.trafalcraft.meleGeneral.file.FileControler;

public class PlayerListener implements Listener{
	
	@EventHandler
	public void onPlayerSendMessageEvent(AsyncPlayerChatEvent e){
		String Message = e.getMessage();
		e.setCancelled(true);

		Message = ("§3§lPVP> §b" + e.getPlayer().getDisplayName() + ">> " + Message);
		Bukkit.getServer().broadcastMessage(Message);
	}
	
	@EventHandler
	public void onEntityDamageEvent(EntityDamageEvent e){
		if(e.getEntity().getType() == EntityType.PLAYER){
			
			if(PlayerControle.contains(((Player)e.getEntity()).getName())){
				
				String arene = PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene();
				
				if(ArenaControle.getArena(arene).getStatus().equalsIgnoreCase("in-game")){
					
					if(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getInGame() == true){
						
						if((((Player)e.getEntity()).getHealth()) - (e.getFinalDamage()) <= 0){
							
							if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
								e.setCancelled(true);
								((Player)e.getEntity()).setHealth(20);
								ClearPotion.clearEffect((Player)e.getEntity());
								((Player)e.getEntity()).setFoodLevel(20);
								PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setNbrVie(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() - 1);
								PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setNbrMort(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrMort() + 1);
								PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setSerie(0);
								((ScoreBoard)ArenaControle.getArena(arene).getScoreBoard()).updateScore((Player)e.getEntity());
							}
							
							if(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() > 0 && e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
								
								if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
									TpInGame.tpGame((Player)e.getEntity());
								}
								
								if(e.getCause() == DamageCause.FALL){
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) de chute, il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
								}else if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK){
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) brulé(e), il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
								}else if(e.getCause() == DamageCause.LAVA){
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) dans la lave, il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
								}else if(e.getCause() == DamageCause.DROWNING){
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) noyé(e), il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
								}else if(e.getCause() == DamageCause.POISON){
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) empoisonné(e), il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
								}else if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e), il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
								}
							}else{
								if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
									ArenaControle.getArena(arene).setNbrPlayerInLife(ArenaControle.getArena(arene).getNbrPlayerInLife() - 1);
								}
								if(ArenaControle.getArena(arene).getNbrPlayerInLife() == 1 && e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
									
									Location loc = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")), FileControler.getArena(arene).getDouble("spec.x"),  FileControler.getArena(arene).getDouble("spec.y"), FileControler.getArena(arene).getDouble("spec.z"), (float)FileControler.getArena(arene).getDouble("spec.yaw"), (float)FileControler.getArena(arene).getDouble("spec.pitch"));
									
									((Player)e.getEntity()).teleport(loc);
									((Player)e.getEntity()).setSaturation(20);
									ClearPotion.clearEffect((Player)e.getEntity());
									((Player)e.getEntity()).setGameMode(GameMode.SPECTATOR);
									PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setInGame(false);
									
									ArenaControle.getArena(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene()).stopGameTimer();
									ArenaControle.getArena(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene()).setStatus("finish");
									ArenaControle.getArena(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene()).WhoWin();
								}else{
									if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE){
										Location loc = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")), FileControler.getArena(arene).getDouble("spec.x"),  FileControler.getArena(arene).getDouble("spec.y"), FileControler.getArena(arene).getDouble("spec.z"), (float)FileControler.getArena(arene).getDouble("spec.yaw"), (float)FileControler.getArena(arene).getDouble("spec.pitch"));
								
										((Player)e.getEntity()).teleport(loc);
										((Player)e.getEntity()).setSaturation(20);
										((Player)e.getEntity()).setFoodLevel(20);
										ClearPotion.clearEffect((Player)e.getEntity());
										((Player)e.getEntity()).setGameMode(GameMode.SPECTATOR);
										PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setInGame(false);
									}
								}
								
								if(e.getCause() == DamageCause.FALL){
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) de chute, il est éliminé !!!");
									}
								}else if(e.getCause() == DamageCause.FIRE || e.getCause() == DamageCause.FIRE_TICK){
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) brulé(e), il est éliminé !!!");
									}
								}else if(e.getCause() == DamageCause.LAVA){
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) dans la lave, il est éliminé !!!");
									}
								}else if(e.getCause() == DamageCause.DROWNING){
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) noyé(e), il est éliminé !!!");
									}
								}else if(e.getCause() == DamageCause.POISON){
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) empoisonné(e), il est éliminé !!!");
									}
								}else if(e.getCause() != DamageCause.ENTITY_ATTACK && e.getCause() != DamageCause.PROJECTILE ){
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e), il est éliminé !!!");
									}
								}
							}
						}
					}else{
						e.setCancelled(true);
					}
				}else{
					
					if(e.getCause() != DamageCause.PROJECTILE){
						e.setCancelled(true);
					}
				}
			}
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onEntityDamageByEntityEvent(EntityDamageByEntityEvent e){
		
		if(e.getEntity().getType() == EntityType.PLAYER){
			
			if(PlayerControle.contains(((Player)e.getEntity()).getName())){
			
				String arene = PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene();
				
				if(ArenaControle.getArena(arene).getStatus().equalsIgnoreCase("in-game")){
				
					if(e.getDamager().getType() == EntityType.PLAYER || e.getDamager().getType() == EntityType.ARROW || e.getDamager().getType() == EntityType.SPECTRAL_ARROW || e.getDamager().getType() == EntityType.TIPPED_ARROW){
					
						if((((Player)e.getEntity()).getHealth()) - (e.getFinalDamage()) <= 0){
						
							e.setCancelled(true);
							((Player)e.getEntity()).setHealth(20);
							ClearPotion.clearEffect((Player)e.getEntity());
							((Player)e.getEntity()).setFoodLevel(20);
							PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setNbrVie(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() - 1);
							PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setNbrMort(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrMort() + 1);
							PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setSerie(0);
							
							if(e.getDamager().getType() == EntityType.PLAYER){
								PlayerControle.getJoueur(((Player)e.getDamager()).getName()).setNbrKill(PlayerControle.getJoueur(((Player)e.getDamager()).getName()).getNbrKill() + 1);
								PlayerControle.getJoueur(((Player)e.getDamager()).getName()).setSerie(PlayerControle.getJoueur(((Player)e.getDamager()).getName()).getSerie() + 1);
							}else{
								PlayerControle.getJoueur(((Player)((Arrow)e.getDamager()).getShooter()).getName()).setNbrKill(PlayerControle.getJoueur(((Player)((Arrow)e.getDamager()).getShooter()).getName()).getNbrKill() + 1);
								PlayerControle.getJoueur(((Player)((Arrow)e.getDamager()).getShooter()).getName()).setSerie(PlayerControle.getJoueur(((Player)((Arrow)e.getDamager()).getShooter()).getName()).getSerie() + 1);
							}
							((ScoreBoard)ArenaControle.getArena(arene).getScoreBoard()).updateScore((Player)e.getEntity());
						
							if(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() > 0){
							
								TpInGame.tpGame((Player)e.getEntity());
							
								if(e.getDamager().getType() == EntityType.PLAYER){
							
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) tué(e) par " + ((Player)e.getDamager()).getName() + ", il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
							
									if(PlayerControle.getJoueur(((Player)e.getDamager()).getName()).getSerie() == 3){
										for(Player p : ArenaControle.getArena(arene).getPlayerList()){
											p.sendMessage("§3§lPVP> §b" + ((Player)e.getDamager()).getName() + " est en serie de trois éliminations !!!          ");
										}
									}
								}else{
								
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) tué(e) par " + ((Player)((Arrow)e.getDamager()).getShooter()).getName() + ", il lui reste " + PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getNbrVie() + " vie(s)");
									}
									
									if(PlayerControle.getJoueur(((Player)((Arrow)e.getDamager()).getShooter()).getName()).getSerie() == 3){
										for(Player p : ArenaControle.getArena(arene).getPlayerList()){
											p.sendMessage("§3§lPVP> §b" + (((Player)((Arrow)e.getDamager()).getShooter()).getName()) + " est en serie de trois éliminations !!!");
										}
									}
								}
								
								
							}else{
							
								ArenaControle.getArena(arene).setNbrPlayerInLife(ArenaControle.getArena(arene).getNbrPlayerInLife() - 1);
							
								if(e.getDamager().getType() == EntityType.PLAYER){
								
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) tué(e) par " + ((Player)e.getDamager()).getName() + ", il est eliminé");
									}
									
									if(PlayerControle.getJoueur(((Player)e.getDamager()).getName()).getSerie() == 3){
										for(Player p : ArenaControle.getArena(arene).getPlayerList()){
											p.sendMessage("§3§lPVP> §b" + ((Player)e.getDamager()).getName() + " est en serie de trois éliminations !!!");
										}
									}
							
								}else{
									
									for(Player p : ArenaControle.getArena(arene).getPlayerList()){
										p.sendMessage("§3§lPVP> §b" + ((Player)e.getEntity()).getName() + " est mort(e) tué(e) par " + ((Player)((Arrow)e.getDamager()).getShooter()).getName() + ", il est eliminé");
									}
									
									if(PlayerControle.getJoueur(((Player)((Arrow)e.getDamager()).getShooter()).getName()).getSerie() == 3){
										for(Player p : ArenaControle.getArena(arene).getPlayerList()){
											p.sendMessage("§3§lPVP> §b" + (((Player)((Arrow)e.getDamager()).getShooter()).getName()) + " est en serie de trois éliminations !!!");
										}
									}
								}
								
								if(ArenaControle.getArena(arene).getNbrPlayerInLife() == 1){
									Location loc = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")), FileControler.getArena(arene).getDouble("spec.x"),  FileControler.getArena(arene).getDouble("spec.y"), FileControler.getArena(arene).getDouble("spec.z"), (float)FileControler.getArena(arene).getDouble("spec.yaw"), (float)FileControler.getArena(arene).getDouble("spec.pitch"));
									
									((Player)e.getEntity()).teleport(loc);
									((Player)e.getEntity()).setSaturation(20);
									((Player)e.getEntity()).setFoodLevel(20);
									ClearPotion.clearEffect((Player)e.getEntity());
									((Player)e.getEntity()).setGameMode(GameMode.SPECTATOR);
									PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setInGame(false);
									
									ArenaControle.getArena(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene()).stopGameTimer();
									ArenaControle.getArena(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene()).setStatus("finish");
									ArenaControle.getArena(PlayerControle.getJoueur(((Player)e.getEntity()).getName()).getArene()).WhoWin();
								}else{
									Location loc = new Location(Bukkit.getWorld(FileControler.getArena(arene).getString("world")), FileControler.getArena(arene).getDouble("spec.x"),  FileControler.getArena(arene).getDouble("spec.y"), FileControler.getArena(arene).getDouble("spec.z"), (float)FileControler.getArena(arene).getDouble("spec.yaw"), (float)FileControler.getArena(arene).getDouble("spec.pitch"));
									
									((Player)e.getEntity()).teleport(loc);
									((Player)e.getEntity()).setSaturation(20);
									ClearPotion.clearEffect((Player)e.getEntity());
									((Player)e.getEntity()).setGameMode(GameMode.SPECTATOR);
									PlayerControle.getJoueur(((Player)e.getEntity()).getName()).setInGame(false);
								}
								
							}
						}
					}else{
						e.setCancelled(true);
					}
				}else{
					e.setCancelled(true);
					
					if(e.getCause() == DamageCause.PROJECTILE){
						e.getDamager().remove();
					}
				}
			}
		}
	}
	
	@EventHandler
	public void onPlayerQuitEvent(PlayerQuitEvent e){
		Leave.leave(e.getPlayer());
	}

	@EventHandler
	public void OnBreackBlockEvent(BlockBreakEvent e){
		if(e.getPlayer().isOp()){
			if(PlayerControle.contains(e.getPlayer().getName())){
				e.setCancelled(true);
			}
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onMob(EntitySpawnEvent e){
		e.setCancelled(true);
	}
	
	@EventHandler
	public void onDrop(PlayerDropItemEvent e){
		
		if(e.getPlayer().isOp() == true){
			
			if(PlayerControle.contains(e.getPlayer().getName())){
				
				e.setCancelled(true);
				
			}
			
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent e){
		
		if(PlayerControle.contains(e.getPlayer().getName())){
		
			if(e.getAction() == Action.RIGHT_CLICK_BLOCK){
					
				if(e.getClickedBlock().getType() == Material.WALL_SIGN || e.getClickedBlock().getType() == Material.SIGN || e.getClickedBlock().getType() == Material.SIGN_POST){
						
					if(((Sign)e.getClickedBlock().getState()).getLine(0).equalsIgnoreCase("mg-classe")){
							
						if(((Sign)e.getClickedBlock().getState()).getLine(0) != null){
								
							ManageClasses.addChestClass(e.getPlayer(), e.getClickedBlock().getLocation(), ((Sign)e.getClickedBlock().getState()).getLine(2));
							PlayerControle.getJoueur(e.getPlayer().getName()).setClasse(((Sign)e.getClickedBlock().getState()).getLine(2));
						}
					}						
				}
			}
		}
	}
	
	
	@EventHandler
	public void OnblockPlaceEvent(BlockPlaceEvent e){
		if(e.getPlayer().isOp()){
			if(PlayerControle.contains(e.getPlayer().getName())){
				e.setCancelled(true);
			}
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void onPlayerpickEvent(PlayerPickupItemEvent e){
		if(e.getPlayer().isOp()){
			if(PlayerControle.contains(e.getPlayer().getName())){
				e.setCancelled(true);
			}
		}else{
			e.setCancelled(true);
		}
	}
	
	@EventHandler
	public void OnPlayerDeathEvent(PlayerDeathEvent e){
		e.setDeathMessage("");
	}
}
