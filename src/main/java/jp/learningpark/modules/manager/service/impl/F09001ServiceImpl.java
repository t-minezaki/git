package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dao.F09001Dao;
import jp.learningpark.modules.manager.dto.F09001HistoryDto;
import jp.learningpark.modules.manager.service.F09001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

/**
 * <p>
 * F09001サービス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/12 ： NWT)hxl ： 新規作成
 */
@Service
public class F09001ServiceImpl implements F09001Service {
    /**
     * f09001Dao
     */
    @Autowired
    F09001Dao f09001Dao;

    /**
     * <p>ページ数に基づいてレコードを取得する</p>
     *
     * @param orgId 組織ID
     * @param startTime 開始時間範囲
     * @param endTime 終了時間範囲
     * @param limit 検索数
     * @param page ページ数
     * @return
     */
    @Override
    public R getEntryHistory(String orgId, String roleDiv, String userId, Timestamp startTime, Timestamp endTime, Integer limit, Integer page) {
        R info;
        Integer historyCount = f09001Dao.getHistoryCount(orgId, roleDiv, userId, startTime, endTime);
        List<F09001HistoryDto> entryHistoryList;
        if (historyCount > 0) {
            info = R.ok();
            info.put("count", historyCount);
            entryHistoryList = f09001Dao.getEntryHistory(orgId, roleDiv, userId, startTime, endTime, limit, (page - 1) * limit);
            info.put("page", new PageUtils(entryHistoryList, historyCount, limit, page));
        } else {
            info = R.error(MessageUtils.getMessage("MSGCOMN0017", "入退室履歴"));
        }
        return info;
    }

    /**
     * @param orgId 組織ID
     * @param startTime 開始時間範囲
     * @param endTime 終了時間範囲
     * @return
     */
    @Override
    public List<F09001HistoryDto> download(String orgId, String roleDiv, String userId, Timestamp startTime, Timestamp endTime) {
        return f09001Dao.getEntryHistory(orgId, roleDiv, userId, startTime, endTime, null, null);
    }
}
