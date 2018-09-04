package com.zhiwei.credit.model.document;


public class DocumentFile {

	public static Short IS_FOLDER=1;
	public static Short NOT_FOLDER=0;
	private Long fileId;
	private String fileName;
	private String fileType;
	private String fileSize;
	private Short isFolder;
	private Short isShared;
	private String author;
	private String keywords;
	private java.util.Date updateTime;
	
	private Long parentId;
	private String parentName;
	private Short rightRead;
	private Short rightMod;
	private Short rightDel;
	
	
	public Long getFileId() {
		return fileId;
	}
	public void setFileId(Long fileId) {
		this.fileId = fileId;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getFileSize() {
		return fileSize;
	}
	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}
	public Short getIsFolder() {
		return isFolder;
	}
	public void setIsFolder(Short isFolder) {
		this.isFolder = isFolder;
	}
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Short getIsShared() {
		return isShared;
	}
	public void setIsShared(Short isShared) {
		this.isShared = isShared;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Short getRightRead() {
		return rightRead;
	}
	public void setRightRead(Short rightRead) {
		this.rightRead = rightRead;
	}
	public Short getRightMod() {
		return rightMod;
	}
	public void setRightMod(Short rightMod) {
		this.rightMod = rightMod;
	}
	public Short getRightDel() {
		return rightDel;
	}
	public void setRightDel(Short rightDel) {
		this.rightDel = rightDel;
	}
	public String getAuthor() {
		return author;
	}
	public void setAuthor(String author) {
		this.author = author;
	}
	public String getKeywords() {
		return keywords;
	}
	public void setKeywords(String keywords) {
		this.keywords = keywords;
	}
	public java.util.Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(java.util.Date updateTime) {
		this.updateTime = updateTime;
	}
	
	
}
