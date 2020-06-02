package util;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Tools {
    public static String addZero(String str, int maxLen) {
        String strZero = "";
        int len = str.trim().length();
        for (int i = 1; i <= maxLen - len; i++) {
            strZero = strZero + "0";
        }
        String strTemp = strZero + str;
        return strTemp;
    }

    public static String addZero(int str, int maxLen) {
        return Tools.addZero(String.valueOf(str), maxLen);

    }

    public static String getExceptionStackTraceString(Exception e) {
        String errorInfo = "";
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        e.printStackTrace(pw);
        errorInfo = sw.toString();
        return errorInfo;
    }

    public static void writeException(Exception e) {
        e.printStackTrace();
        //Log loger = Log.getInstance();
        //String content = Tools.getExceptionStackTraceString(e);
        //	loger.sysException.info(content);

    }

    public static void writeLog(String content) {
        Log loger = Log.getInstance();

        //loger.sysException.info(content);

    }

    static Log logger = Log.getInstance();

    /**
     * Creates a new instance of Tools
     */
    public Tools() {
    }

    public static String StringArrayToString(String[] strs) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < strs.length; i++) {
            sb.append(strs[i]);
            if (i < strs.length - 1) {
                sb.append(",");
            }
        }
        return sb.toString();

    }


    public static void main(String[] args) {
        try {

            // Calendar cal = Calendar.getInstance();
            // cal.set(2007,1,1);
            // int maxDate = cal.getActualMaximum(Calendar.DATE);
            // System.out.println(maxDate);
            // for (int i=1;i<=12;i++)
            // {
            // System.out.print(i+"---");
            // Tools.getLastDay(2008,i);
            // }
            //
            System.out.println(Tools.addZero("1", 4));

        } catch (Exception e) {
            System.out.println(e);
        }

    }

    /**
     * 根据给定的年月，得到最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static int getLastDayInt(int year, int month) {
        // month=month-1;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.add(Calendar.MONTH, -1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        // System.out.println(maxDate);
        return maxDate;
    }

    /**
     * 根据给定的年月，得到最后一天
     *
     * @param year
     * @param month
     * @return
     */
    public static String getLastDay(int year, int month) {
        // month=month-1;
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, 1);
        cal.add(Calendar.MONTH, -1);
        int maxDate = cal.getActualMaximum(Calendar.DATE);
        // System.out.println(maxDate);
        return (String.valueOf(year) + "-" + String.valueOf(month) + "-" + String
                .valueOf(maxDate));
    }

    public static String getDay(String date) {
        String[] s = date.split("-");
        return s[2];
    }

    /*
     * 返回一个日期序列如2006-08-22 3返回的是 2006-08-20 2006-08-21 2006-08-22
     */
    public static List getMonthAllDay(int year, int month) {
        List list = new ArrayList();
        String tempdate = Tools.getLastDay(year, month);
        int lastDay = Integer.parseInt(getDay(tempdate));
        for (int k = 1; k <= lastDay; k++) {
            list.add(new Integer(k));

            // System.out.println(k);
        }
        return list;

    }

    public static String getLastDay() {
        Date date = new Date();
        GregorianCalendar gc = new GregorianCalendar();
        gc.add(GregorianCalendar.MONTH, 1);
        gc.add(GregorianCalendar.DATE, -date.getDate());
        // DateFormat df = DateFormat.getDateInstance(DateFormat.SHORT);
        DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
        Date dateTemp = gc.getTime();
        String s = df.format(dateTemp);
        return (s);
    }

    public static String getDefaultStartDateString() {
        String date = "";
        int[] d = Tools.getDefaultStartDate();
        date = d[0] + "-" + d[1] + "-" + d[2];
        return date;
    }

    public static String getDefaultEndDateString() {
        String date = "";
        int[] d = Tools.getDefaultEndDate();
        date = d[0] + "-" + d[1] + "-" + d[2];
        return date;
    }

    /**
     * 返回一个日期列表如2008-06-04 2008-06-05 2008-06-06
     *
     * @param startDateStr
     * @param days
     * @return
     * @throws ParseException
     */

    public static List getStartEnddate(String startDateStr, int days)
            throws ParseException {

        List list = new ArrayList();
        String seqDate = "";
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Date dd = format.parse(startDateStr);
        for (int day = 0; day <= days; day++) {

            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dd);
            calendar.add(Calendar.DAY_OF_MONTH, day);
            seqDate = format.format(calendar.getTime());
            list.add(seqDate);
            // System.out.println(seqDate);
        }
        return list;
    }

    /**
     * 得到验证码
     *
     * @param request
     */
    public static void getCheckCode(HttpServletRequest request) {
        HttpSession session = request.getSession();
        String sRand = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
        }
        session.setAttribute("rand", sRand);
    }

    /**
     * @return
     */
    public static String test() {
        return "test";
    }

    /**
     * 解码url中特殊字符
     *
     * @param strUrl
     * @return # 用来标志特定的文档位置 %23 % 对特殊字符进行编码 %25 & 分隔不同的变量值对 %26 + 在变量值中表示空格 %2B
     * \ 表示目录路径 %2F = 用来连接键和值 %3D ? 表示查询字符串的开始 %3F
     */
    public static String decodeUrl(String strUrl) {
        String strRt = "";
        if (strUrl == null || "".equals(strUrl))
            return "";
        strUrl = strUrl.trim();
        int nLen = strUrl.length();
        for (int i = 0; i < nLen; i++) {
            String strTmp = String.valueOf(strUrl.charAt(i));
            if (strTmp.equals("/"))
                strTmp = "%2F";
            strRt += strTmp;
        }
        return strRt;
    }

    /**
     * 解码url中特殊字符
     *
     * @param strUrl
     * @return # 用来标志特定的文档位置 %23 % 对特殊字符进行编码 %25 & 分隔不同的变量值对 %26 + 在变量值中表示空格 %2B
     * \ 表示目录路径 %2F = 用来连接键和值 %3D ? 表示查询字符串的开始 %3F
     */
    public static String decodeUrlPush(String strUrl) {
        String strRt = "";
        if (strUrl == null || "".equals(strUrl))
            return "";
        strUrl = strUrl.trim();
        int nLen = strUrl.length();
        for (int i = 0; i < nLen; i++) {
            String strTmp = String.valueOf(strUrl.charAt(i));
            if (strTmp.equals("/"))
                strTmp = "%2F";
            if (strTmp.equals("&"))
                strTmp = "%26";
            strRt += strTmp;
        }
        return strRt;
    }

    /**
     * 检查字符串是不是数字
     *
     * @param strValue
     * @return true 是数字 false 不是数字
     */
    public static boolean isNum(String strValue) {
        if (strValue == null || "".equals(strValue))
            return false;
        strValue = strValue.trim();
        int nLen = strValue.length();
        for (int i = 0; i < nLen; i++) {
            char cTmp = strValue.charAt(i);
            if (!Character.isDigit(cTmp))
                return false;
        }
        return true;
    }

    /**
     * 将文本中的<br/>
     * 转换成'\n' &nbsp;转换成' ' ,以便在多行文本框中显示
     *
     * @param s String
     * @return String
     */
    public static String changeTextToTextArea(String s) {
        if (Tools.isNull(s))
            return "";
        int i = 0;
        while (true) {
            i = s.indexOf("<br/>");
            if (i == -1) {
                break;
            } else {
                s = s.substring(0, i) + '\n' + s.substring(i + 5);
            }
        }
        i = 0;
        while (true) {
            i = s.indexOf("&nbsp;");
            if (i == -1) {
                break;
            } else {
                s = s.substring(0, i) + ' ' + s.substring(i + 6);
            }
        }
        return s;
    }

    /**
     * 将多行文本框中的空格，换行符转换成wml中的&nbsp,<br/>
     *
     * @param s
     * @return
     */
    public static String changeSpace(String s) {
        String r = "";
        if (!"".equals(s)) {
            for (int i = 0; i < s.length(); i++) {
                char line = '\n';
                char cc = s.charAt(i);
                if (cc == (line)) {
                    r = r + "<br/>";
                } else if (cc == ' ') {
                    r = r + "&nbsp;";
                } else {
                    r = r + cc;
                }
            }

        }
        return r;
    }

    /**
     * 返回一个子串
     *
     * @param s      原先的字符串
     * @param length 长度
     * @return
     */
    public static String subString(String s, int length) {
        String r = "";
        if (isNull(s)) {
            return r;
        }

        if (s.length() <= length) {
            r = s;
        } else {
            r = s.substring(0, length);
        }
        return r;

    }

    /*
     * 处理sql字符串将'替换成''
     */
    public static String processSqlString(String sql) {
        String r = "";
        if (Tools.isNull(sql)) {
            return null;
        }

        // sql中'已被替换成'',不做处理
        if (sql.indexOf("''") >= 1) {
            r = sql;
        } else {
            // 将'替换成''
            r = Tools.replaceString(sql, "'", "''");
        }
        return r;

    }

    /**
     * 替换字符串
     *
     * @param originalString 整个字符串
     * @param srcString      要被替换掉的字符串
     * @param destString     新字符串
     * @return
     */
    public static String replaceString(String originalString, String srcString,
                                       String destString) {
        int startIndex = originalString.indexOf(srcString);
        if (startIndex == -1) {
            return originalString;
        } else {
            String newString = originalString;
            String firstPart = newString.substring(0, startIndex);
            String lastPart = newString.substring(startIndex
                    + srcString.length());
            lastPart = replaceString(lastPart, srcString, destString);
            newString = String.valueOf(String.valueOf((new StringBuffer(String
                    .valueOf(String.valueOf(firstPart)))).append(destString)
                    .append(lastPart)));
            return newString;
        }
    }

    /**
     * 返回一个数组存放查询页面中日期的起始值
     */
    public static int[] getDefaultStartDate() {
        Calendar cal = Calendar.getInstance();
        Date d = new Date();
        int TodayYear = d.getYear() + 1900;
        int TodayMonth = d.getMonth();
        int TodayDay = d.getDate();
        cal.set(TodayYear, TodayMonth, TodayDay);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        cal.roll(Calendar.DAY_OF_YEAR, false);
        cal.roll(Calendar.DAY_OF_YEAR, false);
        String d2 = formatter.format(cal.getTime()).toString();
        String[] s = d2.split("-");
        int[] result = new int[3];
        result[0] = Integer.parseInt(s[0]);
        result[1] = Integer.parseInt(s[1]);
        result[2] = Integer.parseInt(s[2]);
        return result;
    }

    /**
     * 返回年月日yyyyMMdd
     *
     * @return
     */

    public static String getDateExport() {

        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyyMMdd");
        Date d = new Date();
        String strNow = String.valueOf(bartDateFormat.format(d));
        return strNow;

    }

    /**
     * 返回当前日期如yyyy-MM-dd
     *
     * @return
     */
    public static String getNowDate() {

        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String strNow = String.valueOf(bartDateFormat.format(d));
        return strNow;

    }

    /**
     * 返回当前日期用数组存放
     *
     * @return
     */
    public static int[] getNowDateArr() {

        SimpleDateFormat bartDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date d = new Date();
        String strNow = String.valueOf(bartDateFormat.format(d));
        String[] s = strNow.split("-");
        int[] result = new int[3];
        result[0] = Integer.parseInt(s[0]);
        result[1] = Integer.parseInt(s[1]);
        result[2] = Integer.parseInt(s[2]);
        return result;

    }

    /**
     * 返回昨天日期如yyyy-MM-dd
     *
     * @return
     */
    public static String getYesterDayDate() {
        String d2 = "";
        try {
            Calendar cal = Calendar.getInstance();
            Date d = new Date();
            int TodayYear = d.getYear() + 1900;
            int TodayMonth = d.getMonth();
            int TodayDay = d.getDate();
            cal.set(TodayYear, TodayMonth, TodayDay);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            cal.roll(Calendar.DAY_OF_YEAR, false);
            // cal.roll(Calendar.DAY_OF_YEAR, false);
            d2 = formatter.format(cal.getTime()).toString();
        } catch (Exception e) {
            //logger.sysException.info("", e);
        }

        return d2;

    }

    /*
     * 返回一个日期序列如2006-08-22 3返回的是 2006-08-20 2006-08-21 2006-08-22
     */
    public static List getSeqDay(String date, int counter) {
        List list = new ArrayList();
        String tempdate = date;
        list.add(date);
        for (int i = 0; i < counter - 1; i++) {
            tempdate = getbeforeDate(tempdate);
            list.add(tempdate);
        }

        List r = new ArrayList();
        int size = list.size() - 1;
        for (int k = 0; k < list.size(); k++) {
            r.add(list.get(size - k));
        }
        return r;

    }

    public static String getbeforeDate(String date) {
        String d2 = "";
        try {
            Calendar cal = Calendar.getInstance();
            String[] s = date.split("-");
            int[] result = new int[3];
            result[0] = Integer.parseInt(s[0]);
            result[1] = Integer.parseInt(s[1]) - 1;
            result[2] = Integer.parseInt(s[2]);

            cal.set(result[0], result[1], result[2]);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            cal.roll(Calendar.DAY_OF_YEAR, false);
            d2 = formatter.format(cal.getTime()).toString();
        } catch (Exception e) {
            //logger.sysException.info("Tools.getbeforeDate", e);
        }

        return d2;

    }

    /**
     * 返回昨天日期用数组存放
     *
     * @return
     */
    public static int[] getYesterDayDateArr() {
        String d2 = "";

        Calendar cal = Calendar.getInstance();
        Date d = new Date();
        int TodayYear = d.getYear() + 1900;
        int TodayMonth = d.getMonth();
        int TodayDay = d.getDate();
        cal.set(TodayYear, TodayMonth, TodayDay);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        cal.roll(Calendar.DAY_OF_YEAR, false);
        // cal.roll(Calendar.DAY_OF_YEAR, false);
        d2 = formatter.format(cal.getTime()).toString();
        String[] s = d2.split("-");
        int[] result = new int[3];
        result[0] = Integer.parseInt(s[0]);
        result[1] = Integer.parseInt(s[1]);
        result[2] = Integer.parseInt(s[2]);
        return result;
    }

    /**
     * 返回一个数组存放查询页面中日期的结束
     */
    public static int[] getDefaultEndDate() {
        Date d = new Date();
        int TodayYear = d.getYear() + 1900;
        int TodayMonth = d.getMonth() + 1;
        int TodayDay = d.getDate();
        int[] s = new int[3];
        s[0] = TodayYear;
        s[1] = TodayMonth;
        s[2] = TodayDay;
        return s;
    }

    // 用于在wap页面中输出汉字
    public static String viewToUTF8(String s) {
        String s1 = s;
        try {
            StringBuffer stringbuffer = new StringBuffer();
            for (int i = 0; i < s1.length(); i++)
                if (s1.charAt(i) > '\177') {
                    stringbuffer.append("&#x");
                    String s2 = Integer.toHexString(s1.charAt(i));
                    StringBuffer stringbuffer1 = new StringBuffer(s2);
                    stringbuffer1.reverse();
                    int l = 4 - stringbuffer1.length();
                    for (int j = 0; j < l; j++)
                        stringbuffer1.append('0');

                    for (int k = 0; k < 4; k++)
                        stringbuffer.append(stringbuffer1.charAt(3 - k));

                    stringbuffer.append(';');
                } else {
                    stringbuffer.append(s1.charAt(i));
                }

            return stringbuffer.toString();
        } catch (Exception exception) {
            System.out.println(exception);
        }
        return s1;
    }

    public static ArrayList TheContentList(String Content) {
        ArrayList list1 = new ArrayList();
        String text1 = "";
        while (Content.length() > 280) {
            text1 = Content.substring(0, 280);
            Content = Content.substring(280);
            list1.add(text1);
        }
        if (Content.length() > 0) {
            list1.add(Content);
        }
        return list1;
    }

    /**
     * To chinese encode
     */
    public static String ToChinese(String strValue) {
        if (strValue == null) {
            strValue = "";
        } else {
            try {
                strValue = new String(strValue.getBytes("iso-8859-1"), "gb2312");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strValue;
    }

    /**
     * To UTF-8 encode
     */
    public static String FileToUtf8(String strValue) {
        if (strValue == null) {
            strValue = "";
        } else {
            try {
                strValue = new String(strValue.getBytes(), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strValue;
    }

    /**
     * To UTF-8 encode
     */
    public static String ToUtf8(String strValue) {
        if (strValue == null) {
            strValue = "";
        } else {
            try {
                strValue = new String(strValue.getBytes("iso-8859-1"), "UTF-8");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strValue;
    }

    /**
     * gb2312 to UTF8
     */
    public static String gbk2utf8(String a) {
        if (isNull(a))
            return null;
        byte[] b = null;
        try {
            b = a.getBytes("UTF-16");
        } catch (Exception ex) {
        }
        int size = b.length;
        StringBuffer h = new StringBuffer(size);
        for (int i = 0; i < size; i++) {
            int u = b[i] & 255; // unsigned conversion
            if (u < 16) {
                h.append("0" + Integer.toHexString(u));
            } else {
                h.append(Integer.toHexString(u));
            }
        }
        String str = h.toString();
        str = str.substring(4);
        byte[] bb = str.getBytes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bb.length; i++) {
            if (i % 4 == 0) {
                if (i != 0)
                    sb.append(";");
                sb.append("&#x");
            }
            sb.append((char) bb[i]);
        }
        sb.append(";");
        return sb.toString();
    }

    /**
     * To Unicode encode
     */
    public static String ToUnicode(String strValue) {
        if (strValue == null) {
            strValue = "";
        } else {
            try {
                strValue = new String(strValue.getBytes("gb2312"), "iso-8859-1");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return strValue;
    }

    /**
     * Check paramter whther is null or "" or "null"
     */
    public static boolean isNull(String strVar) {
        if (null == strVar || "".equals(strVar) || "null".equals(strVar)) {
            return true;
        }
        strVar = strVar.trim();
        if ("".equals(strVar))
            return true;
        return false;
    }

    /**
     * 将url中的&替换成&amp;
     *
     * @param strUrl
     * @return
     */
    public static String encodeURL(String strUrl) {
        if (isNull(strUrl))
            return null;
        byte[] bb = strUrl.getBytes();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < bb.length; i++) {
            if (bb[i] == '&') {
                sb.append("&amp;");
            } else {
                sb.append((char) bb[i]);
            }
        }
        return sb.toString();
    }

    /**
     * process the replace string "_" -> "\_","%" -> "\%" "*" -> "%", "?" -> "_"
     */
    public static String makeWildChar(String strValue) {
        if (Tools.isNull(strValue))
            return "";
        String strResult = "";
        int nLen = strValue.length();
        for (int i = 0; i < nLen; i++) {
            String strTmp = String.valueOf(strValue.charAt(i));
            if ("*".equals(strTmp)) {
                strTmp = "%";
            } else if ("?".equals(strTmp)) {
                strTmp = "_";
            } else if ("_".equals(strTmp) || "%".equals(strTmp)) {
                strResult += "\\";
            }
            strResult += strTmp;
        }
        return strResult;
    }
}
