package com.tcg.light.managers;

import java.util.HashMap;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.tcg.light.managers.midi.Midi;

public class Content {
	
	private HashMap<String, Music> music;
	private HashMap<String, Sound> sound;
	private HashMap<String, BitmapFont> font;
	private HashMap<String, Midi> midi;
	
	public Content() {
		music = new HashMap<String, Music>();
		sound = new HashMap<String, Sound>();
		font = new HashMap<String, BitmapFont>();
		midi = new HashMap<String, Midi>();
	}
	
	/*
	 * Music
	 */
	
	public void loadMusic(String folder, String path, String key, boolean looping) {
		Music m = Gdx.audio.newMusic(Gdx.files.internal(folder + "/" + path));
		m.setLooping(looping);
		music.put(key, m);
	}
	
	public Music getMusic(String key) {
		return music.get(key);
	}
	
	public void setVolumeAll(float vol) {
		for(Object o : music.values()) {
			Music music = (Music) o;
			music.setVolume(vol);
		}
		for(Object o : midi.values()) {
			Midi m = (Midi) o;
			m.setVolume(vol);
		}
	}
	
	/*
	 * Midi
	 */
	
	public void loadMidi(String folder, String path, String key, boolean looping) {
		Midi m = new Midi();
		m.open(folder + "/" + path);
		m.setLooping(looping);
		midi.put(key, m);
	}
	
	public Midi getMidi(String key) {
		return midi.get(key);
	}
	
	/*
	 * Sound
	 */
	
	public void loadSound(String folder, String path, String key) {
		Sound s = Gdx.audio.newSound(Gdx.files.internal(folder + "/" + path));
		sound.put(key, s);
	}
	
	public Sound getSound(String key) {
		return sound.get(key);
	}
	
	/*
	 * Bitmap Font
	 */
	
	@SuppressWarnings("deprecation")
	public void loadBitmapFont(String folder, String path, String key, int size, Color color) {
		FreeTypeFontGenerator gen = new FreeTypeFontGenerator(Gdx.files.internal(folder + "/" + path));
		BitmapFont bmf = gen.generateFont(size);
		bmf.setColor(color);
		font.put(key, bmf);
		gen.dispose();
	}
	
	public BitmapFont getFont(String key) {
		return font.get(key);
	}

	public float getWidth(String key, String s) {
		return font.get(key).getBounds(s).width;
	}
	
	public float getHeight(String key, String s) {
		return font.get(key).getBounds(s).height - font.get(key).getDescent();
	}
	
	/*
	 * Other
	 */
	
	public void removeAll() {
		for(Object o : music.values()) {
			Music m = (Music) o;
			m.dispose();
		}
		for(Object o : sound.values()) {
			Sound s = (Sound) o;
			s.dispose();
		}
		for(Object o : font.values()) {
			BitmapFont bmf = (BitmapFont) o;
			bmf.dispose();
		}
	}
	
	public void stopSound() {
		for(Object o : sound.values()) {
			Sound s = (Sound) o;
			s.stop();
		}
	}
	
	public void stopMusic() {
		for(Object o : music.values()) {
			Music m = (Music) o;
			m.stop();
		}
	}
	
	public void stopMidi() {
		for(Object o : midi.values()) {
			Midi m = (Midi) o;
			m.stop();
		}
	}
	
	public void stopAllSound() {
		stopSound();
		stopMusic();
		stopMidi();
	}
}
