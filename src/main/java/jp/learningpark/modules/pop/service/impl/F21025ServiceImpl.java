/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.pop.dao.F21025Dao;
import jp.learningpark.modules.pop.dto.F21025Dto;
import jp.learningpark.modules.pop.service.F21025Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>スマホ_連絡確認画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2020/02/27 : zpa: 新規<br />
 * @version 1.0
 */
@Service
public class F21025ServiceImpl implements F21025Service {
    @Autowired
    F21025Dao f21025Dao;

    /**
     * IDで詳細な遅刻欠席連絡情報を取得
     *
     * @param id 遅刻欠席連絡履歴.ID
     * @return
     */

    @Override
    public R getDetail(Integer id) {
        F21025Dto detail = f21025Dao.getDetail(id);
        if (detail != null){
            return R.ok().put("detail", detail);
        }
        return R.error();
    }
}
