package com.zhiwei.credit.model.admin;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

import com.zhiwei.core.model.BaseModel;

/**
 * @description 会议类型
 * @date 2010-9-25 PM
 * @author YHZ
 * 
 */
@SuppressWarnings("serial")
public class BoardType extends BaseModel {

	private Long typeId;
	private String typeName;
	private String typeDesc;

	public BoardType() {
		super();
	}

	public BoardType(Long in_typeId) {
		this.setTypeId(in_typeId);
	}

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public String getTypeName() {
		return typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getTypeDesc() {
		return typeDesc;
	}

	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}

	/**
	 * @description equals
	 * @param obj
	 *            Object对象
	 * @return boolean
	 */
	public boolean equals(Object obj) {
		if (!(obj instanceof BoardType)) {
			return false;
		}
		BoardType rhs = (BoardType) obj;
		return new EqualsBuilder().append(this.typeId, rhs.typeId).append(
				this.typeName, rhs.typeName)
				.append(this.typeDesc, rhs.typeDesc).isEquals();
	}

	/**
	 * @description hashCode
	 * @return Integer
	 */
	public int hashCode() {
		return new HashCodeBuilder(-82280557, -700257973).append(this.typeId)
				.append(this.typeName).append(this.typeDesc).hashCode();
	}

	/**
	 * @description toString
	 * @return String
	 */
	public String toString() {
		return new ToStringBuilder(this).append("typeId", this.typeId).append(
				"typeName", this.typeName).append("typeDesc", this.typeDesc)
				.toString();
	}
}
