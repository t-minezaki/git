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
import java.util.Date;

/**
 * 指導報告書ヘーダ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("guid_repr_h")
public class GuidReprHEntity implements Serializable {
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
     * 指導報告書コード
     */
    @NotNull
    private String guidReprCd;

    /**
     * 出席簿コード
     */
    @NotNull
    private String attendBookCd;

    /**
     * 報告書作成日
     */
    @NotNull
    private Date reprCretDt;

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
     * 指導報告書コードを設定する
     */
    public void setGuidReprCd(String guidReprCd) {
        this.guidReprCd = guidReprCd;
    }
    
    /**
     * 指導報告書コードを取得する
     */
    public String getGuidReprCd() {
        return guidReprCd;
    }
    /**
     * 出席簿コードを設定する
     */
    public void setAttendBookCd(String attendBookCd) {
        this.attendBookCd = attendBookCd;
    }
    
    /**
     * 出席簿コードを取得する
     */
    public String getAttendBookCd() {
        return attendBookCd;
    }
    /**
     * 報告書作成日を設定する
     */
    public void setReprCretDt(Date reprCretDt) {
        this.reprCretDt = reprCretDt;
    }
    
    /**
     * 報告書作成日を取得する
     */
    public Date getReprCretDt() {
        return reprCretDt;
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
