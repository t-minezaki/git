/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F00045Dto;

import java.util.List;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
public interface F00045Service {
    /**
     * 生徒、保護者一覧情報を取得
     *
     * @param stuId 生徒Id
     * @return
     */
    List<F00045Dto> getInitCont(String stuId);

    /**
     * 保護者一覧情報を取得
     *
     * @param guardId 保護者Id
     * @return
     */
    F00045Dto selectGuardInfo(String guardId);
}
