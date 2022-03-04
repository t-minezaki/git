/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * コードマスタ_明細
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_cod_d")
public class MstCodDEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * コードキー
     */
    @NotNull
    private String codKey;

    /**
     * コードCD
     */
    @NotNull
    private String codCd;

    /**
     * コード値
     */
    @NotNull
    private String codValue;

    /**
     * コード値２
     */
    @TableField("cod_value_2")
    private String codValue2;

    /**
     * コード値３
     */
    @TableField("cod_value_3")
    private String codValue3;

    /**
     * コード値４
     */
    @TableField("cod_value_4")
    private String codValue4;

    /**
     * コード値５
     */
    @TableField("cod_value_5")
    private String codValue5;

    /**
     * コード内容
     */
    private String codCont;

    /**
     * ソート
     */
    private Integer sort;

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
     * コードキーを設定する
     */
    public void setCodKey(String codKey) {
        this.codKey = codKey;
    }
    
    /**
     * コードキーを取得する
     */
    public String getCodKey() {
        return codKey;
    }
    /**
     * コードCDを設定する
     */
    public void setCodCd(String codCd) {
        this.codCd = codCd;
    }
    
    /**
     * コードCDを取得する
     */
    public String getCodCd() {
        return codCd;
    }
    /**
     * コード値を設定する
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }
    
    /**
     * コード値を取得する
     */
    public String getCodValue() {
        return codValue;
    }
    /**
     * コード値２を設定する
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }
    
    /**
     * コード値２を取得する
     */
    public String getCodValue2() {
        return codValue2;
    }
    /**
     * コード値３を設定する
     */
    public void setCodValue3(String codValue3) {
        this.codValue3 = codValue3;
    }
    
    /**
     * コード値３を取得する
     */
    public String getCodValue3() {
        return codValue3;
    }
    /**
     * コード値４を設定する
     */
    public void setCodValue4(String codValue4) {
        this.codValue4 = codValue4;
    }
    
    /**
     * コード値４を取得する
     */
    public String getCodValue4() {
        return codValue4;
    }
    /**
     * コード値５を設定する
     */
    public void setCodValue5(String codValue5) {
        this.codValue5 = codValue5;
    }
    
    /**
     * コード値５を取得する
     */
    public String getCodValue5() {
        return codValue5;
    }
    /**
     * コード内容を設定する
     */
    public void setCodCont(String codCont) {
        this.codCont = codCont;
    }
    
    /**
     * コード内容を取得する
     */
    public String getCodCont() {
        return codCont;
    }
    /**
     * ソートを設定する
     */
    public void setSort(Integer sort) {
        this.sort = sort;
    }
    
    /**
     * ソートを取得する
     */
    public Integer getSort() {
        return sort;
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
