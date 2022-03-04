/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F10402Dao;
import jp.learningpark.modules.student.dto.F10402Dto;
import jp.learningpark.modules.student.service.F10402Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * <p>F10402 理解度詳細画面（IPAD） ServiceImpl</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/26 : wen: 新規<br />
 * @version 1.0
 */
@Service
public class F10402ServiceImpl implements F10402Service {

    @Autowired
    F10402Dao f10402Dao;

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
    public List<F10402Dto> getValueByCodcd(String stuId,String subjtDiv, Date startDate, Date endDate) {
        return f10402Dao.selectValueByCodcd(stuId,subjtDiv,startDate,endDate);
    }
}
