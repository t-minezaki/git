/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F00062Dto;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F00062Service {
    /**
     * 生徒、メンター一覧情報を取得
     *
     * @param id Id
     * @return
     */
    F00062Dto getInitCont(Integer id);

    /**
     * メンター一覧情報を取得
     *
     * @param mentorId メンターId
     * @return
     */
    F00062Dto selectMentorInfo(String mentorId);

    /**
     * 生徒一覧情報を取得
     *
     * @param stuId 生徒Id
     * @return
     */
    F00062Dto selectStuInfo(String stuId);
}
