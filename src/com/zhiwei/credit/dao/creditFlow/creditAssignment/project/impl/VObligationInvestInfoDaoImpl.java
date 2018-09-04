package com.zhiwei.credit.dao.creditFlow.creditAssignment.project.impl;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.project.VObligationInvestInfoDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.project.VObligationInvestInfo;

public class VObligationInvestInfoDaoImpl extends BaseDaoImpl<VObligationInvestInfo> implements VObligationInvestInfoDao {

	public VObligationInvestInfoDaoImpl() {
		super(VObligationInvestInfo.class);
		// TODO Auto-generated constructor stub
	}
	//查出所有债权信息，或者是不包含系统默认的金帆平台债权账户
	@SuppressWarnings("unchecked")
	@Override
	public List<VObligationInvestInfo> getAllList(String obligationState,
			String obligationName, String investName, String payintentPeriod,
			String investEndDate, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String  hql =" from VObligationInvestInfo as v where 1=1 ";
		if("1".equals(obligationState)){//这个obligationState用来区分是否查出系统默认的平台债权信息
			hql=hql+" and v.systemInvest=1";
		}
		if (obligationName!=null&!"".equals(obligationName)){//债权产品名称
			hql=hql+" and v.obligationName like '%"+obligationName+"%'";
		}
		if(investName!=null&!"".equals(investName)){//投资人姓名
			hql=hql+" and v.investName like '%"+investName+"%'";
		} 
		if(payintentPeriod!=null&!"".equals(payintentPeriod)){
			hql=hql+" and v.payintentPeriod >= "+Integer.valueOf(payintentPeriod);
		}  
		if(investEndDate!=null&!"".equals(investEndDate)){
		
				hql=hql+" and v.investEndDate = '"+/*format1.format(new Date(*/investEndDate/*))*/+"'";
			
		}
		return this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}
	//债权到期提醒查询方法
	@SuppressWarnings("unchecked")
	@Override
	public List<VObligationInvestInfo> getInvestList(String obligationState,
			String obligationName, String investName, String investStartDate,
			String investEndDate, Integer start, Integer limit) {
		// TODO Auto-generated method stub
		DateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
		String  hql =" from VObligationInvestInfo as v where 1=1 ";
		if("1".equals(obligationState)){//这个obligationState用来区分是否查出系统默认的平台债权信息
			hql=hql+" and v.systemInvest=1";
		}
		if (obligationName!=null&!"".equals(obligationName)){//债权产品名称
			hql=hql+" and v.obligationName like '%"+obligationName+"%'";
		}
		if(investName!=null&!"".equals(investName)){//投资人姓名
			hql=hql+" and v.investName like '%"+investName+"%'";
		} 
		if(investStartDate!=null&!"".equals(investStartDate)){
			
				hql=hql+" and v.investStartDate = '"+/*format1.format(new Date(*/investStartDate/*))*/+"'";
			
		}  
		if(investEndDate!=null&!"".equals(investEndDate)){
			String date =format1.format(new Date(investEndDate));
			if(date.equals(format1.format(new Date()))){
				hql=hql+" and v.investEndDate = '"+date+"'";
			}else{
				hql=hql+" and v.investEndDate >= '"+date+"'";
			}
				
		} 
		
		return this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}
	//根据投资人的id查出来投资人的债权情况
	@SuppressWarnings("unchecked")
	@Override
	public List<VObligationInvestInfo> getlistInvestPersonByPersonId(
			String investPersonId, String obligationState, Integer start,
			Integer limit) {
		// TODO Auto-generated method stub
		String  hql =" from VObligationInvestInfo as v where v.investMentPersonId= "+Long.valueOf(investPersonId);
		if("1".equals(obligationState)){//这个obligationState用来区分是否查出系统默认的平台债权信息
			hql=hql+" and v.systemInvest=1";
		}
		return this.getSession().createQuery(hql).setFirstResult(start).setMaxResults(limit).list();
	}
	/**根据投资人的id,回款状态，日期查出来投资人的债权情况*/
	@Override
	public List<VObligationInvestInfo> getlistInvestPersonObligation(String investPersonId, String obligationState, String investStartDate, String investEndDate){
			String  hql =" from VObligationInvestInfo as v where v.investMentPersonId= "+Long.valueOf(investPersonId);
			if(null!=obligationState&&""!=obligationState){
				hql=hql+" and v.investObligationStatus="+obligationState;
			}
			if(null!=investStartDate&&""!=investStartDate){
				hql=hql+" and v.investStartDate > '"+investStartDate+"'";
			}
			if(null!=investEndDate&&""!=investEndDate){
				hql=hql+" and v.investEndDate <'"+investEndDate+"'";
			}
			return this.getSession().createQuery(hql).list();
	}
	
	

}
