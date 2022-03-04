/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F20009Dto;

import java.util.Date;
import java.util.List;

/**
 * <p>テスト目標結果登録画面 Service</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/29 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F20009Service {
    /**
     * 理解度詳細リスト　Service
     *
     * @param stuId     　生徒ID
     * @param subjtDiv  　教科区分
     * @param startDate 　週/月/年開始日
     * @param endDate   　週/月/年終了日
     */
    List<F20009Dto> getValueByCodcd(String stuId, String subjtDiv, Date startDate, Date endDate);
}
