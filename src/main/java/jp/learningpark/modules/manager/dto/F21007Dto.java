package jp.learningpark.modules.manager.dto;

import java.sql.Date;
import java.util.List;

/**
 * <p>F21007_出席簿一覧画面 Dto</p >
 *
 * @author NWT : LiYuHuan <br />
 * @author NWT文
 * @version 9.0要件変更 2020/11/11
 */
public class F21007Dto {

    /**
     * ID
     */
    private Integer id;

    /**
     * 組織ID
     */
    private String orgId;

    /**
     * 対象年月日
     */
    private Date date;

    /**
     * グループID
     */
    private Integer grpId;

    /**
     * 削除フラグ
     */
    private Integer delFlg;

    /**
     * 出席簿コード
     */
    private String attendBookCd;

    /**
     * 回数
     */
    private Integer timesNum;

    /**
     * グループ名
     */
    private String grpNm;

    /**
     * グループIDリスト
     */
    private List<Integer> grpIdList;

    /**
     * 曜日区分
     */
    private Integer dayweekDiv;

    /**
     * 対象年月日
     */
    private Date tgtYmd;

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
     * 組織IDを取得する
     *
     * @return orgId 組織ID
     */
    public String getOrgId() {
        return this.orgId;
    }

    /**
     * 組織IDを設定する
     *
     * @param orgId 組織ID
     */
    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    /**
     * 対象年月日を取得する
     *
     * @return date 対象年月日
     */
    public Date getDate() {
        return this.date;
    }

    /**
     * 対象年月日を設定する
     *
     * @param date 対象年月日
     */
    public void setDate(Date date) {
        this.date = date;
    }

    /**
     * グループIDを取得する
     *
     * @return grpId グループID
     */
    public Integer getGrpId() {
        return this.grpId;
    }

    /**
     * グループIDを設定する
     *
     * @param grpId グループID
     */
    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }

    /**
     * 削除フラグを取得する
     *
     * @return delFlg 削除フラグ
     */
    public Integer getDelFlg() {
        return this.delFlg;
    }

    /**
     * 削除フラグを設定する
     *
     * @param delFlg 削除フラグ
     */
    public void setDelFlg(Integer delFlg) {
        this.delFlg = delFlg;
    }

    /**
     * 出席簿コードを取得する
     *
     * @return attendBookCd 出席簿コード
     */
    public String getAttendBookCd() {
        return this.attendBookCd;
    }

    /**
     * 出席簿コードを設定する
     *
     * @param attendBookCd 出席簿コード
     */
    public void setAttendBookCd(String attendBookCd) {
        this.attendBookCd = attendBookCd;
    }

    /**
     * 回数を取得する
     *
     * @return timesNum 回数
     */
    public Integer getTimesNum() {
        return this.timesNum;
    }

    /**
     * 回数を設定する
     *
     * @param timesNum 回数
     */
    public void setTimesNum(Integer timesNum) {
        this.timesNum = timesNum;
    }

    /**
     * グループ名を取得する
     *
     * @return grpNm グループ名
     */
    public String getGrpNm() {
        return this.grpNm;
    }

    /**
     * グループ名を設定する
     *
     * @param grpNm グループ名
     */
    public void setGrpNm(String grpNm) {
        this.grpNm = grpNm;
    }

    /**
     * グループIDリストを取得する
     *
     * @return grpIdList グループIDリスト
     */
    public List<Integer> getGrpIdList() {
        return this.grpIdList;
    }

    /**
     * グループIDリストを設定する
     *
     * @param grpIdList グループIDリスト
     */
    public void setGrpIdList(List<Integer> grpIdList) {
        this.grpIdList = grpIdList;
    }

    /**
     * 曜日区分を取得する
     *
     * @return dayweekDiv 曜日区分
     */
    public Integer getDayweekDiv() {
        return this.dayweekDiv;
    }

    /**
     * 曜日区分を設定する
     *
     * @param dayweekDiv 曜日区分
     */
    public void setDayweekDiv(Integer dayweekDiv) {
        this.dayweekDiv = dayweekDiv;
    }

    /**
     * 対象年月日を取得する
     *
     * @return tgtYmd 対象年月日
     */
    public Date getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 対象年月日を設定する
     *
     * @param tgtYmd 対象年月日
     */
    public void setTgtYmd(Date tgtYmd) {
        this.tgtYmd = tgtYmd;
    }
}
