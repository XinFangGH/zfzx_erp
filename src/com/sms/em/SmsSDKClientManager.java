package com.sms.em;

import java.util.Iterator;
import java.util.List;

import cn.emay.sdk.client.api.Client;
import cn.emay.sdk.client.api.MO;
import cn.emay.sdk.client.api.StatusReport;
import cn.emay.sdk.client.listener.ReceiveMessageListener;
import cn.emay.sdk.communication.socket.ResponseMsg;

public class SmsSDKClientManager {

	//public static String softwareSerialNo="0SDK-EMY-0130-XXXXX";//软件序列号,请通过亿美销售人员获取
	//public static String key="123456";//序列号首次激活时自己设定
	
	public static String password="578637";// 密码,请通过亿美销售人员获取
	public static final double Balance=0.1;// 每条短信金额
	public static void init() throws Exception{
		
	}
	

	public static void main(String[] args) {
		
		/**
		 * 获取client的方法1
		 * 此示例中用SDKClient.getClient()获取到Client对象,
		 * 您也可以用Client(softwareSerialNo,password)获取一个Client对象
		 * 注:使用SDKClient.getClient()获取到Client对象的序列号及密码在config.properties中配置
		 */
//		Client client=SDKClient.getClient();
		
		/**
		 * 获取client的方法2
		 * 在构造函数中正确的填写softwareSerialNo和password
		 */
//		Client client2=new Client(softwareSerialNo,password);
		

	}
	public static void StartMenu() {
		int i = 1;
		System.out.println(i + "、激活序列号,初次使用、已注销后再次使用时调用该方法.");
		i += 1;
		System.out.println(i + "、企业信息注册,目地在于短信发送异常时Emay可以连系到企业.");
		i += 1;
		System.out.println(i + "、余额查询");
		i += 1;
		System.out.println(i + "、充值");
		i += 1;
		System.out.println(i + "、密码修改");
		i += 1;
		System.out.println(i + "、发送即时短信");
		i += 1;
		System.out.println(i + "、发送定时短信");
		i += 1;
		System.out.println(i + "、发送带有信息ID的短信,可供查询状态报告");
		i += 1;
		System.out.println(i + "、获取上行短信");
		i += 1;
		System.out.println(i + "、获取状态报告");
		i += 1;
		System.out.println(i + "、软件注销");
		i += 1;
		System.out.println(i + "、关闭程序");
	}

	/**
	 * 软件注销
	 * 1、软件注销后像发送短信、接受上行短信接口都无法使用
	 * 2、软件可以重新注册、注册完成后软件序列号的金额保持注销前的状态
	 */
	public static void testLogout() {
		try {
			int a = SingletonClient.getClient().logout();
			System.out.println("testLogout:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 软件序列号注册、或则说是激活、软件序列号首次使用必须激活
	 * registEx(String serialpass)
	 * 1、serialpass 软件序列号密码、密码长度为6位的数字字符串、软件序列号和密码请通过亿美销售人员获取
	 */
	public static void testRegistEx() {
		int i = SingletonClient.getClient().registEx(password);
		System.out.println("testTegistEx:" + i);
	}
	/**
	 * 发送短信、可以发送定时和即时短信
	 * sendSMS(String[] mobiles,String smsContent, String addSerial, int smsPriority)
	 * 1、mobiles 手机数组长度不能超过1000
	 * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内 
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、优先级范围1~5，数值越高优先级越高(相对于同一序列号)
	 * 5、其它短信发送请参考使用手册自己尝试使用
	 */
	public static int SendSMS(String[] phone,String content) {
		int i=-1;
		try {
			 i=SingletonClient.getClient().sendSMS(phone, content,"",5);//带扩展码
			System.out.println("testSendSMS====="+i);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 发送定时短信
	 * sendScheduledSMSEx(String[] mobiles, String smsContent,String sendTime,String srcCharset)
	 * 1、mobiles 手机数组长度不能超过1000
	 * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、sendTime 定时短信发送时间 定时时间格式为：年年年年月月日日时时分分秒秒，例如20090801123030 表示2009年8月1日12点30分30秒该条短信会发送到用户手机 
	 * 4、srcCharset 字符编码，默认为"GBK"
	 * 5、其它定时短信发送请参考使用手册自己尝试使用
	 */
	public static void testsSendScheduledSMS() {
		try {
			int i=SingletonClient.getClient().sendScheduledSMSEx(new String[] {"15000000000"}, "定时短信", "20111206160000","GBK");
			System.out.println("testsSendScheduledSMS====="+i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 发送带有信息ID的短信（可查状态报告）
	 * sendSMSEx(mobiles, smsContent, addSerial, srcCharset, smsPriority, smsID)
	 * 1、mobiles 手机数组长度不能超过1000
	 * 2、smsContent 最多500个汉字或1000个纯英文、请客户不要自行拆分短信内容以免造成混乱、亿美短信平台会根据实际通道自动拆分、计费以实际拆分条数为准、亿美推荐短信长度70字以内
	 * 3、addSerial 附加码(长度小于15的字符串) 用户可通过附加码自定义短信类别,或添加自定义主叫号码( 联系亿美索取主叫号码列表)
	 * 4、srcCharset 字符编码，默认为"GBK"
	 * 5、优先级范围1~5，数值越高优先级越高(相对于同一序列号)
	 * 6、信息ID，此ID可以与查询查询状态报告的方法中对照发送信息的状态（成功，失败）
	 * 7、其它定时短信发送请参考使用手册自己尝试使用
	 */
	public static void testsSendSMSAddMessageId() {
		try {
			int i=SingletonClient.getClient().sendSMSEx(new String[] {"15000000000"}, "即时短信，带短信ID", "", "GBK", 5, 1234567);
			System.out.println("testsSendScheduledSMS====="+i);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 软件序列号充值、如果软件序列号金额不足、那么可以调用该方法给序列号充值
	 * chargeUp(String cardNo, String cardPass)
	 * 1、cardNo 充值卡卡号
	 * 2、cardPass 充值卡密码
	 * 3、充值卡卡号和密码请联系亿美销售人员获得
	 */
	public static void testChargeUp() {
		try {
			int a = SingletonClient.getClient().chargeUp("充值卡卡号", "密码");
			System.out.println("testChargeUp:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 企业详细信息注册
	 * registDetailInfo(String eName, String linkMan, String phoneNum,String mobile, String email, String fax, String address,String postcode)
	 * 1、eName 企业名称(最多60字节)
	 * 2、linkMan 联系人姓名(最多20字节)
	 * 3、phoneNum 联系电话(最多20字节)
	 * 4、mobile 联系手机(最多15字节)
	 * 5、email 电子邮件(最多60字节)
	 * 6、fax 联系传真(最多20字节)
	 * 7、address 公司地址(最多60字节)
	 * 8、postcode 邮政编码(最多6字节)
	 * 9、以上参数信息都必须填写、每个参数都不能为空
	 */
	public static void testRegistDetailInfo() {
		try {
			int a = SingletonClient.getClient().registDetailInfo("企业名称", "联系人", "固话","13000000000", "邮件", "传真", "企业地址", "邮政编码");
			System.out.println("testRegistDetailInfo:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 修改软件序列号密码、注意修改软件序列号密码以后不需要重新注册(激活)
	 * serialPwdUpd(String serialPwd, String serialPwdNew)
	 * 1、serialPwd 旧密码
	 * 2、serialPwdNew 新密码、长度为6位的数字字符串
	 */
	public static void testSerialPwdUpd() {
		try {
			int a = SingletonClient.getClient().serialPwdUpd(password, "501683");
			System.out.println("testSerialPwdUpd:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 序列号的余额
	 * 0.1代表一条
	 */
	public static double getBalance() {
		double a=0.0 ;
		try {
			 a = SingletonClient.getClient().getBalance();
			System.out.println("testGetBalance:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return a;
	}
	
	/**
	 * 发送一条短信所需的费用
	 */
	public static void testGetEachFee() {
		try {
			double a = SingletonClient.getClient().getEachFee();
			System.out.println("testGetEachFee:" + a);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 1、从EUCP平台接收手机用户上行的短信
	 * 2、返回值list中的每个元素为一个Mo对象
	 * 3、Mo具体数据结构参考使用手册
	 */
	public static void testGetMO() {
		try {
			List<MO> list = SingletonClient.getClient().getMO();
			if (list != null) {
				System.out.println("testGetMO1size:" + list.size());

				for(MO mo : list) {
					System.out.println("短信内容:" + mo.getSmsContent());
					System.out.println("通道号:" + mo.getChannelnumber());
					System.out.println("手机号:" + mo.getMobileNumber());
					System.out.println("附加码:" + mo.getAddSerialRev());
					System.out.println("附加码:" + mo.getAddSerial());
					// 上行短信务必要保存,加入业务逻辑代码,如：保存数据库，写文件等等
				}
			} else {
				System.out.println("NO HAVE MO");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 1、从EUCP平台接收下行短信的状态报告
	 * 2、返回值list中的每个元素为一个StatusReport对象
	 * 3、StatusReport具体数据结构参考使用手册
	 */
	public static void testGetReport(){
		try {
			 List<StatusReport> list=SingletonClient.getClient().getReport();
			 if(list!=null){
				for(StatusReport report : list){
					System.out.println("手机号码:"+report.getMobile()+"\t状态:"+report.getReportStatus()+"\t信息ID："+report.getSeqID());
				}
			 }else{
				 System.out.println("no have data");
			 }
		} catch (Exception e) {
		}
	}
	
}
