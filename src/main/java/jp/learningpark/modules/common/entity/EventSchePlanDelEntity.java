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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("event_sche_plan_del")
public class EventSchePlanDelEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * イベントID
     */
    @NotNull
    private Integer eventId;

    /**
     * イベント日程ID
     */
    @NotNull
    private Integer eventScheId;

    /**
     * 関連ID
     */
    @NotNull
    private String refId;

    /**
     * 関連タイプ
     */
    @NotNull
    private String refTypeDiv;

    /**
     * 日程予定日
     */
    @NotNull
    private Date sgdPlanDate;

    /**
     * 日程開始日時
     */
    @NotNull
    private Timestamp sgdStartDatime;

    /**
     * 日程終了日時
     */
    @NotNull
    private Timestamp sgdEndDatime;

    /**
     * 定員
     */
    @NotNull
    private Integer personsLimt;

    /**
     * 予定済人数
     */
    @NotNull
    private Integer planedMember;

    /**
     * 取消フラグ
     */
    @NotNull
    private String cancelFlg;

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
     * イベント日程IDを設定する
     */
    public void setEventScheId(Integer eventScheId) {
        this.eventScheId = eventScheId;
    }
    
    /**
     * イベント日程IDを取得する
     */
    public Integer getEventScheId() {
        return eventScheId;
    }
    /**
     * 関連IDを設定する
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * 関連IDを取得する
     */
    public String getRefId() {
        return refId;
    }
    /**
     * 関連タイプを設定する
     */
    public void setRefTypeDiv(String refTypeDiv) {
        this.refTypeDiv = refTypeDiv;
    }
    
    /**
     * 関連タイプを取得する
     */
    public String getRefTypeDiv() {
        return refTypeDiv;
    }
    /**
     * 日程予定日を設定する
     */
    public void setSgdPlanDate(Date sgdPlanDate) {
        this.sgdPlanDate = sgdPlanDate;
    }
    
    /**
     * 日程予定日を取得する
     */
    public Date getSgdPlanDate() {
        return sgdPlanDate;
    }
    /**
     * 日程開始日時を設定する
     */
    public void setSgdStartDatime(Timestamp sgdStartDatime) {
        this.sgdStartDatime = sgdStartDatime;
    }
    
    /**
     * 日程開始日時を取得する
     */
    public Timestamp getSgdStartDatime() {
        return sgdStartDatime;
    }
    /**
     * 日程終了日時を設定する
     */
    public void setSgdEndDatime(Timestamp sgdEndDatime) {
        this.sgdEndDatime = sgdEndDatime;
    }
    
    /**
     * 日程終了日時を取得する
     */
    public Timestamp getSgdEndDatime() {
        return sgdEndDatime;
    }
    /**
     * 定員を設定する
     */
    public void setPersonsLimt(Integer personsLimt) {
        this.personsLimt = personsLimt;
    }
    
    /**
     * 定員を取得する
     */
    public Integer getPersonsLimt() {
        return personsLimt;
    }
    /**
     * 予定済人数を設定する
     */
    public void setPlanedMember(Integer planedMember) {
        this.planedMember = planedMember;
    }
    
    /**
     * 予定済人数を取得する
     */
    public Integer getPlanedMember() {
        return planedMember;
    }
    /**
     * 取消フラグを設定する
     */
    public void setCancelFlg(String cancelFlg) {
        this.cancelFlg = cancelFlg;
    }
    
    /**
     * 取消フラグを取得する
     */
    public String getCancelFlg() {
        return cancelFlg;
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
