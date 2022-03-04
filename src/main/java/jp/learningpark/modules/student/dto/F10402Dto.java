package jp.learningpark.modules.student.dto;

import java.util.List;

/**
 * <p>生徒ウィークリー計画実績設定　+ コードマスタ_明細</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/21 : wen: 新規<br />
 * @version 1.0
 */
public class F10402Dto {

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
    List<F10402SecDto> f10402SecDtoList;

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
     * @return f10402SecDtoList 生徒ウィークリー計画List
     */
    public List<F10402SecDto> getF10402SecDtoList() {
        return this.f10402SecDtoList;
    }

    /**
     * 生徒ウィークリー計画Listを取得する
     *
     * @param f10402SecDtoList 生徒ウィークリー計画List
     */
    public void setF10402SecDtoList(List<F10402SecDto> f10402SecDtoList) {
        this.f10402SecDtoList = f10402SecDtoList;
    }
}
