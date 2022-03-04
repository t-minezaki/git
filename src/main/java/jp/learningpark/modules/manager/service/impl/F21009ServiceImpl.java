package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21009Dao;
import jp.learningpark.modules.manager.dto.F21009Dto;
import jp.learningpark.modules.manager.service.F21009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>F21009_出席簿指導内容情報画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/26 : yang: 新規<br />
 * @version 5.0
 */
@Service
public class F21009ServiceImpl implements F21009Service {
    /**
     * 出席簿指導内容情報画面 Dao
     */
    @Autowired
    F21009Dao f21009Dao;

    /**
     * 指導報告書情報を画面に表示
     * @param attendBookCd
     * @param orgId
     * @return
     */
    @Override
    public List<F21009Dto> getGrdData(String attendBookCd, String orgId,String absDiv) {
        return f21009Dao.getGrdData(attendBookCd, orgId,absDiv);
    }

    /**
     * 出席簿明細から出席簿情報を取得
     * @param attendBookCd
     * @param orgId
     * @return
     */
    @Override
    public List<F21009Dto> getAbhData(String attendBookCd, String orgId,String absDiv) {
        return f21009Dao.getAbhData(attendBookCd, orgId,absDiv);
    }

}
