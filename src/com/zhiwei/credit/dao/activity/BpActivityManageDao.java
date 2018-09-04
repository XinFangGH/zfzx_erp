package com.zhiwei.credit.dao.activity;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import com.zhiwei.core.dao.BaseDao;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.model.activity.BpActivityManage;

/**
 * 
 * @author 
 *
 */
public interface BpActivityManageDao extends BaseDao<BpActivityManage>{

	public List<BpActivityManage>  findActivityNumber(String flag);

	public List<BpActivityManage> findExistCrossDate(BpActivityManage bpActivityManage);

	public void findList(PageBean<BpActivityManage> pageBean);

	
	/**
	 * 查找全部当前日期存活的活动(含积分丶红包丶优惠券)
	 * 说明--当前活动没有被关闭，并且活动结束日期包含当前日期
     * @param activityType   --活动操作类型
     * 		  		["注册", "1"],
					["邀请", "2"],
					["投标", "3"],
					["充值", "4"],
					["邀请好友第一次投标", "5"]			
     * 
	 * @return
	 * @author LIUSL
	 */
	public List<BpActivityManage> listActivity(Long activityType,Date buyDate);
	
}