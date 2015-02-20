package com.tcg.light.managers;

import java.io.Serializable;

public class Save implements Serializable {
	
	private static final long serialVersionUID = 202437697381389495L;
	
	private int highScore;
	private int level;
	
	private int maxHealth;
	private int maxAmmo;
	
	private int exp, toNext, tier;
	
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
	public int getExp() {
		return exp;
	}
	public void setExp(int exp) {
		this.exp = exp;
	}
	public int getToNext() {
		return toNext;
	}
	public void setToNext(int toNext) {
		this.toNext = toNext;
	}
	public int getTier() {
		return tier;
	}
	public void setTier(int tier) {
		this.tier = tier;
	}
	
}
