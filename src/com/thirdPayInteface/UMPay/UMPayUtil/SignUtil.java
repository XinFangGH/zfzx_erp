
package com.thirdPayInteface.UMPay.UMPayUtil;

import java.security.Signature;
import java.security.cert.X509Certificate;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * ***********************************************************************
 * <br>description : 签名验签工具类
 * @author      umpay
 * @date        2014-8-1 上午09:39:24
 * @version     1.0  
 ************************************************************************
 */
public class SignUtil {

	protected static Log log_=LogFactory.getLog(SignUtil.class);
	public static final String KEY_ALGORITHM = "RSA";
	
	/**
	 * 
	 * <br>description : 商户请求平台数据签名
	 * @param plain
	 * @param merId
	 * @return
	 * @version     1.0
	 * @date        2014-8-1上午09:39:50
	 */
	public static String sign(String plain,String merId){
		log_.debug("签名：plain="+plain);
		try{
			Signature sig = Signature.getInstance("SHA1withRSA");
	        sig.initSign(PkCertFactory.getPk(merId));
	        sig.update(plain.getBytes("gbk"));
	        byte signData[] = sig.sign();
	        String sign = new String(Base64.encode(signData));
	        log_.debug("签名：sign="+sign);
	        return sign;
		}catch(Exception ex){
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
	}
	
	/**
	 * 
	 * <br>description : 商户验证平台数据签名
	 * @param sign
	 * @param plain
	 * @return
	 * @version     1.0
	 * @date        2014-8-1上午09:40:03
	 */
	public static boolean verify(String sign,String plain){
		log_.debug("验签：sign="+sign);

		log_.debug("验签：plain="+plain);
		X509Certificate cert = PkCertFactory.getCert();
		log_.info("获取证书成功!");
		try{
			byte[] signData = Base64.decode(sign.getBytes());
			 Signature sig = Signature.getInstance("SHA1withRSA");
	            sig.initVerify(cert);
	            sig.update(plain.getBytes("gbk"));
	            boolean b = sig.verify(signData);
	            log_.info("验证平台签名是否成功"+b);
	            return b;
		}catch(Exception ex){
			log_.info("解密异常"+ ex);
			RuntimeException rex = new RuntimeException(ex.getMessage());
			rex.setStackTrace(ex.getStackTrace());
			throw rex;
		}
	}
}
