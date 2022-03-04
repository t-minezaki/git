/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

/**
 * <p>レイアウト新規作成。</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/26 : zpa: 新規<br />
 * @version 7.0
 */
public class F11005Dto extends StuWeeklyPlanPerfEntity {
    private String codValue;

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }
}