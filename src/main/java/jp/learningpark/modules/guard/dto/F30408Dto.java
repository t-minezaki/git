package jp.learningpark.modules.guard.dto;

/**
 * <p>保護者面談の結果表示</p >
 */
public class F30408Dto {

    /**
     * 保護者イベント申込状況．ＩＤ
     */
    private Integer applyId;

    /**
     * 保護者イベント申込状況．イベントＩＤ
     */
    private Integer eventId;

    /**
     * 画面．イベントＩＤ
     *
     * @return eventId 画面．イベントＩＤ
     */
    public Integer getEventId() {
        return eventId;
    }

    /**
     * 画面．イベントＩＤ
     *
     * @param eventId 画面．イベントＩＤ
     */
    public void setEventId(Integer eventId) {
        this.eventId = eventId;
    }

    /**
     * 保護者イベント申込状況．ＩＤを取得する
     *
     * @return applyId 保護者イベント申込状況．ＩＤ
     */
    public Integer getApplyId() {
        return this.applyId;
    }

    /**
     * 保護者イベント申込状況．ＩＤを設定する
     *
     * @param applyId 保護者イベント申込状況．ＩＤ
     */
    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
}
