package com.zhiwei.credit.util;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.EncodedKeySpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import javax.crypto.Cipher;

import org.apache.commons.net.util.Base64;


//import sun.misc.BASE64Decoder;
//import sun.misc.BASE64Encoder;

/**
 * RSA 签名、验证、加密、解密帮助类
 * 
 * @author sam
 * 
 */
public class RsaHelper
{
	// 签名对象
	private Signature sign;
	private static final RsaHelper rsaHelper = new RsaHelper();
	
	private String pubkey;
	private String prikey;
	
	private RsaHelper()
	{
		try
		{
			sign = Signature.getInstance("SHA1withRSA");
		}
		catch (NoSuchAlgorithmException nsa)
		{
			System.out.println("" + nsa.getMessage());
		}
	}
	
	public static RsaHelper getInstance()
	{
		return rsaHelper;
	}
	
	private PrivateKey getPrivateKey(String privateKeyStr)
	{
		try
		{
			byte[] privateKeyBytes = b64decode(privateKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
			return keyFactory.generatePrivate(privateKeySpec);
		}
		catch (InvalidKeySpecException e)
		{
			System.out.println("Invalid Key Specs. Not valid Key files." + e.getCause());
			return null;
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("There is no such algorithm. Please check the JDK ver." + e.getCause());
			return null;
		}
	}
	
	private PublicKey getPublicKey(String publicKeyStr)
	{
		try
		{
			byte[] publicKeyBytes = b64decode(publicKeyStr);
			KeyFactory keyFactory = KeyFactory.getInstance("RSA");
			EncodedKeySpec publicKeySpec = new X509EncodedKeySpec(publicKeyBytes);
			return keyFactory.generatePublic(publicKeySpec);
		}
		catch (InvalidKeySpecException e)
		{
			System.out.println("Invalid Key Specs. Not valid Key files." + e.getCause());
			return null;
		}
		catch (NoSuchAlgorithmException e)
		{
			System.out.println("There is no such algorithm. Please check the JDK ver." + e.getCause());
			return null;
		}
	}
	
	/**
	 * RSA 数据签名
	 * 
	 * @param toBeSigned
	 *            (待签名的原文)
	 * @param priKey
	 *            (RSA私钥）
	 * @return （返回RSA签名后的数据签名数据base64编码）
	 */
	public String signData(String toBeSigned, String priKey)
	{
		
		try
		{
			PrivateKey privateKey = getPrivateKey(priKey);
			byte[] signByte = toBeSigned.getBytes("utf-8");
			Signature rsa = Signature.getInstance("SHA1withRSA");
			rsa.initSign(privateKey);
			rsa.update(signByte);
			return b64encode(rsa.sign());
		}
		catch (NoSuchAlgorithmException ex)
		{
			System.out.println(ex);
		}
		catch (InvalidKeyException in)
		{
			System.out.println("Invalid Key file.Please check the key file path" + in.getCause());
		}
		catch (Exception se)
		{
			System.out.println(se);
		}
		return null;
	}
	
	/**
	 * RSA 数据签名验证
	 * 
	 * @param signature
	 *            （RSA签名数据（base64编码）
	 * @param data
	 *            （待验证的数据原文）
	 * @param pubKey
	 *            （RSA公钥数据）
	 * @return 返回验证结果（TRUE:验证成功；FALSE:验证失败）
	 */
	public boolean verifySignature(String signature, String data, String pubKey)
	{
		try
		{
			byte[] signByte = b64decode(signature);
			byte[] dataByte = data.getBytes("utf-8");
			PublicKey publicKey = getPublicKey(pubKey);
			sign.initVerify(publicKey);
			sign.update(dataByte);
			return sign.verify(signByte);
		}
		catch (SignatureException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * base64编码
	 * 
	 * @param data
	 * @return
	 */
	private String b64encode(byte[] data)
	{
		return Base64.encodeBase64String(data);
		//return new BASE64Encoder().encode(data);
	}
	
	/**
	 * base64解码
	 * 
	 * @param data
	 * @return
	 */
	private byte[] b64decode(String data)
	{
		try
		{
			return Base64.decodeBase64(data);
			//return new BASE64Decoder().decodeBuffer(data);
		}
		catch (Exception ex)
		{
		}
		return null;
	}
	
	/**
	 * RSA数据加密
	 * 
	 * @param data
	 *            （需要加密的数据）
	 * @param pubKey
	 *            （RSA公钥）
	 * @return 返回加密后的密文（BASE64编码）
	 */
	public String encryptData(String data, String pubKey)
	{
		try
		{
			byte[] dataByte = data.getBytes("utf-8");
			PublicKey publicKey = getPublicKey(pubKey);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);
			return b64encode(cipher.doFinal(dataByte));
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * RSA数据解密
	 * 
	 * @param encryptedData
	 *            （需要解密的数据base64编码数据）
	 * @param priKey
	 *            （RSA的私钥）
	 * @return 返回解密后的原始明文
	 */
	public String decryptData(String encryptedData, String priKey)
	{
		try
		{
			byte[] encryData = b64decode(encryptedData);
			PrivateKey privateKey = getPrivateKey(priKey);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);
			return new String(cipher.doFinal(encryData), "utf-8");
		}
		catch (Exception e)
		{
			return null;
		}
	}
	
	/**
	 * 得到私钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getPriKeyString(PrivateKey key) throws Exception
	{
		byte[] keyBytes = key.getEncoded();
		//String s = (new BASE64Encoder()).encode(keyBytes);
		String s =Base64.encodeBase64String(keyBytes);
		return s;
	}
	
	/**
	 * 得到公钥字符串（经过base64编码）
	 * 
	 * @return
	 */
	public static String getPubKeyString(PublicKey key) throws Exception
	{
		byte[] keyBytes = key.getEncoded();
		//String s = (new BASE64Encoder()).encode(keyBytes);
		String s=Base64.encodeBase64String(keyBytes);
		return s;
	}
	
	/**
	 * 生成密钥 自动产生RSA1024位密钥
	 * 
	 * @throws NoSuchAlgorithmException
	 * @throws IOException
	 */
	public void getAutoCreateRSA() throws NoSuchAlgorithmException, IOException
	{
		try
		{
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			KeyPair kp = kpg.genKeyPair();
			PublicKey puk = kp.getPublic();
			PrivateKey prk = kp.getPrivate();
			
			pubkey = getPubKeyString(puk);
			prikey = getPriKeyString(prk);
			System.out.print("pubkey==:"+pubkey.replaceAll("\r", "").replaceAll("\n", ""));
			System.out.print("prikey==:"+prikey.replaceAll("\r", "").replaceAll("\n", ""));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public String getPubkey()
	{
		return pubkey;
	}
	
	public void setPubkey(String pubkey)
	{
		this.pubkey = pubkey;
	}
	
	public String getPrikey()
	{
		return prikey;
	}
	
	public void setPrikey(String prikey)
	{
		this.prikey = prikey;
	}
	
}
