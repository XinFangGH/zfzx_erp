package com.zhiwei.credit.service.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.p2p.OaNewsMessage;

/**
 * 
 * @author 
 *
 */
public interface OaNewsMessageService extends BaseService<OaNewsMessage>{
public void sendAllUser(OaNewsMessage oaNewsMessage);
	
	public List<OaNewsMessage> getUserAll(Long userId);
	/**
	 * 给全部用户发送站内信
	 * @param oaNewsMessage
	 * @return
	 */
	public Boolean sendAllUserMessage(OaNewsMessage oaNewsMessage);
	/**
	 * 给特定用户发送站内信
	 * @param oaNewsMessage
	 * @return
	 */
	public Boolean sendsomeUserMessage(OaNewsMessage oaNewsMessage);
	/**
	 * 优惠券派发。活动中心派发站内信
	 * @param title
	 * @param content
	 * @param memberId
	 * @param remaker
	 */
	public void  sedBpcouponsMessage(String title,String content,Long memberId,String remaker);
}


