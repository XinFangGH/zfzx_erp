package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.dao.p2p.OaNewsMessageDao;
import com.zhiwei.credit.dao.p2p.OaNewsMessagerinfoDao;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.OaNewsMessage;
import com.zhiwei.credit.model.p2p.OaNewsMessagerinfo;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.OaNewsMessageService;

/**
 * 
 * @author 
 *
 */
public class OaNewsMessageServiceImpl extends BaseServiceImpl<OaNewsMessage> implements OaNewsMessageService{
	@SuppressWarnings("unused")
	private OaNewsMessageDao dao;
	
	@Resource
	private BpCustMemberService bpCustMemberService;
	@Resource
	private BpCustMemberDao bpCustMemberDao;
	@Resource
	private OaNewsMessagerinfoDao oaNewsMessagerinfoDao;
	
	public OaNewsMessageServiceImpl(OaNewsMessageDao dao) {
		super(dao);
		this.dao=dao;
	}
	/**
	 * 向所有用户发送站内信
	 */
	@Override
	public void sendAllUser(OaNewsMessage oaNewsMessage) {
		// TODO Auto-generated method stub
		
		List<BpCustMember> list = bpCustMemberService.get();
		try{
			for (int i = 0; i < list.size(); i++) {
				BpCustMember bpCustMember = list.get(i);
				OaNewsMessage oa=new OaNewsMessage();
				oa.setTitle(oaNewsMessage.getTitle());//发送标题
				oa.setAddresser(oaNewsMessage.getAddresser());//发送人
				oa.setContent(oaNewsMessage.getContent());//发送内容
				oa.setType(oaNewsMessage.getType());//类型
				oa.setTypename(oaNewsMessage.getTypename());//类型名称
				Long memberID = bpCustMember.getId();//接收人
				oa.setRecipient(memberID);//接收人
				oa.setSendTime(new Date());//发送时间
				dao.save(oa);
			
			}
		}catch(Exception e){
			logger.info(e.getMessage());
		}
		
		
	}

	/**
	 * 获取单个用户所有未删除的站内信
	 */
	@Override
	public List<OaNewsMessage> getUserAll(Long userId) {
		return dao.getUserAll(userId);
	}
	@Override
	public Boolean sendAllUserMessage(OaNewsMessage oaNewsMessage) {
		try{
			if(oaNewsMessage.getIsAllSend()!=null&&oaNewsMessage.getIsAllSend().equals("1")){//全部发送
				List<BpCustMember> list = bpCustMemberDao.get();//获取全部可用的P2P登陆用户信息
				if(list!=null&&list.size()>0){
					for(BpCustMember temp:list){
						OaNewsMessagerinfo info= new OaNewsMessagerinfo();
						info.setUserId(temp.getId());//收件人Id
						info.setUserName(temp.getTruename());
						info.setUserType("P2P");//P2P登陆用户
						info.setMessageId(oaNewsMessage.getId());
						info.setReadStatus(0);//默认阅读状态为未读
						info.setStatus(2);//已发送
						info.setIstop(0);//默认不置顶
						oaNewsMessagerinfoDao.save(info);
					}
				}
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	@Override
	public Boolean sendsomeUserMessage(OaNewsMessage oaNewsMessage) {
		try{
			String commen=oaNewsMessage.getComment2();
			String[] userids = commen.split(",");
			for(int i=0;i<userids.length;i++){
				Long userid = new Long(userids[i]);
				BpCustMember bp=bpCustMemberDao.get(userid);
				
				OaNewsMessagerinfo info= new OaNewsMessagerinfo();
				info.setUserId(userid);//收件人Id
				if(bp!=null){
					info.setUserName(bp.getTruename());			
				}
				info.setUserType("P2P");//P2P登陆用户
				info.setMessageId(oaNewsMessage.getId());
				info.setReadStatus(0);//默认阅读状态为未读
				info.setStatus(2);//已发送
				info.setIstop(0);//默认不置顶
				oaNewsMessagerinfoDao.save(info);
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
	}
	/**
	 * 优惠券派发，活动中心派发站内信
	 */
	@Override
	public void sedBpcouponsMessage(String title, String content,
			Long memberId, String remaker) {
		OaNewsMessage oa=new OaNewsMessage();
		oa.setTitle(title);//发送标题
		oa.setAddresser("超级管理员");//发送人
		oa.setContent(content);//发送内容
		oa.setType("coupons");//类型
		oa.setTypename("优惠券通知");//类型名称
		oa.setRecipient(memberId);//接收人
		oa.setSendTime(new Date());//发送时间
		oa.setStatus("1");
		oa.setIsAllSend("0");
		BpCustMember bp=bpCustMemberDao.get(memberId);
		oa.setComment1(bp.getLoginname());
		oa.setComment2(memberId.toString());
		dao.save(oa);
		this.sendsomeUserMessage(oa);
	}

	

}