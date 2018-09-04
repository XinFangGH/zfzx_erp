package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * FunUrl Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * TODO: add class/table comments
 */
public class FunUrl extends com.zhiwei.core.model.BaseModel {

    protected Long urlId;
	protected String urlPath;
	protected com.zhiwei.credit.model.system.AppFunction appFunction;


	/**
	 * Default Empty Constructor for class FunUrl
	 */
	public FunUrl () {
		super();
	}
	
	public FunUrl(String urlPath){
		this.urlPath=urlPath;
	}
	
	/**
	 * Default Key Fields Constructor for class FunUrl
	 */
	public FunUrl (
		 Long in_urlId
        ) {
		this.setUrlId(in_urlId);
    }

	
	public com.zhiwei.credit.model.system.AppFunction getAppFunction () {
		return appFunction;
	}	
	
	public void setAppFunction (com.zhiwei.credit.model.system.AppFunction in_appFunction) {
		this.appFunction = in_appFunction;
	}
    

	/**
	 * 	 * @return Long
     * @hibernate.id column="urlId" type="java.lang.Long" generator-class="native"
	 */
	public Long getUrlId() {
		return this.urlId;
	}
	
	/**
	 * Set the urlId
	 */	
	public void setUrlId(Long aValue) {
		this.urlId = aValue;
	}	

	/**
	 * 	 * @return Long
	 */
	public Long getFunctionId() {
		return this.getAppFunction()==null?null:this.getAppFunction().getFunctionId();
	}
	
	/**
	 * Set the functionId
	 */	
	public void setFunctionId(Long aValue) {
	    if (aValue==null) {
	    	appFunction = null;
	    } else if (appFunction == null) {
	        appFunction = new com.zhiwei.credit.model.system.AppFunction(aValue);
	        appFunction.setVersion(new Integer(0));//set a version to cheat hibernate only
	    } else {
			appFunction.setFunctionId(aValue);
	    }
	}	

	/**
	 * 	 * @return String
	 * @hibernate.property column="urlPath" type="java.lang.String" length="128" not-null="true" unique="false"
	 */
	public String getUrlPath() {
		return this.urlPath;
	}
	
	/**
	 * Set the urlPath
	 * @spring.validator type="required"
	 */	
	public void setUrlPath(String aValue) {
		this.urlPath = aValue;
	}	

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FunUrl)) {
			return false;
		}
		FunUrl rhs = (FunUrl) object;
		return new EqualsBuilder()
				.append(this.urlId, rhs.urlId)
						.append(this.urlPath, rhs.urlPath)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.urlId) 
						.append(this.urlPath) 
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("urlId", this.urlId) 
						.append("urlPath", this.urlPath) 
				.toString();
	}



}
