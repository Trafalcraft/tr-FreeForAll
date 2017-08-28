package com.trafalcraft.meleGeneral.Data;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

import com.trafalcraft.meleGeneral.Controler.ArenaControle;
import com.trafalcraft.meleGeneral.Controler.PlayerControle;

public class ScoreBoard {
	private static ScoreboardManager manager = Bukkit.getScoreboardManager();
	private Scoreboard Board;
	private Objective o;
	
	public ScoreBoard(String aname){
		addScoreBoard(aname);
	}

	
	private void addScoreBoard(String aname) {
		this.Board = manager.getNewScoreboard();
		this.o = Board.registerNewObjective("Nom", "vie");
		this.sendScoreBoard(aname);
	}
	
	
	private void sendScoreBoard(String aname){
		this.o.setDisplaySlot(DisplaySlot.SIDEBAR);
		this.o.setDisplayName("§3§l" + "vies restantes");
		
		for(Player allp : ArenaControle.getArena(aname).getPlayerList()){
			this.o.getScore(allp.getName()).setScore(0);
		}
		
		for(Player allp : ArenaControle.getArena(aname).getPlayerList()){
			allp.setScoreboard(Board);
		}
		
		for(Player allp : ArenaControle.getArena(aname).getPlayerList()){
			updateScore(allp);
		}
		
	}
	
	
	public void updateScore(Player p){
		this.o.getScore(p.getName()).setScore(PlayerControle.getJoueur(p.getName()).getNbrVie());
	}
	
	
	public void clearScoreBoard(){
		this.Board.clearSlot(DisplaySlot.SIDEBAR);
		this.o.unregister();
	}
}
