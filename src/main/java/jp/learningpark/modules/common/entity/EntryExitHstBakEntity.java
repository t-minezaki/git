/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.sql.Timestamp;
import java.sql.Time;

/**
 * 入退室履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("entry_exit_hst_bak")
public class EntryExitHstBakEntity implements Serializable {
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
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 登録日時
     */
    @NotNull
    private Timestamp entryDt;

    /**
     * 登録フラグ
     */
    @NotNull
    private String entryFlg;

    /**
     * 登録ユーザID
     */
    private String entryUserId;

    /**
     * 通知ステータス
     */
    @NotNull
    private String noticeSts;

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
     * 退避日時
     */
    @NotNull
    private Timestamp evacuationDatime;

    /**
     * 退避ID
     */
    @NotNull
    private String evacuationUsrId;

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
     * 生徒IDを設定する
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    
    /**
     * 生徒IDを取得する
     */
    public String getStuId() {
        return stuId;
    }
    /**
     * 登録日時を設定する
     */
    public void setEntryDt(Timestamp entryDt) {
        this.entryDt = entryDt;
    }
    
    /**
     * 登録日時を取得する
     */
    public Timestamp getEntryDt() {
        return entryDt;
    }
    /**
     * 登録フラグを設定する
     */
    public void setEntryFlg(String entryFlg) {
        this.entryFlg = entryFlg;
    }
    
    /**
     * 登録フラグを取得する
     */
    public String getEntryFlg() {
        return entryFlg;
    }
    /**
     * 登録ユーザIDを設定する
     */
    public void setEntryUserId(String entryUserId) {
        this.entryUserId = entryUserId;
    }
    
    /**
     * 登録ユーザIDを取得する
     */
    public String getEntryUserId() {
        return entryUserId;
    }
    /**
     * 通知ステータスを設定する
     */
    public void setNoticeSts(String noticeSts) {
        this.noticeSts = noticeSts;
    }
    
    /**
     * 通知ステータスを取得する
     */
    public String getNoticeSts() {
        return noticeSts;
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
     * 退避日時を設定する
     */
    public void setEvacuationDatime(Timestamp evacuationDatime) {
        this.evacuationDatime = evacuationDatime;
    }
    
    /**
     * 退避日時を取得する
     */
    public Timestamp getEvacuationDatime() {
        return evacuationDatime;
    }
    /**
     * 退避IDを設定する
     */
    public void setEvacuationUsrId(String evacuationUsrId) {
        this.evacuationUsrId = evacuationUsrId;
    }
    
    /**
     * 退避IDを取得する
     */
    public String getEvacuationUsrId() {
        return evacuationUsrId;
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
