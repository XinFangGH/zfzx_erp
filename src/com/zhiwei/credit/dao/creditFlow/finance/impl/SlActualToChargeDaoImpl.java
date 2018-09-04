package com.zhiwei.credit.dao.creditFlow.finance.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.hibernate.Query;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.creditFlow.finance.SlActualToChargeDao;
import com.zhiwei.credit.dao.creditFlow.finance.SlPlansToChargeDao;
import com.zhiwei.credit.model.creditFlow.assuretenet.OurProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.assuretenet.SlProcreditAssuretenet;
import com.zhiwei.credit.model.creditFlow.finance.SlActualToCharge;
import com.zhiwei.credit.model.creditFlow.finance.SlPlansToCharge;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class SlActualToChargeDaoImpl extends BaseDaoImpl<SlActualToCharge> implements SlActualToChargeDao{
	@Resource
	public SlPlansToChargeDao slPlansToChargeDao;
	
	public SlActualToChargeDaoImpl() {
		super(SlActualToCharge.class);
	}

	@Override
	public void initActualCharges(Long projectId,String projectNumber,String projectName, List<SlPlansToCharge> plans,String businessType,Short flag) {
		 for(SlPlansToCharge temp:plans){
		   SlActualToCharge actualCharge = new SlActualToCharge();
		   actualCharge.setPlanChargeId(temp.getPlansTochargeId());
		   actualCharge.setActualChargeType(temp.getPlanChargeType());
		   actualCharge.setPlanCharges(temp.getPlanCharges());
		   actualCharge.setPayMoney(new BigDecimal(0.00));
		   actualCharge.setIncomeMoney(new BigDecimal(0.00));
		   actualCharge.setAfterMoney(new BigDecimal(0.00));
		   actualCharge.setNotMoney(new BigDecimal(0.00));
		   actualCharge.setFlatMoney(new BigDecimal(0.00));
		   actualCharge.setIntentDate(new Date());
		   actualCharge.setChargeStandard(temp.getChargeStandard());
		   actualCharge.setProjectId(projectId);
		   actualCharge.setProjectName(projectName);
		   actualCharge.setProjectNumber(projectNumber);
		   actualCharge.setBusinessType(businessType);
		   actualCharge.setIsCheck(flag);
		   if(temp.getChargeKey()!=null&&!"".equals(temp.getChargeKey())){
			   actualCharge.setChargeKey(temp.getChargeKey());
		   }else{
			   actualCharge.setChargeKey("keyIsNull");
		   }
		   
           this.save(actualCharge);			  
		}
	}

	@Override
	public void initActualChargesProduct(Long projectId, String projectNumber,String projectName, String businessType, Short flag, Long productId) {
		List<SlActualToCharge> listtemp1 = this.listbyproject(projectId, businessType);
	    if(productId!=null){
	    	List<SlPlansToCharge> list= slPlansToChargeDao.getByPProductIdAndOperationType(productId.toString(),businessType);
	    	if(list!=null&&list.size()!=0){
	    		if(listtemp1==null || listtemp1.size()==0){
	    			for(SlPlansToCharge  temp :list){
	    				if(temp.getIsType()==0){//只默认初始化公有的
	    					SlActualToCharge slActualToCharge = new SlActualToCharge();			
	    					slActualToCharge.setPlanChargeId(temp.getPlansTochargeId());
	    					slActualToCharge.setTypeName(temp.getName());
	    					//slActualToCharge.setCostType(temp.getName());
	    					slActualToCharge.setActualChargeType(temp.getPlanChargeType());
	    					slActualToCharge.setPlanCharges(temp.getPlanCharges());
	    					slActualToCharge.setPayMoney(new BigDecimal(0.00));
	    					slActualToCharge.setIncomeMoney(new BigDecimal(0.00));
	    					slActualToCharge.setAfterMoney(new BigDecimal(0.00));
	    					slActualToCharge.setNotMoney(new BigDecimal(0.00));
	    					slActualToCharge.setFlatMoney(new BigDecimal(0.00));
	    					slActualToCharge.setIntentDate(new Date());
	    					slActualToCharge.setChargeStandard(temp.getChargeStandard());
	    					slActualToCharge.setProjectId(projectId);
	    					slActualToCharge.setProjectName(projectName);
	    					slActualToCharge.setProjectNumber(projectNumber);
	    					slActualToCharge.setBusinessType(businessType);
	    					slActualToCharge.setIsCheck(flag);
	    					slActualToCharge.setProductId(productId);
	    					if(temp.getChargeKey()!=null&&!"".equals(temp.getChargeKey())){
	    						slActualToCharge.setChargeKey(temp.getChargeKey());
	    					}else{
	    						slActualToCharge.setChargeKey("keyIsNull");
	    					}
	    					this.save(slActualToCharge);
	    				}
		    		}
		    	}else{
		    		for(SlPlansToCharge  temp :list){
		    			if(temp.getIsType()==0){//只默认初始化公有的
		    				List<SlActualToCharge> s =this.checkIsExit(projectId.toString(),businessType,temp.getPlansTochargeId());
		    				if(s==null){
		    					SlActualToCharge slActualToCharge = new SlActualToCharge();
		    					slActualToCharge.setPlanChargeId(temp.getPlansTochargeId());
		    					slActualToCharge.setActualChargeType(temp.getPlanChargeType());
		    					slActualToCharge.setPlanCharges(temp.getPlanCharges());
		    					slActualToCharge.setPayMoney(new BigDecimal(0.00));
		    					slActualToCharge.setIncomeMoney(new BigDecimal(0.00));
		    					slActualToCharge.setAfterMoney(new BigDecimal(0.00));
		    					slActualToCharge.setNotMoney(new BigDecimal(0.00));
		    					slActualToCharge.setFlatMoney(new BigDecimal(0.00));
		    					slActualToCharge.setIntentDate(new Date());
		    					slActualToCharge.setChargeStandard(temp.getChargeStandard());
		    					slActualToCharge.setProjectId(projectId);
		    					slActualToCharge.setProjectName(projectName);
		    					slActualToCharge.setProjectNumber(projectNumber);
		    					slActualToCharge.setBusinessType(businessType);
		    					slActualToCharge.setIsCheck(flag);
		    					slActualToCharge.setProductId(productId);
		    					if(temp.getChargeKey()!=null&&!"".equals(temp.getChargeKey())){
		    						slActualToCharge.setChargeKey(temp.getChargeKey());
		    					}else{
		    						slActualToCharge.setChargeKey("keyIsNull");
		    					}
		    					this.save(slActualToCharge);
		    				}else{
		    					s.get(0).setProductId(productId);
		    					this.save(s.get(0));
		    					
		    				}
		    			}
		    		}
		    		List<SlActualToCharge> listtemp11 = this.listbyproject(projectId, businessType);;
		    		if(listtemp11!=null&&listtemp11.size()>0){
		    			for(SlActualToCharge  tempp:listtemp11){
		    				if(tempp.getProductId()!=null && (tempp.getProductId().compareTo(productId)==0)){
		    				}else{
		    					this.remove(tempp.getActualChargeId());
		    				}
		    			}
		    		}
		    	}
	    	}else{//如果产品没有费用清单，将已有的费用清单全部删除
	    		if(listtemp1!=null){
		    		for(SlActualToCharge temp:listtemp1){
		    			this.remove(temp.getActualChargeId());
		    		}
		    	}
	    	}
	    }else{//如果没有产品id将所有已经初始化的费用清单删除
	    	if(listtemp1!=null){
	    		for(SlActualToCharge temp:listtemp1){
	    			this.remove(temp.getActualChargeId());
	    		}
	    	}
	    }
		
	}
	
	

	private List<SlActualToCharge> checkIsExit(String projectId,
			String businessType, Long plansTochargeId) {
		String hql="from SlActualToCharge q  where q.projectId=? and q.businessType=? and q.planChargeId=? and q.slSuperviseRecordId is null and q.slEarlyRepaymentId is null and q.slAlteraccrualRecordId is null and q.bidPlanId is null";
		return this.getSession().createQuery(hql).setParameter(0, Long.valueOf(projectId)).setParameter(1, businessType).setParameter(2, plansTochargeId).list();
	}

	@Override
	public List<SlActualToCharge> listbyproject(Long projectId) {
		String hql="from SlActualToCharge q  where q.projectId="+projectId;
		return findByHql(hql);
	}
	@Override
	public List<SlActualToCharge> listbyproject(Long projectId,String businessType) {
		String hql="from SlActualToCharge q  where q.projectId="+projectId+" and q.businessType='"+businessType+"' and (q.isCheck = 0 or q.isCheck = 1) and( q.chargeKey is null or q.chargeKey not in('premiumMoney','earnestMoney'))";
		return findByHql(hql);
	}
	@Override
	public List<SlActualToCharge> listbyproject(Long projectId,String businessType, String chargeKey) {
		String hql="from SlActualToCharge q  where q.projectId="+projectId+" and q.businessType='"+businessType+"' and (q.isCheck = 0 or q.isCheck = 1) and q.chargeKey in("+chargeKey+")";
		return findByHql(hql);
	}
	@Override
	public List<SlActualToCharge> search(Map<String, String> map,String businessType) {
		String hql="from SlActualToCharge q where q.businessType='"+businessType+"' and q.isCheck = 0";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		
		/*if(businessType.equals("all")){
			hql+=" and  q.isCheck = 0 and q.payMoney=0";
		}*/
		Integer startpage=Integer.parseInt(map.get("start"));
		Integer pagesize=Integer.parseInt(map.get("limit"));
		if(map.size()!=3){
			String projNum=map.get("Q_projNum_N_EQ");
			String projName=map.get("Q_proj_Name_N_EQ");
			String fundType=map.get("Q_planChargeId_N_EQ");
			String isOverdue=map.get("Q_isOverdue_S_E");
			String notMoneyle=map.get("Q_notMoney_BD_LE");
			String intentDatege=map.get("Q_intentDate_D_GE");
			String intentDatele=map.get("Q_intentDate_D_LE");
			String companyId=map.get("companyId");
			String projectProperties=map.get("properties");
			//String operationType=map.get("Q_operationType_N_EQ");
			
			if(null !=companyId && !companyId.equals("")){
				hql=hql+" and q.companyId= "+companyId;
			}
			if(null!=projNum && !projNum.equals("")){
				hql=hql+" and q.projectNumber  like '%/"+projNum+"%'  escape '/' ";
			}
			if(null!=projName && !projName.equals("")){
				hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
			}
			/*if(!operationType.equals("")){
				hql=hql+" and q.businessType  = '"+operationType+"'";
			}*/
			if(null!=fundType && !fundType.equals("")){
				hql=hql+" and q.planChargeId = "+fundType;
			}
			if(!notMoneyle.equals("")&&notMoneyle.equals("0")){
				hql=hql+" and  q.notMoney = 0";
			}
			
			if(!notMoneyle.equals("")&&notMoneyle.equals("1")){
				hql=hql+" and  q.notMoney !=0 ";
			}
			if(null!=intentDatege && !intentDatege.equals("")){
				hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
			}
			
			if(null!=intentDatele && !intentDatele.equals("")){
				hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
			}
			if(null!=isOverdue && !isOverdue.equals("")){
				hql=hql+" and q.isOverdue = '"+isOverdue+"'";
			}
			if(null!=projectProperties && !projectProperties.equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
				}
			}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
		hql=hql+" order by q.intentDate asc";
		if(startpage==-1){
			 Query query = getSession().createQuery(hql);
			 return  query.list();
		}else{
			 Query query = getSession().createQuery(hql).setFirstResult(startpage).setMaxResults(pagesize);
				
			 return  query.list();
		}
	   
	}

	@Override
	public int searchsize(Map<String, String> map,String businessType) {
		String hql="from SlActualToCharge q where q.isCheck = 0 and q.businessType='"+businessType+"'";
		String strs=ContextUtil.getBranchIdsStr();//39,40
		if(null!=strs && !"".equals(strs)){
			hql+=" and q.companyId in ("+strs+")"; //
		}
		
		/*if(businessType.equals("all")){
			hql+=" and  q.isCheck = 0 and q.payMoney=0";
		}*/
		if(map.size()!=3){
			String projNum=map.get("Q_projNum_N_EQ");
			String projName=map.get("Q_proj_Name_N_EQ");
			String fundType=map.get("Q_planChargeId_N_EQ");
			String isOverdue=map.get("Q_isOverdue_S_E");
			String notMoneyle=map.get("Q_notMoney_BD_LE");
			String intentDatege=map.get("Q_intentDate_D_GE");
			String intentDatele=map.get("Q_intentDate_D_LE");
            String companyId=map.get("companyId");
            String projectProperties=map.get("properties");
			
			if(null !=companyId && !companyId.equals("")){
				hql=hql+" and q.companyId= "+companyId;
			}
			if(null!=projNum && !projNum.equals("")){
				hql=hql+" and q.projectNumber  like '%/"+projNum+"%'  escape '/' ";
			}
			if(null!=projName && !projName.equals("")){
				hql=hql+" and q.projectName like '%/"+projName+"%'  escape '/' ";
			}
			/*if(!operationType.equals("")){
				hql=hql+" and q.businessType  = '"+operationType+"'";
			}*/
			if(null!=fundType && !fundType.equals("")){
				hql=hql+" and q.planChargeId = "+fundType;
			}
			if(!notMoneyle.equals("")&&notMoneyle.equals("0")){
				hql=hql+" and  q.notMoney = 0";
			}
			
			if(!notMoneyle.equals("")&&notMoneyle.equals("1")){
				hql=hql+" and  q.notMoney !=0 ";
			}
			if(null!=intentDatege && !intentDatege.equals("")){
				hql=hql+" and  q.intentDate >= '"+intentDatege+"'";
			}
			
			if(null!=intentDatele && !intentDatele.equals("")){
				hql=hql+" and  q.intentDate <= '"+intentDatele+"'";
			}
			if(null!=isOverdue && !isOverdue.equals("")){
				hql=hql+" and q.isOverdue = '"+isOverdue+"'";
			}
			if(null!=projectProperties && !projectProperties.equals("")){
				if(null!=businessType && businessType.equals("SmallLoan")){
					hql=hql+" and q.projectId in (select s.projectId from SlSmallloanProject as s where s.projectProperties in ("+projectProperties+"))";
				}
			}
		}else{
			hql=hql+" and  q.notMoney !=0 ";
		}
	    Query query = getSession().createQuery(hql);
		
		 return  query.list().size();
	}

	@Override
	public int updateFlatMoney(SlActualToCharge s) {
		return getSession().createQuery("UPDATE SlActualToCharge f SET f.flatMoney='"+s.getFlatMoney()+"',f.notMoney='"+s.getNotMoney()+"' where f.actualChargeId='"+s.getActualChargeId()+"'").executeUpdate();
	}

	@Override
	public int updateOverdue(SlActualToCharge s) {
		return getSession().createQuery("UPDATE SlActualToCharge f SET f.isOverdue='"+s.getIsOverdue()+"' where f.actualChargeId='"+s.getActualChargeId()+"'").executeUpdate();
		
	}

	@Override
	public List<SlActualToCharge> getlistbyslSuperviseRecordId(
			Long slSuperviseRecordId, String businessType,Long projectId) {
		 String hql="from SlActualToCharge q  where q.slSuperviseRecordId="+slSuperviseRecordId+" and q.businessType='"+businessType+"'"+" and q.projectId="+projectId;
			
			return findByHql(hql);
	}

	@Override
	public List<SlActualToCharge> getallbycompanyId() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<SlActualToCharge> getlistbyslEarlyRepaymentRecordId(
			Long slEarlyRepaymentRecordId, String businessType, Long projectId) {
		String hql="from SlActualToCharge as s where s.slEarlyRepaymentId=? and s.projectId=? and s.businessType=?";
		return getSession().createQuery(hql).setParameter(0, slEarlyRepaymentRecordId).setParameter(1, projectId).setParameter(2, businessType).list();
	}

	@Override
	public List<SlActualToCharge> getlistbyslAlteraccrualRecordId(
			Long slAlteraccrualRecordId, String businessType, Long projectId) {
		String hql="from SlActualToCharge as s where s.slAlteraccrualRecordId=? and s.projectId=? and s.businessType=?";
		return getSession().createQuery(hql).setParameter(0, slAlteraccrualRecordId).setParameter(1, projectId).setParameter(2, businessType).list();
	}

	@Override
	public List<SlActualToCharge> getAllbyProjectId(Long projectId,String businessType) {
			String hql="from SlActualToCharge as s where s.projectId=? and s.businessType=?";
			return getSession().createQuery(hql).setParameter(0, projectId).setParameter(1, businessType).list();
	}

	@Override
	public BigDecimal getSumMoney(String bidId, String projectId2, String type,String businessType) {
		// TODO Auto-generated method stub
		String hql="select sum(s.notMoney) from SlActualToCharge as s where s.projectId=? and s.businessType=? and s.bidPlanId=? and s.notMoney>0";
		BigDecimal fee=new BigDecimal(0);
		if(type.equals("1")){//表示计算计划收入金额
			hql=hql+"  and s.payMoney=0";
			fee=(BigDecimal) this.getSession().createQuery(hql).setParameter(0, Long.valueOf(projectId2)).setParameter(1, businessType).setParameter(2, Long.valueOf(bidId)).uniqueResult();
		}else if(type.equals("2")){//表示计划支出金额
			hql=hql+"  and s.incomeMoney=0";
			fee=(BigDecimal) this.getSession().createQuery(hql).setParameter(0, Long.valueOf(projectId2)).setParameter(1, businessType).setParameter(2, Long.valueOf(bidId)).uniqueResult();
		}
		return fee;
	}
/*	@Override
	public List<SlActualToCharge> listByBidPlanId(Long bidPlanId) {
		String hql="from SlActualToCharge as s where s.bidPlanId=?";
		return this.findByHql(hql, new Object[]{bidPlanId});
	}*/

	@Override
	public List<SlActualToCharge> listByBidPlanId(Long bidPlanId) {
		String hql="from SlActualToCharge as s where s.bidPlanId=?";
		return this.findByHql(hql, new Object[]{bidPlanId});
	}

	@Override
	public List<SlActualToCharge> listbyProjectIdAndBidPlanId(Long projectId,
			String bidPlanId) {
		String hql="from SlActualToCharge as s where  s.projectId = ? and  s.bidPlanId=? ";
		return this.findByHql(hql, new Object[]{projectId,Long.valueOf(bidPlanId)});
	}


}