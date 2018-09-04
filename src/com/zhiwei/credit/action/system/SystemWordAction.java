package com.zhiwei.credit.action.system;
/*
 *  北京互融时代软件有限公司   -- http://www.hurongtime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.system.SystemWord;
import com.zhiwei.credit.service.system.SystemWordService;
/**
 * 
 * @author 
 *
 */
public class SystemWordAction extends BaseAction{
	@Resource
	private SystemWordService systemWordService;
	private SystemWord systemWord;
	private String wordName;
	private String wordKey;
	private Long wordId;

	public Long getWordId() {
		return wordId;
	}

	public void setWordId(Long wordId) {
		this.wordId = wordId;
	}

	public SystemWord getSystemWord() {
		return systemWord;
	}

	public void setSystemWord(SystemWord systemWord) {
		this.systemWord = systemWord;
	}
	
	public String getWordName() {
		return wordName;
	}

	public void setWordName(String wordName) {
		this.wordName = wordName;
	}
	
	public String getWordKey() {
		return wordKey;
	}

	public void setWordKey(String wordKey) {
		this.wordKey = wordKey;
	}

	/**
	 * 产生树
	 * @return
	 */
	public String tree(){
		StringBuffer buff = new StringBuffer("[{id:'"+0+"',text:'总分类',expanded:true,children:[");
		List<SystemWord> wordList = systemWordService.getByParentId(Long.valueOf(0),null,true);
		for(SystemWord word:wordList){
			buff.append("{id:'"+word.getWordId()).append("',text:'"+word.getWordName()).append("',");
			buff.append("wordKey:'" + word.getWordKey() + "',");
			buff.append(getChild(word.getWordId()));
		}
		if (!wordList.isEmpty()) {
			buff.deleteCharAt(buff.length() - 1);
	    }
		buff.append("]}]");

		setJsonString(buff.toString());
		
		logger.info("tree json:" + buff.toString());		
		return SUCCESS;
	}
	
	public String getChild(Long parentId){
		StringBuffer buff = new StringBuffer();   
		List<SystemWord> wordList = systemWordService.getByParentId(parentId,null,true);
		if(wordList.size() == 0){
			buff.append("leaf:true,expanded:true},");
			return buff.toString(); 
		}else {
			buff.append("expanded:true,children:[");
			for(SystemWord word:wordList){
				if(wordKey.equals(word.getWordKey())) {
					buff.append("{id:'"+word.getWordId()).append("',text:'"+word.getWordName()).append("',");
					buff.append("wordKey:'" + word.getWordKey() + "',");
				    buff.append(getChild(word.getWordId()));
				}
			}
			buff.deleteCharAt(buff.length() - 1);
			buff.append("]},");
			return buff.toString();
		}
	}
	
	/**
	 * 显示词条列表
	 */
	public String sub(){
		
		Long parentId=null;
		String sParentId = systemWordService.getSystemWordByKey(wordKey).getWordId().toString();
		if(StringUtils.isNotEmpty(sParentId)){
			parentId=new Long(sParentId);
		}
		List<SystemWord> list=systemWordService.getByParentId(parentId, wordName, false);
		Type type=new TypeToken<List<SystemWord>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':").append(list.size()).append(",result:");
		
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
				systemWordService.remove(new Long(id));
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
		SystemWord systemWord=systemWordService.get(wordId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(systemWord));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(systemWord != null && systemWord.getWordId()!=null){
			SystemWord orgSystemWord=systemWordService.get(systemWord.getWordId());
			try{
				BeanUtil.copyNotNullProperties(orgSystemWord, systemWord);
				systemWordService.save(orgSystemWord);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}else{
			//缺省0代表父节点
			String parentPath="0.";
			Long level= new Long(1);
			Long wordId = new Long(1);
			SystemWord parentWord=systemWordService.get(systemWordService.getSystemWordByKey(wordKey).getWordId());
			if(parentWord != null){
				parentPath = parentWord.getPath();
				level = parentWord.getDepth() + 1;
				wordId = parentWord.getWordId();
			}
			systemWord.setDepth(level);
			systemWord.setParentId(wordId);
			systemWordService.save(systemWord);
			systemWord.setPath(parentPath+ systemWord.getWordId()+"." ) ;
			systemWordService.save(systemWord);
		}
		
		setJsonString("{success:true}");
		return SUCCESS;
	}
	
	public String getByKey() throws IOException{
		SystemWord systemWord=systemWordService.getSystemWordByKey(wordKey);
		if(systemWord != null) {
			ServletActionContext.getResponse().getWriter().write(systemWord.getWordDescription());
		}
		return null;
	}
}
