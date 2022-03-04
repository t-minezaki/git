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
 * 質問
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("mst_qn")
public class MstQnEntity implements Serializable {
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
     * 利用区分
     */
    @NotNull
    private String usedDiv;

    /**
     * 関連ID
     */
    @NotNull
    private Integer refId;

    /**
     * ソート
     */
    @NotNull
    private Integer sort;

    /**
     * 質問名
     */
    @NotNull
    private String qaNm;

    /**
     * 質問内容
     */
    @NotNull
    private String qaCnt;

    /**
     * 回答タイプ
     */
    @NotNull
    private String replyType;

    /**
     * 選択肢
     */
    private String qaChoices;

    /**
     * 回答必須フラグ
     */
    @NotNull
    private String replayMustFlg;

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
     * 利用区分を設定する
     */
    public void setUsedDiv(String usedDiv) {
        this.usedDiv = usedDiv;
    }
    
    /**
     * 利用区分を取得する
     */
    public String getUsedDiv() {
        return usedDiv;
    }
    /**
     * 関連IDを設定する
     */
    public void setRefId(Integer refId) {
        this.refId = refId;
    }
    
    /**
     * 関連IDを取得する
     */
    public Integer getRefId() {
        return refId;
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
     * 質問名を設定する
     */
    public void setQaNm(String qaNm) {
        this.qaNm = qaNm;
    }
    
    /**
     * 質問名を取得する
     */
    public String getQaNm() {
        return qaNm;
    }
    /**
     * 質問内容を設定する
     */
    public void setQaCnt(String qaCnt) {
        this.qaCnt = qaCnt;
    }
    
    /**
     * 質問内容を取得する
     */
    public String getQaCnt() {
        return qaCnt;
    }
    /**
     * 回答タイプを設定する
     */
    public void setReplyType(String replyType) {
        this.replyType = replyType;
    }
    
    /**
     * 回答タイプを取得する
     */
    public String getReplyType() {
        return replyType;
    }
    /**
     * 選択肢を設定する
     */
    public void setQaChoices(String qaChoices) {
        this.qaChoices = qaChoices;
    }
    
    /**
     * 選択肢を取得する
     */
    public String getQaChoices() {
        return qaChoices;
    }
    /**
     * 回答必須フラグを設定する
     */
    public void setReplayMustFlg(String replayMustFlg) {
        this.replayMustFlg = replayMustFlg;
    }
    
    /**
     * 回答必須フラグを取得する
     */
    public String getReplayMustFlg() {
        return replayMustFlg;
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
