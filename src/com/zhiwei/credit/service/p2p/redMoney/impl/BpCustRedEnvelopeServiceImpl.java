package com.zhiwei.credit.service.p2p.redMoney.impl;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.util.List;

import javax.annotation.Resource;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.credit.dao.p2p.redMoney.BpCustRedEnvelopeDao;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedEnvelope;
import com.zhiwei.credit.model.p2p.redMoney.BpCustRedMember;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedEnvelopeService;
import com.zhiwei.credit.service.p2p.redMoney.BpCustRedMemberService;

/**
 * 
 * @author 
 *
 */
public class BpCustRedEnvelopeServiceImpl extends BaseServiceImpl<BpCustRedEnvelope> implements BpCustRedEnvelopeService{
	@SuppressWarnings("unused")
	private BpCustRedEnvelopeDao dao;
	@Resource
	private BpCustRedMemberService bpCustRedMemberService;
	public BpCustRedEnvelopeServiceImpl(BpCustRedEnvelopeDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public String delete(String[] ids) {
		for(String id:ids){
			dao.remove(new Long(id));
			List<BpCustRedMember> list= bpCustRedMemberService.listbyredId(Long.valueOf(id),"all");
			for(BpCustRedMember s:list){
				bpCustRedMemberService.remove(s);
			}
		}
		return "";
	}

}