package com.tcg.light;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

public class Constants {

	public static float ZOOM = 1.25f;
	
	public static int LEFT = 0;
	public static int RIGHT = 1;

	//XBox Mappings
	public static final int A = 0;
	public static final int B = 1;
	public static final int X = 2;
	public static final int Y = 3;
	public static final int LB = 4;
	public static final int RB = 5;
	public static final int BACK = 6;
	public static final int START = 7;
	public static final int L3 = 8;
	public static final int R3 = 9;
	
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
	
	public static float distance(float x1,float y1,float x2,float y2) {
		float a = (x2 - x1);
		float b = (y2 - y1);
		return (float) Math.sqrt((Math.pow(a, 2)) + (Math.pow(b, 2)));
	}
	
	public static float distance(Vector2 p1, Vector2 p2) {
		return distance(p1.x, p1.y, p2.x, p2.y);
	}
	
	public static Color randomColor() {
		return new Color(MathUtils.random(), MathUtils.random(), MathUtils.random(), 1);
	}
	
	public static Color rgb(float r, float g, float b, float a) {
		return new Color(r / 255f, g / 255f, b / 255f, a / 255f);
	}

	public static void drawTextBg(ShapeRenderer sr, float x, float y, float w, float h, MyCamera cam) {
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		Color bL = Constants.rgb(45, 50, 107, 255);
		Color bR = Constants.rgb(41, 42, 65, 255);
		Color tR = Constants.rgb(45, 50, 107, 255);
		Color tL = Constants.rgb(112, 118, 176, 255);
		sr.rect(x, y, w, h, bL, bR, tR, tL);
		sr.end();
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(x, y, w, h);
		sr.end();
	}
}
