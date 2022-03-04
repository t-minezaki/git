package jp.learningpark.modules.guard.dto;

import java.util.List;

/**
 * 生徒教科書の登録
 */
public class F30105Dto {

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
    List<F30105SecDto> f30105SecDtoList;

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
     * @return f30105SecDtoList 生徒ウィークリー計画List
     */
    public List<F30105SecDto> getF30105SecDtoList() {
        return this.f30105SecDtoList;
    }

    /**
     * 生徒ウィークリー計画Listを取得する
     *
     * @param f30105SecDtoList 生徒ウィークリー計画List
     */
    public void setF30105SecDtoList(List<F30105SecDto> f30105SecDtoList) {
        this.f30105SecDtoList = f30105SecDtoList;
    }
}
