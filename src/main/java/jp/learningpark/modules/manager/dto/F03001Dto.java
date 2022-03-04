/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstCodDEntity;

import java.sql.Timestamp;
import java.util.List;
import java.util.Map;

/**
 * <p>教科書一覧</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/12/26 : hujunjie: 新規<br />
 * @version 1.0
 */
public class F03001Dto extends MstCodDEntity {
    /**
     * 教科書ID
     */
    private Integer textbId;

    /**
     * 学年
     */
    private String schyVal;

    /**
     * 教科名
     */
    private String subjtVal;

    /**
     * 出版社名
     */
    private String publisherVal;

    /**
     * 教科書名
     */
    private String textbNm;

    /**
     * 階層
     */
    private String level;

    /**
     * 組織名
     */
    private String orgNm;

    /**
     * 本組織フラグ
     */
    private Integer orgFlg;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 教科書マスタ更新時間
     */
    private String mtUpdTm;

    /**
     * 教科書マスタ更新時間Timestamp
     */
    private Timestamp mtUpdTmTimestamp;

    /**
     * 学年区分
     */
    private String schyDiv;

    /**
     * 教科区分
     */
    private String subjtDiv;

    /**
     * 出版社区分
     */
    private String publisherDiv;

    /**
     * 更新時間リスト
     */
    private List<F03001TmListDto> tdtiUpdTmList;

    /**
     * 更新時間map
     */
    private List<Map<String, Object>> tdtiUpdTmMap;

    /**
     * textbIdを取得する
     *
     * @return textbId textbId
     */
    public Integer getTextbId() {
        return textbId;
    }

    /**
     * textbIdを設定する
     *
     * @param textbId textbId
     */
    public void setTextbId(Integer textbId) {
        this.textbId = textbId;
    }

    /**
     * schyValを取得する
     *
     * @return schyVal schyVal
     */
    public String getSchyVal() {
        return schyVal;
    }

    /**
     * schyValを設定する
     *
     * @param schyVal schyVal
     */
    public void setSchyVal(String schyVal) {
        this.schyVal = schyVal;
    }

    /**
     * subjtValを取得する
     *
     * @return subjtVal subjtVal
     */
    public String getSubjtVal() {
        return subjtVal;
    }

    /**
     * subjtValを設定する
     *
     * @param subjtVal subjtVal
     */
    public void setSubjtVal(String subjtVal) {
        this.subjtVal = subjtVal;
    }

    /**
     * publisherValを取得する
     *
     * @return publisherVal publisherVal
     */
    public String getPublisherVal() {
        return publisherVal;
    }

    /**
     * publisherValを設定する
     *
     * @param publisherVal publisherVal
     */
    public void setPublisherVal(String publisherVal) {
        this.publisherVal = publisherVal;
    }

    /**
     * textbNmを取得する
     *
     * @return textbNm textbNm
     */
    public String getTextbNm() {
        return textbNm;
    }

    /**
     * textbNmを設定する
     *
     * @param textbNm textbNm
     */
    public void setTextbNm(String textbNm) {
        this.textbNm = textbNm;
    }

    /**
     * levelを取得する
     *
     * @return level level
     */
    public String getLevel() {
        return level;
    }

    /**
     * levelを設定する
     *
     * @param level level
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * orgNmを取得する
     *
     * @return orgNm orgNm
     */
    public String getOrgNm() {
        return orgNm;
    }

    /**
     * orgNmを設定する
     *
     * @param orgNm orgNm
     */
    public void setOrgNm(String orgNm) {
        this.orgNm = orgNm;
    }

    /**
     * orgFlgを取得する
     *
     * @return orgFlg orgFlg
     */
    public Integer getOrgFlg() {
        return orgFlg;
    }

    /**
     * orgFlgを設定する
     *
     * @param orgFlg orgFlg
     */
    public void setOrgFlg(Integer orgFlg) {
        this.orgFlg = orgFlg;
    }

    /**
     * orgIdを取得する
     *
     * @return orgId orgId
     */
    public String getOrgId() {
        return orgId;
    }

    /**
     * orgIdを設定する
     *
     * @param orgId orgId
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * mtUpdTmを取得する
     *
     * @return mtUpdTm mtUpdTm
     */
    public String getMtUpdTm() {
        return mtUpdTm;
    }

    /**
     * mtUpdTmを設定する
     *
     * @param mtUpdTm mtUpdTm
     */
    public void setMtUpdTm(String mtUpdTm) {
        this.mtUpdTm = mtUpdTm;
    }

    /**
     * schyDivを取得する
     *
     * @return schyDiv schyDiv
     */
    public String getSchyDiv() {
        return schyDiv;
    }

    /**
     * schyDivを設定する
     *
     * @param schyDiv schyDiv
     */
    public void setSchyDiv(String schyDiv) {
        this.schyDiv = schyDiv;
    }

    /**
     * subjtDivを取得する
     *
     * @return subjtDiv subjtDiv
     */
    public String getSubjtDiv() {
        return subjtDiv;
    }

    /**
     * subjtDivを設定する
     *
     * @param subjtDiv subjtDiv
     */
    public void setSubjtDiv(String subjtDiv) {
        this.subjtDiv = subjtDiv;
    }

    /**
     * publisherDivを取得する
     *
     * @return publisherDiv publisherDiv
     */
    public String getPublisherDiv() {
        return publisherDiv;
    }

    /**
     * publisherDivを設定する
     *
     * @param publisherDiv publisherDiv
     */
    public void setPublisherDiv(String publisherDiv) {
        this.publisherDiv = publisherDiv;
    }

    /**
     * tdtiUpdTmListを取得する
     *
     * @return tdtiUpdTmList tdtiUpdTmList
     */
    public List<F03001TmListDto> getTdtiUpdTmList() {
        return tdtiUpdTmList;
    }

    /**
     * tdtiUpdTmListを設定する
     *
     * @param tdtiUpdTmList tdtiUpdTmList
     */
    public void setTdtiUpdTmList(List<F03001TmListDto> tdtiUpdTmList) {
        this.tdtiUpdTmList = tdtiUpdTmList;
    }

    /**
     * tdtiUpdTmMapを取得する
     *
     * @return tdtiUpdTmMap tdtiUpdTmMap
     */
    public List<Map<String, Object>> getTdtiUpdTmMap() {
        return tdtiUpdTmMap;
    }

    /**
     * tdtiUpdTmMapを設定する
     *
     * @param tdtiUpdTmMap tdtiUpdTmMap
     */
    public void setTdtiUpdTmMap(List<Map<String, Object>> tdtiUpdTmMap) {
        this.tdtiUpdTmMap = tdtiUpdTmMap;
    }

    /**
     * mtUpdTmTimestampを取得する
     *
     * @return mtUpdTmTimestamp mtUpdTmTimestamp
     */
    public Timestamp mtUpdTmTimestamp() {
        return mtUpdTmTimestamp;
    }

    /**
     * mtUpdTmTimestampを設定する
     *
     * @param mtUpdTmTimestamp mtUpdTmTimestamp
     */
    public void mtUpdTmTimestamp(Timestamp mtUpdTmTimestamp) {
        this.mtUpdTmTimestamp = mtUpdTmTimestamp;
    }
}
