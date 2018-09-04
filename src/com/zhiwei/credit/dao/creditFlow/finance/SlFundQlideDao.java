package com.zhiwei.credit.dao.creditFlow.finance;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.Map;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.credit.model.creditFlow.finance.SlFundIntent;
import com.zhiwei.credit.model.creditFlow.finance.SlFundQlide;

/**
 * 
 * @author 
 *
 */
public interface SlFundQlideDao extends BaseDao<SlFundQlide>{
	public  List<SlFundQlide> getallbycompanyId();
	public  List<SlFundQlide> getLastByMaxDate(Long comId);
	
	public List<SlFundQlide> search(Map<String,String> map);
	public int searchsize(Map<String,String> map);
	public List<SlFundQlide> getsortAll(Map<String,String> map);
	public int getsortcount(Map<String,String> map);
	public List<SlFundQlide> getcashQlide(String iscash);
	public List<SlFundQlide> searchcash(Map<String,String> map);
	public int searchcashsize(Map<String,String> map);
	public List<SlFundQlide> listbyaccount(Map<String,String> map);
	public int listbyaccountsize(Map<String,String> map);
	public List<SlFundQlide>  listbyaccountall(Map<String,String> map);
	public List<SlFundQlide>  listcashall(Map<String,String> map);
	public List<SlFundQlide> searchnotcheck(Map<String,String> map);
	public int searchnotchecksize(Map<String,String> map);
}