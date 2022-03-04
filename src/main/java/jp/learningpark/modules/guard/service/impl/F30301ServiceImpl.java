package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30301Dao;
import jp.learningpark.modules.guard.service.F30301Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>F30301_トップニュース一覧画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/02/21 : xiong: 新規<br />
 * @version 1.0
 */

@Service
public class F30301ServiceImpl implements F30301Service {

    @Autowired
    F30301Dao f30301Dao;

    /**
     * お知らせ未読カウント数
     * @param guardId
     * @param stuId
     * @param orgId
     * @return
     */
    @Override
    public Integer getNewsCount(String guardId, String stuId,String orgId) {
        return f30301Dao.getNewsCount(guardId,stuId,orgId);
    }

}
