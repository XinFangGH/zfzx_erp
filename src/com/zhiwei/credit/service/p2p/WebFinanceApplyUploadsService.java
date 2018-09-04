package com.zhiwei.credit.service.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.WebFinanceApplyUploads;

/**
 * 
 * @author 
 *
 */
public interface WebFinanceApplyUploadsService extends BaseService<WebFinanceApplyUploads>{
	public List<WebFinanceApplyUploads> upLoadList(Integer start, Integer limit,String state, HttpServletRequest request);
	
	public List<WebFinanceApplyUploads> getlistByUserIDAndType(Integer userID ,String materialstype);
	/**
	 * 稳安贷 开通p2p 向web_finance_apply_uploads表中添加16条数据
	 * @param userid
	 */
	public void addp2pLoginName(Long userid);
	/**
	 * 查询所有用户线上上传未添加到项目的文件列表
	 */
	public List<WebFinanceApplyUploads> upLoadOnLineList(Integer start, Integer limit,String state ,HttpServletRequest request);
}


