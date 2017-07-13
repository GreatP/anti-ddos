package com.cetc.backend.web.model;

public class FileMeta {

	private String url;
	private String thumbnaiUrl;
	private String name;
	private String type;
	private long size;
	private String deleteUrl;
	private String deleteType;
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getThumbnaiUrl() {
		return thumbnaiUrl;
	}
	public void setThumbnaiUrl(String thumbnaiUrl) {
		this.thumbnaiUrl = thumbnaiUrl;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public long getSize() {
		return size;
	}
	public void setSize(long size) {
		this.size = size;
	}
	public String getDeleteUrl() {
		return deleteUrl;
	}
	public void setDeleteUrl(String deleteUrl) {
		this.deleteUrl = deleteUrl;
	}
	public String getDeleteType() {
		return deleteType;
	}
	public void setDeleteType(String deleteType) {
		this.deleteType = deleteType;
	}
	
}
