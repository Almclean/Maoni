package com.maoni.modelutil;

import java.io.InputStream;

import com.maoni.batch.Batch;

public interface ModelLoader {
	
	public Batch createBatch(final InputStream is);

}
