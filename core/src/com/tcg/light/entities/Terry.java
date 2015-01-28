package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;
import com.tcg.light.entities.buffs.*;
import com.tcg.light.entities.enemies.Enemy;
import com.tcg.light.managers.MyInput;

public class Terry extends Entity {

	private Rectangle ls, rs, ts, bs;
	private boolean touchingG, pTouchingG;
	
	private Animation rj, lj, l, r, il, ir;
	private float stateTime;
	private TextureRegion currentFrame;
	private int dir;
	
	private Texture jltemp, jrtemp, wltemp, wrtemp, irtemp, iltemp;
	
	public boolean first;
	
	private int health, maxHealth, ammo, maxAmmo, lives, pScore;
	
	private Array<Bullet> bullets;
	
	private boolean damageB = false;
	private float dX = 0;
	
	private boolean takingDamage, pTakingDamage;
	
	public boolean shouldEnd;
	
	public Terry() {
		super();
		setPosition(96, 32);
		ls = new Rectangle();
		rs = new Rectangle();
		ts = new Rectangle();
		bs = new Rectangle();
		pTouchingG = true;
		dir = Constants.RIGHT;
		bullets = new Array<Bullet>();
		initializeAnimations();
		first = true;
		maxHealth = Game.MAXHEALTH;
		maxAmmo = Game.MAXAMMO;
		health = maxHealth;
		ammo = maxAmmo;
		takingDamage = false;
		pTakingDamage = false;
		lives = Game.LIVES;
		pScore = Game.SCORE;
		shouldEnd = false;
	}
	
	private void initializeAnimations() {
		stateTime = 0;
		
		String p = "entities/terry/";
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
        
    	int numirFrames = 1;
    	irtemp = new Texture(p + "idler.png");
    	TextureRegion[] irframes = TextureRegion.split(irtemp, irtemp.getWidth() / numirFrames, irtemp.getHeight())[0];

    	int numilFrames = 1;
    	iltemp = new Texture(p + "idlel.png");
    	TextureRegion[] ilframes = TextureRegion.split(iltemp, iltemp.getWidth() / numilFrames, iltemp.getHeight())[0];

    	
    	float frameDur = .1f;
    	
		rj = new Animation(frameDur, jrframes);
		lj = new Animation(frameDur, jlframes);
		l = new Animation(frameDur, wlframes);
		r = new Animation(frameDur, wrframes);
		il = new Animation(frameDur, ilframes);
		ir = new Animation(frameDur, irframes);
		
		currentFrame = irframes[0];
	}

	public void handleInput() {
		if(MyInput.keyDown(MyInput.LEFT)) {
			vel.x = -5;
			dir = Constants.LEFT;
		} else if(MyInput.keyDown(MyInput.RIGHT)) {
			vel.x = 5;
			dir = Constants.RIGHT;
		} else {
			vel.x = 0;
		}
		if(bounds.y < 0) {
			vel.x = 0;
		}
		if(touchingG) {
			vel.y = 0;
			if(MyInput.keyPressed(MyInput.JUMP) && !first) {
				vel.y = 17.5f;
				Game.res.getSound("jump").play(Game.VOLUME * .8f);
			}
		}
		if(!first) {
			if(ammo > 0) {
				if(MyInput.keyDown(MyInput.SHOOT)) {
					if(bullets.size < 5) {
						Game.res.getSound("shoot").play(Game.VOLUME * .8f);
						bullets.add(new Bullet(dir, getCenter()));
						if(ammo > 0) {
							ammo--;
						} else {
							ammo = 0;
						}
					}
				}
			} else {
				if(MyInput.keyPressed(MyInput.SHOOT)) {
					if(bullets.size < 5) {
						Game.res.getSound("shoot").play(Game.VOLUME * .8f);
						bullets.add(new Bullet(dir, getCenter()));
					}
				}
			}
		}
	}
	
	public void upadate(World w, MyCamera cam, float dt, boolean tutorial) {
		bounds.width = 32;
		bounds.height = 32;
		
		if(!touchingG && vel.y > -20) {
			vel.y--;
		}
		
		bounds.x += vel.x;
		bounds.y += vel.y;
		
		if(bounds.x < 0) {
			bounds.x = 0;
		}
		if(getRight() > w.getWidth()) {
			bounds.x = w.getWidth() - bounds.width;
		}
		
		if(getTop() < -500 || health <= 0) {
			bounds.x = 96;
			bounds.y = 32;
			w.resetEnemies();
			Game.SCORE = pScore;
			health = maxHealth;
			lives--;
		}
		
		resetBounds();
		collisions(w, tutorial);
		resetBounds();
		
		updateBullets(w, cam);
		
		if(touchingG && !pTouchingG) {
			Game.res.getSound("land").play(Game.VOLUME * .8f);
		}

		Game.MAXAMMO = maxAmmo;
		Game.MAXHEALTH = maxHealth;
		Game.LIVES = lives;
		
		setDamage(dt);
		
		pTouchingG = touchingG;
		pTakingDamage = takingDamage;
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
	
	private void updateBullets(World w, MyCamera cam) {
		for(Bullet b : bullets) {
			b.update();
			for(Rectangle r : w.getBounds()) {
				if(b.collidingWith(r)) {
					bullets.removeValue(b, true);
					return;
				}
			}
			if(!cam.inView(b.getCenter())) {
				bullets.removeValue(b, true);
			}
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
	
	private void collisionGround(World w, boolean tutorial) {
		for(Rectangle r : w.getBounds()) {
			if(bs.overlaps(r)) {
				bounds.y = r.y + r.height - 4;
				touchingG = true;
				break;
			} else {
				touchingG = false;
			}
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(rs.overlaps(r)) {
				bounds.x = r.x - bounds.width;
				break;
			} 
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(ls.overlaps(r)) {
				bounds.x = r.x + bounds.width;
				break;
			}
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(ts.overlaps(r)) {
				bounds.y = r.y - bounds.height;
				vel.y = 0;
				break;
			}
		}
		resetBounds();
		for(Rectangle r : w.getBounds()) {
			if(bs.overlaps(r) && ts.overlaps(r)) {
				bounds.y = r.y + r.height - 4;
				break;
			} 
		}
	}
	
	private void collisions(World w, boolean tutorial) {
		collisionGround(w, tutorial);
		for(Buff b : w.getBuffs()) {
			if(collidingWith(b)) {
				if(b instanceof SmallAmmo) {
					if(ammo <= maxAmmo - 10) {
						ammo += 10;
					} else {
						ammo = maxAmmo;
					}
				}
				if(b instanceof SmallHealth) {
					if(health <= maxHealth - 10) {
						health += 10;
					} else {
						health = maxHealth;
					}
				}
				if(b instanceof FullHealth) {
					health = maxHealth;
				}
				if(b instanceof FullAmmo) {
					ammo = maxAmmo;
				}
				if(b instanceof Stock) {
					lives++;
				}
				if(b instanceof HealthUp) {
					maxHealth += 10;
					try {
						if(tutorial) {
							shouldEnd = true;
						} else {
							w.newLevel();
						}
					} catch (LevelDoesNotExist e) {
						e.printStackTrace();
						maxHealth -= 10;
						shouldEnd = true;
					}
				}
				if(b instanceof AmmoUp) {
					maxAmmo += 5;
					try {
						if(tutorial) {
							shouldEnd = true;
						} else {
							w.newLevel();
						}
					} catch (LevelDoesNotExist e) {
						e.printStackTrace();
						maxAmmo -= 5;
						shouldEnd = true;
					}
				}
				w.getBuffs().removeValue(b, true);
			}
		}
		for(Enemy e : w.getEnemies()) {
			if(collidingWith(e)) {
				if(dir == Constants.RIGHT) {
					bounds.x = e.getX() - bounds.width - 5;
					dir = Constants.LEFT;
				} else {
					bounds.x = e.getX() - e.getWidth() + 5;
					dir = Constants.RIGHT;
				}
				if(!pTakingDamage) {
					Game.res.getSound("hit").play(Game.VOLUME * .5f);
					health -= 5;
					damageB = true;
				}
				takingDamage = true; 
				break;
			}
			takingDamage = false;
		}
		collisionGround(w, tutorial);
	}
	
	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		
		stateTime += dt;
		
		if(touchingG) {
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
		if(damageB) {
			sb.draw(damage, getX(), getY(), getWidth(), getHeight());
		}
	}
	
	public void drawLightning(ShapeRenderer sr, SpriteBatch sb, float dt) {
		for(Bullet b : bullets) {
			b.draw(sr, sb, dt);
		}
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

	public int getMaxHealth() {
		return maxHealth;
	}

	public void setMaxHealth(int maxHealth) {
		this.maxHealth = maxHealth;
	}

	public int getAmmo() {
		return ammo;
	}

	public void setAmmo(int ammo) {
		this.ammo = ammo;
	}

	public int getMaxAmmo() {
		return maxAmmo;
	}

	public void setMaxAmmo(int maxAmmo) {
		this.maxAmmo = maxAmmo;
	}

	public int getLives() {
		return lives;
	}

	public void setLives(int lives) {
		this.lives = lives;
	}

	public Array<Bullet> getBullets() {
		return bullets;
	}

	public void setBullets(Array<Bullet> bullets) {
		this.bullets = bullets;
	}

}
