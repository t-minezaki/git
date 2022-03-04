/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * 生徒テスト目標結果_明細
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_test_goal_result_d")
public class StuTestGoalResultDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @NotNull
    private Integer id;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 目標点数
     */
    private Integer goalPoints;

    /**
     * 目標満点
     */
    private Integer goalPerfectPoints;

    /**
     * 結果点数
     */
    private Integer resultPoints;

    /**
     * 結果満点
     */
    private Integer resultPerfectPoints;

    /**
     * 結果順位
     */
    private Integer resultRank;

    /**
     * 結果平均点
     */
    private BigDecimal resultAvg;

    /**
     * 偏差値
     */
    private BigDecimal devionValue;

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
     * 目標点数を設定する
     */
    public void setGoalPoints(Integer goalPoints) {
        this.goalPoints = goalPoints;
    }
    
    /**
     * 目標点数を取得する
     */
    public Integer getGoalPoints() {
        return goalPoints;
    }
    /**
     * 目標満点を設定する
     */
    public void setGoalPerfectPoints(Integer goalPerfectPoints) {
        this.goalPerfectPoints = goalPerfectPoints;
    }
    
    /**
     * 目標満点を取得する
     */
    public Integer getGoalPerfectPoints() {
        return goalPerfectPoints;
    }
    /**
     * 結果点数を設定する
     */
    public void setResultPoints(Integer resultPoints) {
        this.resultPoints = resultPoints;
    }
    
    /**
     * 結果点数を取得する
     */
    public Integer getResultPoints() {
        return resultPoints;
    }
    /**
     * 結果満点を設定する
     */
    public void setResultPerfectPoints(Integer resultPerfectPoints) {
        this.resultPerfectPoints = resultPerfectPoints;
    }
    
    /**
     * 結果満点を取得する
     */
    public Integer getResultPerfectPoints() {
        return resultPerfectPoints;
    }
    /**
     * 結果順位を設定する
     */
    public void setResultRank(Integer resultRank) {
        this.resultRank = resultRank;
    }
    
    /**
     * 結果順位を取得する
     */
    public Integer getResultRank() {
        return resultRank;
    }
    /**
     * 結果平均点を設定する
     */
    public void setResultAvg(BigDecimal resultAvg) {
        this.resultAvg = resultAvg;
    }
    
    /**
     * 結果平均点を取得する
     */
    public BigDecimal getResultAvg() {
        return resultAvg;
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
     * 偏差値を取得する
     *
     * @return devionValue 偏差値
     */
    public BigDecimal getDevionValue() {
        return this.devionValue;
    }

    /**
     * 偏差値を設定する
     *
     * @param devionValue 偏差値
     */
    public void setDevionValue(BigDecimal devionValue) {
        this.devionValue = devionValue;
    }
}
