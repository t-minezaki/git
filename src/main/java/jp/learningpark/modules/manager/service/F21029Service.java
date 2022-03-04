package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.util.Map;

/**
 * <p>
 * F21029Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/17 ： NWT)hxl ： 新規作成
 * @date 2020/02/17 13:12
 */
public interface F21029Service {

    /**
     * 生徒情報を取得する
     *
     * @param params パラメータ
     * @return
     */
    R getLineDate(Map<String, Object> params);
}
