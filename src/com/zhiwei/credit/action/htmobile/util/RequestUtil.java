package com.zhiwei.credit.action.htmobile.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;

/**
 * 扩展HttpServletRequest的功能，所有请求参数获取都通过该类方法来获取。
 */
public class RequestUtil {

	private static ThreadLocal<HttpServletRequest> reqLocal = new ThreadLocal<HttpServletRequest>();

	/**
	 * 获取出错的url
	 * 
	 * @param request
	 * @return
	 */
	public final static String getErrorUrl(HttpServletRequest request) {
		String errorUrl = (String) request
				.getAttribute("javax.servlet.error.request_uri");
		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.forward.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = (String) request
					.getAttribute("javax.servlet.include.request_uri");
		}
		if (errorUrl == null) {
			errorUrl = request.getRequestURL().toString();
		}
		return errorUrl;
	}

	/**
	 * 获取出错信息
	 * 
	 * @param request
	 * @param isInfoEnabled
	 * @return
	 */
	public final static StringBuilder getErrorInfoFromRequest(
			HttpServletRequest request, boolean isInfoEnabled) {
		StringBuilder sb = new StringBuilder();
		String errorUrl = getErrorUrl(request);
		sb.append(StringUtil.formatMsg(
				"Error processing url: %1, Referrer: %2, Error message: %3.\n",
				errorUrl, request.getHeader("REFERER"),
				request.getAttribute("javax.servlet.error.message")));

		Throwable ex = (Throwable) request.getAttribute("exception");
		if (ex == null) {
			ex = (Throwable) request
					.getAttribute("javax.servlet.error.exception");
		}

		if (ex != null) {
			sb.append(StringUtil.formatMsg("Exception stack trace: \n", ex));
		}
		return sb;
	}

	public final static String getHtml(String fullUrl) throws IOException {

		URL url = new URL(fullUrl);

		HttpURLConnection conn = (HttpURLConnection) url.openConnection();

		InputStream in = conn.getInputStream();
		Writer writer = new StringWriter();
		if (in != null) {
			char[] buffer = new char[1024];
			try {
				Reader reader = new BufferedReader(new InputStreamReader(in,
						"UTF-8"));
				int n;
				while ((n = reader.read(buffer)) != -1) {
					writer.write(buffer, 0, n);
				}
			} finally {
				in.close();
			}

		}

		return writer.toString();
	}

	public static void setHttpServletRequest(HttpServletRequest request) {
		reqLocal.set(request);
	}

	/**
	 * 获取当前请求的Request，需要保证AopFilter在web.xml里配置才能取到
	 * 
	 * @return
	 */
	public static HttpServletRequest getHttpServletRequest() {
		return reqLocal.get();
	}

	private RequestUtil() {
	}

	public static String getString(HttpServletRequest request, String key,
			String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return value;
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。<br/>
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getString(HttpServletRequest request, String key) {
		return getString(request, key, "");
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回默认字符串。<br/>
	 * 含有 '转换成''
	 * 
	 * @param request
	 * @param key
	 *            字段名成
	 * @param defaultValue
	 * @return
	 */
	public static String getStringConvert(HttpServletRequest request,
			String key, String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return value.replace("'", "''").trim();
	}

	/**
	 * 取字符串类型的参数。 如果取得的值为null，则返回空字符串。<br/>
	 * 含有 '转换成''
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getStringConvert(HttpServletRequest request, String key) {
		return getStringConvert(request, key, "");
	}

	/**
	 * 取得安全字符串。
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static String getSecureString(HttpServletRequest request,
			String key, String defaultValue) {
		String value = request.getParameter(key);
		if (StringUtil.isEmpty(value))
			return defaultValue;
		return filterInject(value);

	}

	/**
	 * 取得安全字符串，防止程序sql注入，脚本攻击。
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String getSecureString(HttpServletRequest request, String key) {
		return getSecureString(request, key, "");
	}

	/**
	 * 过滤script|iframe|\\||;|exec|insert|select|delete|update|count|chr|truncate
	 * |char字符串 防止SQL注入
	 * 
	 * @param str
	 * @return
	 */
	public static String filterInject(String str) {
		String injectstr = "\\||;|exec|insert|select|delete|update|count|chr|truncate|char";
		Pattern regex = Pattern.compile(injectstr, Pattern.CANON_EQ
				| Pattern.DOTALL | Pattern.CASE_INSENSITIVE
				| Pattern.UNICODE_CASE);
		Matcher matcher = regex.matcher(str);
		str = matcher.replaceAll("");
		str = str.replace("'", "''");
		str = str.replace("-", "—");
		str = str.replace("(", "（");
		str = str.replace(")", "）");
		str = str.replace("%", "％");

		return str;
	}

	/**
	 * 从Request中取得指定的小写值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static String getLowercaseString(HttpServletRequest request,
			String key) {
		return getString(request, key).toLowerCase();
	}

	/**
	 * 从request中取得int值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(HttpServletRequest request, String key) {
		return getInt(request, key, 0);
	}

	/**
	 * 从request中取得int值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static int getInt(HttpServletRequest request, String key,
			int defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Integer.parseInt(str);

	}

	/**
	 * 从request中取得short值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static short getShort(HttpServletRequest request, String key) {
		return getShort(request, key, (short) 0);
	}

	/**
	 * 从request中取得short值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static short getShort(HttpServletRequest request, String key,
			short defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Short.parseShort(str);

	}

	/**
	 * 从Request中取得long值 如果无值则返回0
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(HttpServletRequest request, String key) {
		return getLong(request, key, 0);
	}

	/**
	 * 取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		if (BeanUtil.isEmpty(aryKey))
			return null;
		Long[] aryLong = new Long[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryLong[i] = Long.parseLong(aryKey[i]);
		}
		return aryLong;
	}

	/**
	 * 根据一串逗号分隔的长整型字符串取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Long[] getLongAryByStr(HttpServletRequest request, String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		Long[] lAryId = new Long[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = Long.parseLong(aryId[i]);
		}
		return lAryId;
	}

	/**
	 * 根据一串逗号分隔的长整型字符串取得长整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static String[] getStringAryByStr(HttpServletRequest request,
			String key) {
		String sysUserId = request.getParameter(key);
		String[] aryId = sysUserId.split(",");
		String[] lAryId = new String[aryId.length];
		for (int i = 0; i < aryId.length; i++) {
			lAryId[i] = (aryId[i]);
		}
		return lAryId;
	}

	/**
	 * 根据键值取得整形数组
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static Integer[] getIntAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Integer[] aryInt = new Integer[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			aryInt[i] = Integer.parseInt(aryKey[i]);
		}
		return aryInt;
	}

	public static Float[] getFloatAry(HttpServletRequest request, String key) {
		String[] aryKey = request.getParameterValues(key);
		Float[] fAryId = new Float[aryKey.length];
		for (int i = 0; i < aryKey.length; i++) {
			fAryId[i] = Float.parseFloat(aryKey[i]);
		}
		return fAryId;
	}

	/**
	 * 从Request中取得long值,如果无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static long getLong(HttpServletRequest request, String key,
			long defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Long.parseLong(str);
	}

	/**
	 * 从Request中取得float值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(HttpServletRequest request, String key) {
		return getFloat(request, key, 0);
	}

	/**
	 * 从Request中取得float值,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static float getFloat(HttpServletRequest request, String key,
			float defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Float.parseFloat(request.getParameter(key));
	}

	/**
	 * 从Request中取得boolean值,如无值则返回缺省值 ture
	 * 
	 * @param request
	 * @param key
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key) {
		return getBoolean(request, key, true);
	}

	/**
	 * 从Request中取得boolean值 对字符串,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @param defaultValue
	 * @return
	 */
	public static boolean getBoolean(HttpServletRequest request, String key,
			boolean defaultValue) {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return defaultValue;
		return Boolean.parseBoolean(str);
	}

	/**
	 * 从Request中取得Date值,如无值则返回缺省值 按格式yyyy-MM-dd
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key)
			throws ParseException {
		return getDate(request, key, "yyyy-MM-dd");
	}

	/**
	 * 从Request中取得Date值,如无值则返回缺省值 按格式 yyyy-MM-dd HH:mm:ss
	 * 
	 * @param request
	 * @param key
	 * @return
	 * @throws ParseException
	 */
	public static Date getTimestamp(HttpServletRequest request, String key)
			throws ParseException {
		return getDate(request, key, "yyyy-MM-dd HH:mm:ss");
	}

	/**
	 * 从Request中取得Date值,如无值则返回缺省值
	 * 
	 * @param request
	 * @param key
	 * @param style
	 *            格式日期 yyyy-MM-dd 或yyyy-MM-dd HH:mm:ss
	 * @return
	 * @throws ParseException
	 */
	public static Date getDate(HttpServletRequest request, String key,
			String style) throws ParseException {
		String str = request.getParameter(key);
		if (StringUtil.isEmpty(str))
			return null;
		Date _date = DateFormatUtil.parse(key, style);
		return _date;

	}

	/**
	 * 取得当前页URL,如有参数则带参数
	 * 
	 * @param HttpServletRequest
	 *            request
	 * @return
	 */
	public static String getUrl(HttpServletRequest request) {
		StringBuffer urlThisPage = new StringBuffer();
		urlThisPage.append(request.getRequestURI());
		Enumeration<?> e = request.getParameterNames();
		String para = "";
		String values = "";
		urlThisPage.append("?");
		while (e.hasMoreElements()) {
			para = (String) e.nextElement();
			values = request.getParameter(para);
			urlThisPage.append(para);
			urlThisPage.append("=");
			urlThisPage.append(values);
			urlThisPage.append("&");
		}
		return urlThisPage.substring(0, urlThisPage.length() - 1);
	}

	/**
	 * 取得上一页的路径。
	 * 
	 * @param request
	 * @return
	 */
	public static String getPrePage(HttpServletRequest request) {
		return request.getHeader("Referer");
	}

	/**
	 * 把当前上下文的请求封装在map中
	 * 
	 * @param request
	 * @param remainArray
	 *            保持为数组
	 * @param isSecure
	 *            过滤不安全字符
	 * @return
	 */
	public static Map<String, Object> getParameterValueMap(
			HttpServletRequest request, boolean remainArray, boolean isSecure) {
		Map<String, Object> map = new HashMap<String, Object>();
		Enumeration<?> params = request.getParameterNames();
		while (params.hasMoreElements()) {
			String key = params.nextElement().toString();
			String[] values = request.getParameterValues(key);
			if (values == null)
				continue;
			if (values.length == 1) {
				String tmpValue = values[0];
				if (tmpValue == null)
					continue;
				tmpValue = tmpValue.trim();
				if (tmpValue.endsWith(""))
					continue;
				if (isSecure)
					tmpValue = filterInject(tmpValue);
				if (tmpValue.endsWith(""))
					continue;
				map.put(key, tmpValue);
			} else {
				String rtn = getByAry(values, isSecure);
				if (rtn.length() > 0) {
					if (remainArray)
						map.put(key, rtn.split(","));
					else
						map.put(key, rtn);
				}
			}
		}
		return map;
	}

	/**
	 * 
	 * @param aryTmp
	 * @param isSecure
	 * @return
	 */
	private static String getByAry(String[] aryTmp, boolean isSecure) {
		String rtn = "";
		for (int i = 0; i < aryTmp.length; i++) {
			String str = aryTmp[i].trim();
			if (!str.endsWith("")) {
				if (isSecure) {
					str = filterInject(str);
					if (!str.endsWith(""))
						rtn += str + ",";
				} else {
					rtn += str + ",";
				}
			}
		}
		if (rtn.length() > 0)
			rtn = rtn.substring(0, rtn.length() - 1);
		return rtn;
	}

	/**
	 * 根据参数名称获取参数值。
	 * 
	 * <pre>
	 * 1.根据参数名称取得参数值的数组。
	 * 2.将数组使用逗号分隔字符串。
	 * </pre>
	 * 
	 * @param request
	 * @param paramName
	 * @return
	 */
	public static String getStringValues(HttpServletRequest request,
			String paramName) {
		String[] values = request.getParameterValues(paramName);
		if (BeanUtil.isEmpty(values))
			return "";
		String tmp = "";
		for (int i = 0; i < values.length; i++) {
			if (i == 0) {
				tmp += values[i];
			} else {
				tmp += "," + values[i];
			}
		}
		return tmp;
	}

	/**
	 * 取得local。
	 * 
	 * @param request
	 * @return
	 */
	public static Locale getLocal(HttpServletRequest request) {
		Locale local = request.getLocale();
		if (local == null)
			local = Locale.CHINA;
		return local;
	}

	/**
	 * contentType常见文件类型java设置
	 * 
	 * @param postfix
	 * @return
	 */
	public static String getContentType(String postfix) {
		String contentType = "x-msdownload";
		postfix = postfix.toUpperCase();
		if (postfix.endsWith("XLS") || postfix.endsWith("XLT")
				|| postfix.endsWith(".XLW") || postfix.endsWith("CSV")) {
			contentType = "application/vnd.ms-excel";
		} else if (postfix.endsWith("DOC")) {
			contentType = "application/msword";
		} else if (postfix.endsWith("RTF")) {
			contentType = "application/rtf";
		} else if (postfix.endsWith("TEXT")|| postfix.endsWith("TXT")) {
			contentType = "text/plain";
		} else if (postfix.endsWith("XML") ) {
			contentType = "text/xml";
		} else if (postfix.endsWith("BMP")) {
			contentType = "image/bmp";
		} else if (postfix.endsWith("JPG") || postfix.endsWith("JPEG")) {
			contentType = "image/jpeg";
		} else if (postfix.endsWith("GIF")) {
			contentType = "image/gif";
		} else if (postfix.endsWith("AVI")) {
			contentType = "video/x-msvideo";
		} else if (postfix.endsWith("MP3")) {
			contentType = "audio/mpeg";
		} else if (postfix.endsWith("MPA") || postfix.endsWith("MPE")
				|| postfix.endsWith("MPEG") || postfix.endsWith("MPG")) {
			contentType = "video/mpeg";
		} else if (postfix.endsWith("PPT") || postfix.endsWith("PPS")) {
			contentType = "application/vnd.ms-powerpoint";
		} else if (postfix.endsWith("PDF")) {
			contentType = "application/pdf";
		} else if (postfix.endsWith("ZIP") || postfix.endsWith("RAR")) {
			contentType = "application/zip";
		}
		return contentType;
	}

}
