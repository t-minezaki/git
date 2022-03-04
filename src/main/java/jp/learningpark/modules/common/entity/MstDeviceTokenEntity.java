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
 * デバイスTOKEN管理
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_device_token")
public class MstDeviceTokenEntity implements Serializable {
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
     * デバイスTOKEN
     */
    @NotNull
    private String deviceToken;

    /**
     * 端末類型区分
     */
    @NotNull
    private String phoneTypeDiv;

    /**
     * デバイスID
     */
    private Integer deviceId;

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
     * デバイスTOKENを設定する
     */
    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }
    
    /**
     * デバイスTOKENを取得する
     */
    public String getDeviceToken() {
        return deviceToken;
    }
    /**
     * 端末類型区分を設定する
     */
    public void setPhoneTypeDiv(String phoneTypeDiv) {
        this.phoneTypeDiv = phoneTypeDiv;
    }
    
    /**
     * 端末類型区分を取得する
     */
    public String getPhoneTypeDiv() {
        return phoneTypeDiv;
    }
    /**
     * デバイスIDを設定する
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
    
    /**
     * デバイスIDを取得する
     */
    public Integer getDeviceId() {
        return deviceId;
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
