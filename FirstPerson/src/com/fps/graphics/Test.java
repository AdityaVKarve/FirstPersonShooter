package com.fps.graphics;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Test {
	public static void main(String[] args) {
		String filePath = "resources/textures/sampleTile1.png";
		Path file = Paths.get(filePath);
		if (Files.exists(file)) {
			System.out.println("Found");
		}
		else {
			System.out.println("Not found");
			
		}
	}

}
