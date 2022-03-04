/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F00044Dto;

import java.util.List;


/**
 * <p>F00044_生徒保護者関係検索一覧画面 Service</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/03/15 : tan: 新規<br />
 * @version 1.0
 */
public interface F00044Service {
    /**
     * 初期化学年区分を取得
     *
     * @return
     */
    List<F00044Dto> getSchySearch();

    /**
     * 生徒基本マスタ、保護者基本マスタを元に一覧情報を取得
     *
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param schy      画面．検索条件．学年
     * @param limit     １ページ最大件数
     * @param page      現在のページ数
     * @param orgIdList     画面  检索条件  組織ID
     * @return
     */
    List<F00044Dto> getSearch(String stuId,
                              String guardId,
                              String stuName,
                              String guardName,
                              String schy,
                              Integer limit,
                              Integer page,
                              List<String> orgIdList);

    /**
     * 総件数をとる
     *
     * @param stuId     画面．検索条件．生徒ID
     * @param guardId   画面．検索条件．保護者ID
     * @param stuName   画面．検索条件．生徒姓名
     * @param guardName 画面．検索条件．保護者姓名
     * @param schy      画面．検索条件．学年
     * @param orgIdList     画面  检索条件  組織ID
     * @return
     */
    Integer getTotalCount(String stuId,
                          String guardId,
                          String stuName,
                          String guardName,
                          String schy,
                          List<String> orgIdList);
}
