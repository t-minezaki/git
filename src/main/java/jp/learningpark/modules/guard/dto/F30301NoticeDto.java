package jp.learningpark.modules.guard.dto;

public class F30301NoticeDto {

    // お知らせID
    Integer id;
    // お知らせ画像パス
    String titleImgPath;
    // お知らせタイトル
    String noticeTitle;
    // お知らせ内容
    String noticeCont;
    // コード値
    String codValue;
    // お知らせレベル区分
    String noticeLevelDiv;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleImgPath() {
        return titleImgPath;
    }

    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    public String getNoticeTitle() {
        return noticeTitle;
    }

    public void setNoticeTitle(String noticeTitle) {
        this.noticeTitle = noticeTitle;
    }

    public String getNoticeCont() {
        return noticeCont;
    }

    public void setNoticeCont(String noticeCont) {
        this.noticeCont = noticeCont;
    }

    public String getCodValue() {
        return codValue;
    }

    public void setCodValue(String codValue) {
        this.codValue = codValue;
    }

    public String getNoticeLevelDiv() {
        return noticeLevelDiv;
    }

    public void setNoticeLevelDiv(String noticeLevelDiv) {
        this.noticeLevelDiv = noticeLevelDiv;
    }
}
