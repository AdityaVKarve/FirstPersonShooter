package com.fps.game;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.awt.image.DataBuffer;
import java.awt.image.DataBufferInt;
import javax.swing.JFrame;

import com.fps.Input.Controller;
import com.fps.Input.InputHandler;
import com.fps.graphics.Render;
import com.fps.graphics.Screen;

public class Display extends Canvas implements Runnable {
	//This class manages the game window and the game loop
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
	public static final String TITLE = "MeinKraft v0.01 Alpha";
	private Thread thread;
	private Render render;
	private Screen screen;
	private BufferedImage img;
	private boolean running = false;
	private int[] pixels;
	private int newX = 0;
	private int oldX = 0;
	private int fps;
	private InputHandler input;
	private Game game;
	
	public Display() {
		render = new Render(WIDTH, HEIGHT);
		screen = new Screen(WIDTH,HEIGHT);
		img = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_RGB);
		pixels = ((DataBufferInt) img.getRaster().getDataBuffer()).getData();
		game = new Game();
		input = new InputHandler();
		addKeyListener(input);
		addMouseListener(input);
		addMouseMotionListener(input);
		addFocusListener(input);
	}
	
	private void start() {
		if(running) return;
		running = true;
		thread = new Thread(this);
		thread.start();
		System.out.println("Program Started");
	}
	
	public static void main(String args[]) {
		BufferedImage cursor = new BufferedImage(16,16,BufferedImage.TYPE_INT_ARGB);
		Cursor blank = Toolkit.getDefaultToolkit().createCustomCursor(cursor, new Point(0,0), "blank");	//Remove mouse on screen
		
		Display game = new Display();
		JFrame frame = new JFrame();
		frame.add(game);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.getContentPane().setCursor(blank);
		frame.setSize(WIDTH, HEIGHT);
		frame.setTitle(TITLE);
		
		game.start();
	}
	
	private void stop() {
		if(!running) return;
		running = false;
		try {
			thread.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		
	}
	
	@Override
	public void run() {
		int frames = 0;
		double unprocessedSeconds = 0;
		long previousTime = System.nanoTime();
		long timeHolder = previousTime;
		double secondsPerTick = 1/60.0;
		int tickCount = 0;
		boolean ticked = false;
		// TODO Auto-generated method stub
		while(running) {
			requestFocus();
			long currentTime = System.nanoTime();
			long pastTime = currentTime - previousTime;
			previousTime = currentTime;
			unprocessedSeconds +=(double)(pastTime / 1000000000.00);
			while(unprocessedSeconds > secondsPerTick) {
				tick();
				unprocessedSeconds -= secondsPerTick;
				ticked = true;
				tickCount +=1;
				
				if(tickCount%60 == 0) {
					fps = frames;
					previousTime += 1000;
					frames = 0;
					
				}
			}
			if(ticked == true) {
				render();
				frames++;
			}
			render();
			frames++;
			newX = InputHandler.mouseX;
			if(newX > oldX) {
				Controller.turnRight = true;
			}
			if(newX < oldX) {
				Controller.turnLeft = true;
			}
			if(newX == oldX) {
				Controller.turnLeft = false;
				Controller.turnRight = false;
			}
			oldX = newX;
		
		}
	}
	
	private void tick() {
		game.tick(input.key);
	}
	private void render() {
		BufferStrategy bs = this.getBufferStrategy();
		if(bs == null) {
			 createBufferStrategy(3);
			 return;
		}
		screen.render(game);
		for(int i = 0; i < WIDTH*HEIGHT; i++) {
			pixels[i] = screen.PIXELS[i];
		}
		Graphics g = bs.getDrawGraphics();
		g.drawImage(img,0, 0, WIDTH, HEIGHT, null);
		g.setFont(new Font("Ariel",1,25));
		g.setColor(new Color(0xffffff));
		g.drawString("fps: "+Integer.toString(fps), 10, 25);
		g.dispose();
		bs.show();
	}
}
