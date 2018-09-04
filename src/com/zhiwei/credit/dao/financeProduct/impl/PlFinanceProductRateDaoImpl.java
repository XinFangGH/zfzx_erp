package com.zhiwei.credit.dao.financeProduct.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.hibernate.Hibernate;
import org.hibernate.transform.Transformers;

import com.zhiwei.core.dao.impl.BaseDaoImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.financeProduct.PlFinanceProductRateDao;
import com.zhiwei.credit.model.creditFlow.finance.BpFundIntent;
import com.zhiwei.credit.model.financeProduct.PlFinanceProduct;
import com.zhiwei.credit.model.financeProduct.PlFinanceProductRate;

/**
 * 
 * @author 
 *
 */
@SuppressWarnings("unchecked")
public class PlFinanceProductRateDaoImpl extends BaseDaoImpl<PlFinanceProductRate> implements PlFinanceProductRateDao{

	public PlFinanceProductRateDaoImpl() {
		super(PlFinanceProductRate.class);
	}

	@Override
	public List<PlFinanceProductRate> getAllRateAndOrder(HttpServletRequest request,PagingBean pb) {
		// TODO Auto-generated method stub
		String sql="SELECT "+
						"p.id AS id, "+
						"p.intentDate AS intentDate, "+
						"p.modifyDate AS modifyDate, "+
						"p.createDate AS createDate, "+
						"p.createUserId AS createUserId, "+
						"p.createUserName AS createUserName, "+
						"p.yearRate AS yearRate, "+
						"p.sevYearRate as sevYearRate, "+
						"p.dayRate as dayRate, "+
						"p.sevDayRate as sevDayRate, "+
						"p.productId AS productId, "+
						"pp.productName AS productName "+
					"FROM "+
						"`pl_finance_product_rate` AS p "+
					"LEFT JOIN pl_finance_product AS pp on (p.productId =pp.id) "+
					"where 1=1  ";
		String intentDateStart=request.getParameter("intentDateStart");
		if(intentDateStart!=null&&!"".equals(intentDateStart)){
			sql=sql+" and p.intentDate>= '"+intentDateStart+" 00:00:00'";
		}
		String intentDateEnd=request.getParameter("intentDateEnd");
		if(intentDateEnd!=null&&!"".equals(intentDateEnd)){
			sql=sql+" and p.intentDate<= '"+intentDateEnd+" 59:59:59'";
		}
		sql=sql+" ORDER BY p.productId ASC,p.intentDate DESC";
		List<PlFinanceProductRate> list=this.getSession().createSQLQuery(sql).
										addScalar("id", Hibernate.LONG).
										addScalar("intentDate", Hibernate.DATE).
										addScalar("modifyDate", Hibernate.DATE).
										addScalar("createDate", Hibernate.DATE).
										addScalar("createUserId", Hibernate.LONG).
										addScalar("createUserName", Hibernate.STRING).
										addScalar("yearRate", Hibernate.BIG_DECIMAL).
										addScalar("sevYearRate", Hibernate.BIG_DECIMAL).
										addScalar("dayRate", Hibernate.BIG_DECIMAL).
										addScalar("sevDayRate", Hibernate.BIG_DECIMAL).
										addScalar("productId", Hibernate.LONG).
										addScalar("productName", Hibernate.STRING).
										setResultTransformer(Transformers.aliasToBean(PlFinanceProductRate.class)).
										list();
		
        if(pb!=null&&pb.getStart()!=null&&pb.getPageSize()!=null){
        	pb.setTotalItems(list.size());
        	List<PlFinanceProductRate> listpage=this.getSession().createSQLQuery(sql).
												addScalar("id", Hibernate.LONG).
												addScalar("intentDate", Hibernate.DATE).
												addScalar("modifyDate", Hibernate.DATE).
												addScalar("createDate", Hibernate.DATE).
												addScalar("createUserId", Hibernate.LONG).
												addScalar("createUserName", Hibernate.STRING).
												addScalar("yearRate", Hibernate.BIG_DECIMAL).
												addScalar("sevYearRate", Hibernate.BIG_DECIMAL).
												addScalar("dayRate", Hibernate.BIG_DECIMAL).
												addScalar("sevDayRate", Hibernate.BIG_DECIMAL).
												addScalar("productId", Hibernate.LONG).
												addScalar("productName", Hibernate.STRING).
												setResultTransformer(Transformers.aliasToBean(PlFinanceProductRate.class)).
												setFirstResult(pb.getStart()).
												setMaxResults(pb.getPageSize()).
												list();
        	return listpage;
        }else{
        	return list;
        }
		
	}

	@Override
	public PlFinanceProductRate setSevenRate(
			PlFinanceProductRate plFinanceProductRate) {
		// TODO Auto-generated method stub
		SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
		String sql="select  sum(pp.dayRate) as seveDayRate,sum(pp.yearRate) as seveYearRate,count(*) as counts from (SELECT p.* FROM `pl_finance_product_rate` AS p "+
		"WHERE p.intentDate < '"+formatDate.format(plFinanceProductRate.getIntentDate())+"' AND p.productId ="+plFinanceProductRate.getProductId();
		sql =sql+" ORDER BY p.intentDate DESC LIMIT 6) as pp";
		PlFinanceProductRate plFinanceProductRatep=(PlFinanceProductRate) this.getSession().createSQLQuery(sql).
					addScalar("seveDayRate", Hibernate.BIG_DECIMAL).
					addScalar("seveYearRate", Hibernate.BIG_DECIMAL).
					addScalar("counts", Hibernate.INTEGER).
					setResultTransformer(Transformers.aliasToBean(PlFinanceProductRate.class)).uniqueResult();
		return plFinanceProductRatep;
	}

	@Override
	public Date getLastDay(String productId) {
		// TODO Auto-generated method stub
		String sql =" SELECT  MAX(p.intentDate) FROM `pl_finance_product_rate` AS p WHERE  p.productId = "+Long.valueOf(productId);
		return (Date) this.getSession().createSQLQuery(sql).uniqueResult();
	}

	@Override
	public void addNowDayRate(Date date) {
		// TODO Auto-generated method stub
		try{
			if(date==null){
				date=new Date();
			}
			String firstDay=DateUtil.getFirstDate(date, "first");
			String endDay=DateUtil.getFirstDate(date, "end");
			String yfirstDay=DateUtil.getFirstDate(DateUtil.addDaysToDate(date, -1), "first");
			String yendDay=DateUtil.getFirstDate(DateUtil.addDaysToDate(date, -1), "end");
			String sql ="SELECT p.id as id,  pfr1.yearRate as dayRate, pfr.yearRate as yearRate FROM pl_finance_product AS p  "+
	        "LEFT JOIN pl_finance_product_rate AS pfr ON (p.id = pfr.productId and pfr.intentDate Between  '"+firstDay+"' and '"+endDay+"') " +
	        "LEFT JOIN pl_finance_product_rate AS pfr1 ON (p.id = pfr1.productId and pfr1.intentDate Between  '"+yfirstDay+"' and '"+yendDay+"')  ";
			System.out.println(sql);
			List<PlFinanceProduct> list=this.getSession().createSQLQuery(sql).
			addScalar("id", Hibernate.LONG).
			addScalar("dayRate", Hibernate.BIG_DECIMAL).
			addScalar("yearRate", Hibernate.BIG_DECIMAL).
			setResultTransformer(Transformers.aliasToBean(PlFinanceProduct.class)).list();
			if(list!=null&&list.size()>0){
				for(PlFinanceProduct  temp:list){
					if(temp.getYearRate()==null&&temp.getDayRate()!=null){//如果没有初始化的利率则不能自动生成
						SimpleDateFormat formatDate = new SimpleDateFormat("yyyy-MM-dd");
						PlFinanceProductRate plFinanceProductRate=new PlFinanceProductRate();
						plFinanceProductRate.setProductId(temp.getId());
						plFinanceProductRate.setYearRate(temp.getDayRate());
						plFinanceProductRate.setIntentDate(formatDate.parse(firstDay));
						plFinanceProductRate.setCreateDate(new Date());
						plFinanceProductRate.setModifyDate(new Date());
						plFinanceProductRate.setCreateUserId(ContextUtil.getCurrentUserId()!=null?ContextUtil.getCurrentUserId():1l);
						plFinanceProductRate.setCreateUserName(ContextUtil.getCurrentUser()!=null?ContextUtil.getCurrentUser().getFullname():"超级管理员");
						plFinanceProductRate.setDayRate(plFinanceProductRate.getYearRate().divide(new BigDecimal(365), 6, BigDecimal.ROUND_HALF_UP));
						PlFinanceProductRate npf=this.setSevenRate(plFinanceProductRate);
						if(npf!=null&&npf.getCounts()>0){
							int counts=npf.getCounts()+1;
							plFinanceProductRate.setSevDayRate((plFinanceProductRate.getDayRate().add(npf.getSeveDayRate())).divide(new BigDecimal(counts), 6, BigDecimal.ROUND_HALF_UP));
							plFinanceProductRate.setSevYearRate((plFinanceProductRate.getYearRate().add(npf.getSeveYearRate())).divide(new BigDecimal(counts), 6, BigDecimal.ROUND_HALF_UP));
						}else{
							plFinanceProductRate.setSevDayRate(plFinanceProductRate.getDayRate());
							plFinanceProductRate.setSevYearRate(plFinanceProductRate.getYearRate());
						}
						this.save(plFinanceProductRate);
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		
	}

	@Override
	public PlFinanceProductRate getRate(Long productId, Date date) {
		// TODO Auto-generated method stub
		String firstDay=DateUtil.getFirstDate(date, "first");
		String sql="from PlFinanceProductRate as p where p.productId="+productId+" and p.intentDate='"+firstDay+"'";
		return (PlFinanceProductRate) this.getSession().createQuery(sql).list().get(0);
	}

}