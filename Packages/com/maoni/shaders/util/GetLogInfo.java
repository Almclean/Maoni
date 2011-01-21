package com.maoni.shaders.util;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.ARBShaderObjects;

public enum GetLogInfo {
	INSTANCE,
	;
	
	public boolean printLogInfo(final String objectType, final int obj){
        IntBuffer iVal = BufferUtils.createIntBuffer(1);
        ARBShaderObjects.glGetObjectParameterARB(obj,
        ARBShaderObjects.GL_OBJECT_INFO_LOG_LENGTH_ARB, iVal);
 
        int length = iVal.get();
        if (length > 1) {
            // We have some info we need to output.
            ByteBuffer infoLog = BufferUtils.createByteBuffer(length);
            iVal.flip();
            ARBShaderObjects.glGetInfoLogARB(obj, iVal, infoLog);
            byte[] infoBytes = new byte[length];
            infoLog.get(infoBytes);
            String out = new String(infoBytes);
            System.out.println("Info log for " + objectType + ":\n"+out);
            return true;
        } else {
        	return false;
        }
	}

}
