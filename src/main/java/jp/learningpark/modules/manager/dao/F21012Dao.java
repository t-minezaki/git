package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F21012Dto;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
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
 * 2019/11/28 ： NWT)hxl ： 新規作成
 * @date 2019/11/28 18:29
 */
@Mapper
public interface F21012Dao {
    /**
     * 指導報告書明細、指導報告書ヘーダ、出席簿ヘーダから対象者リスト中の送信対象は指導報告書存在チェック
     *
     * @param date  対象日
     * @param orgId 組織ID
     * @param list  学生IDリスト
     * @return
     */
    List<F21012Dto> getStuIdAndGuiRepId(@Param("date") Date date, @Param("orgId")String orgId, @Param("list")List<F21012Dto> list);

    /**
     * 指導報告書 マスタ管理、保護者指導報告書申請状況から該当報告書の設定済の配信対象を抽出し、リスト化する
     *
     * @param guidDeliverCD セッションデータ．指導報告書配信コード
     * @param orgId         セッションデータ．組織ID
     * @return
     */
    List<F21012Dto> getGuidApplyByDeliverId(@Param("guidDeliverCD")String guidDeliverCD, @Param("orgId")String orgId);
}
