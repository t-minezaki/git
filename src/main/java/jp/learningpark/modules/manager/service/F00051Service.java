/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F00051Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F00051 グループ検索一覧画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/18: wen: 新規<br />
 * @version 1.0
 */
public interface F00051Service {

    /**
     * <p>生徒テスト目標結果一覧取得</p>
     *
     * @param orgIdList    組織ＩＤリステ
     * @param startRow 開始位置
     * @param grpNm    グループ名
     * @return
     */
    List<F00051Dto> getGrpList(@Param("orgIdList") List<String> orgIdList, @Param("grpNm") String grpNm, @Param("startRow") Integer startRow);

    /**
     * <p>生徒テスト目標結果total</p>
     *
     * @param orgId    組織ＩＤ
     * @param grpNm    グループ名
     * @return
     */
    Integer getGrpListCount(@Param("orgId") String orgId, @Param("grpNm") String grpNm);

    /**
     * @param grpId    グループID
     * @param updateTm 更新時間
     * @return
     */
    R delete(Integer grpId, String updateTm);
}
