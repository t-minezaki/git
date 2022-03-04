package jp.learningpark.modules.job.entity;

import jp.learningpark.modules.common.entity.MstUsrEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/12/21 ： NWT)hxl ： 新規作成
 * @date 2020/12/21 11:26
 */
public class BTGKA1004StudentDto extends MstUsrEntity {
    /**
     * 生徒基本マスタ．会員コード
     */
    private String memberCd;

    /**
     * 生徒基本マスタ．会員コードを取得する
     *
     * @return memberCd 生徒基本マスタ．会員コード
     */
    public String getMemberCd() {
        return this.memberCd;
    }

    /**
     * 生徒基本マスタ．会員コードを設定する
     *
     * @param memberCd 生徒基本マスタ．会員コード
     */
    public void setMemberCd(String memberCd) {
        this.memberCd = memberCd;
    }
}
