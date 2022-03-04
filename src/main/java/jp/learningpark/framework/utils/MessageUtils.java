/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項   :
 */
package jp.learningpark.framework.utils;

import org.springframework.context.MessageSource;

import java.util.Locale;

/**
 * <p>メッセージの取得に対する共通関数を定義するクラスです。</p>
 *
 * @author NWT : GAOLI
 * 変更履歴 <br/>
 * 日付 : 2018/10/11  GAOLI: 新規
 * @version 1.0
 */
public class MessageUtils {

    /**
     * <p>デフォルトコンストラクタ</p>
     */
    private MessageUtils() {
        super();
    }

    /**
     * <p>プロパティファイルからのメッセージ文言を取得する。</p>
     *
     * @param key  メッセージのキー
     * @param args メッセージの引数
     * @return メッセージ文言
     */
    public static String getMessage(final String key, final String... args) {
        return getMessage(null, key, args);
    }

    /**
     * <p>プロパティファイルからのメッセージ文言を取得する。</p>
     *
     * @param locale ロケール
     * @param key    メッセージのキー
     * @param args   メッセージの引数
     * @return メッセージ文言
     */
    public static String getMessage(final Locale locale, final String key, final String... args) {
        MessageSource messageSource = SpringContextUtils.getBean("messageSource", MessageSource.class);
        return messageSource.getMessage(key, args, locale);
    }
}

