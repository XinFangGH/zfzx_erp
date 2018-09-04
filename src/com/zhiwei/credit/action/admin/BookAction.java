package com.zhiwei.credit.action.admin;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   --  http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.admin.Book;
import com.zhiwei.credit.model.admin.BookSn;
import com.zhiwei.credit.model.admin.BookType;
import com.zhiwei.credit.service.admin.BookService;
import com.zhiwei.credit.service.admin.BookSnService;
import com.zhiwei.credit.service.admin.BookTypeService;

import flexjson.JSONSerializer;
/**
 * 
 * @author csx
 *
 */
public class BookAction extends BaseAction{
	@Resource
	private BookService bookService;
	@Resource
	private BookTypeService bookTypeService;
	@Resource
	private BookSnService bookSnService;
	
	private Book book;
	
	private Long bookId;
	
	private Long typeId;
	private BookType bookType;

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

	public Long getBookId() {
		return bookId;
	}

	public void setBookId(Long bookId) {
		this.bookId = bookId;
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<Book> list= bookService.getAll(filter);
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
				bookService.remove(new Long(id));
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
		Book book=bookService.get(bookId);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=new JSONSerializer();
		sb.append(serializer.exclude(new String[]{"class"}).serialize(book));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		String bookSnNumber = "";
		//bookId为空，说明是新增加图书
			if(book.getBookId()==null){
				//添加图书时将未借出的数量设置为和图书数量一样
				book.setLeftAmount(book.getAmount());
				bookService.save(book);	
			for(int i=1;i<=book.getAmount();i++){
				//添加图书成功后，根据ISBN和图书数量自动生成每本图书SN号
				BookSn bookSn = new BookSn();
				//每本书的bookSn号根据书的ISBN号和数量自动生成,
				//如书的ISBN是123,数量是3,则每本书的bookSn分别是：123-1,123-2,123-3
				bookSnNumber=book.getIsbn()+"-"+i;
				bookSn.setBookId(book.getBookId());
				bookSn.setBookSN(bookSnNumber);
				//默认书的状态是0表示未借出
				bookSn.setStatus(new Short((short) 0));
				//添加bookSn信息
				bookSnService.save(bookSn);
			}
			}else{
				bookService.save(book);	
			}
		setJsonString("{success:true,bookSnNumber:'"+bookSnNumber+"'}");
		return SUCCESS;
	}
	/**
	 * 增加图书数量时修改图书数量
	 */
	public String updateAmount(){
		Long bookId = Long.valueOf(getRequest().getParameter("bookId"));
		book = bookService.get(bookId);
		int addAmount = Integer.parseInt(getRequest().getParameter("addAmount"));
		int amount = book.getAmount()+addAmount;
		BookSn bookSn = null;
		String bookSnNumber = "";
			for(int i=book.getAmount()+1;i<=book.getAmount()+addAmount;i++){
				//添加图书成功后，根据ISBN和图书数量自动生成每本图书SN号
				 bookSn = new BookSn();
				//每本书的bookSn号根据书的ISBN号和数量自动生成,
				//如书的ISBN是123,数量是3,则每本书的bookSn分别是：123-1,123-2,123-3
				bookSnNumber=book.getIsbn()+"-"+i;
				bookSn.setBookId(book.getBookId());
				bookSn.setBookSN(bookSnNumber);
				//默认书的状态是0表示未借出
				bookSn.setStatus(new Short((short) 0));
				//添加bookSn信息
				bookSnService.save(bookSn);
			}
			book.setAmount(amount);
			book.setLeftAmount(book.getLeftAmount()+addAmount);
			bookService.save(book);	
			StringBuffer sb = new StringBuffer("{success:true,data:");
			JSONSerializer serializer=new JSONSerializer();
			sb.append(serializer.exclude(new String[]{"class"}).serialize(book));
			sb.append("}");
			setJsonString(sb.toString());
			return SUCCESS;
	}
	/**
	 * 删除图书标签后修改图书数量和未借出数量
	 */
	public String updateAmountAndLeftAmount(){
		Long bookId = Long.valueOf(getRequest().getParameter("bookId"));
		book = bookService.get(bookId);
		int amount = book.getAmount()-1;
		int leftAmount = book.getLeftAmount()-1;
		book.setAmount(amount);
		book.setLeftAmount(leftAmount);
		bookService.save(book);
		StringBuffer sb = new StringBuffer("{success:true,data:");
		JSONSerializer serializer=new JSONSerializer();
		sb.append(serializer.exclude(new String[]{"class"}).serialize(book));
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
	}
}
