package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class SplashState extends GameState {
	
	private float time, timer;

	private Texture temp;
	private Animation anim;
	private TextureRegion currentFrame;
	private float stateTime;
	private MyCamera cam;
	private float aX, aY, aW;
	private String tiny, presents;
	private float tX, tY, tW, tH, pX, pY, pW;
	
	public SplashState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(Game.SIZE, true);
		time = 0;
		Game.res.getSound("splash").play(Game.VOLUME);
		
		stateTime = 0;
		
		TextureRegion[] frames = new TextureRegion[16];
		String path = "splash/0";
		for(int i = 0; i < frames.length; i++) {
			String file;
			if(i < 9) {
				file = path + "0" + (i + 1) + ".png";
			} else {
				file = path + (i + 1) + ".png";
			}
			temp = new Texture(file);
			frames[i] = new TextureRegion(temp);
		}
		
		anim = new Animation(.15f, frames);
		currentFrame = frames[0];
		setValues();
		timer = (anim.getAnimationDuration() * 2f);
	}

	@Override
	public void handleInput() {
		if(MyInput.anyKeyPressed()) {
			Game.res.stopAllSound();
			gsm.setState(gsm.TITLE);
		}
	}

	@Override
	public void update(float dt) {
		setValues();
		time += dt;
		if(time >= timer) {
			gsm.setState(gsm.TITLE);
		}
	}
	
	private void setValues() {
		tiny = "Tiny Country Games";
		presents = "Presents";
		tW = Game.res.getWidth("large", tiny);
		tH = Game.res.getHeight("large", tiny);
		tX = Game.CENTER.x - (tW * .5f);
		tY = Game.CENTER.y + (tH * .5f);
		aW = currentFrame.getRegionWidth();
		aX = Game.CENTER.x - (aW * .5f);
		aY = tY + tH;
		pW = Game.res.getWidth("large", presents);
		pX = Game.CENTER.x - (pW * .5f);
		pY = tY - (tH * 2);
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		stateTime += dt;

		currentFrame = anim.getKeyFrame(stateTime, true);
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		sb.draw(currentFrame, aX, aY);
		Game.res.getFont("large").draw(sb, tiny, tX, tY);
		Game.res.getFont("large").draw(sb, presents, pX, pY);
		sb.end();
	}
	
	@Override
	public void resize(Vector2 size) {
		cam.resize(size, true);
	}

	@Override
	public void dispose() {
		temp.dispose();
	}

}
