/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.common.entity.MstMaxIdEntity;
import jp.learningpark.modules.manager.dto.F08002Dto;
import jp.learningpark.modules.manager.dto.F08017Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>F08002 イベント新規・編集画面 Dao</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/26 : wq: 新規<br />
 * @version 1.0
 */
public interface F08002Dao {

    /**
     * @param id イベントID
     * @return
     */
    F08002Dto selectInitInfo(Integer id);

    /**
     * @param orgId 　組織ID
     * @return
     */
    MstMaxIdEntity selectMaxId(String orgId);

    /**
     * 質問面談テンプレートから取得データをそのままで表示する
     * @param id        ID
     * @param orgId     組織ID
     * @return
     */
    List<F08017Dto> selectMstAskTalkData(@Param("id")Integer id, @Param("orgId")String orgId, @Param("event")Boolean isEvent);
}
