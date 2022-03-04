/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/04 : gong: 新規<br />
 * @version 1.0
 */
public class F10003Dto extends TextbDefTimeInfoEntity {

    /***
     * 生徒
     */
    private String stuId;

    /***
     * 塾id
     */
    private String crmschId;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 塾学習期間ID
     */
    private Integer crmschLearnPrdId;

    /***
     * 教科書List
     */
    List<Map<String,String>> chocList;

    /**
     * 　教科アイコン 画像のパス
     */
    private String imgPath;

    /**
     * 教科区分
     */
    private String codValue;

    /**
     * 教科の色
     */
    private String codValue2;

    /**
     * 教科のパス2
     */
    private String codValue3;


    List<F10003TextListDto> f10003TextListDtos;

    /**
     * 計画学習時期ID
     */
    private Integer planLearnSeasnId;

    /**
     * 計画学習時期
     */
    private Integer planLearnSeasn;

    /**
     * 教科書名
     */
    private String textbNm;


    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 更新日時
     */
    private String updateTimeStr;


    /**
     * 教科書名を設定する
     *
     * @return textbNm 教科書名
     */
    public String getTextbNm() {
        return this.textbNm;
    }

    /**
     * 教科書名を取得する
     *
     * @param textbNm 教科書名
     */
    public void setTextbNm(String textbNm) {
        this.textbNm = textbNm;
    }

    /**
     * 学年区分を設定する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を取得する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 計画学習時期IDを設定する
     *
     * @return planLearnSeasnId 計画学習時期ID
     */
    public Integer getPlanLearnSeasnId() {
        return this.planLearnSeasnId;
    }

    /**
     * 計画学習時期IDを取得する
     *
     * @param planLearnSeasnId 計画学習時期ID
     */
    public void setPlanLearnSeasnId(Integer planLearnSeasnId) {
        this.planLearnSeasnId = planLearnSeasnId;
    }

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
     * 　教科アイコン 画像のパスを設定する
     *
     * @return imgPath 　教科アイコン 画像のパス
     */
    public String getImgPath() {
        return this.imgPath;
    }

    /**
     * 　教科アイコン 画像のパスを取得する
     *
     * @param imgPath 　教科アイコン 画像のパス
     */
    public void setImgPath(String imgPath) {
        this.imgPath = imgPath;
    }

    /**
     * 教科区分を設定する
     *
     * @return codValue 教科区分
     */
    public String getCodValue() {
        return this.codValue;
    }

    /**
     * 教科区分を取得する
     *
     * @param codValue 教科区分
     */
    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    /**
     * 教科の色を設定する
     *
     * @return codValue2 教科の色
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * 教科の色を取得する
     *
     * @param codValue2 教科の色
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }

    /**
     * 教科のパス2を設定する
     *
     * @return codValue3 教科のパス2
     */
    public String getCodValue3() {
        return this.codValue3;
    }

    /**
     * 教科のパス2を取得する
     *
     * @param codValue3 教科のパス2
     */
    public void setCodValue3(String codValue3) {
        this.codValue3 = codValue3;
    }

    /**
     * 塾idを設定する
     *
     * @return crmschId 塾id
     */
    public String getCrmschId() {
        return this.crmschId;
    }

    /**
     * 塾idを取得する
     *
     * @param crmschId 塾id
     */
    public void setCrmschId(String crmschId) {
        this.crmschId = crmschId;
    }

    /**
     * を設定する
     *
     * @return f10003TextListDtos
     */
    public List<F10003TextListDto> getF10003TextListDtos() {
        return this.f10003TextListDtos;
    }

    /**
     * を取得する
     *
     * @param f10003TextListDtos
     */
    public void setF10003TextListDtos(List<F10003TextListDto> f10003TextListDtos) {
        this.f10003TextListDtos = f10003TextListDtos;
    }

    /**
     * 生徒を設定する
     *
     * @return stuId 生徒
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒を取得する
     *
     * @param stuId 生徒
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 塾学習期間IDを設定する
     *
     * @return crmschLearnPrdId 塾学習期間ID
     */
    public Integer getCrmschLearnPrdId() {
        return this.crmschLearnPrdId;
    }

    /**
     * 塾学習期間IDを取得する
     *
     * @param crmschLearnPrdId 塾学習期間ID
     */
    public void setCrmschLearnPrdId(Integer crmschLearnPrdId) {
        this.crmschLearnPrdId = crmschLearnPrdId;
    }

    /**
     * 教科書Listを設定する
     *
     * @return chocList 教科書List
     */
    public List<Map<String, String>> getChocList() {
        return this.chocList;
    }

    /**
     * 教科書Listを取得する
     *
     * @param chocList 教科書List
     */
    public void setChocList(List<Map<String, String>> chocList) {
        this.chocList = chocList;
    }

    /**
     * 更新日時を設定する
     *
     * @return updateTimeStr 更新日時
     */
    public String getUpdateTimeStr() {
        return this.updateTimeStr;
    }

    /**
     * 更新日時を取得する
     *
     * @param updateTimeStr 更新日時
     */
    public void setUpdateTimeStr(String updateTimeStr) {
        this.updateTimeStr = updateTimeStr;
    }

    /**
     * planLearnSeasnを取得する
     *
     * @return planLearnSeasn planLearnSeasn
     */
    @Override
    public Integer getPlanLearnSeasn() {
        return planLearnSeasn;
    }

    /**
     * planLearnSeasnを設定する
     *
     * @param planLearnSeasn planLearnSeasn
     */
    @Override
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }
}
