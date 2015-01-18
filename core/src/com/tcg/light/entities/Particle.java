package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Constants;

public class Particle extends Entity {

	private float time, timer;
	
	private boolean shouldRemove;
	
	public Particle(Vector2 pos, float lifeTime) {
		super();
		vel.set(Constants.randomVelocity(5));
		setPosition(pos);
		setDimensions(2, 2);
		time = 0;
		timer = lifeTime;
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		time += dt;
		shouldRemove = time >= timer;
		bounds.x += vel.x;
		bounds.y += vel.y;
		sr.setColor(Constants.randomColor());
		sr.rect(getX(), getY(), getWidth(), getHeight());
		sr.setColor(Color.WHITE);
	}

	@Override
	public void dispose() {}

	public boolean isShouldRemove() {
		return shouldRemove;
	}

	public void setShouldRemove(boolean shouldRemove) {
		this.shouldRemove = shouldRemove;
	}

}
