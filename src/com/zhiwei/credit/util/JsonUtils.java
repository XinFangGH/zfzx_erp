package com.zhiwei.credit.util;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.credit.model.admin.Car;
import com.zhiwei.credit.model.p2p.article.Articlecategory;

import flexjson.JSONSerializer;

public class JsonUtils {
	public static final String TYPE_OBJ = "type_obj";
	public static final String TYPE_LIST = "type_list";

	public static String getJson(Object o, String type) {
		String ret = "";

		if (type.equals(TYPE_LIST)) {
			ret = getList2Json(o);
		} else {
			ret = getObj2Json(o);
		}
		return ret;
	}

	@SuppressWarnings("unchecked")
	public static String getList2Json(Object o) {

		JSONSerializer serializer = JsonUtil.getJSONSerializer("createDate",
				"modifyDate");
		StringBuffer buff = new StringBuffer("{success:true")
				.append(",'result':");
		// buff.append(serializer.exclude(new String[]{"class"}).serialize(o));
		Gson gson = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation()
				.setDateFormat("yyyy-MM-dd").registerTypeAdapterFactory(
						HibernateProxyTypeAdapter.FACTORY).create();
		buff.append(gson.toJson(o));
		buff.append("}");
		return buff.toString().replaceAll("null", "''");
	}

	public static String getObj2Json(Object o) {
		StringBuffer buff = new StringBuffer();
		Gson gson = new GsonBuilder().disableHtmlEscaping().excludeFieldsWithoutExposeAnnotation().setDateFormat("yyyy-MM-dd").registerTypeAdapterFactory(
				HibernateProxyTypeAdapter.FACTORY).create();
		buff.append(gson.toJson(o));
		return buff.toString().replaceAll("null", "''");
	}
}
