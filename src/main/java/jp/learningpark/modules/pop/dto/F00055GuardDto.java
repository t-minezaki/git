package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstUsrEntity;

/**
 * <p>F00055 ユーザー選択画面（POP）GuardDto</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/20 : wen: 新規<br />
 * @version 1.0
 */
public class F00055GuardDto extends MstUsrEntity {

    /**
     * 保護者ID
     */
    private String guardId;
    /**
     * 保護者名前
     */
    private String guardNm;
    /**
     * メールアドレス
     */
    private String mailad;
    /**
     * 続柄
     */
    private String reltnspDiv;

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
     * 保護者名前を取得する
     *
     * @return guardNm 保護者名前
     */
    public String getGuardNm() {
        return this.guardNm;
    }

    /**
     * 保護者名前を設定する
     *
     * @param guardNm 保護者名前
     */
    public void setGuardNm(String guardNm) {
        this.guardNm = guardNm;
    }

    /**
     * メールアドレスを取得する
     *
     * @return mailad メールアドレス
     */
    public String getMailad() {
        return this.mailad;
    }

    /**
     * メールアドレスを設定する
     *
     * @param mailad メールアドレス
     */
    public void setMailad(String mailad) {
        this.mailad = mailad;
    }

    /**
     * 続柄を取得する
     *
     * @return reltnspDiv 続柄
     */
    public String getReltnspDiv() {
        return this.reltnspDiv;
    }

    /**
     * 続柄を設定する
     *
     * @param reltnspDiv 続柄
     */
    public void setReltnspDiv(String reltnspDiv) {
        this.reltnspDiv = reltnspDiv;
    }
}
