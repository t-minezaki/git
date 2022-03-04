package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.util.Map;

/**
 * <p>
 * F21028Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/13 ： NWT)hxl ： 新規作成
 * @date 2020/02/13 12:04
 */
public interface F21028Service {

    /**
     * 生徒情報を取得する
     *
     * @param params パラメータ
     * @return
     */
    R getLineDate(Map<String, Object> params);
}
