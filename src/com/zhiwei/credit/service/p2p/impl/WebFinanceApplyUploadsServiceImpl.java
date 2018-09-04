package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.WebFinanceApplyUploadsDao;
import com.zhiwei.credit.model.p2p.WebFinanceApplyUploads;
import com.zhiwei.credit.service.p2p.WebFinanceApplyUploadsService;

/**
 * 
 * @author 
 *
 */
public class WebFinanceApplyUploadsServiceImpl extends BaseServiceImpl<WebFinanceApplyUploads> implements WebFinanceApplyUploadsService{
	@SuppressWarnings("unused")
	private WebFinanceApplyUploadsDao dao;
	
	public WebFinanceApplyUploadsServiceImpl(WebFinanceApplyUploadsDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<WebFinanceApplyUploads> upLoadList(Integer start,Integer limit,String state, HttpServletRequest request) {
		return dao.upLoadList(start, limit,state, request);
	}


	@Override
	public List<WebFinanceApplyUploads> getlistByUserIDAndType(Integer userID ,String materialstype) {
		// TODO Auto-generated method stub
		return dao.getlistByUserIDAndType(userID , materialstype  );
	}
	/**
	 * 稳安贷 开通p2p 向web_finance_apply_uploads表中添加16条数据
	 * @param userid  
	 */
	@Override
	public void addp2pLoginName(Long userid) {
		String[] str={"IDCard","CreditRecord","Income","WebShop","House","Vehicle","Marriage","Education","Career","JobTitle","MobilePhone","MicroBlog","Residence","CompanyPlace","CompanyRevenue","Teacher"};
		WebFinanceApplyUploads webFinanceApplyUploads;
		for(int i=0;i<str.length;i++){
			webFinanceApplyUploads=new WebFinanceApplyUploads();
			webFinanceApplyUploads.setUserID(userid);
			webFinanceApplyUploads.setFiles("");
			webFinanceApplyUploads.setLastuploadtime(new Date());
			webFinanceApplyUploads.setMaterialstype(str[i]);
			webFinanceApplyUploads.setStatus(0);
			dao.save(webFinanceApplyUploads);
		}
	}

	@Override
	public List<WebFinanceApplyUploads> upLoadOnLineList(Integer start,
			Integer limit, String state, HttpServletRequest request) {
		// TODO Auto-generated method stub
		return dao.upLoadOnLineList(start, limit, state, request);
	}
}