package com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildrenDao;
import com.zhiwei.credit.model.creditFlow.financingAgency.manageMoney.PlMmObligatoryRightChildren;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlMmObligatoryRightChildrenDaoImpl extends BaseDaoImpl<PlMmObligatoryRightChildren> implements PlMmObligatoryRightChildrenDao{

	public PlMmObligatoryRightChildrenDaoImpl() {
		super(PlMmObligatoryRightChildren.class);
	}

	@Override
	public List<PlMmObligatoryRightChildren> listbysearch(PagingBean pb,
			Map<String, String> map) {
		String childType=map.get("childType");
		StringBuffer hql = new StringBuffer("from PlMmObligatoryRightChildren fmpb where 1=1 and childType='"+childType+"'");
		String seachDate=map.get("seachDate");
		String parentOrBidName=map.get("parentOrBidName");
		String isMatching=map.get("isMatching");
		String receiver=map.get("receiver");
		if(null!=receiver && !"".equals(receiver)&&!"null".equals(receiver)){
			hql.append(" and fmpb.receiverP2PAccountNumber= '"+receiver+"'");
		}
		if(null !=seachDate&&!seachDate.equals("")){
			hql.append(" and fmpb.startDate <= '"+seachDate+"'");
			hql.append(" and fmpb.intentDate > '"+seachDate+"'");
			
		}
		if(null ==isMatching){
			
			hql.append(" and fmpb.availableMoney>0");
		}else{
			if(!isMatching.equals("")){
				
				if(isMatching.equals("1")){
					hql.append(" and fmpb.availableMoney>0");
				}else{
					
					hql.append(" and fmpb.availableMoney=0");
				}
			}else{
				
			}
		}
		if(parentOrBidName!=null&&!"".equals(parentOrBidName)){
			hql.append(" and fmpb.parentOrBidName like '%"+parentOrBidName+"%'");
		}
		hql.append(" order by fmpb.dayRate desc,fmpb.intentDate asc,fmpb.principalMoney desc");
		if(null==pb){
			return findByHql(hql.toString());
			
		}else{
			return findByHql(hql.toString(),null,pb);
		}
	
	}

	@Override
	public List<PlMmObligatoryRightChildren> listbydifferentBigOr(
			Map<String, String> map) {
		StringBuffer hql = new StringBuffer("from PlMmObligatoryRightChildren fmpb where fmpb.availableMoney>0 ");
		String seachDate=map.get("seachDate");
		if(null !=seachDate&&!seachDate.equals("")){
		hql.append(" and fmpb.startDate <= '"+seachDate+"'");
		hql.append(" and fmpb.intentDate > '"+seachDate+"'");
		}
		hql.append(" order by fmpb.principalMoney desc,fmpb.dayRate desc,fmpb.intentDate asc ");
			return findByHql(hql.toString());
			
	
	}

	@Override
	public List<PlMmObligatoryRightChildren> listbyUPLan(PagingBean pb,
			Map<String, String> map) {
		String childType=map.get("childType");
		StringBuffer sql = new StringBuffer("SELECT " +
				"pmc.childrenorId," +
				"pbp.bidProName," +
				"pbi.userName," +
				"pmc.parentOrBidId," +
				"pmc.parentOrBidName," +
				"pmc.childrenorName," +
				"pmc.startDate," +
				"pmc.intentDate,"+
                "pmc.orlimit," +
                "pmc.principalMoney," +
                "pmc.dayRate," +
                "pmc.availableMoney," +
                "pmc.surplusValueMoney,"+
                "pmc.projectId," +
                "pmc.projectName," +
                "pmc.typeRelation," +
                "pmc.type," +
                "pmc.childType"+
                " from pl_mm_obligatoryright_children AS pmc LEFT JOIN pl_bid_info AS pbi ON pbi.id=pmc.typeRelation"+
                " LEFT JOIN pl_bid_plan AS pbp ON pbp.bidId=pbi.bidId"+
                " where pmc.childType='"+childType+"'");
		String seachDate=map.get("seachDate");
		String parentOrBidName=map.get("parentOrBidName");
		String isMatching=map.get("isMatching");
		String userName=map.get("userName");
		if(null !=seachDate&&!seachDate.equals("")){
			sql.append(" and pmc.startDate <= '"+seachDate+"'");
			sql.append(" and pmc.intentDate > '"+seachDate+"'");
		}
		if(null ==isMatching){
			sql.append(" and pmc.availableMoney>0");
		}else{
			if(!isMatching.equals("")){
				if(isMatching.equals("1")){
					sql.append(" and pmc.availableMoney>0");
				}else{
					sql.append(" and pmc.availableMoney=0");
				}
			}else{
				
			}
		}
		if(parentOrBidName!=null&&!"".equals(parentOrBidName)){
			sql.append(" and pmc.parentOrBidName like '%"+parentOrBidName+"%'");
		}
		if(null !=userName && !"".equals(userName)){
			sql.append(" and pbi.userName like '%"+userName+"%'");
		}
		sql.append(" order by pmc.dayRate desc,pmc.intentDate asc,pmc.principalMoney desc");
		System.out.println("sql="+sql);
		if(null==pb){
			return this.getSession().createSQLQuery(sql.toString())
			.addScalar("childrenorId", Hibernate.LONG)
			.addScalar("bidProName", Hibernate.STRING)
			.addScalar("userName", Hibernate.STRING)
			.addScalar("parentOrBidId", Hibernate.LONG)
			.addScalar("parentOrBidName", Hibernate.STRING)
			.addScalar("childrenorName", Hibernate.STRING)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("orlimit", Hibernate.INTEGER)
			.addScalar("principalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("dayRate", Hibernate.BIG_DECIMAL)
			.addScalar("availableMoney", Hibernate.BIG_DECIMAL)
			.addScalar("surplusValueMoney", Hibernate.BIG_DECIMAL)
			.addScalar("projectId", Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("typeRelation", Hibernate.STRING)
			.addScalar("type", Hibernate.SHORT)
			.addScalar("childType", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(PlMmObligatoryRightChildren.class)).list();
		}else{
			return this.getSession().createSQLQuery(sql.toString())
			.addScalar("childrenorId", Hibernate.LONG)
			.addScalar("bidProName", Hibernate.STRING)
			.addScalar("userName", Hibernate.STRING)
			.addScalar("parentOrBidId", Hibernate.LONG)
			.addScalar("parentOrBidName", Hibernate.STRING)
			.addScalar("childrenorName", Hibernate.STRING)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("orlimit", Hibernate.INTEGER)
			.addScalar("principalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("dayRate", Hibernate.BIG_DECIMAL)
			.addScalar("availableMoney", Hibernate.BIG_DECIMAL)
			.addScalar("surplusValueMoney", Hibernate.BIG_DECIMAL)
			.addScalar("projectId", Hibernate.LONG)
			.addScalar("projectName", Hibernate.STRING)
			.addScalar("typeRelation", Hibernate.STRING)
			.addScalar("type", Hibernate.SHORT)
			.addScalar("childType", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(PlMmObligatoryRightChildren.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}
	}

	@Override
	public List<PlMmObligatoryRightChildren> balanceList(PagingBean pb,Map<String, String> map) {
		String account=map.get("account");
		String seachDate=map.get("seachDate");
		String childType=map.get("childType");
		if(null==account || "".equals(account)){
			pb.setTotalItems(0);
			return null;
		}
		StringBuffer countSql=new StringBuffer(" select count(*) from ( ");
		StringBuffer hql = new StringBuffer("select childrenorId,parentOrBidId,parentOrBidName,childrenorName,startDate,intentDate,"
				+" orlimit,principalMoney,dayRate,availableMoney,surplusValueMoney "
				+" from pl_mm_obligatoryright_children as children "
				+" where receiverP2PAccountNumber='"+account+"' and DATE_FORMAT('"+seachDate+"','%Y-%m-%d') BETWEEN children.startDate and children.intentDate "
				+" and children.surplusValueMoney>0 and children.childType='"+childType+"'");
		countSql.append(hql).append(" ) ");
		List countList=this.getSession().createSQLQuery(hql.toString()).list();
		if(null==pb){
			return this.getSession().createSQLQuery(hql.toString())
			.addScalar("childrenorId", Hibernate.LONG)
			.addScalar("parentOrBidId", Hibernate.LONG)
			.addScalar("parentOrBidName", Hibernate.STRING)
			.addScalar("childrenorName", Hibernate.STRING)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("orlimit", Hibernate.INTEGER)
			.addScalar("principalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("dayRate", Hibernate.BIG_DECIMAL)
			.addScalar("availableMoney", Hibernate.BIG_DECIMAL)
			.addScalar("surplusValueMoney", Hibernate.BIG_DECIMAL)
			.setResultTransformer(Transformers.aliasToBean(PlMmObligatoryRightChildren.class)).list();
		}else{
			pb.setTotalItems(countList.size());
			return this.getSession().createSQLQuery(hql.toString())
			.addScalar("childrenorId", Hibernate.LONG)
			.addScalar("parentOrBidId", Hibernate.LONG)
			.addScalar("parentOrBidName", Hibernate.STRING)
			.addScalar("childrenorName", Hibernate.STRING)
			.addScalar("startDate", Hibernate.DATE)
			.addScalar("intentDate", Hibernate.DATE)
			.addScalar("orlimit", Hibernate.INTEGER)
			.addScalar("principalMoney", Hibernate.BIG_DECIMAL)
			.addScalar("dayRate", Hibernate.BIG_DECIMAL)
			.addScalar("availableMoney", Hibernate.BIG_DECIMAL)
			.addScalar("surplusValueMoney", Hibernate.BIG_DECIMAL)
			.setResultTransformer(Transformers.aliasToBean(PlMmObligatoryRightChildren.class))
			.setFirstResult(pb.getFirstResult()).setMaxResults(pb.getPageSize()).list();
		}
	}

	@Override
	public BigDecimal totalMoney(String account,String keystr) {
		String childType="";
		if("mmplan".equals(keystr)){
			childType="mmplanOr";
		}else if("UPlan".equals(keystr)){
			childType="UPlanOr";
		}
		/*String hql=" select sum(availableMoney)"
			+" from pl_mm_obligatoryright_children as children "
			+" LEFT JOIN pl_bid_plan as plan on plan.bidId=children.parentOrBidId and plan.receiverP2PAccountNumber=?"
			+" where DATE_FORMAT(NOW(),'%Y-%m-%d') BETWEEN children.startDate and children.intentDate" 
			+" and children.surplusValueMoney>0 and children.childType='"+childType+"'"
			+" and plan.receiverP2PAccountNumber=?"
			+" GROUP BY plan.receiverP2PAccountNumber";*/
		String hql="select sum(availableMoney)"
			 +" from pl_mm_obligatoryright_children as children"
			 +" where DATE_FORMAT(NOW(),'%Y-%m-%d') BETWEEN children.startDate and children.intentDate"
			 +" and children.surplusValueMoney>0 and children.childType=?"
			 +" and children.receiverP2PAccountNumber=?"
			 +" GROUP BY children.receiverP2PAccountNumber";
		return (BigDecimal)this.getSession().createSQLQuery(hql).setParameter(0,childType).setParameter(1,account).uniqueResult();
	}
}