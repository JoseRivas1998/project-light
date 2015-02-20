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
import com.tcg.light.entities.buffs.*;
import com.tcg.light.entities.enemies.*;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class TutorialState extends GameState {
	
	private World w;
	
	private Terry t;

	private MyCamera cam;
	private MyCamera pcam;
	
	private Texture bg1;
	
	private Texture sa;
	private Texture so;
	
	private HUD hud;
	
	private boolean paused;
	
	private Array<Particle> p;
	
	private final Array<EnemyBullet> eb = new Array<EnemyBullet>();

	public TutorialState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		
		Game.SCORE = 0;
		Game.FIRST = false;
		Game.HIGHSCORE = 0;
		Game.MAXHEALTH = 100;
		Game.MAXAMMO = 30;
		Game.LIVES = 5;
		Game.EXP = 0;
		Game.TONEXT = 250;
		Game.TIER = 1;
		
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
		
		t = new Terry();

		sa = new Texture("backgrounds/StarlitSky.png");
		so = new Texture("backgrounds/StormySky.png");

		cam = new MyCamera(Game.SIZE, true);
		pcam = new MyCamera(Game.SIZE, true);
		pcam.zoom = Constants.ZOOM;
		pcam.update();
		
		hud = new HUD("", t);
		
		paused = false;
		
		p = new Array<Particle>();
		t.setAmmo(0);
		
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
			t.upadate(w, cam, dt, true, eb);
			t.handleInput();
			
			t.first = false;
			for(Enemy e : w.getEnemies()) {
				e.update(w, cam, t.getBullets(), dt);
				if(e.getHealth() <= 0) {
					Game.SCORE += e.worth();
					createParticles(e.paricles(), e.getCenter());
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
		}
		
		if(Game.SCORE > Game.HIGHSCORE) {
			Game.HIGHSCORE = Game.SCORE;
		}
		
		if(Game.LEVEL < 5) {
			bg1 = sa;
		} else {
			bg1 = so;
		}
		
		if(t.shouldEnd) {
			Game.SCORE = 0;
			Game.HIGHSCORE = 0;
			Game.LEVEL = 1;
			Game.MAXHEALTH = 100;
			Game.MAXAMMO = 30;
			Game.LIVES = 5;
			Game.EXP = 0;
			Game.TONEXT = 250;
			Game.TIER = 1;
			gsm.setState(gsm.PLAY);
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
		String todraw;
		String s1 = "Welcome to " + Game.TITLE + "!, Use the Arrow Keys, WASD, DPad, or Left Stick to move";
		String s2 = "Press Space, Z, or A (on controller) to jump!";
		String s3 = "Beware of enemies! They deal damage to you if you touch them. To get rid of them, shoot them with Enter, X, or B(on Controller). If you would like to rapid fire, hold down C, Right Shift, or X(on Controller) at the cost of ammo, check bottom right.";
		String s4 = "These are buffs, they either give you 10 health or ammo, or refill your health or ammo!";
		String s5 = "At the top of these stairs, there will be a dummy boss. Defeating a boss lets you choose between upgrading health or ammo. Once you upgrade, you will progress to the next level";
		if(t.getX() < 22 * 32) {
			todraw = s1;
		} else if(t.getX() < 50 * 32) {
			todraw = s2;
		} else if(t.getX() < 75 * 32) {
			todraw = s3;
		} else if(t.getX() < 100 * 32) {
			todraw = s4;
		} else {
			todraw = s5;
		}
		Game.res.getFont("main").drawWrapped(sb, todraw, cam.getLeft(), cam.position.y + (Game.CENTER.y * .5f), Game.CENTER.x);
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
		
		hud.render("", sb, sr, paused, t);
	}

	private void drawBG(SpriteBatch sb) {
		int numDrawW = (int) ((w.getWidth() * Constants.ZOOM) / bg1.getWidth());
		int numDrawH = (int) ((w.getHeight() * Constants.ZOOM) / bg1.getHeight());
		numDrawW++;
		numDrawH++;
		sb.begin();
		sb.setProjectionMatrix(pcam.combined);
		for(int w = 0; w <= numDrawW; w++) {
			for(int h = 0; h <= numDrawH; h++) {
			sb.draw(bg1, Constants.paraOrigin().x + (bg1.getWidth() * w), Constants.paraOrigin().y + (bg1.getHeight() * h));
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
		bg1.dispose();
		sa.dispose();
		t.dispose();
		hud.dispose();
		w.dispose();
	}

}
