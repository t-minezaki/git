/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * ログイン管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_login")
public class MstLoginEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * ログインタイプ区分
     */
    @NotNull
    private String loginTypeKbn;

    /**
     * ログインID
     */
    @NotNull
    private String loginId;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * ログイン日時
     */
    @NotNull
    private Timestamp loginTime;

    /**
     * ログイン結果フラグ
     */
    @NotNull
    private String loginResultFlg;

    /**
     * ログインエラー区分
     */
    private String loginErrKbn;

    /**
     * 備考
     */
    private String commentTxt;

    /**
     * 作成日時
     */
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    private Timestamp updDatime;

    /**
     * 更新ユーザＩＤ
     */
    private String updUsrId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;


    /**
     * IDを設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * IDを取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * ログインタイプ区分を設定する
     */
    public void setLoginTypeKbn(String loginTypeKbn) {
        this.loginTypeKbn = loginTypeKbn;
    }
    
    /**
     * ログインタイプ区分を取得する
     */
    public String getLoginTypeKbn() {
        return loginTypeKbn;
    }
    /**
     * ログインIDを設定する
     */
    public void setLoginId(String loginId) {
        this.loginId = loginId;
    }
    
    /**
     * ログインIDを取得する
     */
    public String getLoginId() {
        return loginId;
    }
    /**
     * ログイン日時を設定する
     */
    public void setLoginTime(Timestamp loginTime) {
        this.loginTime = loginTime;
    }
    
    /**
     * ログイン日時を取得する
     */
    public Timestamp getLoginTime() {
        return loginTime;
    }
    /**
     * ログイン結果フラグを設定する
     */
    public void setLoginResultFlg(String loginResultFlg) {
        this.loginResultFlg = loginResultFlg;
    }
    
    /**
     * ログイン結果フラグを取得する
     */
    public String getLoginResultFlg() {
        return loginResultFlg;
    }
    /**
     * ログインエラー区分を設定する
     */
    public void setLoginErrKbn(String loginErrKbn) {
        this.loginErrKbn = loginErrKbn;
    }
    
    /**
     * ログインエラー区分を取得する
     */
    public String getLoginErrKbn() {
        return loginErrKbn;
    }
    /**
     * 備考を設定する
     */
    public void setCommentTxt(String commentTxt) {
        this.commentTxt = commentTxt;
    }
    
    /**
     * 備考を取得する
     */
    public String getCommentTxt() {
        return commentTxt;
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
     * 作成ユーザＩＤを設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }
    
    /**
     * 作成ユーザＩＤを取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }
    /**
     * 更新日時を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * 更新ユーザＩＤを設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }
    
    /**
     * 更新ユーザＩＤを取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }
    /**
     * 削除フラグを設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
    
    /**
     * 削除フラグを取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }

    /**
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
}
