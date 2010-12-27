package com.maoni;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL32;
import org.lwjgl.opengl.GL33;
import org.lwjgl.opengl.GLContext;

import com.maoni.shaders.util.CreateProgram;
import com.maoni.shaders.util.CreateShader;
import com.maoni.shaders.util.InitializeVertexBuffer;
import com.maoni.tests.ShaderLoaderTests;

public class Main {
	
	private static final List<Integer> shaderList = new ArrayList<Integer>();
	private static final String _VERTEX_SHADER_LOCATION = "C:\\Users\\Alistair\\Code\\Maoni\\src\\com\\maoni\\shaders\\normalProt.vert.shader";
	private static final String _FRAGMENT_SHADER_LOCATION = "C:\\Users\\Alistair\\Code\\Maoni\\src\\com\\maoni\\shaders\\normalProt.frag.shader";
	private static int _PROGRAM;
	private static int pbo;
	private static int vao;
	private static final float vertexPositions[] = {
			0.75f, 0.75f, 0.0f, 1.0f,
			0.75f, -0.75f, 0.0f, 1.0f,
			-0.75f, -0.75f, 0.0f, 1.0f,
		};
	
	public static void main (String args[]) {
		init();
		while (!Display.isCloseRequested()) {
			Display.sync(60);
			render();
			Display.update();
		}
		Display.destroy();
		System.out.println("Closed Display.");
	}
	
	private static void render() {
		GL11.glClearColor(1.0f, 0.0f, 0.0f, 0.0f);
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT);		

		GL20.glUseProgram(_PROGRAM);
		
	    FloatBuffer verticesBuffer = BufferUtils.createFloatBuffer(vertexPositions.length);
		verticesBuffer.put(vertexPositions).flip();

		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, pbo);
		GL20.glEnableVertexAttribArray(0);
		GL20.glVertexAttribPointer(0, 3, GL11.GL_FLOAT, false, 4, 0);
		//GL31.glDrawArraysInstanced(GL11.GL_TRIANGLES, 0, 3, 1);
		GL11.glDrawArrays(GL11.GL_TRIANGLES, 0, 3);

		GL20.glDisableVertexAttribArray(0);
		GL20.glUseProgram(0);
	}

	private static void init() {
		try {
			int width = 800;
			int height = 600;
			
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.setTitle("Maoni V0.01");
			Display.create();
			
			// Setup the shader program
			shaderList.add(CreateShader.VERTEX.load(_VERTEX_SHADER_LOCATION));
			shaderList.add(CreateShader.FRAGMENT.load(_FRAGMENT_SHADER_LOCATION));
			_PROGRAM = CreateProgram.INSTANCE.create(shaderList);
			
			// Setup the PositionBufferObject
			pbo = InitializeVertexBuffer.INSTANCE.createPositionBufferObject(vertexPositions);
			
			// Get some space for a vertex array object
			vao = GL30.glGenVertexArrays();
			GL30.glBindVertexArray(vao);
			
			GL11.glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}
