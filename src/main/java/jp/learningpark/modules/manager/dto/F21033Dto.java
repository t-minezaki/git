package jp.learningpark.modules.manager.dto;

import jp.learningpark.modules.common.entity.MstMessageEntity;

import java.io.Serializable;

public class F21033Dto extends MstMessageEntity implements Serializable {
    /**
     * 閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * ユーザー名
     */
    private String usrNm;

    /**
     * 閲覧状況区分を取得する
     *
     * @return readingStsDiv 閲覧状況区分
     */
    public String getReadingStsDiv() {
        return this.readingStsDiv;
    }

    /**
     * 閲覧状況区分を設定する
     *
     * @param readingStsDiv 閲覧状況区分
     */
    public void setReadingStsDiv(String readingStsDiv) {
        this.readingStsDiv = readingStsDiv;
    }

    /**
     * ユーザー名を取得する
     *
     * @return usrNm ユーザー名
     */
    public String getUsrNm() {
        return this.usrNm;
    }

    /**
     * ユーザー名を設定する
     *
     * @param usrNm ユーザー名
     */
    public void setUsrNm(String usrNm) {
        this.usrNm = usrNm;
    }
}
