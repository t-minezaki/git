package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.util.Map;

/**
 * <p>
 * F21018Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/20 ： NWT)hxl ： 新規作成
 * @date 2020/02/20 17:32
 */
public interface F21018Service {

    /**
     * 情報を取得する
     *
     * @param stuId 生徒ID
     * @return
     */
    R init(String stuId);

    /**
     * 情報を保存する
     *
     * @param params パラメータ
     * @return
     */
    R update(Map<String, Object> params);
}
