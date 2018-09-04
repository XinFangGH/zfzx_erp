package com.zhiwei.credit.service.creditFlow.customer.salesRecord.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.creditFlow.customer.salesRecord.CsSalesRecordDao;
import com.zhiwei.credit.model.creditFlow.customer.salesRecord.CsSalesRecord;
import com.zhiwei.credit.service.creditFlow.customer.salesRecord.CsSalesRecordService;

/**
 * 
 * @author 
 *
 */
public class CsSalesRecordServiceImpl extends BaseServiceImpl<CsSalesRecord> implements CsSalesRecordService{
	@SuppressWarnings("unused")
	private CsSalesRecordDao dao;
	
	public CsSalesRecordServiceImpl(CsSalesRecordDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<CsSalesRecord> getListByRequest(HttpServletRequest request,Integer start, Integer limit, String[] userIds) {
		// TODO Auto-generated method stub
		/*SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
		String hql =" from CsSalesRecord as cs where cs.companyId="+ContextUtil.getLoginCompanyId();
		if(request.getParameter("userName")!=null&&!"".equals(request.getParameter("userName"))){
			hql=hql +" and cs.userName like '%"+request.getParameter("userName")+"%'";
		}
		if(request.getParameter("userNumber")!=null&&!"".equals(request.getParameter("userNumber"))){
			hql=hql +" and cs.userNumber like '%"+request.getParameter("userNumber")+"%'";
		}
		if(request.getParameter("createDate")!=null&&!"".equals(request.getParameter("createDate"))){
			hql=hql +" and cs.createDate = "+sd.format(request.getParameter("createDate"));
		}
		hql=hql+"  order by cs.createDate asc";*/
		return this.dao.getListByRequest(request, start,limit,userIds);
	}

}