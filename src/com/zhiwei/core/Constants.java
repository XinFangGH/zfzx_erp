package com.zhiwei.core;
/*
 *  北京互融时代软件有限公司 OA办公管理系统   -- http://www.hurongtime.com
 *  Copyright (C) 2008-2011 Hurong Software Company
*/

/**
 * 全应用程序的常量
 * @author csx
 *
 */
public class Constants {
	public static final String CHAR_GBK="GBK";
	public static final String CHAR_UTF="UTF-8";
	/**
	 * 双乾支付 
	 */
	public static final String MONEYMOREMORE="moneyMoreMoreConfig";
	/**
	 * 国付宝支付
	 */
	public static final String GOPAY="gopayConfig";
	/**
	 * 富有支付
	 */
	public static final String FUIOU="fuiouConfig";
	/**
	 * 联动优势
	 */
	public static final String UMPAY="umpayConfig";
	/**
	 * 汇付天下支付
	 */
	public static final String HUIFU="huifuConfig";
	/**
	 * 通过webservices 生成静态页面 传输的数据格式
	 */
	public static final String BUILDHTML_FORMAT_JSON="application/json";
    public static final String BUILDHTML_FORMAT_XML = "application/xml"; 
    /**
	 * 附件目录
	 */
	public static final String ATTACH_FILES="attachFiles/";
	
	/**
	 * 成功标识
	 */
	public static final String SUCCESSFLAG="8888";
	/**
	 * 失败标识
	 */
	public static final String FAILDFLAG="0000";
	
	/**
	 * 项目状态:
	 * 0-表示进行中;
	 * 1-表示贷中(流程已正常结束:对应流程状态为2);
	 * 2-表示已完成(已正常还款);
	 * 3-表示已终止(对应流程状态为3);
	 * 
	 * 4-展期申请中(未审批-小贷特有);
	 * 5-通过展期申请(展期状态-小贷特有);
	 * 6-未通过展期申请(小贷特有)。
	 * 8-违约项目。
	 * 10-(已挂起项目：与多个不同的项目表以及和任务相关的表状态一致,都为10,避免不同的地方是不同的值,而本身所代表的意思一样。)
	 * 
	 * 在流程结束或终止流程需要改变项目的状态,在此定义全局常量。
	 * add by lu 2011.12.02 start
	 */
	public static final Short PROJECT_STATUS_ACTIVITY=0;
	
	public static final Short PROJECT_STATUS_MIDDLE=1;
	
	public static final Short PROJECT_STATUS_COMPLETE=2;
	
	public static final Short PROJECT_STATUS_STOP=3;
	
	public static final Short PROJECT_POSTPONED_STATUS_ACT=4;
	
	public static final Short PROJECT_POSTPONED_STATUS_PASS=5;
	
	public static final Short PROJECT_POSTPONED_STATUS_REFUSE=6;
	
	public static final Short PROJECT_SUPERVISE_STATUS_MIDDLE=7;
	
	public static final Short PROJECT_STATUS_BREAKACONTRACT=8;
	
//	public static final Short PROJECT_SUPERVISE_STATUS_COMPLETE=9;
	
	public static final Short PROJECT_STATUS_SUSPENDED=10;
	/**
	 * add by lu 2011.12.02 end
	 */
	
	/**
	 * 代表禁用
	 */
	public static final Short FLAG_DISABLE = 0;
	/**
	 * 代表激活
	 */
	public static final Short FLAG_ACTIVATION = 1;
	/**
	 * 代表记录删除
	 */
	public static final Short FLAG_DELETED=1;
	/**
	 * 代表未删除记录
	 */
	public static final Short FLAG_UNDELETED=0;
	
	/**
	 * 应用程序的格式化符
	 */
	public static final String DATE_FORMAT_FULL="yyyy-MM-dd HH:mm:ss";
	/**
	 * 短日期格式
	 */
	public static final String DATE_FORMAT_YMD="yyyy-MM-dd"; 
	
	/**
	 * 流程启动者，可用于在流程定义使用
	 */
	//public static final String FLOW_START_USER="flowStartUser";
//	/**
//	 * 流程启动ID
//	 */
//	public static final String FLOW_START_USERID="flowStartUserId";
	
	/**
	 * 流程任务执行过程中，指定了某人执行该任务，该标识会存储于Variable变量的Map中，随流程进入下一步
	 */
	public static final String FLOW_ASSIGN_ID="flowAssignId";
	
	/**
	 * 为会签任务指定多个用户
	 */
	public static final String FLOW_SIGN_USERIDS="signUserIds";
	
	/**
	 * 若当前流程节点分配的节点为流程启动者，其userId则为以下值
	 */
	public static final String FLOW_START_ID="__start";
	/**
	 * 若当前流程分配置为当前启动者的上司，则其对应的ID为以下值
	 */
	public static final String FLOW_SUPER_ID="__super";
	
	/**
	 * 请假流程的key
	 */
	public static final String FLOW_LEAVE_KEY="ReqHolidayOut";
	
	/**
	 * 流程下一步跳转列表常量
	 */
	public static final String FLOW_NEXT_TRANS="nextTrans";
	/**
	 * 公文ID
	 */
	public static final String ARCHIES_ARCHIVESID="archives.archivesId";
	/**
	 * 公司LOGO路径
	 */
	public static final String COMPANY_LOGO="app.logoPath";
	/**
	 * 默认的LOGO
	 */
	public static final String DEFAULT_LOGO="/images/ht-logo-dynamic.png";
	/**
	 * 公司名称
	 */
	public static final String COMPANY_NAME="app.companyName";
	/**
	 * 默认公司的名称
	 */
	public static final String DEFAULT_COMPANYNAME="互融时代";
	/**
	 * 通过审核
	 */
	public static final Short FLAG_PASS=1;
	/**
	 * 不通过审核
	 */
	public static final Short FLAG_NOTPASS=2;
	
	/**
	 * 启用
	 */
	public static final Short ENABLED=1;
	/**
	 * 未启用
	 */
	public static final Short UNENABLED=0;
	
	/**
	 * 接口调用成功编码
	 */
	public static final String CODE_SUCCESS="8888";
	
	/**
	 * 接口调用失败编码
	 */
	public static final String CODE_FAILED="0000";
	
	public static final String YEEPAY="yeepayConfig";
	
	//通过第三方进行实名验证
	public static final String SYSTEM_AUTHENTICATION_THIRDPAY="thirdPayAuthentication";
	//通过ID5进行实名认证
	public static final String SYSTEM_AUTHENTICATION_ID5="ID5Authentication";
	//我们自己的实名认证方法
	public static final String SYSTEM_AUTHENTICATION_SYSTEM="systemAuthentication";
}
