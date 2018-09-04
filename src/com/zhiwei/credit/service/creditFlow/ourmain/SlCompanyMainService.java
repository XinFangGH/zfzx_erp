package com.zhiwei.credit.service.creditFlow.ourmain;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/

import java.util.List;


import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.ourmain.SlCompanyMain;

public interface SlCompanyMainService extends BaseService<SlCompanyMain>{
	public List<SlCompanyMain> findCompanyList(String corName);
	
	/**
	 * 获取我方企业主体
	 * @param corName
	 * @param lawName
	 * @param organizeCode
	 * @param PagingBean
	 * @return
	 */
	public List<SlCompanyMain> findCompanyListByIsPledge(String corName,String lawName,String organizeCode,PagingBean pb,String companyId);
	
	/**
	 * 获取我方企业主体-参照
	 * @param corName
	 * @param PagingBean
	 * @return
	 */
	public List<SlCompanyMain> findCompanyListReference(String corName,PagingBean pb);
	
	/**
	 * 获取我方企业主体-添加抵质押物索引匹配信息
	 * @param query
	 * @return
	 */
	public List<SlCompanyMain> queryListForCombo(String query);
	
	/**
	 * 获取我方企业主体-包含pb
	 * @param query
	 * @author gaoqingrui
	 */
	public List<SlCompanyMain> queryListWithPB(PagingBean pb);
}


