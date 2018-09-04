package com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.core.commons.CreditBaseDao;
import com.zhiwei.credit.dao.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfoDao;
import com.zhiwei.credit.model.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfo;
import com.zhiwei.credit.service.creditFlow.leaseFinance.leaseobject.VLeaseFinanceObjectInfoService;

/**
 * 
 * @author 
 *
 */
public class VLeaseFinanceObjectInfoServiceImpl extends BaseServiceImpl<VLeaseFinanceObjectInfo>  implements VLeaseFinanceObjectInfoService{
	@SuppressWarnings("unused")
	private VLeaseFinanceObjectInfoDao dao;
	@Resource
	private CreditBaseDao creditBaseDao;
	
	public VLeaseFinanceObjectInfoServiceImpl(VLeaseFinanceObjectInfoDao dao) {
		super(dao);
		this.dao=dao;
	}
	
	@Override
	public List<VLeaseFinanceObjectInfo> getListByProjectId(Long projectId) {
		return dao.getListByProjectId(projectId);
	}
	public List<VLeaseFinanceObjectInfo> getVLeaseFinanceObjectInfoList(int start,int limit,String projectName,String projectNumber,String standardSize,String owner,String companyId,boolean isManaged,boolean isHandled,boolean isBuyBack){
		String objectName="";
		String proName="";
		String strs=ContextUtil.getBranchIdsStr();
		if(null!=companyId && !"".equals(companyId)){
			strs=companyId;
		}
		List<VLeaseFinanceObjectInfo> list=new ArrayList<VLeaseFinanceObjectInfo>();
		List tocalList=null;
		try {
			String hql="from VLeaseFinanceObjectInfo v where 1=1";
			//boolean类型为空的时候按照false情况处理
			if(!isManaged){
				hql += " and (v.isManaged = " + isManaged + " or v.isManaged is null) ";
			}else{
				hql += " and v.isManaged = " + isManaged + " ";
			}
			//boolean类型为空的时候按照false情况处理
			if(!isHandled){
				hql += " and (v.isHandled = " + isHandled + " or v.isHandled is null )";
			}else{
				hql += " and v.isHandled = " + isHandled + " ";
			}
			if(!isBuyBack){
				hql += " and (v.isBuyBack = " + isBuyBack+ " or v.isBuyBack is null )";
			}else{
				hql += " and v.isBuyBack = " + isBuyBack + " ";
			}
			if(null!=projectName && !"".equals(projectName)){
				hql=hql+" and v.projectName like '%"+projectName+"%'";
			}
			if(null!=projectNumber && !"".equals(projectNumber)){
				hql=hql+" and v.projectNumber like '%"+projectNumber+"%'";
			}
			if(null!=standardSize && !"".equals(standardSize)){
				hql=hql+" and v.standardSize like '%"+standardSize +"%'";
			}
			if(null!=owner && !"".equals(owner)){
				hql=hql+" and v.owner like '%"+ owner +"%'";
			}
			/*按照集团办处理
			 * if(null!=strs && !"".equals(strs)){
				hql=hql+" and v.mineId in ("+strs+")";
			}*/
			hql=hql+" order by v.id desc";
		    list = this.creditBaseDao.queryHql(hql, start, limit);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}