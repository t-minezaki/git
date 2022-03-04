package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F21008Dao;
import jp.learningpark.modules.manager.dto.F21008Dto;
import jp.learningpark.modules.manager.service.F21008Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>F21008_出席簿出欠情報入力画面 ServiceImpl</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/11/15 : yang: 新規<br />
 * @version 5.0
 */
@Service
public class F21008ServiceImpl implements F21008Service {
    @Autowired
    F21008Dao f21008Dao;

    /**
     * @param dateStartTime
     * @param dateEndTime
     * @param grpId
     * @param absDiv
     * @return
     */
    @Override
    public List<F21008Dto> getNewStuList(Date dateStartTime, Date dateEndTime, Integer grpId, String absDiv) {
        return f21008Dao.getNewStuList(dateStartTime, dateEndTime, grpId, absDiv);
    }

    /**
     * @param dateStartTime
     * @param dateEndTime
     * @param attendBookId
     * @param absDiv
     * @return
     */
    @Override
    public List<F21008Dto> getEditStuList(Date dateStartTime, Date dateEndTime, Integer attendBookId, String absDiv) {
        return f21008Dao.getEditStuList(dateStartTime, dateEndTime, attendBookId, absDiv);
    }

    /**
     * ユーザ表示項目設定、或いは、コードマスタより、画面初期化項目を取得し
     *
     * @param userId
     * @param menuId
     * @return
     */
    @Override
    public F21008Dto selectDspItems(String userId, String menuId) {
        return f21008Dao.selectDspItems(userId, menuId);
    }

    @Override
    public List<F21008Dto> addStu(Date dateStartTime, Date dateEndTime, List<String> stuIdList) {
        return f21008Dao.addStu(dateStartTime, dateEndTime, stuIdList);
    }

    /**
     * <p>出席簿付与ポイント履歴登録</p>
     * <p>
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    @Override
    public int insertPointHst(Map<String, Object> map) {
        return f21008Dao.insertPointHst(map);
    }

    /**
     * <p>出席簿付与ポイント履歴更新</p>
     * <p>
     * add at 2021/08/11 for V9.02 by NWT wen
     *
     * @param map
     * @return
     */
    @Override
    public int updatePointHst(Map<String, Object> map) {
        return f21008Dao.updatePointHst(map);
    }
}
