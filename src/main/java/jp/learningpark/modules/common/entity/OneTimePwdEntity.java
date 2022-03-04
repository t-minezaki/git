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
 * ONETIMEパスワード管理
 *
 *@author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/4 : xie: 新規<br />
 * @version 1.0
 * */
@TableName("one_time_pwd")
public class OneTimePwdEntity implements Serializable {
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
    private String afterUsrId;

    /**
     * ユーザログインPW
     */
    @NotNull
    private String mailad;

    /**
     * 初回登録フラグ
     */
    @NotNull
    private String oneTimePw;

    /**
     * ユーザ名
     */
    @NotNull
    private Timestamp cretDatime;

    /**
     * ロール区分
     */
    @NotNull
    private String cretUsrId;

    /**
     * 組織ID
     */
    @NotNull
    private Timestamp updDatime;

    /**
     * PW更新フラグ
     */
    @NotNull
    private String updUsrId;

    /**
     * ユーザステータス
     */
    @NotNull
    private Integer delFlg;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAfterUsrId() {
        return afterUsrId;
    }

    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    public String getMailad() {
        return mailad;
    }

    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    public String getOneTimePw() {
        return oneTimePw;
    }

    public void setOneTimePw(String oneTimePw) {
        this.oneTimePw = oneTimePw;
    }

    public Timestamp getCretDatime() {
        return cretDatime;
    }

    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }

    public String getCretUsrId() {
        return cretUsrId;
    }

    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }

    public Timestamp getUpdDatime() {
        return updDatime;
    }

    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }

    public String getUpdUsrId() {
        return updUsrId;
    }

    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }

    public Integer getDelFlg() {
        return delFlg;
    }

    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
}
