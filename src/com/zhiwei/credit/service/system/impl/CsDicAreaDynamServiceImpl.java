package com.zhiwei.credit.service.system.impl;
/*
 *  北京互融时代软件有限公司 hurong协同办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2010 BEIJING JINZHIWANWEI SOFTWARES LIMITED COMPANY
*/
import java.util.List;

import net.sf.json.JSONArray;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.system.CsDicAreaDynamDao;
import com.zhiwei.credit.model.system.CsDicAreaDynam;
import com.zhiwei.credit.service.system.CsDicAreaDynamService;


public class CsDicAreaDynamServiceImpl extends BaseServiceImpl<CsDicAreaDynam> implements CsDicAreaDynamService{
	
	private CsDicAreaDynamDao dao;
	public CsDicAreaDynamServiceImpl(CsDicAreaDynamDao dao) {
		super(dao);
		this.dao=dao;
	}
	/*
	 * 根据ID查找其一级子节点
	 */
	@Override
	public List<CsDicAreaDynam> getAllItemById(final Long ID){
		return dao.getAllItemById(ID);
	}

	/*
	 * 根据父ID为xx的所有节点
	 */
	@Override
	public List<CsDicAreaDynam> getAllItemByParentId(final Long parentID){
		return dao.getAllItemByParentId(parentID);
	}
	@Override
	public String getAllParentBanksTree(String node) {
		List<CsDicAreaDynam> LList = dao.getAllItemByParentId(Long.valueOf(node));
		JSONArray jsonArray=JSONArray.fromObject(LList); 
	    String json = jsonArray.toString();
		JsonUtil.responseJsonString(json);
		return json;
	}
}