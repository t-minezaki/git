package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.guard.dao.F30410Dao;
import jp.learningpark.modules.guard.dto.F30410Dto;
import jp.learningpark.modules.guard.service.F30410Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>F30410_保護者面談の申込内容キャンセル画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/22: yang: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F30410ServiceImpl implements F30410Service {
    //f30410Dao
    @Autowired
    F30410Dao f30410Dao;

    /**
     * 初期化
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @param refType
     * @return
     */
    public F30410Dto getGuardEventApplySts(Integer eventId, String stuId, String guardId,String refType) {
        return f30410Dao.getGuardEventApplySts(eventId, stuId, guardId, refType);
    }

    /**
     * 申込をキャンセルボタン押下時
     *
     * @param eventId
     * @param stuId
     * @param guardId
     * @return
     */
    public GuardEventApplyStsEntity getDeleteOne(Integer eventId, String stuId, String guardId) {
        return f30410Dao.getDeleteOne(eventId, stuId, guardId);
    }

}
