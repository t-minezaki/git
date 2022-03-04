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
 * 住所マスタ
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_addr")
public class MstAddrEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * 住所コード
     */
    @NotNull
    private String addrCd;

    /**
     * 郵便番号_主番
     */
    private String postcdMnum;

    /**
     * 郵便番号_枝番
     */
    private String postcdBnum;

    /**
     * 都道府県名
     */
    @NotNull
    private String prefctNm;

    /**
     * 町村市名
     */
    private String ctyctrNm;

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
     * 住所コードを設定する
     */
    public void setAddrCd(String addrCd) {
        this.addrCd = addrCd;
    }
    
    /**
     * 住所コードを取得する
     */
    public String getAddrCd() {
        return addrCd;
    }
    /**
     * 郵便番号_主番を設定する
     */
    public void setPostcdMnum(String postcdMnum) {
        this.postcdMnum = postcdMnum;
    }
    
    /**
     * 郵便番号_主番を取得する
     */
    public String getPostcdMnum() {
        return postcdMnum;
    }
    /**
     * 郵便番号_枝番を設定する
     */
    public void setPostcdBnum(String postcdBnum) {
        this.postcdBnum = postcdBnum;
    }
    
    /**
     * 郵便番号_枝番を取得する
     */
    public String getPostcdBnum() {
        return postcdBnum;
    }
    /**
     * 都道府県名を設定する
     */
    public void setPrefctNm(String prefctNm) {
        this.prefctNm = prefctNm;
    }
    
    /**
     * 都道府県名を取得する
     */
    public String getPrefctNm() {
        return prefctNm;
    }
    /**
     * 町村市名を設定する
     */
    public void setCtyctrNm(String ctyctrNm) {
        this.ctyctrNm = ctyctrNm;
    }
    
    /**
     * 町村市名を取得する
     */
    public String getCtyctrNm() {
        return ctyctrNm;
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
