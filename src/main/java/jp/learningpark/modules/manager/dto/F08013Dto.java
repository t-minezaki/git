/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/08/12 : wq: 新規<br />
 * @version 1.0
 */
public class F08013Dto {
    /**
     * 関連ID
     */
    private String refId;
    /**
     * 関連タイプ
     */
    private String refType;
    /**
     * 表示名
     */
    private String displayNm;

    /**
     * 組織IDを取得する
     *
     * @return refId 組織ID
     */
    public String getRefId() {
        return this.refId;
    }

    /**
     * 組織IDを設定する
     *
     * @param refId 組織ID
     */
    public void setRefId(String refId) {
        this.refId = refId;
    }

    /**
     * 組織名を取得する
     *
     * @return refType 組織名
     */
    public String getRefType() {
        return this.refType;
    }

    /**
     * 組織名を設定する
     *
     * @param refType 組織名
     */
    public void setRefType(String refType) {
        this.refType = refType;
    }

    /**
     * ユーザーIDを取得する
     *
     * @return displayNm ユーザーID
     */
    public String getDisplayNm() {
        return this.displayNm;
    }

    /**
     * ユーザーIDを設定する
     *
     * @param displayNm ユーザーID
     */
    public void setDisplayNm(String displayNm) {
        this.displayNm = displayNm;
    }
}
