package com.tcg.light.gamestates;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.managers.GameStateManager;
import com.tcg.light.managers.MyInput;

public class TitleState extends GameState {

	private String[] menuItems;
	
	private float tX, tY;
	
	private MyCamera cam, bgcam;
	
	private Texture tex, txtbg;
	
	private int currentItem;
	
	private float quitTime, quitTimer;
	private boolean quit;
	
	private String hs;
	private float hsx, hsy;
	
	public TitleState(GameStateManager gsm) {
		super(gsm);
	}

	@Override
	protected void init() {
		setTextValues();
		
		menuItems = new String[] {
				"New Game", "Continue", "Quit"
		};
		
		if(Game.FIRST) {
			currentItem = 0;
		} else {
			currentItem = 1;
		}
		
		quitTime = 0;
		quitTimer = .6f;
		quit = false;
		
		tex = new Texture("backgrounds/SeaofClouds.png");
		txtbg = new Texture("textbg.png");
		
		cam = new MyCamera(Game.SIZE, true);
		bgcam = new MyCamera(Game.SIZE, true);
		Game.res.getMusic("title").play();
	}

	@Override
	public void handleInput() {
			if(!quit) {
				if(MyInput.keyPressed(MyInput.UP)) {
					currentItem--;
					Game.res.getSound("cursor").play(Game.VOLUME * .8f);
				}
				if(MyInput.keyPressed(MyInput.DOWN)) {
					currentItem++;
					Game.res.getSound("cursor").play(Game.VOLUME * .8f);
				}
				if(currentItem < 0) {
					currentItem = 2;
				}
				if(currentItem > 2) {
					currentItem = 0;
				}
				if(MyInput.keyPressed(MyInput.START) || MyInput.keyPressed(MyInput.SHOOT) || (MyInput.keyPressed(MyInput.JUMP) && !MyInput.keyDown(MyInput.UP))) {
					select();
				}
			} 
	}

	private void select() {
		Game.res.getSound("decision").play(Game.VOLUME * .8f);
		if(currentItem == 0) {
			Game.LEVEL = 0;
			gsm.setState(gsm.TUTORIAL);
		}
		if(currentItem == 1) {
			if(Game.FIRST || Game.LEVEL == 0) {
				Game.LEVEL = 0;
				gsm.setState(gsm.TUTORIAL);
			} else {
				gsm.setState(gsm.PLAY);
			}
		}
		if(currentItem == 2) {
			quit = true;
		}
	}

	@Override
	public void update(float dt) {
		setTextValues();
		if(quit) {
			quitTime += dt;
			if(quitTime >= quitTimer) {
				quitTime = 0;
				Gdx.app.exit();
			}
		}
	}

	private void setTextValues() {
		tX = Game.CENTER.x - (Game.res.getWidth("large", Game.TITLE) * .5f);
		tY = (Game.SIZE.y * .75f) + (Game.res.getHeight("large", Game.TITLE) * .75f);
	}
	
	@Override
	public void draw(SpriteBatch sb, ShapeRenderer sr, float dt) {
		sb.begin();
		sb.setProjectionMatrix(bgcam.combined);
		sb.draw(tex, 0, 0, Game.SIZE.x, Game.SIZE.y);
		sb.end();
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
		Game.res.getFont("large").draw(sb, Game.TITLE, tX, tY);

		
		float w = 0;
		for(String s : menuItems) {
			if(Game.res.getWidth("mItems", s) > w) {
				w = Game.res.getWidth("mItems", s);
			}
		}
		w += 80;
		
		float h = ((tY - (Game.res.getHeight("large", Game.TITLE) * 4)) - ((Game.res.getHeight("mItems", menuItems[0]) * 2) * 0)) - (tY - (Game.res.getHeight("large", Game.TITLE) * 4)) - ((Game.res.getHeight("mItems", menuItems[2]) * 2) * 4);
		float bgy = (tY - (Game.res.getHeight("large", Game.TITLE) * 4)) - ((Game.res.getHeight("mItems", menuItems[1]) * 2) * 1);
		
		sb.end();
		
		Constants.drawTextBg(sr, Game.CENTER.x - (w * .5f), bgy - (h * .5f), w, h, cam);
		
		sb.begin();
		sb.setProjectionMatrix(cam.combined);
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
		bgcam.resize(size, true);
	}

	@Override
	public void dispose() {
		tex.dispose();
		txtbg.dispose();
	}

}
