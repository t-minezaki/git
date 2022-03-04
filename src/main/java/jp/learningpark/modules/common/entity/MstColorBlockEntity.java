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
@TableName("mst_color_block")
public class MstColorBlockEntity implements Serializable {
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
    private String stuId;

    /**
     * $column.comments
     */
    @NotNull
    private String colorId;

    /**
     * $column.comments
     */
    @NotNull
    private String blockTypeDiv;

    /**
     * $column.comments
     */
    private String subjtDiv;

    /**
     * $column.comments
     */
    private Integer blockId;

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
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getStuId() {
        return stuId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setColorId(String colorId) {
        this.colorId = colorId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getColorId() {
        return colorId;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setBlockTypeDiv(String blockTypeDiv) {
        this.blockTypeDiv = blockTypeDiv;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getBlockTypeDiv() {
        return blockTypeDiv;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public String getSubjtDiv() {
        return subjtDiv;
    }
    /**
     * ${column.comments}を設定する
     */
    public void setBlockId(Integer blockId) {
        this.blockId = blockId;
    }
    
    /**
     * ${column.comments}を取得する
     */
    public Integer getBlockId() {
        return blockId;
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
}
