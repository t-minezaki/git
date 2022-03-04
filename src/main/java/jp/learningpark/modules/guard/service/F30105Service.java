/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service;

import jp.learningpark.modules.guard.dto.F30105Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>F30105 理解度詳細画面 Service</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/11/27 : wen: 新規<br />
 * @version 1.0
 */
public interface F30105Service {


    /**
     * 理解度詳細リスト　Service
     *
     * @param stuId     　生徒ID
     * @param subjtDiv  　教科区分
     * @param startDate 　週/月/年開始日
     * @param endDate   　週/月/年終了日
     */
    List<F30105Dto> getValueByCodcd(String stuId, String subjtDiv, Date startDate, Date endDate);
}
