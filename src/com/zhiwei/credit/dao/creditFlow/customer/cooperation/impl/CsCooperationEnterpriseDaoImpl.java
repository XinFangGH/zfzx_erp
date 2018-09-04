package com.zhiwei.credit.dao.creditFlow.customer.cooperation.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.creditFlow.customer.cooperation.CsCooperationEnterpriseDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObAccountDealInfo;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsCooperationEnterpriseDaoImpl extends BaseDaoImpl<CsCooperationEnterprise> implements CsCooperationEnterpriseDao{

	public CsCooperationEnterpriseDaoImpl() {
		super(CsCooperationEnterprise.class);
	}

	@Override
	public List<CsCooperationEnterprise> getAllAccountList(Map map,PagingBean pb) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"e.id as id,"+
						"e.name as name,"+
						"e.type as type ,"+
						"e.organizationNumber as organizationNumber,"+
						"e.licenseNumber as licenseNumber,"+
						"e.tellPhone as tellPhone, "+
						"o.id as accountId, "+
						"o.accountName as accountName, "+
						"o.accountNumber as accountNumber, "+
						"o.investmentPersonId as investmentPersonId, "+
						"o.investPersionType as investPersionType, "+
						"o.totalMoney  as totalMoney  "+
					"FROM  "+
						"cs_cooperation_enterprise AS e ";
				//	"LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'b_cooperation' AND  p.offlineCusId = e.id) "+
				//	"left join  ob_system_account as o on (p.p2pCustId=o.investmentPersonId and o.investPersionType=0)  where 1=1 ";
		if(!map.isEmpty()){
			Object type=map.get("type");
			if(type!=null&&!"".equals(type.toString())){//查询出企业合作机构的各种类型
				if(type.equals("lenders")){//债权客户
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'b_cooperation' AND  p.offlineCusId = e.id) "+
					"left join  ob_system_account as o on (p.p2pCustId=o.investmentPersonId and o.investPersionType=0)  where 1=1 ";
				}else{//担保机构客户
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'b_guarantee' AND  p.offlineCusId = e.id) "+
					"left join  ob_system_account as o on (p.p2pCustId=o.investmentPersonId and o.investPersionType=0)  where 1=1 ";
				}
				sql=sql +" and e.type='"+type.toString()+"'";
			}
			
			Object accountNumber=map.get("accountNumber");
			if(accountNumber!=null&&!"".equals(accountNumber.toString())){//查询出企业合作机构的各种类型
				sql=sql +" and bp.accountNumber  like '%"+accountNumber.toString()+"%'";
			}
			
			Object name=map.get("name");
			if(name!=null&&!"".equals(name.toString())){//查询登录
				sql=sql +" and e.name  like '%"+name.toString()+"%'";
			}
			
			Object organizationNumber=map.get("organizationNumber");
			if(organizationNumber!=null&&!"".equals(organizationNumber.toString())){//查询出企业组织机构
				sql=sql +" and e.organizationNumber  like '%"+organizationNumber.toString()+"%'";
			}
			
		}
		System.out.println(sql);
		List<CsCooperationEnterprise>  listCount=this.getSession().createSQLQuery(sql).
												addScalar("id",Hibernate.LONG).                                                                         
												addScalar("name",Hibernate.STRING).
												addScalar("type",Hibernate.STRING).
												addScalar("organizationNumber",Hibernate.STRING).
												addScalar("licenseNumber",Hibernate.STRING).
												addScalar("tellPhone",Hibernate.STRING).
												addScalar("accountId",Hibernate.LONG).
												addScalar("accountName",Hibernate.STRING).
												addScalar("accountNumber",Hibernate.STRING).
												addScalar("investmentPersonId",Hibernate.LONG).
												addScalar("investPersionType",Hibernate.SHORT).
												addScalar("totalMoney", Hibernate.BIG_DECIMAL). 
												setResultTransformer(Transformers.aliasToBean(CsCooperationEnterprise.class)).
												list();
		
		if(pb!=null){
			pb.setTotalItems(listCount.size());
			if(pb.getStart()!=null&&pb.getPageSize()!=null){
				List<CsCooperationEnterprise>  list=this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).                                                                         
				addScalar("name",Hibernate.STRING).
				addScalar("type",Hibernate.STRING).
				addScalar("organizationNumber",Hibernate.STRING).
				addScalar("licenseNumber",Hibernate.STRING).
				addScalar("tellPhone",Hibernate.STRING).
				addScalar("accountId",Hibernate.LONG).
				addScalar("accountName",Hibernate.STRING).
				addScalar("accountNumber",Hibernate.STRING).
				addScalar("investmentPersonId",Hibernate.LONG).
				addScalar("investPersionType",Hibernate.SHORT).
				addScalar("totalMoney", Hibernate.BIG_DECIMAL). 
				setResultTransformer(Transformers.aliasToBean(CsCooperationEnterprise.class)).
			    setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).
				list();
				return list;
			}else{
				return listCount;
			}
		}else{
			return listCount;
		}
		
	}

	@Override
	public List<CsCooperationEnterprise> accountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"e.id as id,"+
						"e.name as name,"+
						"e.type as type ,"+
						"e.organizationNumber as organizationNumber,"+
						"e.licenseNumber as licenseNumber,"+
						"e.pphone as pphone, "+
						"bp.id as p2pid,  "+
						"bp.loginname as p2ploginname,  "+
						"bp.truename as p2ptruename, "+
						"bp.cardcode as p2pcardcode,  "+
						"bp.thirdPayFlagId  as thirdPayFlagId, "+
						"bp.telphone as p2ptelphone "+
					"FROM  "+
						"cs_cooperation_enterprise AS e ";
					//"LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'b_cooperation' AND  p.offlineCusId = e.id) "+
					//"left join bp_cust_member as bp on (p.p2pCustId=bp.id )  where 1=1 ";
		if(!map.isEmpty()){
			Object type=map.get("type");
			if(type!=null&&!"".equals(type.toString())){//查询出企业合作机构的各种类型
				if(type.equals("lenders")){//债权客户
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'b_cooperation' AND  p.offlineCusId = e.id) "+
					"left join bp_cust_member as bp on (p.p2pCustId=bp.id )  where 1=1 ";
				}else{//担保机构客户
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'b_guarantee' AND  p.offlineCusId = e.id) "+
					"left join bp_cust_member as bp on (p.p2pCustId=bp.id )  where 1=1 ";
				}
				sql=sql +" and e.type='"+type.toString()+"'";
			}
			
			Object p2ploginname=map.get("p2ploginname");
			if(p2ploginname!=null&&!"".equals(p2ploginname.toString())){//查询出企业合作机构的各种类型
				sql=sql +" and bp.loginname  like '%"+p2ploginname.toString()+"%'";
			}
			
			Object name=map.get("name");
			if(name!=null&&!"".equals(name.toString())){//查询登录
				sql=sql +" and e.name  like '%"+name.toString()+"%'";
			}
			
			Object organizationNumber=map.get("organizationNumber");
			if(organizationNumber!=null&&!"".equals(organizationNumber.toString())){//查询出企业组织机构
				sql=sql +" and e.organizationNumber  like '%"+organizationNumber.toString()+"%'";
			}
			
		}
		List<CsCooperationEnterprise>  listCount=this.getSession().createSQLQuery(sql).
												addScalar("id",Hibernate.LONG).                                                                         
												addScalar("name",Hibernate.STRING).
												addScalar("type",Hibernate.STRING).
												addScalar("organizationNumber",Hibernate.STRING).
												addScalar("licenseNumber",Hibernate.STRING).
												addScalar("pphone",Hibernate.STRING).
												addScalar("p2pid",Hibernate.STRING).
												addScalar("p2ploginname",Hibernate.STRING).
												addScalar("p2ptruename",Hibernate.STRING).
												addScalar("p2pcardcode",Hibernate.STRING).
												addScalar("thirdPayFlagId",Hibernate.STRING).
												addScalar("p2ptelphone", Hibernate.STRING). 
												setResultTransformer(Transformers.aliasToBean(CsCooperationEnterprise.class)).
												list();
		
		if(pb!=null){
			pb.setTotalItems(listCount.size());
			if(pb.getStart()!=null&&pb.getPageSize()!=null){
				List<CsCooperationEnterprise>  list=this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).                                                                         
				addScalar("name",Hibernate.STRING).
				addScalar("type",Hibernate.STRING).
				addScalar("organizationNumber",Hibernate.STRING).
				addScalar("licenseNumber",Hibernate.STRING).
				addScalar("pphone",Hibernate.STRING).
				addScalar("p2pid",Hibernate.STRING).
				addScalar("p2ploginname",Hibernate.STRING).
				addScalar("p2ptruename",Hibernate.STRING).
				addScalar("p2pcardcode",Hibernate.STRING).
				addScalar("thirdPayFlagId",Hibernate.STRING).
				addScalar("p2ptelphone", Hibernate.STRING). 
				setResultTransformer(Transformers.aliasToBean(CsCooperationEnterprise.class)).
			    setFirstResult(pb.getStart()).setMaxResults(pb.getPageSize()).
				list();
				return list;
			}else{
				return listCount;
			}
		}else{
			return listCount;
		}
		
	
	}

}