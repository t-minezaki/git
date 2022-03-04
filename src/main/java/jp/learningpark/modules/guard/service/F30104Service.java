/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import java.util.List;
import java.util.Map;

/**
 * <p>学習状況分析画面（週、月、年）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/27 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F30104Service {

    /**
     * 理解度 棒グラフ
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd 終了日
     * @return
     */
    List<Map<String, Object>> getDegreeTotal(Map<String, Object> map);

    /**
     * 学習時間 円グラフ
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd 終了日
     * @return
     */
    Map<String, Object> getLearnTimeCircleTotal(Map<String, Object> map);

    /**
     * 学習時間 横軸グラフ
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd 終了日
     * @return
     */
    Map<String, Object> getLearnTimeHorizontalTotal(Map<String, Object> map);
}
