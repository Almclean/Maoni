package com.maoni.vBuffer;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;

import static org.lwjgl.opengl.GL15.*;

public enum BufferHelper {
	INSTANCE,
	;
	
	public int createVertexBufferObject(final int target, final float vertices[]) {
		int bufferObject = glGenBuffers();
		FloatBuffer fb = BufferUtils.createFloatBuffer(vertices.length);
		fb.put(vertices);
		fb.flip();
		
		glBindBuffer(target, bufferObject);
		glBufferData(target, fb, GL_STATIC_DRAW);
		glBindBuffer(target, 0);
			
		return bufferObject;
	}
	
	public int createVertexBufferObject(final int target, final FloatBuffer fb) {
		int bufferObject = glGenBuffers();
		glBindBuffer(target, bufferObject);
		glBufferData(target, fb, GL_STATIC_DRAW);
		glBindBuffer(target, 0);
			
		return bufferObject;
	}
	public int createElementArrayBufferObject(final IntBuffer ib) {
		int bufferObject = glGenBuffers();

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, bufferObject);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, ib, GL_STATIC_DRAW);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, 0);
		
		return bufferObject;
	}
	
	
}
