package com.zhiwei.credit.action.flow;
/*
 *  北京互融时代软件有限公司    -- http://www.hurongtime.com
 *  Copyright (C)  JinZhi WanWei Software Limited Company.
*/
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.htmlparser.NodeFilter;
import org.htmlparser.Parser;
import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.filters.OrFilter;
import org.htmlparser.tags.InputTag;
import org.htmlparser.tags.OptionTag;
import org.htmlparser.tags.SelectTag;
import org.htmlparser.tags.TextareaTag;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.lang.reflect.Type;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.util.BeanUtil;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;


import com.zhiwei.credit.model.flow.FormField;
import com.zhiwei.credit.service.flow.FormFieldService;
/**
 * 
 * @author 
 *
 */
public class FormFieldAction extends BaseAction{
	@Resource
	private FormFieldService formFieldService;
	private FormField formField;
	
	private Long fieldId;

	public Long getFieldId() {
		return fieldId;
	}

	public void setFieldId(Long fieldId) {
		this.fieldId = fieldId;
	}

	public FormField getFormField() {
		return formField;
	}

	public void setFormField(FormField formField) {
		this.formField = formField;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<FormField> list= formFieldService.getAll(filter);
		
		Type type=new TypeToken<List<FormField>>(){}.getType();
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
				formFieldService.remove(new Long(id));
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
		FormField formField=formFieldService.get(fieldId);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(formField));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		if(formField.getFieldId()==null){
			formFieldService.save(formField);
		}else{
			FormField orgFormField=formFieldService.get(formField.getFieldId());
			try{
				BeanUtil.copyNotNullProperties(orgFormField, formField);
				formFieldService.save(orgFormField);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	/**
	 * 根据页面传过来的HTML来解析里面FIELDS         
	 * @return
	 */
	//no use
	public String getFields() {

		String html = getRequest().getParameter("htmlCode");
		List<FormField> flist=new ArrayList<FormField>();
		
		try {
			NodeFilter inputFilter = new NodeClassFilter(InputTag.class);
			NodeFilter selectFilter = new NodeClassFilter(SelectTag.class);
			NodeFilter textareaFilter = new NodeClassFilter(TextareaTag.class);
			NodeList nodeList = null;

			
			Parser parser = new Parser();
			parser.setInputHTML(html);

			parser.setEncoding(parser.getEncoding());
			OrFilter lastFilter = new OrFilter();
			lastFilter.setPredicates(new NodeFilter[] { selectFilter,
					inputFilter,textareaFilter });
			nodeList = parser.parse(lastFilter);
			for (int i = 0; i <= nodeList.size(); i++) {
				if (nodeList.elementAt(i) instanceof InputTag) {//输入框,checkbox,radio
					InputTag tag = (InputTag) nodeList.elementAt(i);
					if(!tag.getAttribute("type").toUpperCase().equals("BUTTON")){
						FormField field=new FormField();
						field.setFieldDscp("");
						field.setFieldName(tag.getAttribute("name"));
						int size=64;
						String size2=tag.getAttribute("txtsize");
						if(StringUtils.isNotEmpty(size2)){
							size=new Integer(size2);
						}
						field.setFieldSize(size);
						field.setFieldType(tag.getAttribute("txtvalueType"));
						field.setIsRequired(new Short("1".equals(tag.getAttribute("txtisnotnull"))?"1":"0"));
						field.setIsPrimary((short)0);
						String format=tag.getAttribute("dateFormat");
						if(StringUtils.isNotEmpty(format)){
							field.setShowFormat(format);
						}
						field.setIsList((short)1);
						field.setIsQuery((short)1);
						field.setForeignTable("");
						flist.add(field);
					}
				}
				if (nodeList.elementAt(i) instanceof SelectTag) {//下拉选择
					SelectTag tag = (SelectTag) nodeList.elementAt(i);
					FormField field=new FormField();
					field.setFieldDscp("");
					field.setFieldName(tag.getAttribute("name"));
					int size=128;
					String size2=tag.getAttribute("txtsize");
					if(StringUtils.isNotEmpty(size2)){
						size=new Integer(size2);
					}
					field.setFieldSize(size);
					field.setFieldType(tag.getAttribute("txtvaluetype"));
					field.setIsList((short)1);
					field.setIsRequired((short)0);
					field.setIsPrimary((short)0);
					field.setIsQuery((short)1);
					field.setForeignTable("");
					flist.add(field);
					
				}
				
				
				if (nodeList.elementAt(i) instanceof TextareaTag) {
					TextareaTag tag = (TextareaTag)nodeList.elementAt(i);
					FormField field=new FormField();
					field.setFieldDscp("");
					field.setFieldName(tag.getAttribute("name"));
					int size=128;
					String size2=tag.getAttribute("txtsize");
					if(StringUtils.isNotEmpty(size2)){
						size=new Integer(size2);
					}
					field.setFieldSize(size);
					field.setFieldType(tag.getAttribute("txtvaluetype"));
					field.setIsList((short)1);
					field.setIsRequired((short)0);
					field.setIsPrimary((short)0);
					field.setIsQuery((short)1);
					field.setForeignTable("");
					flist.add(field);
				}
				
			}

		} catch (ParserException e) {
			e.printStackTrace();
		}
		Gson gson=new Gson();
		StringBuffer sb=new StringBuffer("{success:true,fields:");
		sb.append(gson.toJson(flist));
		sb.append("}");
		setJsonString(sb.toString());

		return SUCCESS;

	}
}
