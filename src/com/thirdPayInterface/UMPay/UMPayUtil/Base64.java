package com.thirdPayInterface.UMPay.UMPayUtil;
import java.net.URLDecoder;
import java.net.URLEncoder;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * ***********************************************************************
 * <br>description : base64工具类
 * @author      umpay
 * @date        2014-7-24 下午07:30:02
 * @version     1.0  
 ************************************************************************
 */
public final class Base64 {
    static private final int BASELENGTH = 255;
    static private final int LOOKUPLENGTH = 64;
    static private final int TWENTYFOURBITGROUP = 24;
    static private final int EIGHTBIT = 8;
    static private final int SIXTEENBIT = 16;
    static private final int FOURBYTE = 4;
    static private final int SIGN = -128;
    static private final byte PAD = ( byte) '=';
    static private byte[] base64Alphabet = new byte[BASELENGTH];
    static private byte[] lookUpBase64Alphabet = new byte[LOOKUPLENGTH];

    static {
        for (int i = 0; i < BASELENGTH; i++) {
            base64Alphabet[i] = -1;
        }
        for (int i = 'Z'; i >= 'A'; i--) {
            base64Alphabet[i] = ( byte) (i - 'A');
        }
        for (int i = 'z'; i >= 'a'; i--) {
            base64Alphabet[i] = ( byte) (i - 'a' + 26);
        }
        for (int i = '9'; i >= '0'; i--) {
            base64Alphabet[i] = ( byte) (i - '0' + 52);
        }

        base64Alphabet['+'] = 62;
        base64Alphabet['/'] = 63;

        for (int i = 0; i <= 25; i++)
            lookUpBase64Alphabet[i] = ( byte) ('A' + i);

        for (int i = 26, j = 0; i <= 51; i++, j++)
            lookUpBase64Alphabet[i] = ( byte) ('a' + j);

        for (int i = 52, j = 0; i <= 61; i++, j++)
            lookUpBase64Alphabet[i] = ( byte) ('0' + j);

        lookUpBase64Alphabet[62] = ( byte) '+';
        lookUpBase64Alphabet[63] = ( byte) '/';
    }

    public static boolean isBase64(String isValidString) {
        return isArrayByteBase64(isValidString.getBytes());
    }

    public static boolean isBase64(byte octect) {
        //shall we ignore white space? JEFF??
        return (octect == PAD || base64Alphabet[octect] != -1);
    }

    public static boolean isArrayByteBase64(byte[] arrayOctect) {
        int length = arrayOctect.length;
        if (length == 0) {
            // shouldn't a 0 length array be valid base64 data?
            // return false;
            return true;
        }
        for (int i = 0; i < length; i++) {
            if (!Base64.isBase64(arrayOctect[i]))
                return false;
        }
        return true;
    }

    /**
     * Encodes hex octects into Base64.
     * 
     * @param binaryData Array containing binary data to encode.
     * @return Base64-encoded data.
     */
    public static byte[] encode(byte[] binaryData) {
        int lengthDataBits = binaryData.length
            * EIGHTBIT;
        int fewerThan24bits = lengthDataBits
            % TWENTYFOURBITGROUP;
        int numberTriplets = lengthDataBits
            / TWENTYFOURBITGROUP;
        byte[] encodedData = null;

        if (fewerThan24bits != 0) {
            //data not divisible by 24 bit
            encodedData = new byte[(numberTriplets + 1) * 4];
        } else {
            // 16 or 8 bit
            encodedData = new byte[numberTriplets * 4];
        }

        byte k = 0, l = 0, b1 = 0, b2 = 0, b3 = 0;

        int encodedIndex = 0;
        int dataIndex = 0;
        int i = 0;
        for (i = 0; i < numberTriplets; i++) {
            dataIndex = i * 3;
            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            b3 = binaryData[dataIndex + 2];

            l = ( byte) (b2 & 0x0f);
            k = ( byte) (b1 & 0x03);

            encodedIndex = i * 4;
            byte val1 = ((b1 & SIGN) == 0) ? ( byte) (b1 >> 2)
                    : ( byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? ( byte) (b2 >> 4)
                    : ( byte) ((b2) >> 4 ^ 0xf0);
            byte val3 = ((b3 & SIGN) == 0) ? ( byte) (b3 >> 6)
                    : ( byte) ((b3) >> 6 ^ 0xfc);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2
                | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[(l << 2)
                | val3];
            encodedData[encodedIndex + 3] = lookUpBase64Alphabet[b3 & 0x3f];
        }

        // form integral number of 6-bit groups
        dataIndex = i * 3;
        encodedIndex = i * 4;
        if (fewerThan24bits == EIGHTBIT) {
            b1 = binaryData[dataIndex];
            k = ( byte) (b1 & 0x03);
            byte val1 = ((b1 & SIGN) == 0) ? ( byte) (b1 >> 2)
                    : ( byte) ((b1) >> 2 ^ 0xc0);
            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[k << 4];
            encodedData[encodedIndex + 2] = PAD;
            encodedData[encodedIndex + 3] = PAD;
        } else if (fewerThan24bits == SIXTEENBIT) {

            b1 = binaryData[dataIndex];
            b2 = binaryData[dataIndex + 1];
            l = ( byte) (b2 & 0x0f);
            k = ( byte) (b1 & 0x03);

            byte val1 = ((b1 & SIGN) == 0) ? ( byte) (b1 >> 2)
                    : ( byte) ((b1) >> 2 ^ 0xc0);
            byte val2 = ((b2 & SIGN) == 0) ? ( byte) (b2 >> 4)
                    : ( byte) ((b2) >> 4 ^ 0xf0);

            encodedData[encodedIndex] = lookUpBase64Alphabet[val1];
            encodedData[encodedIndex + 1] = lookUpBase64Alphabet[val2
                | (k << 4)];
            encodedData[encodedIndex + 2] = lookUpBase64Alphabet[l << 2];
            encodedData[encodedIndex + 3] = PAD;
        }

        return encodedData;
    }

    /**
     * Decodes Base64 data into octects
     * 
     * @param binaryData Byte array containing Base64 data
     * @return Array containing decoded data.
     */
    public static byte[] decode(byte[] base64Data) {
        // handle the edge case, so we don't have to worry about it later
        if (base64Data.length == 0) {
            return new byte[0];
        }

        int numberQuadruple = base64Data.length
            / FOURBYTE;
        byte[] decodedData = null;
        byte b1 = 0, b2 = 0, b3 = 0, b4 = 0, marker0 = 0, marker1 = 0;

        // Throw away anything not in base64Data

        int encodedIndex = 0;
        int dataIndex = 0;
        {
            // this sizes the output array properly - rlw
            int lastData = base64Data.length;
            // ignore the '=' padding
            while (base64Data[lastData - 1] == PAD) {
                if (--lastData == 0) {
                    return new byte[0];
                }
            }
            decodedData = new byte[lastData
                - numberQuadruple];
        }

        for (int i = 0; i < numberQuadruple; i++) {
            dataIndex = i * 4;
            marker0 = base64Data[dataIndex + 2];
            marker1 = base64Data[dataIndex + 3];

            b1 = base64Alphabet[base64Data[dataIndex]];
            b2 = base64Alphabet[base64Data[dataIndex + 1]];

            if (marker0 != PAD
                && marker1 != PAD) {
                //No PAD e.g 3cQl
                b3 = base64Alphabet[marker0];
                b4 = base64Alphabet[marker1];

                decodedData[encodedIndex] = ( byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = ( byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
                decodedData[encodedIndex + 2] = ( byte) (b3 << 6 | b4);
            } else if (marker0 == PAD) {
                //Two PAD e.g. 3c[Pad][Pad]
                decodedData[encodedIndex] = ( byte) (b1 << 2 | b2 >> 4);
            } else if (marker1 == PAD) {
                //One PAD e.g. 3cQ[Pad]
                b3 = base64Alphabet[marker0];

                decodedData[encodedIndex] = ( byte) (b1 << 2 | b2 >> 4);
                decodedData[encodedIndex + 1] = ( byte) (((b2 & 0xf) << 4) | ((b3 >> 2) & 0xf));
            }
            encodedIndex += 3;
        }
        return decodedData;
    }

	/**BASE64编码，去除BASE64后多余的“\n”,再URLEncode
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String doBase64URLEncode(String src)
		throws ParameterCheckException {
			try{
				byte[] raw = src.getBytes();
				BASE64Encoder encoder = new BASE64Encoder();
				String base64data = encoder.encode(raw);
				base64data = filter(base64data);
				base64data = doURLEncode(base64data);
				return base64data;
			}catch(Exception e){
				throw new ParameterCheckException("src进行Base64解码失败");
			}
		

	}
	/**BASE64编码，去除BASE64后多余的“\n”,再URLEncode（指定编码字符集）
	 * @param src
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public static String doBase64URLEncode(String src,String enc)
	throws Exception {
		
		byte[] raw = src.getBytes();
		BASE64Encoder encoder = new BASE64Encoder();
		String base64data = encoder.encode(raw);
		base64data = filter(base64data);
		base64data = doURLEncode(base64data,enc);
		return base64data;

	}
	
	/**BASE64解码，URLDecode后BASE64Decoder解码
	 * @param src
	 * @return
	 * @throws Exception
	 */
	public static String doBase64URLDecode(String src)
	throws ParameterCheckException {
		try{
			src = doURLDecode(src);
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] raw = decoder.decodeBuffer(src);
			return new String(raw);
		}catch(Exception e){
			throw new ParameterCheckException("src进行Base64解码失败");
		}
	}
	/**BASE64解码，URLDecode后BASE64Decoder解码（指定编码字符集）
	 * @param src
	 * @param enc
	 * @return
	 * @throws Exception
	 */
	public static String doBase64URLDecode(String src,String enc)
	throws Exception {
		src = doURLDecode(src,enc);
		BASE64Decoder decoder = new BASE64Decoder();
		byte[] raw = decoder.decodeBuffer(src);
		return new String(raw);

	}
	
	/**
	 * 去掉字符串的换行符号
	 * base64编码3-DES的数据时，得到的字符串有换行符号
	 * 
	 */
	 private static String filter(String str) {
		  String output = null;
		  StringBuffer sb = new StringBuffer();
		  for(int i = 0; i < str.length(); i++)
		  {
		  int asc = str.charAt(i);
		  if(asc != 10 && asc != 13)
		  sb.append(str.subSequence(i, i + 1));
		  }
		  output = new String(sb);
		  return output;
	 }
	 /**
	  * 对字符串进行URLDecoder.encode(src)编码
	  * @param String src 要进行编码的字符串
	  * @return  String 进行编码后的字符串
	  */
	 
	 private static String doURLEncode(String src) {
		  String result = "";
		  try{
			  result = URLEncoder.encode(src);
		  }
		  catch(Exception e){
		    e.printStackTrace();
		  }
		  return result;
	 }
	 /**
	  * 对字符串进行URLDecoder.encode(src)编码
	  * @param String src 要进行编码的字符串
	  * @param String enc 编码字符集
	  * @return  String 进行编码后的字符串
	 */
	private static String doURLEncode(String src,String enc) {
		  String result = "";
		  try{
			  result = URLEncoder.encode(src,enc);
		  }
		  catch(Exception e){
		    e.printStackTrace();
		  }
		  return result;
	 }
	 /**
	  * 对字符串进行URLDecoder.decode(src)解码
	  * @param String src 要进行解码的字符串
	  * @return  String 进行解码后的字符串
	  */
	 
	 private static String doURLDecode(String src)
	 {   
		  String result="";
		  try{
			  result = URLDecoder.decode(src);
		  }
		  catch(Exception e){
		    e.printStackTrace();
		  }
		  return result;
	 }	 
	 /**
	  * 对字符串进行URLDecoder.decode解码
	  * @param String src 要进行解码的字符串
	  * @param String enc 编码字符集
	  * @return  String 进行解码后的字符串
	 */
	private static String doURLDecode(String src,String enc)
	 {   
		  String result="";
		  try{
			  result = URLDecoder.decode(src,enc);
		  }
		  catch(Exception e){
		    e.printStackTrace();
		  }
		  return result;
	 }
    
}