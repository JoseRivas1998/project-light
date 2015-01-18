package com.tcg.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	public static float ZOOM = 1.25f;
	
	public static int LEFT = 0;
	public static int RIGHT = 1;
	
	public static Vector2 paraOrigin() {
		return new Vector2((Game.SIZE.x/10) * -ZOOM, (Game.SIZE.y/10) * -ZOOM);
	}
	
	public static Vector2 randomVelocity(float speed) {
		float radians = MathUtils.random(MathUtils.PI2);
		float x = MathUtils.cos(radians) * speed;
		float y = MathUtils.sin(radians) * speed;
		return new Vector2(x, y);
	}
	
	public static int randomDirection() {
		if(MathUtils.randomBoolean()) {
			return Constants.RIGHT;
		} else {
			return Constants.LEFT;
		}
	}
	
	public static Color randomColor() {
		return new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
	}
	
}
