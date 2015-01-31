package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.managers.GameStateManager;

public class GameOverState extends GameState {

	private String gameOver;
	boolean first;
	
	public GameOverState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		first = true;
		gameOver = "Game Over";
	}

	@Override
	public void handleInput() {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		// TODO Auto-generated method stub
		first = false;
	}

	@Override
	public void resize(Vector2 size) {
		// TODO Auto-generated method stub

	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
