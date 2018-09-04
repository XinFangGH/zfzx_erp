package com.zhiwei.credit.model.flow;

/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 JinZhi WanWei Software Limited company.
 */
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.EqualsBuilder;

/**
 * FieldRights Base Java Bean, base class for the.credit.model, mapped directly to
 * database table
 * 
 * Avoid changing this file if not necessary, will be overwritten.
 * 
 * TODO: add class/table comments
 */
@SuppressWarnings("serial")
public class FieldRights extends com.zhiwei.core.model.BaseModel {

	protected Long rightId;
	protected String taskName;
	protected Short readWrite;
	protected Long refieldId;
	protected Long mappingId;
	transient protected com.zhiwei.credit.model.flow.FormField formField;

	// protected com.zhiwei.credit.model.flow.FormDefMapping formDefMapping;

	/**
	 * Default Empty Constructor for class FieldRights
	 */
	public FieldRights() {
		super();
	}

	/**
	 * Default Key Fields Constructor for class FieldRights
	 */
	public FieldRights(Long in_rightId) {
		this.setRightId(in_rightId);
	}

	public com.zhiwei.credit.model.flow.FormField getFormField() {
		return formField;
	}

	public void setFormField(com.zhiwei.credit.model.flow.FormField in_formField) {
		this.formField = in_formField;
	}

	// public com.zhiwei.credit.model.flow.FormDefMapping getFormDefMapping () {
	// return formDefMapping;
	// }
	//	
	// public void setFormDefMapping (com.zhiwei.credit.model.flow.FormDefMapping
	// in_formDefMapping) {
	// this.formDefMapping = in_formDefMapping;
	// }

	/**
	 * * @return Long
	 * 
	 * @hibernate.id column="rightId" type="java.lang.Long"
	 *               generator-class="native"
	 */
	public Long getRightId() {
		return this.rightId;
	}

	/**
	 * Set the rightId
	 */
	public void setRightId(Long aValue) {
		this.rightId = aValue;
	}

	/**
	 * * @return Long
	 */
	// public Long getMappingId() {
	// return
	// this.getFormDefMapping()==null?null:this.getFormDefMapping().getMappingId();
	// }
	//	
	// /**
	// * Set the mappingId
	// */
	// public void setMappingId(Long aValue) {
	// if (aValue==null) {
	// formDefMapping = null;
	// } else if (formDefMapping == null) {
	// formDefMapping = new com.zhiwei.credit.model.flow.FormDefMapping(aValue);
	// formDefMapping.setVersion(new Integer(0));//set a version to cheat
	// hibernate only
	// } else {
	// //
	// formDefMapping.setMappingId(aValue);
	// }
	// }

	/**
	 * * @return Long
	 */
	public Long getFieldId() {
		return this.getFormField() == null ? null : this.getFormField()
				.getFieldId();
	}

	/**
	 * Set the fieldId
	 */
	public void setFieldId(Long aValue) {
		if (aValue == null) {
			formField = null;
		} else if (formField == null) {
			formField = new com.zhiwei.credit.model.flow.FormField(aValue);
			formField.setVersion(new Integer(0));// set a version to cheat
													// hibernate only
		} else {
			//
			formField.setFieldId(aValue);
		}
	}

	/**
	 * * @return String
	 * 
	 * @hibernate.property column="taskName" type="java.lang.String"
	 *                     length="128" not-null="true" unique="false"
	 */
	public String getTaskName() {
		return this.taskName;
	}

	/**
	 * Set the taskName
	 * 
	 * @spring.validator type="required"
	 */
	public void setTaskName(String aValue) {
		this.taskName = aValue;
	}

	/**
	 * 隐藏读写权限 0=隐藏 1=读 2=写 * @return Short
	 * 
	 * @hibernate.property column="readWrite" type="java.lang.Short" length="5"
	 *                     not-null="true" unique="false"
	 */
	public Short getReadWrite() {
		return this.readWrite;
	}

	/**
	 * Set the readWrite
	 * 
	 * @spring.validator type="required"
	 */
	public void setReadWrite(Short aValue) {
		this.readWrite = aValue;
	}

	public Long getRefieldId() {
		return refieldId;
	}

	public void setRefieldId(Long refieldId) {
		this.refieldId = refieldId;
	}

	// public Long getFieldId() {
	// return fieldId;
	// }
	//
	// public void setFieldId(Long fieldId) {
	// this.fieldId = fieldId;
	// }
	//
	public Long getMappingId() {
		return mappingId;
	}

	public void setMappingId(Long mappingId) {
		this.mappingId = mappingId;
	}

	/**
	 * @see java.lang.Object#equals(Object)
	 */
	public boolean equals(Object object) {
		if (!(object instanceof FieldRights)) {
			return false;
		}
		FieldRights rhs = (FieldRights) object;
		return new EqualsBuilder().append(this.rightId, rhs.rightId).append(
				this.taskName, rhs.taskName).append(this.readWrite,
				rhs.readWrite).isEquals();
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.rightId)
				.append(this.taskName).append(this.readWrite).toHashCode();
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return new ToStringBuilder(this).append("rightId", this.rightId)
				.append("taskName", this.taskName).append("readWrite",
						this.readWrite).toString();
	}

}
