package com.zhiwei.credit;

import java.util.HashMap;
import java.util.Map;

public class FlowConstants {
	/**
	 * 流程表单字段数据类型与JAVA类型的映射
	 */
	public static Map<Object, Class> FIELD_TYPE_MAP = new HashMap<Object, Class>();
	static {
		FIELD_TYPE_MAP.put("java.lang.String", java.lang.String.class);
		FIELD_TYPE_MAP.put("java.lang.Long", java.lang.Long.class);
		FIELD_TYPE_MAP.put("java.lang.Integer", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("java.lang.Short", java.lang.Short.class);
		FIELD_TYPE_MAP.put("java.lang.Float", java.lang.Float.class);
		FIELD_TYPE_MAP.put("java.lang.Double", java.lang.Double.class);
		FIELD_TYPE_MAP.put("java.util.Date", java.util.Date.class);

		FIELD_TYPE_MAP.put("String", java.lang.String.class);
		FIELD_TYPE_MAP.put("Long", java.lang.Long.class);
		FIELD_TYPE_MAP.put("Integer", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("Short", java.lang.Short.class);
		FIELD_TYPE_MAP.put("Float", java.lang.Float.class);
		FIELD_TYPE_MAP.put("Double", java.lang.Double.class);
		FIELD_TYPE_MAP.put("Date", java.util.Date.class);

		FIELD_TYPE_MAP.put("String", java.lang.String.class);
		FIELD_TYPE_MAP.put("long", java.lang.Long.class);
		FIELD_TYPE_MAP.put("int", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("short", java.lang.Short.class);
		FIELD_TYPE_MAP.put("float", java.lang.Float.class);
		FIELD_TYPE_MAP.put("double", java.lang.Double.class);

		FIELD_TYPE_MAP.put("tinyint", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("smallint", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("mediumint", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("int", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("bigint", java.lang.Long.class);

		FIELD_TYPE_MAP.put("float", java.lang.Float.class);
		FIELD_TYPE_MAP.put("double", java.lang.Double.class);

		FIELD_TYPE_MAP.put("char", java.lang.String.class);
		FIELD_TYPE_MAP.put("varchar", java.lang.String.class);
		FIELD_TYPE_MAP.put("tinytext ", java.lang.String.class);
		FIELD_TYPE_MAP.put("text", java.lang.String.class);
		FIELD_TYPE_MAP.put("mediumtext", java.lang.String.class);
		FIELD_TYPE_MAP.put("longtext", java.lang.String.class);

		FIELD_TYPE_MAP.put("blob", java.lang.String.class);
		FIELD_TYPE_MAP.put("longblob", java.lang.String.class);

		FIELD_TYPE_MAP.put("date", java.util.Date.class);
		FIELD_TYPE_MAP.put("time", java.util.Date.class);
		FIELD_TYPE_MAP.put("datetime", java.util.Date.class);
		FIELD_TYPE_MAP.put("timestamp", java.util.Date.class);

		FIELD_TYPE_MAP.put("TINYINT", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("SMALLINT", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("MEDIUMINT", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("INT", java.lang.Integer.class);
		FIELD_TYPE_MAP.put("BIGINT", java.lang.Long.class);

		FIELD_TYPE_MAP.put("FLOAT", java.lang.Float.class);
		FIELD_TYPE_MAP.put("DOUBLE", java.lang.Double.class);

		FIELD_TYPE_MAP.put("CHAR", java.lang.String.class);
		FIELD_TYPE_MAP.put("VARCHAR", java.lang.String.class);
		FIELD_TYPE_MAP.put("TINYTEXT ", java.lang.String.class);
		FIELD_TYPE_MAP.put("TEXT", java.lang.String.class);
		FIELD_TYPE_MAP.put("MEDIUMTEXT", java.lang.String.class);
		FIELD_TYPE_MAP.put("LONGTEXT", java.lang.String.class);

		FIELD_TYPE_MAP.put("BLOB", java.lang.String.class);
		FIELD_TYPE_MAP.put("LONGBLOB", java.lang.String.class);

		FIELD_TYPE_MAP.put("DATE", java.util.Date.class);
		FIELD_TYPE_MAP.put("TIME", java.util.Date.class);
		FIELD_TYPE_MAP.put("DATETIME", java.util.Date.class);
		FIELD_TYPE_MAP.put("TIMESTAMP", java.util.Date.class);

		FIELD_TYPE_MAP.put("decimal", java.lang.Double.class);
		FIELD_TYPE_MAP.put("DECIMAL", java.lang.Double.class);

	}
	
	/**
	 * 会签投票类型常量
	 */
	public final static String SIGN_VOTE_TYPE="signVoteType";
	/**
	 * 跳至父流程参数变量名
	 */
	public final static String TO_PARENT_PATH="toParentPath";
	/**
	 * 下一任务的限定执行时间
	 */
	public final static String DUE_DATE="dueDate";
}
