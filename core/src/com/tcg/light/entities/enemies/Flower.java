package com.tcg.light.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Flower extends Enemy {

	public Flower(Vector2 pos) {
		super("flower", pos, 1, 20);
	}

	@Override
	protected void patrol() {
		if(MathUtils.randomBoolean(.01f)) {
			vel.x *= -1;
		}

	}

}
