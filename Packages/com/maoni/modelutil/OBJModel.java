package com.maoni.modelutil;

import java.util.ArrayList;
import java.util.List;

public class OBJModel {
	private final List<Float> vertices;
	private final List<Integer> indices;
	private final List<Float> texCoords;
	private final List<Float> normalCoords;

	public OBJModel() {
		this.vertices = new ArrayList<Float>();
		this.indices = new ArrayList<Integer>();
		this.texCoords = new ArrayList<Float>();
		this.normalCoords = new ArrayList<Float>();
	}

	public void parseVertexData(final String vertexdata) {

		String[] results = vertexdata.split(" +");
		
		if (!results[0].equals("v")) {
			throw new IllegalArgumentException(
					"Encountered malformed vertex line : " + vertexdata);
		} else {

			try {
				vertices.add(Float.parseFloat(results[1]));
				vertices.add(Float.parseFloat(results[2]));
				vertices.add(Float.parseFloat(results[3]));
			} catch (NumberFormatException nf) {
				nf.printStackTrace();
			}
		}
	}

	public void parseFaceVertexData(final String faceData) {
		String[] results = faceData.split(" +");

		if (!results[0].equals("f")) {
			throw new IllegalArgumentException(
					"Encountered malformed face line : " + faceData);
		} else {
			indices.add(Math.abs(Integer.parseInt(results[1].split("/")[0])) - 1);
			indices.add(Math.abs(Integer.parseInt(results[2].split("/")[0])) - 1);
			indices.add(Math.abs(Integer.parseInt(results[3].split("/")[0])) - 1);
		}
	}

	public void parseTextureData(final String texData) {

		String[] results = texData.split(" +");

		if (!results[0].equals("vt")) {
			throw new IllegalArgumentException(
					"Encountered malformed texture coordinate line : "
							+ texData);
		} else {
			texCoords.add(Float.parseFloat(results[1]));
			texCoords.add(Float.parseFloat(results[2]));
		}
	}

	public void parseNormalData(String normalData) {
		String[] results = normalData.split(" +");

		if (!results[0].equals("vn")) {
			throw new IllegalArgumentException(
					"Encountered malformed normal coordinate line : "
							+ normalData);
		} else {
			normalCoords.add(Float.parseFloat(results[1]));
			normalCoords.add(Float.parseFloat(results[2]));
			normalCoords.add(Float.parseFloat(results[3]));
		}

	}

	public List<Float> getVertices() {
		return this.vertices;
	}

	public List<Integer> getIndices() {
		return this.indices;
	}

	public List<Float> getTextureCoords() {
		return this.texCoords;
	}

	public List<Float> getNormalCoords() {
		return this.normalCoords;
	}
	
	public void writeOutFaceData() {
		for (Integer i : this.getIndices()) {
			System.out.println(i);
		}
	}

	public void writeOutVertexData() {
		for (Float f : this.getVertices()) {
			System.out.println(f);
		}
		
	}

}
