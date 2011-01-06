package com.maoni;

import java.nio.FloatBuffer;
import java.util.ArrayList;
import java.util.List;

import org.jblas.FloatMatrix;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import com.maoni.matrix.MatrixUtil;
import com.maoni.shaders.util.CreateProgram;
import com.maoni.shaders.util.CreateShader;
import com.maoni.vBuffer.InitializeVertexBuffer;

public class Main {
	
	private static final List<Integer> shaderList = new ArrayList<Integer>();
	private static final String _VERTEX_SHADER_LOCATION = "C:\\Users\\Alistair\\Code\\Maoni\\src\\com\\maoni\\shaders\\normalProt.vert.shader";
	private static final String _FRAGMENT_SHADER_LOCATION = "C:\\Users\\Alistair\\Code\\Maoni\\src\\com\\maoni\\shaders\\normalProt.frag.shader";
	private static int _PROGRAM;
	private static int _PBO;
	private static int vao;
	private static final float vertexPositions[] = {
	     0.0f,    0.5f, 0.0f, 1.0f,
	     0.5f, -0.366f, 0.0f, 1.0f,
	     -0.5f, -0.366f, 0.0f, 1.0f,
	     1.0f,    0.0f, 0.0f, 1.0f,
	     0.0f,    1.0f, 0.0f, 1.0f,
	     0.0f,    0.0f, 1.0f, 1.0f,
	};
	
	private static FloatMatrix perspectiveMatrix = MatrixUtil.INSTANCE.genIdentityMatrix4f();
	
	
	private static float rotAngle = 0.0f;
	private static int matrixUniformLoc;
	
	private static void render() {
		glClearColor(0.0f, 0.0f, 0.0f, 1.0f);
		glClearDepth(1.0f);
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);		

		glUseProgram(_PROGRAM);
		// Rotation Matrix.
		FloatMatrix fRot = MatrixUtil.INSTANCE.Multiply(MatrixUtil.INSTANCE.genRotationMatrix(1.0f, 1.0f, 1.0f, -rotAngle));
		FloatBuffer fb = BufferUtils.createFloatBuffer(fRot.length);
		fb.put(fRot.toArray());
		fb.flip();
		
		glUniformMatrix4(matrixUniformLoc, false, fb);
		
		glBindBuffer(GL_ARRAY_BUFFER, _PBO);
		glEnableVertexAttribArray(0);
		glEnableVertexAttribArray(1);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glVertexAttribPointer(1, 4, GL_FLOAT, false, 0, 48);
		glDrawArrays(GL_TRIANGLES, 0, 3);
		glDisableVertexAttribArray(0);
		glUseProgram(0);
		Display.update();
	}

	private static void init() {
		try {
			int width = 800;
			int height = 600;
			
			float fznear = 1.0f;
			float fzfar = 100.0f;
			
			float frustrumScale = calcFrustrumScale(45.0f);
			
			perspectiveMatrix.put(0, 0, frustrumScale * ((float) height / (float) width));
			perspectiveMatrix.put(1, 1, frustrumScale);
			perspectiveMatrix.put(2, 2, (fzfar + fznear) / (fznear - fzfar));
			perspectiveMatrix.put(2, 3, -1.0f);
			perspectiveMatrix.put(3, 2, (2.0f * fznear * fzfar) / (fznear - fzfar));
			
			// Move out a bit.
			perspectiveMatrix.mmuli(MatrixUtil.INSTANCE.genTranslateMatrix(0.0f, 0.0f, -1.0f));
			
			Display.setDisplayMode(new DisplayMode(width, height));
			Display.setVSyncEnabled(true);
			Display.setTitle("Maoni V0.01");
			Display.create();
			
			// Create the Vao
			vao = glGenVertexArrays();
			glBindVertexArray(vao);
			
			// Setup the shader program
			shaderList.add(CreateShader.VERTEX.load(_VERTEX_SHADER_LOCATION));
			shaderList.add(CreateShader.FRAGMENT.load(_FRAGMENT_SHADER_LOCATION));
			_PROGRAM = CreateProgram.INSTANCE.create(shaderList);
			
			int cameraToClipMatrixUnif = glGetUniformLocation(_PROGRAM, "pMatrix");
			matrixUniformLoc = glGetUniformLocation(_PROGRAM, "mvMatrix");
			glUseProgram(_PROGRAM);
			FloatBuffer cb = BufferUtils.createFloatBuffer(perspectiveMatrix.length);
			cb.put(perspectiveMatrix.toArray());
			cb.flip();
			glUniformMatrix4(cameraToClipMatrixUnif, false, cb);
			glUseProgram(0);
			
			// Setup the PositionBufferObject
			_PBO = InitializeVertexBuffer.INSTANCE.createPositionBufferObject(vertexPositions);

			glViewport(0, 0, width, height);
		} catch (LWJGLException e) {
			e.printStackTrace();
		}
	}

	private static float calcFrustrumScale(float f) {
		double radVersion = Math.toRadians(f);
		return (float) (1.0f / Math.tan(radVersion / 2.0f));
	}
	
	public static void main (String args[]) {
		init();
		while (!Display.isCloseRequested()) {
			render();
			Display.sync(60);
	
			rotAngle += 1.0f;
			if (rotAngle > 360.0f)
				rotAngle = 0.0f;
		}
		Display.destroy();
		System.out.println("Closed Display.");
	}

}
