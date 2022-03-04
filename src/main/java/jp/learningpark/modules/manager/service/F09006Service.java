package jp.learningpark.modules.manager.service;

import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.manager.dto.F09006ResetPointDto;

import java.util.List;
import java.util.Map;

public interface F09006Service {
    /**
     * 初期化
     * @param params　パラメータ
     * @return List<F09006Dto>
     */
    R init(Map<String, Object> params, Integer limit, Integer page);

    /**
     *  検索後
     *
     * @param stulist　検索後のstulist
     * @return List<F09006Dto>
     */
    R checkAfter(List<String> stulist, Integer limit, Integer page);

    /**
     * 学生ポイントをゼロにリセットする
     * @param dto 更新された学生ターゲットデータ
     */
    R resetStuPoint(F09006ResetPointDto dto);
}
