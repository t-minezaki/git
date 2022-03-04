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
 * 生徒通塾ステータス
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_crmsch_sts")
public class StuCrmschStsEntity implements Serializable {
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
     * 通塾日付
     */
    @NotNull
    private Date crmschDate;

    /**
     * 出欠フラグ
     */
    @NotNull
    private String absFlg;

    /**
     * 宿題提出フラグ
     */
    @NotNull
    private String hwkSubmitFlg;

    /**
     * 出欠理由
     */
    private String absReason;

    /**
     * メンターID
     */
    @NotNull
    private String mentorId;

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
     * 通塾日付を設定する
     */
    public void setCrmschDate(Date crmschDate) {
        this.crmschDate = crmschDate;
    }
    
    /**
     * 通塾日付を取得する
     */
    public Date getCrmschDate() {
        return crmschDate;
    }
    /**
     * 出欠フラグを設定する
     */
    public void setAbsFlg(String absFlg) {
        this.absFlg = absFlg;
    }
    
    /**
     * 出欠フラグを取得する
     */
    public String getAbsFlg() {
        return absFlg;
    }
    /**
     * 宿題提出フラグを設定する
     */
    public void setHwkSubmitFlg(String hwkSubmitFlg) {
        this.hwkSubmitFlg = hwkSubmitFlg;
    }
    
    /**
     * 宿題提出フラグを取得する
     */
    public String getHwkSubmitFlg() {
        return hwkSubmitFlg;
    }
    /**
     * 出欠理由を設定する
     */
    public void setAbsReason(String absReason) {
        this.absReason = absReason;
    }
    
    /**
     * 出欠理由を取得する
     */
    public String getAbsReason() {
        return absReason;
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
