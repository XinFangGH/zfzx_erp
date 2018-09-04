package com.zhiwei.credit.dao.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductUserAccountInfoDao;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUserAccountInfo;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductUseraccount;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlFinanceProductUserAccountInfoDaoImpl extends BaseDaoImpl<PlFinanceProductUserAccountInfo> implements PlFinanceProductUserAccountInfoDao{

	public PlFinanceProductUserAccountInfoDaoImpl() {
		super(PlFinanceProductUserAccountInfo.class);
	}

	@Override
	public List<PlFinanceProductUserAccountInfo> getListByParamet(
			HttpServletRequest request, PagingBean pb) {
		// TODO Auto-generated method stub
		String sql=" SELECT "+
						"pinfo.id AS id, "+
						"p.id as accountId, "+
						"pinfo.userId AS userId, "+
						"p.userName AS userName, "+
						"p.userloginName AS userloginName, "+
						"pinfo.productId AS productId, "+
						"pfp.productName AS productName, "+
						"pinfo.dealDate AS dealDate, "+
						"pinfo.amont as amont, "+
						"pinfo.currentMoney as currentMoney, "+
						"pinfo.dealtype as dealtype, "+
						"pinfo.dealtypeName as dealtypeName, "+
						"pinfo.dealStatus as dealStatus, "+
						"pinfo.dealStatusName as dealStatusName, "+
						"pinfo.remark as remark, "+
						"pinfo.requestNo as requestNo, "+
						"pinfo.plateFeeRate as plateFeeRate, "+
						"pinfo.plateFee as plateFee, "+
						"pinfo.companyId as companyId "+
					"FROM "+
						"pl_finance_product_useraccountinfo AS pinfo "+
					"left join pl_finance_product as pfp on(pinfo.productId=pfp.id) "+
					"LEFT JOIN `pl_finance_product_useraccount` AS p ON (p.userId = pinfo.userId and p.productId=pinfo.productId )  where pinfo.dealStatus is not null ";
		if(request!=null){
			//用来查询交易类型
			String dealtype=(String) (request.getParameter("dealtype")!=null?request.getParameter("dealtype"):request.getAttribute("dealtype"));
			if(dealtype!=null&&!"".equals(dealtype)){
				sql=sql+" and pinfo.dealtype='"+dealtype+"'";
			}
			String dealStatus=(String) (request.getParameter("dealStatus")!=null?request.getParameter("dealStatus"):request.getAttribute("dealStatus"));
			//用来查询交易状态
			if(dealStatus!=null&&!"".equals(dealStatus)){
				sql=sql+" and pinfo.dealStatus="+Short.valueOf(dealStatus);
			}
			//查询交易开始日期
			String dealDateS=request.getParameter("dealDateS");
	    	if(dealDateS!=null&&!"".equals(dealDateS)){
	    		sql=sql+" and pinfo.dealDate>='"+dealDateS+" 00:00:00' ";
	    	}
	    	//查询交易开结束日期
	    	String dealDateE=request.getParameter("dealDateE");
	    	if(dealDateE!=null&&!"".equals(dealDateE)){
	    		sql=sql+" and pinfo.dealDate<='"+dealDateE+" 23:59:59' ";
	    	}
	    	//查询交易用户姓名
	    	String userName=request.getParameter("userName");
	    	if(userName!=null&&!"".equals(userName)){
	    		sql=sql+" and p.userName like \"%"+userName+"%\" ";
	    	}
	    	//查询交易用户登陆名
	    	String loginName=request.getParameter("loginName");
	    	if(loginName!=null&&!"".equals(loginName)){
	    		sql=sql+" and p.userloginName like \"%"+loginName+"%\" ";
	    	}
		}
		sql=sql+" ORDER BY pinfo.dealDate DESC";
		
		System.out.println(sql);
	     List<PlFinanceProductUserAccountInfo> list=this.getSession().createSQLQuery(sql).
	     										addScalar("id",Hibernate.LONG).
	     										addScalar("accountId",Hibernate.LONG).
	     										addScalar("userId", Hibernate.LONG).
	     										addScalar("userName", Hibernate.STRING).
	     										addScalar("userloginName", Hibernate.STRING).
	     										addScalar("productId", Hibernate.LONG).
	     										addScalar("productName", Hibernate.STRING).
	     										addScalar("dealDate", Hibernate.TIMESTAMP).
	     										addScalar("amont", Hibernate.BIG_DECIMAL).
	     										addScalar("currentMoney", Hibernate.BIG_DECIMAL).
	     										addScalar("dealtype", Hibernate.STRING).
	     										addScalar("dealtypeName", Hibernate.STRING).
	     										addScalar("dealStatus", Hibernate.SHORT).
	     										addScalar("dealStatusName", Hibernate.STRING).
	     										addScalar("remark", Hibernate.STRING).
	     										addScalar("requestNo", Hibernate.STRING).
	     										addScalar("plateFeeRate", Hibernate.BIG_DECIMAL).
	     										addScalar("plateFee", Hibernate.BIG_DECIMAL).
	     										addScalar("companyId", Hibernate.LONG).
	     										setResultTransformer(Transformers.aliasToBean(PlFinanceProductUserAccountInfo.class)).
	     									    list();
	     if(pb!=null&&pb.getStart()!=null&&pb.getPageSize()!=null){
	    	 pb.setTotalItems(list!=null?list.size():0);
	    	 List<PlFinanceProductUserAccountInfo> listP=this.getSession().createSQLQuery(sql).
			    	 	addScalar("id",Hibernate.LONG).
						addScalar("accountId",Hibernate.LONG).
						addScalar("userId", Hibernate.LONG).
						addScalar("userName", Hibernate.STRING).
						addScalar("userloginName", Hibernate.STRING).
						addScalar("productId", Hibernate.LONG).
						addScalar("productName", Hibernate.STRING).
						addScalar("dealDate", Hibernate.TIMESTAMP).
						addScalar("amont", Hibernate.BIG_DECIMAL).
						addScalar("currentMoney", Hibernate.BIG_DECIMAL).
						addScalar("dealtype", Hibernate.STRING).
						addScalar("dealtypeName", Hibernate.STRING).
						addScalar("dealStatus", Hibernate.SHORT).
						addScalar("dealStatusName", Hibernate.STRING).
						addScalar("remark", Hibernate.STRING).
						addScalar("requestNo", Hibernate.STRING).
						addScalar("plateFeeRate", Hibernate.BIG_DECIMAL).
						addScalar("plateFee", Hibernate.BIG_DECIMAL).
						addScalar("companyId", Hibernate.LONG).
						setResultTransformer(Transformers.aliasToBean(PlFinanceProductUserAccountInfo.class)).
						setFirstResult(pb.getStart()).
						setMaxResults(pb.getPageSize()).
						list();
	    	 return listP; 
	     }else{
	    	 return list; 
	     }
	}

	@Override
	public void updateAccountInfo() {
		try{
			String firstDay=DateUtil.getFirstDate(new Date(), "first");
			String endDay=DateUtil.getFirstDate(new Date(), "end");
			String sql="SELECT  p.id  FROM  pl_finance_product_useraccountinfo AS p LEFT JOIN pl_finance_product AS pfp ON (p.productId = pfp.id) WHERE 	p.dealtype = 1"+
						" AND p.dealStatus = 1 AND p.dealDate >= DATE_ADD('"+firstDay+"',INTERVAL-(pfp.intestModel) DAY) and  p.dealDate <= DATE_ADD('"+endDay+"',INTERVAL-(pfp.intestModel) DAY)";
			System.out.println(sql);
			List<PlFinanceProductUserAccountInfo> listP=this.getSession().createSQLQuery(sql).
				addScalar("id",Hibernate.LONG).
				setResultTransformer(Transformers.aliasToBean(PlFinanceProductUserAccountInfo.class)).
				list();
			 
			 if(listP!=null){
				 for(PlFinanceProductUserAccountInfo ss :listP){
					 PlFinanceProductUserAccountInfo temp=get(ss.getId());
					 temp.setDealStatus(Short.valueOf("2"));
					 temp.setDealStatusName("交易成功");
					 temp.setRemark("由在途金额转为计息金额");
					 this.save(temp);
				 }
			 }
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	@Override
	public Boolean getRecord(Long userId, Long productId, Date date) {
		// TODO Auto-generated method stub
		String firstDay=DateUtil.getFirstDate(date, "first");
		String sql=" from PlFinanceProductUserAccountInfo as p where  p.dealtype=3 and p.userId="+userId+" and p.productId="+productId+" and p.dealDate='"+firstDay+"'";
		PlFinanceProductUserAccountInfo info=(PlFinanceProductUserAccountInfo) this.getSession().createQuery(sql).uniqueResult();
		return info!=null?false:true;
	}

}