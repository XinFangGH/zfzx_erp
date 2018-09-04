package com.zhiwei.credit.action.creditFlow.bonusSystem.setting;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.integral.IntegralManage;
import com.zhiwei.core.integral.IntegralManageImpl;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.bonusSystem.setting.WebBonusSetting;
import com.zhiwei.credit.service.creditFlow.bonusSystem.setting.WebBonusSettingService;

import flexjson.JSONSerializer;
/**
 * 
 * @author 
 *
 */
public class WebBonusSettingAction extends BaseAction{
	@Resource
	private WebBonusSettingService webBonusSettingService;
	
	private WebBonusSetting webBonusSetting;
	
	private Long bonusId;

	public Long getBonusId() {
		return bonusId;
	}

	public void setBonusId(Long bonusId) {
		this.bonusId = bonusId;
	}

	public WebBonusSetting getWebBonusSetting() {
		return webBonusSetting;
	}

	public void setWebBonusSetting(WebBonusSetting webBonusSetting) {
		this.webBonusSetting = webBonusSetting;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<WebBonusSetting> list= webBonusSettingService.getAll(filter);
		
		Type type=new TypeToken<List<WebBonusSetting>>(){}.getType();
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
		
		String ids= getRequest().getParameter("ids");
		String[] split = ids.split(",");
		if(split!=null){
			for(String id:split){
				webBonusSettingService.remove(new Long(id));
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
		Map<String, Object> map = new HashMap<String, Object>();
		WebBonusSetting webBonusSetting=webBonusSettingService.get(bonusId);
		map.put("webBonusSetting", webBonusSetting);
		map.put("record", "");
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String result = "操作成功";
		
		Map<String,String> queryMap = new HashMap<String, String>();
		//action类名
		queryMap.put("userAction",webBonusSetting.getUserAction());
		//方法名
		queryMap.put("userActionKey", webBonusSetting.getUserActionKey());
		//会员等级
		queryMap.put("memberLevel", webBonusSetting.getMemberLevel());
		//奖惩措施
	//	queryMap.put("isBonus", webBonusSetting.getIsBonus());
		
		//查询没有相同的积分规则
		WebBonusSetting excludeALike = webBonusSettingService.excludeALike(queryMap);
		
		if(webBonusSetting.getBonusId()==null){
			if(excludeALike==null){//如果没有查到,则保存
				if(webBonusSetting.getMemberLevel()==null||"".equals(webBonusSetting.getMemberLevel())){
					webBonusSetting.setMemberLevel("0");
				}
				webBonusSettingService.save(webBonusSetting);
			}else{
				result = "已有相同条件的规则";
			}
		}else{
			
			if(excludeALike!=null){//如果没有查到,则保存
				//如果更新前,更新后,Key值等信息没变,则更新其它信息
				if(		excludeALike.getUserAction().equals(webBonusSetting.getUserAction())
						&& excludeALike.getUserActionKey().equals(webBonusSetting.getUserActionKey())		
						&& excludeALike.getMemberLevel().equals(webBonusSetting.getMemberLevel())	
						&& excludeALike.getBonusId().compareTo(webBonusSetting.getBonusId())==0
				){
					WebBonusSetting _webBonusSetting = webBonusSettingService.get(webBonusSetting.getBonusId());
					try {
						BeanUtil.copyNotNullProperties(_webBonusSetting, webBonusSetting);
					} catch (Exception e) {
					} 
					webBonusSettingService.save(_webBonusSetting);
				}else{
					result = "不能更改为相同条件的规则";
				}
				
			}else{
				WebBonusSetting _webBonusSetting = webBonusSettingService.get(webBonusSetting.getBonusId());
				try {
					BeanUtil.copyNotNullProperties(_webBonusSetting, webBonusSetting);
				} catch (Exception e) {
				} 
				webBonusSettingService.save(_webBonusSetting);
			}
			
		}
		setJsonString("{success:true,\"msg\":\""+result+"\"}");
		System.out.println("{success:true,\"msg\":\""+result+"\"}");
		return SUCCESS;
		
	}
}
