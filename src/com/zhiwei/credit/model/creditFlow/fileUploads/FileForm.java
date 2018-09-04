package com.zhiwei.credit.model.creditFlow.fileUploads;

import java.util.Date;

import com.zhiwei.core.model.BaseModel;

/**
 * CsFile entity. @author MyEclipse Persistence Tools
 */

public class FileForm extends BaseModel implements java.io.Serializable {

	private static final long serialVersionUID = 1L;
	private Integer fileid;
	private String setname;
	private String filename;
	private String filepath;
	private String extendname;
	private Integer filesize;
	private Integer creatorid;
	private java.util.Date createtime;
	private Boolean isArchiveConfirm;
	private String archiveConfirmRemark;
	private String remark;
	private String contentType;
	private String mark;//"表名.ID"  这个很重要 是区分各种类别的 上传文件的位置标识
	private String webPath;//相对路径
	private String projId;//项目的id
	private String businessType;//业务品种
	private Integer fileAttachID;
	private Date archiveConfirmDate;//收到时间
	public Boolean isSubmit;//是否提交
	public Date submitDate;//提交时间
	public String submitRemarks;//提交备注
	private String minCompressionFilePath; //压缩成最小图片相对路径
	private String compressionFilePath; 
	private  Integer sort;//图片排序

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getMinCompressionFilePath() {
		return minCompressionFilePath;
	}
	public void setMinCompressionFilePath(String minCompressionFilePath) {
		this.minCompressionFilePath = minCompressionFilePath;
	}
	public String getCompressionFilePath() {
		return compressionFilePath;
	}
	public void setCompressionFilePath(String compressionFilePath) {
		this.compressionFilePath = compressionFilePath;
	}
	public Integer getFileAttachID() {
		return fileAttachID;
	}
	public void setFileAttachID(Integer fileAttachID) {
		this.fileAttachID = fileAttachID;
	}
	public FileForm() {
		super();
	}
	public FileForm(Integer fileid, String setname, String filename,
			String filepath, String extendname, Integer filesize,
			Integer creatorid, java.util.Date createtime, String remark,
			String contentType, String mark, String webPath, Boolean isArchiveConfirm, String archiveConfirmRemark,
			String minCompressionFilePath,String compressionFilePath) {
		super();
		this.fileid = fileid;
		this.setname = setname;
		this.filename = filename;
		this.filepath = filepath;
		this.extendname = extendname;
		this.filesize = filesize;
		this.creatorid = creatorid;
		this.createtime = createtime;
		this.isArchiveConfirm = isArchiveConfirm;
		this.archiveConfirmRemark = archiveConfirmRemark;
		this.remark = remark;
		this.contentType = contentType;
		this.mark = mark;
		this.webPath = webPath;
		this.minCompressionFilePath = minCompressionFilePath;
		this.compressionFilePath = compressionFilePath;
	}
	
	
	public Date getArchiveConfirmDate() {
		return archiveConfirmDate;
	}
	public void setArchiveConfirmDate(Date archiveConfirmDate) {
		this.archiveConfirmDate = archiveConfirmDate;
	}
	public Boolean getIsSubmit() {
		return isSubmit;
	}
	public void setIsSubmit(Boolean isSubmit) {
		this.isSubmit = isSubmit;
	}
	public Date getSubmitDate() {
		return submitDate;
	}
	public void setSubmitDate(Date submitDate) {
		this.submitDate = submitDate;
	}
	public String getSubmitRemarks() {
		return submitRemarks;
	}
	public void setSubmitRemarks(String submitRemarks) {
		this.submitRemarks = submitRemarks;
	}
	public Integer getFileid() {
		return fileid;
	}
	public void setFileid(Integer fileid) {
		this.fileid = fileid;
	}
	public String getSetname() {
		return setname;
	}
	public void setSetname(String setname) {
		this.setname = setname;
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public String getFilepath() {
		return filepath;
	}
	public void setFilepath(String filepath) {
		this.filepath = filepath;
	}
	public String getExtendname() {
		return extendname;
	}
	public void setExtendname(String extendname) {
		this.extendname = extendname;
	}
	public Integer getFilesize() {
		return filesize;
	}
	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}
	public Integer getCreatorid() {
		return creatorid;
	}
	public void setCreatorid(Integer creatorid) {
		this.creatorid = creatorid;
	}
	public java.util.Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(java.util.Date createtime) {
		this.createtime = createtime;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getContentType() {
		return contentType;
	}
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	public String getMark() {
		return mark;
	}
	public void setMark(String mark) {
		this.mark = mark;
	}
	public String getWebPath() {
		return webPath;
	}
	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}
	public String getProjId() {
		return projId;
	}
	public void setProjId(String projId) {
		this.projId = projId;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public Boolean getIsArchiveConfirm() {
		return isArchiveConfirm;
	}
	public void setIsArchiveConfirm(Boolean isArchiveConfirm) {
		this.isArchiveConfirm = isArchiveConfirm;
	}
	public String getArchiveConfirmRemark() {
		return archiveConfirmRemark;
	}
	public void setArchiveConfirmRemark(String archiveConfirmRemark) {
		this.archiveConfirmRemark = archiveConfirmRemark;
	}
	
}