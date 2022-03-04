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
 * 褒めポイント管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("stu_compliment_mst")
public class StuComplimentMstEntity implements Serializable {
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
     * 褒め日時
     */
    @NotNull
    private Timestamp complimentDt;

    /**
     * 褒めスタンプ区分
     */
    private String complimentDiv;

    /**
     * コメント
     */
    private String complimentCont;

    /**
     * 送信フラグ
     */
    private Integer transmissionFlg;

    /**
     * 作成者
     */
    @NotNull
    private String creatorId;

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
     * 褒め日時を設定する
     */
    public void setComplimentDt(Timestamp complimentDt) {
        this.complimentDt = complimentDt;
    }
    
    /**
     * 褒め日時を取得する
     */
    public Timestamp getComplimentDt() {
        return complimentDt;
    }
    /**
     * 褒めスタンプ区分を設定する
     */
    public void setComplimentDiv(String complimentDiv) {
        this.complimentDiv = complimentDiv;
    }
    
    /**
     * 褒めスタンプ区分を取得する
     */
    public String getComplimentDiv() {
        return complimentDiv;
    }
    /**
     * コメントを設定する
     */
    public void setComplimentCont(String complimentCont) {
        this.complimentCont = complimentCont;
    }
    
    /**
     * コメントを取得する
     */
    public String getComplimentCont() {
        return complimentCont;
    }
    /**
     * 送信フラグを設定する
     */
    public void setTransmissionFlg(Integer transmissionFlg) {
        this.transmissionFlg = transmissionFlg;
    }
    
    /**
     * 送信フラグを取得する
     */
    public Integer getTransmissionFlg() {
        return transmissionFlg;
    }
    /**
     * 作成者を設定する
     */
    public void setCreatorId(String creatorId) {
        this.creatorId = creatorId;
    }
    
    /**
     * 作成者を取得する
     */
    public String getCreatorId() {
        return creatorId;
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
