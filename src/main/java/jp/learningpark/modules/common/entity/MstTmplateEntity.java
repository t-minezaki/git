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
 * ${comments}
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_tmplate")
public class MstTmplateEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * $column.comments
     */
    @TableId(type = IdType.AUTO)
    @NotNull
    private Integer id;

    /**
     * $column.comments
     */
    @NotNull
    private String orgId;

    /**
     * $column.comments
     */
    @NotNull
    private String tmplateTitle;

    /**
     * $column.comments
     */
    @NotNull
    private String tmplateCnt;

    /**
     * $column.comments
     */
    @NotNull
    private String ctgyNm;

    /**
     * $column.comments
     */
    private String tmplateTypeDiv;

    /**
     * $column.comments
     */
    private String attachFilePath;

    /**
     * $column.comments
     */
    private Timestamp cretDatime;

    /**
     * $column.comments
     */
    private String cretUsrId;

    /**
     * $column.comments
     */
    private Timestamp updDatime;

    /**
     * $column.comments
     */
    private String updUsrId;

    /**
     * $column.comments
     */
    private Integer delFlg;

    /**
     * $column.comments
     */
    @NotNull
    private String tmplateCd;

    /**
     * $column.comments
     */
    private String titleImgPath;


    /**
     * ${column.comments}を設定する
     */
    public void setId(Integer id) {
        this.id = id;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getId() {
        return id;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getOrgId() {
        return orgId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setTmplateTitle(String tmplateTitle) {
        this.tmplateTitle = tmplateTitle;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getTmplateTitle() {
        return tmplateTitle;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setTmplateCnt(String tmplateCnt) {
        this.tmplateCnt = tmplateCnt;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getTmplateCnt() {
        return tmplateCnt;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getCtgyNm() {
        return ctgyNm;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setTmplateTypeDiv(String tmplateTypeDiv) {
        this.tmplateTypeDiv = tmplateTypeDiv;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getTmplateTypeDiv() {
        return tmplateTypeDiv;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getAttachFilePath() {
        return attachFilePath;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setCretDatime(Timestamp cretDatime) {
        this.cretDatime = cretDatime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Timestamp getCretDatime() {
        return cretDatime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setCretUsrId(String cretUsrId) {
        this.cretUsrId = cretUsrId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getCretUsrId() {
        return cretUsrId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setUpdDatime(Timestamp updDatime) {
        this.updDatime = updDatime;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Timestamp getUpdDatime() {
        return updDatime;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setUpdUsrId(String updUsrId) {
        this.updUsrId = updUsrId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getUpdUsrId() {
        return updUsrId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getDelFlg() {
        return delFlg;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setTmplateCd(String tmplateCd) {
        this.tmplateCd = tmplateCd;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getTmplateCd() {
        return tmplateCd;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getTitleImgPath() {
        return titleImgPath;
    }
}
