package com.maoni;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL33;

public class Main {
	
	public static void main (String args[]) {
		System.out.println("Hello world !");
		init();
		
		while (!Display.isCloseRequested()) {
			render();
			Display.update();
		}
		Display.destroy();
	}
	
	private static void render() {
		// TODO Auto-generated method stub
		
	}

	public static void init() {
		try {
			
			Display.setDisplayMode(new DisplayMode(800, 600));
			Display.setVSyncEnabled(true);
			Display.setTitle("Maoni V0.01");
			Display.create();
			
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}
