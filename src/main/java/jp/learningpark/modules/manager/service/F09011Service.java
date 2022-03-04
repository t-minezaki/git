package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09011SaveDto;

import java.util.List;

/**
 * <p>ポイント設定確認画面Service</p >
 *
 * @author NWT : zmh <br />
 * 変更履歴 <br />
 * 2020/11/10 : zmh: 新規<br />
 * @version 1.0
 */
public interface F09011Service {

    /**
     * 学生IDコレクションに基づいて学生の詳細を取得する
     *
     * @param stuIds 照会する学生IDコレクション
     * @param limit  ページングサイズ
     * @param page   現在のページ番号
     * @return ビューレイヤーに返されるデータ
     */
    R init(List<String> stuIds, Integer limit, Integer page);

    /**
     * 学生ポイント情報を保存または更新する
     *
     * @param saveDto データの更新
     */
    void saveOrUpdate(F09011SaveDto saveDto);
}
