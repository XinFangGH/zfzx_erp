package com.zhiwei.credit.action.creditFlow.smallLoan.finance;

import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.smallLoan.finance.SlUrgeRecord;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.smallLoan.finance.SlUrgeRecordService;
import com.zhiwei.credit.service.system.AppUserService;

public class SlUrgeRecordAction extends BaseAction {

	@Resource
	private SlUrgeRecordService slUrgeRecordService;
	@Resource
	private AppUserService appUserService;
	
	private Long fundIntentId;
	public String list(){
		List<SlUrgeRecord> list = slUrgeRecordService.getListByFundIntentId(fundIntentId);
		if(list!=null&&list.size()!=0){
			for(SlUrgeRecord record:list){
				if(record!=null){
					if(record.getCreatorId()!=null){
						AppUser user = appUserService.get(record.getCreatorId());
						if(user!=null)
							record.setCreatorName(user.getFullname());
					}
				}
			}
		}
		StringBuffer buff = new StringBuffer("{success:true").append(",result:");
		Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd")
		/* .excludeFieldsWithoutExposeAnnotation() */.create();
		buff.append(gson.toJson(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	public Long getFundIntentId() {
		return fundIntentId;
	}
	public void setFundIntentId(Long fundIntentId) {
		this.fundIntentId = fundIntentId;
	}


}