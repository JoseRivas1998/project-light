package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;

public class HUD {

	private MyCamera cam;
	
	private float hx, hy, hw, hh, hw1, ax, ay, aw, ah, aw1, hsx, hsy, hsw, hsh, asx, asy, asw, ash;
	private String health, ammo;
	
	public HUD(Terry t) {
		cam = new MyCamera(Game.SIZE, true);
		setValues(t);
	}
	
	private void setValues(Terry t) {
		float ammo = (float) t.getAmmo();
		float mAmmo = (float) t.getMaxAmmo();
		float ratio = ammo / mAmmo;
		this.ammo = t.getAmmo() + "/" + t.getMaxAmmo(); 
		aw = 100;
		ah = 25;
		ax = cam.getRight() - aw - 50;
		ay = cam.getBottom() + 50;
		aw1 = ratio * aw;
		asw = Game.res.getWidth("main", this.ammo);
		ash = Game.res.getHeight("main", this.ammo);
		asx = (ax + (aw * .5f)) - (asw * .5f);
		asy = ay - 10;
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr, boolean paused, Terry t) {
		
		setValues(t);

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.YELLOW);
		sr.setProjectionMatrix(cam.combined);
		sr.rect(ax, ay, aw1, ah);
		sr.end();
		
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(ax, ay, aw, ah);
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("main").draw(sb, ammo, asx, asy);
		if(paused) {
			String p1, p2, p3;
			p1 = "Game Paused";
			p2 = "Press Enter or Start to resume";
			p3 = "Press Back or Backspace to exit";
			float pX1, pX2, pX3, pY1, pY2, pY3;
			pX1 = Game.CENTER.x - (Game.res.getWidth("main", p1) * .5f);
			pX2 = Game.CENTER.x - (Game.res.getWidth("main", p2) * .5f);
			pX3 = Game.CENTER.x - (Game.res.getWidth("main", p3) * .5f);
			
			pY2 = Game.CENTER.y + (Game.res.getHeight("main", p3) * .5f);
			pY1 = pY2 + Game.res.getHeight("main", p3) + 5;
			pY3 = pY2 - Game.res.getHeight("main", p3) - 5;

			Game.res.getFont("main").draw(sb, p1, pX1, pY1);
			Game.res.getFont("main").draw(sb, p2, pX2, pY2);
			Game.res.getFont("main").draw(sb, p3, pX3, pY3);
		}
		sb.end();
		
		sr.setColor(Color.WHITE);
	}
	
	public void resize(Vector2 size) {
		cam.resize(size, true);
	}

}
