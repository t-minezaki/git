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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("perf_stand_mst")
public class PerfStandMstEntity implements Serializable {
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
     * ユーザID
     */
    @NotNull
    private String usrId;

    /**
     * しきい値1（日）
     */
    @NotNull
    private Integer perfStandDay1;

    /**
     * しきい値2（日）
     */
    @NotNull
    private Integer perfStandDay2;

    /**
     * しきい値3（日）
     */
    @NotNull
    private Integer perfStandDay3;

    /**
     * しきい値1（週）
     */
    @NotNull
    private Integer perfStandWeek1;

    /**
     * しきい値2（週）
     */
    @NotNull
    private Integer perfStandWeek2;

    /**
     * しきい値3（週）
     */
    @NotNull
    private Integer perfStandWeek3;

    /**
     * しきい値1（月）
     */
    @NotNull
    private Integer perfStandMonth1;

    /**
     * しきい値2（月）
     */
    @NotNull
    private Integer perfStandMonth2;

    /**
     * しきい値3（月）
     */
    @NotNull
    private Integer perfStandMonth3;

    /**
     * 作成者
     */
    @NotNull
    private String creatorId;

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
     * ユーザIDを設定する
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
    
    /**
     * ユーザIDを取得する
     */
    public String getUsrId() {
        return usrId;
    }
    /**
     * しきい値1（日）を設定する
     */
    public void setPerfStandDay1(Integer perfStandDay1) {
        this.perfStandDay1 = perfStandDay1;
    }
    
    /**
     * しきい値1（日）を取得する
     */
    public Integer getPerfStandDay1() {
        return perfStandDay1;
    }
    /**
     * しきい値2（日）を設定する
     */
    public void setPerfStandDay2(Integer perfStandDay2) {
        this.perfStandDay2 = perfStandDay2;
    }
    
    /**
     * しきい値2（日）を取得する
     */
    public Integer getPerfStandDay2() {
        return perfStandDay2;
    }
    /**
     * しきい値3（日）を設定する
     */
    public void setPerfStandDay3(Integer perfStandDay3) {
        this.perfStandDay3 = perfStandDay3;
    }
    
    /**
     * しきい値3（日）を取得する
     */
    public Integer getPerfStandDay3() {
        return perfStandDay3;
    }
    /**
     * しきい値1（週）を設定する
     */
    public void setPerfStandWeek1(Integer perfStandWeek1) {
        this.perfStandWeek1 = perfStandWeek1;
    }
    
    /**
     * しきい値1（週）を取得する
     */
    public Integer getPerfStandWeek1() {
        return perfStandWeek1;
    }
    /**
     * しきい値2（週）を設定する
     */
    public void setPerfStandWeek2(Integer perfStandWeek2) {
        this.perfStandWeek2 = perfStandWeek2;
    }
    
    /**
     * しきい値2（週）を取得する
     */
    public Integer getPerfStandWeek2() {
        return perfStandWeek2;
    }
    /**
     * しきい値3（週）を設定する
     */
    public void setPerfStandWeek3(Integer perfStandWeek3) {
        this.perfStandWeek3 = perfStandWeek3;
    }
    
    /**
     * しきい値3（週）を取得する
     */
    public Integer getPerfStandWeek3() {
        return perfStandWeek3;
    }
    /**
     * しきい値1（月）を設定する
     */
    public void setPerfStandMonth1(Integer perfStandMonth1) {
        this.perfStandMonth1 = perfStandMonth1;
    }
    
    /**
     * しきい値1（月）を取得する
     */
    public Integer getPerfStandMonth1() {
        return perfStandMonth1;
    }
    /**
     * しきい値2（月）を設定する
     */
    public void setPerfStandMonth2(Integer perfStandMonth2) {
        this.perfStandMonth2 = perfStandMonth2;
    }
    
    /**
     * しきい値2（月）を取得する
     */
    public Integer getPerfStandMonth2() {
        return perfStandMonth2;
    }
    /**
     * しきい値3（月）を設定する
     */
    public void setPerfStandMonth3(Integer perfStandMonth3) {
        this.perfStandMonth3 = perfStandMonth3;
    }
    
    /**
     * しきい値3（月）を取得する
     */
    public Integer getPerfStandMonth3() {
        return perfStandMonth3;
    }
    /**
     * 作成者を設定する
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    
    /**
     * 作成者を取得する
     */
    public String getCreatorId() {
        return creatorId;
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
