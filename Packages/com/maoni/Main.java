package com.maoni;

import java.util.ArrayList;
import java.util.List;

import org.jblas.FloatMatrix;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.util.glu.Sphere;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.maoni.matrix.MatrixStack;
import com.maoni.matrix.MatrixUtil;
import com.maoni.matrix.ViewPoint;
import com.maoni.shaders.util.CreateProgram;
import com.maoni.shaders.util.CreateShader;
import com.maoni.vBuffer.InitializeVertexBuffer;

public class Main {

	private static final List<Integer> shaderList = new ArrayList<Integer>();
	private static final String _VERTEX_SHADER_LOCATION = "com/maoni/shaders/normalProt.vert.shader";
	private static final String _FRAGMENT_SHADER_LOCATION = "com/maoni/shaders/normalProt.frag.shader";
	private static int _PROGRAM;
	private static int _PBO;
	private static int vao;
	private static final float vertexPositions[] = { 0.0f, 0.5f, 0.0f, 1.0f,
		0.5f, -0.366f, 0.0f, 1.0f, -0.5f, -0.366f, 0.0f, 1.0f, 1.0f, 0.0f,
		0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 1.0f, 0.0f, 0.0f, 1.0f, 1.0f, };
	private static FloatMatrix perspectiveMatrix;
	private static MatrixStack transformStack = new MatrixStack();
	private static float rotAngle = 0.0f;
	private static int matrixUniformLoc;
    private static void render() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glUseProgram(_PROGRAM);
		transformStack.push(perspectiveMatrix);
		transformStack.push(MatrixUtil.INSTANCE.genRotationMatrix(0.0f, 1.0f, 0.0f, -rotAngle));


		glUniformMatrix4(matrixUniformLoc, false,
				MatrixUtil.INSTANCE.genBuffer(transformStack
						.getModelViewProjection()));

		glBindBuffer(GL_ARRAY_BUFFER, _PBO);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 48);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glDisableVertexAttribArray(0);
		glUseProgram(0);

		// reset the transformStack.
		transformStack.pop();
		transformStack.pop();
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
					width, fznear, fzfar, 35.0f);
			perspectiveMatrix.mmuli(MatrixUtil.INSTANCE.genTranslateMatrix(0.0f, 0.0f, -1.0f));

			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.setTitle("Maoni V0.01");
			Display.create();
			Mouse.create();
			// Create the Vao
			vao = glGenVertexArrays();
			glBindVertexArray(vao);

			// Setup the shader program
			shaderList.add(CreateShader.VERTEX.load(ClassLoader.getSystemResourceAsStream(_VERTEX_SHADER_LOCATION)));
			shaderList.add(CreateShader.FRAGMENT.load(ClassLoader.getSystemResourceAsStream(_FRAGMENT_SHADER_LOCATION)));
			_PROGRAM = CreateProgram.INSTANCE.create(shaderList);

			// Setup the PositionBufferObject
			_PBO = InitializeVertexBuffer.INSTANCE
			.createPositionBufferObject(vertexPositions);

			glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		init();
		Display.setVSyncEnabled(true);
		while (!Display.isCloseRequested()) {
			render();
			Display.sync(60);
			rotAngle += 1.0f % 360.0f;
			logic();
		}
		Display.destroy();
		System.out.println("Closed Display.");
	}

}
