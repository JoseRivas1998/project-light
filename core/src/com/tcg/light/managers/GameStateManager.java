package com.tcg.light.managers;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.gamestates.*;

public class GameStateManager {

	private SpriteBatch sb;
	private ShapeRenderer sr;
	
	private GameState gamestate;

	public final int TITLE = 1;
	public final int PLAY = 2;
	
	private float tTime, tTimer;
	private boolean playing, pPlaying;
	private MyCamera tCam;
	
	public GameStateManager() {
		sb = new SpriteBatch();
		sr = new ShapeRenderer();
		setState(PLAY);
		tTimer = MathUtils.random(15);
		tTime = 0;
		playing = false;
		pPlaying = false;
		tCam = new MyCamera(Game.SIZE, true);
	}
	
	public void setState(int state) {
		if(gamestate != null) gamestate.dispose();
		if(state == TITLE) {
			gamestate = new TitleState(this);
		}
		if(state == PLAY) {
			gamestate = new PlayState(this);
		}
	}
	
	public void handleInput() {
		gamestate.handleInput();
	}
	
	public void update(float dt) {
		
		tTime += dt;
		playing = tTime >= tTimer;
		if(playing && tTime >= tTimer + 1.245) {
			tTime = 0;
			tTimer = MathUtils.random(15, 60);
			playing = false;
		}
		if(playing && !pPlaying && isPlay()) {
			Game.res.getSound("crack").play();
		}
		pPlaying = playing;
		
		gamestate.update(dt);
	}
	
	public void draw() {
		gamestate.draw(sb, sr);
		
		sr.begin(ShapeType.Filled);
		sr.setColor(1, 1, 1, .1f);
		sr.setProjectionMatrix(tCam.combined);
		if(playing && isPlay()) {
			if(MathUtils.randomBoolean())sr.rect(0, 0, Game.SIZE.x, Game.SIZE.y);
		}
		sr.setColor(Color.WHITE);
		sr.end();
	}
	
	public void resize(Vector2 size) {
		gamestate.resize(size);
		tCam.resize(size, true);
	}
	
	public void dispose() {
		gamestate.dispose();
		sb.dispose();
		sr.dispose();
	}
	
	public boolean isPlay() {
		return gamestate instanceof PlayState;
	}
	
}
