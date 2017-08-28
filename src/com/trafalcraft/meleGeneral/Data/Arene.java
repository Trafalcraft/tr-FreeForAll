package com.trafalcraft.meleGeneral.Data;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.inventivetalent.bossbar.BossBarAPI;
import org.inventivetalent.bossbar.BossBarAPI.Property;
import org.inventivetalent.bossbar.BossBarAPI.Style;

import com.trafalcraft.meleGeneral.ClearPotion;
import com.trafalcraft.meleGeneral.Leave;
import com.trafalcraft.meleGeneral.Main;
import com.trafalcraft.meleGeneral.TpStart;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;
import com.trafalcraft.meleGeneral.file.FileControler;
import com.trafalcraft.meleGeneral.util.BungeeCord;

import net.md_5.bungee.api.chat.TextComponent;

public class Arene extends org.inventivetalent.bossbar.BossBarAPI{
	private String name;
	private ArrayList <Player> playerList = new ArrayList<Player>();
	private String status;
	private int nbrPlayer;
	private int nbrPlayerInLife;
	private int temps;
	private int taskLobby;
	private int taskGame;
	private int taskFinish;
	private ArrayList<String> winner = new ArrayList<String>();
	private Object scoreBoard;
	private boolean event;
	
	public Arene(String nom){
		this.name = nom;
		this.nbrPlayer = 0;
		this.status = "lobby";
	}
	
	public int getTemps(){
		return this.temps;
	}
	
	public int getTaskLobby(){
		return this.taskLobby;
	}
	
	public void setTemps(int i){
		this.temps = i;
	}
	
	public void setEvent(boolean e){
		this.event = e;
	}
	
	public boolean getEvent(){
		return this.event;
	}
	
	public void setName(String nom){
		this.name = nom;
	}
	
	public String getName(){
		return this.name;
	}
	
	public void addPlayer(Player p){
		playerList.add(p);
		
		if(playerList.size() == 2){
			startLobbyTimer();
			
			for(Player pl : getPlayerList()){
				BossBarAPI.removeAllBars(pl);
				BossBarAPI.addBar(pl, new TextComponent("§b" + "mélée générale"), Color.BLUE, Style.PROGRESS, 1.0f, 620, 1, Property.PLAY_MUSIC);
			
				pl.setLevel(30);
			}
		}
	}
	
	public void removePlayer(Player p){
		playerList.remove(p);
		
		if(playerList.size() == 1){
			if(getTaskLobby() !=0){
				stopLobbyTimer();
				
				for(Player pl : getPlayerList()){
					pl.sendMessage("§3§lPVP> §b" +"il n'y a plus assez de joueurs pour lancer la partie");  
					
					BossBarAPI.removeAllBars(pl);
					BossBarAPI.addBar(pl, new TextComponent("§b" + "mélée générale"), Color.BLUE, Style.PROGRESS, 1.0f);
					
					pl.setLevel(0);
				}
				
				
			}
		}
	}
	
	public int PlayerIndexOf(Player p){
		return playerList.indexOf(p);
	}
	
	public void setStatus(String s){
		this.status = s;
	}
	
	public String getStatus(){
		return this.status;
	}
	
	public int getNbrJoueur(){
		return this.nbrPlayer;
	}
	
	public void setNbrJoueur(int i){
		this.nbrPlayer = i;
	}
	
	public ArrayList<Player> getPlayerList(){
		return this.playerList;
	}
	
	public int getNbrPlayerInLife(){
		return this.nbrPlayerInLife;
	}
	
	public void setNbrPlayerInLife(int i){
		this.nbrPlayerInLife = i;
	}
	
	public void startLobbyTimer(){
		this.temps = 31;
		this.taskLobby = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            
			public void run() {
						for(Player pl : getPlayerList()){
							pl.setLevel(temps);
						}
                   		if(temps == 30||temps == 20||temps == 10||(temps <= 5&&temps>0)){
                   			for(Player pl : getPlayerList()){
                   				pl.sendMessage("§3§lPVP> §b" +"la partie commence dans " + temps + " seconde(s)");
                   				pl.playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                   				pl.sendTitle("§b" + temps + "", "§b" + "seconde(s)");
                   			}
                   		}else if(temps <= 0){
                   			for(Player pl : getPlayerList()){
                   				
                   				PlayerControle.getJoueur(pl.getName()).setInGame(true);
                   				
                   				pl.sendMessage("§3§lPVP> §b" +"la partie commence !!!");   
                   				pl.playSound(pl.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100.0F, 1.0F);
                   				ClearPotion.clearEffect(pl);
                   				
                   				BossBarAPI.removeAllBars(pl);
                   				BossBarAPI.addBar(pl, new TextComponent("§b" + "mélée générale"), Color.BLUE, Style.PROGRESS, 1.0f, (FileControler.getArena(getName()).getInt("temps")) * 20 * 60, 1, Property.PLAY_MUSIC);
            					
                   				
                   			}
                   			
                   			stopLobbyTimer();
                   			taskLobby = 0;
               				startGameTimer();
               				status = "in-game";
               				TpStart.tpStart(playerList, name);
               				nbrPlayerInLife = nbrPlayer;
               				ScoreBoard board = new ScoreBoard(name);
               				scoreBoard = board;
               				
               				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {

            	                @Override
            	                public void run() {
            	                   for(Player p : playerList){
            	                	   p.sendTitle("", "");
            	                   }
            	                }
            	            },20);
                   		}
            	temps = temps-1;
            }
         }, 0, 20);
	}
	
	public void stopLobbyTimer(){
		Bukkit.getServer().getScheduler().cancelTask(this.taskLobby);
	}
	
	public void startGameTimer(){
		this.temps = (FileControler.getArena(name).getInt("temps"))*60;
		this.taskGame = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            
			public void run() {
            	
                   		if(temps == 60){
                   			for(Player pl : getPlayerList()){
                   				pl.playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                   				pl.sendMessage("§3§lPVP> §b" +"la partie se termine dans une minute !!!");
                   			}
                   		}else if(temps == 30||temps == 20||temps == 10||(temps <= 5&&temps>0)){
                   			for(Player pl : getPlayerList()){
                   				pl.playSound(pl.getLocation(), Sound.ENTITY_ARROW_HIT_PLAYER, 1.0F, 1.0F);
                   			}
                   		}else if(temps <= 0){
                   			for(Player pl : getPlayerList()){
                   				pl.sendMessage("§3§lPVP> §b" +"la partie est terminée !!!");
                   			}
                   			stopGameTimer();
                   			taskGame = 0;
               				WhoWin();
                   		}
            	temps = temps-1;
            }
         }, 0, 20);
	}
	
	public void stopGameTimer(){
		Bukkit.getServer().getScheduler().cancelTask(taskGame);
	}
	
	public void WhoWin(){
		
			
			for(int i = 0;playerList.size() > i; i++){
				if(winner.size() == 0){
					winner.add(playerList.get(i).getName());
				}else if(PlayerControle.getJoueur(playerList.get(i).getName()).getNbrVie() > PlayerControle.getJoueur(winner.get(0)).getNbrVie()){
					
					winner.clear();
					winner.add(playerList.get(i).getName());
				}else if(PlayerControle.getJoueur(playerList.get(i).getName()).getNbrVie() == PlayerControle.getJoueur(winner.get(0)).getNbrVie()){
					winner.add(playerList.get(i).getName());
				}
			}
			
			if(winner.size() == 1){
			
				for(Player p : playerList){
					p.sendMessage("§3§lPVP> §b" + winner.get(0) + " a gagné la partie !!!!!!");
					p.sendTitle("§b" + winner.get(0) + "" , "§b" + "a gagné!!!");
					
					BossBarAPI.removeAllBars(p);
					BossBarAPI.addBar(p, new TextComponent("§b" + winner.get(0) + " a gagné!!!"), Color.BLUE, Style.PROGRESS, 1.0f, Property.PLAY_MUSIC);
       				
				}
				
				startFinishTimer();
			}else if(winner.size() > 1){
				for(Player p : playerList){
					p.sendTitle("§b" + "égalité" , "");
					

       				BossBarAPI.removeAllBars(p);
					BossBarAPI.addBar(p, new TextComponent("§b" + "égalité"), Color.BLUE, Style.PROGRESS, 1.0f, Property.PLAY_MUSIC);
       				
					
					p.sendMessage("§3§lPVP> §b" + "les gagnants sont:");
				}
				
				for(Player p : playerList){
					for(String gagnant : winner){
						p.sendMessage("§3§lPVP> §b" + "-" + gagnant);
					}
				}
				
				startFinishTimer();
			}else{
				finGame();
			}
			
	}
	
	public void startFinishTimer(){
		this.temps = 5;
		this.taskFinish = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(Main.getPlugin(), new Runnable() {
            
			public void run() {
            	
                   		if(temps != 0){
                   			for(String listwinner : winner){
                   				Player pl = Bukkit.getPlayer(listwinner);
                   				Firework f = (Firework) pl.getWorld().spawn(pl.getLocation(), Firework.class);
                   				FireworkMeta fm = f.getFireworkMeta();
                    		
                   				fm.addEffects(FireworkEffect.builder()
                   						.flicker(true)
                   						.trail(true)
                   						.with(Type.BALL_LARGE)
               							.withColor(org.bukkit.Color.AQUA)
               							.withColor(org.bukkit.Color.RED)
               							.withColor(org.bukkit.Color.AQUA)
               							.withColor(org.bukkit.Color.RED)
               							.build()
               							);
                    		
                   				fm.setPower(2);
                   				f.setFireworkMeta(fm);
                   			}
                   		}else{
                   			StopFinishTimer();
                   			taskFinish = 0;
                   			finGame();
                   		}
            	temps = temps-1;
            }
         }, 0, 20);
	}
	
	public void StopFinishTimer(){
		Bukkit.getServer().getScheduler().cancelTask(taskFinish);
	}
	
	private void finGame(){
		
		if(event == true){
			
			for(int i = 0; i < getPlayerList().size(); i = i){
				getPlayerList().get(0).teleport(new Location(Bukkit.getWorld(FileControler.getArena(getName()).getString("world")), FileControler.getArena(getName()).getDouble("eventlobby.x"), FileControler.getArena(getName()).getDouble("eventlobby.y"), FileControler.getArena(getName()).getDouble("eventlobby.z"), (float)FileControler.getArena(getName()).getDouble("eventlobby.yaw"), (float)FileControler.getArena(getName()).getDouble("eventlobby.pitch")));
				Leave.leave(getPlayerList().get(0));
			}
		}else{
			for(int i = 0; i < getPlayerList().size(); i = i){
				BungeeCord.sendPlayerToHub(getPlayerList().get(0));
			}
		}
		
		
		((ScoreBoard) this.scoreBoard).clearScoreBoard();
		playerList.clear();
		status = "lobby";
		nbrPlayer = 0;
		nbrPlayerInLife = 0;
		temps = 0;
		this.taskLobby = 0;
		this.taskGame = 0;
		this.taskFinish = 0;
		this.winner.clear();
		this.event = false;
	}
	
	public Object getScoreBoard(){
		return this.scoreBoard;
	}
}
