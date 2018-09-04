package com.zhiwei.core.json;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

public class JsonDateDeserializer implements JsonDeserializer<java.util.Date>{
	@Override
	public Date deserialize(JsonElement jsonEl, Type type,
			JsonDeserializationContext context) throws JsonParseException {
		return new java.util.Date(jsonEl.getAsJsonPrimitive().getAsLong()); 
	}
}
