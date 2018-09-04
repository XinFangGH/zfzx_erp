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
import com.zhiwei.credit.dao.creditFlow.customer.cooperation.CsCooperationPersonDao;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsCooperationPersonDaoImpl extends BaseDaoImpl<CsCooperationPerson> implements CsCooperationPersonDao{

	public CsCooperationPersonDaoImpl() {
		super(CsCooperationPerson.class);
	}

	@Override
	public CsCooperationPerson queryByCardnumber(String cardNumber) {
		// TODO Auto-generated method stub
		String hql = "from CsCooperationPerson AS p where p.cardNumber=? " ;
		List list = findByHql(hql,new Object[]{cardNumber} ) ;
		if(list == null || list.size()==0 ){
			return null;
		}else
		    return (CsCooperationPerson)list.get(0);
	}

	@Override
	public List<CsCooperationPerson> getAllAccountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"cp.id as id, "+
						"cp.name as name, "+
						"cp.type as type , "+
						"cp.email as email, "+
						"cp.cardNumber as cardNumber, "+
						"cp.phoneNumber as phoneNumber, "+
						"o.id as accountId, "+
						"o.accountName as accountName, "+
						"o.accountNumber as accountNumber, "+
						"o.investmentPersonId as investmentPersonId, "+
						"o.investPersionType as investPersionType, "+
						"o.totalMoney  as totalMoney  "+
					"FROM  "+  
						"cs_cooperation_person AS cp  ";
			//		"LEFT JOIN bp_cust_relation AS p ON ( p.offlineCustType = 'p_cooperation' AND p.offlineCusId = cp.id)   "+
			//		"left join  ob_system_account as o on (p.p2pCustId=o.investmentPersonId and o.investPersionType=0) where 1=1 ";
		
		
		if(!map.isEmpty()){
			Object type=map.get("type");
			if(type!=null&&!"".equals(type.toString())){//查询出企业合作机构的各种类型
				//个人理财顾问已经合并到个人债权客户，需要维护之前的旧数据
				if(type.equals("lenders")){//个人债权客户
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON ( p.offlineCustType = 'p_cooperation' AND p.offlineCusId = cp.id)   "+
					"left join  ob_system_account as o on (p.p2pCustId=o.investmentPersonId and o.investPersionType=0) where 1=1 ";
				}else{//个人理财顾问
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON ( p.offlineCustType = 'p_financial' AND p.offlineCusId = cp.id)   "+
					"left join  ob_system_account as o on (p.p2pCustId=o.investmentPersonId and o.investPersionType=0) where 1=1 ";
				}
				sql=sql +" and (cp.type='"+type.toString()+"' or  cp.type='financial') ";
			}
			Object accountNumber=map.get("accountNumber");
			if(accountNumber!=null&&!"".equals(accountNumber.toString())){//查询出企业合作机构的各种类型
				sql=sql +" and o.accountNumber like '%"+accountNumber.toString()+"%'";
			}
			
			Object name=map.get("name");
			if(name!=null&&!"".equals(name.toString())){//客户姓名
				sql=sql +" and cp.name like '%"+name.toString()+"%'";
			}
			
			Object cardNumber=map.get("cardNumber");
			if(cardNumber!=null&&!"".equals(cardNumber.toString())){//客户证件号码
				sql=sql +" and cp.cardNumber like '%"+cardNumber.toString()+"%'";
			}
			
		}
		List<CsCooperationPerson> listCount=this.getSession().createSQLQuery(sql).
												addScalar("id",Hibernate.LONG).                                                                         
												addScalar("name",Hibernate.STRING).
												addScalar("type",Hibernate.STRING).
												addScalar("email",Hibernate.STRING).
												addScalar("cardNumber",Hibernate.STRING).
												addScalar("phoneNumber",Hibernate.STRING).
												addScalar("accountId",Hibernate.LONG).
												addScalar("accountName",Hibernate.STRING).
												addScalar("accountNumber",Hibernate.STRING).
												addScalar("investmentPersonId",Hibernate.LONG).
												addScalar("investPersionType",Hibernate.SHORT).
												addScalar("totalMoney", Hibernate.BIG_DECIMAL). 
												setResultTransformer(Transformers.aliasToBean(CsCooperationPerson.class)).
												list();
		
		if(pb!=null){
			pb.setTotalItems(listCount.size());
			if(pb.getStart()!=null&&pb.getPageSize()!=null){
				List<CsCooperationPerson>  list=this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).                                                                         
				addScalar("name",Hibernate.STRING).
				addScalar("type",Hibernate.STRING).
				addScalar("email",Hibernate.STRING).
				addScalar("cardNumber",Hibernate.STRING).
				addScalar("phoneNumber",Hibernate.STRING).
				addScalar("accountId",Hibernate.LONG).
				addScalar("accountName",Hibernate.STRING).
				addScalar("accountNumber",Hibernate.STRING).
				addScalar("investmentPersonId",Hibernate.LONG).
				addScalar("investPersionType",Hibernate.SHORT).
				addScalar("totalMoney", Hibernate.BIG_DECIMAL). 
				setResultTransformer(Transformers.aliasToBean(CsCooperationPerson.class)).
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
	public List<CsCooperationPerson> accountList(Map map, PagingBean pb) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"cp.id as id, "+
						"cp.name as name, "+
						"cp.type as type , "+
						"cp.email as email, "+
						"cp.cardNumber as cardNumber, "+
						"cp.phoneNumber as phoneNumber, "+
						"bp.id as p2pid,  "+
						"bp.loginname as p2ploginname,  "+
						"bp.truename as p2ptruename, "+
						"bp.cardcode as p2pcardcode,  "+
						"bp.thirdPayFlagId  as thirdPayFlagId, "+
						"bp.telphone as p2ptelphone "+
					"FROM  "+
						"cs_cooperation_person AS cp ";
				//	"LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'p_cooperation' AND  p.offlineCusId = cp.id) "+
				//	"left join bp_cust_member as bp on (p.p2pCustId=bp.id )  where 1=1 ";
		if(!map.isEmpty()){
			Object type=map.get("type");
			if(type!=null&&!"".equals(type.toString())){//查询出企业合作机构的各种类型
				//理财顾问已经合并到个人债权客户中，要维护之前的旧数据
				if(type.equals("lenders")){//个人债权客户
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'p_cooperation' AND  p.offlineCusId = cp.id) "+
					"left join bp_cust_member as bp on (p.p2pCustId=bp.id )  where 1=1 ";
				}else{//个人理财顾问
					sql = sql + "LEFT JOIN bp_cust_relation AS p ON (p.offlineCustType = 'p_financial' AND  p.offlineCusId = cp.id) "+
					"left join bp_cust_member as bp on (p.p2pCustId=bp.id )  where 1=1 ";
				}
				sql=sql +" and (cp.type='"+type.toString()+"' or cp.type='financial') ";
			}
			Object p2ploginname=map.get("p2ploginname");
			if(p2ploginname!=null&&!"".equals(p2ploginname.toString())){//网站登录账户
				sql=sql +" and bp.loginname  like '%"+p2ploginname.toString()+"%'";
			}
			
			Object name=map.get("name");
			if(name!=null&&!"".equals(name.toString())){//客户姓名
				sql=sql +" and cp.name like '%"+name.toString()+"%'";
			}
			
			Object cardNumber=map.get("cardNumber");
			if(cardNumber!=null&&!"".equals(cardNumber.toString())){//客户证件号码
				sql=sql +" and cp.cardNumber like '%"+cardNumber.toString()+"%'";
			}
		}
		System.out.println("sql="+sql);
		List<CsCooperationPerson>  listCount=this.getSession().createSQLQuery(sql).
												addScalar("id",Hibernate.LONG).                                                                         
												addScalar("name",Hibernate.STRING).
												addScalar("type",Hibernate.STRING).
												addScalar("email",Hibernate.STRING).
												addScalar("cardNumber",Hibernate.STRING).
												addScalar("phoneNumber",Hibernate.STRING).
												addScalar("p2pid",Hibernate.STRING).
												addScalar("p2ploginname",Hibernate.STRING).
												addScalar("p2ptruename",Hibernate.STRING).
												addScalar("p2pcardcode",Hibernate.STRING).
												addScalar("thirdPayFlagId",Hibernate.STRING).
												addScalar("p2ptelphone", Hibernate.STRING). 
												setResultTransformer(Transformers.aliasToBean(CsCooperationPerson.class)).
												list();
		
		if(pb!=null){
			pb.setTotalItems(listCount.size());
			if(pb.getStart()!=null&&pb.getPageSize()!=null){
				List<CsCooperationPerson>  list=this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).                                                                         
				addScalar("name",Hibernate.STRING).
				addScalar("type",Hibernate.STRING).
				addScalar("email",Hibernate.STRING).
				addScalar("cardNumber",Hibernate.STRING).
				addScalar("phoneNumber",Hibernate.STRING).
				addScalar("p2pid",Hibernate.STRING).
				addScalar("p2ploginname",Hibernate.STRING).
				addScalar("p2ptruename",Hibernate.STRING).
				addScalar("p2pcardcode",Hibernate.STRING).
				addScalar("thirdPayFlagId",Hibernate.STRING).
				addScalar("p2ptelphone", Hibernate.STRING). 
				setResultTransformer(Transformers.aliasToBean(CsCooperationPerson.class)).
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