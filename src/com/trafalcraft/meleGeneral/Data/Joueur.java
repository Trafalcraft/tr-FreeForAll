package com.trafalcraft.meleGeneral.Data;
import org.bukkit.entity.Player;

public class Joueur {
	Player player;
	String classe = "defaut";
	String arene;
	int nbrVie;
	boolean inGame;
	int nbrKill;
	int nbrMort;
	int serie;
	
	public Joueur(Player p, String c, String a, int v){
		this.player = p;
		this.classe = c;
		this.arene = a;
		this.nbrVie = v;
		this.inGame = false;
	}
	
	public void setNbrKill(int i){
		this.nbrKill = i;
	}
	
	public int getNbrKill(){
		return this.nbrKill;
	}
	
	public void setNbrMort(int i){
		this.nbrMort = i;
	}
	
	public int getNbrMort(){
		return this.nbrMort;
	}
	
	public void setSerie(int i){
		this.serie = i;
	}
	
	public int getSerie(){
		return this.serie;
	}
	
	public void setPlayer(Player pl){
		this.player = pl;
	}
	
	public void setClasse(String cl){
		this.classe = cl;
	}
	
	public void setArene(String ar){
		this.arene = ar;
	}
	
	public Player getPlayer(){
		return this.player;
	}
	
	public String getClasse(){
		return this.classe;
	}
	
	public String getArene(){
		return this.arene;
	}
	
	public int getNbrVie(){
		return this.nbrVie;
	}
	
	public void setNbrVie(int i){
		this.nbrVie = i;
	}
	
	public boolean getInGame(){
		return this.inGame;
	}
	
	public void setInGame(boolean b){
		this.inGame = b;
	}

}
