/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F08011DspDto;
import jp.learningpark.modules.manager.dto.F08011Dto;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F08011 配信設定詳細一覧画面 Service</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/08 : yang: 新規<br />
 * @version 1.0
 */
public interface F08011Service {
    /**
     * @param userId ユーザーID
     * @param menuId 画面ID
     * @return
     */
    F08011Dto selectDspItem(String userId, String menuId);
    Integer getDspCount(String userId, String menuId);

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
    //イベント配信者一覧情報を表示するため
    List<F08011Dto> getDeliverList(Integer eventId,String orgId, Map<String,String> paramsMap, Integer page, Integer limit);

    R getCsvFileName(List<String> stuIdList);

    /**
     * <p>ファイルを作成する。</p>
     *
     * add at 2021/08/16 for V9.02 by NWT wen
     * @param stuList
     * @return
     */
    String getFile(List<LinkedHashMap<String, Object>> stuList);
}
