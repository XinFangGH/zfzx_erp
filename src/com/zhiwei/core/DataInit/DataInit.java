package com.zhiwei.core.DataInit;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.Element;

import com.zhiwei.core.model.BaseModel;
import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.core.util.XmlUtil;

/**
 * 
 * <B>
 * <P>
 * Joffice -- http://www.hurongtime.com
 * </P>
 * </B> <B>
 * <P>
 * Copyright (C)  JinZhi WanWei Software Company (北京互融时代软件有限公司)
 * </P>
 * </B> <B>
 * <P>
 * description:无限树,程序启动数据初始化!总体思路:将xml的model 节点属性转化为Map , 再将Map 转为javaBen
 * 然后即可保存javaBean 到数据库
 * </P>
 * </B>
 * <P>
 * </P>
 * <P>
 * product:joffice
 * </P>
 * <P>
 * 
 * </P>
 * 
 * @see <P>
 *      </P>
 * @author
 * @version V1
 * @create: 2010-11-24上午10:40:26
 */
public class DataInit {
	private static Log logger = LogFactory.getLog(DataInit.class);
	private static java.text.SimpleDateFormat df = new SimpleDateFormat(
			"yyyy-mm-dd");

	// 初始化入口
	public static void init(String absPath) {
		String confPath = absPath + "/WEB-INF/classes/conf";
		String dataInitFile = confPath + "/data-init.xml";
		Document rootDoc = XmlUtil.load(dataInitFile);
		if (rootDoc != null) {
			Element rootEl = rootDoc.getRootElement();
			initNode(rootEl, null);
		}

	}

	// 递归调用
	public static void initNode(Element rootEl, Object parentObj) {
		if (rootEl != null) {
			Iterator<Element> model_It = rootEl.elementIterator();
			while (model_It.hasNext()) {
				Element modelEl = model_It.next();

				String _class = modelEl.attributeValue("class");
				String _service = modelEl.attributeValue("service");
				String _description = modelEl.attributeValue("description");

				if (_description != null)
					logger.info(_description);
				// 属性
				List<Element> propertyList = modelEl.selectNodes("property");
				Iterator<Element> modelIt = propertyList.iterator();
				// set
				List<Element> setList = modelEl.selectNodes("set");
				Iterator<Element> setIt = setList.iterator();
				try {
					BaseModel model = (BaseModel) Class
							.forName(_class).newInstance();
					BaseService service = (BaseService) AppUtil
							.getBean(_service);
					// 将节点转为Map
					Map<Object, Object> nodeMap = convertNodeToMap(modelIt,
							parentObj);
					// 将Map转为Bean
					model = (BaseModel) convertMapToBean(Class.forName(_class),
							nodeMap);
					model = (BaseModel) service.save(model);
					service.flush();
					// 将Bean转为Map
					String primary_key = modelEl.attributeValue("primary-key");
					String key_type = modelEl.attributeValue("key-type");
					Map<Object, Object> beanMap = convertBeanToMap(model);
					Object key_value = beanMap.get(primary_key);
					while (setIt.hasNext()) {
						Element setEl = setIt.next();
						initNode(setEl, key_value);// 递归
					}

				} catch (Exception e) {
					e.printStackTrace();
				}

			}
		}
	}

	// 将XML 结点转化为Map

	private static Map<Object, Object> convertNodeToMap(
			Iterator<Element> modelIt, Object parentObj) {
		Map<Object, Object> beanMap = new HashMap<Object, Object>();
		if (modelIt != null) {// 表目录
			// Iterator<Element> modelIt = modelEl.elementIterator();
			while (modelIt.hasNext()) {
				Element propertyEl = modelIt.next();
				String name = propertyEl.attributeValue("name");// 属情
				String value = propertyEl.attributeValue("value");// 值
				String foreign_key = propertyEl.attributeValue("foreign-key");
				String today_value = propertyEl.attributeValue("today-value");

				String date_format = propertyEl.attributeValue("date-format");
				if (date_format != null) {
					df.applyPattern(date_format);
				}
				java.lang.Object valueObj = null;
				if (foreign_key != null && foreign_key.equals("true")) {
					valueObj = parentObj;
				} else if (today_value != null && today_value.equals("true")) {
					valueObj = df.format(new Date());
				} else {
					valueObj = value;
				}

				beanMap.put(name, valueObj);

			}
		}
		return beanMap;
	}

	// 将javaBean 转化为 Map
	private static <T> Map<Object, Object> convertBeanToMap(Object bean)
			throws IntrospectionException {
		Class type = bean.getClass();
		Map returnMap = new HashMap();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);

		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();

				try {
					Object result = readMethod.invoke(bean, new Object[0]);
					returnMap.put(propertyName, result);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					logger.debug("解析方法名:" + readMethod + ",有误!");
					// e.printStackTrace();
				}

			}
		}
		return returnMap;
	}

	// 将Map转化为javaBean
	private static <T> T convertMapToBean(Class<T> type, Map<Object, Object> map)
			throws IntrospectionException, InstantiationException,
			IllegalAccessException {
		BeanInfo beanInfo = Introspector.getBeanInfo(type); // 获取类属性
		T t = type.newInstance(); // 创建 JavaBean 对象

		// 给 JavaBean 对象的属性赋值
		for (PropertyDescriptor descriptor : beanInfo.getPropertyDescriptors()) {
			String propertyName = descriptor.getName();
			if (map.containsKey(propertyName)) {
				// 下面一句可以 try 起来，这样当一个属性赋值失败的时候就不会影响其他属性赋值。
				String value = ConvertUtils.convert(map.get(propertyName));
				Object[] args = new Object[1];

				try {
					args[0] = df.parse(value);
				} catch (ParseException e) {
					args[0] = ConvertUtils.convert(value,
							descriptor.getPropertyType());
				}

				try {
					descriptor.getWriteMethod().invoke(t, args);
				} catch (IllegalArgumentException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return t;
	}

}
