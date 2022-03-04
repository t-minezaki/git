/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.StuWeeklyPlanPerfEntity;

/**
 * <p>生徒ウィークリー計画実績設定　+ コードマスタ_明細</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/12 : gong: 新規<br />
 * @version 1.0
 */
public class F20009SecDto extends StuWeeklyPlanPerfEntity {
    /**
     * 章名
     */
    private String chaptNm;

    /***
     * ブロック種類区分
     */
    private String blockNm;

    /**
     * 章名を設定する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return this.chaptNm;
    }

    /**
     * 章名を取得する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * ブロック種類区分を取得する
     *
     * @return
     */
    public String getBlockNm() {
        return blockNm;
    }

    /**
     * ブロック種類区分を設定する
     *
     * @param blockNm
     */
    public void setBlockNm(String blockNm) {
        this.blockNm = blockNm;
    }
}
