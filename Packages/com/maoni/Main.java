package com.maoni;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;
import java.util.List;

import org.jblas.FloatMatrix;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

import com.maoni.modelutil.ObjModelLoader;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.maoni.batch.Batch;
import com.maoni.batch.SphereBatch;
import com.maoni.matrix.MatrixStack;
import com.maoni.matrix.MatrixUtil;
import com.maoni.matrix.ViewPoint;
import com.maoni.shaders.util.CreateProgram;
import com.maoni.shaders.util.CreateShader;
import com.maoni.vBuffer.BufferHelper;

public class Main {

	private static final String MODEL_PATH = "com/maoni/models/bendychair.obj";
	private static final List<Integer> shaderList = new ArrayList<Integer>();
	private static final String _VERTEX_SHADER_LOCATION = "com/maoni/shaders/normalProt.vert.shader";
	private static final String _FRAGMENT_SHADER_LOCATION = "com/maoni/shaders/normalProt.frag.shader";
	private static int _PROGRAM;
	private static int _VBO;
	private static int vao;
	private static FloatMatrix perspectiveMatrix;
	private static MatrixStack transformStack = new MatrixStack();
	private static float rotAngle = 0.0f;
	private static int matrixUniformLoc;
	private static Batch ob;
	private static Texture t;
	private static int _VBO2;		
	
    private static void render() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		//glClearDepth(1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glBindVertexArray(vao);
		glUseProgram(_PROGRAM);
		matrixUniformLoc = glGetUniformLocation(_PROGRAM, "mvpMatrix");
		
		transformStack.push(perspectiveMatrix);
		transformStack.push(MatrixUtil.INSTANCE.genTranslateMatrix(0.0f, 0.0f, -1.0f));
		transformStack.push(MatrixUtil.INSTANCE.genRotationMatrix(0.0f, 1.0f, 0.0f, -rotAngle));
		//transformStack.push(MatrixUtil.INSTANCE.genRotationMatrix(1.0f, 0.0f, 0.0f, -rotAngle));
		
		
		glUniformMatrix4(matrixUniformLoc, false,
				MatrixUtil.INSTANCE.genBuffer(transformStack
						.getModelViewProjection()));
		
		//glDrawArrays(GL_POINTS, 0, ob.getVertexLength());
		glDrawElements(GL_TRIANGLES, ob.getIndexLength(), GL_UNSIGNED_INT, 0);
		
		transformStack.pop();
		glBindVertexArray(0);
		glUseProgram(0);

		// reset the transformStack.
		transformStack.clear();
		Display.update();
	}

	private static void logic() {

		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE)) {
			Display.destroy();
			System.exit(0);
		}
	}

	private static void init() {
		try {
			int width = 1024;
			int height = 768;

			float fznear = 1.0f;
			float fzfar = 1000.0f;

			perspectiveMatrix = ViewPoint.INSTANCE.getPerspectiveMatrix(height,
					width, fznear, fzfar, 45.0f);
			perspectiveMatrix.mmuli(MatrixUtil.INSTANCE.genTranslateMatrix(0.0f, 0.0f, -100.0f));

			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.setTitle("Maoni V0.01");
			Display.create();
			Mouse.create();
			
			// Setup the shader program
			shaderList.add(CreateShader.VERTEX.load(ClassLoader.getSystemResourceAsStream(_VERTEX_SHADER_LOCATION)));
			shaderList.add(CreateShader.FRAGMENT.load(ClassLoader.getSystemResourceAsStream(_FRAGMENT_SHADER_LOCATION)));
			_PROGRAM = CreateProgram.INSTANCE.create(shaderList);

			// Create the Vao
			vao = glGenVertexArrays();
			glBindVertexArray(vao);
			
			ob = ObjModelLoader.INSTANCE.createBatch(ClassLoader.getSystemResourceAsStream(MODEL_PATH));
			System.out.println("Number of vertices = " + ob.getVertexLength());
			System.out.println("Number of indices = " + ob.getIndexLength());
			
			// Setup the VBO
			_VBO = BufferHelper.INSTANCE
			.createVertexBufferObject(GL_ARRAY_BUFFER, ob.getVertexCoordData());
			
			_VBO2 = BufferHelper.INSTANCE.createVertexBufferObject(GL_ARRAY_BUFFER, ob.getTexCoordData());
			
			int vAttrib = glGetAttribLocation(_PROGRAM, "vVector");
			int tAttrib = glGetAttribLocation(_PROGRAM, "texCoord");
			
			// Load in Geometry
			glBindBuffer(GL_ARRAY_BUFFER, _VBO);
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(vAttrib, 3, GL_FLOAT, false, 0, 0);

			int ebo = BufferHelper.INSTANCE.createElementArrayBufferObject(ob.getIndexData());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
			
			// Load in textures
			System.out.println("Loading in texture data");
			t = TextureLoader.getTexture("PNG", ClassLoader.getSystemResourceAsStream("com/maoni/models/crackedpaint2.jpg"));
			
			glBindBuffer(GL_ARRAY_BUFFER, _VBO2);
			glEnableVertexAttribArray(1);
			glVertexAttribPointer(tAttrib, 2, GL_FLOAT, false, 0, 0);
			
			glViewport(0, 0, width, height);
			glEnable(GL_CULL_FACE);
			glEnable(GL_DEPTH_TEST);
			glDepthMask(true);

			
		} catch (LWJGLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		init();
		Display.setVSyncEnabled(true);
		while (!Display.isCloseRequested()) {
			render();
			Display.sync(60);
			rotAngle += 0.5f % 360.0f;
			logic();
		}
		Display.destroy();
		System.out.println("Closed Display.");
	}

}
