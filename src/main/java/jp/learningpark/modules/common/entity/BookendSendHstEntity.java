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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("bookend_send_hst")
public class BookendSendHstEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 学研IDプライマリキー
     */
    @NotNull
    private String gidpk;

    /**
     * ロール区分
     */
    @NotNull
    private String roleDiv;

    /**
     * グループコード
     */
    private String bookGroupCd;

    /**
     * 登録退会フラグ
     */
    @NotNull
    private String bookApiFlg;

    /**
     * 連携日
     */
    @NotNull
    private Timestamp sendDatime;

    /**
     * 作成日時
     */
    @NotNull
    private Timestamp cretDatime;

    /**
     * 作成ユーザＩＤ
     */
    private String cretUsrId;

    /**
     * 更新日時
     */
    @NotNull
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
     * 学研IDプライマリキーを設定する
     */
    public void setGidpk(String gidpk) {
        this.gidpk = gidpk;
    }
    
    /**
     * 学研IDプライマリキーを取得する
     */
    public String getGidpk() {
        return gidpk;
    }
    /**
     * ロール区分を設定する
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }
    
    /**
     * ロール区分を取得する
     */
    public String getRoleDiv() {
        return roleDiv;
    }
    /**
     * グループコードを設定する
     */
    public void setBookGroupCd(String bookGroupCd) {
        this.bookGroupCd = bookGroupCd;
    }
    
    /**
     * グループコードを取得する
     */
    public String getBookGroupCd() {
        return bookGroupCd;
    }
    /**
     * 登録退会フラグを設定する
     */
    public void setBookApiFlg(String bookApiFlg) {
        this.bookApiFlg = bookApiFlg;
    }
    
    /**
     * 登録退会フラグを取得する
     */
    public String getBookApiFlg() {
        return bookApiFlg;
    }
    /**
     * 連携日を設定する
     */
    public void setSendDatime(Timestamp sendDatime) {
        this.sendDatime = sendDatime;
    }
    
    /**
     * 連携日を取得する
     */
    public Timestamp getSendDatime() {
        return sendDatime;
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
}
