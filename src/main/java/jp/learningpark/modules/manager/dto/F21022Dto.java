/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.StuComplimentMstEntity;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : lyh <br />
 * 変更履歴 <br />
 * 2020/02/13 : lyh: 新規<br />
 * @version 1.0
 */
public class F21022Dto extends StuComplimentMstEntity {
    /**
     *コード値２
     */
    private String codValue2;
    /**
     * コメントmstCod
     */
    private String compliment;
    /**
     *保護者ID
     */
    private String guardId;
    /**
     *閲覧状況区分
     */
    private String readingStsDiv;

    /**
     * コード値２を取得する
     *
     * @return codValue2 コード値２
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * コード値２を設定する
     *
     * @param codValue2 コード値２
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
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

    /**
     * 閲覧状況区分を取得する
     *
     * @return readingStsDiv 閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 閲覧状況区分を設定する
     *
     * @param readingStsDiv 閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * コメントmstCodを取得する
     *
     * @return compliment コメントmstCod
     */
    public String getCompliment() {
        return this.compliment;
    }

    /**
     * コメントmstCodを設定する
     *
     * @param compliment コメントmstCod
     */
    public void setCompliment(String compliment) {
        this.compliment = compliment;
    }
}