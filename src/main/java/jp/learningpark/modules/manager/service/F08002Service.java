/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F08002AskTalkDto;
import jp.learningpark.modules.manager.dto.F08002Dto;
import jp.learningpark.modules.manager.dto.F08017Dto;

import java.util.List;

/**
 * <p>F08002 イベント新規・編集画面 Service</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/26 : wq: 新規<br />
 * @version 1.0
 */
public interface F08002Service {

    /**
     * @param id イベントID
     * @return
     */
    F08002Dto getInitInfo(Integer id);

    /**
     *
     * @param id        イベントID
     * @param orgId     組織ID
     * @return
     */
    List<F08017Dto> getEventAskList(Integer id, String orgId);

    /**
     *
     * @param id        テンプレートID
     * @param orgId     組織ID
     * @return
     */
    List<F08017Dto> getTemplateAskList(Integer id, String orgId);

    /**
     * @param dto 引渡データ
     * @param eventCont イベント内容
     * @return
     */
    R doInsert(F08002Dto dto, String eventCont, List<F08002AskTalkDto> askTalkList);
}
