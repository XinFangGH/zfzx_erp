 /**
  *检查电话号码格式
  *如果格式不正确     返回false  正确   返回true
  */

 function checkPhoneFormat(object) {
     var pattern = /^[0-9-]{6,40}$/;
     flag = pattern.test(object.value);
     if (object.value == "") return true;
     if (!flag) {
        // alert('联系电话格式不正确!');
         object.focus();
         return false;
     } else {
         return true; 
     }
 }
 /**
  *检查电话号码格式
  *如果格式不正确     返回false  正确   返回true
  */

 /*(1)电话号码由数字、"("、")"和"-"构成
　　(2)电话号码为3到8位
　　(3)如果电话号码中包含有区号，那么区号为三位或四位
　　(4)区号用"("、")"或"-"和其他部分隔开
　　(5)移动电话号码为11或12位，如果为12位,那么第一位为0
　　(6)11位移动电话号码的第一位和第二位为"13"
　　(7)12位移动电话号码的第二位和第三位为"13"
     */
 function PhoneCheck(object) {
     var str = object.value;
     var reg = /(^[0-9]{3,4}\-[0-9]{3,8}$)|(^[0-9]{3,8}$)|(^\([0-9]{3,4}\)[0-9]{3,8}$)|(^0{0,1}13[0-9]{9}$)/;
     flag = reg.test(object.value);
     if (object.value == "") return true;
     if (!flag) {
        // alert('电话格式不正确!');
         object.focus();
         return false;
     } else {
         return true;
     }
 }
 //检查是否数字

 function checkNUM(NUM) {
     var i, j, strTemp;
     strTemp = "0123456789";
     if (NUM.length == 0) return 0
     for (i = 0; i < NUM.length; i++) {
         j = strTemp.indexOf(NUM.charAt(i));
         if (j == -1) {
             return 0;
         }
     }
     return 1;
 }

 //检查是否年（YYYY）

 function chkYear(datestr) {
     var lthdatestr
     if (datestr != "") lthdatestr = datestr.length;
     else return 1;

     if (lthdatestr == 0) return 1;
     if (lthdatestr != 4) return 0;

     if (checkNUM(datestr) == 0) return 0;

     return 1;
 }

 //检查是否月份（YYYYMM）

 function chkMonth(datestr) {
     var lthdatestr
     if (datestr != "") lthdatestr = datestr.length;
     else return 1;

     if (lthdatestr == 0) return 1;
     if (lthdatestr != 6) return 0;

     if (checkNUM(datestr) == 0) return 0;

     tmp = new String(datestr);

     var month = tmp.substr(4, 2);
     if (!((1 <= month) && (12 >= month))) {
         return 0;
     }
     return 1;
 }


 //检查是否日期（YYYYMMDD）

 function chkdate(datestr) {
     var lthdatestr
     if (datestr != "") lthdatestr = datestr.length;
     else return 1;

     if (lthdatestr == 0) return 1;
     if (lthdatestr != 8) return 0;

     if (checkNUM(datestr) == 0) return 0;

     tmp = new String(datestr);

     var year = tmp.substr(0, 4);
     var month = tmp.substr(4, 2);
     var day = tmp.substr(6, 2);

     if (!((1 <= month) && (12 >= month) && (31 >= day) && (1 <= day))) {
         return 0;
     }
     if (!((year % 4) == 0) && (month == 2) && (day == 29)) {
         return 0;
     }
     if ((month <= 7) && ((month % 2) == 0) && (day >= 31)) {
         return 0;

     }
     if ((month >= 8) && ((month % 2) == 1) && (day >= 31)) {
         return 0;
     }
     if ((month == 2) && (day == 30)) {
         return 0;
     }

     return 1;
 }


 //----去空格----

 function LTrim(str) {
     var i = 0;
     while (str.charAt(i) == ' ') i++;
     return str.substring(i);
 }

 function RTrim(str) {
     var i = 0;
     var len = str.length;
     while (str.charAt(len - i - 1) == ' ') i++;
     return str.substring(0, len - i);
 }

 function ATrim(str) {
     return RTrim(LTrim(str));
 }

 //----判断数据项非空----

 function isEmpty(str) {
     if (str != null) {
         var tmp = ATrim(str)
         if (tmp.length == 0) {
             return true;
         }
     }
     return false;
 }

 //----判断数据项输入长度是否不够----

 function isLenErr(str, len) {
     var tmp = ATrim(str)
     if (tmp.length == len) {
         return false;
     }
     return true;
 }

 //----判断数据项输入长度是否超长----

 function isLenOver(str, len) {
     var tmp = ATrim(str)
     if (tmp.length <= len) {
         return false;
     }
     return true;
 }

 //----检查费率是否错误----

 function isRateErr(str) {
     var tmp = ATrim(str)
     var bOneDot = false;
     for (var i = tmp.length - 1; i >= 0; i--) {
         var oneChar = tmp.charAt(i)
         if (oneChar == "." && (!bOneDot)) {
             if ((tmp.length - 1 - i) > 4) return true;

             bOneDot = true;
             continue;
         }
         if (oneChar < "0" || oneChar > "9") {
             return true;
         }
     }
     if (tmp > 1) {
         return true;
     }
     return false;
 }

 //----检查金额是否错误----

 function isAmountErr(str) {
     var tmp = ATrim(str)
     var bOneDot = false;
     for (var i = tmp.length - 1; i >= 0; i--) {
         var oneChar = tmp.charAt(i)
         if (oneChar == "." && (!bOneDot)) {
             if ((tmp.length - 1 - i) > 2) {
                 return true;
             }

             bOneDot = true;
             continue;
         }
         if (oneChar < "0" || oneChar > "9") {
             return true;
         }
     }
     return false;
 }
 
 //----检查金额是否合法----
 // 文本id
 function chkMoney(idval) {
 	var object=$("#"+idval);
     var reg = /(?!^0\d*$)^\d{1,16}(\.\d{1,2})?$/;
     flag = reg.test(object.val());
     if (object.val() == ""){
     	$("#money_span").html("金额不能为空。");
     return false;
     } 
     if (!flag) {
        $("#money_span").html("金额必须大于0且为整数或小数，小数点后不超过2位。");
         object.focus();
         return false;
     } else {
     	 $("#money_span").html("");
         return true;
     }
 }

 //----判断是否闰年----

 function isRyear(inputInt) {
     if (inputInt % 100 == 0 && inputInt % 400 == 0 || inputInt % 100 != 0 && inputInt % 4 == 0) {
         return true
     } else {
         return false
     }
 }

 //----判断日期是否合法----

 function isDate(str) {
     var year = parseFloat(str.substring(0, 4))
     var month = parseFloat(str.substring(4, 6))
     var day = parseFloat(str.substring(6, 8))
     if (month < 1 || month > 12 || day < 1 || day > 31 || year < 1000 || year > 2050) {
         return false
     } else if ((month == 4 || month == 6 || month == 9 || month == 11) && (day > 30)) {
         return false
     } else if (isRyear(year) && month == 2 && day > 29 || !isRyear(year) && month == 2 && day > 28) {
         return false
     } else {
         return true
     }
 }

 //----判断输入项类型是否合法日期----

 function isCorrectDate(str) {
     if (str.length != 8) {
         return false
     }
     for (var i = 0; i < 8; i++) {
         var oneChar = str.charAt(i)
         if (oneChar < "0" || oneChar > "9") {
             return false
         }
     }
     if (!isDate(str)) {
         return false
     } else {
         return true
     }
 }
  function isChina(s) //判断字符是否是中文字符 
{ 
var patrn= /[\u4E00-\u9FA5]|[\uFE30-\uFFA0]/gi; 
if (!patrn.exec(s)) 
{ 
return false; 
}else{ 
return true; 
} 
} 
 //--------------检查是否合法手机号码------------------

 function isMobile(mobile) {
 	

     if (mobile==null||mobile.length != 11) {
         return 0;
     }
     if (checkNUM(mobile) == 0) {
         return 0;
     }
     var s = mobile.substring(0, 2);
     if (s != "13" && s != "14" && s != "15" && s != "18") {
         return 0;
     }
     return 1;

 }

 //----判断输入项是否由数字或字母或数字和字母组成----

 function isNumOrLetter(str) {
     var regu = "^[0-9a-zA-Z]+$";

     var re = new RegExp(regu);

     if (re.test(str)) {

         return true;

     } else {

         return false;

     }
 }


 //正整数和最多包含两位小数点的正数，不包含0

 function isAmount(amount) {
     if (amount == '0') {
         return 1;
     }
     var regu = /(^([1-9]\d*|[0])\.\d{1,2}$|^[1-9]\d*$)/;
     var re = new RegExp(regu);
     return re.test(amount);
 }

 //true  : 非法返回true
 //false : 正常返回false
 //--------------检查邮箱号码是否合法------------------

 function isEmail(email) {
     var emailReg2 = /^.+\@(\[?)[a-zA-Z0-9\-\.]+\.([a-zA-Z]{2,6}|[0-9]{1,3})(\]?)$/;
     if (emailReg2.test(email)) {
         return false
     } else {
         return true;
     }

 }

 /**计算字符串的长度，汉字算3个长度
  @param str : 需要检查长度的字符 
  @param trim : 是否去除空格后检查，true：去除；false：不去除； 
  @return 字符串的长度 
  */
 function chkLength(str, trim) {
     if (str.length <= 0) {
         return 0;
     }
     if ('string' !== typeof str) {
         return false;
     }
     if (trim == undefined || trim == null) {
         trim = 0;
     }
     if (trim != 0) {
         return parseInt(str.replace(/\s/g, '').replace(/[^\x00-\xff]/g, "***").length);
     }
     return parseInt(str.replace(/[^\x00-\xff]/g, "***").length);
 }

 /**
  * 校验身份证号码格式
  * @param num 身份证号码
  * @returns
  */

 function isIdCardNo(num) {
     return /^(\d{18,18}|\d{15,15}|\d{17,17}(x|X))$/.test(num);
 }

 /**
  * 效验是否为中文
  * @param str
  */

 function isChinese(str) {
     return /^[\u4E00-\u9FA5]+$/.test(str);
 }
 
 /**
  * 效验组织机构代码格式
  * @param str
  */

 function isorganizecode(organizecode) {
	 var organizecodeReg = /^[A-Za-z0-9]{8}-[A-Za-z0-9]{1}/;
     if (organizecodeReg.test(organizecode)) {
         return false
     } else {
         return true;
     }
 }