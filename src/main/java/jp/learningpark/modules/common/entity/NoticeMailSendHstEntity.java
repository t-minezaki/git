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
 * 指導報告書明細
 *
 * @author NWT
 * @email  lwteam@sinways.com.cn
 * @date   2018/10/18
 */
@TableName("notice_mail_send_hst")
public class NoticeMailSendHstEntity implements Serializable {
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
     * お知らせID
     */
    @NotNull
    private Integer noticeId;

    /**
     * カテゴリ
     */
    @NotNull
    private String ctgyNm;

    /**
     * 生徒ID
     */
    @NotNull
    private String stuId;

    /**
     * 保護者ID
     */
    @NotNull
    private String guardId;

    /**
     * メールアドレス
     */
    @NotNull
    private String mailad;

    /**
     * メールタイトル
     */
    @NotNull
    private String mailTitle;

    /**
     * メール内容
     */
    @NotNull
    private String mailCnt;

    /**
     * ステータス
     */
    @NotNull
    private String status;

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
     * お知らせIDを設定する
     */
    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }
    
    /**
     * お知らせIDを取得する
     */
    public Integer getNoticeId() {
        return noticeId;
    }
    /**
     * カテゴリを設定する
     */
    public void setCtgyNm(String ctgyNm) {
        this.ctgyNm = ctgyNm;
    }
    
    /**
     * カテゴリを取得する
     */
    public String getCtgyNm() {
        return ctgyNm;
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
     * メールアドレスを設定する
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }
    
    /**
     * メールアドレスを取得する
     */
    public String getMailad() {
        return mailad;
    }
    /**
     * メールタイトルを設定する
     */
    public void setMailTitle(String mailTitle) {
        this.mailTitle = mailTitle;
    }
    
    /**
     * メールタイトルを取得する
     */
    public String getMailTitle() {
        return mailTitle;
    }
    /**
     * メール内容を設定する
     */
    public void setMailCnt(String mailCnt) {
        this.mailCnt = mailCnt;
    }
    
    /**
     * メール内容を取得する
     */
    public String getMailCnt() {
        return mailCnt;
    }
    /**
     * ステータスを設定する
     */
    public void setStatus(String status) {
        this.status = status;
    }
    
    /**
     * ステータスを取得する
     */
    public String getStatus() {
        return status;
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
