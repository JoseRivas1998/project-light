package com.tcg.light.managers;

public class MyInput {

	private static boolean[] keys, pkeys;
	
	private static final int NUM_KEYS = 12;
	public static final int UP = 0;
	public static final int DOWN = 1;
	public static final int LEFT = 2;
	public static final int RIGHT = 3;
	public static final int SHOOT = 4;
	public static final int JUMP = 5;
	public static final int START = 6;
	public static final int BACK = 7;
	public static final int SCREENSHOT = 8;
	public static final int FULLSCREEN = 9;
	public static final int AUTO = 10;
	public static final int ANY = 11;
	
	static {
		keys = new boolean[NUM_KEYS];
		pkeys = new boolean[NUM_KEYS];
	}
	
	public static void update() {
		for(int i = 0; i < NUM_KEYS; i++) {
			pkeys[i] = keys[i];
		}
	}
	
	public static void setKey(int k, boolean b) {
		keys[k] = b;
	}
	
	public static boolean keyDown(int k) {
		return keys[k];
	}
	
	public static boolean keyPressed(int k) {
		return keys[k] && !pkeys[k];
	}
	
	public static boolean anyKeyDown() {
		return keyDown(ANY);
	}
	
	public static boolean anyKeyPressed() {
		return keyPressed(ANY);
	}

}
