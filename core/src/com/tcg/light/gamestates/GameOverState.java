package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class GameOverState extends GameState {

	private String gameOver, score;
	private boolean first;
	private MyCamera cam;
	private float gX, gY, sX, sY;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		first = true;
		System.out.println("First:" + first);
		Game.LIVES = 5;
		cam = new MyCamera(Game.SIZE, true);
		setValues();
	}

	@Override
	public void handleInput() {
		if(!first && MyInput.anyKeyPressed()) {
			gsm.setState(gsm.TITLE);
		}

	}

	@Override
	public void update(float dt) {
		setValues();
		first = false;
	}
	
	private void setValues() {
		gameOver = "Game Over";
		score = "Score: " + Game.getScore(Game.SCORE); 
		gX = Game.res.centerX("large", gameOver);
		gY = Game.res.centerY("large", gameOver);
		sX = Game.res.centerX("mItems", score);
		sY = gY - (Game.res.getHeight("large", gameOver) * 2);
	}
	
	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		// TODO Auto-generated method stub
		Color col1, col2;
		col1 = new Color(75f / 255f, 75f / 255f, 75f / 255f, 1);
		col2 = Color.BLACK;
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		sr.rect(0, 0, Game.SIZE.x, Game.SIZE.y, col1, col2, col1, col2);
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("large").draw(sb, gameOver, gX, gY);
		Game.res.getFont("mItems").draw(sb, score, sX, sY);
		sb.end();
		
	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(Game.SIZE, true);

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
