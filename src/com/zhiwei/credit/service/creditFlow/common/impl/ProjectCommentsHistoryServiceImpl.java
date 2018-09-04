package com.zhiwei.credit.service.creditFlow.common.impl;

import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.common.ProjectCommentsHistoryDao;
import com.zhiwei.credit.model.creditFlow.common.ProjectCommentsHistory;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.common.ProjectCommentsHistoryService;
import com.zhiwei.credit.service.system.AppUserService;

public class ProjectCommentsHistoryServiceImpl extends BaseServiceImpl<ProjectCommentsHistory> implements ProjectCommentsHistoryService {
	@SuppressWarnings("unused")
	private ProjectCommentsHistoryDao dao;
	@Resource
	private AppUserService appUserService;
	public ProjectCommentsHistoryServiceImpl(ProjectCommentsHistoryDao dao) {
		super(dao);
		this.dao=dao;
	}
	@Override
	public List<ProjectCommentsHistory> getList(Long projectId,
			String businessType,String componentKey) {
		List<ProjectCommentsHistory> list = dao.getList(projectId, businessType, componentKey);
		if(list!=null){
			for(ProjectCommentsHistory obj:list){
				Long userId = obj.getUserId();
				AppUser au = appUserService.get(userId);
				obj.setUserPhotoURL(au.getPhoto());
				obj.setUserName(au.getFullname());
			}
		}
		return list;
	}
}
