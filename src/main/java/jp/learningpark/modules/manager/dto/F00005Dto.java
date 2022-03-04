/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;

/**
 * <p>F00005_生徒グループ管理画面 Dto</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/27 : gong: 新規<br />
 * @version 1.0
 */
public class F00005Dto {

    /**
     * ID
     */
    private Integer id;

    /**
     * 変更後ユーザーID
     */
    private String afterUsrId;

    /**
     * グループID
     */
    private Integer grpId;

    /**
     * IDを設定する
     *
     * @return id ID
     */
    public Integer getId() {
        return this.id;
    }

    /**
     * IDを取得する
     *
     * @param id ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 変更後ユーザーIDを設定する
     *
     * @return afterUsrId 変更後ユーザーID
     */
    public String getAfterUsrId() {
        return this.afterUsrId;
    }

    /**
     * 変更後ユーザーIDを取得する
     *
     * @param afterUsrId 変更後ユーザーID
     */
    public void setAfterUsrId(String afterUsrId) {
        this.afterUsrId = afterUsrId;
    }

    /**
     * グループIDを設定する
     *
     * @return grpId グループID
     */
    public Integer getGrpId() {
        return this.grpId;
    }

    /**
     * グループIDを取得する
     *
     * @param grpId グループID
     */
    public void setGrpId(Integer grpId) {
        this.grpId = grpId;
    }
}
