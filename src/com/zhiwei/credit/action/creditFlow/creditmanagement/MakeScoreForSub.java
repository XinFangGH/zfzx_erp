package com.zhiwei.credit.action.creditFlow.creditmanagement;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.core.commons.CreditBaseDaoImpl;
import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

public class MakeScoreForSub {
	@Resource
	private PersonService personService;
	@Resource
	private CreditBaseDao creditBaseDao;

	public MakeScoreForSub(CreditBaseDao c) {
//		this.creditBaseDao = c;
		this.creditBaseDao = new CreditBaseDaoImpl();
		
		
	}

	// 企业客户答案
	public Map<String, Double> getEnterpriseValue(int cid) throws Exception {
		Map<String, Double> mt = new HashMap<String, Double>();
		Map<String, String> mapping = getMappingEnterprise();

		// 资产类
		String hql = "from EnterpriseFinance e where e.enterpriseId=?";
		List<EnterpriseFinance> list = null;
		list = creditBaseDao.queryHql(hql, cid);// 所有资产类
		// other 企业相关

		getTheSubAndAns(mapping, list, 0);// 获取特定的答案与题目
		return mt;
	}

	// 个人客户答案
	public Map<String, Double> getPersonValue(int cid) throws Exception {
		Map<String, String> mapping = getMappingPerson();
		// 家庭资产
		VPersonDic person = personService.queryPerson(cid);
		Map vpM = beanToMap(person);
		// other 个人相关
		Map<String, Double> mt = getTheSubAndAns(mapping, vpM, 1);// 获取特定的答案与题目

		return mt;

	}

	public Map<String, String> getMappingForSubject(Map haveM, String type) {
		Map em = null;
		Map pm = null;
		Map allM = new HashMap();
		if (type == null) {// 显示全部
			em = getMappingPerson();
			pm = getMappingEnterprise();
		} else {
			em = ("企".equals(type) ? getMappingEnterprise() : new HashMap());
			pm = ("个".equals(type) ? getMappingPerson() : new HashMap());
		}
		allM.putAll(pm);
		allM.putAll(em);
		return allM;

	}

	private Map<String, String> getMappingPerson() {
		Map<String, String> m = new HashMap<String, String>();
		// 对应关系--维护VPersonDic
		m.put("grossasset", "个:总资产");
		m.put("homeasset", "个:家庭资产");

		// other维护

		return m;
	}

	public Map<String, String> getMappingEnterprise() {
		Map<String, String> m = new HashMap<String, String>();
		// 对应关系--维护 EnterpriseFinanceInfoAction
		m.put("id_xxxx_xxxx124", "企:总资产");
		m.put("id_xxxx_xxxx120", "企:净资产");
		return m;
	}

	// 题目 ,答案 必有答案
	private Map<String, Double> getTheSubAndAns(Map<String, String> mapping,
			Object o, int type) {
		Map<String, Double> mt = new HashMap<String, Double>();
		String temp;
		String key;
		String exam;
		Double v;
		switch (type) {
		case 0:// 企业中资产使用 答案较少
			List<EnterpriseFinance> list = (List<EnterpriseFinance>) o;
			for (EnterpriseFinance wf : list) {
				key = wf.getTextFeildId();
				exam = mapping.get(key);
				v = Double.valueOf(wf.getTextFeildText());
				mt.put(exam, v);
			}
			break;
		case 1:// for map
			Map vpM = (Map) o;
			// 遍历题目获取答案 题目较少
			for (Map.Entry<String, String> en : mapping.entrySet()) {
				temp = en.getKey(); // 题目id
				exam = en.getValue(); // 题目
				v = (Double) vpM.get(temp); // 答案
				if (v != null) {
					mt.put(exam, v);
				}
			}
			break;
		default:
			break;
		}

		return mt;
	}

	// 工具
	public Map beanToMap(Object bean) throws Exception {
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
				Object result = readMethod.invoke(bean, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					returnMap.put(propertyName, "");
				}
			}
		}
		return returnMap;
	}

}
