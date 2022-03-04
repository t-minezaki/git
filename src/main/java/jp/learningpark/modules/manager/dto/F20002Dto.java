/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;

import java.util.List;
import java.util.Map;

/**
 * <p>F20002_生徒基本情報画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/04 : gong: 新規<br />
 * @version 1.0
 */
public class F20002Dto extends TextbDefTimeInfoEntity {

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
     * 更新日時
     */
    private String updateTimeStr;

    /**
     * 塾学習期間ID
     */
    private Integer crmschLearnPrdId;

    /***
     * 教科書List
     */
    List<Map<String,String>> chocList;

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
     * 得意科目
     */
    private List<String> goodSubjt;

    /**
     * 不得意科目
     */
    private List<String> nogoodSubjt;

    /**
     * 得意科目区分
     */
    private String goodSubjtDiv;

    /**
     * 不得意科目区分
     */
    private String nogoodSubjtDiv;

    /**
     * 担当者氏名
     */
    private String resptyNm;

    /**
     * 習い事
     */
    private String studyCont;

    /**
     * 希望職種
     */
    private String hopeJobNm;

    /**
     * 希望大学
     */
    private String hopeUnityNm;

    /**
     * 希望学部学科
     */
    private String hopeLearnSub;

    /**
     * 特記事項
     */
    private String specCont;


    List<F20002TextListDto> f20002TextListDtos;


    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 氏名
     */
    private String stuNm;

    /**
     * 学年
     */
    private String schy;

    /**
     * 学校名
     */
    private String schNm;

    /**
     * 　教科アイコン 画像のパス
     */
    private String imgPath;

    /**
     * 学校ID
     */
    private String schId;
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

    /**
     * 組織名
     */
    private String orgNm;

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
     * 生徒ID:ユーザIDを設定する
     *
     * @return stuId 生徒ID:ユーザID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒ID:ユーザIDを取得する
     *
     * @param stuId 生徒ID:ユーザID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 氏名を設定する
     *
     * @return stuNm 氏名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 氏名を取得する
     *
     * @param stuNm 氏名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 学年を設定する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を取得する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 学校名を設定する
     *
     * @return schNm 学校名
     */
    public String getSchNm() {
        return this.schNm;
    }

    /**
     * 学校名を取得する
     *
     * @param schNm 学校名
     */
    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    /**
     * 学校IDを設定する
     *
     * @return schId 学校ID
     */
    public String getSchId() {
        return this.schId;
    }

    /**
     * 学校IDを取得する
     *
     * @param schId 学校ID
     */
    public void setSchId(String schId) {
        this.schId = schId;
    }

    /**
     * 組織名を設定する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を取得する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
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
     * を設定する
     *
     * @return f20002TextListDtos
     */
    public List<F20002TextListDto> getF20002TextListDtos() {
        return this.f20002TextListDtos;
    }

    /**
     * を取得する
     *
     * @param f20002TextListDtos
     */
    public void setF20002TextListDtos(List<F20002TextListDto> f20002TextListDtos) {
        this.f20002TextListDtos = f20002TextListDtos;
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
     * 計画学習時期を設定する
     *
     * @return planLearnSeasn 計画学習時期
     */
    @Override
    public Integer getPlanLearnSeasn() {
        return this.planLearnSeasn;
    }

    /**
     * 計画学習時期を取得する
     *
     * @param planLearnSeasn 計画学習時期
     */
    @Override
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }

    /**
     * 担当者氏名を取得する
     *
     * @return resptyNm 担当者氏名
     */
    public String getResptyNm() {
        return this.resptyNm;
    }

    /**
     * 担当者氏名を設定する
     *
     * @param resptyNm 担当者氏名
     */
    public void setResptyNm(String resptyNm) {
        this.resptyNm = resptyNm;
    }

    /**
     * 習い事を取得する
     *
     * @return studyCont 習い事
     */
    public String getStudyCont() {
        return this.studyCont;
    }

    /**
     * 習い事を設定する
     *
     * @param studyCont 習い事
     */
    public void setStudyCont(String studyCont) {
        this.studyCont = studyCont;
    }

    /**
     * 希望職種を取得する
     *
     * @return hopeJobNm 希望職種
     */
    public String getHopeJobNm() {
        return this.hopeJobNm;
    }

    /**
     * 希望職種を設定する
     *
     * @param hopeJobNm 希望職種
     */
    public void setHopeJobNm(String hopeJobNm) {
        this.hopeJobNm = hopeJobNm;
    }

    /**
     * 希望大学を取得する
     *
     * @return hopeUnityNm 希望大学
     */
    public String getHopeUnityNm() {
        return this.hopeUnityNm;
    }

    /**
     * 希望大学を設定する
     *
     * @param hopeUnityNm 希望大学
     */
    public void setHopeUnityNm(String hopeUnityNm) {
        this.hopeUnityNm = hopeUnityNm;
    }

    /**
     * 希望学部学科を取得する
     *
     * @return hopeLearnSub 希望学部学科
     */
    public String getHopeLearnSub() {
        return this.hopeLearnSub;
    }

    /**
     * 希望学部学科を設定する
     *
     * @param hopeLearnSub 希望学部学科
     */
    public void setHopeLearnSub(String hopeLearnSub) {
        this.hopeLearnSub = hopeLearnSub;
    }

    /**
     * 特記事項を取得する
     *
     * @return specCont 特記事項
     */
    public String getSpecCont() {
        return this.specCont;
    }

    /**
     * 特記事項を設定する
     *
     * @param specCont 特記事項
     */
    public void setSpecCont(String specCont) {
        this.specCont = specCont;
    }

    /**
     * 得意科目を取得する
     *
     * @return goodSubjt 得意科目
     */
    public List<String> getGoodSubjt() {
        return this.goodSubjt;
    }

    /**
     * 得意科目を設定する
     *
     * @param goodSubjt 得意科目
     */
    public void setGoodSubjt(List<String> goodSubjt) {
        this.goodSubjt = goodSubjt;
    }

    /**
     * 不得意科目を取得する
     *
     * @return nogoodSubjt 不得意科目
     */
    public List<String> getNogoodSubjt() {
        return this.nogoodSubjt;
    }

    /**
     * 不得意科目を設定する
     *
     * @param nogoodSubjt 不得意科目
     */
    public void setNogoodSubjt(List<String> nogoodSubjt) {
        this.nogoodSubjt = nogoodSubjt;
    }

    /**
     * 得意科目区分を取得する
     *
     * @return goodSubjtDiv 得意科目区分
     */
    public String getGoodSubjtDiv() {
        return this.goodSubjtDiv;
    }

    /**
     * 得意科目区分を設定する
     *
     * @param goodSubjtDiv 得意科目区分
     */
    public void setGoodSubjtDiv(String goodSubjtDiv) {
        this.goodSubjtDiv = goodSubjtDiv;
    }

    /**
     * 不得意科目区分を取得する
     *
     * @return nogoodSubjtDiv 不得意科目区分
     */
    public String getNogoodSubjtDiv() {
        return this.nogoodSubjtDiv;
    }

    /**
     * 不得意科目区分を設定する
     *
     * @param nogoodSubjtDiv 不得意科目区分
     */
    public void setNogoodSubjtDiv(String nogoodSubjtDiv) {
        this.nogoodSubjtDiv = nogoodSubjtDiv;
    }
}
