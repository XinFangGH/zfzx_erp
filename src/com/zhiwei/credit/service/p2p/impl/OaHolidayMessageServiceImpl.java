package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.util.AppUtil;
import com.zhiwei.credit.dao.p2p.OaHolidayMessageDao;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.model.p2p.OaHolidayMessage;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import com.zhiwei.credit.service.p2p.OaHolidayMessageService;
import com.zhiwei.credit.service.sms.util.SmsSendUtil;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 
 * @author 
 *
 */
public class OaHolidayMessageServiceImpl extends BaseServiceImpl<OaHolidayMessage> implements OaHolidayMessageService{
	@SuppressWarnings("unused")
	private OaHolidayMessageDao dao;

	public OaHolidayMessageServiceImpl(OaHolidayMessageDao dao) {
		super(dao);
		this.dao=dao;
	}

    private SmsSendUtil util;
    private Map configMap= null;
    private  String subject = "";
	@Resource
	private BpCustMemberService bpCustMemberService;

    @Override
    public List<OaHolidayMessage> getMessageByType(Integer type) {
        return dao.getMessageByType(type);
    }

    /**
     * 1 查询节日祝福短信，是否有今天的
     * 2 循环发送短信
     */
    @Override
    public void messageSending() {
        getSubjectName();
        List<OaHolidayMessage> list = dao.getMessageByDate(new Date(),null);
        String tels = "";
        Integer start = 0;
        System.out.println("发送短信方法调用");
        if (null != list && list.size() > 0) {
            for (OaHolidayMessage oaHolidayMessage : list) {
                while (true){
                    tels = bpCustMemberService.getMemberTel(start,1000);
                    start += 1000;
                    if (StringUtils.isNotEmpty(tels)) {
                        System.out.println("start:"+start);
                        //发送短信
                        sendMessage(tels,subject + "节日祝福短信测试"+start);
                    }else {
                        break;
                    }
                }
            }
        }
    }




    @Override
    public void birthdayMessageSending() {
        getSubjectName();
        String content = "";
        System.out.println("发送生日短信方法调用");
        List<OaHolidayMessage> list = dao.getMessageByDate(null,2);
        if (null != list && list.size() > 0) {
            OaHolidayMessage message = list.get(0);
            List<BpCustMember> listMessage = bpCustMemberService.getBirThDay();
            for (BpCustMember bpCustMember : listMessage) {
                //发送短信
                content = getSendMessage(message.getMessage(), bpCustMember);
                sendMessage(bpCustMember.getTelphone(),subject + message.getMessage());
                System.out.println(subject + content);
            }
        }
    }

    /**
     * 替换需要替换的要素
     * @param content
     * @param bpCustMember
     */
    protected String getSendMessage(String content, BpCustMember bpCustMember) {
        if(content.contains("{name}")) {
            content = content.replace("{name}", bpCustMember.getTruename());
        }
        if(content.contains("{loginName}")) {
            content = content.replace("{loginName}", bpCustMember.getLoginname());
        }
        if(content.contains("{telphone}")) {
            content = content.replace("{telphone}", bpCustMember.getTelphone());
        }
        return content;
    }

    /**
     * 发送短信
     * @param telphone
     * @param content
     */
    private void sendMessage(String telphone,String content) {
        if (util == null)
             util = new SmsSendUtil();
        util.sendMessage(telphone,content);
    }

    /**
     * 获取短信签名
     */
    protected void getSubjectName() {
        if (configMap == null) {
            configMap= AppUtil.getConfigMap();
        }
        if (configMap != null && configMap.containsKey("subject")) {
            if (StringUtils.isNotEmpty(configMap.get("subject").toString())) {
                subject = "【"+configMap.get("subject").toString()+"】";
            }
        }
    }
}