package com.zhiwei.credit.core.creditUtils;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.ServletActionContext;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

/**
 * 负责将对象或者list转换成json形式的工具类
 * @author 刘俊
 */
public class JsonUtil {
	private static String defaultDateFormat = "yyyy-MM-dd" ;
	private static String defaultContentType = "text/json; charset=utf-8" ;
//--------------------------------response------------往客户端输出jsonString数据---------------------------------------------------
	/**
	 * 直接返回成功
	 */
	public static void responseJsonSuccess(){
		responseJsonString("{success:true}") ;
	}
	/**
	 * 直接返回失败
	 */
	public static void responseJsonFailure(){
		responseJsonString("{success:false}") ;
	}
	/**
	 * @author 刘俊
	 * response jsonString到客户端
	 * @param jsonString response到客户端的json字符串
	 * @param contentType response的contentType
	 */
	public static void responseJsonString(String jsonString, String contentType){
		try {
			//过滤特殊字符串防止xss工具
			/*jsonString = jsonString.replace("<", "").replace(">", "").replace("alert","").replace("onerror","")
								   .replace("/","").replace("\\","").replace("(","").replace(")","");*/
			HttpServletResponse response = ServletActionContext.getResponse();
			response.setContentType(contentType);
			PrintWriter pw = response.getWriter() ;
			pw.write(jsonString) ;
			pw.flush() ;
			pw.close() ;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * @author 刘俊
	 * 使用默认text/json contentType往客户端写json数据-----liujun
	 * @param jsonString response到客户端的json字符串
	 */
	public static void responseJsonString(String jsonString){
		responseJsonString(jsonString, defaultContentType) ;
	}
//	public static String responseJsonStringForIphone(String jsonString){
//		return responseJsonStringForIphone(jsonString, defaultContentType) ;
//	}
//----------------------------------转换map对象,输出转换的jsonString到客户端
	/**
	 * @author 刘俊
	 * 将map对象转换成jsonString response到客户端
	 * @param map Map对象
	 * @param config JsonConfig配置
	 */
	public static void jsonFromMapObject(Map<String,Object> map,JsonConfig config) {
		responseJsonString(JSONObject.fromObject(map,config).toString());
	}
	public static String jsonFromMapObjectForIphone(Map<String,Object> map,JsonConfig config) {
		return JSONObject.fromObject(map,config).toString();
	}
//---------------------------------转换list对象,输出转换后的jsonString到客户端,列表分页时使用---------------------
	/**
	 * @author 刘俊
	 * 将list转换成json形式String ，response到客户端
	 * 以topics为根拿数据
	 * @param list   传入的当前页数据List
	 * @param totalProperty  记录总数
	 * @param config  JsonConfig配置
	 */
	public static void jsonFromList(List<? extends Object> list,int totalProperty,JsonConfig config) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("totalProperty", totalProperty);
		map.put("topics", list);
		jsonFromMapObject(map,config) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,int totalProperty,JsonConfig config) {
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("totalProperty", totalProperty);
		map.put("topics", list);
		return jsonFromMapObjectForIphone(map,config) ;
	}
	/**
	 * @author 刘俊
	 * 将list转换成json形式String ，response到客户端
	 * 以topics为根拿数据
	 * @param list   传入的当前页数据List
	 * @param totalProperty  记录总数
	 * @param config  JsonConfig配置
	 * @param dateFormat 日期格式化字符串
	 */
	public static void jsonFromList(List<? extends Object> list,int totalProperty,JsonConfig config,String dateFormat){
		config.registerJsonValueProcessor(Timestamp.class, new JsonTimestampValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat)) ;
		jsonFromList(list, totalProperty, config) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,int totalProperty,JsonConfig config,String dateFormat){
		config.registerJsonValueProcessor(Timestamp.class, new JsonTimestampValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat)) ;
		return jsonFromListForIphone(list, totalProperty, config) ;
	}
	/**
	 * @author 刘俊
	 * 将list转换成json形式String ，response到客户端
	 * 以topics为根拿数据
	 * @param list   传入的当前页数据List
	 * @param totalProperty  记录总数
	 * @param dateFormat 日期格式化字符串
	 */
	public static void jsonFromList(List<? extends Object> list,int totalProperty,String dateFormat) {
		JsonConfig config = new JsonConfig();
		jsonFromList(list, totalProperty, config, dateFormat) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,int totalProperty,String dateFormat) {
		JsonConfig config = new JsonConfig();
		return jsonFromListForIphone(list, totalProperty, config, dateFormat) ;
	}
	/**
	 * @author 刘俊
	 * 将list转换成json形式String ，response到客户端
	 * 以topics为根拿数据
	 * @param list   传入的当前页数据List
	 * @param totalProperty  记录总数
	 */
	public static void jsonFromList(List<? extends Object> list,int totalProperty){
		jsonFromList(list, totalProperty, defaultDateFormat) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,int totalProperty){
		return jsonFromListForIphone(list, totalProperty, defaultDateFormat) ;
	}
//-------------------------------转换list对象,将转换后的jsonString输出到客户端,不带分页的列表使用-------------
	/**
	 * @author 刘俊
	 * 简单的把list转换成jsonString，不带记录总数totalProperty,以topics为根拿数据
	 * @param list
	 * @param config  JsonConfig配置
	 */
	public static void jsonFromList(List<? extends Object> list,JsonConfig config){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("topics", list);
		map.put("success",true) ;
		jsonFromMapObject(map,config) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,JsonConfig config){
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("topics", list);
		map.put("success",true) ;
		return jsonFromMapObjectForIphone(map,config) ;
	}
	/**
	 * @author 刘俊
	 * 简单的把list转换成jsonString，不带记录总数totalProperty,以topics为根拿数据
	 * @param list	数据集合List
	 * @param config JsonConfig配置
	 * @param dateFormat 日期格式化字符串
	 */
	public static void jsonFromList(List<? extends Object> list,JsonConfig config ,String dateFormat) {
		config.registerJsonValueProcessor(Timestamp.class, new JsonTimestampValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat)) ;
		jsonFromList(list, config) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,JsonConfig config ,String dateFormat) {
		config.registerJsonValueProcessor(Timestamp.class, new JsonTimestampValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat)) ;
		return jsonFromListForIphone(list, config) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端
	 * 简单的把list转换成jsonString
	 * 以topics为根拿数据
	 * @param list
	 * @param dateFormat 日期格式化字符串
	 */
	public static void jsonFromList(List<? extends Object> list,String dateFormat){
		JsonConfig config = new JsonConfig();
		jsonFromList(list, config,dateFormat) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list,String dateFormat){
		JsonConfig config = new JsonConfig();
		return jsonFromListForIphone(list, config,dateFormat) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端
	 * 简单的把list转换成jsonString
	 * 以topics为根拿数据,使用默认的JsonConfig,此方法已经处理Timestamp和java.util.Date,转换格式为yyyy-MM-dd
	 * @param list
	 */
	public static void jsonFromList(List<? extends Object> list){
		jsonFromList(list, defaultDateFormat) ;
	}
	public static String jsonFromListForIphone(List<? extends Object> list){
		return jsonFromListForIphone(list, defaultDateFormat) ;
	}
//---------------------------void--------------JSONOBJECT------转换对象到jsonString,并附带成功与否信息------------------------------------
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端,把Object对象转换成jsonString
	 * @param object  需要转换的对象
	 * @param config JsonConfig配置对象
	 * @param success 是否成功
	 */
	public static void jsonFromObject(Object object,JsonConfig config,boolean success) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
        map.put("data", object);
		jsonFromMapObject(map, config) ;
	}
	public static String jsonFromObjectForIphone(Object object,JsonConfig config,boolean success) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", success);
        map.put("data", object);
		return jsonFromMapObjectForIphone(map, config) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端,把Object对象转换成jsonString
	 * @param object  需要转换的对象
	 * @param config JsonConfig配置对象
	 * @param dateFormat 日期格式化字符串
	 * @param success 是否成功
	 */
	public static void jsonFromObject(Object object,JsonConfig config ,String dateFormat , boolean success){
		config.registerJsonValueProcessor(Timestamp.class, new JsonTimestampValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat)) ;
		jsonFromObject(object, config, success) ;
	}
	public static String jsonFromObjectForIphone(Object object,JsonConfig config ,String dateFormat , boolean success){
		config.registerJsonValueProcessor(Timestamp.class, new JsonTimestampValueProcessor(dateFormat));
		config.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor(dateFormat)) ;
		return jsonFromObjectForIphone(object, config, success) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端,把Object对象转换成jsonString
	 * @param object  需要转换的对象
	 * @param dateFormat 日期格式化字符串
	 * @param success 是否成功
	 */
	public static void jsonFromObject(Object object,String dateFormat,boolean success) {
		JsonConfig config = new JsonConfig();
/*		config.setJsonPropertyFilter(new PropertyFilter()
		    {
		         public boolean apply(Object source, String name, Object value) {
		           if(name.equals("bpCustEntLawsuitlist")||name.equals("bpCustEntCashflowAndSaleIncomelist")||name.equals("bpCustEntUpanddownstreamlist")) {
		             return true;
		           } else {
		             return false;
		          }
		        }
		       });*/
		jsonFromObject(object, config, dateFormat, success) ;
	}
	public static String jsonFromObjectForIphone(Object object,String dateFormat,boolean success) {
		JsonConfig config = new JsonConfig();
		return jsonFromObjectForIphone(object, config, dateFormat, success) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端,把Object对象转换成jsonString,日期处理格式为yyyy-MM-dd
	 * @param object  需要转换的对象
	 * @param success 是否成功
	 */
	public static void jsonFromObject(Object object,boolean success) {
		jsonFromObject(object, defaultDateFormat, success) ;
	}
	public static String jsonFromObjectForIphone(Object object,boolean success) {
		return jsonFromObjectForIphone(object, defaultDateFormat, success) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端,把Object对象转换成jsonString,日期处理格式为yyyy-MM-dd,默认success:true
	 * @param object  需要转换的对象
	 */
	public static void jsonFromObjectSuccess(Object object) {
		jsonFromObject(object,true) ;
	}
	/**
	 * @author 刘俊
	 * 通过response写数据至客户端,把Object对象转换成jsonString,日期处理格式为yyyy-MM-dd,默认success:false
	 * @param object  需要转换的对象
	 */
	public static void jsonFromObjectFailure(Object object) {
		jsonFromObject(object,false) ;
	}
}
