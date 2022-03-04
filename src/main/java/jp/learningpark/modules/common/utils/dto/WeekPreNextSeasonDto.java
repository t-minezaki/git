/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.utils.dto;

import java.util.Date;

/**
 * <p> 前週 今週 次週。</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/12/12 : huangyong: 新規<br />
 * @version 1.0
 */
public class WeekPreNextSeasonDto {

    /**
     * 学習時期ID
     */
    private String seasnId;

    /**
     * 対象日
     */
    private String tgtYmd;

    /**
     * 学習時期開始日
     */
    private Date learnSeasnStartDy;

    /**
     * 学習時期終了日
     */
    private Date learnSeasnEndDy;

    /**
     * 前週学習時期開始日
     */
    private String preWeek;

    /**
     * 次週学習時期開始日
     */
    private String nextWeek;

    /**
     * 学習時期開始日表示
     */
    private String weekDisply;

    /**
     * 前週学習時期開始日表示
     */
    private String preWeekDisply;

    /**
     * 次週学習時期開始日表示
     */
    private String nextWeekDisply;

    /**
     * 学習時期IDを取得する
     *
     * @return seasnId 学習時期ID
     */
    public String getSeasnId() {
        return this.seasnId;
    }

    /**
     * 学習時期IDを設定する
     *
     * @param seasnId 学習時期ID
     */
    public void setSeasnId(String seasnId) {
        this.seasnId = seasnId;
    }

    /**
     * 対象日を取得する
     *
     * @return tgtYmd 対象日
     */
    public String getTgtYmd() {
        return this.tgtYmd;
    }

    /**
     * 対象日を設定する
     *
     * @param tgtYmd 対象日
     */
    public void setTgtYmd(String tgtYmd) {
        this.tgtYmd = tgtYmd;
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
     * 前週学習時期開始日を取得する
     *
     * @return preWeek 前週学習時期開始日
     */
    public String getPreWeek() {
        return this.preWeek;
    }

    /**
     * 前週学習時期開始日を設定する
     *
     * @param preWeek 前週学習時期開始日
     */
    public void setPreWeek(String preWeek) {
        this.preWeek = preWeek;
    }

    /**
     * 次週学習時期開始日を取得する
     *
     * @return nextWeek 次週学習時期開始日
     */
    public String getNextWeek() {
        return this.nextWeek;
    }

    /**
     * 次週学習時期開始日を設定する
     *
     * @param nextWeek 次週学習時期開始日
     */
    public void setNextWeek(String nextWeek) {
        this.nextWeek = nextWeek;
    }

    /**
     * 学習時期開始日表示を取得する
     *
     * @return weekDisply 学習時期開始日表示
     */
    public String getWeekDisply() {
        return this.weekDisply;
    }

    /**
     * 学習時期開始日表示を設定する
     *
     * @param weekDisply 学習時期開始日表示
     */
    public void setWeekDisply(String weekDisply) {
        this.weekDisply = weekDisply;
    }

    /**
     * 前週学習時期開始日表示を取得する
     *
     * @return preWeekDisply 前週学習時期開始日表示
     */
    public String getPreWeekDisply() {
        return this.preWeekDisply;
    }

    /**
     * 前週学習時期開始日表示を設定する
     *
     * @param preWeekDisply 前週学習時期開始日表示
     */
    public void setPreWeekDisply(String preWeekDisply) {
        this.preWeekDisply = preWeekDisply;
    }

    /**
     * 次週学習時期開始日表示を取得する
     *
     * @return nextWeekDisply 次週学習時期開始日表示
     */
    public String getNextWeekDisply() {
        return this.nextWeekDisply;
    }

    /**
     * 次週学習時期開始日表示を設定する
     *
     * @param nextWeekDisply 次週学習時期開始日表示
     */
    public void setNextWeekDisply(String nextWeekDisply) {
        this.nextWeekDisply = nextWeekDisply;
    }
}
