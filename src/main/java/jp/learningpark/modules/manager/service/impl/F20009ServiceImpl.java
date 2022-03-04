/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F20009Dao;
import jp.learningpark.modules.manager.dto.F20009Dto;
import jp.learningpark.modules.manager.service.F20009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/29 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F20009ServiceImpl implements F20009Service {
    @Autowired
    F20009Dao f20009Dao;

    /**
     * 理解度詳細リスト　ServiceImpl
     *
     * @param stuId     　生徒ID
     * @param subjtDiv  　教科区分
     * @param startDate 　週/月/年開始日
     * @param endDate   　週/月/年終了日
     * @return
     */
    @Override
    public List<F20009Dto> getValueByCodcd(String stuId, String subjtDiv, Date startDate, Date endDate) {
        return f20009Dao.selectValueByCodcd(stuId,subjtDiv,startDate,endDate);
    }
}
