package com.zhiwei.core.json;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
/**
 * 用于转化Gson的中的日期类型SqlTime
 * @author Administrator
 *
 */
public class SqlTimestampConverter implements JsonSerializer<Timestamp> {
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	
	@Override
	public JsonElement serialize(Timestamp src, Type type,
			JsonSerializationContext context) {
		return new JsonPrimitive(sdf.format(src));
	}
}
