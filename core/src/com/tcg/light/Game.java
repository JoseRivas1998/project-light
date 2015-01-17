package com.tcg.light;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import javax.swing.JOptionPane;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.managers.*;

public class Game extends ApplicationAdapter {
	
	public static final String TITLE = "Project Light";
	
	public static Vector2 SIZE, CENTER;
	
	public GameStateManager gsm;
	
	public static int SCORE, HIGHSCORE, LEVEL, MAXHEALTH, MAXAMMO, LIVES;
	
	public static float VOLUME;
	
	private Save s;
	
	public static int fps;
	private int frames;
	private float ftime;
	
	public static Content res;
	
	@Override
	public void create () {
		float width = Gdx.graphics.getWidth();
		float height = Gdx.graphics.getHeight();

		SIZE = new Vector2();
		CENTER = new Vector2();
		SIZE.set(width, height);
		CENTER.set(width * .5f, height * .5f);
		
		Game.SCORE = 0;
		try {
			FileInputStream fileIn = new FileInputStream("save.dat");
			ObjectInputStream file = new ObjectInputStream(fileIn);
			s = (Save) file.readObject();
			file.close();
			fileIn.close();
		} catch (Exception e) {
			e.printStackTrace();
			boolean f = Gdx.graphics.isFullscreen();
			if(f) {
				Gdx.graphics.setDisplayMode(800, 600, false);
			}
			JOptionPane.showMessageDialog(null, "Could not load \"save.dat\", a new one will be created on exit", Game.TITLE, JOptionPane.INFORMATION_MESSAGE);
			if(f) {
				int width1 = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
				int height1 = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
				Gdx.graphics.setDisplayMode(width1, height1, true);
			}
			s = new Save();
			s.setHighScore(0);
			s.setLevel(1);
			s.setMaxHealth(100);
			s.setMaxAmmo(60);
			s.setLives(5);
		}
		
		Game.HIGHSCORE = s.getHighScore();
		Game.LEVEL = s.getLevel();
		Game.MAXAMMO = s.getMaxAmmo();
		Game.MAXHEALTH = s.getMaxHealth();
		Game.LIVES = s.getLives();
		
		ftime = 0;
		frames = 0;
		fps = 0;
		
		res = new Content();

		res.loadSound("sound", "Thunder7.ogg", "crack");
		res.loadSound("sound", "hit.wav", "hit");
		res.loadSound("sound", "land.wav", "land");
		res.loadSound("sound", "jump.wav", "jump");
		res.loadSound("sound", "shoot.wav", "shoot");

		res.loadMidi("midi", "hc- Bloody Tears(NICH2).mid", "bloody tears", true);
		res.loadMidi("midi", "darude-sandstorm.mid", "darude sandstorm", true);
		res.loadMidi("midi", "hyrule-castle.mid", "castle", true);
		
		res.loadMusic("music", "09 Technologic.mp3", "daft", true);

		res.loadBitmapFont("font", "GOTHIC.TTF", "main", 24, Color.WHITE);
		res.loadBitmapFont("font", "GOTHIC.TTF", "large", 56, Color.WHITE);
		res.loadBitmapFont("font", "GOTHIC.TTF", "mItems", 34, Color.WHITE);
		
		res.setVolumeAll(VOLUME);
		
		Gdx.input.setInputProcessor(new MyInputProcessor());
		Controllers.addListener(new MyControllerProcessor());
		
		gsm = new GameStateManager();
	}
 
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		ftime += dt;
		frames++;
		if(ftime >= 1) {
			fps = frames;
			frames = 0;
			ftime = 0;
		}
		Gdx.graphics.setTitle(Game.TITLE + " | " + Game.fps + "fps");
		
		
		gsm.handleInput();
		gsm.update(dt);
		gsm.draw(dt);
		
		res.setVolumeAll(Game.VOLUME);
		
		if(MyInput.keyPressed(MyInput.FULLSCREEN)) {
			if(Gdx.graphics.isFullscreen()) {
				Gdx.graphics.setDisplayMode(800, 600, false);
			} else {
				int width = java.awt.Toolkit.getDefaultToolkit().getScreenSize().width;
				int height = java.awt.Toolkit.getDefaultToolkit().getScreenSize().height;
				Gdx.graphics.setDisplayMode(width, height, true);
			}
			Gdx.input.setCursorCatched(Gdx.graphics.isFullscreen());
		}
		
		MyInput.update();
	}
 
	@Override
	public void resize(int width, int height) {
		SIZE.set(width, height);
		CENTER.set(width * .5f, height * .5f);
		gsm.resize(Game.SIZE);
	}

	public static String getScore(int score) {
		if(score < 10) {
			return "00000" + score;
		} else if(score < 100) {
			return "0000" + score;
		} else if(score < 1000) {
			return "000" + score;
		} else if(score < 10000) {
			return "00" + score;
		} else if(score < 100000) {
			return "0" + score;
		} else {
			return "" + score;
		}
	}
	
	@Override
	public void dispose() {
		gsm.dispose();
		s.setHighScore(Game.HIGHSCORE);
		s.setLevel(Game.LEVEL);
		s.setMaxAmmo(Game.MAXAMMO);
		s.setMaxHealth(Game.MAXHEALTH);
		s.setLives(Game.LIVES);
		try {
		 	FileOutputStream fileOut = new FileOutputStream("save.dat");
		 	ObjectOutputStream out = new ObjectOutputStream(fileOut);
		 	out.writeObject(s);
		 	out.close();
		 	fileOut.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
