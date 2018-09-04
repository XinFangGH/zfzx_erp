package com.zhiwei.credit.action.creditFlow.finance;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.entity.Mail;
import com.zhiwei.credit.model.creditFlow.fileUploads.FileForm;
import com.zhiwei.credit.model.creditFlow.finance.FundsToPromote;
import com.zhiwei.credit.model.creditFlow.smallLoan.project.SlSmallloanProject;
import com.zhiwei.credit.model.customer.InvestPerson;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.service.creditFlow.fileUploads.FileFormService;
import com.zhiwei.credit.service.creditFlow.finance.FundsToPromoteService;
import com.zhiwei.credit.service.creditFlow.smallLoan.project.SlSmallloanProjectService;
import com.zhiwei.credit.service.customer.InvestPersonService;
/**
 * 
 * @author 
 *
 */
@SuppressWarnings("serial")
public class FundsToPromoteAction extends BaseAction{
	@Resource
	private FundsToPromoteService fundsToPromoteService;
	@Resource
	private InvestPersonService investPersonService;
	private FundsToPromote fundsToPromote;
	@Resource
	private FileFormService fileFormService;
	@Resource
	private SlSmallloanProjectService slSmallloanProjectService;
	private Long fundsToPromoteId;

	public Long getFundsToPromoteId() {
		return fundsToPromoteId;
	}

	public void setFundsToPromoteId(Long fundsToPromoteId) {
		this.fundsToPromoteId = fundsToPromoteId;
	}

	public FundsToPromote getFundsToPromote() {
		return fundsToPromote;
	}

	public void setFundsToPromote(FundsToPromote fundsToPromote) {
		this.fundsToPromote = fundsToPromote;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		String projectId=this.getRequest().getParameter("projectId");
		if(null!=projectId && !"".equals(projectId)){
			List<FundsToPromote> list= fundsToPromoteService.getListByProjectId(Long.valueOf(projectId));
			StringBuffer buff = new StringBuffer("{success:true,result:");
			Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
			buff.append(gson.toJson(list));
			buff.append("}");
			
			jsonString=buff.toString();
		}
		
		
		
		return SUCCESS;
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		if(fundsToPromoteId!=null){
			fundsToPromoteService.remove(fundsToPromoteId);
		}
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		FundsToPromote fundsToPromote=fundsToPromoteService.get(fundsToPromoteId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(fundsToPromote));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(fundsToPromote.getFundsToPromoteId()==null){
			fundsToPromoteService.save(fundsToPromote);
		}else{
			FundsToPromote orgFundsToPromote=fundsToPromoteService.get(fundsToPromote.getFundsToPromoteId());
			try{
				BeanUtil.copyNotNullProperties(orgFundsToPromote, fundsToPromote);
				fundsToPromoteService.save(orgFundsToPromote);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	@SuppressWarnings("deprecation")
	public String saveInfo() throws Exception{
		Mail cn = new Mail();
		List<String> tos = new ArrayList<String>();  //收件人地址
		String promoteids = this.getRequest().getParameter("promoteids");
		
		AppUser user=ContextUtil.getCurrentUser();
		String investPersonIds = this.getRequest().getParameter("investPersonIds");
		String[] invIds = investPersonIds.split(",");
		String investPersonNames = this.getRequest().getParameter("investPersonNames");
		FundsToPromote f=new FundsToPromote();
		f.setPromoteMonthod("邮件推介");
		f.setPromoteDate(new Date());
		f.setPromotePersonId(user.getUserId());
		f.setPromotePersonName(user.getFullname());
		//f.setInvestPersonId(Long.valueOf(invIds[i]));
		f.setInvestPersonName(investPersonNames);
		f.setTitle(fundsToPromote.getTitle());
		f.setPromoteContent(fundsToPromote.getPromoteContent());
		f.setProjectId(fundsToPromote.getProjectId());
		f.setFileId(fundsToPromote.getFileId());
		for(int i=0; i<invIds.length;i++){
			
			InvestPerson invest = investPersonService.get(Long.valueOf(invIds[i]));
			tos.add(invest.getPostCode());
			
		}
		if(promoteids!=null && !promoteids.trim().equals("")){
			String[] ids = promoteids.split(",");
			Map<String, String> affixs = new HashMap<String, String>();
			for(String fileid : ids){
				FileForm file = fileFormService.getFileForm(Integer.valueOf(fileid));
				affixs.put(file.getFilename(), ServletActionContext.getRequest().getRealPath("/")+file.getFilepath());
				FileForm file1 = new FileForm();
				file1.setArchiveConfirmRemark(file.getArchiveConfirmRemark());
				file1.setBusinessType(file.getBusinessType());
				file1.setContentType(file.getContentType());
				file1.setCreatetime(new Date());
				file1.setCreatorid(Integer.valueOf(user.getUserId().toString()));
				file1.setExtendname(file.getExtendname());
				file1.setFileAttachID(file.getFileAttachID());
				file1.setFilename(file.getFilename());
				file1.setFilepath(file.getFilepath());
				file1.setFilesize(file.getFilesize());
				file1.setIsArchiveConfirm(file.getIsArchiveConfirm());
				file1.setMark("FundsToPromote."+f.getFundsToPromoteId());
				file1.setProjId(file.getProjId());
				//file1.setRemark("FundsToPromote");
				file1.setSetname("已发邮件");
				file1.setWebPath(file.getWebPath());
				fileFormService.save(file1);
			}
			cn.setAffixs(affixs);
		}
		
		//List<FileForm> files = new ArrayList<FileForm>();
		
		fundsToPromoteService.save(f);
		
		
		// 设置发件人地址、收件人地址和邮件标题
		cn.setAddress("2259181125@qq.com", tos, fundsToPromote.getTitle());
		cn.setContent(fundsToPromote.getPromoteContent());
		// 设置要发送附件的位置和标题
		
		
		cn.send("smtp.qq.com", "2259181125@qq.com", "abcde123456");
		String projectId = this.getRequest().getParameter("projectId");
		SlSmallloanProject slSmallloanProject = slSmallloanProjectService.get(Long.valueOf(projectId));
		slSmallloanProject.setLastPresentDate(new Date());
		slSmallloanProjectService.merge(slSmallloanProject);
		return SUCCESS;
	}
}
