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
 * ユーザ初期パスワード管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_usr_firt_pw")
public class MstUsrFirtPwEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * ユーザID
     */
    @NotNull
    private String usrId;

    /**
     * ロール区分
     */
    @NotNull
    private String roleDiv;

    /**
     * ユーザ初期PW
     */
    @NotNull
    private String usrFstPassword;

    /**
     * システム区分
     */
    @NotNull
    private String sysDiv;

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
     * ユーザIDを設定する
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }
    
    /**
     * ユーザIDを取得する
     */
    public String getUsrId() {
        return usrId;
    }
    /**
     * ロール区分を設定する
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }
    
    /**
     * ロール区分を取得する
     */
    public String getRoleDiv() {
        return roleDiv;
    }
    /**
     * ユーザ初期PWを設定する
     */
    public void setUsrFstPassword(String usrFstPassword) {
        this.usrFstPassword = usrFstPassword;
    }
    
    /**
     * ユーザ初期PWを取得する
     */
    public String getUsrFstPassword() {
        return usrFstPassword;
    }
    /**
     * システム区分を設定する
     */
    public void setSysDiv(String sysDiv) {
        this.sysDiv = sysDiv;
    }
    
    /**
     * システム区分を取得する
     */
    public String getSysDiv() {
        return sysDiv;
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
