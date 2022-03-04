package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dao.F21004Dao;
import jp.learningpark.modules.pop.dto.F21004Dto;
import jp.learningpark.modules.pop.service.F21004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * F21004ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/26 ： NWT)hxl ： 新規作成
 * @date 2019/11/26 14:00
 */
@Service
public class F21004ServiceImpl implements F21004Service {
    @Autowired
    F21004Dao f21004Dao;

    /**
     * IDで詳細な遅刻欠席連絡情報を取得
     *
     * @param id 遅刻欠席連絡履歴.ID
     * @return
     */
    @Override
    public R getDetail(Integer id) {
        F21004Dto detail = f21004Dao.getDetail(id);
        if (detail != null){
            return R.ok().put("detail", detail);
        }
        return R.error();
    }
}
