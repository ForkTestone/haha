import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Hashtable;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * 功能：身份证的有效验证
 */
public static boolean JudgeID(String ID) throws ParseException {
        ID = ID.trim().toUpperCase();
        String[] ValCodeArr = { "1", "0", "X", "9", "8", "7", "6", "5", "4", "3", "2" };
        String[] LastNum = { "7", "9", "10", "5", "8", "4", "2", "1", "6", "3", "7", "9", "10", "5", "8", "4", "2" };
        String Num = "";
        // ================ 号码的长度 15位或18位 ================
        if (ID.length() != 15 && ID.length() != 18) {
        return false;
        }

//15位数身份证纯数字，18位身份证除最后一位都为数字
        if (ID.length() == 18) {
        Num = ID.substring(0, 17);
        } else if (ID.length() == 15) {
        Num = ID.substring(0, 6) + "19" + ID.substring(6, 15);
        }
        if (isNumeric(Num) == false) {
        //身份证15位号码都应为数字 ; 18位号码除最后一位外，都应为数字。
        return false;
        }


// 出生年月是否有效
        String strYear = Num.substring(6, 10);// 年份
        String strMonth = Num.substring(10, 12);// 月份
        String strDay = Num.substring(12, 14);// 月份
        if (isDataFormat(strYear + "-" + strMonth + "-" + strDay) == false) {
        //身份证生日无效。
        return false;
        }
        GregorianCalendar gc = new GregorianCalendar();
        SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd");
        if ((gc.get(Calendar.YEAR) - Integer.parseInt(strYear)) > 150
        || (gc.getTime().getTime() - s.parse(strYear + "-" + strMonth + "-" + strDay).getTime()) < 0) {
        return false;
        }
        if (Integer.parseInt(strMonth) > 12 || Integer.parseInt(strMonth) == 0) {
        return false;
        }
        if (Integer.parseInt(strDay) > 31 || Integer.parseInt(strDay) == 0) {
        return false;
        }

//判断最后一位的值
        int Last_Num = 0;
        for (int i = 0; i < 17; i++) {
        Last_Num = Last_Num + Integer.parseInt(String.valueOf(Num.charAt(i))) * Integer.parseInt(LastNum[i]);
        }
        int modValue = Last_Num % 11;
        String strVerifyCode = ValCodeArr[modValue];
        Num = Num + strVerifyCode;

        if (ID.length() == 18) {
        if (Num.equals(ID) == false) {
        //身份证无效，不是合法的身份证号码
        return false;
        }
        } else {
        return true;
        }

// 地区码是否有效
        Hashtable hash = GetAreaCode();
        if (hash.get(Num.substring(0, 2)) == null) {
        //身份证地区编码错误。
        return false;
        }
        return true;
        }

/**
 * 验证日期字符串是否是YYYY-MM-DD格式
 */
public static boolean isDataFormat(String str) {
        boolean flag = false;
        // String
        // regxStr="[1-9][0-9]{3}-[0-1][0-2]-((0[1-9])|([12][0-9])|(3[01]))";
        String regxStr = "^((\\d{2}(([02468][048])|([13579][26]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))(\\s(((0?[0-9])|([1-2][0-3]))\\:([0-5]?[0-9])((\\s)|(\\:([0-5]?[0-9])))))?$";
        Pattern pattern1 = Pattern.compile(regxStr);
        Matcher isNo = pattern1.matcher(str);
        if (isNo.matches()) {
        flag = true;
        }
        return flag;
        }

/**
 * 功能：判断字符串是否为数字
 */
private static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (isNum.matches()) {
        return true;
        } else {
        return false;
        }
        }

/**
 * 功能：设置地区编码
 */
private static Hashtable GetAreaCode() {
        Hashtable hashtable = new Hashtable();
        hashtable.put("11", "北京");
        hashtable.put("12", "天津");
        hashtable.put("13", "河北");
        hashtable.put("14", "山西");
        hashtable.put("15", "内蒙古");
        hashtable.put("21", "辽宁");
        hashtable.put("22", "吉林");
        hashtable.put("23", "黑龙江");
        hashtable.put("31", "上海");
        hashtable.put("32", "江苏");
        hashtable.put("33", "浙江");
        hashtable.put("34", "安徽");
        hashtable.put("35", "福建");
        hashtable.put("36", "江西");
        hashtable.put("37", "山东");
        hashtable.put("41", "河南");
        hashtable.put("42", "湖北");
        hashtable.put("43", "湖南");
        hashtable.put("44", "广东");
        hashtable.put("45", "广西");
        hashtable.put("46", "海南");
        hashtable.put("50", "重庆");
        hashtable.put("51", "四川");
        hashtable.put("52", "贵州");
        hashtable.put("53", "云南");
        hashtable.put("54", "西藏");
        hashtable.put("61", "陕西");
        hashtable.put("62", "甘肃");
        hashtable.put("63", "青海");
        hashtable.put("64", "宁夏");
        hashtable.put("65", "新疆");
        hashtable.put("71", "台湾");
        hashtable.put("81", "香港");
        hashtable.put("82", "澳门");
        hashtable.put("91", "国外");
        return hashtable;
        }

/**
 * 验证手机号
 */

public static boolean isPhone(String phone) {
        String regex = "^((13[0-9])|(14[5,7,9])|(15([0-3]|[5-9]))|(166)|(17[0,1,3,5,6,7,8])|(18[0-9])|(19[8|9]))\\d{8}$";
        if (phone.length() != 11) {
        return false;
        } else {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(phone);
        boolean isMatch = m.matches();
        return isMatch;
        }
        }
