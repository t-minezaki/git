/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30105Dao;
import jp.learningpark.modules.guard.dto.F30105Dto;
import jp.learningpark.modules.guard.service.F30105Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>F30105 理解度詳細画面 ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/27 : wen: 新規<br />
 * @version 1.0
 */
@Service
public class F30105ServiceImpl implements F30105Service {

    @Autowired
    F30105Dao f30105Dao;

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
    public List<F30105Dto> getValueByCodcd(String stuId, String subjtDiv, Date startDate, Date endDate) {
        return f30105Dao.selectValueByCodcd(stuId, subjtDiv, startDate, endDate);
    }
}
