package com.zhiwei.credit.service.p2p.impl;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.impl.BaseServiceImpl;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.dao.customer.BpCustRelationDao;
import com.zhiwei.credit.dao.p2p.BpCustMemberDao;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.customer.BpCustRelation;
import com.zhiwei.credit.model.p2p.BpCustMember;
import com.zhiwei.credit.service.p2p.BpCustMemberService;
import org.apache.commons.lang3.StringUtils;

/**
 *
 * @author
 *
 */

public class BpCustMemberServiceImpl extends BaseServiceImpl<BpCustMember> implements BpCustMemberService{
	private BpCustMemberDao dao;
	@Resource
	private BpCustRelationDao bpCustRelationDao;

	public BpCustMemberServiceImpl(BpCustMemberDao dao) {
		super(dao);
		this.dao=dao;
	}

	@Override
	public List<BpCustMember> get() {
		// TODO Auto-generated method stub

		return dao.get();
	}

	@Override
	public BpCustMember getMemberByPIdAndFlag(String flag, String pid) {
		return dao.getMemberByPIdAndFlag(flag,pid);
	}

	@Override
	public BpCustMember getMemberUserName(String userName, String cardNum) {
		// TODO Auto-generated method stub
		return dao.getMemberUserName(userName,cardNum);
	}

	@Override
	public BpCustMember getMemberByFlagId(String ret) {
		// TODO Auto-generated method stub
		return dao.getMemberByFlagId(ret);
	}

	@Override
	public List<BpCustMember> getAllList(HttpServletRequest request,Integer start,Integer limit) {
		// TODO Auto-generated method stub
		return dao.getAllList(request,start,limit);
	}
	@Override
	public List<BpCustMember> getAllListCount(HttpServletRequest request,
			Integer start, Integer limit){
		return dao.getAllListCount(request, start, limit);
	}

	@Override
	public void changeRecommand(String[] split, String recommandPerson) {
		for (int i = 0; i < split.length; i++) {
			dao.changeRecommand(split[i],recommandPerson);
		}
	}
	/**
	 *活动推荐查询
	 *
	 * @auther: XinFang
	 * @date: 2018/7/5 15:47
	 */
    @Override
    public List<BpCustMember> listActicity(String loginname,String plainpassword,String truename,String yearThan
            ,String sumThird,String sumSecond,String sumFirst) {
        List<BpCustMember> bpCustMember = dao.getActicityCount(loginname,plainpassword,truename);

            for (int i = 0; i < bpCustMember.size(); i++) {
                Integer count = dao.getInventCount(bpCustMember.get(i).getPlainpassword());
                BigDecimal yearMoney = dao.getYearMoney(bpCustMember.get(i).getPlainpassword(),yearThan);
                BigDecimal sumMoney = dao.getSumActicityMoney(bpCustMember.get(i).getPlainpassword(),sumThird,sumSecond,sumFirst);
                if (StringUtils.isNotBlank(yearThan) || StringUtils.isNotBlank(sumThird) || StringUtils.isNotBlank(sumSecond) || StringUtils.isNotBlank(sumFirst)){
                    if (StringUtils.isNotBlank(yearThan) && yearMoney.intValue() == 0){
                    bpCustMember.remove(i);
                    i--;
                    continue;
                    }else if (StringUtils.isNotBlank(sumThird) &&   sumMoney.intValue() == 0){
                        bpCustMember.remove(i);
                        i--;
						continue;
                    }else if (StringUtils.isNotBlank(sumSecond) &&   sumMoney.intValue() == 0){
                        bpCustMember.remove(i);
                        i--;
						continue;
                    }else if (StringUtils.isNotBlank(sumFirst) &&   sumMoney.intValue() == 0){
                        bpCustMember.remove(i);
                        i--;
						continue;
                    }
                }
                bpCustMember.get(i).setMouthInventCount(count);
                bpCustMember.get(i).setYearMoney(yearMoney);
                bpCustMember.get(i).setSumActicityMoney(sumMoney);
            }



        return bpCustMember;
    }

	@Override
	public List<BpCustMember> getAddressAndSex(String startDate, String endDate) {
     List<BpCustMember> address = dao.getAddressCount(startDate,endDate);
     List<BpCustMember> sex = dao.getSexCount(startDate,endDate);

     List<BpCustMember> list = new ArrayList<>();

     list.addAll(sex);
     list.addAll(address);
		return list;
	}

	@Override
	public List<Object[]> getAge(String startDate, String endDate) {

		return dao.getAgeInfo(startDate,endDate);
	}



	@Override
	public BpCustMember getMemberUserName(String userName) {
		// TODO Auto-generated method stub
		return dao.getMemberUserName(userName);
	}

	@Override
	public List<BpCustMember> getAllList1(HttpServletRequest request,Integer start,Integer limit) {
		// TODO Auto-generated method stub
		return dao.getAllList1(request,start,limit);
	}

	/**
	 * 刷新邀请推荐用户数
	 * svn：songwj
	 * @return
	 */
	public List<BpCustMember> getBpCustMemberList(){
		return dao.getBpCustMemberList();
	}



	public BpCustMember updateNum(String recommandPerson){
		return dao.updateNum(recommandPerson);
	}

	/**
	 * 根据推荐码查询人员推荐会员的信息
	 * svn:songwj
	 */
	public List<BpCustMember> getBpCustMemberByrecommandPerson(String recommandPerson){
		return dao.getBpCustMemberByrecommandPerson(recommandPerson);
	}
	@Override
	/**
	 * 判断邀请的推荐码是否存在
	 */
	public BpCustMember isRecommandPerson(String recommandPerson) {
		// TODO Auto-generated method stub
		return dao.isRecommandPerson(recommandPerson);
	}
	@Override
	public List<BpCustMember> getByCardId(String userId) {
		// TODO Auto-generated method stub
		 return dao.getByCardId(userId);
	}

	@Override
	public Long cusrNumSize(HttpServletRequest request) {

		return dao.cusrNumSize(request);
	}

	@Override
	public List cusrNumList(HttpServletRequest request, PagingBean pb) {
		// TODO Auto-generated method stub
		return dao.cusrNumList(request, pb);
	}

	@Override
	public List<BpCustMember> listAccount(Map<String, String> map) {

		return dao.listAccount(map);

	}

	@Override
	public Long listAccountNumber(Map<String, String> map) {

		return dao.listAccountNumber(map);
	}

	@Override
	public BpCustMember setP2PInfo(CsCooperationPerson cs) {
		try {
			String type = null;
			if(cs.getType().equals("lenders")){//个人债权客户
				type = "p_cooperation";
			}else if(cs.getType().equals("financial")){//理财顾问
				type = "p_financial";
			}
			BpCustRelation bpCustRelation = bpCustRelationDao.getByOfflineCusIdAndOfflineCustType(cs.getId(), type);
			if(bpCustRelation!=null){
				BpCustMember bpCustMember = dao.get(bpCustRelation.getP2pCustId());
				if(bpCustMember!=null){
					cs.setP2pid(bpCustMember.getId().toString());
					cs.setP2ploginname(bpCustMember.getLoginname());
					cs.setP2ptruename(bpCustMember.getTruename());
					cs.setP2ptelphone(bpCustMember.getTelphone());
					cs.setP2pemail(bpCustMember.getEmail());
					cs.setP2pcardcode(bpCustMember.getCardcode());
					cs.setP2pisForbidden(bpCustMember.getIsForbidden()==null?null:bpCustMember.getIsForbidden().toString());
				}
				return bpCustMember;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public BpCustMember setP2PInfo(CsCooperationEnterprise cs) {
		try {
			String type = null;
			if(cs.getType().equals("lenders")){//企业债权客户
				type = "b_cooperation";
			}else if(cs.getType().equals("guarantee")){//担保客户
				type = "b_guarantee";
			}
			BpCustRelation bpCustRelation = bpCustRelationDao.getByOfflineCusIdAndOfflineCustType(cs.getId(), type);
			if(bpCustRelation!=null){
				BpCustMember bpCustMember = dao.get(bpCustRelation.getP2pCustId());
				if(bpCustMember!=null){
					cs.setP2pid(bpCustMember.getId().toString());
					cs.setP2ploginname(bpCustMember.getLoginname());
					cs.setP2ptruename(bpCustMember.getTruename());
					cs.setP2ptelphone(bpCustMember.getTelphone());
					cs.setP2pemail(bpCustMember.getEmail());
					cs.setP2pcardcode(bpCustMember.getCardcode());
					cs.setP2pisForbidden(bpCustMember.getIsForbidden()==null?null:bpCustMember.getIsForbidden().toString());
				}
				return bpCustMember;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public BpCustMember isExist(String loginname) {
		return dao.isExist(loginname);
	}

	@Override
	public BpCustMember getPlainpassword(String recommandPerson) {
		// TODO Auto-generated method stub
		return dao.getPlainpassword(recommandPerson);
	}

	@Override
	public List<BpCustMember> getByTrueName(String trueName) {
		// TODO Auto-generated method stub
		return dao.getByTrueName(trueName);
	}

	/**
	 *查询某人邀请详情
	 *
	 * @auther: XinFang
	 * @date: 2018/8/13 11:25
	 */
    @Override
    public List<PlBidInfo> searchInvitesById(Long userId,String name, String startDate, String endDate) {

        return dao.searchInvitesById(userId,name,startDate,endDate);
    }

	/**
	 *获取某人投资总额
	 *
	 * @auther: XinFang
	 * @date: 2018/8/13 11:29
	 */
	@Override
	public BigDecimal getTotalInvestMoney(Long id, String startDate, String endDate) {
		return dao.getTotalInvestMoney(id,startDate,endDate);
	}

	/**
	 *
	 * 获取某人投资详情
	 * @auther: XinFang
	 * @date: 2018/8/13 16:57
	 */
	@Override
	public List<PlBidInfo> getInvestmentInfo(Long id, String startDate, String endDate) {
		return dao.getInvestmentInfo(id,startDate,endDate);
	}
	@Override
	public List<BpCustMember> getBirThDay() {
		return dao.getBirThDay();
	}

	@Override
	public String getMemberTel(Integer start, Integer limit) {
		return dao.getMemberTel(start,limit);
	}
}