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
 * 生徒出席予定実績
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_attend_plan_hst")
public class StuAttendPlanHstEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 予定出席日付
     */
    @NotNull
    private Date planAbsDate;

    /**
     * 出欠区分
     */
    @NotNull
    private String absDiv;

    /**
     * 遅刻理由
     */
    private String absReason;

    /**
     * 登録者ID
     */
    private String entryId;

    /**
     * 登録区分
     */
    private String entryDiv;

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
     * 予定出席日付を設定する
     */
    public void setPlanAbsDate(Date planAbsDate) {
        this.planAbsDate = planAbsDate;
    }
    
    /**
     * 予定出席日付を取得する
     */
    public Date getPlanAbsDate() {
        return planAbsDate;
    }
    /**
     * 出欠区分を設定する
     */
    public void setAbsDiv(String absDiv) {
        this.absDiv = absDiv;
    }
    
    /**
     * 出欠区分を取得する
     */
    public String getAbsDiv() {
        return absDiv;
    }
    /**
     * 遅刻理由を設定する
     */
    public void setAbsReason(String absReason) {
        this.absReason = absReason;
    }
    
    /**
     * 遅刻理由を取得する
     */
    public String getAbsReason() {
        return absReason;
    }
    /**
     * 登録者IDを設定する
     */
    public void setEntryId(String entryId) {
        this.entryId = entryId;
    }
    
    /**
     * 登録者IDを取得する
     */
    public String getEntryId() {
        return entryId;
    }
    /**
     * 登録区分を設定する
     */
    public void setEntryDiv(String entryDiv) {
        this.entryDiv = entryDiv;
    }
    
    /**
     * 登録区分を取得する
     */
    public String getEntryDiv() {
        return entryDiv;
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
