package com.zhiwei.credit.service.creditFlow.archives.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.archives.PlProjectArchivesDao;
import com.zhiwei.credit.dao.creditFlow.archives.VProjectArchivesDao;
import com.zhiwei.credit.model.creditFlow.archives.PlProjectArchives;
import com.zhiwei.credit.model.creditFlow.archives.VProjectArchives;
import com.zhiwei.credit.service.creditFlow.archives.PlProjectArchivesService;
import com.zhiwei.credit.service.creditFlow.archives.VProjectArchivesService;

/**
 * 
 * @author 
 *
 */
public class VProjectArchivesServiceImpl extends BaseServiceImpl<VProjectArchives> implements VProjectArchivesService{
	@SuppressWarnings("unused")
	private VProjectArchivesDao dao;
	
	public VProjectArchivesServiceImpl(VProjectArchivesDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<VProjectArchives> searchproject(Map<String, String> map, String businessType) {
		// TODO Auto-generated method stub
		return dao.searchproject(map, businessType);
	}

	@Override
	public int searchprojectsize(Map<String, String> map, String businessType) {
		// TODO Auto-generated method stub
		return dao.searchprojectsize(map, businessType);
	}

	@Override
	public List<VProjectArchives> getbyproject(Long projectId,
			String businessType) {
		// TODO Auto-generated method stub
		return dao.getbyproject(projectId, businessType);
	}

}