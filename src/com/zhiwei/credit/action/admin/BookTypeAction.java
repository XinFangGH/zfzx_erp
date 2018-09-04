package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.lang.reflect.Type;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.Book;
import com.zhiwei.credit.model.admin.BookType;
import com.zhiwei.credit.service.admin.BookService;
import com.zhiwei.credit.service.admin.BookTypeService;
/**
 * 
 * @author csx
 *
 */
public class BookTypeAction extends BaseAction{
	@Resource
	private BookTypeService bookTypeService;
	private BookType bookType;
	@Resource
	private BookService bookService;
	
	private Long typeId;

	public Long getTypeId() {
		return typeId;
	}

	public void setTypeId(Long typeId) {
		this.typeId = typeId;
	}

	public BookType getBookType() {
		return bookType;
	}

	public void setBookType(BookType bookType) {
		this.bookType = bookType;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BookType> list= bookTypeService.getAll(filter);
		Type type=new TypeToken<List<BookType>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		Gson gson=new Gson();
		buff.append(gson.toJson(list, type));
		buff.append("}");
		jsonString=buff.toString();
		return SUCCESS;
	}
	/**
	 * 显示图书类别下拉选框
	 * @return
	 */
	public String tree(){
		String method=getRequest().getParameter("method");
		List<BookType> list=bookTypeService.getAll();
		StringBuffer sb=new StringBuffer();
		int i=0;
		if(StringUtils.isNotEmpty(method)){
			sb.append("[");
		}else{
			i++;
			sb.append("[{id:'"+0+"',text:'图书类别',expanded:true,children:[");
		}
		for(BookType bookType:list){
			sb.append("{id:'"+bookType.getTypeId()+"',text:'"+bookType.getTypeName()+"',leaf:true},");
		}
		if(list.size()>0){
			sb.deleteCharAt(sb.length()-1);
		}
		if(i==0){
			sb.append("]");
		}else{
			sb.append("]}]");
		}
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 删除图书类别
	 * @return
	 */
	public String remove(){
		Long typeId = Long.valueOf(getRequest().getParameter("typeId"));
		setBookType(bookTypeService.get(typeId));
		if(bookType!=null){
			QueryFilter filter=new QueryFilter(getRequest());
			filter.addFilter("Q_bookType.typeId_L_EQ",typeId.toString());
			List<Book> list=bookService.getAll(filter);
			if(list.size()>0){
				jsonString="{success:false,message:'该类型下还有图书，请将图书移走后再删除！'}";
				return SUCCESS;
			}
			bookTypeService.remove(typeId);
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
				QueryFilter filter=new QueryFilter(getRequest());
				filter.addFilter("Q_bookType.typeId_L_EQ",id);
				List<Book> list=bookService.getAll(filter);
				if(list.size()>0){
					jsonString="{success:false,message:'该类型下还有图书，请将图书移走后再删除！'}";
					return SUCCESS;
				}
				bookTypeService.remove(new Long(id));
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
		BookType bookType=bookTypeService.get(typeId);
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bookType));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		bookTypeService.save(bookType);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
