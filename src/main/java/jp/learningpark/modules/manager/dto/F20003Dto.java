/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import java.util.Date;
import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/07 : gong: 新規<br />
 * @version 1.0
 */
public class F20003Dto {

    /**
     * ID
     */
    private Integer id;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 教科
     */
    private String subjtNm;

    /**
     * 塾ID
     */
    private String crmschId;

    /**
     * 調整倍率
     */
    private String adjustMnfct;

    /**
     * 単元ID
     */
    private Integer unitId;

    /**
     * 計画学習時期ID
     */
    private Integer planLearnSeasnId;

    /**
     * 学習時期開始日
     */
    private Date learnSeasnStartDy;

    /**
     * 学習時期終了日
     */
    private Date learnSeasnEndDy;

    /**
     * 画面．選択マス数
     */
    private Integer cont;

    /**
     * 計画済みマス数
     */
    private Integer planedCount;

    /**
     * 教科書ID
     */
    private String textbId;

    /**
     * 生徒ID:ユーザID
     */
    private String stuId;

    /**
     * 氏名
     */
    private String stuNm;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 学年
     */
    private String schy;

    /**
     * 学校名
     */
    private String schNm;

    /**
     * 教科区分名
     */
    private String subjtDivNm;

    /**
     * 塾学習期間ID
     */
    private Integer crmLearnPrdId;

    /**
     * 計画学習時期
     */
    private Integer planLearnSeasn;

    /**
     * 計画学習時間（分）
     */
    private Integer planLearnTm;

    /**
     * 表示順番
     */
    private Integer dispyOrder;

    /**
     * 生徒タームプラン設定対象フラグ
     */
    private String termFlg;

    /**
     * rowspanの数量
     */
    private Integer rowspanLen;

    /**
     * 枝番(Max)
     */
    private int maxBnum;

    /**
     * 理解度すべて60%Flg、
     */
    private boolean blueFlg;

    /**
     * selesの判定活性
     */
    private boolean selectFlg;

    /**
     * 枝番(Count)
     */
    private int bnumCount;

    /**
     * 教科
     */
    private String subjt;

    /**
     * 学習時期開始日表示
     */
    private String learnSeasnStartDyDisply;

    /**
     * 教科のパス1
     */
    private String codCont;

    /**
     * 教科の色
     */
    private String codValue2;

    /**
     * 教科のパス2
     */
    private String codValue3;

    /**
     * 学校ID
     */
    private String schId;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * ブロック表示名
     */
    private String blockDispyNm;

    /**
     * 教科書デフォルト単元ID
     */
    private Integer textbDefUnitId;

    /**
     * 塾ID
     */
    private String orgId;

    /**
     * 単元NO
     */
    private String unitNo;

    /**
     * 単元名
     */
    private String unitNm;

    /**
     * 教科書ページ
     */
    private String textbPage;

    /**
     * 章NO
     */
    private String chaptNo;

    /**
     * 章名
     */
    private String chaptNm;

    /**
     * 節NO
     */
    private String sectnNo;

    /**
     * 節名
     */
    private String sectnNm;

    /**
     * F20003 理解度と枝番リスト
     */
    private List<F20003BnumLearnLevDto> f20003BnumLearnLevDtoList;

    /**
     * IDを取得する
     *
     * @return id ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * IDを設定する
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 教科区分を取得する
     *
     * @return subjtDiv 教科区分
     */
    public String getSubjtDiv() {
        return this.subjtDiv;
    }

    /**
     * 教科区分を設定する
     *
     * @param subjtDiv 教科区分
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * 教科を取得する
     *
     * @return subjtNm 教科
     */
    public String getSubjtNm() {
        return this.subjtNm;
    }

    /**
     * 教科を設定する
     *
     * @param subjtNm 教科
     */
    public void setSubjtNm(String subjtNm) {
        this.subjtNm = subjtNm;
    }

    /**
     * 塾IDを取得する
     *
     * @return crmschId 塾ID
     */
    public String getCrmschId() {
        return this.crmschId;
    }

    /**
     * 塾IDを設定する
     *
     * @param crmschId 塾ID
     */
    public void setCrmschId(String crmschId) {
        this.crmschId = crmschId;
    }

    /**
     * 調整倍率を取得する
     *
     * @return adjustMnfct 調整倍率
     */
    public String getAdjustMnfct() {
        return this.adjustMnfct;
    }

    /**
     * 調整倍率を設定する
     *
     * @param adjustMnfct 調整倍率
     */
    public void setAdjustMnfct(String adjustMnfct) {
        this.adjustMnfct = adjustMnfct;
    }

    /**
     * 単元IDを取得する
     *
     * @return unitId 単元ID
     */
    public Integer getUnitId() {
        return this.unitId;
    }

    /**
     * 単元IDを設定する
     *
     * @param unitId 単元ID
     */
    public void setUnitId(Integer unitId) {
        this.unitId = unitId;
    }

    /**
     * 計画学習時期IDを取得する
     *
     * @return planLearnSeasnId 計画学習時期ID
     */
    public Integer getPlanLearnSeasnId() {
        return this.planLearnSeasnId;
    }

    /**
     * 計画学習時期IDを設定する
     *
     * @param planLearnSeasnId 計画学習時期ID
     */
    public void setPlanLearnSeasnId(Integer planLearnSeasnId) {
        this.planLearnSeasnId = planLearnSeasnId;
    }

    /**
     * 学習時期開始日を取得する
     *
     * @return learnSeasnStartDy 学習時期開始日
     */
    public Date getLearnSeasnStartDy() {
        return this.learnSeasnStartDy;
    }

    /**
     * 学習時期開始日を設定する
     *
     * @param learnSeasnStartDy 学習時期開始日
     */
    public void setLearnSeasnStartDy(Date learnSeasnStartDy) {
        this.learnSeasnStartDy = learnSeasnStartDy;
    }

    /**
     * 学習時期終了日を取得する
     *
     * @return learnSeasnEndDy 学習時期終了日
     */
    public Date getLearnSeasnEndDy() {
        return this.learnSeasnEndDy;
    }

    /**
     * 学習時期終了日を設定する
     *
     * @param learnSeasnEndDy 学習時期終了日
     */
    public void setLearnSeasnEndDy(Date learnSeasnEndDy) {
        this.learnSeasnEndDy = learnSeasnEndDy;
    }

    /**
     * 画面．選択マス数を取得する
     *
     * @return cont 画面．選択マス数
     */
    public Integer getCont() {
        return this.cont;
    }

    /**
     * 画面．選択マス数を設定する
     *
     * @param cont 画面．選択マス数
     */
    public void setCont(Integer cont) {
        this.cont = cont;
    }

    /**
     * 計画済みマス数を取得する
     *
     * @return planedCount 計画済みマス数
     */
    public Integer getPlanedCount() {
        return this.planedCount;
    }

    /**
     * 計画済みマス数を設定する
     *
     * @param planedCount 計画済みマス数
     */
    public void setPlanedCount(Integer planedCount) {
        this.planedCount = planedCount;
    }

    /**
     * 教科書IDを取得する
     *
     * @return textbId 教科書ID
     */
    public String getTextbId() {
        return this.textbId;
    }

    /**
     * 教科書IDを設定する
     *
     * @param textbId 教科書ID
     */
    public void setTextbId(String textbId) {
        this.textbId = textbId;
    }

    /**
     * 生徒ID:ユーザIDを取得する
     *
     * @return stuId 生徒ID:ユーザID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒ID:ユーザIDを設定する
     *
     * @param stuId 生徒ID:ユーザID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 氏名を取得する
     *
     * @return stuNm 氏名
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 氏名を設定する
     *
     * @param stuNm 氏名
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    /**
     * 学年区分を取得する
     *
     * @return schyDiv 学年区分
     */
    public String getSchyDiv() {
        return this.schyDiv;
    }

    /**
     * 学年区分を設定する
     *
     * @param schyDiv 学年区分
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * 学年を取得する
     *
     * @return schy 学年
     */
    public String getSchy() {
        return this.schy;
    }

    /**
     * 学年を設定する
     *
     * @param schy 学年
     */
    public void setSchy(String schy) {
        this.schy = schy;
    }

    /**
     * 学校名を取得する
     *
     * @return schNm 学校名
     */
    public String getSchNm() {
        return this.schNm;
    }

    /**
     * 学校名を設定する
     *
     * @param schNm 学校名
     */
    public void setSchNm(String schNm) {
        this.schNm = schNm;
    }

    /**
     * 教科区分名を取得する
     *
     * @return subjtDivNm 教科区分名
     */
    public String getSubjtDivNm() {
        return this.subjtDivNm;
    }

    /**
     * 教科区分名を設定する
     *
     * @param subjtDivNm 教科区分名
     */
    public void setSubjtDivNm(String subjtDivNm) {
        this.subjtDivNm = subjtDivNm;
    }

    /**
     * 塾学習期間IDを取得する
     *
     * @return crmLearnPrdId 塾学習期間ID
     */
    public Integer getCrmLearnPrdId() {
        return this.crmLearnPrdId;
    }

    /**
     * 塾学習期間IDを設定する
     *
     * @param crmLearnPrdId 塾学習期間ID
     */
    public void setCrmLearnPrdId(Integer crmLearnPrdId) {
        this.crmLearnPrdId = crmLearnPrdId;
    }

    /**
     * 計画学習時期を取得する
     *
     * @return planLearnSeasn 計画学習時期
     */
    public Integer getPlanLearnSeasn() {
        return this.planLearnSeasn;
    }

    /**
     * 計画学習時期を設定する
     *
     * @param planLearnSeasn 計画学習時期
     */
    public void setPlanLearnSeasn(Integer planLearnSeasn) {
        this.planLearnSeasn = planLearnSeasn;
    }

    /**
     * 計画学習時間（分）を取得する
     *
     * @return planLearnTm 計画学習時間（分）
     */
    public Integer getPlanLearnTm() {
        return this.planLearnTm;
    }

    /**
     * 計画学習時間（分）を設定する
     *
     * @param planLearnTm 計画学習時間（分）
     */
    public void setPlanLearnTm(Integer planLearnTm) {
        this.planLearnTm = planLearnTm;
    }

    /**
     * 表示順番を取得する
     *
     * @return dispyOrder 表示順番
     */
    public Integer getDispyOrder() {
        return this.dispyOrder;
    }

    /**
     * 表示順番を設定する
     *
     * @param dispyOrder 表示順番
     */
    public void setDispyOrder(Integer dispyOrder) {
        this.dispyOrder = dispyOrder;
    }

    /**
     * 生徒タームプラン設定対象フラグを取得する
     *
     * @return termFlg 生徒タームプラン設定対象フラグ
     */
    public String getTermFlg() {
        return this.termFlg;
    }

    /**
     * 生徒タームプラン設定対象フラグを設定する
     *
     * @param termFlg 生徒タームプラン設定対象フラグ
     */
    public void setTermFlg(String termFlg) {
        this.termFlg = termFlg;
    }

    /**
     * rowspanの数量を取得する
     *
     * @return rowspanLen rowspanの数量
     */
    public Integer getRowspanLen() {
        return this.rowspanLen;
    }

    /**
     * rowspanの数量を設定する
     *
     * @param rowspanLen rowspanの数量
     */
    public void setRowspanLen(Integer rowspanLen) {
        this.rowspanLen = rowspanLen;
    }

    /**
     * 枝番(Max)を取得する
     *
     * @return maxBnum 枝番(Max)
     */
    public int getMaxBnum() {
        return this.maxBnum;
    }

    /**
     * 枝番(Max)を設定する
     *
     * @param maxBnum 枝番(Max)
     */
    public void setMaxBnum(int maxBnum) {
        this.maxBnum = maxBnum;
    }

    /**
     * 理解度すべて60%Flg、を取得する
     *
     * @return blueFlg 理解度すべて60%Flg、
     */
    public boolean isBlueFlg() {
        return this.blueFlg;
    }

    /**
     * 理解度すべて60%Flg、を設定する
     *
     * @param blueFlg 理解度すべて60%Flg、
     */
    public void setBlueFlg(boolean blueFlg) {
        this.blueFlg = blueFlg;
    }

    /**
     * selesの判定活性を取得する
     *
     * @return selectFlg selesの判定活性
     */
    public boolean isSelectFlg() {
        return this.selectFlg;
    }

    /**
     * selesの判定活性を設定する
     *
     * @param selectFlg selesの判定活性
     */
    public void setSelectFlg(boolean selectFlg) {
        this.selectFlg = selectFlg;
    }

    /**
     * 枝番(Count)を取得する
     *
     * @return bnumCount 枝番(Count)
     */
    public int getBnumCount() {
        return this.bnumCount;
    }

    /**
     * 枝番(Count)を設定する
     *
     * @param bnumCount 枝番(Count)
     */
    public void setBnumCount(int bnumCount) {
        this.bnumCount = bnumCount;
    }

    /**
     * 教科を取得する
     *
     * @return subjt 教科
     */
    public String getSubjt() {
        return this.subjt;
    }

    /**
     * 教科を設定する
     *
     * @param subjt 教科
     */
    public void setSubjt(String subjt) {
        this.subjt = subjt;
    }

    /**
     * 学習時期開始日表示を取得する
     *
     * @return learnSeasnStartDyDisply 学習時期開始日表示
     */
    public String getLearnSeasnStartDyDisply() {
        return this.learnSeasnStartDyDisply;
    }

    /**
     * 学習時期開始日表示を設定する
     *
     * @param learnSeasnStartDyDisply 学習時期開始日表示
     */
    public void setLearnSeasnStartDyDisply(String learnSeasnStartDyDisply) {
        this.learnSeasnStartDyDisply = learnSeasnStartDyDisply;
    }

    /**
     * 教科のパス1を取得する
     *
     * @return codCont 教科のパス1
     */
    public String getCodCont() {
        return this.codCont;
    }

    /**
     * 教科のパス1を設定する
     *
     * @param codCont 教科のパス1
     */
    public void setCodCont(String codCont) {
        this.codCont = codCont;
    }

    /**
     * 教科の色を取得する
     *
     * @return codValue2 教科の色
     */
    public String getCodValue2() {
        return this.codValue2;
    }

    /**
     * 教科の色を設定する
     *
     * @param codValue2 教科の色
     */
    public void setCodValue2(String codValue2) {
        this.codValue2 = codValue2;
    }

    /**
     * 教科のパス2を取得する
     *
     * @return codValue3 教科のパス2
     */
    public String getCodValue3() {
        return this.codValue3;
    }

    /**
     * 教科のパス2を設定する
     *
     * @param codValue3 教科のパス2
     */
    public void setCodValue3(String codValue3) {
        this.codValue3 = codValue3;
    }

    /**
     * 学校IDを取得する
     *
     * @return schId 学校ID
     */
    public String getSchId() {
        return this.schId;
    }

    /**
     * 学校IDを設定する
     *
     * @param schId 学校ID
     */
    public void setSchId(String schId) {
        this.schId = schId;
    }

    /**
     * 組織名を取得する
     *
     * @return orgNm 組織名
     */
    public String getOrgNm() {
        return this.orgNm;
    }

    /**
     * 組織名を設定する
     *
     * @param orgNm 組織名
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * ブロック表示名を取得する
     *
     * @return blockDispyNm ブロック表示名
     */
    public String getBlockDispyNm() {
        return this.blockDispyNm;
    }

    /**
     * ブロック表示名を設定する
     *
     * @param blockDispyNm ブロック表示名
     */
    public void setBlockDispyNm(String blockDispyNm) {
        this.blockDispyNm = blockDispyNm;
    }

    /**
     * 教科書デフォルト単元IDを取得する
     *
     * @return textbDefUnitId 教科書デフォルト単元ID
     */
    public Integer getTextbDefUnitId() {
        return this.textbDefUnitId;
    }

    /**
     * 教科書デフォルト単元IDを設定する
     *
     * @param textbDefUnitId 教科書デフォルト単元ID
     */
    public void setTextbDefUnitId(Integer textbDefUnitId) {
        this.textbDefUnitId = textbDefUnitId;
    }

    /**
     * 塾IDを取得する
     *
     * @return orgId 塾ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 塾IDを設定する
     *
     * @param orgId 塾ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 単元NOを取得する
     *
     * @return unitNo 単元NO
     */
    public String getUnitNo() {
        return this.unitNo;
    }

    /**
     * 単元NOを設定する
     *
     * @param unitNo 単元NO
     */
    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    /**
     * 単元名を取得する
     *
     * @return unitNm 単元名
     */
    public String getUnitNm() {
        return this.unitNm;
    }

    /**
     * 単元名を設定する
     *
     * @param unitNm 単元名
     */
    public void setUnitNm(String unitNm) {
        this.unitNm = unitNm;
    }

    /**
     * 教科書ページを取得する
     *
     * @return textbPage 教科書ページ
     */
    public String getTextbPage() {
        return this.textbPage;
    }

    /**
     * 教科書ページを設定する
     *
     * @param textbPage 教科書ページ
     */
    public void setTextbPage(String textbPage) {
        this.textbPage = textbPage;
    }

    /**
     * 章NOを取得する
     *
     * @return chaptNo 章NO
     */
    public String getChaptNo() {
        return this.chaptNo;
    }

    /**
     * 章NOを設定する
     *
     * @param chaptNo 章NO
     */
    public void setChaptNo(String chaptNo) {
        this.chaptNo = chaptNo;
    }

    /**
     * 章名を取得する
     *
     * @return chaptNm 章名
     */
    public String getChaptNm() {
        return this.chaptNm;
    }

    /**
     * 章名を設定する
     *
     * @param chaptNm 章名
     */
    public void setChaptNm(String chaptNm) {
        this.chaptNm = chaptNm;
    }

    /**
     * 節NOを取得する
     *
     * @return sectnNo 節NO
     */
    public String getSectnNo() {
        return this.sectnNo;
    }

    /**
     * 節NOを設定する
     *
     * @param sectnNo 節NO
     */
    public void setSectnNo(String sectnNo) {
        this.sectnNo = sectnNo;
    }

    /**
     * 節名を取得する
     *
     * @return sectnNm 節名
     */
    public String getSectnNm() {
        return this.sectnNm;
    }

    /**
     * 節名を設定する
     *
     * @param sectnNm 節名
     */
    public void setSectnNm(String sectnNm) {
        this.sectnNm = sectnNm;
    }

    /**
     * F20003 理解度と枝番リストを取得する
     *
     * @return f20003BnumLearnLevDtoList F20003 理解度と枝番リスト
     */
    public List<F20003BnumLearnLevDto> getF20003BnumLearnLevDtoList() {
        return this.f20003BnumLearnLevDtoList;
    }

    /**
     * F20003 理解度と枝番リストを設定する
     *
     * @param f20003BnumLearnLevDtoList F20003 理解度と枝番リスト
     */
    public void setF20003BnumLearnLevDtoList(List<F20003BnumLearnLevDto> f20003BnumLearnLevDtoList) {
        this.f20003BnumLearnLevDtoList = f20003BnumLearnLevDtoList;
    }
}
