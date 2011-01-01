package com.maoni.shaders.util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;

public enum InitializeVertexBuffer {
	INSTANCE,
	;
	
	public int createPositionBufferObject(float vertices[]) {
		int positionBufferObject = glGenBuffers();
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
		fb.put(vertices);
		fb.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, positionBufferObject);
		glBufferData(GL_ARRAY_BUFFER, fb, GL_STATIC_DRAW);
		glVertexAttribPointer(0, 4, GL_FLOAT, false, 0, 0);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
			
		return positionBufferObject;
	}
}
