package com.tcg.light.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class TitleState extends GameState {

	private String[] menuItems;
	
	private float tX, tY;
	
	private MyCamera cam;
	
	private int currentItem;
	
	public TitleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		setTextValues();
		
		menuItems = new String[] {
				"New Game", "Continue", "Quit"
		};
		
		currentItem = 0;
		
		cam = new MyCamera(Game.SIZE, true);
	}

	@Override
	public void handleInput() {
		if(MyInput.keyPressed(MyInput.UP)) {
			currentItem--;
		}
		if(MyInput.keyPressed(MyInput.DOWN)) {
			currentItem++;
		}
		if(currentItem < 0) {
			currentItem = 0;
		}
		if(currentItem > 2) {
			currentItem = 2;
		}
		if(MyInput.keyPressed(MyInput.START) || MyInput.keyPressed(MyInput.SHOOT) || MyInput.keyPressed(MyInput.JUMP)) {
			select();
		}
	}

	private void select() {
		if(currentItem == 0) {
			gsm.setState(gsm.PLAY);
		}
		if(currentItem == 1) {
			gsm.setState(gsm.PLAY);
		}
		if(currentItem == 2) {
			Gdx.app.exit();
		}
	}

	@Override
	public void update(float dt) {
		setTextValues();

	}

	private void setTextValues() {
		tX = Game.CENTER.x - (Game.res.getWidth("large", Game.TITLE) * .5f);
		tY = (Game.SIZE.y * .75f) + (Game.res.getHeight("large", Game.TITLE) * .75f);
	}
	
	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("large").draw(sb, Game.TITLE, tX, tY);
		
		for(int i = 0; i < menuItems.length; i++) {
			if(i == currentItem) {
				Game.res.getFont("mItems").setColor(Color.RED);
			} else {
				Game.res.getFont("mItems").setColor(Color.WHITE);
			}
			String s = menuItems[i];
			float x, y;
			x = Game.CENTER.x - (Game.res.getWidth("mItems", s) * .5f);
			y = (tY - (Game.res.getHeight("large", Game.TITLE) * 4)) - ((Game.res.getHeight("mItems", s) * 2) * i);
			Game.res.getFont("mItems").draw(sb, s, x, y);
		}
		
		sb.end();

	}

	@Override
	public void resize(Vector2 size) {
		cam.resize(size, true);
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

}
