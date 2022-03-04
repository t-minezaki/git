package jp.learningpark.modules.student.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.student.dto.F10001Dto;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/07/08 ： NWT)hxl ： 新規作成
 * @date 2020/07/08 11:34
 */
public interface F10000Service {
    /**
     * 初期化
     *
     * @param orgId セッションデータ．組織ID
     * @param stuId セッションデータ．生徒ID
     * @return
     */
    R init(String orgId, String stuId);

    /**
     * 生徒基本マスタへ更新
     *
     * @param f10001Dto 入力内容
     * @return
     */
    R submit(F10001Dto f10001Dto);
}
