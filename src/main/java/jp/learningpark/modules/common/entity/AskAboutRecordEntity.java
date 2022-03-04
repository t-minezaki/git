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
 * 問い合せ記録
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("ask_about_record")
public class AskAboutRecordEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 登録ユーザID
     */
    @NotNull
    private String askUserId;

    /**
     * 問い合せ日時
     */
    @NotNull
    private Timestamp askDatime;

    /**
     * 問い合せタイトル
     */
    @NotNull
    private String askTitle;

    /**
     * 問い合せ内容
     */
    private String askCont;

    /**
     * 約束事項
     */
    private String contlMatCont;

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
     * 組織IDを設定する
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * 組織IDを取得する
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * 登録ユーザIDを設定する
     */
    public void setAskUserId(String askUserId) {
        this.askUserId = askUserId;
    }
    
    /**
     * 登録ユーザIDを取得する
     */
    public String getAskUserId() {
        return askUserId;
    }
    /**
     * 問い合せ日時を設定する
     */
    public void setAskDatime(Timestamp askDatime) {
        this.askDatime = askDatime;
    }
    
    /**
     * 問い合せ日時を取得する
     */
    public Timestamp getAskDatime() {
        return askDatime;
    }
    /**
     * 問い合せタイトルを設定する
     */
    public void setAskTitle(String askTitle) {
        this.askTitle = askTitle;
    }
    
    /**
     * 問い合せタイトルを取得する
     */
    public String getAskTitle() {
        return askTitle;
    }
    /**
     * 問い合せ内容を設定する
     */
    public void setAskCont(String askCont) {
        this.askCont = askCont;
    }
    
    /**
     * 問い合せ内容を取得する
     */
    public String getAskCont() {
        return askCont;
    }
    /**
     * 約束事項を設定する
     */
    public void setContlMatCont(String contlMatCont) {
        this.contlMatCont = contlMatCont;
    }
    
    /**
     * 約束事項を取得する
     */
    public String getContlMatCont() {
        return contlMatCont;
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
