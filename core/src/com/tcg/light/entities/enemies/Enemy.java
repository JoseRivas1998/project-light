package com.tcg.light.entities.enemies;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.entities.*;

public abstract class Enemy extends Entity {

	protected Rectangle ls, rs, ts, bs;
	protected boolean touchingG;
	
	protected Animation rj, lj, l, r, il, ir;
	protected float stateTime;
	protected TextureRegion currentFrame;
	protected int dir;
	
	protected Texture jltemp, jrtemp, wltemp, wrtemp, irtemp, iltemp;
	
	protected int health, maxHealth;

	protected Vector2 spawn;
	
	private boolean damageB = false;
	private float dX = 0;
	
	protected boolean move;
	
	public Enemy(String folder, Vector2 pos, float speed, int health) {
		super();
		this.spawn = pos;
		setPosition(spawn);
		ls = new Rectangle();
		rs = new Rectangle();
		ts = new Rectangle();
		bs = new Rectangle();
		dir = Constants.randomDirection();
		if(dir == Constants.LEFT) {
			setVel(new Vector2(-speed, 0));
		} else {
			setVel(new Vector2(speed, 0));
		}
		initializeAnimations(folder, 1, .1f);
		this.health = health;
		this.maxHealth = health;
	}
	
	public Enemy(String folder, Vector2 pos, float speed, int health, int numIdle, float frameDura) {
		super();
		this.spawn = pos;
		setPosition(spawn);
		ls = new Rectangle();
		rs = new Rectangle();
		ts = new Rectangle();
		bs = new Rectangle();
		dir = Constants.randomDirection();
		if(dir == Constants.LEFT) {
			setVel(new Vector2(-speed, 0));
		} else {
			setVel(new Vector2(speed, 0));
		}
		initializeAnimations(folder, numIdle, frameDura);
		this.health = health;
		this.maxHealth = health;
	}
	
	protected void initializeAnimations(String folder, int numIdle, float frameDura) {
		stateTime = 0;
		
		String p = "entities/enemies/" + folder +  "/";
		int numJRFrames = 1;
		jrtemp = new Texture(p + "jumpr.png");
		TextureRegion[] jrframes = TextureRegion.split(jrtemp, jrtemp.getWidth() / numJRFrames, jrtemp.getHeight())[0];
        
        int numjlFrames = 1;
    	jltemp = new Texture(p + "jumpl.png");
    	TextureRegion[] jlframes = TextureRegion.split(jltemp, jltemp.getWidth() / numjlFrames, jltemp.getHeight())[0];
        
        int numwlFrames = 4;
        wltemp = new Texture(p + "walkl.png");
        TextureRegion[] wlframes = TextureRegion.split(wltemp,  wltemp.getWidth() / numwlFrames, wltemp.getHeight())[0];
        
        int numwrFrames = 4;
    	wrtemp = new Texture(p + "walkr.png");
    	TextureRegion[] wrframes = TextureRegion.split(wrtemp, wrtemp.getWidth() / numwrFrames, wrtemp.getHeight())[0];
        
    	int numirFrames = numIdle;
    	irtemp = new Texture(p + "idler.png");
    	TextureRegion[] irframes = TextureRegion.split(irtemp, irtemp.getWidth() / numirFrames, irtemp.getHeight())[0];

    	int numilFrames = numIdle;
    	iltemp = new Texture(p + "idlel.png");
    	TextureRegion[] ilframes = TextureRegion.split(iltemp, iltemp.getWidth() / numilFrames, iltemp.getHeight())[0];

    	
    	float frameDur = frameDura;
    	
		rj = new Animation(frameDur, jrframes);
		lj = new Animation(frameDur, jlframes);
		l = new Animation(frameDur, wlframes);
		r = new Animation(frameDur, wrframes);
		il = new Animation(frameDur, ilframes);
		ir = new Animation(frameDur, irframes);
		
		currentFrame = irframes[0];
		
		move = true;
	}

	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt, MyCamera cam) {
		if(cam.inView(getCenter())) {
			draw(sr, sb, dt);
		}
		
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		
		stateTime += dt;
		
		if(touchingG || !move) {
			if(vel.x == 0) {
				if(dir == Constants.LEFT) {
					currentFrame = il.getKeyFrame(stateTime, true);
				}
				if(dir == Constants.RIGHT) {
					currentFrame = ir.getKeyFrame(stateTime, true);
				}
			} else {
				if(dir == Constants.LEFT) {
					currentFrame = l.getKeyFrame(stateTime, true);
				}
				if(dir == Constants.RIGHT) {
					currentFrame = r.getKeyFrame(stateTime, true);
				}
			}
		} else {
			if(dir == Constants.LEFT) {
				currentFrame = lj.getKeyFrame(stateTime, true);
			}
			if(dir == Constants.RIGHT) {
				currentFrame = rj.getKeyFrame(stateTime, true);
			}
		}
		
		sb.draw(currentFrame, getCenter().x - (currentFrame.getRegionWidth() * .5f), getCenter().y - (currentFrame.getRegionHeight() * .5f));
		if(damageB && MathUtils.randomBoolean()) {
			sb.draw(damage, getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public void drawHealth(ShapeRenderer sr, MyCamera cam) {
		if(cam.inView(getCenter())) {
			float width = bounds.width;
			float height = 10;
			float ratio = (float) health / (float) maxHealth;
			float rWidth = width * ratio;
			sr.begin(ShapeType.Filled);
			sr.setColor(Color.RED);
			sr.rect(getX(), getY() - height - 2, rWidth, height, new Color(1, 0, 0, 1), new Color(1, .5f, .5f, 1), new Color(1, .5f, .5f, 1), new Color(1, 0, 0, 1));
			sr.end();
			sr.begin(ShapeType.Line);
			sr.setColor(Color.WHITE);
			sr.rect(getX(), getY() - height - 2, width, height);
			sr.end();
		}
	}

	public void update(World w, MyCamera cam, Array<Bullet> b, float dt) {
		if(cam.inView(getCenter()) || Game.fps > 57) {
			bounds.width = currentFrame.getRegionWidth();
			bounds.height = currentFrame.getRegionHeight();
			
			if(!touchingG && vel.y > -20) {
				vel.y--;
			}
			
			if(vel.x > 0) {
				dir = Constants.RIGHT;
			} else {
				dir = Constants.LEFT;
			}
			
			if(move) {
				bounds.x += vel.x;
				bounds.y += vel.y;
			}
			
			if(bounds.x < 0) {
				bounds.x = 0;
			}
			if(getRight() > w.getWidth()) {
				bounds.x = w.getWidth() - bounds.width;
			}
			
			resetBounds();
			collisions(w);
			resetBounds();
			patrol();
			for(Bullet bu : b) {
				if(bu.collidingWith(this)) {
					Game.res.getSound("hit").play(Game.VOLUME * .8f);
					this.health -= 5;
					damageB = true;
					b.removeValue(bu, true);
				}
			}
			setDamage(dt);
		}
		
		if(getCenter().y <= 1) {
			dir = Constants.randomDirection();
			float speed = vel.x;
			if(dir == Constants.LEFT) {
				setVel(new Vector2(-speed, 0));
			} else {
				setVel(new Vector2(speed, 0));
			}
			setPosition(spawn);
		}
	}

	
	private void setDamage(float dt) {
		if(damageB) {
			dX += dt;
			if(dX > 0.25f) {
				damageB = false;
			}
		} else {
			dX = 0;
		}
	}
	private void resetBounds() {
		ls.width = 2;
		rs.width = 2;
		ts.width = 4;
		bs.width = 4;
		ls.height = 4;
		rs.height = 4;
		ts.height = 2;
		bs.height = 2;
		
		ls.x = bounds.x;
		ls.y = getCenter().y - (ls.height * .5f);
		rs.x = getRight() - rs.width;
		rs.y = ls.y;
		bs.x = getCenter().x - (bs.width * .5f);
		bs.y = bounds.y;
		ts.x = bs.x;
		ts.y = getTop() - ts.height;
	}
	
	private void collisions(World w) {
		for(Rectangle r : w.getBounds()) {
			if(bs.overlaps(r)) {
				bounds.y = r.y + r.height - (bounds.height / 8);
				touchingG = true;
				break;
			} else {
				touchingG = false;
			}
		}
		resetBounds();
		boolean touchingLeft, touchingRight;
		touchingLeft = false;
		touchingRight = false;
		for(Rectangle r : w.getBounds()) {
			if(rs.overlaps(r)) {
				bounds.x = r.x - bounds.width;
				touchingRight = true;
				break;
			} 
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(ls.overlaps(r)) {
				bounds.x = r.x + bounds.width;
				touchingLeft = true;
				break;
			}
		}
		if(touchingG) {
			vel.y = 0;
			if(touchingLeft || touchingRight) {
				vel.y = 17.5f;
			}
		}
	}
	
	protected abstract void patrol();
	
	public int worth() {
		return (maxHealth / 5) * 100;
	}
	
	public int expWorth() {
		return maxHealth / 5;
	}
	
	public int paricles() {
		return (maxHealth / 5) * 3;
	}
	
	@Override
	public void dispose() {
		super.dispose();
		jltemp.dispose();
		jrtemp.dispose();
		wltemp.dispose();
		wrtemp.dispose();
		irtemp.dispose();
		iltemp.dispose();

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
