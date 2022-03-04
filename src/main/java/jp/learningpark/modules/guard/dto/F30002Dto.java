/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import jp.learningpark.modules.common.entity.MstStuEntity;

/**
 * <p>F30002_子供選択画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/17 : gong: 新規<br />
 * @version 1.0
 */
public class F30002Dto extends MstStuEntity {
    /**
     * 組織ID
     */
    private String orgId;
    /**
     * 組織名
     */
    private String orgNm;
    /**
     * イベント未読件数
     */
    private Integer unEventReadCount;
    /**
     * お知らせ未読件数
     */
    private Integer unNoticeReadCount;
    /**
     * メッセージ未読件数
     */
    private Integer unMessageReadCount;
    /**
     * チャンネル未読件数
     */
    private Integer unChannelReadCount;
    /**
     * 未読件数
     */
    private Integer unReadCount;
    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * 保護者ID
     */
    private String guardId;

    /**
     * 未読件数を取得する
     *
     * @return unReadCount 未読件数
     */
    public Integer getUnReadCount() {
        return unReadCount;
    }

    /**
     * 未読件数を設定する
     *
     * @param unReadCount 未読件数
     */
    public void setUnReadCount(Integer unReadCount) {
        this.unReadCount = unReadCount;
    }

    /**
     * イベント未読件数を取得する
     *
     * @return unEventReadCount イベント未読件数
     */
    public Integer getUnEventReadCount() {
        return unEventReadCount;
    }

    /**
     * イベント未読件数を設定する
     *
     * @param unEventReadCount イベント未読件数
     */
    public void setUnEventReadCount(Integer unEventReadCount) {
        this.unEventReadCount = unEventReadCount;
    }

    /**
     * お知らせ未読件数を取得する
     *
     * @return unNoticeReadCount お知らせ未読件数
     */
    public Integer getUnNoticeReadCount() {
        return unNoticeReadCount;
    }

    /**
     * お知らせ未読件数を設定する
     *
     * @param unNoticeReadCount お知らせ未読件数
     */
    public void setUnNoticeReadCount(Integer unNoticeReadCount) {
        this.unNoticeReadCount = unNoticeReadCount;
    }

    /**
     * メッセージ未読件数を取得する
     *
     * @return unMessageReadCount メッセージ未読件数
     */
    public Integer getUnMessageReadCount() {
        return unMessageReadCount;
    }

    /**
     * メッセージ未読件数を設定する
     *
     * @param unMessageReadCount メッセージ未読件数
     */
    public void setUnMessageReadCount(Integer unMessageReadCount) {
        this.unMessageReadCount = unMessageReadCount;
    }

    /**
     * チャンネル未読件数を取得する
     *
     * @return unChannelReadCount チャンネル未読件数
     */
    public Integer getUnChannelReadCount() {
        return unChannelReadCount;
    }

    /**
     * チャンネル未読件数を設定する
     *
     * @param unChannelReadCount チャンネル未読件数
     */
    public void setUnChannelReadCount(Integer unChannelReadCount) {
        this.unChannelReadCount = unChannelReadCount;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return orgNm;
    }
    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * 組織IDを設定する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを取得する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * 保護者IDを取得する
     *
     * @return guardId 保護者ID
     */
    public String getGuardId() {
        return this.guardId;
    }

    /**
     * 保護者IDを設定する
     *
     * @param guardId 保護者ID
     */
    public void setGuardId(String guardId) {
        this.guardId = guardId;
    }
}
