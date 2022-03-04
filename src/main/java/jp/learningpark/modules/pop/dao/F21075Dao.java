/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F21075Dto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>面談記録画面</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/05/25 : wq: 新規<br />
 * @version 1.0
 */
public interface F21075Dao {

    /**
     * @param talkId
     * @param eventId
     * @return
     */
    List<F21075Dto> getTalkItems(@Param("talkId") Integer talkId, @Param("eventId") Integer eventId, @Param("div") String div);

    /**
     * @param trhId
     * @return
     */
    boolean getProxyFlg(@Param("trhId") Integer trhId);
}
