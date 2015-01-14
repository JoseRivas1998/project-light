package com.tcg.light.managers;

import java.io.Serializable;

public class Save implements Serializable {
	
	private static final long serialVersionUID = 202437697381389495L;
	
	private int highScore;
	private int level;
	private float volume;
	
	public float getVolume() {
		return volume;
	}
	public void setVolume(float volume) {
		this.volume = volume;
	}
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
	
}
