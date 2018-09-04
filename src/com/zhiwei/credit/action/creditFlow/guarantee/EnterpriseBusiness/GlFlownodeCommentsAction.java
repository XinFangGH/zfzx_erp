package com.zhiwei.credit.action.creditFlow.guarantee.EnterpriseBusiness;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeComments;
import com.zhiwei.credit.model.flow.ProcessForm;
import com.zhiwei.credit.model.flow.TaskSignData;
import com.zhiwei.credit.service.creditFlow.guarantee.EnterpriseBusiness.GlFlownodeCommentsService;
import com.zhiwei.credit.service.flow.ProcessFormService;
import com.zhiwei.credit.service.flow.TaskSignDataService;
/**
 * 
 * @author 
 *
 */
public class GlFlownodeCommentsAction extends BaseAction{
	@Resource
	private GlFlownodeCommentsService glFlownodeCommentsService;
	@Resource
	private ProcessFormService processFormService;
	@Resource
	private TaskSignDataService taskSignDataService;
	
	private GlFlownodeComments glFlownodeComments;
	
	private Long zmId;
	private String zmTaskId;
	private Long dataId;
	private String feedbackComments;
	
	
	public String updateInfo(){
		ProcessForm processForm = processFormService.getByTaskId(zmTaskId);
		String comments = getRequest().getParameter("comments");
		if(processForm!=null){
			if(glFlownodeComments.getZmId()==null){
				glFlownodeComments.setFormId(processForm.getFormId());
				glFlownodeComments.setRunId(processForm.getRunId());
				glFlownodeCommentsService.save(glFlownodeComments);
			}else{
				GlFlownodeComments orgGlFlownodeComments=glFlownodeCommentsService.get(glFlownodeComments.getZmId());
				try{
					BeanUtil.copyNotNullProperties(orgGlFlownodeComments, glFlownodeComments);
					glFlownodeCommentsService.save(orgGlFlownodeComments);
				}catch(Exception ex){
					ex.printStackTrace();
					logger.error("审保会集体决议保存意见信息出错："+ex.getMessage());
				}
			}
			processForm.setComments(comments);
			processFormService.merge(processForm);
		}
		setJsonString("{success:true}");
		return SUCCESS;
		/*String premiumRateComments = getRequest().getParameter("premiumRateComments");
		String mortgageComments = getRequest().getParameter("mortgageComments");
		String assureTimeLimitComments = getRequest().getParameter("assureTimeLimitComments");
		String comments = getRequest().getParameter("comments");
		
		ProcessForm processForm = processFormService.getByTaskId(zmTaskId);
		if(processForm!=null){
			GlFlownodeComments flow = glFlownodeCommentsService.getByFormId(processForm.getFormId());
			if(flow==null){
				GlFlownodeComments flowNode = new GlFlownodeComments();
				flowNode.setFormId(processForm.getFormId());
				flowNode.setRunId(processForm.getRunId());
				flowNode.setAssureTimeLimitComments(assureTimeLimitComments);
				flowNode.setMortgageComments(mortgageComments);
				flowNode.setPremiumRateComments(premiumRateComments);
				
				glFlownodeCommentsService.save(flowNode);
			}else{
				flow.setAssureTimeLimitComments(assureTimeLimitComments);
				flow.setMortgageComments(mortgageComments);
				flow.setPremiumRateComments(premiumRateComments);
				
				glFlownodeCommentsService.merge(flow);
			}
			processForm.setComments(comments);
			processFormService.merge(processForm);
		}
		
		return SUCCESS;*/
	}
	
	public String update(){
		if(dataId!=null&&!"".equals(dataId)){
			TaskSignData taskSignData = taskSignDataService.get(dataId);
			Long formId = new Long(0);
			if(taskSignData!=null){
				//ProcessForm processForm = processFormService.getByRunIdFromTaskIdCreatorId(taskSignData.getRunId(), taskSignData.getFromTaskId(), taskSignData.getVoteId());
				ProcessForm processForm = null;
				if(taskSignData.getFormId()!=null&&!"".equals(taskSignData.getFormId())&&!"0".equals(taskSignData.getFormId().toString())){
					formId = taskSignData.getFormId();
				}else{
					processForm = processFormService.getByRunIdFromTaskIdCreatorId(taskSignData.getRunId(), taskSignData.getFromTaskId(), taskSignData.getVoteId());
					formId = processForm.getFormId();
				}
				if(processForm!=null){
					GlFlownodeComments com = glFlownodeCommentsService.getByFormId(formId);
					if(com!=null){
						com.setFeedbackComments(feedbackComments);
						glFlownodeCommentsService.merge(com);
					}
				}
			}
		}
		return SUCCESS;
	}
	

	public Long getZmId() {
		return zmId;
	}

	public void setZmId(Long zmId) {
		this.zmId = zmId;
	}

	public String getZmTaskId() {
		return zmTaskId;
	}

	public void setZmTaskId(String zmTaskId) {
		this.zmTaskId = zmTaskId;
	}

	public GlFlownodeComments getGlFlownodeComments() {
		return glFlownodeComments;
	}

	public void setGlFlownodeComments(GlFlownodeComments glFlownodeComments) {
		this.glFlownodeComments = glFlownodeComments;
	}

	public Long getDataId() {
		return dataId;
	}

	public void setDataId(Long dataId) {
		this.dataId = dataId;
	}

	public String getFeedbackComments() {
		return feedbackComments;
	}

	public void setFeedbackComments(String feedbackComments) {
		this.feedbackComments = feedbackComments;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<GlFlownodeComments> list= glFlownodeCommentsService.getAll(filter);
		
		Type type=new TypeToken<List<GlFlownodeComments>>(){}.getType();
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
				glFlownodeCommentsService.remove(new Long(id));
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
		GlFlownodeComments glFlownodeComments=glFlownodeCommentsService.get(zmId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(glFlownodeComments));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(glFlownodeComments.getZmId()==null){
			glFlownodeCommentsService.save(glFlownodeComments);
		}else{
			GlFlownodeComments orgGlFlownodeComments=glFlownodeCommentsService.get(glFlownodeComments.getZmId());
			try{
				BeanUtil.copyNotNullProperties(orgGlFlownodeComments, glFlownodeComments);
				glFlownodeCommentsService.save(orgGlFlownodeComments);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
}
