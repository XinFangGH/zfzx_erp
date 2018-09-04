package com.zhiwei.credit.action.admin;

/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
 */
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.Car;
import com.zhiwei.credit.model.admin.CarApply;
import com.zhiwei.credit.model.info.ShortMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.admin.CarApplyService;
import com.zhiwei.credit.service.admin.CarService;
import com.zhiwei.credit.service.info.ShortMessageService;

import flexjson.JSONSerializer;

/**
 * 
 * @author csx
 * 
 */
public class CarApplyAction extends BaseAction {
	@Resource
	private CarApplyService carApplyService;
	private CarApply carApply;
	@Resource
	private ShortMessageService shortMessageService;
	@Resource
	private CarService carService;

	private Long applyId;

	public Long getApplyId() {
		return applyId;
	}

	public void setApplyId(Long applyId) {
		this.applyId = applyId;
	}

	public CarApply getCarApply() {
		return carApply;
	}

	public void setCarApply(CarApply carApply) {
		this.carApply = carApply;
	}

	/**
	 * 显示列表
	 */
	public String list() {

		QueryFilter filter = new QueryFilter(getRequest());
		List<CarApply> list = carApplyService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
				.append(filter.getPagingBean().getTotalItems()).append(
						",result:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("applyDate",
				"startTime", "endTime");
		buff.append(serializer.exclude(new String[] { "class" })
				.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}

	/**
	 * 批量删除
	 * 
	 * @return
	 */
	public String multiDel() {

		String[] ids = getRequest().getParameterValues("ids");
		if (ids != null) {
			for (String id : ids) {
				carApplyService.remove(new Long(id));
			}
		}
		jsonString = "{success:true}";
		return SUCCESS;
	}

	/**
	 * 显示详细信息
	 * 
	 * @return
	 */
	public String get() {
		CarApply carApply = carApplyService.get(applyId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer = JsonUtil.getJSONSerializer("applyDate",
				"startTime", "endTime");
		sb.append(serializer.exclude(new String[] { "class", "car.carApplys" })
				.serialize(carApply));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}

	/**
	 * 判断该车是否在某个时间段在使用
	 */
	private String judgeCarApply(Long carId, Date startTime, Date endTime){
		if(startTime != null && endTime != null){			
			List<CarApply> list = carApplyService.findByCarIdAndStartEndTime(carId, startTime, endTime);
			if(list != null && list.size() > 0){
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				CarApply ca = list.get(0);
				return "车牌号为[" + ca.getCar().getCarNo() + "]" + "在[" + sdf.format(ca.getStartTime())+ " - " + sdf.format(ca.getEndTime()) + "]时间段已经申请！";
			} else {
				return "success";
			}
		}else {
			return "success";
		}
	}
	
	/**
	 * 添加及保存操作
	 */
	public String save() {
		if (carApply.getApplyId() != null) {
			CarApply orgCarApply = carApplyService.get(carApply.getApplyId());
			try {
				BeanUtil.copyNotNullProperties(orgCarApply, carApply);
				String msg = judgeCarApply(carApply.getCarId(), carApply.getStartTime(), carApply.getEndTime());
				if(msg.equalsIgnoreCase("success")){ // 可以申请					
					carApplyService.save(orgCarApply);
					if (orgCarApply.getApprovalStatus() == Car.PASS_APPLY) {
						Long receiveId = orgCarApply.getUserId();
						Car car = carService.get(orgCarApply.getCar().getCarId());
						String content = "你申请的车牌号为" + car.getCarNo() + "已经通过审批，请注意查收";
						shortMessageService.save(AppUser.SYSTEM_USER, receiveId.toString(), content, ShortMessage.MSG_TYPE_SYS);
					}
				} else {
					setJsonString("{failure:true,msg:'" + msg + "'}");
					return SUCCESS;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else {
			String msg = judgeCarApply(carApply.getCarId(), carApply.getStartTime(), carApply.getEndTime());
			if(msg.equalsIgnoreCase("success")){ // 可以申请	
				carApplyService.save(carApply);
			} else {
				setJsonString("{failure:true,msg:'" + msg + "'}");
				return SUCCESS;
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
