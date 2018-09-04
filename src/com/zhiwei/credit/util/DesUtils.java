package com.zhiwei.credit.util;

import java.security.Key;
import java.security.Security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;

public class DesUtils {

	private static String strDefaultKey = "";//字符串默认键值
	private Cipher encryptCipher = null;//加密工具
	private Cipher decryptCipher = null;//解密工具

	/**  
	 * 将byte数组转换为表示16进制值的字符串， 如：byte[]{8,18}转换为：0813， 和public static byte[]  
	 * hexStr2ByteArr(String strIn) 互为可逆的转换过程   
	 * @param arrB  需要转换的byte数组  
	 * @return 转换后的字符串  
	 *  
	 */
	public static String byteArr2HexStr(byte[] arrB){
		int iLen = arrB.length;
		StringBuffer sb = new StringBuffer(iLen * 2);// 每个byte用两个字符才能表示，所以字符串的长度是数组长度的两倍   
		for (int i = 0; i < iLen; i++) {
			int intTmp = arrB[i];
			while (intTmp < 0) {// 把负数转换为正数   
				intTmp = intTmp + 256;
			}
			if (intTmp < 16) {// 小于0F的数需要在前面补0   
				sb.append("0");
			}
			sb.append(Integer.toString(intTmp, 16));
		}
		return sb.toString();
	}

	/**  
	 * 将表示16进制值的字符串转换为byte数组， 和public static String byteArr2HexStr(byte[] arrB)  
	 * 互为可逆的转换过程  
	 *   
	 * @param strIn  需要转换的字符串  
	 * @return 转换后的byte数组  
	 */
	public static byte[] hexStr2ByteArr(String strIn){
		byte[] arrB = strIn.getBytes();
		int iLen = arrB.length;
		byte[] arrOut = new byte[iLen / 2];// 两个字符表示一个字节，所以字节数组长度是字符串长度除以2   
		for (int i = 0; i < iLen; i = i + 2) {
			String strTmp = new String(arrB, i, 2);
			arrOut[i / 2] = (byte) Integer.parseInt(strTmp, 16);
		}
		return arrOut;
	}

	/**  
	 * 默认构造方法，使用默认密钥  
	 *   
	 * @throws Exception  
	 */
	public DesUtils() throws Exception {
		this(strDefaultKey);
	}

	/**  
	 * DES字符串加密
	 * 指定密钥构造方法  
	 * @param strKey  指定的密钥 
	 * @throws Exception  
	 */
	public DesUtils(String strKey){
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			Key key = getKey(strKey.getBytes());
			encryptCipher = Cipher.getInstance("DES");
			encryptCipher.init(Cipher.ENCRYPT_MODE, key);
			decryptCipher = Cipher.getInstance("DES");
			decryptCipher.init(Cipher.DECRYPT_MODE, key);
		} catch (Exception e) {
			e.printStackTrace();
		} 
	}

	/**  
	 * 加密字节数组  
	 *   
	 * @param arrB  需加密的字节数组 
	 * @return 加密后的字节数组  
	 * @throws Exception  
	 */
	public byte[] encrypt(byte[] arrB){
		byte[] encrypt=null;
		try {
			 encrypt=encryptCipher.doFinal(arrB);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
		return encrypt;
	}

	/**  
	 * 加密字符串  
	 *   
	 * @param strIn   需加密的字符串 
	 * @return 加密后的字符串  
	 * @throws Exception  
	 */
	public String encrypt(String strIn){
		return byteArr2HexStr(encrypt(strIn.getBytes()));
	}

	/**  
	 * 解密字节数组  
	 *   
	 * @param arrB  需解密的字节数组  
	 * @return 解密后的字节数组  
	 * @throws Exception  
	 */
	public byte[] decrypt(byte[] arrB) throws BadPaddingException{
		byte[] decrypt=null;
		try {
			decrypt= decryptCipher.doFinal(arrB);
		} catch (Exception e) {
			//e.printStackTrace();
			return null;
		}
		return decrypt;
	}

	/**  
	 * 解密字符串  
	 *   
	 * @param strIn  需解密的字符串  
	 * @return 解密后的字符串  
	 * @throws Exception  
	 */
	public String decrypt(String strIn) throws BadPaddingException{
		
		if(null==decrypt(hexStr2ByteArr(strIn))){
			return "";
		}
		return new String(decrypt(hexStr2ByteArr(strIn)));
	}

	/**  
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位  
	 *   
	 * @param arrBTmp  构成该字符串的字节数组  
	 * @return 生成的密钥  
	 * @throws java.lang.Exception  
	 */
	private Key getKey(byte[] arrBTmp) throws Exception {
		byte[] arrB = new byte[8];// 创建一个空的8位字节数组（默认值为0）   
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {// 将原始字节数组转换为8位   
			arrB[i] = arrBTmp[i];
		}
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");// 生成密钥   
		return key;
	}
	public static boolean getAuthorization(String password,String key){
		
		try{
			boolean flag=false;
			DesUtils des = new DesUtils(password);//自定义密钥   
			String code=des.decrypt(key);
			String mac=GetMACUtil.getMacStr();//机器码
			if(mac.equals(code)){
				flag=true;
			}
			return flag;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}
