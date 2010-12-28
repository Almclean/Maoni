package com.maoni.shaders.util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL15.*;

public enum InitializeVertexBuffer {
	INSTANCE,
	;
	
	public int createPositionBufferObject(float vertices[]) {
		int positionBufferObject = glGenBuffers();
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
		fb.put(vertices);
		fb.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
		glBufferData(positionBufferObject, fb, GL_STATIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
			
		return positionBufferObject;
	}
}
