package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;

import java.io.UnsupportedEncodingException;
import java.util.Map;

/**
 * <p>
 * マナミルチャンネル新規·編集 Service
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/10 ： NWT)hxl ： 新規作成
 * @date 2020/02/10 13:31
 */
public interface F04012Service {
    /**
     * お知らせ登録
     *
     * @param params    パラメータ
     * @return
     */
    R save(Map<String, Object> params);

    /**
     * お知らせ編集
     *
     * @param params    パラメータ
     * @return
     */
    R update(Map<String, Object> params);

    /**
     * 初期化
     *
     * @param id        お知らせID
     * @return
     */
    R init(Integer id);
}
