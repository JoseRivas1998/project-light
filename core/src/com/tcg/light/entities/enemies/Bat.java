package com.tcg.light.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Bat extends Enemy {

	private float deg;
	private final float radius = 32;
	private float speed;
	private Vector2 spawn;
	
	public Bat(Vector2 pos) {
		super("bat", pos, 1, 35);
		deg = 0;
		spawn = new Vector2(pos);
	}

	@Override
	protected void patrol() {
		move = false;
		if(deg < 180) {
			vel.x = -1;
			speed = 2.5f;
		} else {
			vel.x = 1;
			speed = 5f;
		}
		deg += speed;
		
		float r = deg * MathUtils.degreesToRadians;
		float x = spawn.x + (MathUtils.cos(r) * radius);
		float y = spawn.y + (MathUtils.sin(r) * radius);
		
		setPosition(x, y);
		
		if(deg >= 360) {
			deg = 0;
		}
	}

}
