package jp.learningpark.framework.gakkenID.utils;

import org.apache.commons.lang3.time.DateFormatUtils;

import java.lang.management.ManagementFactory;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 時間ツールクラス
 * 
 */
public class DateUtils extends org.apache.commons.lang3.time.DateUtils
{
    public static final String YYYY = "yyyy";

    public static final String YYYY_MM = "yyyy-MM";

    public static final String YYYY_MM_DD = "yyyy-MM-dd";

    public static final String YYYY_M_D = "yyyy/M/d";

    public static final String YYYY_M = "yyyy/M";

    public static final String YYYY_M_D_HORIZONTAL = "yyyy-M-d";

    public static final String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    
    public static final String YYYY_M_D_HH_MM = "yyyy/M/d HH:mm";

    /**
     * 日付フォマード・ISO_yyyy-MM-dd hh:mm
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMM_ISO = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日付フォマード・yyyyMMddhhmmss
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 日付フォマード・yyyyMMdd
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    /**
     * 日付フォマード・yyyy年MM月dd日
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy年MM月dd日";
    /**
     * 日付フォマード・yyyy年MM月dd日
     */
    public static final String DATE_FORMAT_YYYY_M_D = "yyyy年M月d日";
    /**
     * 日付フォマード・yyyy-MM-dd
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_BARS = "yyyy-MM-dd";
    /**
     * 日付フォマード・yyyy/MM/dd
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_SLASH = "yyyy/MM/dd";
    /**
     * 日付フォマード・yyyy年MM月
     */
    public static final String DATE_FORMAT_YYYY_MM = "yyyy年MM月";
    /**
     * 日付フォマード・yyyy/MM
     */
    public static final String DATE_FORMAT_YYYY_MM_SLASH = "yyyy/MM";
    /**
     * 日付フォマード・MM/dd
     */
    public static final String DATE_FORMAT_MM_DD_SLASH = "MM/dd";
    /**
     * 日付フォマード・DD日(E)
     */
    public static final String DATE_FORMAT_DD_E = "dd日(E)";
    /**
     * 日付フォマード・DD日(E)
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_E = "YYYY/MM/dd(E)";
    /**
     * 日付フォマード・DD日
     */
    public static final String DATE_FORMAT_DD = "dd日";
    /**
     * 日付フォマード・E
     */
    public static final String DATE_FORMAT_E = "E";
    /**
     * 日付フォマード・E曜
     */
    public static final String DATE_FORMAT_EE = "E曜";
    /**
     * 日付フォマード・MM月dd日(E)
     */
    public static final String DATE_FORMAT_MM_DD_E = "MM月dd日(E)";
    /**
     * 日付フォマード・MM月dd日
     */
    public static final String DATE_FORMAT_MM_DD = "MM月dd日";
    /**
     * 日付フォマード・HH:MM
     */
    public static final String DATE_FORMAT_HH_MM = "HH:mm";
    /**
     * 日付フォマード・H:MM
     */
    public static final String DATE_FORMAT_H_MM = "H:mm";
    /**
     * 日付フォマード・HH:MM:SS
     */
    public static final String DATE_FORMAT_HH_MM_SS = "HH:mm:ss";
    /**
     * 日付フォマード・M/D
     */
    public static final String DATE_FORMAT_M_D_SLASH = "M/d";
    /**
     * 日付フォマード・M/D(E)
     */
    public static final String DATE_FORMAT_M_D_E_SLASH = "M/d(E)";
    /**
     * 日付フォマード・yyyy年M月
     */
    public static final String DATE_FORMAT_YYYY_M = "yyyy年M月";
    /**
     * 日付フォマード・YYYY/MM/DD HH:mm
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
    /**
     * 日付フォマード・YYYY/MM/DD HH:mm:ss
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS = "yyyy/MM/dd HH:mm:ss";
    /**
     * 日付フォマード・YYYY/MM/DD HH:mm
     */
    public static final String DATE_FORMAT_YYYY = "yyyy";
    /**
     * 日付フォマード・YYYY/M/D
     */
    public static final String DATE_FORMAT_YYYYMD = "yyyy/M/d";

    /**
     * 日付フォマード・YYYYMMDDHHmmssSSS
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSSSSS = "yyyyMMddHHmmssSSS";

    /**
     * 日付フォマード・YYYY-MM-DD HH:mm:ss.SSS
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_SS_SSS = "yyyy-MM-dd HH:mm:ss.SSS";

    private static String[] parsePatterns = {
            "yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd HH:mm", "yyyy-MM", 
            "yyyy/MM/dd", "yyyy/MM/dd HH:mm:ss", "yyyy/MM/dd HH:mm", "yyyy/MM",
            "yyyy.MM.dd", "yyyy.MM.dd HH:mm:ss", "yyyy.MM.dd HH:mm", "yyyy.MM"};

    /**
     * システム日付を取得する
     * 
     * @return Date() システム日付
     */
    public static Date getNowDate()
    {
        return new Date();
    }

    /**
     * システム時間を取得する。
     *
     * @return システム時間
     */
    public static Timestamp getSysTimestamp() {
        return new Timestamp(Calendar.getInstance().getTimeInMillis());
    }

    /**
     * システム日付を取得する、フォーマットはyyyy-MM-dd
     * 
     * @return String
     */
    public static String getDate()
    {
        return dateTimeNow(YYYY_MM_DD);
    }

    public static final String getTime()
    {
        return dateTimeNow(YYYY_MM_DD_HH_MM_SS);
    }

    public static final String dateTimeNow()
    {
        return dateTimeNow(YYYYMMDDHHMMSS);
    }

    public static final String dateTimeNow(final String format)
    {
        return parseDateToStr(format, new Date());
    }

    public static final String dateTime(final Date date)
    {
        return parseDateToStr(YYYY_MM_DD, date);
    }

    public static final String parseDateToStr(final String format, final Date date)
    {
        return new SimpleDateFormat(format).format(date);
    }

    public static final Date dateTime(final String format, final String ts)
    {
        try
        {
            return new SimpleDateFormat(format).parse(ts);
        }
        catch (ParseException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * システム日付を取得する、フォーマットはyyyy/MM/dd
     */
    public static final String datePath()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyy/MM/dd");
    }

    /**
     * システム日付を取得する、フォーマットはyyyyMMdd
     */
    public static final String dateTime()
    {
        Date now = new Date();
        return DateFormatUtils.format(now, "yyyyMMdd");
    }

    /**
     * 日付型文字列は日付を変換する
     */
    public static Date parseDate(Object str)
    {
        if (str == null)
        {
            return null;
        }
        try
        {
            return parseDate(str.toString(), parsePatterns);
        }
        catch (ParseException e)
        {
            return null;
        }
    }

    /**
     * サーバ起動時間を取得する
     */
    public static Date getServerStartDate()
    {
        long time = ManagementFactory.getRuntimeMXBean().getStartTime();
        return new Date(time);
    }

    /**
     * 時間の差を計算する
     */
    public static String getDatePoor(Date endDate, Date nowDate)
    {
        long nd = 1000 * 24 * 60 * 60;
        long nh = 1000 * 60 * 60;
        long nm = 1000 * 60;

        long diff = endDate.getTime() - nowDate.getTime();

        long day = diff / nd;

        long hour = diff % nd / nh;

        long min = diff % nd % nh / nm;

        return day + "日" + hour + "時間" + min + "分";
    }
}
