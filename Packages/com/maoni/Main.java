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
import com.maoni.vBuffer.BufferHelper;

public class Main {

	private static final List<Integer> shaderList = new ArrayList<Integer>();
	private static final String _VERTEX_SHADER_LOCATION = "com/maoni/shaders/normalProt.vert.shader";
	private static final String _FRAGMENT_SHADER_LOCATION = "com/maoni/shaders/normalProt.frag.shader";
	private static int _PROGRAM;
	private static int _VBO;
	private static int vao;
	private static final float vertexPositions[] = { -1.0f, -0.5f, 0,
                                                     1.0f, -0.5f, 0,
												     0.0f,  1.0f, 0,
													 // Colours for the vertices.
													 1.0f, 0.0f, 0.0f, 1.0f,
													 0.0f, 1.0f, 0.0f, 1.0f,
													 0.0f, 0.0f, 1.0f, 1.0f, };
	private static FloatMatrix perspectiveMatrix;
	private static MatrixStack transformStack = new MatrixStack();
	private static float rotAngle = 0.0f;
	private static int matrixUniformLoc;
	private static SphereBatch sb;
    private static void render() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		glBindVertexArray(vao);
		glUseProgram(_PROGRAM);
		matrixUniformLoc = glGetUniformLocation(_PROGRAM, "mvpMatrix");
		transformStack.push(perspectiveMatrix);
		
		transformStack.push(MatrixUtil.INSTANCE.genRotationMatrix(0.0f, 1.0f, 0.0f, -rotAngle));
		
		
		glUniformMatrix4(matrixUniformLoc, false,
				MatrixUtil.INSTANCE.genBuffer(transformStack
						.getModelViewProjection()));
		
		glDrawElements(GL_POINTS, sb.getIndexLength(), GL_UNSIGNED_INT, 0);
		transformStack.pop();
		transformStack.push(MatrixUtil.INSTANCE.genRotationMatrix(1.0f, .0f, 0.0f, rotAngle));
		transformStack.push(MatrixUtil.INSTANCE.genScaleMatrix(1.2f, 1.2f, 1.2f));
		glUniformMatrix4(matrixUniformLoc, false,
				MatrixUtil.INSTANCE.genBuffer(transformStack
						.getModelViewProjection()));

		glDrawElements(GL_POINTS, sb.getIndexLength(), GL_UNSIGNED_INT, 0);
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
			float fzfar = 100.0f;

			perspectiveMatrix = ViewPoint.INSTANCE.getPerspectiveMatrix(height,
					width, fznear, fzfar, 45.0f);
			perspectiveMatrix.mmuli(MatrixUtil.INSTANCE.genTranslateMatrix(0.0f, 0.0f, -5.0f));

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
			
			sb = new SphereBatch(3, 300, 100);
			// Setup the VBO
			_VBO = BufferHelper.INSTANCE
			.createVertexBufferObject(GL_ARRAY_BUFFER, sb.getVertexCoordData());
			
			glBindBuffer(GL_ARRAY_BUFFER, _VBO);
			glEnableVertexAttribArray(0);
			glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
			
			int ebo = BufferHelper.INSTANCE.createElementArrayBufferObject(sb.getIndexData());
			glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, ebo);
						
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
			rotAngle += 0.5f % 360.0f;
			logic();
		}
		Display.destroy();
		System.out.println("Closed Display.");
	}

}
