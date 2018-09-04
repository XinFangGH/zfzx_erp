package com.zhiwei.credit.dao.creditFlow.creditmanagement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.zhiwei.credit.model.creditFlow.customer.enterprise.EnterpriseFinance;
import com.zhiwei.credit.model.creditFlow.customer.person.VPersonDic;

public class MakeScoreGetSubDao {

	public Map getForMap(String type) {
		List<Object[]> p;
		if ("企业".equals(type)) {
			p = getEnterprise();
		} else {
			p = getPerson();
		}
		Map m = new HashMap();
		for (Object[] o : p) {
			m.put(o[0], o);
		}
		return m;
	}

	// 指标元素 ,bean,属性-----要保持顺序
	public List<Object[]> getPerson() {
		List<Object[]> l = new ArrayList<Object[]>();
		l.add(new Object[] { "个人总资产", "vPersonDic", "grossasset" });
		l.add(new Object[] { "个人净资产", "vPersonDic", "grossasset" });
		// other维护
		return l;
	}
	
	public List<Object[]> getEnterprise() {
		List<Object[]> l = new ArrayList<Object[]>();
		l.add(new Object[] { "企业总资产", "enterpriseFinanceList",
				"id_xxxx_xxxx124" });
		l.add(new Object[] { "企业净资产", "enterpriseFinanceList",
				"id_xxxx_xxxx120" });
		// other维护
		return l;
	}
	
	
	//""/list/sum
	//list暂时只有一个,不维护，再有一个创建map进行维护
	public Map mappingModel() {
		Map<String, Object[]> m = new HashMap<String, Object[]>();
		m.put("vPersonDic", new Object[] { VPersonDic.class, "", "id" });
		m.put("enterpriseFinanceList", new Object[] { EnterpriseFinance.class,
				"list", "enterpriseId" });

		return m;
	}

}
