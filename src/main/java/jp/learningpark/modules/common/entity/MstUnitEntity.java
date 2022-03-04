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

/**
 * 単元マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_unit")
public class MstUnitEntity implements Serializable {
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
     * 単元管理コード
     */
    private String unitMstCd;

    /**
     * 学年区分
     */
    @NotNull
    private String schyDiv;

    /**
     * 教科区分
     */
    @NotNull
    private String subjtDiv;

    /**
     * 単元名
     */
    private String unitNm;

    /**
     * 節名
     */
    private String sectnNm;

    /**
     * 章名
     */
    private String chaptNm;

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
     * 単元管理コードを設定する
     */
    public void setUnitMstCd(String unitMstCd) {
        this.unitMstCd = unitMstCd;
    }
    
    /**
     * 単元管理コードを取得する
     */
    public String getUnitMstCd() {
        return unitMstCd;
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
     * 単元名を設定する
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }
    
    /**
     * 単元名を取得する
     */
    public String getUnitNm() {
        return unitNm;
    }
    /**
     * 節名を設定する
     */
    public void setSectnNm(String sectnNm) {
        this.sectnNm = sectnNm;
    }
    
    /**
     * 節名を取得する
     */
    public String getSectnNm() {
        return sectnNm;
    }
    /**
     * 章名を設定する
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }
    
    /**
     * 章名を取得する
     */
    public String getChaptNm() {
        return chaptNm;
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
