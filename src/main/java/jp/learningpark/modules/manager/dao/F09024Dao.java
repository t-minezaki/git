package jp.learningpark.modules.manager.dao;

import jp.learningpark.modules.manager.dto.F09024Dto;

import java.util.List;
import java.util.Map;

/**
 * <p>入退室履歴画面</p >
 *
 * @author NWT : wq <br />
 * <p>
 * 2021/08/18
 * @version 1.0
 */
public interface F09024Dao {

    /**
     *
     * @param map パラメータ
     * @return 入退室履歴一覧情報
     */
    List<F09024Dto> selectHstList(Map<String, Object> map);

    /**
     *
     * @param map パラメータ
     * @return 入退室履歴一覧情報記録数
     */
    Integer selectCount(Map<String, Object> map);
}
