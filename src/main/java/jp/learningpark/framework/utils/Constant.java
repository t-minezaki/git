package jp.learningpark.framework.utils;

/**
 * <p>定数ファイル。</p >
 *
 * @author NWT : NWT <br />
 * 変更履歴 <br />
 * 2018/10/09 : NWT: 新規<br />
 * @version 1.0
 */
public class Constant {

    /**
     * 日付フォマード・ISO_yyyy-MM-dd hh:mm
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMM_ISO = "yyyy-MM-dd HH:mm:ss";

    /**
     * 日付フォマード・yyyyMMddhhmmss
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
    /**
     * 日付フォマード・yyyyMMddhhmm
     */
    public static final String DATE_FORMAT_YYYYMMDDHHMM = "yyyyMMddHHmm";
    /**
     * 日付フォマード・yyyyMMdd
     */
    public static final String DATE_FORMAT_YYYYMMDD = "yyyyMMdd";
    /**
     * 日付フォマード・yyyy年MM月dd日
     */
    public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy年MM月dd日";
    /**
     * 日付フォマード・yyyy年MM月dd日 HH時mm分
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_MAIL = "yyyy年MM月dd日 HH:mm";
    /**
     * 日付フォマード・ HH時mm分
     */
    public static final String DATE_FORMAT_HH_MM_MAIL = "HH時mm分";
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
     * 日付フォマード・yyyy-MM
     */
    public static final String DATE_FORMAT_YYYY_MM_BARS = "yyyy-MM";
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
     * 日付フォマード・M月d日H時m分
     */
    public static final String DATE_FORMAT_M_D_H_M = "M月d日H時m分";
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
     * 日付フォマード・M月d日 (E) HH:mm
     */
    public static final String DATE_FORMAT_M_D_E_HH_MM = "M月d日 (E) HH:mm";
    /**
     * 日付フォマード・yyyy年M月
     */
    public static final String DATE_FORMAT_YYYY_M = "yyyy年M月";
    /**
     * 日付フォマード・YYYY/MM/DD HH:mm
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM = "yyyy/MM/dd HH:mm";
    /**
     * 日付フォマード・MM/DD HH:mm
     */
    public static final String DATE_FORMAT_MM_DD_E_HH_MM = "MM/dd(E) HH:mm";
    /**
     * 日付フォマード・YYYY-MM-DD HH:mm
     */
    public static final String DATE_FORMAT_YYYY_MM_DD_HH_MM_BARS = "yyyy-MM-dd HH:mm";
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


    /**
     * バッチ状態：正常
     */
    public static final int SCHEDULE_STATUS_NORMAL = 0;

    /**
     * バッチ状態：休止
     */
    public static final int SCHEDULE_STATUS_PAUSE = 1;

    /**
     * メニュータイプ CATALOG
     */
    public static final int MENU_TYPE_CATALOG = 0;

    /**
     * メニュータイプ フォルダ
     */
    public static final int MENU_TYPE_FOLDER = 0;

    /**
     * メニュータイプ メニュー
     */
    public static final int MENU_TYPE_MENU = 1;

    /**
     * メニュータイプ ボタン
     */
    public static final int MENU_TYPE_BUTTON = 2;

    /**
     * セッション オンライン
     */
    public static final String SESSION_ON_LINE = "1";

    /**
     * セッション オフライン
     */
    public static final String SESSION_OFF_LINE = "0";

    /**
     * セッション オンライン キー
     */
    public static final String SESSION_ONLINE_KEY = "online_session";


    /**
     * ログインタイプ 本システム
     */
    public static final String LOGIN_TYPE_GAKKEN = "0";

    /**
     * ログインタイプ LEシステム
     */
    public static final String LOGIN_TYPE_LE = "1";
    
    /**
     * ログインタイプ SAML2
     */
    public static final String LOGIN_TYPE_SAML2 = "2";

    /**
     * マナミルQR認識API.QR_LOGIN
     */
    public static final String QR_LOGIN = "qrlogin";

    /**
     * マナミルQR認識API.TOKEN_REFLASH
     */
    public static final String TOKEN_REFRESH = "tokenrefresh";

    /**
     * マナミルQR認識API.STUDENT_LOGIN
     */
    public static final String STUDENT_LOGIN = "studentlogin";
    /**
     * マナミルQR認識API.GO_SCHOOL
     */
    public static final String GO_SCHOOL = "登校";
    /**
     * マナミルQR認識API.LEAVE_SCHOOL
     */
    public static final String LEAVE_SCHOOL = "下校";
    /**
     * マナミルQR認識API.GO_SCHOOL_FOR_GKGC
     */
    public static final String GO_SCHOOL_FOR_GKGC = "入室";
    /**
     * マナミルQR認識API.LEAVE_SCHOOL_FOR_GKGC
     */
    public static final String LEAVE_SCHOOL_FOR_GKGC = "退室";
    /**
     * QR解答集アプリとの連携API.QR_PDF_GET_TOKEN
     */
    public static final String QR_PDF_GET_TOKEN = "qrpdfgettoken";
    /**
     * QR解答集アプリとの連携API.QR_DL_LOGIN
     */
    public static final String QR_DL_LOGIN = "qrdllogin";
    /**
     * QR解答集アプリとの連携API.CHECK_VER
     */
    public static final String CHECK_VER = "checkver";
    /**
     * 5分钟
     */
    public static final Long FIVE_MIN = (long) (5 * 60);

    /**
     * ブランド取得API
     */
    public static final String getBrandCdApi = "resetpwd";
    /**
     * 通知アプリのログインAPI
     */
    public static final String appLoginCheck = "pushmsglogin";
    /**
     * 未読件数取得Api
     */
    public static final String unReadGetApi = "unReadGetApi";
    /**
     * 既読状態更新Api
     */
    public static final String readStsUpdateApi = "readStsUpdateApi";
    /**
     * メッセージ送信
     */
    public static final String sendMessage = "sendMessage";
    /**
     * 送信失敗データの取得(errorDataGet)
     */
    public static final String getErrorData = "getErrorData";
    /**
     * 返却した送信失敗データをプッシュ失敗データに登録
     */
    public static final String insert = "insert";
    /**
     * ログアウト（logout）
     */
    public static final String logout = "msglogout";
    /**
     * プッシュmsgType(ニュース管理のお知らせ)
     */
    public static final String sendMsgTypeNotice = "1";
    /**
     * プッシュmsgType(イベント)
     */
    public static final String sendMsgTypeEvent = "3";
    /**
     * プッシュmsgType(保護者イベント申込状況)
     */
    public static final String sendMsgTypeGuardAplSts = "4";
    /**
     * プッシュmsgType(mailSend)
     */
    public static final String sendMsgTypeMailSend = "5";
    /**
     * プッシュmsgType(保護者イベント申込状況)
     */
    public static final String sendMsgTypeGuardEventApplySts = "6";
    /**
     * プッシュmsgType(保護者イベント送信)
     */
    public static final String sendMsgTypeEventMailSend = "7";
    /**
     * プッシュmsgType(QRCodeApi)
     */
    public static final String sendMsgTypeQRCodeApi= "8";
    /**
     * プッシュmsgType(入退室履歴)
     */
    public static final String sendMsgTypeEntryExitHst = "9";
    /**
     * プッシュmsgType(指導報告書)
     */
    public static final String sendMsgTypeGuidRepr = "10";
    /**
     * プッシュmsgType(マナミルチャンネル)
     */
    public static final String sendMsgTypeLateAbs = "11";
    /**
     * プッシュmsgType(褒めポイント)
     */
    public static final String sendMsgTypestuCompliment = "13";

    /**
     * プッシュmsgType(メッセージ)
     */
    public static final String sendMsgTypeMessage = "14";
    /**
     * プッシュmsgType(unread)
     */
    public static final String sendMsgTypeUnread = "999";
    /**
     * プッシュタイトル(unread)
     */
    public static final String sendTitleUnread = "unread";
    /**
     * プッシュMSG
     */
    public static final String GO_CLASSROOM = "入室しました";
    /**
     * プッシュMSG
     */
    public static final String LEAVE_CLASSROOM = "退室しました";
    /**
     * プッシュTITLE
     */
    public static final String PUSH_TITLE_GO_SCHOOL = "入室のお知らせ";
    /**
     * プッシュTITLE
     */
    public static final String PUSH_TITLE_LEAVE_SCHOOL = "退室のお知らせ";
    /**
     * tchCd非初回登録標識取得
     * NWT　楊　2021/07/15　MANAMIRU1-727　Edit
     */
    public static final String ALL_TCHCD_USERS_FIRST_LOGIN_FLG = "ALL_TCHCD_USERS_FIRST_LOGIN_FLG";
}
