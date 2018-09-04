package com.zhiwei.credit.core.creditUtils;

import org.apache.commons.codec.BinaryDecoder; 
import org.apache.commons.codec.BinaryEncoder; 
import org.apache.commons.codec.DecoderException; 
import org.apache.commons.codec.EncoderException; 
import org.apache.commons.codec.binary.Base64; 
import org.apache.commons.codec.binary.BinaryCodec; 
import org.apache.commons.codec.binary.Hex; 
import org.apache.commons.codec.digest.DigestUtils; 
import org.apache.log4j.Logger; 

import com.hurong.core.util.DateUtil;
import com.zhiwei.credit.util.Common;
 
import java.util.*; 
 
/** 
 * 字符串相关的工具类 
 * 
 * @author liujun 
 */ 
public class StringUtils { 
 
    private static final String HEX_DIGITS = "0123456789ABCDEF"; 
    private static final Logger LOG = Logger.getLogger(StringUtils.class); 
    private static final String COMMA = ","; 
    private static final String EMPTY = ""; 
    private static final String BLANK_SPACE = " "; 
 
    /** 
     * 通配符 % 
     */ 
    public static final String WILDCARD_STRING = "%"; 
 
    /** 
     * 通配符: 加到右边 
     */ 
    public static final int WILDCARD_RIGHT = 1; 
 
    /** 
     * 通配符: 加到两边 
     */ 
    public static final int WILDCARD_BOTH = 0; 
 
    /** 
     * 加密方式: SHA 
     */ 
    public static final String SHA = "SHA"; 
 
    /** 
     * 加密方式: MD5 
     */ 
    public static final String MD5 = "MD5"; 
 
    /** 
     * 加密方式: 普通文本 
     */ 
    public static final String PLAIN = "Plain"; 
 
    /** 
     * 表示true的字符串 
     */ 
    public static final String[] TRUE_STRING = {"true", "yes", "on", "是", "はい"}; 
 
    /** 
     * 判断str是否在strArray中 
     * 
     * @param str      字符串 
     * @param strArray 字符串数组 
     * @return true存在；false不存在 
     */ 
    public static boolean isStringInArray(String str, String[] strArray) { 
        for (String a : strArray) { 
            if (a.equals(str)) { 
                return true; 
            } 
        } 
        return false; 
    } 
 
    /** 
     * 字符是否在字符数组中 
     * 
     * @param c     字符 
     * @param array 数组 
     * @return true:在,false:不在 
     */ 
    public static boolean isCharInArray(char c, char[] array) { 
        for (char ca : array) { 
            if (ca == c) { 
                return true; 
            } 
        } 
        return false; 
    } 
 
    /** 
     * 把传入的str根据pos加上通配符 %,如果str为null或者空字符串不做任何操作 
     * 
     * @param str 要加通配符的字符串 
     * @param pos 通配符的位置 WILDCARD_RIGHT、WILDCARD_BOTH 
     * @return 处理过的字符串 
     */ 
    public static String wildcard(String str, int pos) { 
        String result = str; 
        if (str != null && !str.trim().equals("")) { 
            if (pos == WILDCARD_RIGHT) { 
                result = str.trim() + WILDCARD_STRING; 
            } else if (pos == WILDCARD_BOTH) { 
                result = WILDCARD_STRING + str.trim() + WILDCARD_STRING; 
            } 
        } 
        return result; 
    } 
 
    /** 
     * 把传入的str两边加上通配符 %,如果str为null或者空字符串不做任何操作 
     * 
     * @param str 要加通配符的字符串 
     * @return 处理过的字符串 
     */ 
    public static String wildcardBoth(String str) { 
        return wildcard(str, WILDCARD_BOTH); 
    } 
 
    /** 
     * 把传入的str右边加上通配符 %,如果str为null或者空字符串不做任何操作 
     * 
     * @param str 要加通配符的字符串 
     * @return 处理过的字符串 
     */ 
    public static String wildcardRight(String str) { 
        return wildcard(str, WILDCARD_RIGHT); 
    } 
 
    /** 
     * 把Map中的key对应的字符串加上通配符 
     * 
     * @param map 要为其中的字符串加上通配符的Map 
     * @param key 要进行本操作的字符串的key 
     * @param pos 通配符的位置 WILDCARD_RIGHT、WILDCARD_BOTH 
     */ 
    public static void wildcard(Map<String, Object> map, String key, int pos) { 
        if (map != null) { 
            Object obj = map.get(key); 
            if ((obj != null) && (!isEmpty(obj.toString()))) { 
                String result = obj.toString(); 
                if (pos == WILDCARD_RIGHT) { 
                    map.put(key, StringUtils.wildcard(result, WILDCARD_RIGHT)); 
                } else if (pos == WILDCARD_BOTH) { 
                    map.put(key, StringUtils.wildcard(result, WILDCARD_BOTH)); 
                } 
            } 
        } 
    } 
 
    /** 
     * 加密字符串,可以通过decodeString解密 
     * 
     * @param src       字符串 
     * @param algorithm 算法,支持Hex,Base64,Binary 
     * @return 加密后的字符串 
     */ 
    public static String encodeString(String src, String algorithm) { 
        String result = src; 
        BinaryEncoder encoder = null; 
        if (algorithm.equalsIgnoreCase("Hex")) { 
            encoder = new Hex(); 
        } else if (algorithm.equalsIgnoreCase("Base64")) { 
            encoder = new Base64(); 
        } else if (algorithm.equalsIgnoreCase("Binary")) { 
            encoder = new BinaryCodec(); 
        } 
        if (encoder != null) { 
            try { 
                result = new String(encoder.encode(src.getBytes())); 
            } catch (EncoderException e) { 
                LOG.warn(e.getMessage(), e); 
            } 
        } 
        return result; 
    } 
 
    /** 
     * 解密字符串,字符串必须是通过encodeString加密的 
     * 
     * @param src       字符串 
     * @param algorithm 算法,支持Hex,Base64,Binary 
     * @return 加密后的字符串 
     */ 
    public static String decodeString(String src, String algorithm) { 
        String result = src; 
        BinaryDecoder decoder = null; 
        if (algorithm.equalsIgnoreCase("Hex")) { 
            decoder = new Hex(); 
        } else if (algorithm.equalsIgnoreCase("Base64")) { 
            decoder = new Base64(); 
        } else if (algorithm.equalsIgnoreCase("Binary")) { 
            decoder = new BinaryCodec(); 
        } 
        if (decoder != null) { 
            try { 
                result = new String(decoder.decode(src.getBytes())); 
            } catch (DecoderException e) { 
                LOG.warn(e.getMessage(), e); 
            } 
        } 
        return result; 
    } 
 
    /** 
     * 判断字符串是否为空, null,或trim size==0 
     * 
     * @param src 要判断的字符串 
     * @return true: 空, false:非空 
     */ 
    public static boolean isEmpty(String src) { 
        return src == null || src.trim().length() == 0; 
    } 
 
    /** 
     * 截取字符串 
     * 
     * @param src    要截取的字符串 
     * @param length 长度 
     * @param end    结尾 
     * @return 截取后的字符串 
     */ 
    public static String truncate(String src, int length, String end) { 
        if (src == null) { 
            return null; 
        } 
 
        if (src.length() > length) { 
            return src.substring(0, length) + end; 
        } else { 
            return src; 
        } 
    } 
 
    /** 
     * 取得字符串中的所有空格 
     * 
     * @param src 字符串 
     * @return 没有空格的字符串 
     */ 
    public static String removeAllBlank(String src) { 
        if (src == null) { 
            return null; 
        } else { 
            return src.replaceAll("\\s+", ""); 
        } 
    } 
 
    public static String htmlEncode(String s) { 
        return htmlEncode(s, true); 
    } 
 
    /** 
     * Escape html entity characters and high characters (eg "curvy" Word 
     * quotes). Note this method can also be used to encode XML. 
     * 
     * @param s                  the String to escape. 
     * @param encodeSpecialChars if true high characters will be encode other 
     *                           wise not. 
     * @return the escaped string 
     */ 
    public static String htmlEncode(String s, boolean encodeSpecialChars) { 
        s = s == null ? EMPTY : s; 
 
        StringBuffer buffer = new StringBuffer(); 
 
        char[] chars = s.toCharArray(); 
        for (char c : chars) { 
            // encode standard ASCII characters into HTML entities where needed 
            if (c < '\200') { 
                switch (c) { 
                    case '"': 
                        buffer.append("&quot;"); 
                        break; 
                    case '&': 
                        buffer.append("&amp;"); 
                        break; 
                    case '<': 
                        buffer.append("&lt;"); 
                        break; 
                    case '>': 
                        buffer.append("&gt;"); 
                        break; 
                    default: 
                        buffer.append(c); 
                } 
            } 
            // encode 'ugly' characters (ie Word "curvy" quotes etc) 
            else if (encodeSpecialChars && (c < '\377')) { 
                String hexChars = "0123456789ABCDEF"; 
                int a = c % 16; 
                int b = (c - a) / 16; 
                String hex = "" + hexChars.charAt(b) + hexChars.charAt(a); 
                buffer.append("&#x").append(hex).append(";"); 
            } 
            // add other characters back in - to handle charactersets 
            // other than ascii 
            else { 
                buffer.append(c); 
            } 
        } 
 
        return buffer.toString(); 
    } 
 
    /** 
     * Join an Iteration of Strings together. <p/> 
     * <h5>Example</h5> 
     * <p/> 
     * <p/> 
     * <pre> 
     * // get Iterator of Strings (&quot;abc&quot;,&quot;def&quot;,&quot;123&quot;); 
     * Iterator i = getIterator(); 
     * out.print(TextUtils.join(&quot;, &quot;, i)); 
     * // prints: &quot;abc, def, 123&quot; 
     * </pre> 
     * 
     * @param glue   Token to place between Strings. 
     * @param pieces Iteration of Strings to join. 
     * @return String presentation of joined Strings. 
     */ 
    public static String join(String glue, Iterator<String> pieces) { 
        StringBuffer s = new StringBuffer(); 
 
        while (pieces.hasNext()) { 
            s.append(pieces.next()); 
 
            if (pieces.hasNext()) { 
                s.append(glue); 
            } 
        } 
 
        return s.toString(); 
    } 
 
    /** 
     * Join an array of Strings together. 
     * 
     * @param glue   Token to place between Strings. 
     * @param pieces Array of Strings to join. 
     * @return String presentation of joined Strings. 
     * @see #join(String,java.util.Iterator) 
     */ 
    public static String join(String glue, String[] pieces) { 
        return join(glue, Arrays.asList(pieces).iterator()); 
    } 
 
    /** 
     * 把Collection中的内容用glue链接起来 
     * 
     * @param glue   连接符. 
     * @param pieces 要链接的字符串集合. 
     * @return String 链接完的字符串. 
     * @see #join(String,java.util.Iterator) 
     */ 
    public static String join(String glue, Collection<String> pieces) { 
        return join(glue, pieces.iterator()); 
    } 
 
    /** 
     * 解析字符串中的UTF-8字符, \\uxxxx; 
     * 
     * @param src 源字符串 
     * @return char 
     */ 
    public static char decodeUTF8(String src) { 
        if (src == null) { 
            throw new IllegalArgumentException("Malformed \\uxxxx encoding."); 
        } 
 
        if (!(src.startsWith("\\u") && src.length() <= 6)) { 
            throw new IllegalArgumentException("Malformed \\uxxxx encoding."); 
        } 
 
        char[] sources = src.substring(2).toCharArray(); 
        char res = 0; 
        for (char nextChar : sources) { 
            int digit = HEX_DIGITS.indexOf(Character.toUpperCase(nextChar)); 
            res = (char) (res * 16 + digit); 
        } 
        return res; 
    } 
 
    public static String decodeUTF8String(String src) { 
        StringBuilder sb = new StringBuilder(); 
        char[] sources = src.toCharArray(); 
        for (int i = 0; i < sources.length; i++) { 
            if (sources[i] == '\\' && i < sources.length - 5 && sources[i + 1] == 'u') { 
                String utf8 = "" + sources[i++] + sources[i++] + sources[i++] + sources[i++] + sources[i++] + sources[i]; 
                sb.append(decodeUTF8(utf8)); 
                i = i + 5; 
            } else { 
                sb.append(sources[i]); 
            } 
        } 
        return sb.toString(); 
    } 
 
    /** 
     * 把十六进制的字符串转换为十进制字符串 
     * 
     * @param hex 六进制的字符串 
     * @return 十进制字符串 
     */ 
    public static String hexToDec(String hex) { 
        char[] sources = hex.toCharArray(); 
        int dec = 0; 
        for (int i = 0; i < sources.length; i++) { 
            int digit = HEX_DIGITS.indexOf(Character.toUpperCase(sources[i])); 
            dec += digit * Math.pow(16, (sources.length - (i + 1))); 
        } 
        return String.valueOf(dec); 
    } 
 
    public static boolean isLong(String text) { 
        try { 
            new Long(text); 
            return true; 
        } catch (NumberFormatException e) { 
            return false; 
        } 
    } 
 
    public static boolean isDouble(String text) { 
        try { 
            new Double(text); 
            return true; 
        } catch (NumberFormatException e) { 
            return false; 
        } 
    } 
 
    public static String insertAt(String str, String text, int start) { 
        if (str == null || str.length() < start) { 
            return str; 
        } else { 
            return str.substring(0, start) + text + (str.length() == start ? "" : str.substring(start)); 
        } 
    } 
 
    public static String replaceAt(String str, String text, int start, int end) { 
        if (str == null || str.length() < end) { 
            return str; 
        } else { 
            return str.substring(0, start) + text + (str.length() == end ? "" : str.substring(end)); 
        } 
    } 
 
    public static String randomString(int length) { 
        StringBuilder sb = new StringBuilder(); 
        Random rand = new Random(); 
        for (int i = 0; i < length; i++) { 
            sb.append((char) (48 + rand.nextInt(122 - 48))); 
        } 
        return sb.toString(); 
    } 
 
    public static String safe(String str) { 
        return safe(str, ""); 
    } 
 
    public static String safe(String str, String defaultValue) { 
        return str == null ? defaultValue : str; 
    } 
 
    /** 
     * 计算字符串中的某个字符的数量 
     * 
     * @param text 要计算的字符串 
     * @param c    字符 
     * @return 数量,如果text为null,返回0 
     */ 
    public static int count(String text, char c) { 
        if (text == null) { 
            return 0; 
        } 
        int result = 0; 
        for (char it : text.toCharArray()) { 
            if (it == c) { 
                ++result; 
            } 
        } 
        return result; 
    } 
 
    /** 
     * 计算在字符串text中的第n个字符c的位置 
     * 
     * @param text 要计算的字符串 
     * @param c    字符 
     * @param n    第几个 
     * @return 如果没有找到c,返回-1,如果c的数量不够n返回-1 
     */ 
    public static int charAt(String text, char c, int n) { 
        int pos = -1; 
        char[] charArray = text.toCharArray(); 
        int count = 0; 
        for (int i = 0; i < charArray.length; i++) { 
            if (charArray[i] == c) { 
                ++count; 
                if (count == n) { 
                    pos = i; 
                    break; 
                } 
            } 
        } 
 
        return pos; 
    } 
 
    /** 
     * 用字符c把字符串src分割成字符串数组,如果字符c前面有逃逸符esc,不进行分割 
     * 
     * @param src 字符串 
     * @param c   分隔符 
     * @param esc 逃逸符 
     * @return 分割后的字符串数组 
     */ 
    public static String[] splitWith(String src, char c, char esc) { 
        if (src == null || src.trim().equals(EMPTY)) { 
            return new String[0]; 
        } 
 
        List<String> result = new ArrayList<String>(); 
        StringBuilder sb = new StringBuilder(); 
        char[] chars = src.toCharArray(); 
        for (int i = 0; i < chars.length; i++) { 
            if (chars[i] == c) { 
                if (i > 0 && chars[i - 1] != esc) { 
                    result.add(sb.toString()); 
                    sb = new StringBuilder(); 
                } else { 
                    sb.deleteCharAt(sb.length() - 1); 
                    sb.append(chars[i]); 
                } 
            } else if (i != 0 || chars[i] != c) { 
                sb.append(chars[i]); 
            } 
        } 
        if (!sb.toString().equals("")) { 
            result.add(sb.toString()); 
        } 
 
        return result.toArray(new String[result.size()]); 
    } 
 
    /** 
     * 用字符c把字符串src分割成字符串数组,只分割第一个后面的不进行分割,如果字符c前面有逃逸符esc,不进行分割 
     * 
     * @param src 字符串 
     * @param c   分隔符 
     * @param esc 逃逸符 
     * @return 分割后的字符串数组 
     */ 
    public static String[] splitFirstWith(String src, char c, char esc) { 
        if (src == null || src.trim().equals("")) { 
            return new String[0]; 
        } 
 
        List<String> result = new ArrayList<String>(); 
        StringBuilder sb = new StringBuilder(); 
        char[] chars = src.toCharArray(); 
        boolean hit = false; 
        for (int i = 0; i < chars.length; i++) { 
            if (chars[i] == c && !hit) { 
                if (i > 0 && chars[i - 1] != esc) { 
                    result.add(sb.toString()); 
                    sb = new StringBuilder(); 
                    hit = true; 
                } else { 
                    sb.deleteCharAt(sb.length() - 1); 
                    sb.append(chars[i]); 
                } 
            } else if (i != 0 || chars[i] != c) { 
                sb.append(chars[i]); 
            } 
        } 
        if (!sb.toString().equals("")) { 
            result.add(sb.toString()); 
        } 
 
        return result.toArray(new String[result.size()]); 
    } 
 
    /** 
     * 根据空格来分割字符串 
     * 
     * @param src 要分割的字符串 
     * @return 分割结果 
     */ 
    public static List<String> splitBySpace(String src) { 
        src = src.replaceAll("\t", BLANK_SPACE); 
        return asList(src, BLANK_SPACE); 
    } 
 
    /** 
     * 根据separater把字符串分割成字符串数组 
     * 
     * @param src       要分割的字符串 
     * @param separater 分隔符 
     * @return 分割结果 
     */ 
    public static List<String> asList(String src, String separater) { 
        List<String> result = new ArrayList<String>(); 
        if (src == null || src.trim().equals(EMPTY)) { 
            return result; 
        } else { 
            String values[] = src.split(separater); 
            for (String value : values) { 
                if (!value.trim().equals(EMPTY)) { 
                    result.add(value.trim()); 
                } 
            } 
            return result; 
        } 
    } 
 
    public static String htmlSafe(String strText) { 
        // returns safe code for preloading in the RTE 
        String tmpString = strText; 
 
        tmpString = tmpString.replace("\n", EMPTY); 
 
        // convert all types of single quotes 
        tmpString = tmpString.replace((char) 145, (char) 39); 
        tmpString = tmpString.replace((char) 146, (char) 39); 
        tmpString = tmpString.replace("'", "&#39;"); 
 
        // convert all types of double quotes 
        tmpString = tmpString.replace((char) 147, (char) 34); 
        tmpString = tmpString.replace((char) 148, (char) 34); 
 
        // replace carriage returns & line feeds 
        tmpString = tmpString.replace((char) 10, (char) 32); 
        tmpString = tmpString.replace((char) 13, (char) 32); 
 
        return tmpString; 
 
    } 
 
    /** 
     * 把一个字符串按逗号分割, 并且去除两端空白 
     * 
     * @param str 字符串 
     * @return 分割后的列表 
     */ 
    public static List<String> splitToList(String str) { 
        StringTokenizer st = new StringTokenizer(str, COMMA); 
        List<String> result = new ArrayList<String>(); 
        while (st.hasMoreTokens()) { 
            String t = st.nextToken(); 
            if (!isEmpty(t)) { 
                result.add(t.trim()); 
            } 
        } 
        return result; 
    } 
 
    /** 
     * trim全角和半角空格 
     * 
     * @param str 要trim的字符串 
     * @return 两边去掉所有的全角和半角空格 
     */ 
    public static String fullSpaceTrim(String str) { 
        if (str == null) { 
            return null; 
        } 
        int length = str.length(); 
        int start = 0; 
        while ((start < length) && (str.charAt(start) == ' ' || str.charAt(start) == '\u3000')) { 
            ++start; 
        } 
        int end = length - 1; 
        while ((end >= 0) && (str.charAt(end) == ' ' || str.charAt(end) == '\u3000')) { 
            --end; 
        } 
        return start > end ? "" : str.substring(start, end + 1); 
    } 
 
    /** 
     * 密码加密,单向加密<br> 
     * 现在的加密算法支持SHA,MD5,PlainText, 
     * 
     * @param password  要加密的密码 
     * @param algorithm 加密算法名,可以为sha md5 plain,如果不是这些密码原样返回 
     * @return 加密后的字符串 
     */ 
    public static String encodePassword(String password, String algorithm) { 
        return encodePassword(password, algorithm, null); 
    } 
 
    /** 
     * 密码加密,单向加密<br> 
     * 现在的加密算法支持SHA,MD5,PlainText, 
     * 
     * @param password  要加密的密码 
     * @param algorithm 加密算法名,可以为sha md5 plain,如果不是这些密码原样返回 
     * @param salt      混淆 
     * @return 加密后的字符串 
     */ 
    public static String encodePassword(String password, String algorithm, Object salt) { 
        // null不进行加密 
        if (password == null) { 
            return null; 
        } 
 
        String saltedPwd; 
        if ((salt == null) || "".equals(salt)) { 
            saltedPwd = password; 
        } else { 
            saltedPwd = password + "{" + salt.toString() + "}"; 
        } 
 
 
        byte[] encoded; 
        if (algorithm.equalsIgnoreCase(SHA)) { 
            encoded = Base64.encodeBase64(DigestUtils.sha(saltedPwd)); 
        } else if (algorithm.equalsIgnoreCase(MD5)) { 
            encoded = Base64.encodeBase64(DigestUtils.md5(saltedPwd)); 
        } else if (algorithm.equalsIgnoreCase(PLAIN)) { 
            encoded = Base64.encodeBase64(saltedPwd.getBytes()); 
        } else { 
            return password; 
        } 
 
        return new String(encoded); 
    } 
 
    /** 
     * 这个字符串是否表示true: 
     * true, yes, on , 是, はい 
     * 
     * @param str 要验证的字符串 
     * @return 是否表示true 
     * @see StringUtils#TRUE_STRING 
     */ 
    public static boolean isTrue(String str) { 
        if (str == null) { 
            return false; 
        } else { 
            for (String tstr : TRUE_STRING) { 
                if (tstr.equalsIgnoreCase(str)) { 
                    return true; 
                } 
            } 
            return false; 
        } 
    } 
 
    /** 
     * @param str String 
     * @return Set 
     */ 
    public static Set<String> splitToSet(String str) { 
        return new HashSet<String>(splitToList(str)); 
    } 
    
    /**
	 * 生成时间戳/订单号
	 * @return
	 */
	public static String generateRandomTime(){
		return Common.getRandomNum(2)+DateUtil.dateToStr(new Date(), "yyyyMMddHHmmssSSS"); //启用防抵赖 
	}
} 
