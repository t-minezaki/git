/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.learningpark.framework.utils.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 教科書マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_textb")
public class MstTextbEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 教科書ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer textbId;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 組織ID
     */
    @NotNull
    private String orgId;

    /**
     * 教科書名
     */
    @NotNull
    private String textbNm;

    /**
     * 出版社区分
     */
    @NotNull
    private String publisherDiv;

    /**
     * 学年区分
     */
    @NotNull
    private String schyDiv;

    /**
     * 教科書廃止フラグ
     */
    @NotNull
    private String textbAboltFlg;

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
     * 教科書IDを設定する
     */
    public void setTextbId(Integer textbId) {
        this.textbId = textbId;
    }
    
    /**
     * 教科書IDを取得する
     */
    public Integer getTextbId() {
        return textbId;
    }
    /**
     * 教科区分を設定する
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }
    
    /**
     * 教科区分を取得する
     */
    public String getSubjtDiv() {
        return subjtDiv;
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
     * 教科書名を設定する
     */
    public void setTextbNm(String textbNm) {
        this.textbNm = textbNm;
    }
    
    /**
     * 教科書名を取得する
     */
    public String getTextbNm() {
        return textbNm;
    }
    /**
     * 出版社区分を設定する
     */
    public void setPublisherDiv(String publisherDiv) {
        this.publisherDiv = publisherDiv;
    }
    
    /**
     * 出版社区分を取得する
     */
    public String getPublisherDiv() {
        return publisherDiv;
    }
    /**
     * 学年区分を設定する
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }
    
    /**
     * 学年区分を取得する
     */
    public String getSchyDiv() {
        return StringUtils.trim(schyDiv);
    }
    /**
     * 教科書廃止フラグを設定する
     */
    public void setTextbAboltFlg(String textbAboltFlg) {
        this.textbAboltFlg = textbAboltFlg;
    }
    
    /**
     * 教科書廃止フラグを取得する
     */
    public String getTextbAboltFlg() {
        return textbAboltFlg;
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
