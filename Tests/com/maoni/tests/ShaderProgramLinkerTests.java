package com.maoni.tests;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import com.maoni.shaders.util.CreateProgram;
import com.maoni.shaders.util.CreateShader;

import static org.junit.Assert.*;

public class ShaderProgramLinkerTests {
	
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
	public void testThatShaderProgramsAreLinkedCorrectly() {
		int vertex = CreateShader.VERTEX.load(ClassLoader.getSystemResourceAsStream("com/maoni/shaders/normalProt.vert.shader"));
		int frag = CreateShader.FRAGMENT.load(ClassLoader.getSystemResourceAsStream("com/maoni/shaders/normalProt.frag.shader"));
		
		List<Integer> shaderList = new ArrayList<Integer>();
		shaderList.add(vertex);
		shaderList.add(frag);
		
		int result = CreateProgram.INSTANCE.create(shaderList);
		assertTrue (result != 0);
	}
	
	@After
	public void tearDown() {
		Display.destroy();
	}

}
