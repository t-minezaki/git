package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dao.F21005Dao;
import jp.learningpark.modules.manager.dto.F21005Dto;
import jp.learningpark.modules.manager.service.F21005Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * F21005サービス
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/19 ： NWT)hxl ： 新規作成
 * @date 2019/11/19 14:13
 */
@Service
public class F21005ServiceImpl implements F21005Service {
    /**
     * f21005Dao
     */
    @Autowired
    F21005Dao f21005Dao;

    /**
     * <p>欠席の申請記録を取得する</p>
     *
     * @param day    日付
     * @param userId ユーザーID
     * @param orgId  組織ID
     * @param limit  検索数
     * @param page   ページ数
     * @return
     */
    @Override
    public R getLateAbsHistoryByDay(String roleDiv,Date day, String userId, String orgId, Integer limit, Integer page) {
        R info;
        Integer count = f21005Dao.getCount(roleDiv,day, userId, orgId);
        List<F21005Dto> lateAbsList;
        if (count > 0) {
            info = R.ok();
            info.put("count", count);
            lateAbsList = f21005Dao.getLateAbsHistoryByDay(roleDiv,day, userId, orgId, limit, (page - 1) * limit);
            info.put("page", new PageUtils(lateAbsList, count, limit, page));
        } else {
            info = R.error(MessageUtils.getMessage("MSGCOMN0017", "遅刻・欠席連絡"));
        }
        return info;
    }
}
