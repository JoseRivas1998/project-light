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

public class PlayState extends GameState {
	
	private World w;

	private MyCamera cam;
	private MyCamera pcam;
	
	private Texture b;

	public PlayState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		
		w = new World();
		
		b = new Texture("badlogic.jpg");

		cam = new MyCamera(Game.SIZE, true);
		pcam = new MyCamera(Game.SIZE, true);
		pcam.zoom = Constants.ZOOM;
		pcam.update();

	}

	@Override
	public void handleInput() {
		if(Gdx.input.isKeyPressed(Keys.UP)) {
			cam.position.y+=2;
		}
		if(Gdx.input.isKeyPressed(Keys.DOWN)) {
			cam.position.y-=2;
		}
		if(Gdx.input.isKeyPressed(Keys.RIGHT)) {
			cam.position.x+=2;
		}
		if(Gdx.input.isKeyPressed(Keys.LEFT)) {
			cam.position.x-=2;
		}
		if(Gdx.input.isKeyPressed(Keys.Q)) {
			pcam.zoom += .1f;
		}
		if(Gdx.input.isKeyPressed(Keys.Z)) {
			pcam.zoom -= .1f;
		}
		if(cam.position.x < Game.CENTER.x) {
			cam.position.x = Game.CENTER.x;
		}
		if(cam.position.y < Game.CENTER.y) {
			cam.position.y = Game.CENTER.y;
		}
		pcam.position.set(cam.position);
		pcam.update();
		cam.update();
	}

	@Override
	public void update(float dt) {
		// TODO Auto-generated method stub

	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr) {
 

		int numDrawW = (int) ((w.getWidth() * Constants.ZOOM) / b.getWidth());
		int numDrawH = (int) ((w.getHeight() * Constants.ZOOM) / b.getHeight());
		numDrawW++;
		numDrawH++;
		sb.begin();
		sb.setProjectionMatrix(pcam.combined);
		for(int w = 0; w <= numDrawW; w++) {
			for(int h = 0; h <= numDrawH; h++) {
			sb.draw(b, Constants.paraOrigin().x + (b.getWidth() * w), Constants.paraOrigin().y + (b.getHeight() * h));
			}
		}
		sb.end();
		w.render(cam);
	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(Game.SIZE, false);
		pcam.resize(Game.SIZE, false);

	}

	@Override
	public void dispose() {
		b.dispose();

	}

}
