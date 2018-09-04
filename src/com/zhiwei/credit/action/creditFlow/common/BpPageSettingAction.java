package com.zhiwei.credit.action.creditFlow.common;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.creditFlow.common.BpPageSetting;
import com.zhiwei.credit.service.creditFlow.common.BpPageSettingService;
/**
 * 
 * @author 
 *
 */
public class BpPageSettingAction extends BaseAction{
	@Resource
	private BpPageSettingService bpPageSettingService;
	private BpPageSetting bpPageSetting;
	
	private Long pageSetId;

	public Long getPageSetId() {
		return pageSetId;
	}

	public void setPageSetId(Long pageSetId) {
		this.pageSetId = pageSetId;
	}

	public BpPageSetting getBpPageSetting() {
		return bpPageSetting;
	}

	public void setBpPageSetting(BpPageSetting bpPageSetting) {
		this.bpPageSetting = bpPageSetting;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		try{
			StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'总分类',expanded:true,children:[");
			List<BpPageSetting> list =bpPageSettingService.listByParentId(new Long(0));
			for(BpPageSetting page:list){
				StringBuffer tempContent=new StringBuffer();
				if(null!=page.getPageContent() && !"".equals(null==page.getPageContent())){
					tempContent.append(page.getPageContent().replace("'", "\\'"));
				}else{
					tempContent.append(page.getPageContent());
				}
				buff.append("{id:'"+page.getPageSetId()).append("',text:'"+page.getPageName()).append("',");
				buff.append("pageKey:'" + page.getPageKey() +"',parentText:'总分类',pageContent:'"+tempContent+"',");
			    buff.append(getChildType(page.getPageSetId()));
			}
			
			if (!list.isEmpty()) {
				buff.deleteCharAt(buff.length() - 1);
		    }
			buff.append("]}]");
	
			setJsonString(buff.toString());
		}catch(Exception e){
			e.printStackTrace();
		}
		return SUCCESS;
	}
	public String getChildType(Long parentId){
		StringBuffer buff = new StringBuffer();
		List<BpPageSetting> list = bpPageSettingService.listByParentId(parentId);
		if(list.size() == 0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(BpPageSetting ps:list){
				StringBuffer tempContent=new StringBuffer();
				if(null!=ps.getPageContent() && !"".equals(null==ps.getPageContent())){
					tempContent.append(ps.getPageContent().replace("'", "\\'"));
				}else{
					tempContent.append(ps.getPageContent());
				}
				BpPageSetting s=bpPageSettingService.get(ps.getParentId());
				buff.append("{id:'"+ps.getPageSetId()).append("',text:'"+ps.getPageName()).append("',");
				try {
					buff.append("pageKey:'" + ps.getPageKey() + "',parentText:'"+s.getPageName()+"',pageContent:'"+tempContent+"',");
				} catch (Exception e) {
					e.printStackTrace();
				}
				
			    buff.append(getChildType(ps.getPageSetId()));
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	/**
	 * 批量删除
	 * @return
	 */
	public String multiDel(){
		
		
		bpPageSettingService.remove(pageSetId);
		
		
		jsonString="{success:true}";
		
		return SUCCESS;
	}
	
	/**
	 * 显示详细信息
	 * @return
	 */
	public String get(){
		BpPageSetting bpPageSetting=bpPageSettingService.get(pageSetId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bpPageSetting));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(bpPageSetting.getPageSetId()==null){
			bpPageSettingService.save(bpPageSetting);
		}else{
			BpPageSetting orgBpPageSetting=bpPageSettingService.get(bpPageSetting.getPageSetId());
			try{
				BeanUtil.copyNotNullProperties(orgBpPageSetting, bpPageSetting);
				bpPageSettingService.save(orgBpPageSetting);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	public String update(){
		try{
			String pageSettingId=this.getRequest().getParameter("pageSettingId");
			String pageContent=this.getRequest().getParameter("pageContent");
			if(null!=pageSettingId && !pageSettingId.equals("")){
				BpPageSetting orgBpPageSetting=bpPageSettingService.get(new Long(pageSettingId));
				orgBpPageSetting.setPageContent(replaceBlank(pageContent));
				bpPageSettingService.merge(orgBpPageSetting);
			}
			jsonString="{success:true}";
		}catch(Exception e){
			jsonString="{success:false}";
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	public  String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	/**
	 * 查询申请类型
	 * @return
	 */
	public String getApplytypeList(){
		QueryFilter filter=new QueryFilter(this.getRequest());
		filter.addFilter("Q_parentId_L_NEQ", "0");
		List<BpPageSetting> list = bpPageSettingService.getAll(filter);
		if(null!=list && list.size()>0){
			StringBuffer buff = new StringBuffer("[");
			for (BpPageSetting glType : list) {
				buff.append("[").append(glType.getPageSetId()).append(",'")
						.append(glType.getPageName()).append("'],");
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]");
			setJsonString(buff.toString());
		}
		return SUCCESS;
	}
}
