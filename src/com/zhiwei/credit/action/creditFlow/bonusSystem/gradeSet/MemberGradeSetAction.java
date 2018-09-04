package com.zhiwei.credit.action.creditFlow.bonusSystem.gradeSet;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.core.creditUtils.JsonUtil;
import com.zhiwei.credit.model.creditFlow.bonusSystem.gradeSet.MemberGradeSet;
import com.zhiwei.credit.service.creditFlow.bonusSystem.gradeSet.MemberGradeSetService;

import flexjson.JSONSerializer;

/**
 * 会员等级设置Action
 * @author songwenjie
 *
 */
public class MemberGradeSetAction extends BaseAction {
	
	@Resource
	private MemberGradeSetService memberGradeSetService;

	private MemberGradeSet memberGradeSet;
	
	private Long levelId;//声明一个值作为单项查询会员等级的条件条件

	/**
	 * 会员等级设置页面首次加载时，查询列表数据
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<MemberGradeSet> list= memberGradeSetService.getAll(filter);
		Type type=new TypeToken<List<MemberGradeSet>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	/**
	 * 会员等级设置添加及保存操作
	 */
	public String save(){
		String msg="";
		if(memberGradeSet.getLevelId() == null){//直接保存
			MemberGradeSet reMemberGradeSet = memberGradeSetService.findBylevelName(memberGradeSet.getLevelName());
			if(reMemberGradeSet==null){
				memberGradeSetService.save(memberGradeSet);
				msg = "添加成功";
			}else{
				msg = "添加的等级重复";
			}
		}
		else{//数据更新
			try{
				MemberGradeSet orgWebBonusRecord=memberGradeSetService.get(memberGradeSet.getLevelId());
				memberGradeSetService.evict(orgWebBonusRecord);
				BeanUtil.copyNotNullProperties(orgWebBonusRecord, memberGradeSet);
				MemberGradeSet reMemberGradeSet = memberGradeSetService.findBylevelName(memberGradeSet.getLevelName());
				if(reMemberGradeSet==null){
					memberGradeSetService.save(orgWebBonusRecord);
					msg = "修改成功";
				}else if(reMemberGradeSet!=null&&(reMemberGradeSet.getLevelId().compareTo(orgWebBonusRecord.getLevelId())==0)){
					memberGradeSetService.merge(orgWebBonusRecord);
					msg = "修改成功";
				}else{
					msg = "修改的等级重复";
				}
			}catch(Exception ex){
				ex.printStackTrace();
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true,msg:\""+msg+"\"}");
		return SUCCESS;
	}
	
	/**
	 * 单条查询会员等级设置的信息
	 * @return
	 */
	public String find(){
		Map<String, Object> map = new HashMap<String, Object>();
		MemberGradeSet memberGradeSet=memberGradeSetService.get(levelId);
		
		map.put("memberGradeSet", memberGradeSet);
		StringBuffer buff = new StringBuffer("{success:true,data:");
		JSONSerializer json = com.zhiwei.core.util.JsonUtil.getJSONSerializerDateByFormate("yyyy-MM-dd");
		buff.append(json.serialize(map));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	
	public void findTree() {
		StringBuffer json = new StringBuffer("[");
		List<?> list =memberGradeSetService.getAll();
		for(int i=0;i<list.size();i++){
			MemberGradeSet dic=(MemberGradeSet)list.get(i);
			json.append("[").append(dic.getLevelId()).append(",\"").append(dic.getLevelName()).append("\"],");
		}
		try {
			JsonUtil.responseJsonString(json.toString().substring(0, json.toString().length()-1)+"]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	
	
	/**
	 * 批量删除会员等级设置数据
	 * @return
	 */
	public String multiDel(){
		String[]ids=getRequest().getParameterValues("ids");
		if(ids!=null){
			for(String id:ids){
				memberGradeSetService.remove(new Long(id));
			}
		}
		jsonString="{success:true}";
		return SUCCESS;
	}
	
	
	/**
	 * 加载下拉选项
	 * @return
	 */
	public String jsonArr(){
		List<MemberGradeSet> list = memberGradeSetService.getAll();
		
		StringBuffer buff = new StringBuffer("[[0,'无'],");
		for(MemberGradeSet m:list){
			buff.append("[").append(m.getLevelId()).append(",'")
			.append(m.getLevelName()).append("'],");
		}
		if (list.size() > 0) {
			buff.deleteCharAt(buff.length() - 1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	
	}
	
	
	
	
	public Long getLevelId() {
		return levelId;
	}

	public void setLevelId(Long levelId) {
		this.levelId = levelId;
	}
	
	public MemberGradeSetService getMemberGradeSetService() {
		return memberGradeSetService;
	}

	public void setMemberGradeSetService(MemberGradeSetService memberGradeSetService) {
		this.memberGradeSetService = memberGradeSetService;
	}

	public MemberGradeSet getMemberGradeSet() {
		return memberGradeSet;
	}
	
	public void setMemberGradeSet(MemberGradeSet memberGradeSet) {
		this.memberGradeSet = memberGradeSet;
	}

}
