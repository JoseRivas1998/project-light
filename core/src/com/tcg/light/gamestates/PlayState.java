package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.entities.*;
import com.tcg.light.entities.enemies.Enemy;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class PlayState extends GameState {
	
	private World w;
	
	private Terry t;

	private MyCamera cam;
	private MyCamera pcam;
	
	private Texture bg;
	
	private Texture stary;
	private Texture stormy;
	
	private HUD hud;
	
	private boolean paused;
	
	private Array<Particle> p;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		
		w = new World();
		
		t = new Terry();

		stary = new Texture("backgrounds/StarlitSky.png");
		stormy = new Texture("backgrounds/StormySky.png");

		cam = new MyCamera(Game.SIZE, true);
		pcam = new MyCamera(Game.SIZE, true);
		pcam.zoom = Constants.ZOOM;
		pcam.update();
		
		hud = new HUD(t);
		
		paused = false;
		
		p = new Array<Particle>();
		
		Game.SCORE = 0;
		
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.START)) {
			paused = !paused;
		}
		if(paused && MyInput.keyPressed(MyInput.BACK)) {
			gsm.setState(gsm.TITLE);
		}
	}

	@Override
	public void update(float dt) {

		
		cam.position.set(t.getPosition(), 0);
		
		if(cam.position.x < Game.CENTER.x) {
			cam.position.x = Game.CENTER.x;
		}
		if(cam.position.y < Game.CENTER.y) {
			cam.position.y = Game.CENTER.y;
		}
		if(cam.position.x > (w.getWidth() - Game.CENTER.x)) {
			cam.position.x = w.getWidth() - Game.CENTER.x;
		}
		if(cam.position.y > (w.getHeight() - Game.CENTER.y)) {
			cam.position.y = w.getHeight() - Game.CENTER.y;
		}
		
		pcam.position.set(cam.position);
		pcam.update();
		cam.update();
		
		if(!paused) {
			t.upadate(w, cam);
			t.handleInput();
			
			t.first = false;
			for(Enemy e : w.getEnemies()) {
				e.update(w, cam, t.getBullets());
				if(e.getHealth() <= 0) {
					Game.SCORE += e.worth();
					createParticles(e.paricles(), e.getCenter());
					w.getEnemies().removeValue(e, true);
				}
			}
		}
		
		if(Game.SCORE > Game.HIGHSCORE) {
			Game.HIGHSCORE = Game.SCORE;
		}
		
		if(Game.LEVEL < 5) {
			bg = stary;
		} else {
			bg = stormy;
		}
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		drawBG(sb);
		w.render(cam, sb);
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		t.draw(sr, sb, dt);
		for(Enemy e : w.getEnemies()) {
			e.draw(sr, sb, dt, cam);
		}
		sb.end();
		
		sr.begin(ShapeType.Filled);
		sr.setProjectionMatrix(cam.combined);
		t.drawLightning(sr, sb, dt);
		for(Particle pa : p) {
			pa.draw(sr, sb, dt);
			if(pa.isShouldRemove()) {
				p.removeValue(pa, true);
			}
		}
		sr.end();
		
		for(Enemy e : w.getEnemies()) {
			e.drawHealth(sr, cam);
		}
		
		hud.render(sb, sr, paused, t);
	}

	private void drawBG(SpriteBatch sb) {
		int numDrawW = (int) ((w.getWidth() * Constants.ZOOM) / bg.getWidth());
		int numDrawH = (int) ((w.getHeight() * Constants.ZOOM) / bg.getHeight());
		numDrawW++;
		numDrawH++;
		sb.begin();
		sb.setProjectionMatrix(pcam.combined);
		for(int w = 0; w <= numDrawW; w++) {
			for(int h = 0; h <= numDrawH; h++) {
			sb.draw(bg, Constants.paraOrigin().x + (bg.getWidth() * w), Constants.paraOrigin().y + (bg.getHeight() * h));
			}
		}
		sb.end();
	}
	
	private void createParticles(int amount, Vector2 pos) {
		Game.res.getSound("endie").play(Game.VOLUME * .8f);
		for(int i = 0; i < amount; i++) {
			p.add(new Particle(pos, 1));
		}
	}
	
	@Override
	public void resize(Vector2 size) {
		cam.resize(size, false);
		pcam.resize(size, false);
		hud.resize(size);
	}

	@Override
	public void dispose() {
		bg.dispose();
		stary.dispose();
		t.dispose();
		hud.dispose();
		w.dispose();
	}

}
