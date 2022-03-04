/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.dao;

import org.apache.ibatis.annotations.Mapper;

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
@Mapper
public interface F30104Dao {

    /**
     * 理解度
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd終了日
     * @return
     */
    List<Map<String, Object>> getDegreeTotal(Map<String, Object> map);

    /**
     * 学習時間
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd終了日
     * @return
     */
    Map<String, Object> getLearnTimeCircleTotal(Map<String, Object> map);

    /**
     * 学習時間
     *
     * @param map stuId 生徒ID startYmd 開始日 endYmd終了日
     * @return
     */
    Map<String, Object> getLearnTimeHorizontalTotal(Map<String, Object> map);
}
