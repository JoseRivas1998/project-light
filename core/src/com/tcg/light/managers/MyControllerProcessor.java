package com.tcg.light.managers;

import com.badlogic.gdx.controllers.Controller;
import com.badlogic.gdx.controllers.ControllerAdapter;
import com.badlogic.gdx.controllers.PovDirection;
import com.tcg.light.Constants;

public class MyControllerProcessor extends ControllerAdapter {

	@Override
	public boolean buttonDown(Controller controller, int buttonIndex) {
		if(buttonIndex == Constants.A) {
			MyInput.setKey(MyInput.JUMP, true);
		}
		if(buttonIndex == Constants.B) {
			MyInput.setKey(MyInput.SHOOT, true);
		}
		if(buttonIndex == Constants.X) {
			MyInput.setKey(MyInput.AUTO, true);
		}
		if(buttonIndex == Constants.LB) {
			MyInput.setKey(MyInput.SCREENSHOT, true);
		}
		if(buttonIndex == Constants.RB) {
			MyInput.setKey(MyInput.FULLSCREEN, true);
		}
		if(buttonIndex == Constants.BACK) {
			MyInput.setKey(MyInput.BACK, true);
		}
		if(buttonIndex == Constants.START) {
			MyInput.setKey(MyInput.START, true);
		}
		MyInput.setKey(MyInput.ANY, true);
		return true;
	}

	@Override
	public boolean buttonUp(Controller controller, int buttonIndex) {
		if(buttonIndex == Constants.A) {
			MyInput.setKey(MyInput.JUMP, false);
		}
		if(buttonIndex == Constants.B) {
			MyInput.setKey(MyInput.SHOOT, false);
		}
		if(buttonIndex == Constants.X) {
			MyInput.setKey(MyInput.AUTO, false);
		}
		if(buttonIndex == Constants.LB) {
			MyInput.setKey(MyInput.SCREENSHOT, false);
		}
		if(buttonIndex == Constants.RB) {
			MyInput.setKey(MyInput.FULLSCREEN, false);
		}
		if(buttonIndex == Constants.BACK) {
			MyInput.setKey(MyInput.BACK, false);
		}
		if(buttonIndex == Constants.START) {
			MyInput.setKey(MyInput.START, false);
		}
		MyInput.setKey(MyInput.ANY, false);
		return true;
	}

	@Override
	public boolean povMoved(Controller controller, int povIndex,
			PovDirection value) {
		if(value == PovDirection.north || value == PovDirection.northEast || value == PovDirection.northWest) {
			MyInput.setKey(MyInput.UP, true);
		} else {
			MyInput.setKey(MyInput.UP, false);
		}
		if(value == PovDirection.south || value == PovDirection.southEast || value == PovDirection.southWest) {
			MyInput.setKey(MyInput.DOWN, true);
		} else {
			MyInput.setKey(MyInput.DOWN, false);
		}
		if(value == PovDirection.east || value == PovDirection.northEast || value == PovDirection.southEast) {
			MyInput.setKey(MyInput.RIGHT, true);
		} else {
			MyInput.setKey(MyInput.RIGHT, false);
		}
		if(value == PovDirection.west || value == PovDirection.northWest || value == PovDirection.southWest) {
			MyInput.setKey(MyInput.LEFT, true);
		} else {
			MyInput.setKey(MyInput.LEFT, false);
		}
		return true;
	}

	@Override
	public boolean axisMoved(Controller controller, int axisIndex, float value) {
		if(axisIndex == 0) {
			if(value > .3f) {
				MyInput.setKey(MyInput.DOWN, true);
			} else {
				MyInput.setKey(MyInput.DOWN, false);
			}
			if(value < -.3f) {
				MyInput.setKey(MyInput.UP, true);
			} else {
				MyInput.setKey(MyInput.UP, false);
			}
		}
		if(axisIndex == 1) {
			if(value > .3f) {
				MyInput.setKey(MyInput.RIGHT, true);
			} else {
				MyInput.setKey(MyInput.RIGHT, false);
			}
			if(value < -.3f) {
				MyInput.setKey(MyInput.LEFT, true);
			} else {
				MyInput.setKey(MyInput.LEFT, false);
			}
		}
		return true;
	}

}
