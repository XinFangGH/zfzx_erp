package com.zhiwei.credit.core.util;

import java.util.Iterator;
import java.util.List;

import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.model.creditFlow.multiLevelDic.AreaDic;

public class HTMLSelectUtil {

	private CreditBaseDao creditBaseDao;
	
	public CreditBaseDao getCreditBaseDao() {
		return creditBaseDao;
	}

	public void setCreditBaseDao(CreditBaseDao creditBaseDao) {
		this.creditBaseDao = creditBaseDao;
	}

	public String[][] getSelectOption(String lable ,String parentId){
		String hql = "from AreaDic AS d where d.parentId=" +
				"(select a.id from AreaDic AS a where a.lable=?)";
		String hql2 = "from AreaDic AS d where d.parentId=?";
		String strArray[][] = null ;
		List<AreaDic> list = null; 
		try {
			if(null != parentId && !"null".equals(parentId)){
				list = creditBaseDao.queryHql(hql2, Integer.parseInt(parentId));
			}else{
				list = creditBaseDao.queryHql(hql, lable);
			}
			if(null != list){
				int i = 0;
				for(Iterator<AreaDic> iter = list.iterator();iter.hasNext();){
					AreaDic dic = (AreaDic)iter.next();
					String strText = dic.getText();
					String id = dic.getId().toString();
					if(null == strArray){
						strArray = new String[list.size()][2];
					}
					if(strText != null || "".equals(strText)) {
						strArray[i][0] = strText;
						strArray[i][1] = id;
						i ++;
					}
				}
			}else{
				return null;
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return strArray;
	}
}
