package jp.learningpark.modules.manager.dto;

/**
 * <p>ポイント設定確認画面 生徒 Dto</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
public class F09011StuDto {

    /**
     * ユーザーＩＤ
     */
    private String usrId;

    /**
     * 変更後ユーザーＩＤ
     */
    private String afterUsrId;

    /**
     * コード値（学年）
     */
    private String schy;

    /**
     * 生徒基本マスタ．姓名
     */
    private String stuNm;

    /**
     * 排他
     */
    private String updateTimeFormat;

    public String getUsrId() {
        return usrId;
    }

    public void setUsrId(String usrId) {
        this.usrId = usrId;
    }

    public String getAfterUsrId() {
        return afterUsrId;
    }

    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    public String getSchy() {
        return schy;
    }

    public void setSchy(String schy) {
        this.schy = schy;
    }

    public String getStuNm() {
        return stuNm;
    }

    public void setStuNm(String stuNm) {
        this.stuNm = stuNm;
    }

    public String getUpdateTimeFormat() {
        return updateTimeFormat;
    }

    public void setUpdateTimeFormat(String updateTimeFormat) {
        this.updateTimeFormat = updateTimeFormat;
    }
}
