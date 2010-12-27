package com.maoni.shaders.util;

import java.util.List;

import org.lwjgl.opengl.GL20;

public enum CreateProgram {
	INSTANCE,
	;
	
	public int create (final List<Integer> shaderList) {
		int program = GL20.glCreateProgram();

		for(int shader : shaderList) {
			GL20.glAttachShader(program, shader);
		}
		
		GL20.glLinkProgram(program);

		if(GetLogInfo.INSTANCE.printLogInfo(this.name() + " Shader Program", program)) {
			program = 0;
		}
		return program;
	}

}
