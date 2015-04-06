package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.entities.*;
import com.tcg.light.entities.buffs.*;
import com.tcg.light.entities.enemies.*;
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
	
	private Array<EnemyBullet> ebullets;
	
	private float stime, stimer;
	
	private String[] names;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		
		try {
			w = new World();
		} catch (LevelDoesNotExist e) {
			e.printStackTrace();
			Game.LEVEL = 0;
			try {
				w = new World();
			} catch (LevelDoesNotExist e1) {
				e1.printStackTrace();
				gsm.setState(gsm.TITLE);
			}
		}
		stime = 0;
		stimer = MathUtils.random(5);
		t = new Terry();

		stary = new Texture("backgrounds/StarlitSky.png");
		stormy = new Texture("backgrounds/StormySky.png");

		cam = new MyCamera(Game.SIZE, true);
		pcam = new MyCamera(Game.SIZE, true);
		pcam.zoom = Constants.ZOOM;
		pcam.update();
		
		names = new String[9];
		
		names[0] = "Tutorial";
		names[1] = "New Beginnings";
		names[2] = "Rip Off";
		
		hud = new HUD(names[Game.LEVEL], t);
		
		paused = false;
		
		p = new Array<Particle>();
		
		ebullets = new Array<EnemyBullet>();
		
		
		
		Game.SCORE = 0;
		
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.START)) {
			Game.res.getSound("decision").play(Game.VOLUME * .8f);
			paused = !paused;
		}
		if(paused && MyInput.keyPressed(MyInput.BACK)) {
			Game.res.getSound("decision").play(Game.VOLUME * .8f);
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
			t.upadate(w, cam, dt, false, ebullets);
			t.handleInput();
			for(EnemyBullet eb : ebullets) {
				eb.update(cam);
				if(eb.isShouldRemove()) {
					ebullets.removeValue(eb, true);
				}
			}
			t.first = false;
			for(Enemy e : w.getEnemies()) {
				e.update(w, cam, t.getBullets(), dt);
				if(e.getHealth() <= 0) {
					Game.SCORE += e.worth();
					t.incrementExp(e.expWorth());
					createParticles(e.paricles(), e.getCenter());
					if(e instanceof Pixie) {
						t.setHealth(t.getMaxHealth());
					}
					if(e instanceof Boss) {
						HealthUp h;
						AmmoUp a;
						Vector2 pos = new Vector2();
						pos.y = t.getCenter().y - 16;
						pos.x = t.getX() - 128;
						h = new HealthUp(pos);
						pos.x = t.getRight() + 128;
						a = new AmmoUp(pos);
						w.getBuffs().add(a);
						w.getBuffs().add(h);
					}
					w.getEnemies().removeValue(e, true);
				}
			}
			
			boolean enshoot = false;
			Vector2 shot = new Vector2();
			
			for(Enemy e : w.getEnemies()) {
				if(e instanceof Boss) {
					enshoot = cam.inView(e.getCenter());
					shot.set(e.getCenter());
					break;
				}
			}
			if(enshoot) {
				stime += dt;
				if(stime > stimer) {
					ebullets.add(new EnemyBullet(shot, t));
					stime = 0;
					stimer = MathUtils.random(3);
				}
			} else {
				stime = 0;
			}
		}
		
		if(Game.SCORE > Game.HIGHSCORE) {
			Game.HIGHSCORE = Game.SCORE;
		}
		
		if(t.shouldEnd) {
			gsm.setState(gsm.TITLE);
		}
		if(t.getLives() <= 0) {
			gsm.setState(gsm.GAMEOVER);
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
		sr.begin(ShapeType.Line);
		sr.setProjectionMatrix(cam.combined);
		for(EnemyBullet eb : ebullets) {
			eb.draw(sr, sb, dt);
		}
		sr.end();
		for(Enemy e : w.getEnemies()) {
			e.drawHealth(sr, cam);
		}
		
		hud.render(names[Game.LEVEL],sb, sr, paused, t);
	}

	private void drawBG(SpriteBatch sb) {
		if(Game.LEVEL < 5) {
			bg = stary;
		} else {
			bg = stormy;
		}
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
