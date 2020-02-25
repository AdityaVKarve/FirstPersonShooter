package com.fps.graphics;

import java.util.Random;

import com.fps.Input.Controller;
import com.fps.game.Game;

public class Render3d extends Render{

	public double zBuffer[];
	public int renderDistance = 2500;
	private double forward, right, rotation, cosine, sine, up, walking;
	public Render3d(int WIDTH, int HEIGHT) {
		super(WIDTH, HEIGHT);
		zBuffer = new double[WIDTH*HEIGHT];
		// TODO Auto-generated constructor stub
	}
	public void floor(Game game) {	//This render floor and ceiling
		rotation =game.controls.rotation;
		double floorPosition = 8;
		double ceilingPosition = 8;
		forward = game.controls.z;
		right = game.controls.x;
		cosine = Math.cos(rotation);
		up =game.controls.y;
		
		double running = Math.sin(game.time/6.0)*0.8;
		sine = Math.sin(rotation);
		for(int y = 0; y < HEIGHT; y++) {
			double ceiling = (y - HEIGHT/2.0)/HEIGHT;
			double z = (floorPosition+up)/ceiling;
			if(Controller.walk) {
				z = (floorPosition+up+walking)/ceiling;
				walking = Math.sin(game.time/6.0)*0.5;
			}
			if(Controller.runBob) {
				z = (floorPosition+up+running)/ceiling;
			}
			if(ceiling < 0) {
				z =(ceilingPosition - up)/ -ceiling;
				if(Controller.walk) {
					z =(ceilingPosition - up - walking)/ -ceiling;
				} 
				if(Controller.runBob) {
					z =(ceilingPosition - up - running)/ -ceiling;
				}
			}
			
			for(int x = 0; x < WIDTH; x++) {
				double depth = (x - WIDTH/2.0)/HEIGHT;
				depth *=z;
				double xx = depth * cosine + z * sine;
				double yy = z*cosine-depth*sine;
				int xPix = (int)(xx+right);
				int yPix = (int) (yy + forward);
				zBuffer[x+y*WIDTH] = z;
				PIXELS[x+y*WIDTH] = Texture.floor.PIXELS[(xPix & 7)+(yPix & 7)*8];
			}
		}
	}

	public void renderDistanceLimiter() {
		for(int i = 0; i < WIDTH*HEIGHT;i++) {
			int colour = PIXELS[i];
			int brightness = (int)(renderDistance/zBuffer[i]);
			if(brightness < 0)
				brightness = 0;
			if(brightness > 255)
				brightness = 255;
			
			int R, G, B;
			R = (colour>>16)& 0xff;
			G = (colour>>8)&0xff;
			B = (colour)&0xff;
			
			R = R*brightness/255;
			G = G*brightness/255;
			B = B*brightness/255;
			
			PIXELS[i] = R<<16|G<<8|B;
		}
		
	}
	
	public void renderWall(double xLeft, double xRight, double zDistanceRight, double zDistanceLeft, double yHeight) {
		double rightCorrect = 0.062;
		double forwardCorrect = 0.062;
		double walkCorrect = -0.062;
		double xcLeft = ((xLeft) - right*rightCorrect)*2;
		double zcLeft = ((zDistanceLeft) - forward*forwardCorrect) * 2;
		double upCorrect = 0.062;
		
		double rotLeftSideX = xcLeft * cosine - zcLeft * sine;
		double yCornerTL = ((-yHeight) - (-up)*upCorrect - walking * walkCorrect)*2;
		double yCornerBL = ((+0.5-yHeight) - (-up)*upCorrect- walking * walkCorrect)*2;
		double rotLeftSideZ = zcLeft * cosine + xcLeft * sine;
		
		double xcRight = ((xRight) - right*rightCorrect) * 2;
		double zcRight = ((zDistanceRight) - forward*forwardCorrect)*2;
		
		double rotRightSideX = xcRight * cosine - zcRight * sine;
		double yCornerTR = ((-yHeight) - (-up)*upCorrect - walking * walkCorrect)*2;
		double yCornerBR = ((+0.5-yHeight) - (-up)*upCorrect - walking * walkCorrect)*2; 
		
		double rotRightSideZ = zcRight * cosine +xcRight*sine;
		
		double xPixelLeft = (rotLeftSideX / rotLeftSideZ*HEIGHT+WIDTH/2);
		double xPixelRight = (rotRightSideX / rotRightSideZ*HEIGHT+WIDTH/2);
		
		if(xPixelLeft >= xPixelRight) {
			return;
		}
		int xPixelLeftInt = (int)(xPixelLeft);
		int xPixelRightInt = (int)(xPixelRight);
		
		if(xPixelLeftInt < 0) {
			xPixelLeftInt = 0;
		}
		
		if(xPixelRightInt > WIDTH) {
			xPixelRightInt = WIDTH;
		}
		double yPixelLeftTop = (yCornerTL/rotLeftSideZ * HEIGHT + HEIGHT / 2.0);
		double yPixelLeftBottom = (yCornerBL/rotLeftSideZ * HEIGHT + HEIGHT/2.0);
		double yPixelRightTop = (yCornerTR / rotRightSideZ * HEIGHT + HEIGHT / 2.0);
		double yPixelRightBottom = (yCornerBR / rotRightSideZ * HEIGHT + HEIGHT/2.0);
		double tex0 = 1/rotLeftSideZ;
		double tex1 = 1/rotRightSideZ;
		double tex2 = 0/rotLeftSideZ;
		double tex3 = 8/rotRightSideZ - tex2;
		
		for(int x = xPixelLeftInt; x < xPixelRightInt; x++) {
			double pixelRotation = (x - xPixelLeft)/(xPixelRight - xPixelLeft);
			double yPixelTop = yPixelLeftTop + (yPixelRightTop - yPixelLeftTop)*pixelRotation;
			double yPixelBottom = yPixelLeftBottom + (yPixelRightBottom - yPixelLeftBottom)*pixelRotation;
			
			int xTexture = (int)((tex2+tex3*pixelRotation) /(tex0 + (tex1 - tex0) * pixelRotation));
			int yPixelTopInt = (int)(yPixelTop);
			int yPixelBottomInt = (int)(yPixelBottom);
			
			if(yPixelTopInt < 0) {
				yPixelTopInt = 0;
			}
			if(yPixelBottomInt > HEIGHT) {
				yPixelBottomInt = HEIGHT;
			}
			for(int y = yPixelTopInt; y < yPixelBottomInt; y++) {
					double pixelRotationY = (y - yPixelTop)/(yPixelBottom - yPixelTop);
					int yTexture = (int)(8*pixelRotationY);
					PIXELS[x+y*WIDTH] = xTexture * 100 + yTexture * 100;
					zBuffer[x+y*WIDTH] = 1/(tex0 + (tex1 - tex0) * pixelRotation)*2;
				
			}
		}
	}
}
