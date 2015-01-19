package com.tcg.light.entities.buffs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.tcg.light.entities.Entity;

public abstract class Buff extends Entity {

	private Texture t;
	
	public Buff(String path, Vector2 pos) {
		super();
		t = new Texture("entities/buffs/" + path);
		bounds.width = t.getWidth();
		bounds.height = t.getHeight();
		setPosition(pos);
	}

	@Override
	public void draw(ShapeRenderer sr, SpriteBatch sb, float dt) {
		sb.draw(t, getX(), getY());
	}

	@Override
	public void dispose() {
		super.dispose();
		t.dispose();
	}

}
