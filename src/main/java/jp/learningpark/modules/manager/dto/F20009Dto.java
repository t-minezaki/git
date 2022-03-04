/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;


import jp.learningpark.modules.common.entity.StuTestGoalResultDEntity;

import java.util.List;

/**
 * <p>生徒テスト目標結果_明細 Extend + コードマスタ_明細</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/29 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F20009Dto extends StuTestGoalResultDEntity {
    /***
     * 学習理解度
     */
    private String codValue;

    /***
     * 学習理解度色
     */
    private String codValue2;

    /***
     * 生徒ウィークリー計画List
     */
    List<F20009SecDto> f20009SecDtoList;

    /**
     * 学習理解度を設定する
     *
     * @return codValue 学習理解度
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * 学習理解度を取得する
     *
     * @param codValue 学習理解度
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 学習理解度色を設定する
     *
     * @return codValue2 学習理解度色
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * 学習理解度色を取得する
     *
     * @param codValue2 学習理解度色
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }

    /**
     * 生徒ウィークリー計画Listを設定する
     *
     * @return f20009SecDtoList 生徒ウィークリー計画List
     */
    public List<F20009SecDto> getF20009SecDtoList() {
        return this.f20009SecDtoList;
    }

    /**
     * 生徒ウィークリー計画Listを取得する
     *
     * @param f20009SecDtoList 生徒ウィークリー計画List
     */
    public void setF20009SecDtoList(List<F20009SecDto> f20009SecDtoList) {
        this.f20009SecDtoList = f20009SecDtoList;
    }
}
