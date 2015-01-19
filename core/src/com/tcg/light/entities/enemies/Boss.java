package com.tcg.light.entities.enemies;

import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;

public class Boss extends Enemy {

	public Boss(Vector2 pos) {
		super("boss" + Game.LEVEL, pos, 0, Game.LEVEL * 50, 3, .25f);
	}
	
	public Boss(Vector2 pos, int health) {
		super("boss" + Game.LEVEL, pos, 0, health, 3, .25f);
	}

	@Override
	protected void patrol() {
		vel.x = 0;
	}

}
