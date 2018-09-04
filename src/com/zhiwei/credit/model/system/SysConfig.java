package com.zhiwei.credit.model.system;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 zhiwei Software Company
*/

import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * SysConfig Base Java Bean, base class for the.credit.model, mapped directly to database table
 * 
 * Avoid changing this file if not necessary, will be overwritten. 
 *
 * ������������\r\n\r\n���������������������������\r\n���������������
 */
public class SysConfig extends com.zhiwei.core.model.BaseModel {

	/**
	 * 开启动态密码
	 */
	public static final String DYPWD_OPEN = "1";
	/**
	 * 屏蔽动态密码
	 */
	public static final String DYPWD_COLSE = "2";
	/**
	 * 开启验证码
	 */
	public static final String CODE_OPEN = "1";
	/**
	 * 屏蔽验证码
	 */
	public static final String CODE_COLSE = "2";
	/**
	 * 开启手机短信
	 */
	public static final String SMS_OPEN = "1";
	/**
	 * 关闭手机短信
	 */
	public static final String SMS_COLSE = "2";
	/**
	 * 数据类型为Integer
	 */
	public static final Short SYS_DATA_TYPE_INTEGER = 2;
	public static final String lOCK_NUM = "lock_num";//登陆失败次数，登陆成功后清零
	public static final String LOCK_NAME = "lock_name";//登陆失败名字
	public static final String LOCK_TIME = "lock_time";//登陆锁定时间--锁定的账号还是ip？
	
    protected Long configId;
	protected String configKey;
	protected String configName;
	protected String configDesc;
	protected String typeName;
	protected Short dataType;
	protected String dataValue;
	protected String typeKey;

	public String getDataValue() {
		return dataValue;
	}

	public void setDataValue(String dataValue) {
		this.dataValue = dataValue;
	}

	/**
	 * Default Empty Constructor for class SysConfig
	 */
	public SysConfig () {
		super();
	}
	
	/**
	 * Default Key Fields Constructor for class SysConfig
	 */
	public SysConfig (
		 Long in_configId
        ) {
		this.setConfigId(in_configId);
    }

    

	/**
	 * 	 * @return Long
     * @hibernate.id column="configId" type="java.lang.Long" generator-class="native"
	 */
	public Long getConfigId() {
		return this.configId;
	}
	
	/**
	 * Set the configId
	 */	
	public void setConfigId(Long aValue) {
		this.configId = aValue;
	}	

	/**
	 * Key	 * @return String
	 * @hibernate.property column="configKey" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getConfigKey() {
		return this.configKey;
	}
	
	/**
	 * Set the configKey
	 * @spring.validator type="required"
	 */	
	public void setConfigKey(String aValue) {
		this.configKey = aValue;
	}	

	/**
	 * 配置名称	 * @return String
	 * @hibernate.property column="configName" type="java.lang.String" length="64" not-null="true" unique="false"
	 */
	public String getConfigName() {
		return this.configName;
	}
	
	/**
	 * Set the configName
	 * @spring.validator type="required"
	 */	
	public void setConfigName(String aValue) {
		this.configName = aValue;
	}	

	/**
	 * 配置描述	 * @return String
	 * @hibernate.property column="configDesc" type="java.lang.String" length="256" not-null="false" unique="false"
	 */
	public String getConfigDesc() {
		return this.configDesc;
	}
	
	/**
	 * Set the configDesc
	 */	
	public void setConfigDesc(String aValue) {
		this.configDesc = aValue;
	}	

	/**
	 * 所属分类名称	 * @return String
	 * @hibernate.property column="typeName" type="java.lang.String" length="32" not-null="true" unique="false"
	 */
	public String getTypeName() {
		return this.typeName;
	}
	
	/**
	 * Set the typeName
	 * @spring.validator type="required"
	 */	
	public void setTypeName(String aValue) {
		this.typeName = aValue;
	}	

	/**
	 * 数据类型
            1=varchar
            2=intger
            3=decimal
            4=datetime
            5=time
            	 * @return Short
	 * @hibernate.property column="dataType" type="java.lang.Short" length="5" not-null="true" unique="false"
	 */
	public Short getDataType() {
		return this.dataType;
	}
	
	/**
	 * Set the dataType
	 * @spring.validator type="required"
	 */	
	public void setDataType(Short aValue) {
		this.dataType = aValue;
	}	

	
	public String getTypeKey() {
		return typeKey;
	}

	public void setTypeKey(String typeKey) {
		this.typeKey = typeKey;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof SysConfig)) {
			return false;
		}
		SysConfig rhs = (SysConfig) object;
		return new EqualsBuilder()
				.append(this.configId, rhs.configId)
				.append(this.configKey, rhs.configKey)
				.append(this.configName, rhs.configName)
				.append(this.configDesc, rhs.configDesc)
				.append(this.typeName, rhs.typeName)
				.append(this.dataType, rhs.dataType)
				.append(this.typeKey, rhs.typeKey)
				.isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973)
				.append(this.configId) 
				.append(this.configKey) 
				.append(this.configName) 
				.append(this.configDesc) 
				.append(this.typeName) 
				.append(this.dataType) 
				.append(this.typeKey)
				.toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this)
				.append("configId", this.configId) 
				.append("configKey", this.configKey) 
				.append("configName", this.configName) 
				.append("configDesc", this.configDesc) 
				.append("typeName", this.typeName) 
				.append("dataType", this.dataType) 
				.append("typeKey",this.typeKey)
				.toString();
	}



}
