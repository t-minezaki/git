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
 * 機能一覧マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_fun")
public class MstFunEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 管理分類名
     */
    @NotNull
    private String mgrDivNm;

    /**
     * 機能分類名
     */
    @NotNull
    private String funDivNm;

    /**
     * 機能名
     */
    @NotNull
    private String funNm;

    /**
     * 機能ID
     */
    @NotNull
    private String funId;

    /**
     * 管理者フラグ
     */
    @NotNull
    private String mgrFlg;

    /**
     * メンターフラグ
     */
    @NotNull
    private String mentFlg;

    /**
     * 順番
     */
    @NotNull
    private Integer junbanNum;

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
     * 管理分類名を設定する
     */
    public void setMgrDivNm(String mgrDivNm) {
        this.mgrDivNm = mgrDivNm;
    }
    
    /**
     * 管理分類名を取得する
     */
    public String getMgrDivNm() {
        return mgrDivNm;
    }
    /**
     * 機能分類名を設定する
     */
    public void setFunDivNm(String funDivNm) {
        this.funDivNm = funDivNm;
    }
    
    /**
     * 機能分類名を取得する
     */
    public String getFunDivNm() {
        return funDivNm;
    }
    /**
     * 機能名を設定する
     */
    public void setFunNm(String funNm) {
        this.funNm = funNm;
    }
    
    /**
     * 機能名を取得する
     */
    public String getFunNm() {
        return funNm;
    }
    /**
     * 機能IDを設定する
     */
    public void setFunId(String funId) {
        this.funId = funId;
    }
    
    /**
     * 機能IDを取得する
     */
    public String getFunId() {
        return funId;
    }
    /**
     * 管理者フラグを設定する
     */
    public void setMgrFlg(String mgrFlg) {
        this.mgrFlg = mgrFlg;
    }
    
    /**
     * 管理者フラグを取得する
     */
    public String getMgrFlg() {
        return mgrFlg;
    }
    /**
     * メンターフラグを設定する
     */
    public void setMentFlg(String mentFlg) {
        this.mentFlg = mentFlg;
    }
    
    /**
     * メンターフラグを取得する
     */
    public String getMentFlg() {
        return mentFlg;
    }
    /**
     * 順番を設定する
     */
    public void setJunbanNum(Integer junbanNum) {
        this.junbanNum = junbanNum;
    }
    
    /**
     * 順番を取得する
     */
    public Integer getJunbanNum() {
        return junbanNum;
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
