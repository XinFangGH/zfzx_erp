package com.zhiwei.credit.action.activity;
/*
 *  
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.util.DateUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.core.web.paging.PageBean;
import com.zhiwei.credit.model.activity.BpActivityManage;
import com.zhiwei.credit.service.activity.BpActivityManageService;
/**
 * 
 * @author 
 *
 */
public class BpActivityManageAction extends BaseAction{
	@Resource
	private BpActivityManageService bpActivityManageService;
	private BpActivityManage bpActivityManage;
	
	private Long activityId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public BpActivityManage getBpActivityManage() {
		return bpActivityManage;
	}

	public void setBpActivityManage(BpActivityManage bpActivityManage) {
		this.bpActivityManage = bpActivityManage;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		PageBean<BpActivityManage> pageBean = new PageBean<BpActivityManage>(start, limit,getRequest());
		bpActivityManageService.findList(pageBean);
		
		if(pageBean.getResult()!=null&&pageBean.getResult().size()>0){
			for(BpActivityManage b : pageBean.getResult()){
				bpActivityManageService.evict(b);
				
				if(b.getActivityEndDate()!=null){
					if(DateUtil.compare_date(b.getActivityEndDate(), new Date())>0){
						b.setOverdueValue("是");
					}else{
						b.setOverdueValue("否");
					}
				}
			}
		}
		
		
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(pageBean.getTotalCounts()).append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		buff.append(gson.toJson(pageBean.getResult()));
		buff.append("}");
		jsonString = buff.toString();
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
				bpActivityManageService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String find(){
		BpActivityManage bpActivityManage=bpActivityManageService.get(activityId);
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpActivityManage));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	
	/***
	 * 根据活动类型及当前日期生成活动编号
	 */
	public void findActivityNumber(){
		String flag=this.getRequest().getParameter("flag");
		bpActivityManageService.findActivityNumber(flag);
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		StringBuffer sb = new StringBuffer();
		String flag=this.getRequest().getParameter("bpActivityManage.flag");
		String sendType=this.getRequest().getParameter("bpActivityManage.sendType");
		String isGetAstrict =  this.getRequest().getParameter("bpActivityManage.isGetAstrict");
		SimpleDateFormat sdf =new SimpleDateFormat("yyyy-MM-dd 23:59:59");
		SimpleDateFormat sdf1 =new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf2 =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date endDate=null;
		try {
			if(flag.equals("3")){//积分兑换优惠券
				bpActivityManage.setActivityStartDate(sdf1.parse(sdf1.format(new Date())));
				Date date = sdf1.parse(sdf1.format(new Date()));
				Calendar cl = Calendar.getInstance();
			    cl.setTime(date); cl.add(Calendar.DATE,bpActivityManage.getValidNumber()-1);
			    endDate =sdf2.parse(sdf.format(cl.getTime()));
			}else{
				endDate = sdf2.parse(sdf.format(bpActivityManage.getActivityEndDate()));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		bpActivityManage.setActivityEndDate(endDate);
		if(null==bpActivityManage.getActivityId()){
			//需判断如果是同一活动类型_同一发送类型_活动的起止日期不允许交叉                                          @1
//			boolean isExist=true;
//			boolean tempFlag="3".equals(flag)?false:true;//3代表积分兑换优惠券  此时不要判断@1
			//如果活动设置的是累计活动，不需要判断 @1
//			if(sendType.equals("6")||sendType.equals("7")||sendType.equals("8")||sendType.equals("9")){
//				tempFlag=false;
//			}
//			if(tempFlag){
//				isExist=bpActivityManageService.findExistCrossDate(bpActivityManage);
//			}
//			if(isExist && tempFlag){
//				sb.append("{success:true,flag:false,msg:'在该[时间周期内]已提交过该[发送类型]的[活动]'}");
//			}else{
				bpActivityManage.setCompanyId(ContextUtil.getLoginCompanyId());
				bpActivityManage.setCreaterId(ContextUtil.getCurrentUserId());
				bpActivityManage.setCreateDate(new Date());
				bpActivityManage.setIsGetAstrict(isGetAstrict);
				bpActivityManageService.save(bpActivityManage);
				sb.append("{success:true,flag:true,msg:'成功提交活动'}");
//			}
		}else{
			BpActivityManage orgBpActivityManage=bpActivityManageService.get(bpActivityManage.getActivityId());
			try{
				BeanUtil.copyNotNullProperties(orgBpActivityManage, bpActivityManage);
				bpActivityManageService.save(orgBpActivityManage);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString(sb.toString());
		return SUCCESS;
		
	}
	
	/***
	 * 关闭活动
	 */
	public String close(){
		String[] ids = getRequest().getParameterValues("ids");
		boolean flag=bpActivityManageService.closeActivity(ids);
		if(flag){
			setJsonString("{success:true,msg:'关闭活动成功'}");
		}else{
			setJsonString("{success:true,msg:'关闭活动失败'}");
		}
		return SUCCESS;
	}
	
	/***
	 * 开启活动
	 */
	public String open(){
		String id = getRequest().getParameter("id");
		
		BpActivityManage _bpActivityManage = bpActivityManageService.get(Long.valueOf(id));
		if(_bpActivityManage.getFlag().intValue() ==3){//积分兑换活动--直接开启
			_bpActivityManage.setStatus(Integer.valueOf(0));
			bpActivityManageService.save(_bpActivityManage);
			setJsonString("{success:true,msg:'开启活动成功'}");
			return SUCCESS;
		}
		
		//判断有没有相同的活动操作类型的同一种活动，有就不能开启
		List<BpActivityManage> list = bpActivityManageService.findBySendTypeAndState(_bpActivityManage.getSendType(),_bpActivityManage.getFlag(),Integer.valueOf(0));
		if(list!=null&&list.size()>0){
			setJsonString("{success:true,msg:'开启活动失败，当前有同一种活动正在进行中'}");
			return SUCCESS;
		}
		
		//判断活动结束日期是否大于当前日期，如果大，可以开启，否则不能开启
		long index = DateUtil.compare_date(_bpActivityManage.getActivityEndDate(),new Date());
		if(index<0){
			_bpActivityManage.setStatus(Integer.valueOf(0));
			bpActivityManageService.save(_bpActivityManage);
			setJsonString("{success:true,msg:'开启活动成功'}");
		}else{
			setJsonString("{success:true,msg:'开启活动失败，结束日期不能小于当前日期'}");
		}
		
		return SUCCESS;
	}
	
}
