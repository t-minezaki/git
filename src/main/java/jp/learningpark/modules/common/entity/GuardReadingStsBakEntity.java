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
 * 保護者お知らせ閲覧状況BAK
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("guard_reading_sts_bak")
public class GuardReadingStsBakEntity implements Serializable {
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
     * お知らせID
     */
    @NotNull
    private Integer noticeId;

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
     * 閲覧状況区分
     */
    @NotNull
    private String readingStsDiv;

    /**
     * 開封状況区分
     */
    private String openedFlg;

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
     * 閲覧状況区分を設定する
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }
    
    /**
     * 閲覧状況区分を取得する
     */
    public String getReadingStsDiv() {
        return readingStsDiv;
    }
    /**
     * 開封状況区分を設定する
     */
    public void setOpenedFlg(String openedFlg) {
        this.openedFlg = openedFlg;
    }
    
    /**
     * 開封状況区分を取得する
     */
    public String getOpenedFlg() {
        return openedFlg;
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
