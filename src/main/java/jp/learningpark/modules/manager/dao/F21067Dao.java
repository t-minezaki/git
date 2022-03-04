package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.pop.dto.F21066Dto;
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
 * 2020/05/22 ： NWT)hxl ： 新規作成
 * @date 2020/05/22 14:13
 */
@Mapper
public interface F21067Dao {
    /**
     * 全体の送信生徒を抽出
     *
     * @param messageId メッセージＩＤ
     * @return
     */
    List<F21066Dto> getStuList(@Param("messageId") Integer messageId);
}
