package com.maoni.shaders.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.ByteBuffer;

import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL32;

public enum CreateShader {
	VERTEX(GL20.GL_VERTEX_SHADER),
	FRAGMENT(GL20.GL_FRAGMENT_SHADER),
	GEOMETRY(GL32.GL_GEOMETRY_SHADER)
	;
	
	final int shaderType;
	private CreateShader(final int shaderType) {
		this.shaderType = shaderType;
	}
	
	public int load(final String shaderLocation) {
		int shader = GL20.glCreateShader(shaderType);
		
		final String shaderSource = loadShaderFromFile(shaderLocation);
		ByteBuffer buf = ByteBuffer.allocateDirect(shaderSource.getBytes().length);
		buf.put(shaderSource.getBytes());
		
		// Important, sets the buffer back to the beginning following the put
		buf.clear();
		
		GL20.glShaderSource(shader, buf);
		GL20.glCompileShader(shader);
	
		if(GetLogInfo.INSTANCE.printLogInfo(this.name() + " Shader", shader)) {
			shader = 0;
		}
		return shader;
	}

	private String loadShaderFromFile(final String shaderLocation) {
		final StringBuffer sb = new StringBuffer();
		BufferedReader buf;
		String line;
		try {
			buf = new BufferedReader(new FileReader(shaderLocation));
			while ((line = buf.readLine()) != null) {
				sb.append(line);
				sb.append("\n");
			}
			buf.close();
		} catch (IOException e) {
			e.printStackTrace();
		} 
		return sb.toString();
	}

}
