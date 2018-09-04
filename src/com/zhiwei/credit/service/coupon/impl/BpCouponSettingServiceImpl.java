package com.zhiwei.credit.service.coupon.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.credit.dao.coupon.BpCouponSettingDao;
import com.zhiwei.credit.model.coupon.BpCouponSetting;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.service.coupon.BpCouponSettingService;
import com.zhiwei.credit.service.coupon.BpCouponsService;

/**
 * 
 * @author 
 *
 */
public class BpCouponSettingServiceImpl extends BaseServiceImpl<BpCouponSetting> implements BpCouponSettingService{
	@SuppressWarnings("unused")
	private BpCouponSettingDao dao;
	@Resource
	private BpCouponsService bpCouponsService;
	@Resource
	private BpCouponSettingService bpCouponSettingService;
	public BpCouponSettingServiceImpl(BpCouponSettingDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String check(String checkType,Long categoryId) {
		// TODO Auto-generated method stub
		try {
			String msg="";
			if(categoryId!=null){
				//审核类型
				if(checkType!=null&&!"".equals(checkType)){
					BpCouponSetting bps=dao.get(categoryId);
					if(bps!=null){
						bps.setCouponCheckUserId(ContextUtil.getCurrentUserId());
						bps.setCouponCheckDate(new Date());
						bps.setCouponCheckUserName(ContextUtil.getCurrentUser().getFullname());
						if(checkType.equals("pass")){
							BpCouponSetting couset = bpCouponSettingService.get(categoryId);
							//判断是批量派发还是单个派发
							if(couset!=null&&!couset.equals("")&&couset.getSetType()==1){//单个派发
								Boolean flag=bpCouponsService.createBpCoupons(bps,BpCoupons.COUPONRESOURCE_NORMAL);
								if(flag){
									bps.setCouponState(Short.valueOf("10"));
									msg="操作成功,审核通过并成功生成了所有的优惠券";
								}else{
									bps.setCouponCheckUserId(null);
									bps.setCouponCheckDate(null);
									bps.setCouponCheckUserName(null);
									msg="操作失败,没有生成所有的优惠券";
								}
							}else if(couset!=null&&!couset.equals("")&&couset.getSetType()==2){//批量派发
								bps.setCouponState(Short.valueOf("10"));
								msg="操作成功,审核通过请在批量派发里面查看";
							}else{
								msg="操作失败,派发类型错误";
							}
						}else if(checkType.equals("refuse")){
							bps.setCouponState(Short.valueOf("5"));
							msg="操作成功";
						}
						dao.save(bps);
					}else{
						msg="操作失败,数据库没有找到对应审核记录";
					}
				}else{
					msg="操作失败,没有审核类型";
				}
			}else{
				msg="操作失败,没有审核记录";
			}
			return msg;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "系统出错了，请联系管理员";
		}
	}

}