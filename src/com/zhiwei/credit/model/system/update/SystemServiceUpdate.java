package com.zhiwei.credit.model.system.update;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * 
 * @author 
 *
 */
/**
 * SystemServiceUpdate Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class SystemServiceUpdate extends com.zhiwei.core.model.BaseModel {

    protected Long id;//标的主键
	protected java.util.Date updatetime;//上传时间
	protected Long updatesize;//文件大小
	protected String filepath;//上传路径
	protected String operatorname;//操作人姓名
	protected Integer ifsuccess;//是否成功

	public String projname;//项目名称

	public  String disUpTime;//展示上传 时间
	public String disFilesize;//展示中的文件大小
	
	
	
	
	public String getDisFilesize() {
		return disFilesize;
	}

	public void setDisFilesize(String disFilesize) {
		this.disFilesize = disFilesize;
	}

	public String getDisUpTime() {
		return disUpTime;
	}

	public void setDisUpTime(String disUpTime) {
		this.disUpTime = disUpTime;
	}

	public String getProjname() {
		return projname;
	}

	public void setProjname(String projname) {
		this.projname = projname;
	}

	/**
	 * Default Empty Constructor for class SystemServiceUpdate
	 */
	public SystemServiceUpdate () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SystemServiceUpdate
	 */
	public SystemServiceUpdate (
		 Long in_id
        ) {
		this.setId(in_id);
    }

    

	/**
	 * 表主键	 * @return Long
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
	 * 上传时间	 * @return java.util.Date
	 * @hibernate.property column="updatetime" type="java.util.Date" length="19" not-null="false" unique="false"
	 */
	public java.util.Date getUpdatetime() {
		return this.updatetime;
	}
	
	/**
	 * Set the updatetime
	 */	
	public void setUpdatetime(java.util.Date aValue) {
		this.updatetime = aValue;
	}	

	/**
	 * 文件大小	 * @return Long
	 * @hibernate.property column="updatesize" type="java.lang.Long" length="19" not-null="false" unique="false"
	 */
	public Long getUpdatesize() {
		return this.updatesize;
	}
	
	/**
	 * Set the updatesize
	 */	
	public void setUpdatesize(Long aValue) {
		this.updatesize = aValue;
	}	

	/**
	 * 上传路径	 * @return String
	 * @hibernate.property column="filepath" type="java.lang.String" length="512" not-null="false" unique="false"
	 */
	public String getFilepath() {
		return this.filepath;
	}
	
	/**
	 * Set the filepath
	 */	
	public void setFilepath(String aValue) {
		this.filepath = aValue;
	}	

	/**
	 * 操作人姓名	 * @return String
	 * @hibernate.property column="operatorname" type="java.lang.String" length="64" not-null="false" unique="false"
	 */
	public String getOperatorname() {
		return this.operatorname;
	}
	
	/**
	 * Set the operatorname
	 */	
	public void setOperatorname(String aValue) {
		this.operatorname = aValue;
	}	

	/**
	 * 是否成功	 * @return Integer
	 * @hibernate.property column="ifsuccess" type="java.lang.Integer" length="10" not-null="false" unique="false"
	 */
	public Integer getIfsuccess() {
		return this.ifsuccess;
	}
	
	/**
	 * Set the ifsuccess
	 */	
	public void setIfsuccess(Integer aValue) {
		this.ifsuccess = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SystemServiceUpdate)) {
			return false;
		}
		SystemServiceUpdate rhs = (SystemServiceUpdate) object;
		return new EqualsBuilder()
				.append(this.id, rhs.id)
				.append(this.updatetime, rhs.updatetime)
				.append(this.updatesize, rhs.updatesize)
				.append(this.filepath, rhs.filepath)
				.append(this.operatorname, rhs.operatorname)
				.append(this.ifsuccess, rhs.ifsuccess)
				.append(this.projname, rhs.projname)
				
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.id) 
				.append(this.updatetime) 
				.append(this.updatesize) 
				.append(this.filepath) 
				.append(this.operatorname) 
				.append(this.ifsuccess) 
				.append(this.projname) 
			
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("id", this.id) 
				.append("updatetime", this.updatetime) 
				.append("updatesize", this.updatesize) 
				.append("filepath", this.filepath) 
				.append("operatorname", this.operatorname) 
				.append("ifsuccess", this.ifsuccess) 
				
				.append("projname", this.projname) 
				.toString();
	}



}
