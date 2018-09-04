package com.zhiwei.credit.dao.creditFlow.creditAssignment.project.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.project.ObObligationProjectDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.ObObligationProject;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class ObObligationProjectDaoImpl extends BaseDaoImpl<ObObligationProject> implements ObObligationProjectDao{

	public ObObligationProjectDaoImpl() {
		super(ObObligationProject.class);
	}
	@Override
	public ObObligationProject getInfo(String projectId) {
		String hql="from ObObligationProject as op where op.projectId="+projectId;
		List<ObObligationProject>  list=getSession().createQuery(hql).list();
		if(!list.isEmpty()){
			return list.get(0);
		}else{
			return null;
		}
	}

//查询出来债权产品的列表
	@Override
	public List<ObObligationProject> getProjectList(String projectNumber,
			String obligationName, String obligationNumber,
			String projectMoney, String payintentPeriod,String obligationState) {
		// TODO Auto-generated method stub
		String hql=" from ObObligationProject as obligation where 1=1 ";
		if(obligationState!=null&&!"".equals(obligationState)){
			hql=hql+" and obligation.obligationStatus="+Short.valueOf(obligationState);
		}
		if(projectNumber!=null&& !"".equals(projectNumber)&&!"null".equals(projectNumber)){
			hql=hql+" and obligation.projectNumber="+projectNumber;
		}
		if(obligationName!=null&& !"".equals(obligationName)&&!"null".equals(obligationName)){
			hql=hql+"  and obligation.obligationName  like '%" +obligationName+"%'";
		}
		if(obligationNumber!=null&& !"".equals(obligationNumber)&&!"null".equals(obligationNumber)){
			hql=hql+"  and obligation.obligationNumber  ="+obligationNumber;
		}
		if(projectMoney!=null&& !"".equals(projectMoney)&&!"null".equals(projectMoney)){
			BigDecimal money =new BigDecimal(projectMoney);
			hql =hql+" and obligation.projectMoney >="+money;
		}if(payintentPeriod!=null&& !"".equals(payintentPeriod)&&!"null".equals(payintentPeriod)){
			hql=hql+" and obligation.payintentPeriod="+Integer.valueOf(payintentPeriod);
		}
		
		return this.getSession().createQuery(hql).list();
	}

	//查询出来债权产品信息
	@Override
	public List<ObObligationProject> getProjectListInfo(String projectName,
			String obligationNumber, String payintentPeriod) {
		String hql=" from ObObligationProject as obligation where 1=1 and (obligation.obligationStatus=0 or obligation.obligationStatus is null)";
		if(null!=projectName && !"".equals(projectName)&&!"null".equals(projectName)){
			hql=hql+"  and obligation.projectName  like '%" +projectName+"%'";
		}
		if(obligationNumber!=null&& !"".equals(obligationNumber)&&!"null".equals(obligationNumber)){
			hql=hql+" and obligation.obligationNumber="+obligationNumber;
		}
		if(null!=payintentPeriod&& !"".equals(payintentPeriod)&&!"null".equals(payintentPeriod)){
			hql =hql+" and obligation.payintentPeriod ="+payintentPeriod;
		}
		return this.getSession().createQuery(hql).list();
	}

}