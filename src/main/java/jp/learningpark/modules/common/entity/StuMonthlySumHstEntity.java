/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jp.learningpark.framework.utils.StringUtils;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * 子供ニュース月次集計履歴
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_monthly_sum_hst")
public class StuMonthlySumHstEntity implements Serializable {
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
     * 掲載開始日
     */
    @NotNull
    private Date pubStartDate;

    /**
     * 掲載終了日
     */
    @NotNull
    private Date pubEndDate;

    /**
     * 集計開始日
     */
    @NotNull
    private Date sumStartDate;

    /**
     * 集計終了日
     */
    @NotNull
    private Date sumEndDate;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 学年区分
     */
    @NotNull
    private String schyDiv;

    /**
     * 累積実績学習時間
     */
    @NotNull
    private Integer sumPerfLeaTime;

    /**
     * 学習時間順位
     */
    private Integer perfLeaRanking;

    /**
     * 学習時間順位（全体）
     */
    private Integer perfLeaRankingAll;

    /**
     * 累積学習時間レベル区分
     */
    private String totalLeaTmLevelDiv;

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
     * 掲載開始日を設定する
     */
    public void setPubStartDate(Date pubStartDate) {
        this.pubStartDate = pubStartDate;
    }
    
    /**
     * 掲載開始日を取得する
     */
    public Date getPubStartDate() {
        return pubStartDate;
    }
    /**
     * 掲載終了日を設定する
     */
    public void setPubEndDate(Date pubEndDate) {
        this.pubEndDate = pubEndDate;
    }
    
    /**
     * 掲載終了日を取得する
     */
    public Date getPubEndDate() {
        return pubEndDate;
    }
    /**
     * 集計開始日を設定する
     */
    public void setSumStartDate(Date sumStartDate) {
        this.sumStartDate = sumStartDate;
    }
    
    /**
     * 集計開始日を取得する
     */
    public Date getSumStartDate() {
        return sumStartDate;
    }
    /**
     * 集計終了日を設定する
     */
    public void setSumEndDate(Date sumEndDate) {
        this.sumEndDate = sumEndDate;
    }
    
    /**
     * 集計終了日を取得する
     */
    public Date getSumEndDate() {
        return sumEndDate;
    }
    /**
     * 教科区分を設定する
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }
    
    /**
     * 教科区分を取得する
     */
    public String getSubjtDiv() {
        return subjtDiv;
    }
    /**
     * 学年区分を設定する
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }
    
    /**
     * 学年区分を取得する
     */
    public String getSchyDiv() {
        return StringUtils.trim(schyDiv);
    }
    /**
     * 累積実績学習時間を設定する
     */
    public void setSumPerfLeaTime(Integer sumPerfLeaTime) {
        this.sumPerfLeaTime = sumPerfLeaTime;
    }
    
    /**
     * 累積実績学習時間を取得する
     */
    public Integer getSumPerfLeaTime() {
        return sumPerfLeaTime;
    }
    /**
     * 学習時間順位を設定する
     */
    public void setPerfLeaRanking(Integer perfLeaRanking) {
        this.perfLeaRanking = perfLeaRanking;
    }
    
    /**
     * 学習時間順位を取得する
     */
    public Integer getPerfLeaRanking() {
        return perfLeaRanking;
    }
    /**
     * 学習時間順位（全体）を設定する
     */
    public void setPerfLeaRankingAll(Integer perfLeaRankingAll) {
        this.perfLeaRankingAll = perfLeaRankingAll;
    }
    
    /**
     * 学習時間順位（全体）を取得する
     */
    public Integer getPerfLeaRankingAll() {
        return perfLeaRankingAll;
    }
    /**
     * 累積学習時間レベル区分を設定する
     */
    public void setTotalLeaTmLevelDiv(String totalLeaTmLevelDiv) {
        this.totalLeaTmLevelDiv = totalLeaTmLevelDiv;
    }
    
    /**
     * 累積学習時間レベル区分を取得する
     */
    public String getTotalLeaTmLevelDiv() {
        return totalLeaTmLevelDiv;
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
