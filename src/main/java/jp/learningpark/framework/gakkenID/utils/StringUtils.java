package jp.learningpark.framework.gakkenID.utils;

import java.util.Collection;
import java.util.Map;
import java.util.Random;


/**
 * STRING共通メソッドクラス
 * 
 */
public class StringUtils extends org.apache.commons.lang3.StringUtils
{
    /** 空白STRING */
    public static final String NULLSTR = "";

    /** アンダーライン */
    private static final char SEPARATOR = '_';

    /**
     * NULL替わるメソッド
     * 
     * @param value 判定対象
     * @param defaultValue ディフォルトパラメータ
     * @return 戻り結果
     */
    public static <T> T nvl(T value, T defaultValue)
    {
        return value != null ? value : defaultValue;
    }

    /**
     * NULL又は空白判定メソッド
     *
     * @param obj 判定対象
     * @return 戻り結果
     */
	public static boolean isEmpty(Object obj) {
		return (isNull(obj) || "".equals(obj));
	}

    /**
     * CollectionはNULLかどうか判定メソッド(List，Set，Queue)
     * 
     * @param coll 判定対象
     * @return true：NULL false：NOT NULL
     */
    public static boolean isEmpty(Collection<?> coll)
    {
        return isNull(coll) || coll.isEmpty();
    }

    /**
     * CollectionはNOT NULLかどうか判定メソッド(List，Set，Queue)
     * 
     * @param coll 判定対象
     * @return true：NOT NULL false：NULL
     */
    public static boolean isNotEmpty(Collection<?> coll)
    {
        return !isEmpty(coll);
    }

    /**
     * オブジェクトArrayは空白かどうか判定メソッド
     * 
     * @param objects 判定対象
     ** @return true：空白 false：非空白
     */
    public static boolean isEmpty(Object[] objects)
    {
        return isNull(objects) || (objects.length == 0);
    }

    /**
     * オブジェクトArrayは非空白かどうか判定メソッド
     * 
     * @param objects 判定対象
     * @return true：非空白 false：空白
     */
    public static boolean isNotEmpty(Object[] objects)
    {
        return !isEmpty(objects);
    }

    /**
     * MAPは空白かどうか判定メソッド
     * 
     * @param map 判定対象
     * @return true：空白 false：非空白
     */
    public static boolean isEmpty(Map<?, ?> map)
    {
        return isNull(map) || map.isEmpty();
    }

    /**
     * MAPは非空白かどうか判定メソッド
     * 
     * @param map 判定対象
     * @return true：非空白 false：空白
     */
    public static boolean isNotEmpty(Map<?, ?> map)
    {
        return !isEmpty(map);
    }

    /**
     * 文字列は空白かどうか判定メソッド
     * 
     * @param str 判定対象
     * @return true：空白 false：非空白
     */
    public static boolean isEmpty(String str)
    {
        return isNull(str) || NULLSTR.equals(str.trim());
    }

    /**
     * 文字列は非空白かどうか判定メソッド
     * 
     * @param str 判定対象
     * @return true：非空白 false：空白
     */
    public static boolean isNotEmpty(String str)
    {
        return !isEmpty(str);
    }

    /**
     * オブジェクトは空白かどうか判定メソッド
     * 
     * @param object 判定対象
     * @return true：空白 false：非空白
     */
    public static boolean isNull(Object object)
    {
        return object == null;
    }

    /**
     * オブジェクトは非空白かどうか判定メソッド
     * 
     * @param object 判定対象
     * @return true：非空白 false：空白
     */
    public static boolean isNotNull(Object object)
    {
        return !isNull(object);
    }

    /**
     * オブジェクトはArrayかどうか判定メソッド
     * 
     * @param object 判定対象
     * @return true：文字列 false：非文字列
     */
    public static boolean isArray(Object object)
    {
        return isNotNull(object) && object.getClass().isArray();
    }

    /**
     * 空文字列除く
     */
    public static String trim(String str)
    {
        return (str == null ? "" : str.trim());
    }

    /**
     * 文字列切り取り（開始位置だけ）
     * 
     * @param str 文字列
     * @param start 切り取り開始位置
     * @return 切り取り結果
     */
    public static String substring(final String str, int start)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = str.length() + start;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (start > str.length())
        {
            return NULLSTR;
        }

        return str.substring(start);
    }

    /**
     * 文字列切り取り
     * 
     * @param str 文字列
     * @param start 切り取り開始位置
     * @param end 切り取り終了位置
     * @return 切り取り結果
     */
    public static String substring(final String str, int start, int end)
    {
        if (str == null)
        {
            return NULLSTR;
        }

        if (end < 0)
        {
            end = str.length() + end;
        }
        if (start < 0)
        {
            start = str.length() + start;
        }

        if (end > str.length())
        {
            end = str.length();
        }

        if (start > end)
        {
            return NULLSTR;
        }

        if (start < 0)
        {
            start = 0;
        }
        if (end < 0)
        {
            end = 0;
        }

        return str.substring(start, end);
    }

    /**
     * 文字フォーマット, {} プレースホルダ<br>
     * プレースホルダ {} 順々に<br>を置き換え
     *
     * 例えば：<br>
     * 通常使用：format("this is {} for {}", "a", "b") -> this is a for b<br>
     * 義をかえる{}： format("this is \\{} for {}", "a", "b") -> this is \{} for a<br>
     * 義をかえる\： format("this is \\\\{} for {}", "a", "b") -> this is \a for b<br>
     * 
     * @param template テンプレート，置き換えところは {} で表示する
     * @param params パラメータ
     * @return フォーマット後テンプレート
     */
//    public static String format(String template, Object... params)
//    {
//        if (isEmpty(params) || isEmpty(template))
//        {
//            return template;
//        }
//        return StrFormatter.format(template, params);
//    }

    /**
     * アンダーラインはキャメル式で置き換えるネーミングメソッド
     */
    public static String toUnderScoreCase(String str)
    {
        if (str == null)
        {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        // 前の文字は大文字ですか
        boolean preCharIsUpperCase = true;
        // 現在の文字は大文字ですか
        boolean curreCharIsUpperCase = true;
        // 次の文字は大文字ですか
        boolean nexteCharIsUpperCase = true;
        for (int i = 0; i < str.length(); i++)
        {
            char c = str.charAt(i);
            if (i > 0)
            {
                preCharIsUpperCase = Character.isUpperCase(str.charAt(i - 1));
            }
            else
            {
                preCharIsUpperCase = false;
            }

            curreCharIsUpperCase = Character.isUpperCase(c);

            if (i < (str.length() - 1))
            {
                nexteCharIsUpperCase = Character.isUpperCase(str.charAt(i + 1));
            }

            if (preCharIsUpperCase && curreCharIsUpperCase && !nexteCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            else if ((i != 0 && !preCharIsUpperCase) && curreCharIsUpperCase)
            {
                sb.append(SEPARATOR);
            }
            sb.append(Character.toLowerCase(c));
        }
        return sb.toString();
    }

    /**
     * 文字列を含めるかどうか
     * 
     * @param str 検証対象
     * @param strs 文字列組合
     * @return 検証対象　true:含む
     */
    public static boolean inStringIgnoreCase(String str, String... strs)
    {
        if (str != null && strs != null)
        {
            for (String s : strs)
            {
                if (str.equalsIgnoreCase(trim(s)))
                {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * HELLO_WORLD->HelloWorld
     * 
     * @param name 変換前対象
     * @return 変換後結果
     */
    public static String convertToCamelCase(String name)
    {
        StringBuilder result = new StringBuilder();
        // 空白チェック
        if (name == null || name.isEmpty())
        {
            // 変換不要
            return "";
        }
        else if (!name.contains("_"))
        {
            // アンダーラインなし
            return name.substring(0, 1).toUpperCase() + name.substring(1);
        }

        String[] camels = name.split("_");
        for (String camel : camels)
        {

            if (camel.isEmpty())
            {
                continue;
            }

            result.append(camel.substring(0, 1).toUpperCase());
            result.append(camel.substring(1).toLowerCase());
        }
        return result.toString();
    }
    
    /**
     * 文字列同じかどうかを判別する
     * @param target1
     * @param target2
     * @return boolean
     */
    public static boolean isEquals(Object target1, Object target2) {
    	if (target1 == null && target2 == null) {
    		return true;
    	}
    	if (target1 == null && target2 != null) {
    		return false;
    	}
    	if (target1 != null && target2 == null) {
    		return false;
    	}
    	
    	return target1.equals(target2);
    }

    /**
     * NULL文字列は空白に変換する
     *
     * @param str 変換対象
     * @return boolean
     */
    public static String defaultString(final String str) {
        return defaultString(str, "");
    }

    /**
     * NULL文字列はディフォルト値に変換する
     *
     * @param str 変換対象
     * @param defaultStr ディフォルト値
     * @return boolean
     */
    public static String defaultString(final String str, final String defaultStr) {
        return str == null ? defaultStr : str;
    }

    /**
     * ランダム文字列を作る
     *
     * @param length 作る文字列の長さ
     * @return 作る文字列
     */
	public static String getStringRandom(int length) {
		
		String val = "";
		Random random = new Random();

		for(int i = 0; i < length; i++) {
			
			String charOrNum = random.nextInt(2) % 2 == 0 ? "char" : "num";

			if( "char".equalsIgnoreCase(charOrNum) ) {

				int temp = random.nextInt(2) % 2 == 0 ? 65 : 97;
				val += (char)(random.nextInt(26) + temp);
			} else if( "num".equalsIgnoreCase(charOrNum) ) {
				val += String.valueOf(random.nextInt(10));
			}
		}
		return val;
	}

    /**
     * 文字列を１を添えた後、補足する
     * @param string
     * @param size
     * @return
     */
	public static String getAutoCreMentStr(String string, Integer size, String padStr) {

	    if (string == null) {
	        string = "0";
        }

        Integer maxIdInt = Integer.valueOf(string);
        return StringUtils.leftPad(String.valueOf(maxIdInt + 1), size, padStr);
    }
}