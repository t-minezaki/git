package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F09024Dao;
import jp.learningpark.modules.manager.dto.F09024Dto;
import jp.learningpark.modules.manager.service.F09024Service;
import org.springframework.stereotype.Service;

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
@Service
public class F09024ServiceImpl implements F09024Service {

    /**
     * 入退室履歴画面 Service
     */
    private final F09024Dao dao;

    public F09024ServiceImpl(F09024Dao dao) {
        this.dao = dao;
    }

    /**
     *
     * @param map パラメータ
     * @return 入退室履歴一覧情報
     */
    @Override
    public List<F09024Dto> getHstList(Map<String, Object> map) {
        return dao.selectHstList(map);
    }

    /**
     *
     * @param map パラメータ
     * @return 入退室履歴一覧情報記録数
     */
    @Override
    public Integer getCount(Map<String, Object> map) {
        return dao.selectCount(map);
    }
}
