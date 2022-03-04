/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.modules.common.dao.StuFixdSchdlDao;
import jp.learningpark.modules.common.dao.StuIndivSchdlAdjustDao;
import jp.learningpark.modules.common.entity.StuIndivSchdlAdjustEntity;
import jp.learningpark.modules.pop.service.F10102Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>F10102_固定活動登録画面(POP) ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/13 : wen: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F10102ServiceImpl implements F10102Service {
    /**
     * StuFixdSchdlDao 生徒固定スケジュール設定
     */
    @Autowired
    private StuFixdSchdlDao stuFixdSchdlDao;

    /**
     * StuIndivSchdlAdjustDao 生徒個別スケジュール調整
     */
    @Autowired
    private StuIndivSchdlAdjustDao stuIndivSchdlAdjustDao;

    @Override
    public void delBoth(Integer id) {
        stuIndivSchdlAdjustDao.delete(new QueryWrapper<StuIndivSchdlAdjustEntity>().and(wrapper -> wrapper.eq("stu_fixd_schdl_id", id)));
        stuFixdSchdlDao.deleteById(id);
    }
}
