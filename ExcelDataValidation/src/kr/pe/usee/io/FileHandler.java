package kr.pe.usee.io;

import java.io.File;

abstract class FileHandler {
	File file;

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	
}
