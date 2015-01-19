package com.tcg.light.entities.enemies;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Imp extends Enemy {

	public Imp(Vector2 pos) {
		super("imp", pos, 1, 30);
	}

	@Override
	protected void patrol() {
		if(MathUtils.randomBoolean(.01f)) {
			vel.x *= -1;
		}

	}

}
