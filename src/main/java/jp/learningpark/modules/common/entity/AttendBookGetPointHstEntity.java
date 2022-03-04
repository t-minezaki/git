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
 * 出席簿付与ポイント履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("attend_book_get_point_hst")
public class AttendBookGetPointHstEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 対象年月日
     */
    @NotNull
    private Date tgtYmd;

    /**
     * 出席簿ID
     */
    @NotNull
    private Integer attendBookId;

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
     * 合格付与ポイント
     */
    private Integer passScorePoint;

    /**
     * 満点付与ポイント
     */
    private Integer fullScorePoint;

    /**
     * 宿題提出付与ポイント
     */
    private Integer hworkOutPoint;

    /**
     * 出席登録付与ポイント
     */
    private Integer absLoginPoint;

    /**
     * 付与ポイント日時
     */
    private Timestamp getPointDatime;

    /**
     * 作成日時
     */
    @NotNull
    private Timestamp createTime;

    /**
     * 作成ユーザＩＤ
     */
    @NotNull
    private String createBy;

    /**
     * 更新日時
     */
    @NotNull
    private Timestamp updateTime;

    /**
     * 更新ユーザＩＤ
     */
    @NotNull
    private String updateBy;

    /**
     * 削除フラグ
     */
    @NotNull
    private Integer delFlag;


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
     * 対象年月日を設定する
     */
    public void setTgtYmd(Date tgtYmd) {
        this.tgtYmd = tgtYmd;
    }
    
    /**
     * 対象年月日を取得する
     */
    public Date getTgtYmd() {
        return tgtYmd;
    }
    /**
     * 出席簿IDを設定する
     */
    public void setAttendBookId(Integer attendBookId) {
        this.attendBookId = attendBookId;
    }
    
    /**
     * 出席簿IDを取得する
     */
    public Integer getAttendBookId() {
        return attendBookId;
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
     * 合格付与ポイントを設定する
     */
    public void setPassScorePoint(Integer passScorePoint) {
        this.passScorePoint = passScorePoint;
    }
    
    /**
     * 合格付与ポイントを取得する
     */
    public Integer getPassScorePoint() {
        return passScorePoint;
    }
    /**
     * 満点付与ポイントを設定する
     */
    public void setFullScorePoint(Integer fullScorePoint) {
        this.fullScorePoint = fullScorePoint;
    }
    
    /**
     * 満点付与ポイントを取得する
     */
    public Integer getFullScorePoint() {
        return fullScorePoint;
    }
    /**
     * 宿題提出付与ポイントを設定する
     */
    public void setHworkOutPoint(Integer hworkOutPoint) {
        this.hworkOutPoint = hworkOutPoint;
    }
    
    /**
     * 宿題提出付与ポイントを取得する
     */
    public Integer getHworkOutPoint() {
        return hworkOutPoint;
    }
    /**
     * 出席登録付与ポイントを設定する
     */
    public void setAbsLoginPoint(Integer absLoginPoint) {
        this.absLoginPoint = absLoginPoint;
    }
    
    /**
     * 出席登録付与ポイントを取得する
     */
    public Integer getAbsLoginPoint() {
        return absLoginPoint;
    }
    /**
     * 付与ポイント日時を設定する
     */
    public void setGetPointDatime(Timestamp getPointDatime) {
        this.getPointDatime = getPointDatime;
    }
    
    /**
     * 付与ポイント日時を取得する
     */
    public Timestamp getGetPointDatime() {
        return getPointDatime;
    }
    /**
     * 作成日時を設定する
     */
    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
    
    /**
     * 作成日時を取得する
     */
    public Timestamp getCreateTime() {
        return createTime;
    }
    /**
     * 作成ユーザＩＤを設定する
     */
    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }
    
    /**
     * 作成ユーザＩＤを取得する
     */
    public String getCreateBy() {
        return createBy;
    }
    /**
     * 更新日時を設定する
     */
    public void setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
    }
    
    /**
     * 更新日時を取得する
     */
    public Timestamp getUpdateTime() {
        return updateTime;
    }
    /**
     * 更新ユーザＩＤを設定する
     */
    public void setUpdateBy(String updateBy) {
        this.updateBy = updateBy;
    }
    
    /**
     * 更新ユーザＩＤを取得する
     */
    public String getUpdateBy() {
        return updateBy;
    }
    /**
     * 削除フラグを設定する
     */
    public void setDelFlag(Integer delFlag) {
        this.delFlag = delFlag;
    }
    
    /**
     * 削除フラグを取得する
     */
    public Integer getDelFlag() {
        return delFlag;
    }
}
