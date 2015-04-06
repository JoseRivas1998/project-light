package com.tcg.light.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Pixie extends Enemy {

	private Vector2 spawn;
	
	public Pixie(Vector2 pos) {
		super("pixie", pos, 1, 30);
		spawn = new Vector2(pos);
	}
	
	@Override
	protected void patrol() {
		
		move = false;
		
		bounds.x += vel.x;
		bounds.y = (32f * MathUtils.sin((.4f * MathUtils.PI * bounds.x) /10f)) + spawn.y;
		
		if(MathUtils.randomBoolean(.01f)) {
			vel.x *= -1;
		}
	}

}
