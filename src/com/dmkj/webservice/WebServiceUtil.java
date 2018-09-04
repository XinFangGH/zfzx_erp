package com.dmkj.webservice;


import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;

public class WebServiceUtil {
	
	
	public WebServiceUtil(){
		try{
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			String webServicePath = PropertiesUtil.getProperty(getUrl());
			client = dcf.createClient(webServicePath);
		}catch (Exception e) {
		}
		
	}
	
	public WebServiceUtil(String url){
		try{
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			String webServicePath = PropertiesUtil.getProperty(url);
			client = dcf.createClient(webServicePath);
		}catch (Exception e) {
		}
	}
	
	private static Client client = null;
	
	/**
	 * ����webservice ·��
	 */
	private String url = "url";
	
	
	
	public String uploadWhiteUser(String ECCode, String AdminNum, String AdminPass, String PhoneList){
		try{
			Object[] results = client.invoke("UploadWhiteUser",new Object[] {ECCode, AdminNum, AdminPass, PhoneList});
			return results[0].toString();
		}catch (Exception e) {
			return "";
		}
	}
	public String getWhiteUserStatus(String ECCode, String AdminNum, String AdminPass, String PhoneList){
		try{
			Object[] results = client.invoke("GetWhiteUserStatus",new Object[] {ECCode, AdminNum, AdminPass, PhoneList});
			return results[0].toString();
		}catch (Exception e) {
			return "";
		}
	}
	
	
	
	/**
	 * ���Ͷ���
	 * @param ECCode 		���ű���
	 * @param AdminNum		��½�û���
	 * @param AdminPass		��½����
	 * @param PhoneList		��˵ĵ绰���� ÿ�������ύ15������
	 * 						��ʽ��13888880001,13888880002,13888880003,13888880004
	 * @param Msg			�������ݣ�300�������ڣ����������ش���
	 * @param SmsType		�������0��ͨ����  1������
	 * @return
	 * <?xml version="1.0" encoding="gb2312" ?>
	 * <Body>
	 * <ECCode> ��ļ��ű��� </ECCode>
	 * <Stat> ״̬�� </Stat>				״̬�룬�����ɹ���Ϊ0�����Stat��Ϊ0���ɸ�ݸ�¼�еĴ����룬�鿴ԭ��
	 * <Info_ID>��Ϣ�� </Info_ID>		��Ϣ�룬���ڻ�ȡ����״̬���档�������ύ������ͨƽ̨�Ὣ�ö��ŷ��ͳ�ȥ��
	 * 									���Ƕ����Ƿ��ܳɹ��·�����Ҫ�����Ϣ�룬��ȡ״̬���档
										ע�⣺�ڷ��Ͷ���ʱ��ÿ���ύ�ĺ���������15�����������ݵ�����300�֣�
										��Ϣ���ֻ��Ϊ0����1������0Ϊ��ͨ�̶��ţ�1Ϊ�����š����ύ�Ĺ���У�
										����һ������ִ��󣬶��޷���ȷ�ύ��
	 * </Body>
	 */
	public String sendSms(String ECCode, String AdminNum, String AdminPass, String PhoneList, String Msg,String SmsType){
		try{
			Object[] results = client.invoke("SendSms",new Object[] {ECCode, AdminNum, AdminPass, PhoneList,Msg,SmsType});
			return results[0].toString();
		}catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * ��ȡ״̬����
	 * @param ECCode 		���ű���
	 * @param AdminNum		��½�û���
	 * @param AdminPass		��½����
	 * @param PhoneList		��˵ĵ绰���� ÿ�������ύ15������
	 * 						��ʽ��13888880001-1111111,13888880002-2222222,13888880003,13888880004
	 * @return
	 * <?xml version="1.0" encoding="gb2312" ?>
	 * <Body>
	 * <ECCode> ��ļ��ű��� </ECCode>
	 * <Stat> ״̬�� </Stat>			״̬�룬�����ɹ���Ϊ0�����Stat��Ϊ0���ɸ�ݸ�¼�еĴ����룬�鿴ԭ��
	 * <Report> ״̬���� </Report>	״̬���档״̬����Ϊһ�ַ������ַ�Ϊ���������Ϣ�ð�Ƕ��ţ�,��������
	  								һ��������Ϣ֮�����Ϣ�ü���(-)��������Ϣ����Ϊ���ֻ����-��ϢID-״̬�롣���£�
									13888880001-1111111-1,13888880002-222222-0,
									״̬�룺0ʧ�ܣ�1�ɹ� ��2״̬����ȷ��״̬����ȷʱ����ʾ����ͨϵͳδ���յ����ֻ�����״̬��
									��Ҫ�ȴ�һ��ʱ����ٴγ��ԣ�����ʱ�䳬��48Сʱ����ö���Ϊʧ�ܡ�
	 * </Body>
	 */
	public String getSmsReport(String ECCode, String AdminNum, String AdminPass,String PhoneList){
		try{
			Object[] results = client.invoke("GetSmsReport",new Object[] {ECCode, AdminNum, AdminPass, PhoneList});
			return results[0].toString();
		}catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * ��ȡ���ж���
	 * @param ECCode 		���ű���
	 * @param AdminNum		��½�û���
	 * @param AdminPass		��½����
	 * @return
	 * <?xml version="1.0" encoding="gb2312" ?>
	 * <Body>
	 * <ECCode> ��ļ��ű��� </ECCode>
	 * <Stat> ״̬�� </Stat>			״̬�룬�����ɹ���Ϊ0�����Stat��Ϊ0���ɸ�ݸ�¼�еĴ����룬�鿴ԭ��
	 * <Info> ���ж��� </Info>		��StatΪ0ʱ���ɷ������صĸ��ַ����û��������Ϣ���򷵻ص��ַ�Ϊ�ա�
	 * 								�������Ϣ��������Ϣ������������߸�����||����һ����Ϣ֮���ð���Ǻ�(*)������
	 * 								��ʽΪ���ֻ����*��������*����ʱ�䣬���£�
									13888880001*����*2010-10-1-1 12:00:00||
									ע�⣺����һ�θú��������ж��ţ�����5����ٴε��øú������û�ж��ţ�
									����30����ٵ��ã��������׵����˺ű���
	 * </Body>
	 */
	public String getRevSms(String ECCode, String AdminNum, String AdminPass){
		try{
			Object[] results = client.invoke("GetRevSms",new Object[] {ECCode, AdminNum, AdminPass});
			return results[0].toString();
		}catch (Exception e) {
			return "";
		}
	}
	
	public static Client getClient() {
		return client;
	}

	public static void setClient(Client client) {
		WebServiceUtil.client = client;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}	
}
