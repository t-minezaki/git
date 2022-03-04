package jp.learningpark.modules.manager.dto;

public class F09005DeviceDto {

    /**
     * deviceId.id
     */
    private Integer id;
    /**
     * deviceId.ureadcount
     */
    private Integer unreadcount;
    /**
     * deviceId.stuname
     */
    private String stuname;
    /**
     * 生徒Id
     */
    private String stuId;

    /**
     * deviceId.idを取得する
     *
     * @return id deviceId.id
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * deviceId.idを設定する
     *
     * @param id deviceId.id
     */
    public void setId(Integer id) {
        this.id = id;
    }


    /**
     * deviceId.stunameを取得する
     *
     * @return stuname deviceId.stuname
     */
    public String getStuname() {
        return this.stuname;
    }

    /**
     * deviceId.stunameを設定する
     *
     * @param stuname deviceId.stuname
     */
    public void setStuname(String stuname) {
        this.stuname = stuname;
    }

    /**
     * deviceId.ureadcountを取得する
     *
     * @return unreadcount deviceId.ureadcount
     */
    public Integer getUnreadcount() {
        return this.unreadcount;
    }

    /**
     * deviceId.ureadcountを設定する
     *
     * @param unreadcount deviceId.ureadcount
     */
    public void setUnreadcount(Integer unreadcount) {
        this.unreadcount = unreadcount;
    }

    /**
     * 生徒Idを取得する
     *
     * @return stuId 生徒Id
     */
    public String getStuId() {
        return this.stuId;
    }

    /**
     * 生徒Idを設定する
     *
     * @param stuId 生徒Id
     */
    public void setStuId(String stuId) {
        this.stuId = stuId;
    }
}
