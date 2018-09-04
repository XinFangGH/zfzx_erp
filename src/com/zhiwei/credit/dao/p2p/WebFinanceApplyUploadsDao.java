package com.zhiwei.credit.dao.p2p;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.archive.ArchDispatch;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.model.p2p.P2pFriendlink;
import com.zhiwei.credit.model.p2p.WebFinanceApplyUploads;

/**
 * 
 * @author 
 *
 */
public interface WebFinanceApplyUploadsDao extends BaseDao<WebFinanceApplyUploads>{
	public List<WebFinanceApplyUploads> upLoadList(Integer start, Integer limit,String state ,HttpServletRequest request);
	public List <WebFinanceApplyUploads> getlistByUserIDAndType(Integer userId,String materialstype );
	/**
	 * 查询所有用户线上上传未添加到项目的文件列表
	 */
	public List<WebFinanceApplyUploads> upLoadOnLineList(Integer start, Integer limit,String state ,HttpServletRequest request);
	
}