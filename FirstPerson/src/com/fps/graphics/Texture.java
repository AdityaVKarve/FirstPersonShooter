package com.fps.graphics;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

public class Texture {
	public static Render floor = loadBitMap("/textures/sampleTile1.png");
	
	
	public static Render loadBitMap(String filename) {
		try {
			Path file = Paths.get(filename);
			BufferedImage image = ImageIO.read(Texture.class.getResource(filename));
			int width = image.getWidth();
			int height = image.getHeight();
			Render result = new Render(width, height);
			image.getRGB(0,0,width,height,result.PIXELS,0,width);
			return result;
		}catch(Exception e) {
			System.out.println("Error in rendering texture!");
			throw new RuntimeException(e);
		}
		
	}
	
}
