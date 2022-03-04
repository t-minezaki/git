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
 * 学習時期マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_learn_seasn")
public class MstLearnSeasnEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 塾学習期間ID
     */
    @NotNull
    private Integer crmLearnPrdId;

    /**
     * 計画学習時期
     */
    @NotNull
    private Integer planLearnSeasn;

    /**
     * 学習時期開始日
     */
    @NotNull
    private Date learnSeasnStartDy;

    /**
     * 学習時期終了日
     */
    @NotNull
    private Date learnSeasnEndDy;

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
     * 塾学習期間IDを設定する
     */
    public void setCrmLearnPrdId(Integer crmLearnPrdId) {
        this.crmLearnPrdId = crmLearnPrdId;
    }
    
    /**
     * 塾学習期間IDを取得する
     */
    public Integer getCrmLearnPrdId() {
        return crmLearnPrdId;
    }
    /**
     * 計画学習時期を設定する
     */
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }
    
    /**
     * 計画学習時期を取得する
     */
    public Integer getPlanLearnSeasn() {
        return planLearnSeasn;
    }
    /**
     * 学習時期開始日を設定する
     */
    public void setLearnSeasnStartDy(Date learnSeasnStartDy) {
        this.learnSeasnStartDy = learnSeasnStartDy;
    }
    
    /**
     * 学習時期開始日を取得する
     */
    public Date getLearnSeasnStartDy() {
        return learnSeasnStartDy;
    }
    /**
     * 学習時期終了日を設定する
     */
    public void setLearnSeasnEndDy(Date learnSeasnEndDy) {
        this.learnSeasnEndDy = learnSeasnEndDy;
    }
    
    /**
     * 学習時期終了日を取得する
     */
    public Date getLearnSeasnEndDy() {
        return learnSeasnEndDy;
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
