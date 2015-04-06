package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.Constants;
import com.tcg.light.Game;
import com.tcg.light.MyCamera;

public class EnemyBullet extends Entity {

	private float x, y, tx, ty, dx, dy, r;
	private boolean shouldRemove;
	
	public EnemyBullet(Vector2 pos, Terry t) {
		super();
		bounds.width = 5;
		bounds.height = 2;
		setPosition(pos);
		float speed = 20;
		x = pos.x;
		y = pos.y;
		tx = t.getCenter().x;
		ty = t.getCenter().y;
		dx = tx- x;
		dy = ty - y;
		r = MathUtils.atan2(dy, dx);
		vel.x = MathUtils.cos(r) * speed;
		vel.y = MathUtils.sin(r) * speed;
		shouldRemove = false;
		Game.res.getSound("shoot").play(Game.VOLUME * .8f);
	}
	
	public void update(MyCamera cam) {
		bounds.x += vel.x;
		bounds.y += vel.y;
		if(!cam.inView(this)) {
			shouldRemove = true;
		}
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		float x = getX();
		float y = getY();
		float l = Constants.distance(getX(), getY(), getRight(), getTop());
		float tx = x + (MathUtils.cos(r) * l);
		float ty = y + (MathUtils.sin(r) * l);
		sr.setColor(Color.YELLOW);
		sr.line(x, y, tx, ty);
	}

	public boolean isShouldRemove() {
		return shouldRemove;
	}

	public void setShouldRemove(boolean shouldRemove) {
		this.shouldRemove = shouldRemove;
	}

}
