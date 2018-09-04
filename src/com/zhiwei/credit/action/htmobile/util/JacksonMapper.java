package com.zhiwei.credit.action.htmobile.util;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.hibernate3.Hibernate3Module;
/**
 * 实现JAVA类型与JSON相互转换
 * 
 * @author csx
 * 
 */
public class JacksonMapper extends ObjectMapper {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1232645849307489985L;

	public JacksonMapper() {
	}

	/**
	 * 带是否懒加载 的构造方法
	 * 
	 * @param forceLazyLoading
	 *            是否懒加载 true为懒加载，否则false
	 */
	public JacksonMapper(boolean forceLazyLoading) {
		Hibernate3Module mod = new Hibernate3Module();
		mod.configure(Hibernate3Module.Feature.FORCE_LAZY_LOADING,
				forceLazyLoading);
		registerModule(mod);
	}

	/**
	 * 带是时间格式化 的构造方法
	 * 
	 * @param dateFormat
	 *            格式如 yyyy-MM-dd
	 * 
	 */
	public JacksonMapper(String dateFormat) {
		setDateFormat(new SimpleDateFormat(dateFormat));
	}

	/**
	 * 构造方法
	 * 
	 * @param forceLazyLoading
	 *            是否懒加载 true 为懒加载，否则false
	 * @param dateFormat
	 *            格式如： yyyy-MM-dd
	 */
	public JacksonMapper(boolean forceLazyLoading, String dateFormat) {
		this(forceLazyLoading);
		setDateFormat(new SimpleDateFormat(dateFormat));
	}

	/**
	 * 把Object转化为Json字符串
	 * 
	 * @param object
	 *            can be pojo entity,list,map etc.
	 */
	public String toJson(Object object) {
		try {
			return this.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("解析对象错误");
		}
	}

	/**
	 * Json转List
	 * 
	 * @param json
	 *            json字符串
	 * @return list
	 */
	@SuppressWarnings("unchecked")
	public List<Map<String, Object>> toList(String json) {
		try {
			return this.readValue(json, List.class);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("解析json错误");
		}
	}

	/**
	 * json转换为java对象
	 * 
	 * @param json
	 *            字符串
	 * @param clazz
	 *            对象的class
	 * @return 返回对象
	 */
	public <T> T toObject(String json, Class<T> clazz) {
		try {
			return this.readValue(json, clazz);
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("解析json错误");
		}
	}

	/**
	 * 返回分页列表的Json格式
	 * 
	 * @param list
	 * @param totalCounts
	 * @return
	 */
	public String toPageJson(List<?> list, Integer totalCounts) {
		StringBuffer sb = new StringBuffer("{\"success\":true,\"totalCounts\":")
				.append(totalCounts).append(",\"result\":");
		sb.append(toJson(list));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 返回带有结果的Json格式
	 * 
	 * @param object
	 *            can be pojo entity,list,map etc.
	 * @return {success:true,data:['...']}
	 */
	public String toDataJson(Object object) {
		StringBuffer sb = new StringBuffer("{\"success\":true,\"data\":");
		sb.append(toJson(object));
		sb.append("}");
		return sb.toString();
	}

	/**
	 * 返回带有结果的Json格式
	 * 
	 * @param object
	 *            can be pojo entity,list,map etc.
	 * @return {success:true,result:['...']}
	 */
	public String toResultJson(Object object) {
		StringBuffer sb = new StringBuffer("{success:true,result:");
		sb.append(toJson(object));
		sb.append("}");
		return sb.toString();
	}
}
