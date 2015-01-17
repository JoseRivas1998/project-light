package com.tcg.light.managers;

import java.io.Serializable;

public class Save implements Serializable {
	
	private static final long serialVersionUID = 202437697381389495L;
	
	private int highScore;
	private int level;
	
	private int maxHealth;
	private int maxAmmo;
	
	private int lives;
	
	public int getHighScore() {
		return highScore;
	}
	public void setHighScore(int highScore) {
		this.highScore = highScore;
	}
	public int getLevel() {
		return level;
	}
	public void setLevel(int level) {
		this.level = level;
	}
	public int getMaxHealth() {
		return maxHealth;
	}
	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}
	public int getMaxAmmo() {
		return maxAmmo;
	}
	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}
	public int getLives() {
		return lives;
	}
	public void setLives(int lives) {
		this.lives = lives;
	}
	
}
