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
 * 生徒固定スケジュール設定
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_fixd_schdl")
public class StuFixdSchdlEntity implements Serializable {
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
     * ブロックID
     */
    @NotNull
    private Integer blockId;

    /**
     * ブロック開始日
     */
    private Date blockStartDate;

    /**
     * ブロック終了日
     */
    private Date blockEndDate;

    /**
     * ブロック表示名
     */
    @NotNull
    private String blockDispyNm;

    /**
     * ブロック開始時間
     */
    private Timestamp blockStartTime;

    /**
     * ブロック終了時間
     */
    private Timestamp blockEndTime;

    /**
     * 月曜日選択フラグ
     */
    @NotNull
    private String moDwChocFlg;

    /**
     * 火曜日選択フラグ
     */
    @NotNull
    private String tuDwChocFlg;

    /**
     * 水曜日選択フラグ
     */
    @NotNull
    private String weDwChocFlg;

    /**
     * 木曜日選択フラグ
     */
    @NotNull
    private String thDwChocFlg;

    /**
     * 金曜日選択フラグ
     */
    @NotNull
    private String frDwChocFlg;

    /**
     * 土曜日選択フラグ
     */
    @NotNull
    private String saDwChocFlg;

    /**
     * 日曜日選択フラグ
     */
    @NotNull
    private String suDwChocFlg;

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
     * ブロック開始日を設定する
     */
    public void setBlockStartDate(Date blockStartDate) {
        this.blockStartDate = blockStartDate;
    }
    
    /**
     * ブロック開始日を取得する
     */
    public Date getBlockStartDate() {
        return blockStartDate;
    }
    /**
     * ブロック終了日を設定する
     */
    public void setBlockEndDate(Date blockEndDate) {
        this.blockEndDate = blockEndDate;
    }
    
    /**
     * ブロック終了日を取得する
     */
    public Date getBlockEndDate() {
        return blockEndDate;
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
     * 月曜日選択フラグを設定する
     */
    public void setMoDwChocFlg(String moDwChocFlg) {
        this.moDwChocFlg = moDwChocFlg;
    }
    
    /**
     * 月曜日選択フラグを取得する
     */
    public String getMoDwChocFlg() {
        return moDwChocFlg;
    }
    /**
     * 火曜日選択フラグを設定する
     */
    public void setTuDwChocFlg(String tuDwChocFlg) {
        this.tuDwChocFlg = tuDwChocFlg;
    }
    
    /**
     * 火曜日選択フラグを取得する
     */
    public String getTuDwChocFlg() {
        return tuDwChocFlg;
    }
    /**
     * 水曜日選択フラグを設定する
     */
    public void setWeDwChocFlg(String weDwChocFlg) {
        this.weDwChocFlg = weDwChocFlg;
    }
    
    /**
     * 水曜日選択フラグを取得する
     */
    public String getWeDwChocFlg() {
        return weDwChocFlg;
    }
    /**
     * 木曜日選択フラグを設定する
     */
    public void setThDwChocFlg(String thDwChocFlg) {
        this.thDwChocFlg = thDwChocFlg;
    }
    
    /**
     * 木曜日選択フラグを取得する
     */
    public String getThDwChocFlg() {
        return thDwChocFlg;
    }
    /**
     * 金曜日選択フラグを設定する
     */
    public void setFrDwChocFlg(String frDwChocFlg) {
        this.frDwChocFlg = frDwChocFlg;
    }
    
    /**
     * 金曜日選択フラグを取得する
     */
    public String getFrDwChocFlg() {
        return frDwChocFlg;
    }
    /**
     * 土曜日選択フラグを設定する
     */
    public void setSaDwChocFlg(String saDwChocFlg) {
        this.saDwChocFlg = saDwChocFlg;
    }
    
    /**
     * 土曜日選択フラグを取得する
     */
    public String getSaDwChocFlg() {
        return saDwChocFlg;
    }
    /**
     * 日曜日選択フラグを設定する
     */
    public void setSuDwChocFlg(String suDwChocFlg) {
        this.suDwChocFlg = suDwChocFlg;
    }
    
    /**
     * 日曜日選択フラグを取得する
     */
    public String getSuDwChocFlg() {
        return suDwChocFlg;
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
