package com.fps.graphics;

import java.util.Random;

import com.fps.game.Game;

public class Screen extends Render{
	private Render test;
	private Render3d render;
	
	public Screen(int width, int height) {
		// TODO Auto-generated constructor stub
		super(width, height);
		Random random = new Random();
		test = new Render(256,256);
		render = new Render3d(WIDTH, HEIGHT);
		for(int i = 0; i <256*256; i++) {
			test.PIXELS[i] = random.nextInt() * (random.nextInt(10)/9);
		}
		
	}
	public void render(Game game) {
		for(int i = 0; i < WIDTH*HEIGHT; i++) {
			PIXELS[i] = 0;
		}
		for (int i = 0; i < 50; i++){
			int anim0 = (int)(Math.sin(System.currentTimeMillis()%1000.0/1000*Math.PI*2)*100);
			int anim = (int)(Math.sin((game.time+i)%1000.0/100)*100);
			int anim2 = (int)(Math.cos((game.time + i)%1000.0/100)*100);
		}
		render.floor(game);
		
		render.renderWall(0,0.5, 1.5, 1.5, 0);
		render.renderWall(0, 0, 1, 1.5, 0);
		render.renderWall(0,0.5, 1, 1, 0);
		render.renderWall(0.5, 0.5, 1, 1.5, 0);
		render.renderDistanceLimiter();
		//draw(render,0,0);
	}
}
