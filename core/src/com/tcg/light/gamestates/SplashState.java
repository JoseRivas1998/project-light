package com.tcg.light.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.entities.World;
import com.tcg.light.managers.GameStateManager;

public class SplashState extends GameState {
	
	private World w;

	private MyCamera cam;
	private MyCamera pcam;
	
	private Texture bg;
	
	private Texture stary;

	public SplashState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		
		w = new World();
		
		stary = new Texture("backgrounds/StarlitSky.png");

		cam = new MyCamera(Game.SIZE, true);
		pcam = new MyCamera(Game.SIZE, true);
		pcam.zoom = Constants.ZOOM;
		pcam.update();

	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			cam.position.y+=10;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			cam.position.y-=10;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			cam.position.x+=10;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			cam.position.x-=10;
		}
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
	}

	@Override
	public void update(float dt) {
		if(Game.LEVEL < 5) {
			bg = stary;
		}
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr) {
		drawBG(sb);
		w.render(cam);
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
	
	@Override
	public void resize(Vector2 size) {
		cam.resize(Game.SIZE, false);
		pcam.resize(Game.SIZE, false);
	}

	@Override
	public void dispose() {
		bg.dispose();
		stary.dispose();
	}

}
