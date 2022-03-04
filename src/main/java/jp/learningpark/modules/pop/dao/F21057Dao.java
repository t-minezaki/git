package jp.learningpark.modules.pop.dao;

import jp.learningpark.modules.pop.dto.F21057Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/06/02 ： NWT)hxl ： 新規作成
 * @date 2020/06/02 14:12
 */
@Mapper
public interface F21057Dao {
    /**
     * 塾の連絡通知を取得し
     *
     * @param usrId     セッションデータ．先生ＩＤ OR セッションデータ．管理者ＩＤ
     * @param messageId メッセージID
     * @return
     */
    F21057Dto selectDetail(@Param("usrId") String usrId, @Param("messageId") Integer messageId);
}
