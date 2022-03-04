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
 * イベント日程
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("event_schedule")
public class EventScheduleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * $column.comments
     */
    @NotNull
    private String orgId;

    /**
     * $column.comments
     */
    @NotNull
    private Integer eventId;

    /**
     * $column.comments
     */
    @NotNull
    private String refId;

    /**
     * $column.comments
     */
    @NotNull
    private String refType;

    /**
     * $column.comments
     */
    @NotNull
    private Date sgdPlanDate;

    /**
     * $column.comments
     */
    @NotNull
    private Timestamp sgdStartDatime;

    /**
     * $column.comments
     */
    @NotNull
    private Timestamp sgdEndDatime;

    /**
     * $column.comments
     */
    private Integer personsLimt;

    /**
     * $column.comments
     */
    private Integer unitTime;

    /**
     * $column.comments
     */
    private Timestamp cretDatime;

    /**
     * $column.comments
     */
    private String cretUsrId;

    /**
     * $column.comments
     */
    private Timestamp updDatime;

    /**
     * $column.comments
     */
    private String updUsrId;

    /**
     * $column.comments
     */
    private Integer delFlg;


    /**
     * ${column.comments}を設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getEventId() {
        return eventId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getRefId() {
        return refId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getRefType() {
        return refType;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setSgdPlanDate(Date sgdPlanDate) {
        this.sgdPlanDate = sgdPlanDate;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Date getSgdPlanDate() {
        return sgdPlanDate;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setSgdStartDatime(Timestamp sgdStartDatime) {
        this.sgdStartDatime = sgdStartDatime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Timestamp getSgdStartDatime() {
        return sgdStartDatime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setSgdEndDatime(Timestamp sgdEndDatime) {
        this.sgdEndDatime = sgdEndDatime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Timestamp getSgdEndDatime() {
        return sgdEndDatime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setPersonsLimt(Integer personsLimt) {
        this.personsLimt = personsLimt;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getPersonsLimt() {
        return personsLimt;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setUnitTime(Integer unitTime) {
        this.unitTime = unitTime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getUnitTime() {
        return unitTime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }
}
