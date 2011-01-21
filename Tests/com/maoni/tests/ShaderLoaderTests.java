package com.maoni.tests;

import com.maoni.shaders.util.CreateShader;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.junit.Assert.*;

public class ShaderLoaderTests {
	
	@Before
	public void setup() {
		try {
			int width = 800;
			int height = 600;
			
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.setTitle("Maoni Test Instance");
			Display.create();
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testThatShaderLoadsAVertexShaderAndReturnsCorrectly() {
		int result = CreateShader.VERTEX.load(ClassLoader.getSystemResourceAsStream("com/maoni/shaders/normalProt.vert.shader"));
		assertTrue(result != 0);
	}
	
	@Test
	public void testThatTheShaderLoadsAFragmentShaderAndReturnsCorrectly() {
		int result = CreateShader.FRAGMENT.load(ClassLoader.getSystemResourceAsStream("com/maoni/shaders/normalProt.frag.shader"));
		assertTrue(result != 0);
	}
	
	@After
	public void tearDown() {
		Display.destroy();
	}
}
