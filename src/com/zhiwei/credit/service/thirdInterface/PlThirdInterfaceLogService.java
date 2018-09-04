package com.zhiwei.credit.service.thirdInterface;
/*
 *  北京互融时代软件有限公司   -- http://www.zhiweitime.com
 *	Copyright @ 2004 - 2010 Yuseen.com all rights reserved.京ICP备 05007290 号
*/
import com.zhiwei.core.service.BaseService;
import com.zhiwei.credit.model.thirdInterface.PlThirdInterfaceLog;

/**
 * 
 * @author 
 *
 */
public interface PlThirdInterfaceLogService extends BaseService<PlThirdInterfaceLog>{
	/**
	 * 保存接口日志
	 * @param code 返回代码
	 * @param msg  返回 信息
	 * @param bigMsg 返回完整信息
	 * @param interfaceName 接口名称
	 * @param memberId 操作人
	 * @param memberType 操作人类别  操作人类型 0 线下 1 线上
	 * @param typeId 接口类别id  类型id 1第三方支付 2 短信 
	 * @param typeName 接口类别名称 
	 */
	public void saveLog(String code,String msg,String bigMsg,String interfaceName,Long memberId,int memberType,Long typeId,String typeName,String userName,String rem1,String rem2,String rem3);
	/**
	 * 通过平台订单号获取第三方放款订单号
	 * @param orderNo
	 * @return
	 */
	public PlThirdInterfaceLog getByOrdId(String orderNo);
	/**
	 * 通过第三方订单号获取相关信息
	 * @param orderNo
	 * @return
	 */
	public PlThirdInterfaceLog getByLoanOrdId(String orderNo);
	
	//更新短信日志
	public void updateLog(String code,String msg,String bigMsg,String interfaceName,Long memberId,int memberType,Long typeId,String typeName,String userName,String rem1,String rem2,String rem3,String id);
	
	/**
	 * 保存接口日志
	 * @param code 返回代码
	 * @param msg  返回 信息
	 * @param bigMsg 返回完整信息
	 * @param interfaceName 接口名称
	 * @param memberId 操作人
	 * @param memberType 操作人类别  操作人类型 0 线下 1 线上
	 * @param typeId 接口类别id  类型id 1第三方支付 2 短信 
	 * @param typeName 接口类别名称 
	 * svn：songwj
	 */
	public String saveLog1(String code,String msg,String bigMsg,String interfaceName,Long memberId,int memberType,Long typeId,String typeName,String userName,String rem1,String rem2,String rem3);
}


