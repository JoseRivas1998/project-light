package com.tcg.light.managers;

import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Input.Keys;

public class MyInputProcessor extends InputAdapter {

	@Override
	public boolean keyDown(int keycode) {
		if(keycode == Keys.UP || keycode == Keys.W) {
			MyInput.setKey(MyInput.UP, true);
			MyInput.setKey(MyInput.JUMP, true);
		}
		if(keycode == Keys.DOWN || keycode == Keys.S) {
			MyInput.setKey(MyInput.DOWN, true);
		}
		if(keycode == Keys.LEFT || keycode == Keys.A) {
			MyInput.setKey(MyInput.LEFT, true);
		}
		if(keycode == Keys.RIGHT || keycode == Keys.D) {
			MyInput.setKey(MyInput.RIGHT, true);
		}
		if(keycode == Keys.X || keycode == Keys.ENTER) {
			MyInput.setKey(MyInput.SHOOT, true);
		}
		if(keycode == Keys.SPACE || keycode == Keys.Z) {
			MyInput.setKey(MyInput.JUMP, true);
		}
		if(keycode == Keys.ESCAPE) {
			MyInput.setKey(MyInput.START, true);
		}
		if(keycode == Keys.BACKSPACE) {
			MyInput.setKey(MyInput.BACK, true);
		}
		if(keycode == Keys.F2) {
			MyInput.setKey(MyInput.SCREENSHOT, true);
		}
		if(keycode == Keys.F11) {
			MyInput.setKey(MyInput.FULLSCREEN, true);
		}
		MyInput.setKey(MyInput.ANY, true);
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		if(keycode == Keys.UP || keycode == Keys.W) {
			MyInput.setKey(MyInput.UP, false);
			MyInput.setKey(MyInput.JUMP, false);
		}
		if(keycode == Keys.DOWN || keycode == Keys.S) {
			MyInput.setKey(MyInput.DOWN, false);
		}
		if(keycode == Keys.LEFT || keycode == Keys.A) {
			MyInput.setKey(MyInput.LEFT, false);
		}
		if(keycode == Keys.RIGHT || keycode == Keys.D) {
			MyInput.setKey(MyInput.RIGHT, false);
		}
		if(keycode == Keys.X || keycode == Keys.ENTER) {
			MyInput.setKey(MyInput.SHOOT, false);
		}
		if(keycode == Keys.SPACE || keycode == Keys.Z) {
			MyInput.setKey(MyInput.JUMP, false);
		}
		if(keycode == Keys.ESCAPE) {
			MyInput.setKey(MyInput.START, false);
		}
		if(keycode == Keys.BACKSPACE) {
			MyInput.setKey(MyInput.BACK, false);
		}
		if(keycode == Keys.F2) {
			MyInput.setKey(MyInput.SCREENSHOT, false);
		}
		if(keycode == Keys.F11) {
			MyInput.setKey(MyInput.FULLSCREEN, false);
		}
		MyInput.setKey(MyInput.ANY, false);
		return true;
	}

}
