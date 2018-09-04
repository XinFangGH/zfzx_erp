package com.zhiwei.credit.action.creditFlow.customer.person;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonPublicActivity;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonPublicActivityService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustPersonPublicActivityAction extends BaseAction{
	@Resource
	private BpCustPersonPublicActivityService bpCustPersonPublicActivityService;
	private BpCustPersonPublicActivity bpCustPersonPublicActivity;
	@Resource
	private PersonService personService;
	
	private Long activityId;

	public Long getActivityId() {
		return activityId;
	}

	public void setActivityId(Long activityId) {
		this.activityId = activityId;
	}

	public BpCustPersonPublicActivity getBpCustPersonPublicActivity() {
		return bpCustPersonPublicActivity;
	}

	public void setBpCustPersonPublicActivity(BpCustPersonPublicActivity bpCustPersonPublicActivity) {
		this.bpCustPersonPublicActivity = bpCustPersonPublicActivity;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		//根据个人客户ID查询出该客户所有的社会活动信息，返回字符串成功或失败
		int personId = Integer.valueOf(getRequest().getParameter("personId"));
		Person person = personService.getById(personId);
		List<BpCustPersonPublicActivity> list = new ArrayList<BpCustPersonPublicActivity>(person.getActivityInfos());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("activityStartTime","activityEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"activityStartTime","activityEndTime"});
		buff.append(json.serialize(list));
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
				bpCustPersonPublicActivityService.remove(new Long(id));
			}
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 删除
	 */
	public String delete(){
		String[] ids = this.getRequest().getParameterValues("ids");
		if(null!=ids && ids.length>0){
			for(String id:ids){
				bpCustPersonPublicActivityService.remove(new Long(id));
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
		BpCustPersonPublicActivity bpCustPersonPublicActivity=bpCustPersonPublicActivityService.get(activityId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustPersonPublicActivity));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//获取传过来的参数
		try{
			HttpServletRequest request = getRequest();
			int personId = Integer.valueOf(request.getParameter("personId"));
			String publicActivityInfos = request.getParameter("publicActivityInfos");
			if(publicActivityInfos!=null&&!"".equals(publicActivityInfos)){
				String[] publicActivityInfoArr = publicActivityInfos.split("@");
				for (int i = 0; i < publicActivityInfoArr.length; i++) {
					String str = publicActivityInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustPersonPublicActivity publicActivityInfo = (BpCustPersonPublicActivity) JSONMapper.toJava(parser.nextValue(), BpCustPersonPublicActivity.class);
					if(null == publicActivityInfo.getActivityId() || "".equals(publicActivityInfo.getActivityId())){
						bpCustPersonPublicActivityService.save(publicActivityInfo);
					}else{
						BpCustPersonPublicActivity temp = bpCustPersonPublicActivityService.get(publicActivityInfo.getActivityId());
						BeanUtil.copyNotNullProperties(temp, publicActivityInfo);
						bpCustPersonPublicActivityService.save(temp);
					}
				}
				setJsonString("{success:true}");
			}
		}catch (Exception ex) {
			setJsonString("{success:false}");
			ex.printStackTrace();
			logger.error(ex.getMessage());
		}
		return SUCCESS;
	}
}
