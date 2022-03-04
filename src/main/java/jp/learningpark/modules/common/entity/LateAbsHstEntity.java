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
 * 遅刻欠席連絡履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("late_abs_hst")
public class LateAbsHstEntity implements Serializable {
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
     * 保護者ID
     */
    @NotNull
    private String guardId;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 遅欠連絡ステータス
     */
    @NotNull
    private String absSts;

    /**
     * 遅刻時間(分)
     */
    private Integer lateTm;

    /**
     * 対象年月日
     */
    @NotNull
    private Timestamp tgtYmd;

    /**
     * 遅欠理由
     */
    @NotNull
    private String absReason;

    /**
     * 備考
     */
    private String bikou;

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
     * 対応ステータス
     */
    @NotNull
    private String corrspdSts;

    /**
     * 対応日時
     */
    private Timestamp corrspdDatime;

    /**
     * お知らせID
     */
    @NotNull
    private Integer noticeId;


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
     * 遅欠連絡ステータスを設定する
     */
    public void setAbsSts(String absSts) {
        this.absSts = absSts;
    }
    
    /**
     * 遅欠連絡ステータスを取得する
     */
    public String getAbsSts() {
        return absSts;
    }
    /**
     * 遅刻時間(分)を設定する
     */
    public void setLateTm(Integer lateTm) {
        this.lateTm = lateTm;
    }
    
    /**
     * 遅刻時間(分)を取得する
     */
    public Integer getLateTm() {
        return lateTm;
    }
    /**
     * 対象年月日を設定する
     */
    public void setTgtYmd(Timestamp tgtYmd) {
        this.tgtYmd = tgtYmd;
    }
    
    /**
     * 対象年月日を取得する
     */
    public Timestamp getTgtYmd() {
        return tgtYmd;
    }
    /**
     * 遅欠理由を設定する
     */
    public void setAbsReason(String absReason) {
        this.absReason = absReason;
    }
    
    /**
     * 遅欠理由を取得する
     */
    public String getAbsReason() {
        return absReason;
    }
    /**
     * 備考を設定する
     */
    public void setBikou(String bikou) {
        this.bikou = bikou;
    }
    
    /**
     * 備考を取得する
     */
    public String getBikou() {
        return bikou;
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
     * 対応ステータスを設定する
     */
    public void setCorrspdSts(String corrspdSts) {
        this.corrspdSts = corrspdSts;
    }
    
    /**
     * 対応ステータスを取得する
     */
    public String getCorrspdSts() {
        return corrspdSts;
    }
    /**
     * 対応日時を設定する
     */
    public void setCorrspdDatime(Timestamp corrspdDatime) {
        this.corrspdDatime = corrspdDatime;
    }
    
    /**
     * 対応日時を取得する
     */
    public Timestamp getCorrspdDatime() {
        return corrspdDatime;
    }
    /**
     * お知らせIDを設定する
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }
    
    /**
     * お知らせIDを取得する
     */
    public Integer getNoticeId() {
        return noticeId;
    }
}
