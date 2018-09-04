package com.zhiwei.credit.action.coupon;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.coupon.BpCouponSetting;
import com.zhiwei.credit.model.coupon.BpCoupons;
import com.zhiwei.credit.service.coupon.BpCouponSettingService;
import com.zhiwei.credit.service.system.DictionaryIndependentService;
/**
 * 
 * @author 
 *
 */
public class BpCouponSettingAction extends BaseAction{
	@Resource
	private BpCouponSettingService bpCouponSettingService;
	@Resource
	private DictionaryIndependentService dictionaryIndependentService ;
	private BpCouponSetting bpCouponSetting;
	
	private Long categoryId;

	public Long getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(Long categoryId) {
		this.categoryId = categoryId;
	}

	public BpCouponSetting getBpCouponSetting() {
		return bpCouponSetting;
	}

	public void setBpCouponSetting(BpCouponSetting bpCouponSetting) {
		this.bpCouponSetting = bpCouponSetting;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		String setType = this.getRequest().getParameter("Q_setType_L_EQ");
		String couponState = this.getRequest().getParameter("Q_couponState_SN_EQ");
		if(couponState!=null&&!couponState.equals("")&&!couponState.equals("2")){
				if(setType!=null&&!setType.equals("")){
					filter.addFilter("Q_counponCount_N_GT", "0");
				}
		}
		List<BpCouponSetting> list= bpCouponSettingService.getAll(filter);
		for(BpCouponSetting bp:list){
			Date d = new Date();
			if(bp.getCouponEndDate().getTime()<d.getTime()){
				bp.setCouponState(Short.valueOf("4"));
				bpCouponSettingService.save(bp);
			}
		}
		Type type=new TypeToken<List<BpCouponSetting>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		System.out.println(jsonString);
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				bpCouponSettingService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true,msg:\"删除成功\"}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpCouponSetting bpCouponSetting=bpCouponSettingService.get(categoryId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCouponSetting));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date couponEndDate=null;
		try {
			couponEndDate = sdf2.parse(sdf.format(bpCouponSetting.getCouponEndDate()));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		bpCouponSetting.setCouponEndDate(couponEndDate);
		if(bpCouponSetting.getCategoryId()==null){
			
			if(bpCouponSetting.getCouponType()!=null){
				//去除数据字典配置类型
				//bpCouponSetting.setCouponTypeValue(dictionaryIndependentService.getByDicKey(bpCouponSetting.getCouponType()).get(0).getItemValue());
			}
			if(bpCouponSetting.getCounponCount()!=null&&bpCouponSetting.getCouponValue()!=null){
				BigDecimal totalMoney=bpCouponSetting.getCouponValue().multiply(new BigDecimal(bpCouponSetting.getCounponCount()));
				bpCouponSetting.setTotalCouponValue(totalMoney);
				bpCouponSetting.setTotalSumCouponValue(totalMoney);
				bpCouponSetting.setCounponSumCount(bpCouponSetting.getCounponCount());
			}else{
				bpCouponSetting.setTotalCouponValue(new BigDecimal("0"));
			}
			bpCouponSetting.setCompanyId(ContextUtil.getLoginCompanyId());
			bpCouponSetting.setCreateDate(new Date());
			bpCouponSetting.setCreateUserId(ContextUtil.getCurrentUserId());
			bpCouponSetting.setCreateName(ContextUtil.getCurrentUser().getFullname());
			bpCouponSettingService.save(bpCouponSetting);
		}else{
			BpCouponSetting orgBpCouponSetting=bpCouponSettingService.get(bpCouponSetting.getCategoryId());
			try{
				BeanUtil.copyNotNullProperties(orgBpCouponSetting, bpCouponSetting);
				bpCouponSettingService.save(orgBpCouponSetting);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 用来审核优惠券
	 * @return
	 */
	public String check(){
		String checkType=this.getRequest().getParameter("checkType");
		String categoryId = this.getRequest().getParameter("categoryId");
		String cateId[] = categoryId.split(",");
		String msg="";
		for(int i=0;i<cateId.length;i++){
			 msg=bpCouponSettingService.check(checkType,Long.valueOf(cateId[i]));
		}
		setJsonString("{success:true,msg:\""+msg+"\"}");
		return SUCCESS;
		
	}
}
