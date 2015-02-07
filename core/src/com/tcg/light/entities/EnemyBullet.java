package com.tcg.light.entities;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
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
		tx = t.getX();
		ty = t.getY();
		dx = x- tx;
		dy = y - ty;
		r = (float) Math.tan(dy / dx);
		float d = r * MathUtils.radiansToDegrees;
		if(t.getX() < pos.x) d += 180;
		r = d * MathUtils.degreesToRadians;
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
		sr.setColor(Color.YELLOW);
		sr.line(getX(), getCenter().y, getRight(), getCenter().y);
	}

	public boolean isShouldRemove() {
		return shouldRemove;
	}

	public void setShouldRemove(boolean shouldRemove) {
		this.shouldRemove = shouldRemove;
	}

}
