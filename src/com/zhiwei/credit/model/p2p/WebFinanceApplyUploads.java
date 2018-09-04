package com.zhiwei.credit.model.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Timer;

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.compass.core.converter.extended.DataTimeConverter;

import com.google.gson.annotations.Expose;

/**
 * 
 * @author 
 *
 */
/**
 * WebFinanceApplyUploads Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments  p2p
 */
public class WebFinanceApplyUploads extends com.zhiwei.core.model.BaseModel {

    protected Long id;
    /**
     * 用户ID
     */
	protected Long userID;
	/**
	 * 材料类型
	 */
	protected String materialstype;
	/**
	 * 文件路径
	 */
	protected String files;
	/**
	 * 审核状态（0未上传，1待审查，2已驳回，3已认证）
	 */
	protected Integer status;
	/**
	 * 上传时间
	 */
	protected java.util.Date lastuploadtime;
	protected String pictureNum;
	protected String loginName;
	protected String truename;
	/**
	 * 驳回原因
	 */
	protected String rejectReason;
	
	/**
	 * 上传的材料份数
	 */
	protected Long materialCount;
	
	public Long getMaterialCount() {
		return materialCount;
	}

	public void setMaterialCount(Long materialCount) {
		this.materialCount = materialCount;
	}

	public String getRejectReason() {
		return rejectReason;
	}

	public void setRejectReason(String rejectReason) {
		this.rejectReason = rejectReason;
	}

	public String getTruename() {
		return truename;
	}

	public void setTruename(String truename) {
		this.truename = truename;
	}

	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}

	public String getPictureNum() {
		return pictureNum;
	}

	public void setPictureNum(String pictureNum) {
		this.pictureNum = pictureNum;
	}

	/**
	 * Default Empty Constructor for class WebFinanceApplyUploads
	 */
	public WebFinanceApplyUploads () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class WebFinanceApplyUploads
	 */
	public WebFinanceApplyUploads (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="id" type="java.lang.Long" generator-class="native"
	 */
	public Long getId() {
		return this.id;
	}
	
	/**
	 * Set the id
	 */	
	public void setId(Long aValue) {
		this.id = aValue;
	}	

	/**
	 * 	 * @return Long
	 * @hibernate.property column="userID" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUserID() {
		return this.userID;
	}
	
	/**
	 * Set the userID
	 */	
	public void setUserID(Long aValue) {
		this.userID = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="materialstype" type="java.lang.String" length="20" not-null="false" unique="false"
	 */
	public String getMaterialstype() {
		return this.materialstype;
	}
	
	/**
	 * Set the materialstype
	 */	
	public void setMaterialstype(String aValue) {
		this.materialstype = aValue;
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="files" type="java.lang.String" length="1000" not-null="false" unique="false"
	 */
	public String getFiles() {
		return this.files;
	}
	
	/**
	 * Set the files
	 */	
	public void setFiles(String aValue) {
		this.files = aValue;
	}	

	/**
	 * 	 * @return Integer
	 * @hibernate.property column="status" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getStatus() {
		return this.status;
	}
	
	/**
	 * Set the status
	 */	
	public void setStatus(Integer aValue) {
		this.status = aValue;
	}	

	/**
	 * 	 * @return java.util.Date
	 * @hibernate.property column="lastuploadtime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getLastuploadtime() {
		return this.lastuploadtime;
	}
	
	/**
	 * Set the lastuploadtime
	 */	
	public void setLastuploadtime(java.util.Date aValue) {
		this.lastuploadtime = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof WebFinanceApplyUploads)) {
			return false;
		}
		WebFinanceApplyUploads rhs = (WebFinanceApplyUploads) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.userID, rhs.userID)
				.append(this.materialstype, rhs.materialstype)
				.append(this.files, rhs.files)
				.append(this.status, rhs.status)
				.append(this.lastuploadtime, rhs.lastuploadtime)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.userID) 
				.append(this.materialstype) 
				.append(this.files) 
				.append(this.status) 
				.append(this.lastuploadtime) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("userID", this.userID) 
				.append("materialstype", this.materialstype) 
				.append("files", this.files) 
				.append("status", this.status) 
				.append("lastuploadtime", this.lastuploadtime) 
				.toString();
	}



}
