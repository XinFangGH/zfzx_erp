package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.util.List;

import javax.annotation.Resource;

import com.google.gson.Gson;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.BookSn;
import com.zhiwei.credit.service.admin.BookSnService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class BookSnAction extends BaseAction{
	@Resource
	private BookSnService bookSnService;
	private BookSn bookSn;
	
	private Long bookSnId;

	public Long getBookSnId() {
		return bookSnId;
	}

	public void setBookSnId(Long bookSnId) {
		this.bookSnId = bookSnId;
	}

	public BookSn getBookSn() {
		return bookSn;
	}

	public void setBookSn(BookSn bookSn) {
		this.bookSn = bookSn;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<BookSn> list= bookSnService.getAll(filter);
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		JSONSerializer serializer=new JSONSerializer();
		buff.append(serializer.exclude(new String[]{"class"}).serialize(list));
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
				bookSnService.remove(new Long(id));
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
		BookSn bookSn=bookSnService.get(bookSnId);
		Gson gson=new Gson();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(bookSn));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 通过bookId找到图书的boonSn
	 * @return
	 */
	public String getSn(){
		List<BookSn> list = null;
		bookSn = new BookSn();
		Long bookId = 0L;
		bookId = Long.valueOf(getRequest().getParameter("bookId"));
		list = bookSnService.findByBookId(bookId);
		StringBuffer buff = new StringBuffer("[");
		for(BookSn bookSn:list){
			buff.append("['"+bookSn.getBookSnId()+"','"+bookSn.getBookSN()+"'],");
		}
		if(list.size()!=0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 通过bookId找到还没有借出去的图书的boonSn
	 * @return
	 */
	public String getBorrowSn(){
		List<BookSn> list = null;
		bookSn = new BookSn();
		Long bookId = 0L;
		bookId = Long.valueOf(getRequest().getParameter("bookId"));
		list = bookSnService.findBorrowByBookId(bookId);
		StringBuffer buff = new StringBuffer("[");
		for(BookSn bookSn:list){
			buff.append("['"+bookSn.getBookSnId()+"','"+bookSn.getBookSN()+"'],");
		}
		if(list.size()!=0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 通过bookId找到已借出去还未归还的图书的boonSn
	 * @return
	 */
	public String getReturnSn(){
		List<BookSn> list = null;
		bookSn = new BookSn();
		Long bookId = 0L;
		bookId = Long.valueOf(getRequest().getParameter("bookId"));
		list = bookSnService.findReturnByBookId(bookId);
		StringBuffer buff = new StringBuffer("[");
		for(BookSn bookSn:list){
			buff.append("['"+bookSn.getBookSnId()+"','"+bookSn.getBookSN()+"'],");
		}
		if(list.size()!=0){
			buff.deleteCharAt(buff.length()-1);
		}
		buff.append("]");
		setJsonString(buff.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		bookSnService.save(bookSn);
		setJsonString("{success:true}");
		return SUCCESS;
	}
}
