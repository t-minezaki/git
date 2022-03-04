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
 * 保護者基本マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_guard")
public class MstGuardEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 保護者ID
     */
    @NotNull
    private String guardId;

    /**
     * メールアドレス
     */
    private String mailad;

    /**
     * 姓名_姓
     */
    @NotNull
    private String flnmNm;

    /**
     * 姓名_名
     */
    @NotNull
    private String flnmLnm;

    /**
     * 姓名_カナ姓
     */
    @NotNull
    private String flnmKnNm;

    /**
     * 姓名_カナ名
     */
    @NotNull
    private String flnmKnLnm;

    /**
     * 郵便番号_主番
     */
    private String postcdMnum;

    /**
     * 郵便番号_枝番
     */
    private String postcdBnum;

    /**
     * 住所１
     */
    private String adr1;

    /**
     * 住所２
     */
    private String adr2;

    /**
     * 電話番号
     */
    private String telnum;

    /**
     * 続柄区分
     */
    private String reltnspDiv;

    /**
     * 緊急電話番号
     */
    private String urgTelnum;

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
     * 保護者IDを設定する
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }
    
    /**
     * 保護者IDを取得する
     */
    public String getGuardId() {
        return guardId;
    }
    /**
     * メールアドレスを設定する
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }
    
    /**
     * メールアドレスを取得する
     */
    public String getMailad() {
        return mailad;
    }
    /**
     * 姓名_姓を設定する
     */
    public void setFlnmNm(String flnmNm) {
        this.flnmNm = flnmNm;
    }
    
    /**
     * 姓名_姓を取得する
     */
    public String getFlnmNm() {
        return flnmNm;
    }
    /**
     * 姓名_名を設定する
     */
    public void setFlnmLnm(String flnmLnm) {
        this.flnmLnm = flnmLnm;
    }
    
    /**
     * 姓名_名を取得する
     */
    public String getFlnmLnm() {
        return flnmLnm;
    }
    /**
     * 姓名_カナ姓を設定する
     */
    public void setFlnmKnNm(String flnmKnNm) {
        this.flnmKnNm = flnmKnNm;
    }
    
    /**
     * 姓名_カナ姓を取得する
     */
    public String getFlnmKnNm() {
        return flnmKnNm;
    }
    /**
     * 姓名_カナ名を設定する
     */
    public void setFlnmKnLnm(String flnmKnLnm) {
        this.flnmKnLnm = flnmKnLnm;
    }
    
    /**
     * 姓名_カナ名を取得する
     */
    public String getFlnmKnLnm() {
        return flnmKnLnm;
    }
    /**
     * 郵便番号_主番を設定する
     */
    public void setPostcdMnum(String postcdMnum) {
        this.postcdMnum = postcdMnum;
    }
    
    /**
     * 郵便番号_主番を取得する
     */
    public String getPostcdMnum() {
        return postcdMnum;
    }
    /**
     * 郵便番号_枝番を設定する
     */
    public void setPostcdBnum(String postcdBnum) {
        this.postcdBnum = postcdBnum;
    }
    
    /**
     * 郵便番号_枝番を取得する
     */
    public String getPostcdBnum() {
        return postcdBnum;
    }
    /**
     * 住所１を設定する
     */
    public void setAdr1(String adr1) {
        this.adr1 = adr1;
    }
    
    /**
     * 住所１を取得する
     */
    public String getAdr1() {
        return adr1;
    }
    /**
     * 住所２を設定する
     */
    public void setAdr2(String adr2) {
        this.adr2 = adr2;
    }
    
    /**
     * 住所２を取得する
     */
    public String getAdr2() {
        return adr2;
    }
    /**
     * 電話番号を設定する
     */
    public void setTelnum(String telnum) {
        this.telnum = telnum;
    }
    
    /**
     * 電話番号を取得する
     */
    public String getTelnum() {
        return telnum;
    }
    /**
     * 続柄区分を設定する
     */
    public void setReltnspDiv(String reltnspDiv) {
        this.reltnspDiv = reltnspDiv;
    }
    
    /**
     * 続柄区分を取得する
     */
    public String getReltnspDiv() {
        return reltnspDiv;
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
     * 緊急電話番号を取得する
     *
     * @return urgTelnum 緊急電話番号
     */
    public String getUrgTelnum() {
        return this.urgTelnum;
    }

    /**
     * 緊急電話番号を設定する
     *
     * @param urgTelnum 緊急電話番号
     */
    public void setUrgTelnum(String urgTelnum) {
        this.urgTelnum = urgTelnum;
    }
}
