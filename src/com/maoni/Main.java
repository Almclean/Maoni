package com.maoni;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.maoni.shaders.util.CreateProgram;
import com.maoni.shaders.util.CreateShader;
import com.maoni.shaders.util.InitializeVertexBuffer;

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
		}
		Display.destroy();
		System.out.println("Closed Display.");
	}
	
	private static void render() {
		glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
		glClear(GL_COLOR_BUFFER_BIT);		

		glUseProgram(_PROGRAM);

		glBindBuffer(GL_ARRAY_BUFFER, pbo);
		glEnableVertexAttribArray(0);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glDrawArrays(GL_TRIANGLES, 0, 3);

		glDisableVertexAttribArray(0);
		glUseProgram(0);
		
		Display.update();
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
			vao = glGenVertexArrays();
			glBindVertexArray(vao);
			
			glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

}
