package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Constants;

public class Bullet extends Entity {

	public Bullet(int direction, Vector2 pos) {
		super();
		if(direction == Constants.RIGHT) {
			vel.set(20, 0);
		} else {
			vel.set(-20, 0);
		}
		setPosition(pos);
		bounds.width = 5;
		bounds.height = 2;
	}

	public void update() {
		bounds.x += vel.x;
		bounds.y += vel.y;
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		// TODO Auto-generated method stub
		sr.setColor(Color.YELLOW);
		sr.rect(getX(), getY(), getWidth(), getHeight());
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
