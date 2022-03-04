package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.pop.dto.F21072Dto;
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
 * 2020/06/03 ： NWT)hxl ： 新規作成
 * @date 2020/06/03 11:32
 */
@Mapper
public interface F21073Dao {
    /**
     * 全体の送信管理者と先生を抽出
     *
     * @param messageId メッセージＩＤ
     * @return
     */
    List<F21072Dto> getManagerAndMentorList(@Param("messageId") Integer messageId);
}
