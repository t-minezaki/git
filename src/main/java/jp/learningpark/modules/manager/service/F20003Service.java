/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F20003Dto;

import java.util.List;

/**
 * <p>生徒タームプラン設定 Extend Serviceです。</p>
 *
 * @author NWT : gaoli <br />
 * 変更履歴 <br />
 * 2018/10/09 : gaoli: 新規<br />
 * @version 1.0
 */
public interface F20003Service {

    /**
     * <p>生徒基本情報を取得し</p>
     *
     * @param stuId 生徒id
     * @param orgId 塾id
     * @return
     */
    F20003Dto getStuInfoByStuId(String stuId, String orgId);

    /**
     * <p>生徒タームプラン設定、教科書デフォルトターム情報から取得し、画面で表示する</p>
     *
     * @param crmLearnPrdId 塾学習期間ID
     * @param stuId         生徒id
     * @param textbId       教科書Id
     * @return
     */
    List<F20003Dto> getDefaultAndTermPlan(Integer crmLearnPrdId, String stuId, Integer textbId);

    /**
     * <p>登録処理</p>
     *
     * @param dtos
     * @return
     */
    R update(List<F20003Dto> dtos);

    /**
     * <p>当生徒の教科と教科書情報を取得し</p>
     *
     * @param stuId         生徒id
     * @param crmLearnPrdId 塾学習期間ID
     * @return
     */
    List<F20003Dto> getStuTextchocList(String stuId, Integer crmLearnPrdId);

}