
package com.thirdPayInterface.UMPay;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import com.zhiwei.core.util.AppUtil;
import com.thirdPayInterface.ThirdPayConstants;

/**
 * ***********************************************************************
 * <br>description : 证书工厂类
 * @author      umpay
 * @date        2014-7-25 上午11:43:36
 * @version     1.0  
 ************************************************************************
 */
public class PkCertFactory {

	/**生产平台证书*/
	private static X509Certificate umpCert;
	private final static Map pkMap = new HashMap();
	private final static Map configMap = AppUtil.getConfigMap();
	//private final static String pkSuffix = AppUtil.getAppAbsolutePath().toString()+configMap.get("thirdPay_umpay_Key").toString().trim();
	//private final static String platCertPath=AppUtil.getAppAbsolutePath().toString()+configMap.get("thirdPay_umpay_certPath").toString().trim();
	/**
	 * 第三方支付环境（正式环境和测试环境）
	 */
	private static String  thirdPayEnvironmentType=configMap.containsKey("thirdPayEnvironmentType")?configMap.get("thirdPayEnvironmentType").toString().trim():ThirdPayConstants.THIRDENVIRONMENT1;
	
	//初始化联动证书
	static{
		try{
			Map thirdPayConfig=umProperty();
			String platCertPath = thirdPayConfig.get("thirdPay_umpay_certPath").toString();
			String basePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
			platCertPath = basePath + platCertPath;
			byte[] b = null;
			InputStream in = null;
			try{
				if(null == platCertPath)throw new RuntimeException("没有找到配置信息"+platCertPath);
				in = new FileInputStream(new File(platCertPath));
				if(null == in)throw new RuntimeException("文件不存在"+platCertPath);
				b = new byte[20480];
				in.read(b);
			}finally{
				if(null!=in)in.close();
			}
			umpCert = getCert(b);
		}catch(Exception ex){
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
	}
	/**
	 * 获取第三方支付环境参数
	 * @return
	 */
	private static Map umProperty(){
		Map umConfigMap=new HashMap();
		try{
			InputStream in=null;
			//获取当前支付环境为正式环境还是测试环境
			if(thirdPayEnvironmentType.equals(ThirdPayConstants.THIRDENVIRONMENT0)){//正式环境
		       in = UMPayInterfaceUtil.class.getResourceAsStream("UMPayNormalEnvironment.properties"); 
			}else{
		        in = UMPayInterfaceUtil.class.getResourceAsStream("UMPayTestEnvironment.properties"); 
			}
			Properties props =  new  Properties(); 
			if(in!=null){
				props.load(in);
		    	Iterator it= props.keySet().iterator();
		    	while(it.hasNext()){
		    		String key=(String)it.next();
		    		umConfigMap.put(key, props.getProperty(key));
		    	}
			}
		}catch(Exception ex){
			ex.printStackTrace();
    	}
		return umConfigMap;
	}
	/**
	 * 获取证书字节数组
	 * @return
	 */
	public static byte[] getCertByte(){
		byte[] b = null;
		try{
			InputStream in = null;
			Map thirdPayConfig=umProperty();
			String platCertPath = thirdPayConfig.get("thirdPay_umpay_certPath").toString();
			String basePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
			platCertPath = basePath + platCertPath;
			
			try{
				if(null == platCertPath)throw new RuntimeException("没有找到配置信息"+platCertPath);
				in = new FileInputStream(new File(platCertPath));
				if(null == in)throw new RuntimeException("文件不存在"+platCertPath);
				b = new byte[20480];
				in.read(b);
			}finally{
				if(null!=in)in.close();
			}
			//b = ProFileUtil.getFileByte(platCertPath);
		}catch(Exception ex){
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
		return b;
	}
	
	/**
	 * 
	 * <br>description : 根据商户号获取私钥
	 * @param merId
	 * @return
	 * @version     1.0
	 * @date        2014-7-25下午01:34:23
	 */
	public static PrivateKey getPk(String merId){
		if(pkMap.containsKey(merId))
			return (PrivateKey)pkMap.get(merId);//返回缓存的私钥
		else{
			synchronized (pkMap) {
				if(pkMap.containsKey(merId))return (PrivateKey)pkMap.get(merId);
				try{
					byte[] b = null;
					InputStream in = null;
					try{
						Map thirdPayConfig=umProperty();
						String pkSuffix = thirdPayConfig.get("thirdPay_umpay_Key").toString();
						String basePath = Thread.currentThread().getContextClassLoader().getResource("").toURI().getPath();
						pkSuffix = basePath + pkSuffix;
						if(null == pkSuffix)throw new RuntimeException("没有找到配置信息"+pkSuffix);
						in = new FileInputStream(new File(pkSuffix));
						if(null == in)throw new RuntimeException("文件不存在"+pkSuffix);
						b = new byte[20480];
						in.read(b);
					}finally{
						if(null!=in)in.close();
					}
					//byte[] b = ProFileUtil.getFileByte(merId+pkSuffix);
					PrivateKey retVal = getPk(b);//获取商户私钥
					pkMap.put(merId, retVal);//缓存商户私钥
					return retVal;
				}catch(Exception ex){
					RuntimeException rex = new RuntimeException(ex.getMessage());
					rex.setStackTrace(ex.getStackTrace());
					throw rex;
				}
			}
		}
	}
	
	public static X509Certificate getCert(){
		return umpCert;
	}
	
	/**
	 * 
	 * <br>description : 生成平台公钥证书对象
	 * @param b
	 * @return
	 * @version     1.0
	 * @date        2014-7-25上午11:56:05
	 */
	private static X509Certificate getCert(byte[] b){
		try {
			ByteArrayInputStream bais = new ByteArrayInputStream(b);
			CertificateFactory cf = CertificateFactory.getInstance("X.509");
			X509Certificate x509Certificate = (X509Certificate)cf.generateCertificate(bais);
			return x509Certificate;
		} catch (CertificateException e) {
			RuntimeException rex = new RuntimeException();
			rex.setStackTrace(e.getStackTrace());
			throw rex;
		}
	}
	
	/**
	 * 
	 * <br>description : 生成商户私钥证书对象
	 * @param b
	 * @return
	 * @version     1.0
	 * @date        2014-7-25上午11:56:30
	 */
	private static PrivateKey getPk(byte[] b){
		PKCS8EncodedKeySpec peks = new PKCS8EncodedKeySpec(b);
        KeyFactory kf;
        PrivateKey pk;
		try {
			kf = KeyFactory.getInstance("RSA");
			pk = kf.generatePrivate(peks);
			return pk;
		} catch (NoSuchAlgorithmException e) {
			RuntimeException rex = new RuntimeException();
			rex.setStackTrace(e.getStackTrace());
			throw rex;
		}catch (InvalidKeySpecException e) {
			RuntimeException rex = new RuntimeException();
			rex.setStackTrace(e.getStackTrace());
			throw rex;
		}
	}
	
}
