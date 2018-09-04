package com.zhiwei.credit.model.flow;

public class FormTableItem{
	protected Long tableId;
	protected String tableName;
	protected String tableKey;
	protected Short isMain;
	protected String fields;
	public Long getTableId() {
		return tableId;
	}
	public void setTableId(Long tableId) {
		this.tableId = tableId;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getTableKey() {
		return tableKey;
	}
	public void setTableKey(String tableKey) {
		this.tableKey = tableKey;
	}
	public Short getIsMain() {
		return isMain;
	}
	public void setIsMain(Short isMain) {
		this.isMain = isMain;
	}
	public String getFields() {
		return fields;
	}
	public void setFields(String fields) {
		this.fields = fields;
	}
}