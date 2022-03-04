/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.sys.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * システムユーザーオンライン
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("sys_user_online")
public class SysUserOnlineEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * セッションID
     */
    @NotNull
    private String sessionId;

    /**
     * ユーザーID
     */
    @NotNull
    private String userId;
    
    /**
     * ユーザーCD
     */
    private String userCd;

    /**
     * ログインIPアドレス
     */
    private String ipAddr;

    /**
     * ログイン場所
     */
    private String loginLocation;

    /**
     * ブラウザタイプ
     */
    private String browserType;

    /**
     * システムデバイス
     */
    private String sysOs;

    /**
     * オンラインステータス
     */
    @NotNull
    private String onlineStatus;

    /**
     * セッション開始日時
     */
    private Timestamp sesStartDt;

    /**
     * セッション最後アクセス日時
     */
    private Timestamp sesLastAccessDt;

    /**
     * 期限切れ期間
     */
    @NotNull
    private long expireTime;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 更新日時
     */
    @NotNull
    private Timestamp updateTime;
    
    /**
     * ログインタイプ
     */
    @NotNull
    private String loginType;

    /**
     * セッションIDを設定する
     */
    public void setSessionId(String sessionId) {
        this.sessionId = sessionId;
    }
    
    /**
     * セッションIDを取得する
     */
    public String getSessionId() {
        return sessionId;
    }
    /**
     * ユーザーIDを設定する
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }
    
    /**
     * ユーザーIDを取得する
     */
    public String getUserId() {
        return userId;
    }
    /**
     * ユーザーCDを設定する
     */
    public void setUserCd(String userCd) {
        this.userCd = userCd;
    }
    
    /**
     * ユーザーCDを取得する
     */
    public String getUserCd() {
        return userCd;
    }
    /**
     * ログインIPアドレスを設定する
     */
    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
    
    /**
     * ログインIPアドレスを取得する
     */
    public String getIpAddr() {
        return ipAddr;
    }
    /**
     * ログイン場所を設定する
     */
    public void setLoginLocation(String loginLocation) {
        this.loginLocation = loginLocation;
    }
    
    /**
     * ログイン場所を取得する
     */
    public String getLoginLocation() {
        return loginLocation;
    }
    /**
     * ブラウザタイプを設定する
     */
    public void setBrowserType(String browserType) {
        this.browserType = browserType;
    }
    
    /**
     * ブラウザタイプを取得する
     */
    public String getBrowserType() {
        return browserType;
    }
    /**
     * システムデバイスを設定する
     */
    public void setSysOs(String sysOs) {
        this.sysOs = sysOs;
    }
    
    /**
     * システムデバイスを取得する
     */
    public String getSysOs() {
        return sysOs;
    }
    /**
     * オンラインステータスを設定する
     */
    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }
    
    /**
     * オンラインステータスを取得する
     */
    public String getOnlineStatus() {
        return onlineStatus;
    }
    /**
     * セッション開始日時を設定する
     */
    public void setSesStartDt(Timestamp sesStartDt) {
        this.sesStartDt = sesStartDt;
    }
    
    /**
     * セッション開始日時を取得する
     */
    public Timestamp getSesStartDt() {
        return sesStartDt;
    }
    /**
     * セッション最後アクセス日時を設定する
     */
    public void setSesLastAccessDt(Timestamp sesLastAccessDt) {
        this.sesLastAccessDt = sesLastAccessDt;
    }
    
    /**
     * セッション最後アクセス日時を取得する
     */
    public Timestamp getSesLastAccessDt() {
        return sesLastAccessDt;
    }
    /**
     * 期限切れ期間を設定する
     */
    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }
    
    /**
     * 期限切れ期間を取得する
     */
    public long getExpireTime() {
        return expireTime;
    }
    /**
     * 作成日時を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }
    
    /**
     * 作成日時を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }
    /**
     * 更新日時を設定する
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    /**
     * ログインタイプを設定する
     */
    public String getLoginType() {
        return loginType;
    }
    /**
     * ログインタイプを設定する
     */
    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }
}
