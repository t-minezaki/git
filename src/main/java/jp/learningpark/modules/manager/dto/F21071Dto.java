/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstOrgEntity;

public class F21071Dto extends MstOrgEntity {
    /**
     * 総件数
     */
    private Integer countSend;
    /**
     * 既読件数
     */
    private Integer countNotRead;
    /**
     * 未読件数
     */
    private Integer countRead;
    /**
     * 配信先組織
     */
    private String orgIdNm;

    /**
     * 総件数を取得する
     *
     * @return countSend 総件数
     */
    public Integer getCountSend() {
        return this.countSend;
    }

    /**
     * 総件数を設定する
     *
     * @param countSend 総件数
     */
    public void setCountSend(Integer countSend) {
        this.countSend = countSend;
    }

    /**
     * 既読件数を取得する
     *
     * @return countNotRead 既読件数
     */
    public Integer getCountNotRead() {
        return this.countNotRead;
    }

    /**
     * 既読件数を設定する
     *
     * @param countNotRead 既読件数
     */
    public void setCountNotRead(Integer countNotRead) {
        this.countNotRead = countNotRead;
    }

    /**
     * 未読件数を取得する
     *
     * @return countRead 未読件数
     */
    public Integer getCountRead() {
        return this.countRead;
    }

    /**
     * 未読件数を設定する
     *
     * @param countRead 未読件数
     */
    public void setCountRead(Integer countRead) {
        this.countRead = countRead;
    }

    /**
     * 配信先組織を取得する
     *
     * @return orgIdNm 配信先組織
     */
    public String getOrgIdNm() {
        return this.orgIdNm;
    }

    /**
     * 配信先組織を設定する
     *
     * @param orgIdNm 配信先組織
     */
    public void setOrgIdNm(String orgIdNm) {
        this.orgIdNm = orgIdNm;
    }
}
