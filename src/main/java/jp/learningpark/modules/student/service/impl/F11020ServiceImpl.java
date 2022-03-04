package jp.learningpark.modules.student.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.dao.TalkReadingStsDao;
import jp.learningpark.modules.student.dao.F11020Dao;
import jp.learningpark.modules.student.dto.F11020Dto;
import jp.learningpark.modules.student.service.F11020Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
@Service
public class F11020ServiceImpl implements F11020Service {
    /**
     * f11020Dao
     */
    @Autowired
    F11020Dao f11020Dao;
    /**
     * talkReadingStsDao
     */
    @Autowired
    TalkReadingStsDao talkReadingStsDao;

    /**
     * 面談記録を取得し
     *
     * @param messageId メッセージID
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public R getAskAndTalkList(Integer messageId, String stuId, String deliverId) {
        List<F11020Dto> askAndTalkList = f11020Dao.getAskAndTalk(messageId, stuId, deliverId);
        return R.ok().put("askAndTalkList", askAndTalkList);
    }
}
