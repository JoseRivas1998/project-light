package com.tcg.light.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Hornet extends Enemy {

	private Vector2 spawn;
	
	public Hornet(Vector2 pos) {
		super("hornet", pos, 1, 30);
		spawn = new Vector2(pos);
	}
	
	@Override
	protected void patrol() {
		
		move = false;
		
		bounds.x += vel.x;
		bounds.y = (16f * MathUtils.cos((bounds.x) / 6.4f)) + spawn.y;
		
		if(bounds.x > spawn.x + 50 || bounds.x < spawn.x - 50) {
			vel.x *= -1;
		}
	}

}
