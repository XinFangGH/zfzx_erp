package com.zhiwei.credit.action.creditFlow.customer.person;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import java.io.StringReader;
import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.sdicons.json.mapper.JSONMapper;
import com.sdicons.json.parser.JSONParser;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.JsonUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonEducation;
import com.zhiwei.credit.model.creditFlow.customer.person.BpCustPersonNegativeSurvey;
import com.zhiwei.credit.model.creditFlow.customer.person.Person;
import com.zhiwei.credit.service.creditFlow.customer.person.BpCustPersonEducationService;
import com.zhiwei.credit.service.creditFlow.customer.person.PersonService;

import flexjson.JSONSerializer;
import flexjson.transformer.DateTransformer;
/**
 * 
 * @author 
 *
 */
public class BpCustPersonEducationAction extends BaseAction{
	@Resource
	private BpCustPersonEducationService bpCustPersonEducationService;
	private BpCustPersonEducation bpCustPersonEducation;
	@Resource
	private PersonService personService;
	
	private Long educationId;

	public Long getEducationId() {
		return educationId;
	}

	public void setEducationId(Long educationId) {
		this.educationId = educationId;
	}

	public BpCustPersonEducation getBpCustPersonEducation() {
		return bpCustPersonEducation;
	}

	public void setBpCustPersonEducation(BpCustPersonEducation bpCustPersonEducation) {
		this.bpCustPersonEducation = bpCustPersonEducation;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		//根据个人客户ID查询出该客户所有的教育信息，返回字符串成功或失败
		int personId = Integer.valueOf(getRequest().getParameter("personId"));
		Person person = personService.getById(personId);
		List<BpCustPersonEducation> list = new ArrayList<BpCustPersonEducation>(person.getEducationInfos());
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		JSONSerializer json = JsonUtil.getJSONSerializer("educationStartTime","educationEndTime");
		json.transform(new DateTransformer("yyyy-MM-dd"), new String[] {"educationStartTime","educationEndTime"});
		buff.append(json.serialize(list));
		buff.append("}");
		jsonString = buff.toString();
		return SUCCESS;
	}
	/**
	 * 删除
	 */
	public String delete(){
		String[] ids = this.getRequest().getParameterValues("ids");
		if(null!=ids && ids.length>0){
			for(String id:ids){
				bpCustPersonEducationService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
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
				bpCustPersonEducationService.remove(new Long(id));
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
		BpCustPersonEducation bpCustPersonEducation=bpCustPersonEducationService.get(educationId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpCustPersonEducation));
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
			String educationInfos = request.getParameter("educationInfos");
			if(educationInfos!=null&&!"".equals(educationInfos)){
				String[] educationInfoArr = educationInfos.split("@");
				for (int i = 0; i < educationInfoArr.length; i++) {
					String str = educationInfoArr[i];
					JSONParser parser = new JSONParser(new StringReader(str));
					BpCustPersonEducation educationInfo = (BpCustPersonEducation) JSONMapper.toJava(parser.nextValue(), BpCustPersonEducation.class);
					if(null == educationInfo.getEducationId() || "".equals(educationInfo.getEducationId())){
						bpCustPersonEducationService.save(educationInfo);
					}else{
						BpCustPersonEducation temp = bpCustPersonEducationService.get(educationInfo.getEducationId());
						BeanUtil.copyNotNullProperties(temp, educationInfo);
						bpCustPersonEducationService.save(temp);
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
