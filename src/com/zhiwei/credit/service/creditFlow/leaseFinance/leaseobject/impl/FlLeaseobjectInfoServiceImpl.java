package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfoDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfo;
import com.zhiwei.credit.model.creditFlow.leaseFinance.supplior.FlObjectSupplior;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.FlLeaseobjectInfoService;
import com.zhiwei.credit.service.creditFlow.leaseFinance.supplior.FlObjectSuppliorService;
import com.zhiwei.credit.service.system.AppUserService;

/**
 * 
 * @author 
 *
 */
public class FlLeaseobjectInfoServiceImpl extends BaseServiceImpl<FlLeaseobjectInfo> implements FlLeaseobjectInfoService{
	@SuppressWarnings("unused")
	private FlLeaseobjectInfoDao dao;
	@Resource
	private FlObjectSuppliorService flObjectSuppliorService;
	@Resource
	private AppUserService appUserService;
	
	public FlLeaseobjectInfoServiceImpl(FlLeaseobjectInfoDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	public FlLeaseobjectInfo updateFlLeaseFinanceObjectInfo(FlLeaseobjectInfo flLeaseobjectInfo,FlObjectSupplior flObjectSupplior,Long projectId){
		if("".equals(flLeaseobjectInfo.getManagePersonId())||null != flLeaseobjectInfo.getManagePersonId()){//表示有办理人，则点击提交  设置为已办理状态，需求要求
			flLeaseobjectInfo.setIsManaged(true);
		}
		flLeaseobjectInfo.setProjectId(projectId);
		
		FlLeaseobjectInfo flLeaseobjectInfo1 = null;
		try {
		if(flLeaseobjectInfo.getId()==null){//第一次保存
			FlObjectSupplior flObjectSupplior1 = flObjectSuppliorService.save(flObjectSupplior);
			flLeaseobjectInfo.setSuppliorId(flObjectSupplior1.getId());
			flLeaseobjectInfo1 = this.save(flLeaseobjectInfo);
		}else{
			flLeaseobjectInfo1 = dao.get(flLeaseobjectInfo.getId());
			if(flLeaseobjectInfo.getSuppliorId() == null){//并非选择放大镜的情况，点击保存以后，再次保存，同样不会传入suppliorId,但已有关联
				//也就是说前台返回值没有id，但是数据库有此id
				flObjectSuppliorService.updateSuppliorByObjectId(flLeaseobjectInfo1.getSuppliorId(),flObjectSupplior);
			}else{//第二次保存,选择放大镜的时候
				FlObjectSupplior flObjectSupplior1 = flObjectSuppliorService.save(flObjectSupplior);
				flLeaseobjectInfo1.setSuppliorId(flObjectSupplior1.getId());
			}
		}
		
			BeanUtil.copyNotNullProperties(flLeaseobjectInfo1,flLeaseobjectInfo);
		} catch (Exception e) {
			e.printStackTrace();
		} 
		return flLeaseobjectInfo1;
	}
	
	@Override
	public FlLeaseobjectInfo get(Long id){
		FlLeaseobjectInfo flLeaseobjectInfo = this.dao.get(id);
		//将数据转成JSON格式
		if(null!=flLeaseobjectInfo.getManagePersonId()&& !"".equals(flLeaseobjectInfo.getManagePersonId())){
			String managePersonName = appUserService.get(Long.valueOf(flLeaseobjectInfo.getManagePersonId())).getFullname();
			flLeaseobjectInfo.setManagePersonName(managePersonName);
		}
		if(null!=flLeaseobjectInfo.getHandlePersonId()&& !"".equals(flLeaseobjectInfo.getHandlePersonId())){
			String handlePersonName = appUserService.get(Long.valueOf(flLeaseobjectInfo.getHandlePersonId())).getFullname();
			flLeaseobjectInfo.setHandlePersonName(handlePersonName);
		}
		return flLeaseobjectInfo;
	}
}