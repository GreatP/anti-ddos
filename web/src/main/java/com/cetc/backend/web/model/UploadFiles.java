package com.cetc.backend.web.model;

import java.util.ArrayList;
import java.util.List;

public class UploadFiles {

	private List<FileMeta> files = new ArrayList<FileMeta>();

	public List<FileMeta> getFiles() {
		return files;
	}

	public void setFiles(List<FileMeta> files) {
		this.files = files;
	}
	
	public void addFile(FileMeta file) {
		this.files.add(file);
	}
}
