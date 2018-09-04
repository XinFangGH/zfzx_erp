package com.zhiwei.credit.service.p2p;
/*
 *  北京金智万维软件有限公司   -- http://www.credit-software.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import java.math.BigDecimal;
import java.util.*;

import javax.servlet.http.HttpServletRequest;

import com.zhiwei.core.service.BaseService;
import com.zhiwei.core.web.paging.PagingBean;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationEnterprise;
import com.zhiwei.credit.model.creditFlow.customer.cooperation.CsCooperationPerson;
import com.zhiwei.credit.model.creditFlow.financingAgency.PlBidInfo;
import com.zhiwei.credit.model.p2p.BpCustMember;

/**
 * 
 * @author 
 *
 */
public interface BpCustMemberService extends BaseService<BpCustMember>{
	//显示没有被删除的用户
	public List<BpCustMember> get();
	/**
	 * 获取 用户信息 通过 标识 和类别id
	 * @param flag
	 * @param pid
	 * @return
	 */
	public BpCustMember getMemberByPIdAndFlag(String flag, String pid);
	public BpCustMember getMemberUserName(String userName, String cardNum);
	public BpCustMember getMemberUserName(String userName);
	public BpCustMember isExist(String loginname);
	public BpCustMember isRecommandPerson(String recommandPerson);
	/**
	 * 通过第三方标识 获取用户
	 * @param ret
	 * @return
	 */
	public BpCustMember getMemberByFlagId(String ret);
	/**
	 * 获取线上所有用户的列表
	 * @param request
	 * @return
	 */
	public List<BpCustMember> getAllList(HttpServletRequest request,Integer start,Integer limit);
	
	public List<BpCustMember> getAllList1(HttpServletRequest request,Integer start,Integer limit);
	
	
	/**
	 * 刷新邀请推荐用户数
	 * svn：songwj
	 * @return
	 */
	public List<BpCustMember> getBpCustMemberList();
	
	
	/**
	 *更新邀请数量的总数
	 *svn:songwj
	 * @param recommandPerson
	 */
	public BpCustMember updateNum(String recommandPerson);
	
	//svn:songwj
	public List<BpCustMember> getBpCustMemberByrecommandPerson(String recommandPerson);
	public List<BpCustMember> getByCardId(String userId);
	
	public List cusrNumList(HttpServletRequest request,PagingBean pb);
	public Long cusrNumSize(HttpServletRequest request);
	/**
	 *查询推介人
	 * @param recommandPerson
	 */
	public BpCustMember getPlainpassword(String recommandPerson);
	
	/**
	 * 合作机构账户查询
	 * @param map
	 * @return
	 */
	public List<BpCustMember>  listAccount(Map<String, String> map);
	/**
	 * 合作机构账户查询--总记录数
	 * @param map
	 * @return
	 */
	public Long listAccountNumber(Map<String, String> map);
	
	/**
	 * 如果合作机构开通了P2P账户，侧进行设置P2P账户信息
	 * @param cs
	 */
	public BpCustMember setP2PInfo(CsCooperationPerson cs);
	
	/**
	 * 如果合作机构开通了P2P账户，侧进行设置P2P账户信息
	 * @param cs
	 */
	public BpCustMember setP2PInfo(CsCooperationEnterprise cs);
	/**
	 * 根据用户名模糊查询
	 */
	public List<BpCustMember> getByTrueName(String trueName);
	List<BpCustMember> getAllListCount(HttpServletRequest request,
			Integer start, Integer limit);

    void changeRecommand(String[] split, String recommandPerson);
	/**
	 *活动推荐查询
	 *
	 * @auther: XinFang
	 * @date: 2018/7/5 15:47
	 */
	List<BpCustMember>  listActicity(String loginname,String plainpassword,String truename,String yearThan
			,String sumThird,String sumSecond,String sumFirst);

    List<BpCustMember> getAddressAndSex(String startDate, String endDate);

	List<Object[]>  getAge(String startDate, String endDate);

	/**
	 *查询邀请详情
	 *
	 * @auther: XinFang
	 * @date: 2018/8/13 11:28
	 */
    List<PlBidInfo> searchInvitesById(Long userId,String name, String startDate, String endDate);

    /**
     *获取某人投资总额
     *
     * @auther: XinFang
     * @date: 2018/8/13 11:29
     */
    BigDecimal getTotalInvestMoney(Long id,String startDate, String endDate);

    /**
     *
     * 获取某人投资详情
     * @auther: XinFang
     * @date: 2018/8/13 16:57
     */
    List<PlBidInfo> getInvestmentInfo(Long id, String startDate, String endDate);

	/**
	 * 获取当天过生日
	 * @return
	 */
	List<BpCustMember> getBirThDay();

	/**
	 * 查询用户手机号码，并且用','分割
	 * @param start
	 * @param limit
	 * @return
	 */
	String getMemberTel(Integer start, Integer limit);
}


