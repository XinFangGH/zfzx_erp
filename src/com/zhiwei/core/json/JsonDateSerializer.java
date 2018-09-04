package com.zhiwei.core.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

public class JsonDateSerializer implements JsonSerializer<java.util.Date>{
	@Override
	public JsonElement serialize(Date date, Type type,
			JsonSerializationContext context) {
		return new JsonPrimitive(date.getTime());      
	}
}
