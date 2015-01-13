package com.tcg.light;

import com.badlogic.gdx.math.Vector2;

public class Constants {

	public static float ZOOM = 1.25f;
	
	public static Vector2 paraOrigin() {
		return new Vector2((Game.SIZE.x/10) * -ZOOM, (Game.SIZE.y/10) * -ZOOM);
	}
	
}
