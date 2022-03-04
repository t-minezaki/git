/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;

/**
 * <p>数値に関するユーティリティクラス</p>
 *
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2018/09/28  GAOLI: 新規
 * @version 1.0
 */
public final class NumberUtils {

    /**
     * NumberUtils を構築する。
     */
    private NumberUtils() {
    }

    /**
     * 文字列を int型に変換する。
     * <p>
     * 文字列が nullまたは長さ0の文字の場合は 0を返却する。<br>
     * </p>
     *
     * <pre>
     *   NumberUtils.toInt(null) = 0
     *   NumberUtils.toInt(&quot;&quot;) = 0
     *   NumberUtils.toInt(&quot;1&quot;) = 1
     * </pre>
     *
     * @param value 変換対象の文字列
     * @return 変換後の int型
     */
    public static int toInt(String value) {
        return toInt(value, 0);
    }

    /**
     * 文字列を int型に変換する。
     * <p>
     * 文字列が nullまたは長さ0の文字の場合は デフォルト値を返却する。<br>
     * </p>
     *
     * <pre>
     *   NumberUtils.toInt(null, 1) = 1
     *   NumberUtils.toInt(&quot;&quot;, 1) = 1
     *   NumberUtils.toInt(&quot;1&quot;, 0) = 1
     * </pre>
     *
     * @param value        変換対象の文字列
     * @param defaultValue 文字列がnullまたは長さ0の場合に返却するデフォルト値
     * @return 変換後の int型
     */
    public static int toInt(String value, int defaultValue) {
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return toBigDecimal(value).intValue();
    }

    /**
     * 文字列を long型に変換する。
     * <p>
     * 文字列が nullまたは長さ0の文字の場合は 0を返却する。<br>
     * </p>
     *
     * <pre>
     *   NumberUtils.toLong(null) = 0L
     *   NumberUtils.toLong(&quot;&quot;) = 0L
     *   NumberUtils.toLong(&quot;1&quot;) = 1L
     * </pre>
     *
     * @param value 変換対象の文字列
     * @return 変換後の long型
     */
    public static long toLong(String value) {
        return toLong(value, 0L);
    }

    /**
     * 文字列を int型に変換する。
     * <p>
     * 文字列が nullまたは長さ0の文字の場合は デフォルト値を返却する。<br>
     * </p>
     *
     * <pre>
     *   NumberUtils.toLong(null, 1L) = 1
     *   NumberUtils.toLong(&quot;&quot;, 1L) = 1
     *   NumberUtils.toLong(&quot;1&quot;, 0L) = 1
     * </pre>
     *
     * @param value        変換対象の文字列
     * @param defaultValue 文字列がnullまたは長さ0の場合に返却するデフォルト値
     * @return 変換後の int型
     */
    public static long toLong(String value, long defaultValue) {
        if (StringUtils.isEmpty(value)) {
            return defaultValue;
        }
        return toBigDecimal(value).longValue();
    }

    /**
     * 文字列を BigDecimalに変換する。
     * <p>
     * 文字列が nullまたは長さ0の文字の場合は 0を返却する。<br>
     * </p>
     *
     * @param value 変換対象の文字列
     * @return 変換後の BigDecimal
     */
    public static BigDecimal toBigDecimal(String value) {
        return toBigDecimal(value, 0);
    }

    /**
     * BigDecimalが nullの場合は 0を返却する。
     *
     * @param value 変換対象
     * @return 変換後の BigDecimal
     */
    public static BigDecimal toBigDecimal(BigDecimal value) {
        if (value == null) {
            return new BigDecimal(0);
        }
        return value;
    }

    /**
     * Objectが nullの場合は 0を返却する。
     *
     * @param value 変換対象
     * @return 変換後の BigDecimal
     */
    public static BigDecimal toBigDecimal(Object value) {

        return toBigDecimal(StringUtils.defaultString(value), 0);
    }

    /**
     * 文字列を BigDecimalに変換する。
     * <p>
     * 文字列が nullまたは長さ0の文字の場合は デフォルト値を返却する。<br>
     * </p>
     *
     * <pre>
     *   NumberUtils.toBigDecimal(null, 1D) = 1
     *   NumberUtils.toBigDecimal(&quot;&quot;, 1D) = 1
     *   NumberUtils.toBigDecimal(&quot;1&quot;, 0D) = 1
     * </pre>
     *
     * @param value        変換対象の文字列
     * @param defaultValue 文字列がnullまたは長さ0の場合に返却するデフォルト値
     * @return 変換後の BigDecimal
     */
    public static BigDecimal toBigDecimal(String value, double defaultValue) {
        if ("%".equals(StringUtils.right(value, 1))) {
            BigDecimal result = new BigDecimal(StringUtils.left(value, value.length() - 1));
            return result.divide(new BigDecimal(100));
        }
        if (StringUtils.isEmpty(value)) {
            return new BigDecimal(defaultValue);
        }
        return new BigDecimal(StringUtils.replace(value, ",", ""));
    }

    /**
     * 文字列から BegDecimalを生成する。
     * <p>
     * 文字列が <code>null</code> または 長さ0の場合は <code>null</code>が返却される。
     * </p>
     *
     * <pre>
     *   NumberUtils.createBegDecimal(null) = null
     *   NumberUtils.createBegDecimal(&quot;&quot;) = null
     *   NumberUtils.createBegDecimal(&quot;1&quot;) = 1
     * </pre>
     *
     * @param value 変換対象の文字列
     * @return 変換後の BigDecimal
     */
    public static BigDecimal createBigDecimal(String value) {
        if (StringUtils.isEmpty(value)) {
            return null;
        }
        return new BigDecimal(StringUtils.replace(value, ",", ""));
    }

    /**
     * 指定した数値書式に変換する。
     *
     * @param value  変換対象の文字列
     * @param format 数値書式
     * @return 変換後の文字列
     */
    public static String format(String value, String format) {
        return format(toBigDecimal(value), format);
    }

    /**
     * 指定した数値書式に変換する。
     *
     * @param value  変換対象の数値
     * @param format 数値書式
     * @return 変換後の文字列
     */
    public static String format(BigDecimal value, String format) {
        DecimalFormat df = new DecimalFormat(format);
        if (value == null) {
            return null;
        } else if (value.compareTo(BigDecimal.ZERO) == 0) {
            return StringUtils.defaultString(value);
        }
        return df.format(value.doubleValue());
    }

    /**
     * 乗算を行う。
     * <p>
     * ( value * divisor ) である乗算結果を返却する。
     * </p>
     *
     * @param value        計算対象値
     * @param multiplicand 乗算する値
     * @return 除算結果
     */
    public static BigDecimal multiply(int value, int multiplicand) {
        return multiply(value, multiplicand, 0, RoundingMode.DOWN);
    }

    /**
     * 乗算を行う。
     * <p>
     * ( value * divisor ) である乗算結果を返却する。<br>
     * 指定されたスケール、丸めモードが乗算結果に適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param multiplicand 乗算する値
     * @param scale        返却する値のスケール
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal multiply(int value, int multiplicand, int scale, RoundingMode roundingMode) {
        return multiply(new BigDecimal(value), new BigDecimal(multiplicand), 0, roundingMode);
    }

    /**
     * 乗算を行う。
     * <p>
     * ( value * divisor ) である除算結果を返却する。<br>
     * スケール 0、丸めモード {link@RoundingMode#DOWN} が乗算結果に適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param multiplicand 除算する値
     * @return 除算結果
     */
    public static BigDecimal multiply(String value, String multiplicand) {
        return multiply(toBigDecimal(value), toBigDecimal(multiplicand), 0, RoundingMode.DOWN);
    }

    /**
     * 乗算を行う。
     * <p>
     * ( value * divisor ) である除算結果を返却する。<br>
     * 指定されたスケール、丸めモードが乗算結果に適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param multiplicand 除算する値
     * @param scale        返却する値のスケール
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal multiply(String value, String multiplicand, int scale, RoundingMode roundingMode) {
        return multiply(toBigDecimal(value), toBigDecimal(multiplicand), scale, roundingMode);
    }

    /**
     * 乗算を行う。
     * <p>
     * ( value / divisor ) で、除算結果を返却する。<br>
     * 指定されたスケール、丸めモードが適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param multiplicand 除算する値
     * @param scale        返却する値のスケール
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal multiply(BigDecimal value, BigDecimal multiplicand, int scale,
                                      RoundingMode roundingMode) {

        BigDecimal result = value.multiply(multiplicand);
        return result.setScale(scale, roundingMode);
    }

    /**
     * 除算を行う。
     * <p>
     * ( value / divisor ) で、スケールが 0 である除算結果を返却する。<br>
     * 丸めモードは、{link@RoundingMode#DOWN}
     * </p>
     *
     * @param value   計算対象値
     * @param divisor 除算する値
     * @return 除算結果
     */
    public static BigDecimal devide(int value, int divisor) {
        return devide(value, divisor, 0, RoundingMode.DOWN);
    }

    /**
     * 除算を行う。
     * <p>
     * ( value / divisor ) で、スケールが 0 である除算結果を返却する。<br>
     * 指定された丸めモードが適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param divisor      除算する値
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal devide(int value, int divisor, RoundingMode roundingMode) {
        return devide(value, divisor, 0, roundingMode);
    }

    /**
     * 除算を行う。
     * <p>
     * ( value / divisor ) で、除算結果を返却する。<br>
     * 指定されたスケール、丸めモードが適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param divisor      除算する値
     * @param scale        返却する値のスケール
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal devide(int value, int divisor, int scale, RoundingMode roundingMode) {
        return devide(new BigDecimal(value), new BigDecimal(divisor), scale, roundingMode);
    }

    /**
     * 除算を行う。
     * <p>
     * ( value / divisor ) で、スケールが 0 である除算結果を返却する。<br>
     * 丸めモードは、{link@RoundingMode#DOWN}
     * </p>
     *
     * @param value   計算対象値
     * @param divisor 除算する値
     * @return 除算結果
     */
    public static BigDecimal devide(String value, String divisor) {
        return devide(toBigDecimal(value), toBigDecimal(divisor), 0, RoundingMode.DOWN);
    }

    /**
     * 除算を行う。
     * <p>
     * ( value / divisor ) で、除算結果を返却する。<br>
     * 指定されたスケール、丸めモードが適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param divisor      除算する値
     * @param scale        返却する値のスケール
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal devide(String value, String divisor, int scale, RoundingMode roundingMode) {
        return devide(toBigDecimal(value), toBigDecimal(divisor), scale, roundingMode);
    }

    /**
     * 除算を行う。
     * <p>
     * ( value / divisor ) で、除算結果を返却する。<br>
     * 指定されたスケール、丸めモードが適用される。
     * </p>
     *
     * @param value        計算対象値
     * @param divisor      除算する値
     * @param scale        返却する値のスケール
     * @param roundingMode 丸めモード
     * @return 除算結果
     */
    public static BigDecimal devide(BigDecimal value, BigDecimal divisor, int scale, RoundingMode roundingMode) {
        return value.divide(divisor, scale, roundingMode);
    }

    /**
     * 除算を行ない、商と剰余を返却する。
     * <p>
     * ( value / divisor ) の整数（商）と、( value % divisor ) である剰余を返却する。
     * </p>
     *
     * @param value   計算対象値
     * @param divisor 除算する値
     * @return 除算結果
     */
    public static BigDecimal[] divideAndRemainder(BigDecimal value, BigDecimal divisor) {
        return value.divideAndRemainder(divisor);
    }

    /**
     * 絶対値を返却する。
     *
     * @param value 取得対象値
     * @return 絶対値
     */
    public static BigDecimal abs(int value) {
        return abs(new BigDecimal(value));
    }

    /**
     * 絶対値を返却する。
     *
     * @param value 取得対象値
     * @return 絶対値
     */
    public static BigDecimal abs(String value) {
        return abs(toBigDecimal(value));
    }

    /**
     * 絶対値を返却する。
     *
     * @param value 取得対象値
     * @return 絶対値
     */
    public static BigDecimal abs(BigDecimal value) {
        return value.abs();
    }

    /**
     * -1 を乗算した値を返却する。
     *
     * @param value 取得対象値
     * @return -1 を乗算した値
     */
    public static BigDecimal negate(int value) {
        return negate(new BigDecimal(value));
    }

    /**
     * -1 を乗算した値を返却する。
     *
     * @param value 取得対象値
     * @return -1 を乗算した値
     */
    public static BigDecimal negate(String value) {
        return negate(toBigDecimal(value));
    }

    /**
     * -1 を乗算した値を返却する。
     *
     * @param value 取得対象値
     * @return -1 を乗算した値
     */
    public static BigDecimal negate(BigDecimal value) {
        return value.negate();
    }

    /**
     * 指定されたスケール、丸めモードを適用した値を返却する。
     *
     * @param value        取得対象値
     * @param scale        スケール
     * @param roundingMode 丸めモード
     * @return 丸めた値
     */
    public static BigDecimal round(BigDecimal value, int scale, RoundingMode roundingMode) {
        return value.setScale(scale, roundingMode);
    }


    /**
     * longToInt。
     *
     * @return 丸めた値
     */
    public static Integer longToInt(Long data) {
        if (data == null) {
            return null;
        }
        String str = data.toString();
        return Integer.valueOf(str);
    }

}
