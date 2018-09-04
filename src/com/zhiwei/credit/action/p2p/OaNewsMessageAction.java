package com.zhiwei.credit.action.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import org.apache.commons.lang.time.DateUtils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.zhiwei.core.command.QueryFilter;
import com.zhiwei.core.util.BeanUtil;
import com.zhiwei.core.util.ContextUtil;
import com.zhiwei.core.web.action.BaseAction;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.OaNewsMessage;
import com.zhiwei.credit.model.system.AppUser;
import com.zhiwei.credit.model.system.Dictionary;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.OaNewsMessageService;
import com.zhiwei.credit.service.system.DictionaryService;
/**
 * 
 * @author 
 *
 */
public class OaNewsMessageAction extends BaseAction{
	@Resource
	private OaNewsMessageService oaNewsMessageService;
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private DictionaryService dictionaryService;
	private OaNewsMessage oaNewsMessage;
	private String title;//标题
	private String content;//内容
	private java.util.Date sendTime;//发送时间
	private Long recipient;//接收人(用户id)
	private String operator;//操作人
	private String addresser;//发件人（全名）
	private String status;//状态：0未发送，1已发送
	private java.util.Date readTime;//阅读时间 
	private String isDelete;//状态：0未删除，1已删除
	
	
	
	
	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public java.util.Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(java.util.Date sendTime) {
		this.sendTime = sendTime;
	}

	public Long getRecipient() {
		return recipient;
	}

	public void setRecipient(Long recipient) {
		this.recipient = recipient;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getAddresser() {
		return addresser;
	}

	public void setAddresser(String addresser) {
		this.addresser = addresser;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public java.util.Date getReadTime() {
		return readTime;
	}

	public void setReadTime(java.util.Date readTime) {
		this.readTime = readTime;
	}
	private Long id;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public OaNewsMessage getOaNewsMessage() {
		return oaNewsMessage;
	}

	public void setOaNewsMessage(OaNewsMessage oaNewsMessage) {
		this.oaNewsMessage = oaNewsMessage;
	}

	/**
	 * 显示列表
	 */
	public String list(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		QueryFilter filter=new QueryFilter(getRequest());
		if(title!=null&&!"".equals(title)){
			filter.addFilter("Q_title_S_LK", title);
		}
		if(content!=null&&!"".equals(content)){
			filter.addFilter("Q_content_S_LK", content);
		}
		if(sendTime!=null&&!"".equals(sendTime)){
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd"); 
			String time=sdf.format(sendTime); 
			System.out.println("time="+time);
			filter.addFilter("Q_sendTime_DL_GE", time);
			filter.addFilter("Q_sendTime_DG_LE", time);
		}
		if(operator!=null&&!"".equals(operator)){
			filter.addFilter("Q_operator_S_LK", operator);
		}
		if(addresser!=null&&!"".equals(addresser)){
			filter.addFilter("Q_addresser_S_LK", addresser);
		}
		if(status!=null&&!"".equals(status)){
			filter.addFilter("Q_status_S_EQ", status);
		}
		if(isDelete!=null&&!"".equals(isDelete)){
			filter.addFilter("Q_isDelete_S_EQ", isDelete);
		}
		filter.addSorted("sendTime", "Desc");
		List<OaNewsMessage> list1= oaNewsMessageService.getAll(filter);
		List<OaNewsMessage> list = new ArrayList<OaNewsMessage>();
		for(int i=0;i<list1.size();i++){
			OaNewsMessage oa = list1.get(i);
			if(oa.getIsDelete()==null||!oa.getIsDelete().equals("1")){
				if(oa.getRecipient()!=null){
					BpCustMember bp = bpCustMemberService.get(oa.getRecipient());
					if(null!=bp  && !"".equals(bp)){
					oa.setComment1(bp.getLoginname());
					}
				}
				list.add(oa);
			}
		}
		Type type=new TypeToken<List<OaNewsMessage>>(){}.getType();
		StringBuffer buff = new StringBuffer("{success:true,'totalCounts':")
		.append(filter.getPagingBean().getTotalItems()).append(",result:");
		buff.append(gson.toJson(list, type));
		buff.append("}");
		
		jsonString=buff.toString();
	//	System.out.println(jsonString);
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
				oaNewsMessageService.remove(new Long(id));
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
		OaNewsMessage oaNewsMessage=oaNewsMessageService.get(id);
		
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		sb.append(gson.toJson(oaNewsMessage));
		sb.append("}");
		setJsonString(sb.toString());
		
		return SUCCESS;
	}
	/**
	 * 添加及保存操作
	 */
	public String save(){
		//oaNewsMessage.setRecipient(new Long(0));
		String decription = null;
		if(oaNewsMessage.getType()!=null){
			Dictionary dictionary = dictionaryService.get(new Long(oaNewsMessage.getType()));
			decription = dictionary.getItemValue();
		}
		oaNewsMessage.setStatus("0");
		oaNewsMessage.setTypename(decription);
		AppUser user = ContextUtil.getCurrentUser();
		if(oaNewsMessage.getId()==null){
			oaNewsMessage.setOperator(user.getFullname());
			oaNewsMessageService.save(oaNewsMessage);
		}else{
			OaNewsMessage orgOaNewsMessage=oaNewsMessageService.get(oaNewsMessage.getId());
			try{
				oaNewsMessage.setOperator(user.getFullname());
				BeanUtil.copyNotNullProperties(orgOaNewsMessage, oaNewsMessage);
				oaNewsMessageService.save(orgOaNewsMessage);
			}catch(Exception ex){
				logger.error(ex.getMessage());
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	/**
	 * 向特定用户发送站内信
	 */
	public String sendSomeUserMessage(){
		AppUser user = ContextUtil.getCurrentUser();
		String decription = null;
		if(oaNewsMessage.getType()!=null){
			Dictionary dictionary = dictionaryService.get(new Long(oaNewsMessage.getType()));
			if(dictionary!=null){
				decription = dictionary.getItemValue();
			}
		}
		oaNewsMessage.setTypename(decription);
		if(oaNewsMessage.getId()==null){
			String commen=oaNewsMessage.getComment2();
			if(commen!=null&&!"".equals(commen)){
				String[] userids = commen.split(",");
				for(int i=0;i<userids.length;i++){
					Long userid = new Long(userids[i]);
					OaNewsMessage oa = new OaNewsMessage();
					oa.setStatus("0");
					oa.setTitle(oaNewsMessage.getTitle());//标题
					oa.setType(oaNewsMessage.getType());//类别
					oa.setTypename(oaNewsMessage.getTypename());//类别名称
					oa.setContent(oaNewsMessage.getContent());//内容
					oa.setAddresser(user.getFullname());//发送人
					oa.setSendTime(new Date());
					if(userid!=null&&!userid.equals("")){
						oa.setRecipient(userid);//接收人
					}
					oaNewsMessageService.save(oa);
				}
			}
		}else{
			OaNewsMessage orgOaNewsMessage=oaNewsMessageService.get(oaNewsMessage.getId());
			String commen=oaNewsMessage.getComment2();
			String[] userids = commen.split(",");
			for(int i=0;i<userids.length;i++){
				Long userid = new Long(userids[i]);
				OaNewsMessage oa = new OaNewsMessage();
				oa.setTitle(orgOaNewsMessage.getTitle());
				oa.setType(oaNewsMessage.getType());//类别
				oa.setTypename(oaNewsMessage.getTypename());//类别名称
				oa.setContent(orgOaNewsMessage.getContent());
				oa.setAddresser(user.getUsername());//发送人
				oa.setSendTime(new Date());
				oa.setRecipient(userid);//接收人
				oaNewsMessageService.save(oa);
			}
		}
		setJsonString("{success:true}");
		return SUCCESS;
		
	}

	
	/**
	 * 显示列表
	 */
	public String getUserAll(){
		
		
		
		QueryFilter filter=new QueryFilter(getRequest());
		List<OaNewsMessage> list= oaNewsMessageService.getUserAll(recipient);//获取单个用户的所有站内信
		
	
	
		
		return SUCCESS;
	}
	
	
	
	
	
	/**
	 * 是否删除（假删除）单个或批量
	 * pll
	 */
	public String isDelete(){
		
		String[]ids=getRequest().getParameterValues("id");
		if(ids!=null){
			for(String id:ids){
				OaNewsMessage orgOaNewsMessage=oaNewsMessageService.get(new Long(id));
				try{
					orgOaNewsMessage.setIsDelete("1");//0为未删除，1为已删除
					oaNewsMessageService.merge(orgOaNewsMessage);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
			}
		}
			
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	
	/**
	 * 是否已读
	 * pll
	 */
	public String isReady(){
		if(id!=null){
				OaNewsMessage orgOaNewsMessage=oaNewsMessageService.get(id);
				try{
					orgOaNewsMessage.setReadTime(new Date());
					orgOaNewsMessage.setStatus("1");//已读状态 0未读  1已读
					oaNewsMessageService.merge(orgOaNewsMessage);
				}catch(Exception ex){
					logger.error(ex.getMessage());
				}
		}	
		setJsonString("{success:true}");
		return SUCCESS;
		
	}
	
	
	/**
	 * 向所有用户发送站内信
	 */
	public String sendAllUser(){
		Gson gson=new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,data:");
		
		String title = getRequest().getParameter("title");
		String content = getRequest().getParameter("content");
		String type = getRequest().getParameter("type");
		String typename = getRequest().getParameter("typename");
		OaNewsMessage orgOaNewsMessage= new OaNewsMessage();
		AppUser user = ContextUtil.getCurrentUser();
		try{
			//当前登录用户（操作人）
			orgOaNewsMessage.setTitle(title);//标题
			orgOaNewsMessage.setContent(content);//内容
			orgOaNewsMessage.setType(type);
			orgOaNewsMessage.setTypename(typename);
			orgOaNewsMessage.setAddresser(user.getFullname());//发送人
			//orgOaNewsMessage.setOperator(operator);//操作人
			//orgOaNewsMessage.setAddresser(addresser);//发送人（全名）
			//orgOaNewsMessage.setSendTime(new Date());//发送时间
			//orgOaNewsMessage.setStatus("1");//已读状态 0未读  1已读
			oaNewsMessageService.sendAllUser(orgOaNewsMessage);
			sb.append(gson.toJson(orgOaNewsMessage));
			sb.append(",\"result\":1");
			sb.append("}");
		}catch(Exception ex){
			logger.error(ex.getMessage());
		}
	
		setJsonString(sb.toString());
		return SUCCESS;
		
	}
	
	/**
	 * 新发送站内信方法，将具体收件人收到的站内信存放在一张新表里面
	 * 同时具备发送个别人以及全部的功能及重新发送的功能
	 * add by linyan
	 * @return
	 */
	public String newsendSomeUserMessage(){
		try {
			AppUser user = ContextUtil.getCurrentUser();
			String decription = null;
			if(oaNewsMessage.getType()!=null){
				Dictionary dictionary = dictionaryService.get(new Long(oaNewsMessage.getType()));
				if(dictionary!=null){
					decription = dictionary.getItemValue();
				}
			}
			oaNewsMessage.setTypename(decription);
			if(oaNewsMessage.getId()==null){
				oaNewsMessage.setOperator(user.getFullname());
				oaNewsMessage=oaNewsMessageService.save(oaNewsMessage);
			}else{
				oaNewsMessage.setOperator(user.getFullname());
				OaNewsMessage orgOaNewsMessage=oaNewsMessageService.get(oaNewsMessage.getId());
				BeanUtil.copyNotNullProperties(orgOaNewsMessage, oaNewsMessage);
				oaNewsMessage=oaNewsMessageService.save(orgOaNewsMessage);
			}
			String commen=oaNewsMessage.getComment2();
			Boolean flag=false;//表示是否全部发送成功
			if(commen!=null&&commen.equals("_ALL")){//表示收件人为全部人员  目前默认全部收件人为P2P可登陆用户
				 flag=oaNewsMessageService.sendAllUserMessage(oaNewsMessage);
			}else if(commen!=null&&!"".equals(commen)){//根据收件人来分发站内信  目前默认全部收件人为P2P可登陆用户
				 flag=oaNewsMessageService.sendsomeUserMessage(oaNewsMessage);
			}
			if(flag){
			//	oaNewsMessage.setOperator(user.getFullname());
				oaNewsMessage.setAddresser(user.getFullname());
				oaNewsMessage.setSendTime(new Date());
				oaNewsMessage.setStatus("1");
				oaNewsMessageService.save(oaNewsMessage);
			}
			setJsonString("{success:true}");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return SUCCESS;
		
		
	}
	
	/**
	 * 向所有用户发送站内信
	 */
	public String newsend(){
		//将数据转成JSON格式
		StringBuffer sb = new StringBuffer("{success:true,msg:'");
		String msg="";
		String id = getRequest().getParameter("id");
		try{
			if(id!=null&&!id.equals("")){
				AppUser user = ContextUtil.getCurrentUser();
				OaNewsMessage orgOaNewsMessage=oaNewsMessageService.get(Long.valueOf(id));
				if(orgOaNewsMessage!=null){
					String commen=orgOaNewsMessage.getComment2();
					Boolean flag=false;//表示是否全部发送成功
					if(commen!=null&&commen.equals("_ALL")){//表示收件人为全部人员  目前默认全部收件人为P2P可登陆用户
						 flag=oaNewsMessageService.sendAllUserMessage(orgOaNewsMessage);
					}else if(commen!=null&&!"".equals(commen)){//根据收件人来分发站内信  目前默认全部收件人为P2P可登陆用户
						 flag=oaNewsMessageService.sendsomeUserMessage(orgOaNewsMessage);
					}
					if(flag){
					//	orgOaNewsMessage.setOperator(user.getFullname());
						orgOaNewsMessage.setAddresser(user.getFullname());
						orgOaNewsMessage.setSendTime(new Date());
						orgOaNewsMessage.setStatus("1");
						oaNewsMessageService.save(orgOaNewsMessage);
						msg="发送成功";
					}else{
						msg="发送失败";
					}
				}else{
					msg="没有找到需要发送站内信对应信息";
				}
			}else{
				msg="请选择要送的站内信";
			}
			sb.append(msg+"'");
			
		}catch(Exception ex){
			logger.error(ex.getMessage());
			sb.append("出错了'");
		}
		sb.append("}");
		setJsonString(sb.toString());
		return SUCCESS;
		
	}

	/**
	 *手动发送短信方法
	 *
	 * @auther: XinFang
	 * @date: 2018/6/6 15:56
	 */
	public  String  sendMessageManual(){

		String telphone = this.getRequest().getParameter("telphone");
		String name = this.getRequest().getParameter("name");
		String year = this.getRequest().getParameter("year");
		String mouth = this.getRequest().getParameter("mouth");
		String day = this.getRequest().getParameter("day");
		String product = this.getRequest().getParameter("product");
		String money = this.getRequest().getParameter("money");
		String id = this.getRequest().getParameter("id");

		SmsSendUtil smsSendUtil = new SmsSendUtil();
		boolean flag = smsSendUtil.sms_Manual(name, year, mouth, day, product, money, id, telphone);
		StringBuffer sb = new StringBuffer();
		String msg = "";
		if (flag){
		sb.append("{success:true,msg:'");
		msg = "发送成功";
		}else {
			sb.append("{success:false,msg:'");
			msg = "发送失败";
		}
		sb.append(msg+"'");
		sb.append("}");
		setJsonString(sb.toString());
		return  SUCCESS;
	}
	
}
