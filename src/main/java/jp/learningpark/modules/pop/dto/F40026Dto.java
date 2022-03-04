package jp.learningpark.modules.pop.dto;

/**
 * <p>通知プッシュ失敗一覧画面（共通）</p >
 *
 * @author NWT : yyb <br />
 * 変更履歴 <br />
 * 2020/07/17 : yyb: 新規<br />
 * @version 7.1
 */
public class F40026Dto {
    /**
     * id 通知id
     */
    private Integer id;
    /**
     * noticeTitle テーマを知らせる
     */
    private String title;
    /**
     * pubPlanStartDt 掲載開始日時
     */
    private String pubStartDt;
    /**
     * pubPlanEndDt 掲載終了日時
     */
    private String pubEndDt;

    /**
     * afterUsrId 変更後ユーザーID
     */
    private String afterUsrId;
    /**
     * userName
     */
    private String userName;
    /**
     * orgNm ユーザー名
     */
    private String orgNm;
    /**
     * usrId  ユーザーid
     */
    private String usrId;
    /**
     * deliverId  idを配信する
     */
    private String deliverId;
    /**
     * titleImgPath  主題画像
     */
    private String titleImgPath;
    /**
     * roleDiv  役割区分
     */
    private String roleDiv;
    /**
     * 生徒ID
     */
    private String stuId;
    /**
     * 生徒Name
     */
    private String stuNm;
    /**
     * 生徒変更後ユーザーID
     */
    private String stuAfterId;

    /**
     * デバイスID
     */
    private Integer deviceId;

    /**
     * idを取得する
     *
     * @return id id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * idを設定する
     *
     * @param id id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * noticeTitleを取得する
     *
     * @return noticeTitle noticeTitle
     */
    public String getTitle() {
        return this.title;
    }

    /**
     * noticeTitleを設定する
     *
     * @param noticeTitle noticeTitle
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * pubStartDtを取得する
     *
     * @return pubStartDt pubStartDt
     */
    public String getPubStartDt() {
        return this.pubStartDt;
    }

    /**
     * pubStartDtを設定する
     *
     * @param pubStartDt pubStartDt
     */
    public void setPubStartDt(String pubStartDt) {
        this.pubStartDt = pubStartDt;
    }

    /**
     * pubEndDtを取得する
     *
     * @return pubEndDt pubEndDt
     */
    public String getPubEndDt() {
        return this.pubEndDt;
    }

    /**
     * pubEndDtを設定する
     *
     * @param pubEndDt pubEndDt
     */
    public void setPubEndDt(String pubEndDt) {
        this.pubEndDt = pubEndDt;
    }

    /**
     * afterUsrIdを取得する
     *
     * @return afterUsrId afterUsrId
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * afterUsrIdを設定する
     *
     * @param afterUsrId afterUsrId
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * userNameを取得する
     *
     * @return userName userName
     */
    public String getUserName() {
        return this.userName;
    }

    /**
     * userNameを設定する
     *
     * @param userName userName
     */
    public void setUserName(String userName) {
        this.userName = userName;
    }

    /**
     * orgNmを取得する
     *
     * @return orgNm orgNm
     */
    public String getOrgNm() {
        return this.orgNm;
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
     * usrIdを取得する
     *
     * @return usrId usrId
     */
    public String getUsrId() {
        return this.usrId;
    }

    /**
     * usrIdを設定する
     *
     * @param usrId usrId
     */
    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    /**
     * deliverIdを取得する
     *
     * @return deliverId deliverId
     */
    public String getDeliverId() {
        return this.deliverId;
    }

    /**
     * deliverIdを設定する
     *
     * @param deliverId deliverId
     */
    public void setDeliverId(String deliverId) {
        this.deliverId = deliverId;
    }

    /**
     * titleImgPathを取得する
     *
     * @return titleImgPath titleImgPath
     */
    public String getTitleImgPath() {
        return this.titleImgPath;
    }

    /**
     * titleImgPathを設定する
     *
     * @param titleImgPath titleImgPath
     */
    public void setTitleImgPath(String titleImgPath) {
        this.titleImgPath = titleImgPath;
    }

    /**
     * roleDivを取得する
     *
     * @return roleDiv roleDiv
     */
    public String getRoleDiv() {
        return this.roleDiv;
    }

    /**
     * roleDivを設定する
     *
     * @param roleDiv roleDiv
     */
    public void setRoleDiv(String roleDiv) {
        this.roleDiv = roleDiv;
    }

    /**
     * 生徒IDを取得する
     *
     * @return stuId 生徒ID
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒IDを設定する
     *
     * @param stuId 生徒ID
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }

    /**
     * 生徒Nameを取得する
     *
     * @return stuNm 生徒Name
     */
    public String getStuNm() {
        return this.stuNm;
    }

    /**
     * 生徒Nameを設定する
     *
     * @param stuNm 生徒Name
     */
    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }


    /**
     * 生徒変更後ユーザーIDを取得する
     *
     * @return stuAfterId 生徒変更後ユーザーID
     */
    public String getStuAfterId() {
        return this.stuAfterId;
    }

    /**
     * 生徒変更後ユーザーIDを設定する
     *
     * @param stuAfterId 生徒変更後ユーザーID
     */
    public void setStuAfterId(String stuAfterId) {
        this.stuAfterId = stuAfterId;
    }

    /**
     * デバイスIDを設定する
     *
     * @return deviceId デバイスID
     */
    public Integer getDeviceId() {
        return this.deviceId;
    }

    /**
     * デバイスIDを取得する
     *
     * @param deviceId デバイスID
     */
    public void setDeviceId(Integer deviceId) {
        this.deviceId = deviceId;
    }
}
