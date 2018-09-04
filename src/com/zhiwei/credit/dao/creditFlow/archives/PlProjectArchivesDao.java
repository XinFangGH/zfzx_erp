package com.zhiwei.credit.dao.creditFlow.archives;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.archives.PlProjectArchives;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;

/**
 * 
 * @author 
 *
 */
public interface PlProjectArchivesDao extends BaseDao<PlProjectArchives>{
	public int searchprojectsize(Map<String,String> map,String businessType);
	public List searchproject(Map<String,String> map,String businessType);
	public List<PlProjectArchives> getbyproject(Long projectId,String businessType);
	public List projectList(String businessType,HttpServletRequest request,int start,int limit);
	public Long projectCount(String businessType,HttpServletRequest request);
}