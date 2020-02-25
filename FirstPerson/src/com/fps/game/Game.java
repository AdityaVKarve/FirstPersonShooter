package com.fps.game;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import com.fps.Input.Controller;
//This class deals with the game controls
public class Game {
	public int time;
	public Controller controls;
	public Game() {
		controls = new Controller();
	}
	
	public void tick(boolean[] key) {
		time++;
		
		boolean forward, backward, left, right, turnLeft, turnRight,jump,crouch,sprint;
		forward = key[KeyEvent.VK_W];
		backward = key[KeyEvent.VK_S];
		left = key[KeyEvent.VK_A];
		right = key[KeyEvent.VK_D];
		turnLeft = key[KeyEvent.VK_LEFT];
		turnRight = key[KeyEvent.VK_RIGHT];
		jump = key[KeyEvent.VK_SPACE];
		crouch = key[KeyEvent.VK_CONTROL];
		sprint = key[KeyEvent.VK_SHIFT];
		controls.tick(forward, backward, left, right,jump,crouch,sprint);
	}
}






