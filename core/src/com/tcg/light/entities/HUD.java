package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;

public class HUD {

	private MyCamera cam;
	
	private float healthX, healthY, heathW, helathH, healthBarW, healthStringX, healthStringY, healthStringW;
	
	private float ammoX, ammoY, ammoW, ammoH, ammoBarW, ammoStringX, ammoStringY, ammoStringW;
	
	private float scoreX, scoreY, scoreW, highScoreX, highScoreY, highScoreW;
	
	private float faceX, faceY, faceW, faceH;
	
	private float livesX, livesY, livesH;
	
	private Texture face;
	
	private String health, ammo, score, highScore, lives;
	
	public HUD(Terry t) {
		cam = new MyCamera(Game.SIZE, true);
		face = new Texture("hud/My Face.png");
		setValues(t);
	}
	
	private void setValues(Terry t) {
		float health = (float) t.getHealth();
		float mHealth = (float) t.getMaxHealth();
		float hRatio = health / mHealth;
		this.health = t.getHealth() + "/" + t.getMaxHealth();
		
		float ammo = (float) t.getAmmo();
		float mAmmo = (float) t.getMaxAmmo();
		float aRatio = ammo / mAmmo;
		this.ammo = t.getAmmo() + "/" + t.getMaxAmmo(); 
		
		heathW = 100;
		helathH = 25;
		healthX = cam.getLeft() + 10;
		healthY = cam.getTop() - helathH - 25;
		healthBarW = hRatio * heathW;
		healthStringW = Game.res.getWidth("main", this.health);
		healthStringX = (healthX + (heathW * .5f)) - (healthStringW * .5f);
		healthStringY = healthY - 10;
		
		ammoW = 100;
		ammoH = 25;
		ammoX = cam.getRight() - ammoW - 10;
		ammoY = cam.getBottom() + 50;
		ammoBarW = aRatio * ammoW;
		ammoStringW = Game.res.getWidth("main", this.ammo);
		ammoStringX = (ammoX + (ammoW * .5f)) - (ammoStringW * .5f);
		ammoStringY = ammoY - 10;
		
		score = "Score: " + Game.getScore(Game.SCORE);
		highScore = "High Score: " + Game.getScore(Game.HIGHSCORE);
		scoreW = Game.res.getWidth("main", score);
		scoreY = cam.getTop() - 25;
		scoreX = (cam.getRight() - 10) - scoreW;
		
		highScoreW = Game.res.getWidth("main", highScore);
		highScoreY = scoreY - 25;
		highScoreX = (cam.getRight() - 10) - highScoreW;
		
		faceW = face.getWidth() * .5f;
		faceH = face.getHeight() * .5f;
		faceX = cam.getLeft() + 10;
		faceY = cam.getBottom() + 10;
		
		lives = t.getLives() + "";
		livesH = Game.res.getHeight("main", lives);
		livesX = faceX + faceW + 10;
		livesY = faceY + livesH;
		
		
	}
	
	public void render(SpriteBatch sb, ShapeRenderer sr, boolean paused, Terry t) {
		
		setValues(t);

		sr.begin(ShapeType.Filled);
		sr.setColor(Color.YELLOW);
		sr.setProjectionMatrix(cam.combined);
		sr.rect(ammoX, ammoY, ammoBarW, ammoH);
		sr.setColor(Color.RED);
		sr.rect(healthX, healthY, healthBarW, helathH);
		sr.end();
		
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(cam.combined);
		sr.setColor(Color.WHITE);
		sr.rect(ammoX, ammoY, ammoW, ammoH);
		sr.rect(healthX, healthY, heathW, helathH);
		sr.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("main").draw(sb, ammo, ammoStringX, ammoStringY);
		Game.res.getFont("main").draw(sb, health, healthStringX, healthStringY);
		Game.res.getFont("main").draw(sb, score, scoreX, scoreY);
		Game.res.getFont("main").draw(sb, highScore, highScoreX, highScoreY);
		Game.res.getFont("main").draw(sb, lives, livesX, livesY);
		sb.draw(face, faceX, faceY, faceW, faceH);
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
	
	public void dispose() {
		face.dispose();
	}

}
