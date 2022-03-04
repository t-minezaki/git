/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21008Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>F21008_出席簿出欠情報入力画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/15 : yang: 新規<br />
 * @version 5.0
 */
@Mapper
public interface F21008Dao {
    /**
     * @param dateStartTime
     * @param dateEndTime
     * @param grpId
     * @param absDiv
     * @return
     */
    List<F21008Dto> getNewStuList(
            @Param("dateStartTime") Date dateStartTime, @Param("dateEndTime") Date dateEndTime, @Param("grpId") Integer grpId, @Param("absDiv") String absDiv);

    /**
     * @param dateStartTime
     * @param dateEndTime
     * @param attendBookId
     * @param absDiv
     * @return
     */
    List<F21008Dto> getEditStuList(
            @Param("dateStartTime") Date dateStartTime,
            @Param("dateEndTime") Date dateEndTime, @Param("attendBookId") Integer attendBookId, @Param("absDiv") String absDiv);

    /**
     * ユーザ表示項目設定、或いは、コードマスタより、画面初期化項目を取得し
     */
    F21008Dto selectDspItems(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     * @param dateStartTime
     * @param dateEndTime
     * @param stuIdList
     * @return
     */
    List<F21008Dto> addStu(@Param("dateStartTime") Date dateStartTime, @Param("dateEndTime") Date dateEndTime, @Param("stuIdList") List<String> stuIdList);

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
