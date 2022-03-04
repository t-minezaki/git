package jp.learningpark.modules.pop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.common.entity.TalkReadingStsEntity;
import jp.learningpark.modules.pop.dao.F21057Dao;
import jp.learningpark.modules.pop.dto.F21057Dto;
import jp.learningpark.modules.pop.service.F21057Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
 * @date 2020/06/02 14:13
 */
@Service
public class F21057ServiceImpl implements F21057Service {
    /**
     * f21057Dao
     */
    @Autowired
    F21057Dao f21057Dao;

    /**
     * talkReadingStsDao
     */
    @Autowired
    TalkReadingStsDao talkReadingStsDao;

    /**
     * 塾の連絡通知を取得し
     *
     * @param usrId     セッションデータ．先生ＩＤ OR セッションデータ．管理者ＩＤ
     * @param messageId メッセージID
     * @return
     */
    @Override
    public R getDetail(String usrId, Integer messageId) {
        F21057Dto f21057Dto = f21057Dao.selectDetail(usrId, messageId);
        if (f21057Dto == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "インフォメーション"));
        }
        if (StringUtils.equals(f21057Dto.getReadingStsDiv(), "0")) {
            TalkReadingStsEntity talkReadingStsEntity = talkReadingStsDao.selectOne(new QueryWrapper<TalkReadingStsEntity>().eq("deliver_id", usrId).eq("message_id", messageId).eq("del_flg", 0));
            talkReadingStsEntity.setReadingStsDiv("1");
            talkReadingStsEntity.setUpdUsrId(usrId);
            talkReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            talkReadingStsDao.updateById(talkReadingStsEntity);
        }
        return R.ok().put("message", f21057Dto);
    }
}
