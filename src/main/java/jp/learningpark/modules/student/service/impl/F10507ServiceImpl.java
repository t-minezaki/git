/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.dao.StuTestGoalResultDDao;
import jp.learningpark.modules.common.dao.StuTestGoalResultHDao;
import jp.learningpark.modules.common.entity.StuTestGoalResultHEntity;
import jp.learningpark.modules.student.dao.F10507Dao;
import jp.learningpark.modules.student.form.F10507Form;
import jp.learningpark.modules.student.service.F10507Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>テスト目標結果一覧 Service Impl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/15 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F10507ServiceImpl implements F10507Service {
    /**
     * F10507 Dao
     */
    @Autowired
    F10507Dao f10507Dao;

    /**
     * 生徒テスト目標結果_ヘッダ Dao
     */
    @Autowired
    StuTestGoalResultHDao stuTestGoalResultHDao;

    /**
     * 生徒テスト目標結果_明細 Dao
     */
    @Autowired
    StuTestGoalResultDDao stuTestGoalResultDDao;

    /**
     * <p>生徒テスト目標結果一覧取得</p>
     *
     * @param stuId 生徒ID
     * @return
     */
    @Override
    public List<F10507Form> getGoalResultList(String stuId, Integer startRow) {
        return f10507Dao.selectGoalResultList(stuId, startRow);
    }

    @Override
    public R delete(Integer id, String updateTm) {
        StuTestGoalResultHEntity stuTestGoalResultHEntity = stuTestGoalResultHDao.selectById(id);

        //更新日時判断する
        if (updateTm != null && StringUtils.equals(updateTm, stuTestGoalResultHEntity.getUpdDatime().toString())) {
            //ヘッダと明細がらデータを削除する
            stuTestGoalResultHDao.deleteById(id);
            stuTestGoalResultDDao.deleteById(id);
        }
        return R.ok();
    }

    /**
     * @param stuId    生徒ID
     * @param startRow 開始位置
     * @return
     */
    @Override
    public Integer getGoalResultCount(String stuId, Integer startRow) {
        return f10507Dao.selectGoalResultCount(stuId, startRow);
    }
}
