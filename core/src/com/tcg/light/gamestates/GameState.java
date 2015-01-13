package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.managers.GameStateManager;

public abstract class GameState {

	protected GameStateManager gsm;
	
	public GameState(GameStateManager gsm) {
		this.gsm = gsm;
		init();
	}
	
	protected abstract void init();
	public abstract void handleInput();
	public abstract void update(float dt);
	public abstract void draw(SpriteBatch sb, ShapeRenderer sr);
	public abstract void resize(Vector2 size);
	public abstract void dispose();
	
}
