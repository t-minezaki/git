package jp.learningpark.modules.pop.dto;

import jp.learningpark.modules.common.entity.MstMessageEntity;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/02 ： NWT)hxl ： 新規作成
 * @date 2020/06/02 14:13
 */
public class F21057Dto extends MstMessageEntity {
    /**
     * 閲覧状況区分
     */
    private String readingStsDiv;
    /**
     * ユーザー名
     */
    private String usrNm;
    /**
     * 添付ファイルパス
     */
    private String attachFilePath;

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

    /**
     * 添付ファイルパスを取得する
     *
     * @return attachFilePath 添付ファイルパス
     */
    public String getAttachFilePath() {
        return this.attachFilePath;
    }

    /**
     * 添付ファイルパスを設定する
     *
     * @param attachFilePath 添付ファイルパス
     */
    public void setAttachFilePath(String attachFilePath) {
        this.attachFilePath = attachFilePath;
    }
}
