/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F21008Dto;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>F21008_出席簿出欠情報入力画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/15 : yang: 新規<br />
 * @version 5.0
 */
public interface F21008Service {
    /**
     * 遅刻欠席連絡履歴から生徒基本情報を取得
     */
    List<F21008Dto> getNewStuList(Date dateStartTime, Date dateEndTime, Integer grpId, String absDiv);

    /**
     * 遅刻欠席連絡履歴から生徒基本情報を取得
     */
    List<F21008Dto> getEditStuList(Date dateStartTime, Date dateEndTime, Integer attendBookId, String absDiv);

    /**
     * ユーザ表示項目設定、或いは、コードマスタより、画面初期化項目を取得し
     */
    F21008Dto selectDspItems(String userId, String menuId);

    /**
     * 選択した対象者の遅刻連絡情報を取得
     *
     * @param dateStartTime
     * @param dateEndTime
     * @param stuIdList
     * @return
     */
    List<F21008Dto> addStu(Date dateStartTime, Date dateEndTime, List<String> stuIdList);

    /**
     * <p>出席簿付与ポイント履歴登録</p>
     * <p>
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    int insertPointHst(Map<String, Object> map);

    /**
     * <p>出席簿付与ポイント履歴更新</p>
     * <p>
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    int updatePointHst(Map<String, Object> map);
}
