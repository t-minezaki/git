/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.manager.dto.F08011CsvVo;
import jp.learningpark.modules.manager.dto.F08011DspDto;
import jp.learningpark.modules.manager.dto.F08011Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>F08011_配信設定詳細一覧画面 Dao</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/08 : yang: 新規<br />
 * @version 1.0
 */
public interface F08011Dao {
    //配信数
    Integer getInfoAll(@Param("orgId")String orgId,@Param("eventId")Integer eventId);
    //未読数
    Integer getInfoUnread(@Param("orgId")String orgId,@Param("eventId")Integer eventId);
    //既読数
    Integer getInfoRead(@Param("orgId")String orgId,@Param("eventId")Integer eventId);
    //予約数
    Integer getInfoDate(@Param("orgId")String orgId,@Param("eventId")Integer eventId);

    //イベント配信者一覧情報を表示するため
    List<F08011Dto> getDeliverList(@Param("eventId") Integer eventId,
                                   @Param("orgId") String orgId,
                                   @Param("paramsMap") Map<String, String> paramsMap,
                                   @Param("page") Integer page,
                                   @Param("limit") Integer limit);

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    F08011Dto selectDspItems(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    Integer getDspCount(@Param("userId") String userId, @Param("menuId") String menuId);

    /**
     * @param f08011DspDto
     * @return
     */
    Integer insertDspItem(F08011DspDto f08011DspDto);

    /**
     * @param f08011DspDto
     * @return
     */
    Integer updateDspItem(F08011DspDto f08011DspDto);
    F08011Dto getApplyDel(@Param("eventScheDelId")Integer eventScheDelId,@Param("paramsMap") Map<String, String> paramsMap);
    List<MstCodDEntity> getStatus();

    List<F08011CsvVo> selectCsvData(@Param("stuIdList")List<String> stuIdList);
}
