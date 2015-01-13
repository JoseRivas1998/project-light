package com.tcg.light;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.managers.*;

public class Game extends ApplicationAdapter {
	
	
	public static Vector2 SIZE, CENTER;
	
	public GameStateManager gsm;
	
	public static int SCORE, HIGHSCORE, LEVEL;
	
	private Save s;
	
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
			s = new Save();
			s.setHighScore(0);
			s.setLevel(1);
		}
		
		Game.HIGHSCORE = s.getHighScore();
		Game.LEVEL = s.getLevel();
		
		gsm = new GameStateManager();
	}
 
	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
		float dt = Gdx.graphics.getDeltaTime();
		
		gsm.handleInput();
		gsm.update(dt);
		gsm.draw();
	}
 
	@Override
	public void resize(int width, int height) {
		SIZE.set(width, height);
		CENTER.set(width * .5f, height * .5f);
		gsm.resize(Game.SIZE);
	}

	@Override
	public void dispose() {
		gsm.dispose();
		s.setHighScore(Game.HIGHSCORE);
		s.setLevel(Game.LEVEL);
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
