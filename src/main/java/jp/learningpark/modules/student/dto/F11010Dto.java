/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.dto;

import java.sql.Timestamp;

/**
 * <p>スマホ_メッセージ一覧</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/04/29 : zpa: 新規<br />
 * @version 7.0
 */
public class F11010Dto {

    private Integer id;

    private String title;

    private String typeDiv;

    private String levelDiv;

    private String startDt;

    private  String titleImgPath;

    private String eventCd;

    private String readingStsDiv;

    private Timestamp againSendDt;

    private Boolean message;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTypeDiv() {
        return typeDiv;
    }

    public void setTypeDiv(String typeDiv) {
        this.typeDiv = typeDiv;
    }

    public String getLevelDiv() {
        return levelDiv;
    }

    public void setLevelDiv(String levelDiv) {
        this.levelDiv = levelDiv;
    }

    public String getStartDt() {
        return startDt;
    }

    public void setStartDt(String startDt) {
        this.startDt = startDt;
    }

    public String getTitleImgPath() {
        return titleImgPath;
    }

    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    public String getEventCd() {
        return eventCd;
    }

    public void setEventCd(String eventCd) {
        this.eventCd = eventCd;
    }

    public String getReadingStsDiv() {
        return readingStsDiv;
    }

    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    public Timestamp getAgainSendDt() {
        return againSendDt;
    }

    public void setAgainSendDt(Timestamp againSendDt) {
        this.againSendDt = againSendDt;
    }

    public Boolean getMessage() {
        return message;
    }

    public void setMessage(Boolean message) {
        this.message = message;
    }
}