package com.zhiwei.credit.dao.creditFlow.creditAssignment.customer.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.GroupUtil;
import com.zhiwei.credit.dao.creditFlow.creditAssignment.customer.CsInvestmentpersonDao;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.CsInvestmentperson;
import com.zhiwei.credit.model.creditFlow.creditAssignment.customer.VInvestmentPerson;
import com.zhiwei.credit.service.system.AppUserService;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class CsInvestmentpersonDaoImpl extends BaseDaoImpl<CsInvestmentperson> implements CsInvestmentpersonDao{
	@Resource
	private AppUserService appUserService;
	public CsInvestmentpersonDaoImpl() {
		super(CsInvestmentperson.class);
	}

	@Override
	public List<CsInvestmentperson> getPersonAndBank() {
		String sql="SELECT c.investId investId, c.investName investName, c.cardnumber cardnumber,c.postaddress postaddress, ifnull(o.totalMoney,0) totalMoney   FROM ob_system_account as o RIGHT join cs_investmentperson as c on c.investId=o.investmentPersonId";
		List<Object[]> l=this.getSession().createSQLQuery(sql).list();
		 List<CsInvestmentperson> cl=new ArrayList<CsInvestmentperson>();
		 for(int i=0;i<l.size();i++){
			    Object[] o=(Object[]) l.get(i);
			    CsInvestmentperson cp=new CsInvestmentperson();
			    BigInteger b=(BigInteger) o[0];
			    cp.setInvestId(b.longValue());
			    cp.setInvestName((String) o[1]);
			    cp.setCardnumber((String) o[2]);
			    cp.setPostaddress((String)o[3]);
			    cp.setTotalMoney((BigDecimal) o[4]);
			    cl.add(cp);
			}
			return cl;
	}

	@Override
	public List<VInvestmentPerson> getList(HttpServletRequest request,String companyId,String userIds) {
		String hql="from VInvestmentPerson as p where 1=1";
		StringBuffer strBuffer = new StringBuffer(hql);
		
		if(null!=userIds && !"".equals(userIds)){
			strBuffer.append(" and  fn_check_repeat(p.belongedId,'"+userIds+"') = 1 ");
		}
		if(null!=companyId && !"".equals(companyId)){
			strBuffer.append(" and p.companyId in ("+companyId+") ");
		}
		try {
			String investName=java.net.URLDecoder.decode(request.getParameter("investName"),"UTF-8");
			if(null!=investName && !"".equals(investName)){
				strBuffer.append(" and p.investName like '%"+investName+"%'");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String sex=request.getParameter("sex");
		if(null!=sex && !sex.equals("")){
			strBuffer.append(" and p.sex like '%"+sex+"%'");
		}
		String cardnumber=request.getParameter("cardnumber");
		if(null!=cardnumber && !cardnumber.equals("")){
			strBuffer.append(" and p.cardnumber like '%"+cardnumber+"%'");
		}
		String cardtype=request.getParameter("cardtype");
		if(null!=cardtype && !cardtype.equals("")){
			strBuffer.append(" and p.cardtype like '%"+cardtype+"%'");
		}
		return getSession().createQuery(strBuffer.toString()).list();
	}

	@Override
	public List<CsInvestmentperson> ajaxQueryinvestmentPerson(Map<String,String> map,Integer start, Integer limit,String hql,String[] str, Object[] obj, String dir) {
		String companyId= map.get("companyId");
		String userIds= map.get("userId");
		String belongedId= map.get("belongedId");
		String shopId=map.get("shopId");
		StringBuffer sql =new StringBuffer("select " +
					"p.investId ," +
					"p.investName," +
					"p.cardnumber," +
					"p.cellphone," +
					"p.shopId," +
					"p.shopName," +
					"p.birthDay," +
					"p.changeCardStatus," +
					"p.contractStatus," +
					"p.companyId," +
					"p.sex as sex, " +
					"d15.itemValue AS sexvalue," +
					"p.cardtype  as cardtype , " +
					"d16.itemValue AS cardtypevalue," +
					"p.postaddress as postaddress," +
					"p.selfemail as selfemail, " +
					"p.postcode as postcode , " +
					"bank.accountnum as accountNumber," +
					"org.org_name as orgName, " +
					"p.belongedId, " +
					"fn_getDictName(p.belongedId) as belongedName " +
					"from cs_investmentperson AS p  " +
					" left join app_user as u on u.userId=p.belongedId " +
					"left join  dictionary d15 " +
					"ON ((d15.dicId = p.sex)) " +
					"left join dictionary d16 " +
					"ON ((d16.dicId = p.cardtype)) " +
					"left join cs_enterprise_bank as bank  " +
					"on(bank.id=p.investId and bank.isInvest=3) " +
					"left join organization as org " +
					"on (org.org_id=p.companyId)" +
					"where p.createrId is not null AND  p.belongedId is not null ");
		if(null!=belongedId && !"".equals(belongedId)){
			sql.append(" and  fn_check_repeat(p.belongedId,'"+belongedId+"') = 1 ");
		}
		if(null!=userIds && !"".equals(userIds)){
			sql.append(" and  fn_check_repeat(p.belongedId,'"+userIds+"') = 1 ");
		}
		if(null!=companyId && !"".equals(companyId)){
			sql.append(" and fn_check_repeat(p.companyId,'"+companyId+"') = 1 and p.companyId is not NULL ");
		}
		//按门店分离数据
		if(null !=shopId && !"".equals(shopId)){
			sql.append(" and p.shopId="+shopId+" or p.belongedShopId in("+shopId+")");
		}
		for(int j = 0 ; j < str.length ; j ++){
			for(int i = j ; i < obj.length ;){
				if(obj[i] == null){
					obj[i] = "";
				}
				sql.append(" and (p."+str[j]+" like "+"'"+'%'+obj[i]+'%'+"' or(p."+str[j]+" is null and '' = '"+obj[i]+"'))");
				break;
			}
		}
	//	System.out.println("-->"+sql.toString());
		List list=null;
		if(null==start ||null==limit){
			list=this.getSession().createSQLQuery(sql.toString())
				 .addScalar("investId", Hibernate.LONG)
			     .addScalar("investName", Hibernate.STRING)
			     .addScalar("cardnumber", Hibernate.STRING)
			     .addScalar("cellphone", Hibernate.STRING)
			     .addScalar("shopId", Hibernate.LONG)
			     .addScalar("shopName", Hibernate.STRING)
			     .addScalar("birthDay", Hibernate.STRING)
			     .addScalar("changeCardStatus", Hibernate.SHORT)
			     .addScalar("contractStatus", Hibernate.SHORT)
			     .addScalar("companyId", Hibernate.LONG)
			     .addScalar("sex", Hibernate.INTEGER)
			     .addScalar("sexvalue", Hibernate.STRING)
			     .addScalar("cardtype", Hibernate.INTEGER)
			     .addScalar("cardtypevalue", Hibernate.STRING)
			     .addScalar("postaddress", Hibernate.STRING)
			     .addScalar("selfemail", Hibernate.STRING)
			     .addScalar("postcode", Hibernate.STRING)
			     .addScalar("accountNumber", Hibernate.STRING)
			     .addScalar("orgName", Hibernate.STRING)
			     .addScalar("belongedId", Hibernate.STRING)
			     .addScalar("belongedName", Hibernate.STRING)
			     .setResultTransformer(Transformers.aliasToBean(CsInvestmentperson.class))
			     .list();
		}else{
			list=this.getSession().createSQLQuery(sql.toString())
			     .addScalar("investId", Hibernate.LONG)
			     .addScalar("investName", Hibernate.STRING)
			     .addScalar("cardnumber", Hibernate.STRING)
			     .addScalar("cellphone", Hibernate.STRING)
			     .addScalar("shopId", Hibernate.LONG)
			     .addScalar("shopName", Hibernate.STRING)
			     .addScalar("birthDay", Hibernate.STRING)
			     .addScalar("changeCardStatus", Hibernate.SHORT)
			     .addScalar("contractStatus", Hibernate.SHORT)
			     .addScalar("companyId", Hibernate.LONG)
			     .addScalar("sex", Hibernate.INTEGER)
			     .addScalar("sexvalue", Hibernate.STRING)
			     .addScalar("cardtype", Hibernate.INTEGER)
			     .addScalar("cardtypevalue", Hibernate.STRING)
			     .addScalar("postaddress", Hibernate.STRING)
			     .addScalar("selfemail", Hibernate.STRING)
			     .addScalar("postcode", Hibernate.STRING)
			     .addScalar("accountNumber", Hibernate.STRING)
			     .addScalar("orgName", Hibernate.STRING)
			     .addScalar("belongedId", Hibernate.STRING)
			     .addScalar("belongedName", Hibernate.STRING)
			     .setResultTransformer(Transformers.aliasToBean(CsInvestmentperson.class))
			     .setFirstResult(start).setMaxResults(limit)
			     .list();
		}
		return list;
	}

	@Override
	public List<CsInvestmentperson> getAllList(HttpServletRequest request,
			Integer start, Integer limit) {
		String sql ="select " +
		"p.investId ," + 
		"p.investName," +
		"p.cardnumber," +
		"p.cellphone," +
		"p.shopId," +
		"p.shopName," +
		"p.birthDay," +
		"p.changeCardStatus, " +
		"p.contractStatus, " +
		"p.companyId," +
		"p.belongedId as belongedId," +
		"fn_getDictName(p.belongedId) as belongedName," +
		"p.createrId as createrId," +
		"fn_getDictName(p.createrId) as creator," +
		"p.sex as sex, " +
		"d15.itemValue AS sexvalue," +
		"p.cardtype  as cardtype , " +
		"d16.itemValue AS cardtypevalue," +
		"p.postaddress as postaddress," +
		"p.selfemail as selfemail, " +
		"p.postcode as postcode , " +
		"bank.accountnum as accountNumber," +
		"org.org_name as orgName " +
		"from cs_investmentperson AS p  " +
		"left join  dictionary d15 " +
		"ON ((d15.dicId = p.sex)) " +
		"left join dictionary d16 " +
		"ON ((d16.dicId = p.cardtype)) " +
		"left join cs_enterprise_bank as bank  " +
		"on(bank.id=p.investId and bank.isInvest=3) " +
		"left join organization as org " +
		"on (org.org_id=p.companyId)" +
		"where p.createrId is not null AND  p.belongedId is not null ";

		
		if(request!=null){
			Object ids=request.getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(request,ids);
			String companyId= map.get("companyId");
			String userIds= map.get("userId");
			String shopId = map.get("shopId");
			
			if(null!=userIds && !"".equals(userIds)){
				sql=sql+" and  fn_check_repeat(p.belongedId,'"+userIds+"') = 1 ";
			}
			
			//按门店分离数据
			if(null !=shopId && !"".equals(shopId)){
				sql=sql+" and p.shopId="+shopId+" or p.belongedShopId in("+shopId+")";
			}
			
			if(null!=companyId &&!"".equals(companyId)){
				sql=sql+" and p.companyId in("+companyId+") ";
			}
			
			String investName =request.getParameter("investName");
			if(investName!=null&&!"".equals(investName)){
				sql=sql+" and p.investName like '%"+investName+"%'";
			}
			String cardtype =request.getParameter("cardtype");
			if(cardtype!=null&&!"".equals(cardtype)){
				sql=sql+" and p.cardtype="+Integer.parseInt(cardtype);
			}
			String cellphone=request.getParameter("cellphone");
			if(cellphone!=null&&!"".equals(cellphone)){
				sql=sql+" and p.cellphone like '%"+cellphone+"%'";
			}
			String sex=request.getParameter("sex");
			if(sex!=null&&!"".equals(sex)){
				sql=sql+" and p.sex="+Integer.parseInt(sex);
			}
			String belongedId=request.getParameter("belongedId");
			if(belongedId!=null&&!"".equals(belongedId)){
				sql=sql+" and  fn_check_repeat(p.belongedId,'"+belongedId+"') = 1 ";
			}
			String Q_intentDate_D_GE=request.getParameter("Q_intentDate_D_GE");
			if(Q_intentDate_D_GE!=null&&!"".equals(Q_intentDate_D_GE)){
				sql=sql+" and  p.birthDay>='"+Q_intentDate_D_GE+"'";
			}
			String Q_intentDate_D_LE=request.getParameter("Q_intentDate_D_LE");
			if(Q_intentDate_D_LE!=null&&!"".equals(Q_intentDate_D_LE)){
				sql=sql+" and  p.birthDay<='"+Q_intentDate_D_LE+"'";
			}
	   }
		List list=null;
		if(null==start ||null==limit){
		list=this.getSession().createSQLQuery(sql)
			 .addScalar("investId", Hibernate.LONG)
		     .addScalar("investName", Hibernate.STRING)
		     .addScalar("cardnumber", Hibernate.STRING)
		     .addScalar("cellphone", Hibernate.STRING)
		     .addScalar("shopId", Hibernate.LONG)
		     .addScalar("shopName", Hibernate.STRING)
		     .addScalar("birthDay", Hibernate.STRING)
		     .addScalar("changeCardStatus", Hibernate.SHORT)
		     .addScalar("contractStatus", Hibernate.SHORT)
		     .addScalar("companyId", Hibernate.LONG)
		     .addScalar("belongedId", Hibernate.STRING)
		     .addScalar("belongedName", Hibernate.STRING)
		     .addScalar("createrId", Hibernate.LONG)
		     .addScalar("creator", Hibernate.STRING)
		     .addScalar("sex", Hibernate.INTEGER)
		     .addScalar("sexvalue", Hibernate.STRING)
		     .addScalar("cardtype", Hibernate.INTEGER)
		     .addScalar("cardtypevalue", Hibernate.STRING)
		     .addScalar("postaddress", Hibernate.STRING)
		     .addScalar("selfemail", Hibernate.STRING)
		     .addScalar("postcode", Hibernate.STRING)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("orgName", Hibernate.STRING)
		     .setResultTransformer(Transformers.aliasToBean(CsInvestmentperson.class))
		     .list();
		}else{
		list=this.getSession().createSQLQuery(sql)
		     .addScalar("investId", Hibernate.LONG)
		     .addScalar("investName", Hibernate.STRING)
		     .addScalar("cardnumber", Hibernate.STRING)
		     .addScalar("cellphone", Hibernate.STRING)
		     .addScalar("shopId", Hibernate.LONG)
		     .addScalar("shopName", Hibernate.STRING)
		     .addScalar("birthDay", Hibernate.STRING)
		     .addScalar("changeCardStatus", Hibernate.SHORT)
		     .addScalar("contractStatus", Hibernate.SHORT)
		     .addScalar("companyId", Hibernate.LONG)
		     .addScalar("belongedId", Hibernate.STRING)
		     .addScalar("belongedName", Hibernate.STRING)
		     .addScalar("createrId", Hibernate.LONG)
		     .addScalar("creator", Hibernate.STRING)
		     .addScalar("sex", Hibernate.INTEGER)
		     .addScalar("sexvalue", Hibernate.STRING)
		     .addScalar("cardtype", Hibernate.INTEGER)
		     .addScalar("cardtypevalue", Hibernate.STRING)
		     .addScalar("postaddress", Hibernate.STRING)
		     .addScalar("selfemail", Hibernate.STRING)
		     .addScalar("postcode", Hibernate.STRING)
		     .addScalar("accountNumber", Hibernate.STRING)
		     .addScalar("orgName", Hibernate.STRING)
		     .setResultTransformer(Transformers.aliasToBean(CsInvestmentperson.class))
		     .setFirstResult(start).setMaxResults(limit)
		     .list();
		}
		return list;
	}
	/**
	 * 2014-08-01 孙贝贝
	 * 查询个人投资客户
	 */
	@Override
	public List<CsInvestmentperson> listInvest(Integer start, Integer limit,
			HttpServletRequest request) {
		String sql="select"+
		" p.investId,"+
		" p.investName,"+
		"m.loginname as p2pName,"+
		"m.truename as p2pTrueName,"+
		"p.cardtype,"+
		"p.cardnumber,"+
		"m.cardcode as p2pcardnumber,"+
		"p.cellphone,"+
		"m.telphone as p2pcellphone,"+
		"p.selfemail,"+
		"m.email as p2pemail,"+
		"d16.itemValue as cardtypevalue"+
		" from cs_investmentperson p " +
		"left join bp_cust_relation b ON (b.offlineCustType = 'p_invest' AND  b.offlineCusId = p.investId) "+
		"left join bp_cust_member m on b.p2pCustId=m.id"+
		" left join dictionary d16 ON d16.dicId = p.cardtype where 1=1";
		if(null !=request){
			Object ids=request.getSession().getAttribute("userIds");
			Map<String,String> map=GroupUtil.separateFactor(request,ids);
			String companyId= map.get("companyId");
			String userIds= map.get("userId");
			String shopId = map.get("shopId");
			
			if(null!=userIds && !"".equals(userIds)){
				sql=sql+" and  fn_check_repeat(p.belongedId,'"+userIds+"') = 1 ";
			}
			
			//按门店分离数据
			if(null !=shopId && !"".equals(shopId)){
				sql=sql+" and p.shopId="+shopId+" or p.belongedShopId in("+shopId+")";
			}
			
			if(null!=companyId &&!"".equals(companyId)){
				sql=sql+" and p.companyId in("+companyId+") ";
			}
			
			String investName =request.getParameter("investName");
			if(investName!=null&&!"".equals(investName)){
				sql=sql+" and p.investName like '%"+investName+"%'";
			}
			String cardtype =request.getParameter("cardtype");
			if(cardtype!=null&&!"".equals(cardtype)){
				sql=sql+" and p.cardtype="+Integer.parseInt(cardtype);
			}
			String cardnumber=request.getParameter("cardnumber");
			if(cardnumber!=null&&!"".equals(cardnumber)){
				sql=sql+" and p.cardnumber like '%"+cardnumber+"%'";
			}
			String sex=request.getParameter("sex");
			if(sex!=null&&!"".equals(sex)){
				sql=sql+" and p.sex="+Integer.parseInt(sex);
			}
		}
		List list=null;
		if(start!=null&&limit!=null){
			list=this.getSession().createSQLQuery(sql).
			 addScalar("investId",Hibernate.LONG).//主键id
			 addScalar("investName",Hibernate.STRING).//erp姓名
			 addScalar("p2pName",Hibernate.STRING).//p2p账号
			 addScalar("p2pTrueName",Hibernate.STRING).//p2p真实姓名
			 addScalar("cardtypevalue",Hibernate.STRING).//证件类型
			 addScalar("cardnumber",Hibernate.STRING).//erp证件号码
			 addScalar("p2pcardnumber",Hibernate.STRING).//p2p证件号码
			 addScalar("cellphone",Hibernate.STRING).//erp手机号码
			 addScalar("p2pcellphone",Hibernate.STRING).//p2p手机号码
			 addScalar("selfemail",Hibernate.STRING).//erp邮箱
			 addScalar("p2pemail",Hibernate.STRING).//p2p邮箱
			 setResultTransformer(Transformers.aliasToBean(CsInvestmentperson.class)).
			 setFirstResult(start).setMaxResults(limit).
			 list();
		}else{
			list=this.getSession().createSQLQuery(sql).
			addScalar("investId",Hibernate.LONG).//主键id
			 addScalar("investName",Hibernate.STRING).//erp姓名
			 addScalar("p2pName",Hibernate.STRING).//p2p账号
			 addScalar("p2pTrueName",Hibernate.STRING).//p2p真实姓名
			 addScalar("cardtypevalue",Hibernate.STRING).//证件类型
			 addScalar("cardnumber",Hibernate.STRING).//erp证件号码
			 addScalar("p2pcardnumber",Hibernate.STRING).//p2p证件号码
			 addScalar("cellphone",Hibernate.STRING).//erp手机号码
			 addScalar("p2pcellphone",Hibernate.STRING).//p2p手机号码
			 addScalar("selfemail",Hibernate.STRING).//erp邮箱
			 addScalar("p2pemail",Hibernate.STRING).//p2p邮箱
			 setResultTransformer(Transformers.aliasToBean(CsInvestmentperson.class)).
			 list();
		}
		return list;
	}

	@Override
	public List<CsInvestmentperson> getDown(Map<String, String> map,
			Integer start, Integer limit) {
		StringBuffer sb=new StringBuffer("SELECT * from cs_investmentperson as p where 1=1");
		String companyId= map.get("companyId");
		String userIds= map.get("userId");
		String shopId = map.get("shopId");
		
		if(null!=userIds && !"".equals(userIds)){
			sb.append(" and  fn_check_repeat(p.belongedId,'"+userIds+"') = 1 ");
		}
		
		//按门店分离数据
		if(null !=shopId && !"".equals(shopId)){
			sb.append(" and p.shopId="+shopId+" or p.belongedShopId in("+shopId+")");
		}
		
		if(null!=companyId &&!"".equals(companyId)){
			sb.append(" and p.companyId in("+companyId+") ");
		}
		String investName=map.get("investName");
		if(null !=investName && !"".equals(investName)){
			sb.append(" and p.investName like '%"+investName+"%'");
		}
		List list=null;
		if(null ==start ||limit==null){
			list=this.getSession().createSQLQuery(sb.toString()).addEntity(CsInvestmentperson.class).list();
		}else{
			list=this.getSession().createSQLQuery(sb.toString()).addEntity(CsInvestmentperson.class).setFirstResult(start).setMaxResults(limit).list();
		}
		return list;
	}

}