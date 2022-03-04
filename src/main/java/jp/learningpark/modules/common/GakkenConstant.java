package jp.learningpark.modules.common;

import jp.learningpark.framework.utils.Constant;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.regex.Pattern;

@Component
public class GakkenConstant extends Constant implements EnvironmentAware {

    private static Environment env;

    public static String urlPrefix;

    public static boolean localFlag;

    public static final String OK_FLAG = "1";

    public static final String NG_FLAG = "0";

    public static final String MAX_ERROR_COUNT = "MAX_ERROR_COUNT";

    public static final String FILE_SEPARATOR_PATTERN = Pattern.quote(System.getProperty("file.separator"));

    @Override
    public void setEnvironment(Environment environment) {
        env = environment;
        urlPrefix = env.getProperty("ueditor.urlPrefix");
        localFlag = Boolean.parseBoolean(env.getProperty("localFlag"));
    }

    public static String getProperty(String key){
        return env.getProperty(key);
    }

    public interface BaseEnum {
        public int getValue();

        public String getLable();
    }

    /**
     * サーバ画像のプレフィックス
     */
    public static final String SERVER_IMG_PREFIX = "/server-image/";

    /**
     * サーバファイルのプレフィックス
     */
    public static final String SERVER_FILE_PREFIX = "/server-file/";

    /**
     * サーバファイルのプレフィックス
     */
    public static final String SERVER_FILE_PATH_HEAD = "/server-file/ueditor";

    /**
     * ueditor
     */
    public static final String UEDITOR = "ueditor";

    /**
     * SEND_GROUP_SIZE
     */
    public static final Integer SEND_GROUP_SIZE = 500;

    /**
     *　ファイル.プレフィックス
     */
    public static final String FILE_PATH_PREFIX = "../server-file" + File.separator;

    //    /**
    //     * 掲示板.ファイル
    //     */
    //    public static final String NEWS_FILE_PATH = "../server-file/news/";
    //
    //    /**
    //     * お知らせ.ファイル
    //     */
    //    public static final String NOTICE_FILE_PATH = "../server-file/notice/";

    /**
     * CHAR 1
     */
    public static final String FLG_TRUE = "1";

    /**
     * CHAR 0
     */
    public static final String FLG_FALSE = "0";

    /**
     * 生徒ID
     */
    public static final String STU_ID = "stuId";

    /**
     * 生徒姓名
     */
    public static final String STU_NM = "stu_nm";
    /**
     * 組織名
     */
    public static final String ORG_NM = "org_nm";
    /**
     * 組織ID
     */
    public static final String ORG_ID = "org_id";

    /**
     * メンター姓名
     */
    public static final String MENTOR_NM = "mentor_nm";
    /**
     * ログインID
     */
    public static final String LOGIN_ID = "LOGIN_ID";

    /**
     * 塾学習期間ID
     */
    public static final String CRM_LEARN_PRD_ID = "CRM_LEARN_PRD_ID";

    /**
     * セッション.ブランドコード
     */
    public static final String SESSION_BRAND_CD = "BRAND_CD";
    /**
     * セッション.マナURL
     */
    public static final String MANA_FLAG = "MANA_FLAG";
    /**
     * セッション.CSRF トークン
     */
    public static final String SESSION_CSRF_TOKEN = "CSRF_TOKEN";
    /**
     * セッション.変更後ユーザＩＤ
     */
    public static final String SESSION_AFTER_USR_ID = "AFTER_USR_ID";
    /**
     * セッション.学研IDプライマリキーと
     */
    public static final String SESSION_GIDPK = "GIDPK";
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　Start
    /**
     * セッション.変更後ユーザＩＤ
     */
    public static final String SESSION_AFTER_USR_ID_FOR_RESET_PASSWORD = "AFTER_USR_ID_FOR_RESET_PASSWORD";
    /**
     * セッション.学研IDプライマリキーと
     */
    public static final String SESSION_GIDPK_FOR_RESET_PASSWORD = "GIDPK_FOR_RESET_PASSWORD";
    /**
     * セッション.GIDフラグ
     */
    public static final String SESSION_GID_FLG_FOR_RESET_PASSWORD = "GID_FLG_FOR_RESET_PASSWORD";
    //2021/11/08　MANAMIRU1-831 huangxinliang　Edit　End
    public static final String TCH_CD = "TCH_CD";
    /**
     * セッション.GIDフラグ
     */
    public static final String SESSION_GID_FLG = "GID_FLG";

    /**
     * セッション.アクセストークン
     */
    public static final String SESSION_ACCESS_TOKEN = "ACCESS_TOKEN";

    /**
     * セッション.端末類型
     */
    public static final String SESSION_EQUIPMENT_DIV = "EQUIPMENT_DIV";
    /**
     * セッション.ログイン経由(アプリ)
     */
    public static final String SESSION_LOGIN_APP = "SESSION_LOGIN_APP";
    /**
     * セッション.ユーザーID
     */
    public static final String SESSION_PASSWORD = "PASSWORD";
    /**
     * セッション.デバイスTOKEN
     */
    public static final String SESSION_DEVICETOKEN = "DEVICE_TOKEN";
    /**
     * セッション.メールアドレス
     */
    public static final String SESSION_MAIL_ADDRESS = "MAIL_ADDRESS";
    /**
     * セッション.ログインURL
     */
    public static final String SESSION_LOGIN_URL = "LOGIN_URL";
    /**
     * 計画登録フラグ 0：未登録
     */
    public static final String PLAN_REG_FLG_NO = "0";

    /**
     * 計画登録フラグ 1：登録済
     */
    public static final String PLAN_REG_FLG_YES = "1";
    
    /**
     * 計画登録フラグ 2：削除
     */
    public static final String PLAN_REG_FLG_DEL = "2";

    /**
     * ブロック種類区分：通常
     */
    public static final String BLOCK_TYPE_DIV_C1 = "C1";
    /**
     * ブロック種類区分：その他「趣味」
     */
    public static final String BLOCK_TYPE_DIV_O1 = "O1";
    /**
     * ブロック種類区分：その他「習い事」
     */
    public static final String BLOCK_TYPE_DIV_O2 = "O2";
    /**
     * ブロック種類区分：その他「その他」
     */
    public static final String BLOCK_TYPE_DIV_O3 = "O3";
    /**
     * ブロック種類区分：復習
     */
    public static final String BLOCK_TYPE_DIV_R1 = "R1";
    /**
     * ブロック種類区分：学習
     */
    public static final String BLOCK_TYPE_DIV_S1 = "S1";
    /**
     * ブロック種類区分：予習
     */
    public static final String BLOCK_TYPE_DIV_P1 = "P1";
    /**
     * ブロック種類区分：学校の宿題
     */
    public static final String BLOCK_TYPE_DIV_W1 = "W1";

    /**
     * ブロック種類区分：塾の宿題
     *
     * TYJ 追加
     */
    public static final String BLOCK_TYPE_DIV_V1 = "V1";
    /**
     * NOTICE_REMOTE_URL_appLoginCheck
     */
    public static final String NOTICE_REMOTE_URL_appLoginCheck = "http://localhost:8080/v1/3/device";
    /**
     * NOTICE_REMOTE_URL_unReadGet
     */
    public static final String NOTICE_REMOTE_URL_unReadGet = "http://localhost:8080/v1/3/message/unread";
    /**
     * NOTICE_REMOTE_URL_sendMessage
     */
    public static final String NOTICE_REMOTE_URL_sendMessage = "http://localhost:8080/v1/3/message";
    /**
     * NOTICE_REMOTE_URL_errorData
     */
    public static final String NOTICE_REMOTE_URL_errorData = "http://localhost:8080/v1/3/message/errorData";

    /**
     * SNK_MEMBER_PREFIX
     */
    public static final String SNK_MEMBER_PREFIX = "SNK_mana_member_";

    /**
     * DF_MEMBER_PREFIX
     */
    public static final String DF_MEMBER_PREFIX = "DF_mana_member_";

    /**
     * MANA_MEMBER_PREFIX
     */
    public static final String MANA_MEMBER_PREFIX = "mana_member_";

    /**
     * MANA_TEACHER_PREFIX
     */
    public static final String MANA_TEACHER_PREFIX = "mana_teacher_";

    /**
     * MANA_TID_ORG_PREFIX
     */
    public static final String MANA_TID_ORG_PREFIX = "mana_tid_org_";

    /**
     * SNK_ORG_PREFIX
     */
    public static final String MANA_ORG_PREFIX = "mana_org_";
    /**
     * ERR_PREFIX
     */
    public static final String ERR_PREFIX = "ERR_";

    /**
     * BTGKA1010
     */
    public static final String BTGKA1010_PREFIX = "本棚退会連携DAT_";

    /**
     * CSV_FILE_SUFFIX
     */
    public static final String CSV_FILE_SUFFIX = ".csv";

    /**
     * CSV_FILE_SUFFIX
     */
    public static final String JSON_FILE_SUFFIX = ".json";

    public static final String GKGC = "gkgc";

    /**
     * 本棚API.サービスコード
     */
    public static final String GROUP_ID = "ayIPNF3KHB1OUKqv";

    /**
     * 本棚API.グループID
     */
    public static final String SERVICE_CD = "manamiru";

    /**
     * NOTICE_PAGE_SIZE
     */
    public static final Integer NOTICE_PAGE_SIZE = 15;

    /**
     *SYS_BATCH
     */
    public  static  final  String SYS_BATCH ="SYSBatch";

    /**
     *  BTGKA1009_CSV_FILE_NAME
     */
    public  static final  String BTGKA1009_CSV_FILE_NAME ="本棚入会連携DAT_";


    // add at 2021/12/01 for V9.02 by NWT HuangXL START
    public static final String DEVICE_TOKEN_NULL_MSG = "デバイストーケンなしなので、ログイン不可です。";
    // add at 2021/12/01 for V9.02 by NWT HuangXL END

    /**
     * 削除フラグ
     */
    public enum DEL_FLG implements BaseEnum {
        NO("有効", 0),
        YES("無効", 1);

        private final int value;
        private final String lable;

        DEL_FLG(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }

    /**
     * LOGIN_TYPE_KBN
     */
    public enum LOGIN_TYPE_KBN{
        /**
         * マナミルログインURLログイン
         */
        MANAMIRU_LOGIN("マナミルログインURLログイン", "1"),
        /**
         * 通知アプリ経由ログイン
         */
        PUSH_PASS_LOGIN("通知アプリ経由ログイン", "2"),
        /**
         * 通知アプリSSOログイン
         */
        PUSH_SSO_LOGIN("通知アプリSSOログイン", "3"),
        /**
         * 解答集ログイン
         */
        QR_RESPONSE_LOGIN("解答集ログイン", "4"),
        /**
         * QRスキャンログイン
         */
        QR_CODE_LOGIN("QRスキャンログイン", "5");

        private final String value;
        private final String lable;

        LOGIN_TYPE_KBN(final String lable, final String value) {
            this.value = value;
            this.lable = lable;
        }

        public String getValue() {
            return this.value;
        }

        public String getLable() {
            return this.lable;
        }
    }

    /**
     * GIDフラグ
     */
    public enum GID_FLG{
        /**
         * GIG
         */
        YES("GID", "1"),
        /**
         * 非GID
         */
        NO("NOT_GID", "0");

        private final String value;
        private final String label;

        GID_FLG(final String label, final String value) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 本棚連携フラグ
     */
    public enum BOOK_SEND_FLG{
        /**
         * 連携済
         */
        YES("SEND", "1"),
        /**
         * 未連携
         */
        NO("NOT_SEND", "0");

        private final String value;
        private final String label;

        BOOK_SEND_FLG(final String label, final String value) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 本棚有効化フラグ
     */
    public enum BOOK_EFFEC_FLG{
        /**
         * 有効
         */
        YES("EFFEC", "1"),
        /**
         * 無効
         */
        NO("NOT_EFFEC", "0");

        private final String value;
        private final String label;

        BOOK_EFFEC_FLG(final String label, final String value) {
            this.value = value;
            this.label = label;
        }

        public String getValue() {
            return this.value;
        }

        public String getLabel() {
            return this.label;
        }
    }

    /**
     * 学習理解度
     */
    public enum LEARN_LEV_UNDS implements BaseEnum {
        ZERO("実施していない（0分）", 0),
        ONE("90%以上理解できた", 1),
        TWO("75%理解できた", 2),
        THREE("60%理解できた", 3),
        FOUR("59%以下の理解度だった", 4);

        private final int value;
        private final String lable;

        LEARN_LEV_UNDS(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }

    public static final String MANAGER_ROLE_DIV = "1";

    public static final String MENTOR_ROLE_DIV = "2";

    public static final String GUARD_ROLE_DIV = "3";

    public static final String STUDENT_ROLE_DIV = "4";
    /**
     * ロール区分
     */
    public enum ROLE_DIV implements BaseEnum {
        MANAGER("管理者", 1),
        MENTOR("先生", 2),
        GUARD("保護者", 3),
        STUDENT("生徒", 4);

        private final int value;
        private final String lable;

        ROLE_DIV(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }

    /**
     * ブロック種類区分
     */
    public enum BLOCK_TYPE_DIV implements BaseEnum {
        C1("通常", 1),
        O1("その他「趣味」", 2),
        O2("その他「習い事」", 3),
        O3("その他「その他」", 4),
        R1("復習", 5),
        S1("学習", 6),
        P1("予習", 7),
        W1("学校の宿題", 8);

        private final int value;
        private final String lable;

        BLOCK_TYPE_DIV(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }

    public enum DATE_TYPE implements BaseEnum {
        WEEK("week", 1),
        MONTH("month", 2),
        YEAR("year", 3);

        private final int value;
        private final String lable;

        DATE_TYPE(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }

    /**
     * データ状態フラグ
     */
    public enum STATUS_FLG implements BaseEnum {
        NEW("新規", 0),
        SUCCESS("正常", 1),
        ERROR("エラー", 2);

        private final int value;
        private final String lable;

        STATUS_FLG(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }
    
    /**
     * タスクタイプ
     */
    public enum TASK_TYPE implements BaseEnum {
        STATEMENT("Statement", 0),
        ACTIVITY("Activity", 1),
        AGENT("Agent", 2);

        private final int value;
        private final String lable;

        TASK_TYPE(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }

    public enum API_FAMILY_TYPE {

        /**
         * 「代表者以外」
         */
        NOT_REPRESENT((short)0),

        /**
         * 「代表者」
         */
        REPRESENT((short)1);

        private Short value;

        API_FAMILY_TYPE(Short value) {
            this.value = value;
        }

        public Short getValue() {
            return value;
        }
    }

    public enum EQUIPMENT_DIV implements BaseEnum {
        PC("PC", 1),
        TABLET("タブレット", 2),
        PHONE("スマホ", 3);

        private final int value;
        private final String lable;

        EQUIPMENT_DIV(final String lable, final int value) {
            this.value = value;
            this.lable = lable;
        }

        @Override
        public int getValue() {
            return this.value;
        }

        @Override
        public String getLable() {
            return this.lable;
        }
    }
}
