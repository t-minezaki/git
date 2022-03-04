/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dto;

import jp.learningpark.modules.common.entity.StuComplimentMstEntity;

/**
 * <p>学習者の進捗一覧画面（デイリー）Dto</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2020/06/03 : yang: 新規<br />
 * @version 6.0
 */
public class F30101stuConplimentDto extends StuComplimentMstEntity {
    /**
     * 褒め
     */
    private String codValue2;

    /**
     * 褒めを取得する
     *
     * @return codValue2 褒め
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * 褒めを設定する
     *
     * @param codValue2 褒め
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }
}
