package com.tcg.light.gamestates;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class ControlState extends GameState {

	private MyCamera cam;
	
	private Texture controller;
	
	private float time = 0, timer = 3;
	
	private float cX, cY, cW, cH;
	
	private String text;
	private float tX, tY;
	
	public ControlState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		cam = new MyCamera(Game.SIZE, true);
		controller = new Texture("xboxcontroller.png");
		setValues();
	}
	
	private void setValues() {
		cW = controller.getWidth();
		cH = controller.getHeight();
		cX = Game.CENTER.x - (cW * .5f);
		cY = Game.CENTER.y - (cH * .5f);
		
		text = "This game plays better with a controller!";
		tX = Game.res.centerX("main", text);
		tY = cY + cH + (Game.res.getHeight("main", text) * 2f);
	}
	
	@Override
	public void handleInput() {
		if(MyInput.anyKeyPressed()) {
			gsm.setState(gsm.SPLASH);
		}
	}

	@Override
	public void update(float dt) {
		setValues();
		time += dt;
		if(time > timer) {
			gsm.setState(gsm.SPLASH);
			timer = 0;
		}
	}

	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		sb.draw(controller, cX, cY);
		Game.res.getFont("main").draw(sb, text, tX, tY);
		sb.end();
	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(size, true);

	}

	@Override
	public void dispose() {
		controller.dispose();

	}

}
