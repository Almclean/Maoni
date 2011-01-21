package com.maoni.batch;

import java.util.List;

public interface Batch {
	
	// GL-like operations
	public void Draw(final int shaderProgram);
	public void Begin();
	public void End();
	
	// Vertex operations
	public List<Vertex3f> getVertices();
	public void addVertex(final Vertex3f v);
	
	// Texture Coords
	
	// Normals

}
