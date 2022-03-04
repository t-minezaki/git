package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F21012Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 学習連絡確認Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/29 ： NWT)hxl ： 新規作成
 * @date 2019/11/29 17:43
 */
public interface F21012Service {
    /**
     * 編集または作成指導報告書配信管理
     *
     * @param list          生徒ID
     * @param orgId         組織ID
     * @param guidDeliverCD 指導報告書配信コード
     * @param date          出席簿対象日
     * @param startDate     公開開始日時
     * @param endDate       公開終了日時
     * @param statusCD      指導報告書ステータス区分
     * @return
     */
    R saveOrUpdate(List<F21012Dto> list, String orgId, String guidDeliverCD, Date date, Date startDate, Date endDate, String statusCD,String noticeLevelDiv);
}
