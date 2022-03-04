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
 * 生徒個別スケジュール調整
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_indiv_schdl_adjust")
public class StuIndivSchdlAdjustEntity implements Serializable {
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
     * 固定ブロックID
     */
    @NotNull
    private Integer stuFixdSchdlId;

    /**
     * ブロックID
     */
    @NotNull
    private Integer blockId;

    /**
     * 計画年月日
     */
    @NotNull
    private Date planYmd;

    /**
     * ブロック表示名
     */
    @NotNull
    private String blockDispyNm;

    /**
     * ブロック開始時間
     */
    @NotNull
    private Timestamp blockStartTime;

    /**
     * ブロック終了時間
     */
    @NotNull
    private Timestamp blockEndTime;

    /**
     * 固定ブロック廃止フラグ
     */
    @NotNull
    private String fixdBlockAboltFlg;

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
     * 固定ブロックIDを設定する
     */
    public void setStuFixdSchdlId(Integer stuFixdSchdlId) {
        this.stuFixdSchdlId = stuFixdSchdlId;
    }
    
    /**
     * 固定ブロックIDを取得する
     */
    public Integer getStuFixdSchdlId() {
        return stuFixdSchdlId;
    }
    /**
     * ブロックIDを設定する
     */
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
    
    /**
     * ブロックIDを取得する
     */
    public Integer getBlockId() {
        return blockId;
    }
    /**
     * 計画年月日を設定する
     */
    public void setPlanYmd(Date planYmd) {
        this.planYmd = planYmd;
    }
    
    /**
     * 計画年月日を取得する
     */
    public Date getPlanYmd() {
        return planYmd;
    }
    /**
     * ブロック表示名を設定する
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }
    
    /**
     * ブロック表示名を取得する
     */
    public String getBlockDispyNm() {
        return blockDispyNm;
    }
    /**
     * ブロック開始時間を設定する
     */
    public void setBlockStartTime(Timestamp blockStartTime) {
        this.blockStartTime = blockStartTime;
    }
    
    /**
     * ブロック開始時間を取得する
     */
    public Timestamp getBlockStartTime() {
        return blockStartTime;
    }
    /**
     * ブロック終了時間を設定する
     */
    public void setBlockEndTime(Timestamp blockEndTime) {
        this.blockEndTime = blockEndTime;
    }
    
    /**
     * ブロック終了時間を取得する
     */
    public Timestamp getBlockEndTime() {
        return blockEndTime;
    }
    /**
     * 固定ブロック廃止フラグを設定する
     */
    public void setFixdBlockAboltFlg(String fixdBlockAboltFlg) {
        this.fixdBlockAboltFlg = fixdBlockAboltFlg;
    }
    
    /**
     * 固定ブロック廃止フラグを取得する
     */
    public String getFixdBlockAboltFlg() {
        return fixdBlockAboltFlg;
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
