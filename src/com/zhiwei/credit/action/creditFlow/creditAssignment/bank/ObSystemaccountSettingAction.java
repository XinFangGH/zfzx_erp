package com.zhiwei.credit.action.creditFlow.creditAssignment.bank;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import javax.annotation.Resource;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.creditAssignment.bank.ObSystemaccountSetting;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.creditFlow.creditAssignment.bank.ObSystemaccountSettingService;
/**
 * 
 * @author 
 *
 */
public class ObSystemaccountSettingAction extends BaseAction{
	@Resource
	private ObSystemaccountSettingService obSystemaccountSettingService;
	private ObSystemaccountSetting obSystemaccountSetting;
	
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public ObSystemaccountSetting getObSystemaccountSetting() {
		return obSystemaccountSetting;
	}

	public void setObSystemaccountSetting(ObSystemaccountSetting obSystemaccountSetting) {
		this.obSystemaccountSetting = obSystemaccountSetting;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<ObSystemaccountSetting> list= obSystemaccountSettingService.getAll(filter);
		
		Type type=new TypeToken<List<ObSystemaccountSetting>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
		
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
				obSystemaccountSettingService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		ObSystemaccountSetting obSystemaccountSetting=obSystemaccountSettingService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(obSystemaccountSetting));
		sb.append("}");
		setJsonString(sb.toString());
		System.out.println(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(obSystemaccountSetting.getId()==null){
			obSystemaccountSettingService.save(obSystemaccountSetting);
		}else{
			ObSystemaccountSetting orgObSystemaccountSetting=obSystemaccountSettingService.get(obSystemaccountSetting.getId());
			try{
				BeanUtil.copyNotNullProperties(orgObSystemaccountSetting, obSystemaccountSetting);
				obSystemaccountSettingService.save(orgObSystemaccountSetting);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String settingList() {
		List<ObSystemaccountSetting> list= obSystemaccountSettingService.findObSystemaccountSetting();
		StringBuffer buff = new StringBuffer("[");
		for (ObSystemaccountSetting s : list) {
			buff.append("['").append(s.getTypeKey()).append("','")
					.append(s.getTypeName()).append("'],");

		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}

		/**
		 *三方业务类型查询
		 *
		 * @auther: XinFang
		 * @date: 2018/7/23 11:55
		 */
		public String thirdList() {
		List<ObSystemaccountSetting> list= obSystemaccountSettingService.findThirdObSystemaccountSetting();
		StringBuffer buff = new StringBuffer("[");
		for (ObSystemaccountSetting s : list) {
			buff.append("['").append(s.getTypeKey()).append("','")
					.append(s.getTypeName()).append("'],");

		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}


}
