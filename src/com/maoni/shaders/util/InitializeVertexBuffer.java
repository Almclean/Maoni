package com.maoni.shaders.util;

import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL15;

public enum InitializeVertexBuffer {
	INSTANCE,
	;
	
	public int createPositionBufferObject(float vertices[]) {
		int positionBufferObject = GL15.glGenBuffers();
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
			
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, positionBufferObject);
		GL15.glBufferData(positionBufferObject, fb, GL15.GL_STATIC_DRAW);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
			
		return positionBufferObject;
	}
}
