package com.zhiwei.credit.service.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.dao.creditFlow.archives.PlArchivesDao;
import com.zhiwei.credit.model.creditFlow.archives.PlArchives;
import com.zhiwei.credit.service.creditFlow.archives.PlArchivesService;

/**
 * 
 * @author 
 *
 */
public class PlArchivesServiceImpl extends BaseServiceImpl<PlArchives> implements PlArchivesService{
	@SuppressWarnings("unused")
	private PlArchivesDao dao;
	
	public PlArchivesServiceImpl(PlArchivesDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<PlArchives> getallcabinet(int start, int limit) {
		// TODO Auto-generated method stub
		return dao.getallcabinet(start,limit);
	}

	@Override
	public int getallcabinetsize() {
		// TODO Auto-generated method stub
		return dao.getallcabinetsize();
	}

	@Override
	public List<PlArchives> getallchecker(int start, int limit) {
		// TODO Auto-generated method stub
		return dao.getallchecker(start, limit);
	}

	@Override
	public int getallcheckersize() {
		// TODO Auto-generated method stub
		return dao.getallcheckersize();
	}

	@Override
	public List<PlArchives> getcheckerbyparentid(Long parentId, int start,
			int limit) {
		// TODO Auto-generated method stub
		return dao.getcheckerbyparentid(parentId, start, limit);
	}

	@Override
	public int getcheckerbyparentidsize(Long parentId) {
		// TODO Auto-generated method stub
		return dao.getcheckerbyparentidsize(parentId);
	}

	@Override
	public List<PlArchives> searchcabinet(String name,String companyId, int start, int limit) {
		// TODO Auto-generated method stub
		return dao.searchcabinet(name,companyId, start, limit);
	}

	@Override
	public int searchcabinetsize(String name,String companyId) {
		// TODO Auto-generated method stub
		return dao.searchcabinetsize(name,companyId);
	}

	@Override
	public String getCheckerTree(String node,String companyId) {
		String json = "";
		List list = new ArrayList();
		List LList = new ArrayList();
		PlArchives plArchives=new PlArchives();
		list = dao.getbycompanyId(companyId) ;
		if(node == "0" || "0".equals(node)){
			if(list != null && list.size() != 0){
				for(int i=0;i<list.size();i++){//循环银行
					plArchives = (PlArchives)list.get(i);
					dao.evict(plArchives);
					LList.add(plArchives);
				}
			}
		}else{
			list = dao.getcheckerbyparentid(Long.parseLong(node), 0, 999999);//保证金账户表
			if(list == null || list.size() == 0){
				
			}else{
				for(int i=0;i<list.size();i++){//循环银行
					plArchives = (PlArchives)list.get(i);
					plArchives.setPathname(dao.get(plArchives.getParentid()).getName()+"-"+plArchives.getCode());
					dao.evict(plArchives);
					LList.add(plArchives);
			   }
			}
			
			
			
		}
		JSONArray jsonArray=JSONArray.fromObject(LList); 
		json = jsonArray.toString();
		JsonUtil.responseJsonString(json);
		return json;
	}




}