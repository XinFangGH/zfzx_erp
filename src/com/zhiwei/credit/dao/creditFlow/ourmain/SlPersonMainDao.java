package com.zhiwei.credit.dao.creditFlow.ourmain;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;


import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.ourmain.SlPersonMain;


/**
 * 
 * @author 
 *
 */
public interface SlPersonMainDao extends BaseDao<SlPersonMain>{
	public List<SlPersonMain> selectCardNum(String cardNum);
	
	/**
	 * 获取我方个人主体
	 * @param name
	 * @param cardnum
	 * @param PagingBean
	 * @return
	 */
	public List<SlPersonMain> findPersonListByIsPledge(String name,String cardnum,PagingBean pb,String companyId);
	
	/**
	 * 获取我方个人主体--参照
	 * @param name
	 * @param PagingBean
	 * @return
	 */
	public List<SlPersonMain> findPersonListReference(String name,PagingBean pb);
	
	/**
	 * 获取我方个人主体-添加抵质押物索引匹配信息
	 * @param query
	 * @return
	 */
	public List<SlPersonMain> queryListForCombo(String query);
}