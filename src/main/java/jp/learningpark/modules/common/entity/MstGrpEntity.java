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
 * グループマスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_grp")
public class MstGrpEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * グループID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer grpId;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 曜日区分
     */
    private String dayweekDiv;

    /**
     * グループ名
     */
    @NotNull
    private String grpNm;

    /**
     * グループコード
     */
    private String grpCod;

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
     * グループIDを設定する
     */
    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }
    
    /**
     * グループIDを取得する
     */
    public Integer getGrpId() {
        return grpId;
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
     * 曜日区分を設定する
     */
    public void setDayweekDiv(String dayweekDiv) {
        this.dayweekDiv = dayweekDiv;
    }
    
    /**
     * 曜日区分を取得する
     */
    public String getDayweekDiv() {
        return dayweekDiv;
    }

    /**
     * グループコードを取得する
     */
    public String getGrpCod() { return grpCod; }

    /**
     * グループコードを設定する
     */
    public void setGrpCod(String grpCod) { this.grpCod = grpCod; }

    /**
     * グループ名を設定する
     */
    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }
    
    /**
     * グループ名を取得する
     */
    public String getGrpNm() {
        return grpNm;
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
