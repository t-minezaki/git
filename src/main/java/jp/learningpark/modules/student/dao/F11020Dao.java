package jp.learningpark.modules.student.dao;

import jp.learningpark.modules.student.dto.F11020Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/14 ： NWT)hxl ： 新規作成
 */
@Mapper
public interface F11020Dao {
    /**
     * 面談記録を取得し、画面で表示される。
     *
     * @param messageId メッセージ．ＩＤ
     * @param stuId 生徒ID
     * @return
     */
    List<F11020Dto> getAskAndTalk(@Param("messageId") Integer messageId, @Param("stuId") String stuId, @Param("deliverId") String deliverId);
}
