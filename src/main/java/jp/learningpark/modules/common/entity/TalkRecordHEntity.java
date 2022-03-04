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
 * 面談記録
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("talk_record_h")
public class TalkRecordHEntity implements Serializable {
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
     * イベントID
     */
    @NotNull
    private Integer eventId;

    /**
     * 面談日時
     */
    @NotNull
    private Timestamp talkDatime;

    /**
     * メンターID
     */
    private String mentorId;

    /**
     * 生徒ID
     */
    private String stuId;

    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 面談申込状態区分
     */
    private String talkApplyStsDiv;

    /**
     * 部活の両立状況
     */
    private String megState;

    /**
     * 予約事・アドバイス内容
     */
    private String subeCont;

    /**
     * 備考
     */
    private String noteCont;

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
     * イベントIDを設定する
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    
    /**
     * イベントIDを取得する
     */
    public Integer getEventId() {
        return eventId;
    }
    /**
     * 面談日時を設定する
     */
    public void setTalkDatime(Timestamp talkDatime) {
        this.talkDatime = talkDatime;
    }
    
    /**
     * 面談日時を取得する
     */
    public Timestamp getTalkDatime() {
        return talkDatime;
    }
    /**
     * メンターIDを設定する
     */
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }
    
    /**
     * メンターIDを取得する
     */
    public String getMentorId() {
        return mentorId;
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
     * 面談申込状態区分を設定する
     */
    public void setTalkApplyStsDiv(String talkApplyStsDiv) {
        this.talkApplyStsDiv = talkApplyStsDiv;
    }
    
    /**
     * 面談申込状態区分を取得する
     */
    public String getTalkApplyStsDiv() {
        return talkApplyStsDiv;
    }
    /**
     * 部活の両立状況を設定する
     */
    public void setMegState(String megState) {
        this.megState = megState;
    }
    
    /**
     * 部活の両立状況を取得する
     */
    public String getMegState() {
        return megState;
    }
    /**
     * 予約事・アドバイス内容を設定する
     */
    public void setSubeCont(String subeCont) {
        this.subeCont = subeCont;
    }
    
    /**
     * 予約事・アドバイス内容を取得する
     */
    public String getSubeCont() {
        return subeCont;
    }
    /**
     * 備考を設定する
     */
    public void setNoteCont(String noteCont) {
        this.noteCont = noteCont;
    }
    
    /**
     * 備考を取得する
     */
    public String getNoteCont() {
        return noteCont;
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
