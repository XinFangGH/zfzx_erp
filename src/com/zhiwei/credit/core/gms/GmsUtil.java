package com.zhiwei.credit.core.gms;

import java.util.Map;

import org.smslib.IOutboundMessageNotification;
import org.smslib.OutboundMessage;
import org.smslib.Service;
import org.smslib.modem.SerialModemGateway;
import com.zhiwei.core.util.AppUtil;

public class GmsUtil {
	private static Object sign=new Object();
	private static Service service=null;
	
	private static void init(){
		service = new Service();
		IOutboundMessageNotification outboundNotification = new IOutboundMessageNotification() {
			@Override
			public void process(String gatewayId, OutboundMessage msg) {
			}
		};
		Map sysConfig = AppUtil.getSysConfig();
		String deviceName = (String)sysConfig.get("deviceName");
		Integer baudRate = Integer.parseInt((String)sysConfig.get("baudRate"));
		SerialModemGateway gateway = new SerialModemGateway("modem.com3",deviceName, baudRate, "wavecom", ""); // 设置端口与波特率
		gateway.setInbound(true);
		gateway.setOutbound(true);
		gateway.setSimPin("0000");
		gateway.setOutboundNotification(outboundNotification);
		service.addGateway(gateway);
	}
	
	public static Service getGmsService(){
		if(service==null){
			synchronized (sign) {
				init();
			}
		}
		return service;
	}

//	public static String testGmsService(){
//		String msg = null;
//		try {
//			
//			service.startService();
//			
//			msg = "连接设备成功";
//			
//			service.stopService();
//		} catch (Exception e) {
//			
//			msg = "连接设备不成功";
//			e.printStackTrace();
//		}
//		return msg;
//	}
//	
//	public static String resetGmsService(){
//		service = null;
//		init();
//		String msg = null;
//		try {
//			
//			service.startService();
//			
//			msg = "连接设备成功";
//			
//			service.stopService();
//		} catch (Exception e) {
//			
//			msg = "连接设备不成功";
//			e.printStackTrace();
//		}
//		return msg;
//	}
}
