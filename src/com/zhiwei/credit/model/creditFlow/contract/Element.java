package com.zhiwei.credit.model.creditFlow.contract;

public class Element {

	private String code;
	private String value ;
	private String depict ;
	private String fieldValue ;
	private String elementType;
	private String operationType;//企业和个人的区别（定量指标中添加）add  by  linyan 2013-10-31
	private String operationTypeKey;//要素的类型（定量指标中添加）add  by  linyan 2013-10-31
	
	public String getCode() {
		return code;
	}
	public String getOperationType() {
		return operationType;
	}
	public void setOperationType(String operationType) {
		this.operationType = operationType;
	}
	public String getOperationTypeKey() {
		return operationTypeKey;
	}
	public void setOperationTypeKey(String operationTypeKey) {
		this.operationTypeKey = operationTypeKey;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDepict() {
		return depict;
	}
	public void setDepict(String depict) {
		this.depict = depict;
	}
	public String getFieldValue() {
		return fieldValue;
	}
	public void setFieldValue(String fieldValue) {
		this.fieldValue = fieldValue;
	}
	public String getElementType() {
		return elementType;
	}
	public void setElementType(String elementType) {
		this.elementType = elementType;
	}
	
}
