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
@TableName("guard_event_apply_sts")
public class GuardEventApplyStsEntity implements Serializable {
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
     * イベントID
     */
    @NotNull
    private Integer eventId;

    /**
     * 保護者ID
     */
    @NotNull
    private String guardId;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 閲覧状況区分
     */
    @NotNull
    private String readingStsDiv;

    /**
     * 閲覧回答区分
     */
    @NotNull
    private String replyStsDiv;

    /**
     * イベント日程(明細)ID
     */
    private Integer eventScheDelId;

    /**
     * 既読日時
     */
    private Timestamp readTime;

    /**
     * 回答日時
     */
    private Timestamp replyTime;

    /**
     * 代理登録フラグ
     */
    private String proxyFlg;

    /**
     * 再送信日時
     */
    private Timestamp againSendDt;

    /**
     * 質問回答
     */
    private String replyCnt;

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
     * イベントIDを設定する
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }
    
    /**
     * イベントIDを取得する
     */
    public Integer getEventId() {
        return eventId;
    }
    /**
     * 保護者IDを設定する
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }
    
    /**
     * 保護者IDを取得する
     */
    public String getGuardId() {
        return guardId;
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
     * 閲覧状況区分を設定する
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }
    
    /**
     * 閲覧状況区分を取得する
     */
    public String getReadingStsDiv() {
        return readingStsDiv;
    }
    /**
     * 閲覧回答区分を設定する
     */
    public void setReplyStsDiv(String replyStsDiv) {
        this.replyStsDiv = replyStsDiv;
    }
    
    /**
     * 閲覧回答区分を取得する
     */
    public String getReplyStsDiv() {
        return replyStsDiv;
    }
    /**
     * イベント日程(明細)IDを設定する
     */
    public void setEventScheDelId(Integer eventScheDelId) {
        this.eventScheDelId = eventScheDelId;
    }
    
    /**
     * イベント日程(明細)IDを取得する
     */
    public Integer getEventScheDelId() {
        return eventScheDelId;
    }
    /**
     * 既読日時を設定する
     */
    public void setReadTime(Timestamp readTime) {
        this.readTime = readTime;
    }
    
    /**
     * 既読日時を取得する
     */
    public Timestamp getReadTime() {
        return readTime;
    }
    /**
     * 回答日時を設定する
     */
    public void setReplyTime(Timestamp replyTime) {
        this.replyTime = replyTime;
    }
    
    /**
     * 回答日時を取得する
     */
    public Timestamp getReplyTime() {
        return replyTime;
    }
    /**
     * 代理登録フラグを設定する
     */
    public void setProxyFlg(String proxyFlg) {
        this.proxyFlg = proxyFlg;
    }
    
    /**
     * 代理登録フラグを取得する
     */
    public String getProxyFlg() {
        return proxyFlg;
    }
    /**
     * 再送信日時を設定する
     */
    public void setAgainSendDt(Timestamp againSendDt) {
        this.againSendDt = againSendDt;
    }
    
    /**
     * 再送信日時を取得する
     */
    public Timestamp getAgainSendDt() {
        return againSendDt;
    }
    /**
     * 質問回答を設定する
     */
    public void setReplyCnt(String replyCnt) {
        this.replyCnt = replyCnt;
    }
    
    /**
     * 質問回答を取得する
     */
    public String getReplyCnt() {
        return replyCnt;
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
