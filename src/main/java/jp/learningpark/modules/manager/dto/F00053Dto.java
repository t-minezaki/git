/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dto;


import jp.learningpark.modules.common.entity.StuGrpEntity;

/**
 * <p>F00053_生徒グループ関係検索一覧画面 Dto</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/18 : tan: 新規<br />
 * @version 1.0
 */
public class F00053Dto extends StuGrpEntity {
    /**
     * 画面．検索条件．グループ名
     */
    private String tmp;
    /**
     * グループID
     */
    private Integer groupID;
    /**
     * 生徒ID_隠し(生徒グループ管理．生徒ID)
     */
    private String stuIdHidden;


    /**
     * グループIDを取得する
     *
     * @return groupID グループID
     */
    public Integer getGroupID() {
        return this.groupID;
    }

    /**
     * グループIDを設定する
     *
     * @param groupID グループID
     */
    public void setGroupID(Integer groupID) {
        this.groupID = groupID;
    }

    /**
     * 生徒ID_隠し(生徒グループ管理．生徒ID)を取得する
     *
     * @return stuIdHidden 生徒ID_隠し(生徒グループ管理．生徒ID)
     */
    public String getStuIdHidden() {
        return this.stuIdHidden;
    }

    /**
     * 生徒ID_隠し(生徒グループ管理．生徒ID)を設定する
     *
     * @param stuIdHidden 生徒ID_隠し(生徒グループ管理．生徒ID)
     */
    public void setStuIdHidden(String stuIdHidden) {
        this.stuIdHidden = stuIdHidden;
    }

    /**
     * 画面．検索条件．グループ名を取得する
     *
     * @return tmp 画面．検索条件．グループ名
     */
    public String getTmp() {
        return this.tmp;
    }

    /**
     * 画面．検索条件．グループ名を設定する
     *
     * @param tmp 画面．検索条件．グループ名
     */
    public void setTmp(String tmp) {
        this.tmp = tmp;
    }
}
