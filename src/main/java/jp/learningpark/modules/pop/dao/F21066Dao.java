package jp.learningpark.modules.pop.dao;

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
 * @date 2020/05/22 11:56
 */
@Mapper
public interface F21066Dao {
    /**
     *
     * 組織情報、面談メッセージ閲覧状況より、取得する。
     *
     * @param messageId メッセージID
     * @param orgId     セッションデータ．組織ＩＤ
     * @param readFlg   既読未読フラグ
     * @param pageSize  1ページのMAX件数30件
     * @return
     */
    List<F21066Dto> selectStuById(@Param("messageId") Integer messageId, @Param("orgId") String orgId, @Param("pageSize") Integer pageSize
            , @Param("readFlg") String readFlg, @Param("limit") Integer limit);

    /**
     * count
     *
     * @param messageId メッセージID
     * @param orgId     セッションデータ．組織ＩＤ
     * @param readFlg   既読未読フラグ
     * @return
     */
    Integer selectStuCount(@Param("messageId") Integer messageId, @Param("orgId") String orgId
            , @Param("readFlg") String readFlg);
}
