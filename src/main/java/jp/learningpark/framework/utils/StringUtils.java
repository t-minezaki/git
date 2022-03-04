/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.regex.Pattern;

/**
 * <p>文字列ユーティリティクラス</p>
 *
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2018/09/28  GAOLI: 新規
 * @version 1.0
 */
public final class StringUtils {

    /**
     * 空文字列
     **/
    public static final String EMPTY = "";
    /**
     * 半角文字のコードの範囲(UTF-8下限)
     */
    public static final char ALPHABETLO = 0x0020; // space
    /**
     * 半角文字のコードの範囲(UTF-8上限)
     */
    public static final char ALPHABETHI = 0x007e; // ~
    /**
     * 日本語(句点から半濁点まで)(UTF-8下限)
     */
    public static final char KANALO_UTF_8 = 0xff61; // ｡ 65377(10)
    /**
     * 日本語(句点から半濁点まで)(UTF-8上限)
     */
    public static final char KANAHI_UTF_8 = 0xff9f; // ﾟ 65439(10)
    /**
     * 汎用数値フォーマッター
     */
    private static final NumberFormat FORMATTER_GENERIC_NUMBER = NumberFormat.getNumberInstance();

    /**
     * StringUtils を構築する。
     */
    private StringUtils() {
    }

    /**
     * defaultString
     *
     * @param sText チェックを行う対象となる文字列
     * @return String 渡された文字列、null の場合には空の文字列
     */
    public static String defaultString(String sText) {
        return org.apache.commons.lang3.StringUtils.defaultString(sText);
    }

    /**
     * defaultString
     *
     * @param value 対象数列
     * @return String 変換された値
     */
    public static String defaultString(int value) {
        return String.valueOf(value);
    }

    /**
     * defaultString
     *
     * @param value 対象数列
     * @return String 変換された値
     */
    public static String defaultString(BigDecimal value) {
        return defaultString(value, false);
    }

    /**
     * defaultString
     *
     * @param value       対象数列
     * @param isEditComma カンマ編集を行う場合 true
     * @return String 変換された値
     */
    public static String defaultString(BigDecimal value, boolean isEditComma) {
        if (value == null) {
            return "";
        }
        return isEditComma ? FORMATTER_GENERIC_NUMBER.format(value) : value.toPlainString();
    }

    /**
     * defaultString
     *
     * @param value 対象オブジェクト
     * @return String 変換された値
     */
    public static String defaultString(Object value) {
        return value == null ? "" : value.toString();
    }

    /**
     * 文字列の対象文字を指定された文字で置き換える。
     *
     * @param sText 対象文字列
     * @param rep1 置換え対象の文字列
     * @param rep2 置換え後の文字列
     * @return 置換え結果の文字列
     * @see org.apache.commons.lang3.StringUtils#replace(String, String, String)
     */
    public static String replace(String sText, String rep1, String rep2) {
        return org.apache.commons.lang3.StringUtils.replace(sText, rep1, rep2);
    }

    /**
     * 文字列内で指定された文字が最初に出現する位置のインデックスを返す。
     * <p>
     * 文字列内で指定された検索文字列が最初に出現する位置のインデックスを返す。<br>
     * 検索文字列が複数指定された場合は、いずれかの検索文字列が最初に出現する位置のインデックスるを返す。<br>
     * {@link String#indexOf(String)} を使用。
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, &quot;a&quot;) = -1
     * StringUtils.indexOf(&quot;aab&quot;, null) = -1
     * StringUtils.indexOf(&quot;&quot;, &quot;&quot;) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;) = 2
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;) = 1
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;&quot;) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;aa&quot;, &quot;bb&quot;, &quot;cc&quot;) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;aaa&quot;, &quot;baa&quot;) = 2
     * </pre>
     *
     * @param sText   検査対象の文字列
     * @param search 検索する文字列
     * @return 検索文字列が検査対象の文字列内で最初に出現したインデックス番号<br>
     * <code>-1</code>: 検索文字列が存在しないもしくは、検査対象の文字列、検索文字列がnullの場合
     */
    public static int indexOf(String sText, String... search) {
        return org.apache.commons.lang3.StringUtils.indexOfAny(sText, search);
    }

    /**
     * 文字列内の指定されたインデックス以降で指定された文字が最初に出現する位置のインデックスを返す。
     * <p>
     * {@link String#indexOf(String)} を使用。
     * </p>
     *
     * <pre>
     * StringUtils.indexOf(null, &quot;a&quot;, 2) = -1
     * StringUtils.indexOf(&quot;aab&quot;, null, 2) = -1
     * StringUtils.indexOf(&quot;&quot;, &quot;&quot;, 2) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 1) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;a&quot;, 2) = 1
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 1) = 2
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;b&quot;, 2) = 5
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 1) = 1
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;ab&quot;, 2) = 4
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;&quot;, 1) = 0
     * StringUtils.indexOf(&quot;aabaabaa&quot;, &quot;&quot;, 2) = 0
     * </pre>
     *
     * @param sText   検査対象の文字列
     * @param search 検索する文字列
     * @param start  検索開始位置のインデックス
     * @return 検索文字列が検査対象の文字列内の検索開始位置以降で最初に出現したインデックス番号<br>
     * <code>-1</code>: 検索文字列が存在しないもしくは、検査対象の文字列、検索文字列がnullの場合
     */
    public static int indexOf(String sText, String search, int start) {
        return org.apache.commons.lang3.StringUtils.indexOf(sText, search, start);
    }

    /**
     * 文字列内で指定された文字が最後に出現する位置のインデックスを返す。
     * <p>
     * 文字列内で指定された検索文字列が最後に出現する位置のインデックスを返す。<br>
     * {@link String#lastIndexOf(String)} を使用。
     * </p>
     *
     * @param sText   検査対象の文字列
     * @param search 検索する文字列
     * @return 検索文字列が検査対象の文字列内で最後に出現したインデックス番号<br>
     * <code>-1</code>: 検索文字列が存在しないもしくは、検査対象の文字列、検索文字列がnullの場合
     */
    public static int lastIndexOf(String sText, String... search) {
        return org.apache.commons.lang3.StringUtils.lastIndexOfAny(sText, search);
    }

    /**
     * lastIndexOf
     *
     * @param sText   対象文字列
     * @param search 検索文字列
     * @param start  スタート値
     * @return int
     */
    public static int lastIndexOf(String sText, String search, int start) {
        return org.apache.commons.lang3.StringUtils.lastIndexOf(sText, search, start);
    }

    /**
     * startWith
     *
     * @param sText   対象文字列
     * @param prefix 比較文字列
     * @return boolean 判定結果
     */
    public static boolean startWith(String sText, String prefix) {
        return org.apache.commons.lang3.StringUtils.startsWith(sText, prefix);
    }

    /**
     * endsWith
     *
     * @param sText   対象文字列
     * @param prefix 比較文字列
     * @return boolean 判定結果
     */
    public static boolean endsWith(String sText, String prefix) {
        return org.apache.commons.lang3.StringUtils.endsWith(sText, prefix);
    }

    /**
     * contains
     *
     * @param sText   対象文字列
     * @param search 検索文字列
     * @return boolean 判定結果
     */
    public static boolean contains(String sText, String... search) {
        // 指定された検索文字列のいずれかが存在するか
        return (indexOf(sText, search) >= 0);
    }

    /**
     * containsIgnoreCase
     *
     * @param sText   対象文字列
     * @param search 検索文字列
     * @return boolean 判定結果
     */
    public static boolean containsIgnoreCase(String sText, String... search) {

        // 指定された検索文字列のいずれかが大／小文字の区別なく存在するか
        if (search == null || search.length == 0) {
            return false;
        } else {
            for (int i = 0; i < search.length; i++) {
                if (indexOf(sText.toUpperCase(), search[i].toUpperCase()) >= 0) {
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * substring
     *
     * @param sText  対象文字列
     * @param start 開始値
     * @return String 抽出文字列
     */
    public static String substring(String sText, int start) {
        return org.apache.commons.lang3.StringUtils.substring(sText, start);
    }

    /**
     * substring
     *
     * @param sText  対象文字列
     * @param start 開始値
     * @param end   終了値
     * @return String 抽出文字列
     */
    public static String substring(String sText, int start, int end) {
        return org.apache.commons.lang3.StringUtils.substring(sText, start, end);
    }

    /**
     * 概要をここに記述。
     * <p>
     * 詳細をここに記述<br>
     * </p>
     *
     * <pre>
     * StringUtils.substringBetween(null, *)            = null
     * StringUtils.substringBetween(&quot;&quot;, &quot;&quot;)             = &quot;&quot;
     * StringUtils.substringBetween(&quot;&quot;, &quot;tag&quot;)          = null
     * StringUtils.substringBetween(&quot;tagabctag&quot;, null)  = null
     * StringUtils.substringBetween(&quot;tagabctag&quot;, &quot;&quot;)    = &quot;&quot;
     * StringUtils.substringBetween(&quot;tagabctag&quot;, &quot;tag&quot;) = &quot;abc&quot;
     * </pre>
     *
     * @param sText 対象文字列
     * @param tag  タグ文字列
     * @return String
     */
    public static String substringBetween(String sText, String tag) {
        return org.apache.commons.lang3.StringUtils.substringBetween(sText, tag);
    }

    /**
     * substringBetween
     *
     * @param sText  対象文字列
     * @param open  open
     * @param close close
     * @return String
     */
    public static String substringBetween(String sText, String open, String close) {
        return org.apache.commons.lang3.StringUtils.substringBetween(sText, open, close);
    }

    /**
     * left
     *
     * @param sText   対象文字列
     * @param length レングス
     * @return String
     */
    public static String left(String sText, int length) {
        return org.apache.commons.lang3.StringUtils.left(sText, length);
    }

    /**
     * left
     *
     * <pre>
     * StringUtils.left(null, *)      = null
     * StringUtils.left(&quot;&quot;, *)        = &quot;&quot;
     * StringUtils.left(&quot;abc&quot;, &quot;a&quot;)   = &quot;&quot;
     * StringUtils.left(&quot;abcba&quot;, &quot;b&quot;) = &quot;a&quot;
     * StringUtils.left(&quot;abc&quot;, &quot;c&quot;)   = &quot;ab&quot;
     * StringUtils.left(&quot;abc&quot;, &quot;d&quot;)   = &quot;abc&quot;
     * StringUtils.left(&quot;abc&quot;, &quot;&quot;)    = &quot;&quot;
     * StringUtils.left(&quot;abc&quot;, null)  = &quot;abc&quot;
     * </pre>
     *
     * @param sText      対象文字列
     * @param separator セパレータ
     * @return String
     */
    public static String left(String sText, String separator) {
        return org.apache.commons.lang3.StringUtils.substringBefore(sText, separator);
    }

    /**
     * right
     *
     * @param sText   対象文字列
     * @param length レングス
     * @return String
     */
    public static String right(String sText, int length) {
        return org.apache.commons.lang3.StringUtils.right(sText, length);
    }

    /**
     * right
     *
     * <pre>
     * StringUtils.right(null, *)      = null
     * StringUtils.right(&quot;&quot;, *)        = &quot;&quot;
     * StringUtils.right(*, null)      = &quot;&quot;
     * StringUtils.right(&quot;abc&quot;, &quot;a&quot;)   = &quot;bc&quot;
     * StringUtils.right(&quot;abcba&quot;, &quot;b&quot;) = &quot;cba&quot;
     * StringUtils.right(&quot;abc&quot;, &quot;c&quot;)   = &quot;&quot;
     * StringUtils.right(&quot;abc&quot;, &quot;d&quot;)   = &quot;&quot;
     * StringUtils.right(&quot;abc&quot;, &quot;&quot;)    = &quot;abc&quot;
     * </pre>
     *
     * @param sText      対象文字列
     * @param separator セパレータ
     * @return String
     */
    public static String right(String sText, String separator) {
        return org.apache.commons.lang3.StringUtils.substringAfter(sText, separator);
    }

    /**
     * mid
     *
     * @param sText   対象文字列
     * @param start  開始値
     * @param length レングス
     * @return String
     */
    public static String mid(String sText, int start, int length) {
        return org.apache.commons.lang3.StringUtils.mid(sText, start, length);
    }

    /**
     * mid
     *
     * <pre>
     * StringUtils.mid(&quot;wx[b]yz&quot;, &quot;[&quot;, &quot;]&quot;) = &quot;b&quot;
     * StringUtils.mid(null, *, *)          = null
     * StringUtils.mid(*, null, *)          = null
     * StringUtils.mid(*, *, null)          = null
     * StringUtils.mid(&quot;&quot;, &quot;&quot;, &quot;&quot;)          = &quot;&quot;
     * StringUtils.mid(&quot;&quot;, &quot;&quot;, &quot;]&quot;)         = null
     * StringUtils.mid(&quot;&quot;, &quot;[&quot;, &quot;]&quot;)        = null
     * StringUtils.mid(&quot;yabcz&quot;, &quot;&quot;, &quot;&quot;)     = &quot;&quot;
     * StringUtils.mid(&quot;yabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
     * StringUtils.mid(&quot;yabczyabcz&quot;, &quot;y&quot;, &quot;z&quot;)   = &quot;abc&quot;
     * </pre>
     *
     * @param sText     対象文字列
     * @param openTag  開始タグ
     * @param closeTag 終了タグ
     * @return String
     */
    public static String mid(String sText, String openTag, String closeTag) {
        return org.apache.commons.lang3.StringUtils.substringBetween(sText, openTag, closeTag);
    }

    /**
     * 文字列を分割文字で分割し配列の文字列に変換する。
     * <p>
     * 指定された分割文字で文字列を配列に分割する。<br>
     * 分割文字で囲まれた長さ0の文字列も対象となる。
     * </p>
     *
     * <pre>
     *   StringUtils.split(null, ',')   = null
     *   StringUtils.split(&quot;&quot;, ',')   = []
     *   StringUtils.split(&quot;a,b,c&quot;, ',')   = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
     *   StringUtils.split(&quot;a,b,,c&quot;, ',')   = [&quot;a&quot;, &quot;b&quot;, &quot;&quot;, &quot;c&quot;]
     * </pre>
     *
     * @param sText      分割対象の文字列
     * @param separator 分割する文字
     * @return 分割後の配列の文字列
     */
    public static String[] split(String sText, String separator) {
        return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(sText, separator);
    }

    /**
     * 文字列を分割文字で分割し配列の文字列に変換する。
     * <p>
     * 指定された分割文字で文字列を配列に分割する。<br>
     * 分割文字で囲まれた長さ0の文字列を対象とするかどうかを指定できる。
     * </p>
     *
     * <pre>
     *   StringUtils.split(&quot;a,b,,c&quot;, ',', true)  = [&quot;a&quot;, &quot;b&quot;, &quot;&quot;, &quot;c&quot;]
     *   StringUtils.split(&quot;a,b,,c&quot;, ',', false) = [&quot;a&quot;, &quot;b&quot;, &quot;c&quot;]
     * </pre>
     *
     * @param sText              分割対象の文字列
     * @param separator         分割する文字
     * @param preserveAllTokens 分割したトークン全てを対象とするか、長さ0の場合は排除するかを指定
     *                           <ul>
     *                           <li><code>true</code>: 全てを対象</li>
     *                           <li><code>false</code>: 長さ0のトークンは排除</li>
     *                           </ul>
     * @return 分割後の配列の文字列
     */
    public static String[] split(String sText, String separator, boolean preserveAllTokens) {
        if (preserveAllTokens) {
            return org.apache.commons.lang3.StringUtils.splitPreserveAllTokens(sText, separator);
        } else {
            return org.apache.commons.lang3.StringUtils.split(sText, separator);
        }
    }

    /**
     * 配列内の要素を連結する。区切り文字なし。
     *
     * <pre>
     *   StringUtils.join(null, ',')                = null
     *   StringUtils.join([], ',')                  = &quot;&quot;
     *   StringUtils.join([null], ',')              = &quot;&quot;
     *   StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], ',')     = &quot;abc&quot;
     *   StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;&quot;, &quot;c&quot;], ',') = &quot;abc&quot;
     * </pre>
     *
     * @param array 配列
     * @return 連結結果の文字列
     */
    public static String join(Object[] array) {
        return org.apache.commons.lang3.StringUtils.join(array);
    }

    /**
     * 配列内の要素を指定された区切り文字で連結する。
     *
     * <pre>
     *   StringUtils.join(null, ',')                = null
     *   StringUtils.join([], ',')                  = &quot;&quot;
     *   StringUtils.join([null], ',')              = &quot;&quot;
     *   StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], ',')     = &quot;a,b,c&quot;
     *   StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;c&quot;], null)    = &quot;abc&quot;
     *   StringUtils.join([&quot;a&quot;, &quot;b&quot;, &quot;&quot;, &quot;c&quot;], ',') = &quot;a,b,,c&quot;
     * </pre>
     *
     * @param array     配列
     * @param separator 区切り文字
     * @return 連結結果の文字列
     */
    public static String join(Object[] array, char separator) {
        return org.apache.commons.lang3.StringUtils.join(array, separator);
    }

    /**
     * repeat
     *
     * @param sText   対象文字列
     * @param repeat 繰り返し数
     * @return String
     */
    public static String repeat(String sText, int repeat) {
        return org.apache.commons.lang3.StringUtils.repeat(sText, repeat);
    }

    /**
     * leftPad
     *
     * @param sText 対象文字列
     * @param size サイズ
     * @return String
     */
    public static String leftPad(String sText, int size) {
        return org.apache.commons.lang3.StringUtils.leftPad(sText, size);
    }

    /**
     * leftPad
     *
     * @param sText   対象文字列
     * @param size   サイズ
     * @param padStr 埋め込み文字列
     * @return String
     */
    public static String leftPad(String sText, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.leftPad(sText, size, padStr);
    }

    /**
     * rightPad
     *
     * @param sText 対象文字列
     * @param size サイズ
     * @return String
     */
    public static String rightPad(String sText, int size) {
        return org.apache.commons.lang3.StringUtils.rightPad(sText, size);
    }

    /**
     * rightPad
     *
     * @param sText   対象文字列
     * @param size   サイズ
     * @param padStr 埋め込み文字列
     * @return String
     */
    public static String rightPad(String sText, int size, String padStr) {
        return org.apache.commons.lang3.StringUtils.rightPad(sText, size, padStr);
    }

    /**
     * trim
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String trim(String sText) {
        return org.apache.commons.lang3.StringUtils.trim(sText);
    }

    /**
     * trimToNull
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String trimToNull(String sText) {
        return org.apache.commons.lang3.StringUtils.trimToNull(sText);
    }

    /**
     * deleteWhitespace
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String deleteWhitespace(String sText) {
        return org.apache.commons.lang3.StringUtils.deleteWhitespace(sText);
    }

    /**
     * upperCase
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String upperCase(String sText) {
        return org.apache.commons.lang3.StringUtils.upperCase(sText);
    }

    /**
     * lowerCase
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String lowerCase(String sText) {
        return org.apache.commons.lang3.StringUtils.lowerCase(sText);
    }

    /**
     * swapCase
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String swapCase(String sText) {
        return org.apache.commons.lang3.StringUtils.swapCase(sText);
    }

    /**
     * capitalize
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String capitalize(String sText) {
        return org.apache.commons.lang3.StringUtils.capitalize(sText);
    }

    /**
     * capitalize
     *
     * @param sText      対象文字列
     * @param separator セパレータ
     * @return String
     */
    public static String capitalize(String sText, char separator) {

        if (StringUtils.isEmpty(sText)) {
            return sText;
        } else {
            String[] splits = split(sText, String.valueOf(separator));
            StringBuilder sb = new StringBuilder(sText.length());
            for (int i = 0; i < splits.length; i++) {
                sb.append(org.apache.commons.lang3.StringUtils.capitalize(splits[i]));
            }
            return sb.toString();
        }
    }

    /**
     * reverse
     *
     * <pre>
     * StringUtils.reverse(null)  = null
     * StringUtils.reverse(&quot;&quot;)    = &quot;&quot;
     * StringUtils.reverse(&quot;bat&quot;) = &quot;tab&quot;
     * </pre>
     *
     * @param sText 対象文字列
     * @return String
     */
    public static String reverse(String sText) {
        return org.apache.commons.lang3.StringUtils.reverse(sText);
    }

    /**
     * reverseDelimited
     *
     * <pre>
     * StringUtils.reverseDelimited(null, *)      = null
     * StringUtils.reverseDelimited(&quot;&quot;, *)        = &quot;&quot;
     * StringUtils.reverseDelimited(&quot;a.b.c&quot;, 'x') = &quot;a.b.c&quot;
     * StringUtils.reverseDelimited(&quot;a.b.c&quot;, &quot;.&quot;) = &quot;c.b.a&quot;
     * StringUtils.reverseDelimited(&quot;ab.cd.ef&quot;, &quot;.&quot;) = &quot;c.b.a&quot;
     * </pre>
     *
     * @param sText      対象文字列
     * @param separator セパレータ
     * @return String
     */
    public static String reverseDelimited(String sText, char separator) {
        return org.apache.commons.lang3.StringUtils.reverseDelimited(sText, separator);
    }

    /**
     * abbreviate
     *
     * <pre>
     * StringUtils.abbreviate(null, *)      = null
     * StringUtils.abbreviate(&quot;&quot;, 4)        = &quot;&quot;
     * StringUtils.abbreviate(&quot;abcdefg&quot;, 6) = &quot;abc...&quot;
     * StringUtils.abbreviate(&quot;abcdefg&quot;, 7) = &quot;abcdefg&quot;
     * StringUtils.abbreviate(&quot;abcdefg&quot;, 8) = &quot;abcdefg&quot;
     * StringUtils.abbreviate(&quot;abcdefg&quot;, 4) = &quot;a...&quot;
     * StringUtils.abbreviate(&quot;abcdefg&quot;, 3) = IllegalArgumentException
     *
     * @param sText 対象文字列
     * @param length レングス
     * @return String
     */
    public static String abbreviate(String sText, int length) {
        return org.apache.commons.lang3.StringUtils.abbreviate(sText, length);
    }

    /**
     * 文字列のバイト数を取得する。
     * <p>
     * ログインユーザのエンコードで文字列のバイト数を取得する。
     * </p>
     *
     * @param value 対象文字列
     * @return 文字列のバイト数
     * @since 1.0
     */
    public static int getByteSize(String value) {
        return getByteSize(value, "UTF-8");
    }

    /**
     * 文字列のバイト数を取得する。
     * <p>
     * 指定されたエンコードで文字列のバイト数を取得する。
     * </p>
     *
     * @param value  対象文字列
     * @param encode エンコード
     * @return 文字列のバイト数
     * @since 1.0
     */
    public static int getByteSize(String value, String encode) {
        int size = 0;

        if (!StringUtils.isEmpty(encode)) {
            try {
                size = value.getBytes(encode).length;

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            size = value.getBytes().length;
        }

        return size;
    }

    /**
     * 指定されたパターンを使用してMessageFormatを生成し、指定された置換え文字列をフォーマットする。
     *
     * @param pattern  パターン文字列
     * @param replaces 置換え文字列
     * @return フォーマット後文字列
     */
    public static String messageFormat(String pattern, Object... replaces) {
        return MessageFormat.format(pattern, replaces);
    }

    /**
     * 指定された文字列がnullかどうかをチェックする。
     * <p>
     * 評価対象に指定された文字列がnullかどうかをチェックする。 長さ0の場合もnullと評価する。
     * </p>
     *
     * @param value 評価対象文字列
     * @return 評価結果 nullの場合は true、以外は false
     */
    public static boolean isEmpty(String value) {
        if (value == null || StringUtils.trim(value).length() == 0) {
            return true;
        }
        return false;
    }

    /**
     * 指定された文字列がnullかどうかをチェックする。
     * <p>
     * 評価対象に指定された文字列がnullかどうかをチェックする。 長さ0の場合もnullと評価する。
     * </p>
     *
     * @param value1 評価対象文字列
     * @param value2 評価対象文字列
     * @return 評価結果 nullの場合は true、以外は false
     */
    public static boolean equals(String value1, String value2) {
        return org.apache.commons.lang3.StringUtils.equals(value1, value2);
    }

    /**
     * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
     *
     * <pre>
     * StringUtils.isNotBlank(null)      = false
     * StringUtils.isNotBlank("")        = false
     * StringUtils.isNotBlank(" ")       = false
     * StringUtils.isNotBlank("bob")     = true
     * StringUtils.isNotBlank("  bob  ") = true
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is
     * not empty and not null and not whitespace
     * @since 2.0
     */
    public static boolean isNotBlank(String str) {
        return !StringUtils.isBlank(str);
    }

    /**
     * <p>Checks if a String is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     * @param str the String to check, may be null
     * @return <code>true</code> if the String is null, empty or whitespace
     * @since 2.0
     */
    public static boolean isBlank(String str) {
        return org.apache.commons.lang3.StringUtils.isBlank(str);
    }

    /**
     * 全角文字は２桁、半角文字は１桁として文字数をカウントする
     * @param str 対象文字列
     * @return 文字数
     */
    public static int getStrLength(String str) {
        //戻り値
        int ret = 0;

        //全角半角判定
        char[] c = str.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (String.valueOf(c[i]).getBytes().length <= 1) {
                //半角文字なら＋１
                ret += 1;
            } else {
                //全角文字なら＋２
                ret += 2;
            }
        }
        return ret;
    }

    /**
     * <p>
     * 対象文字数をカウントする
     * </p>
     *
     * @param str 対象文字列
     * @param chs 対象文字列
     * @return カウント
     */
    public static int getStringCnt(String str, String chs){
        if (str.length() ==0 || chs.length() == 0) {
            return 0;
        }
        String destStr = str.replaceAll(chs, "");
        return (str.length() - destStr.length()) / chs.length();
    }

    /**
     * <p>パスワードフォーマットのチェック</p>
     * @param password
     * @return
     */
    public static boolean PWDCheck(String password) {
        Pattern pattern = Pattern.compile("^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)[A-Za-z\\d]{8,32}$");
        return pattern.matcher(password).matches();
    }


    public static String changeEmptyStringToNull(String str){
        return isEmpty(str) ? null : str;
    }

    /**
     * 文字列がメールボックスかどうかを確認する
     *
     * @param target 検証するターゲット文字列
     */
    public static boolean isEmail(String target){
        if (isEmpty(target)){
            return false;
        }
        return target.matches("[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?");
    }
    private static final Pattern number = Pattern.compile("[0-9]");
    public static String getTelNum(String str){
        if (isEmpty(str)){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < str.length(); i++){
            String character = str.substring(i, i + 1);
            if (number.matcher(str.substring(i, i+1)).matches()){
                sb.append(character);
            }
        }
        return sb.toString();
    }
}
