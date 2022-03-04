/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.modules.manager.dto.F20004Dto;

import java.util.List;

/**
 * <p>週別タームプラン確認画面Service。</p >
 *
 * @author NWT : yangfan <br />
 * 変更履歴 <br />
 * 2018/10/26 : yangfan: 新規<br />
 * @version 1.0
 */
public interface F20004Service {

    /**
     * <p>生徒の週毎の学習時間取得(F20004)</p>
     *
     * @param crmLearnPrdId 塾学習時間Id
     * @param stuId 生徒ID
     * @return 生徒の週毎の学習時間情報
     */
    List<F20004Dto> selectStuWeeklyLearnTmInfo(Integer crmLearnPrdId, String stuId);

    /**
     * <p>生徒基本情報</p>
     * @param stuId 生徒ID
     * @param orgId 塾ID
     * @return
     */
    F20004Dto getStuInfo( String stuId,String orgId);
}
