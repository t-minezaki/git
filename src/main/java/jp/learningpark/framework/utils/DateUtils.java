/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import java.sql.Timestamp;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

/**
 * <p>日付ユーティリティクラス</p>.
 *
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2018/09/28  GAOLI: 新規
 * @version 1.0
 */
public class DateUtils {

    /**
     * 時間計算で端数が発生した場合に切り捨てるモード。
     */
    public static final int ROUND_DOWN = 1;

    /**
     * 時間計算で端数が発生した場合に切り上げるモード。
     */
    public static final int ROUND_UP = 2;

    /**
     * 時間計算で端数が発生した場合にもっとも近い時間に丸めるモード。
     * <p>
     * 両隣りの時間が等距離の場合は切り上げる。
     * </p>
     */
    public static final int ROUND_HALF_UP = 3;

    /**
     * 時間計算で端数が発生した場合にもっとも近い時間に丸めるモード。
     * <p>
     * 両隣りの時間が等距離の場合は切り下げる。
     * </p>
     */
    public static final int ROUND_HALF_DOWN = 4;

    /**
     * 時間計算で端数が発生した場合にもっとも近い時間に丸めるモード。
     * <p>
     * 両隣りの時間が等距離の場合は偶数側に丸める。
     * </p>
     */
    public static final int ROUND_HALF_EVEN = 5;

    /**
     * DateUtils を構築する。
     */
    private DateUtils() {
    }

    /**
     * 指定された形式の日付時刻文字列を日付型に変換する。
     *
     * @param sText   日付時刻文字列
     * @param sFormat 日付時刻文字列の形式
     * @return 変換後の日付
     */
    public static Date parse(String sText, String sFormat) {
        if (StringUtils.isEmpty(sText) || StringUtils.isEmpty(sFormat)) {
            return null;
        }

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat(sFormat);
            dateFormat.setLenient(false);
            ParsePosition pos = new ParsePosition(0);

            Date date = dateFormat.parse(sText, pos);

            return date;
        } catch (Exception e) {

        }
        return null;
    }

    /**
     * 指定された形式の配列から日付時刻文字列を日付型に変換する。
     * <p>
     * 指定された形式の配列から日付時刻文字列に一致したフォーマットで変換し日付型を返却する。
     * </p>
     *
     * @param sText    日付時刻文字列
     * @param sFormats 日付時刻文字列の形式の配列
     * @return 変換後の日付
     */
    public static Date parse(String sText, String[] sFormats) {
        if (StringUtils.isEmpty(sText) || sFormats == null) {
            throw null;
        }
        for (String format : sFormats) {
            return parse(sText, format);
        }
        return null;
    }

    /**
     * yyyyMMdd形式の日付文字列を日付型に変換する。
     *
     * @param sText yyyyMMdd形式の日付文字列
     * @return 変換後の日付
     */
    public static Date parseYMD(String sText) {
        return parse(sText, "yyyyMMdd");
    }

    /**
     * 日付を指定された形式の日付時刻文字列に変換する。
     *
     * @param date    日付
     * @param sFormat 日付時刻文字列の形式
     * @param locale  ロカール
     * @return 変換後の日付時刻文字列
     */
    public static String format(Date date, String sFormat, Locale locale) {
        if (date == null || StringUtils.isEmpty(sFormat)) {
            throw new IllegalArgumentException();
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat(sFormat, locale);
        return dateFormat.format(date);
    }

    /**
     * 日付を指定された形式の日付時刻文字列に変換する。
     *
     * @param date    日付
     * @param sFormat 日付時刻文字列の形式
     * @return 変換後の日付時刻文字列
     */
    public static String format(Date date, String sFormat) {
        return format(date, sFormat, Locale.JAPAN);
    }

    /**
     * 日付文字列を指定された日付書式に変換し返却する。
     * <p>
     * 入力値を入力書式で日付変換を行い、出力書式に変換し返却する。
     * </p>
     *
     * @param sText     変換対象の文字列
     * @param inFormat  入力値の書式
     * @param outFormat 出力する書式
     * @return 書式変換後の文字列
     */
    public static String format(String sText, String inFormat, String outFormat) {
        String outDate = "";
        try {
            Date date = parse(sText, inFormat);
            outDate = format(date, outFormat);
        } catch (Exception e) {
            outDate = sText;
        }
        return outDate;

    }

    /**
     * 日付文字列を指定された日付書式に変換し返却する。
     * <p>
     * 入力値の書式は複数指定可能、入力値に一致する書式で日付変換を行い、出力書式に変換し返却する。
     * </p>
     *
     * @param sText      変換対象の文字列
     * @param inFormat   入力値の書式
     * @param sOutFormat 出力する書式
     * @return 書式変換後の文字列
     */
    public static String format(String sText, String[] inFormat, String sOutFormat) {

        Date date = parse(sText, inFormat);
        return format(date, sOutFormat);
    }

    /**
     * 日付文字列を指定された日付書式に変換し返却する。
     * <p>
     * 入力された日付文字列の書式を出力書式から推測して日付変換を行う。<br>
     * 入力値の入力書式は、以下の書式に限定する。
     * </p>
     *
     * <pre>
     * ・年月日時分秒
     * yyyyMMddHHmmss
     * yyyy/MM/dd|HH:mm:ss
     * yyyy/MM/ddHH:mm:ss
     * yyyy年MM月dd日 HH時mm分ss秒
     * yyyy年MM月dd日HH時mm分ss秒
     *
     * ・年月日時分
     * yyyyMMddHHmm
     * yyyy/MM/dd|HH:mm
     * yyyy/MM/ddHH:mm
     * yyyy年MM月dd日 HH時mm分
     * yyyy年MM月dd日HH時mm分
     *
     * ・年月日時
     * yyyyMMddHH
     * yyyy/MM/dd|HH
     * yyyy/MM/ddHH
     * yyyy年MM月dd日 HH時
     * yyyy年MM月dd日HH時
     *
     * ・年月日
     * yyyyMMdd
     * yyyy/MM/dd
     * yyyy年MM月dd日
     *
     * ・年月
     * yyyyMM
     * yyyy/MM
     * yyyy年MM月
     *
     * ・年
     * yyyy
     * yyyy年
     *
     * ・月日
     * MM/dd
     * MM月dd日
     * MMdd
     *
     * ・月
     * MM
     * MM月
     *
     * ・日
     * dd
     * dd日
     *
     * ・時分秒
     * HHmmss
     * HH:mm:ss
     * HH時mm分ss秒
     *
     * ・時分
     * HHmm
     * HH:mm
     * HH時mm分
     *
     * ・時
     * HH
     * HH時
     *
     * ・分秒
     * mmss
     * mm:ss
     * mm分ss秒
     *
     * ・分
     * mm
     * mm分
     *
     * ・秒
     * ss
     * ss秒
     * </pre>
     *
     * @param sText      変換対象の文字列
     * @param sOutFormat 出力する書式
     * @return 書式変換後の文字列
     */
    public static String format(String sText, String sOutFormat) {

        List<String> patterns = new LinkedList<String>();

        boolean isexists = false;

        // 長いフォーマットのパターンから設定していく
        // 年月日時分秒
        patterns.add("yyyyMMddHHmmss");
        patterns.add("yyyy/MM/dd|HH:mm:ss");
        patterns.add("yyyy/MM/dd HH:mm:ss");
        patterns.add("yyyy年MM月dd日 HH時mm分ss秒");
        patterns.add("yyyy年MM月dd日HH時mm分ss秒");
        isexists = patterns.contains(sOutFormat);

        // 年月日時分
        if (!isexists) {
            patterns.add("yyyyMMddHHmm");
            patterns.add("yyyy/MM/dd|HH:mm");
            patterns.add("yyyy/MM/dd HH:mm");
            patterns.add("yyyy年MM月dd日 HH時mm分");
            patterns.add("yyyy年MM月dd日HH時mm分");
            isexists = patterns.contains(sOutFormat);
        }

        // 年月日時
        if (!isexists) {
            patterns.add("yyyyMMddHH");
            patterns.add("yyyy/MM/dd|HH");
            patterns.add("yyyy/MM/dd HH");
            patterns.add("yyyy年MM月dd日 HH時");
            patterns.add("yyyy年MM月dd日HH時");
            isexists = patterns.contains(sOutFormat);
        }

        if (!isexists) {
            if (sOutFormat.startsWith("y")) {
                // 年月日
                patterns.add("yyyyMMdd");
                patterns.add("yyyy/MM/dd");
                patterns.add("yyyy年MM月dd日");
                isexists = patterns.contains(sOutFormat);

                // 年月
                if (!isexists) {
                    patterns.add("yyyyMM");
                    patterns.add("yyyy/MM");
                    patterns.add("yyyy年MM月");
                    isexists = patterns.contains(sOutFormat);
                }

                // 年
                if (!isexists) {
                    patterns.add("yyyy");
                    patterns.add("yyyy年");
                    isexists = patterns.contains(sOutFormat);
                }
            }
        }

        if (!isexists) {
            if (sOutFormat.startsWith("M")) {
                // 月日
                patterns.add("MM/dd");
                patterns.add("MM月dd日");
                patterns.add("MMdd");
                isexists = patterns.contains(sOutFormat);

                // 月
                if (!isexists) {
                    patterns.add("MM");
                    patterns.add("MM月");
                    isexists = patterns.contains(sOutFormat);
                }
            }
        }

        // 日
        if (!isexists) {
            if (sOutFormat.startsWith("d")) {
                patterns.add("dd");
                patterns.add("dd日");
                isexists = patterns.contains(sOutFormat);
            }
        }

        if (!isexists) {
            if (sOutFormat.startsWith("H")) {
                // 時分秒
                patterns.add("HHmmss");
                patterns.add("HH:mm:ss");
                patterns.add("HH時mm分ss秒");
                isexists = patterns.contains(sOutFormat);

                // 時分
                if (!isexists) {
                    patterns.add("HHmm");
                    patterns.add("HH:mm");
                    patterns.add("HH時mm分");
                    isexists = patterns.contains(sOutFormat);
                }

                // 時
                if (!isexists) {
                    patterns.add("HH");
                    patterns.add("HH時");
                    isexists = patterns.contains(sOutFormat);
                }
            }
        }

        if (!isexists) {
            if (sOutFormat.startsWith("m")) {
                // 分秒
                patterns.add("mmss");
                patterns.add("mm:ss");
                patterns.add("mm分ss秒");
                isexists = patterns.contains(sOutFormat);

                // 分
                if (!isexists) {
                    patterns.add("mm");
                    patterns.add("mm分");
                    isexists = patterns.contains(sOutFormat);
                }
            }
        }

        if (!isexists) {
            if (sOutFormat.startsWith("s")) {
                // 秒
                if (!isexists) {
                    patterns.add("ss");
                    patterns.add("ss秒");
                    isexists = patterns.contains(sOutFormat);
                }
            }
        }

        if (!isexists) {
            // それでもなかったら、変換フォーマットを追加
            patterns.add(sOutFormat);
        }

        String[] pattern = patterns.toArray(new String[patterns.size()]);
        return format(sText, pattern, sOutFormat);
    }

    /**
     * 年数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する年数
     * @return 日付に年数を加算した結果の日付
     */
    public static Date addYears(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addYears(date, amount);
    }

    /**
     * 月数を加算後の日付を取得する。
     * <p>
     * addMonths(2008/01/30, 2) = 2008/03/30<br>
     * addMonths(2008/01/30, 1) = 2008/02/29<br>
     * addMonths(2008/03/30, -1) = 2008/02/29
     * </p>
     *
     * @param date   計算対象の日付
     * @param amount 加算する月数
     * @return 日付に月数を加算した結果の日付
     */
    public static Date addMonths(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMonths(date, amount);
    }

    /**
     * 週数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する週数
     * @return 日付に週数を加算した結果の日付
     */
    public static Date addWeeks(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addWeeks(date, amount);
    }

    /**
     * 日数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する日数
     * @return 日付に日数を加算した結果の日付
     */
    public static Date addDays(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addDays(date, amount);
    }

    /**
     * 時間数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する時間数
     * @return 日付に時間数を加算した結果の日付
     */
    public static Date addHours(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addHours(date, amount);
    }

    /**
     * 分数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する分数
     * @return 日付に分数を加算した結果の日付
     */
    public static Date addMinutes(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMinutes(date, amount);
    }

    /**
     * 秒数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する秒数
     * @return 日付に秒数を加算した結果の日付
     */
    public static Date addSeconds(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addSeconds(date, amount);
    }
    
    /**
     * ミリ秒数を加算後の日付を取得する。
     *
     * @param date   計算対象の日付
     * @param amount 加算する秒数
     * @return 日付に秒数を加算した結果の日付
     */
    public static Date addMilliseconds(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.addMilliseconds(date, amount);
    }

    /**
     * 指定された単位で日時を切捨てる
     *
     * @param date   計算対象の日付
     * @param amount 単位
     * @return 結果の日付
     */
    public static Date truncate(Date date, int amount) {
        return org.apache.commons.lang3.time.DateUtils.truncate(date, amount);
    }

    // 日付の差を取得する

    /**
     * 開始から終了の時間の差を年数で取得する。
     * <p>
     * 開始から終了の時間の差を年数で取得する。<br>
     * 端数は切り捨てる。
     * </p>
     *
     * @param startDate 開始日時
     * @param endDate   終了日時
     * @return 年数
     */
    public static int pireodYears(Date startDate, Date endDate) {
        return pireodAge(startDate, endDate);
        // return pireodYears(startDate, endDate, ROUND_DOWN);
    }

    /**
     * 開始から終了の時間の実年数で取得する。
     * <p>
     * 開始から終了の時間の差を年数で取得する。<br>
     * </p>
     *
     * @param startDate 開始日時
     * @param endDate   終了日時
     * @return 年数
     */
    public static int pireodAge(Date startDate, Date endDate) {
        int age = 0;
        if (startDate == null || endDate == null) {
            return age;
        }
        if (startDate.compareTo(endDate) > 0) {
            Date date = startDate;
            startDate = endDate;
            endDate = date;
        }
        while (startDate.compareTo(endDate) <= 0) {
            startDate = DateUtils.addYears(startDate, 1);
            age = age + 1;
        }
        age = age - 1;

        return age;
    }

    /**
     * 開始年月と終了年月により期間年数を算出する。(NEW)
     *
     * @param startYM 開始年月（西暦）
     * @param endYM   終了年月（西暦）
     * @return 年数
     */
    public static int pireodYears(String startYM, String endYM) {
        Date startDate = parseYMD(startYM + "01");
        Date endDate = parseYMD(endYM + "01");
        if (startDate.after(endDate)) {
            throw new IllegalArgumentException();
        }
        int pireod = pireodYears(startDate, endDate) + 1;
        if (pireod > 99) {
            throw new IllegalArgumentException();
        }
        return pireod;
    }

    /**
     * 指定された日付の月初日を取得する。
     *
     * @param date 日付
     * @return 月初日
     */
    public static Date getMonthFirstDay(Date date) {

        if (date == null) {
            throw new IllegalArgumentException();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return calendar.getTime();
    }

    /**
     * 指定された日付の月末日を取得する。
     *
     * @param date 日付
     * @return 月末日
     */
    public static Date getMonthEndDay(Date date) {

        if (date == null) {
            throw new IllegalArgumentException();
        }

        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        int monthEndDay = calendar.getActualMaximum(Calendar.DATE);
        calendar.set(Calendar.DATE, monthEndDay);

        return calendar.getTime();
    }

    /**
     * 指定された日付の年初日を取得する。
     *
     * @param date 日付
     * @return 年初日
     */
    public static Date getYearFirstDay(Date date) {

        if (date == null) {
            throw new IllegalArgumentException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_YEAR, 1);
        return calendar.getTime();
    }

    /**
     * 指定された日付の年末日を取得する。
     *
     * @param date 日付
     * @return 年末日
     */
    public static Date getYearEndDay(Date date) {

        if (date == null) {
            throw new IllegalArgumentException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        calendar.set(Calendar.DAY_OF_YEAR, 0);

        return calendar.getTime();
    }

    /**
     * システム時間を取得する。
     *
     * @return システム時間
     */
    public static Date getSysDate() {
        return Calendar.getInstance().getTime();
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
     * システム時間を取得する。
     *
     * @return システム時間
     */
    public static Timestamp toTimestamp(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return new Timestamp(calendar.getTimeInMillis());
    }

    /**
     * 指定された日付の週開始日を取得する。
     *
     * @param date 日付
     * @return 週開始日
     */
    public static Date getMonday(Date date) {

        if (date == null) {
            throw new IllegalArgumentException();
        }

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
            calendar.add(Calendar.DAY_OF_WEEK, -6);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 指定された日付の週終了日を取得する。
     *
     * @param date 日付
     * @return 週終了日
     */
    public static Date getSunday(Date date) {
        return DateUtils.addDays(DateUtils.getMonday(date), 6);
    }

    /**
     * <p>日付+時間の変換処理</p>
     *
     * @param date 日付
     * @param time 時間
     * @return 日付(yyyy - MM - dd) + 時間(hh:mm)
     */
    public static String setTimeToISO(Date date, Timestamp time) {
        // 時間
        Calendar timeCal = Calendar.getInstance();
        timeCal.setTime(time);
        // 日付
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.set(Calendar.HOUR_OF_DAY, timeCal.get(Calendar.HOUR_OF_DAY));
        cal.set(Calendar.MINUTE, timeCal.get(Calendar.MINUTE));
        cal.set(Calendar.MILLISECOND, 0);

        return DateUtils.format(cal.getTime(), Constant.DATE_FORMAT_YYYYMMDDHHMM_ISO);
    }

}
