package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dao.F08001Dao;
import jp.learningpark.modules.manager.dto.F08001DspDto;
import jp.learningpark.modules.manager.dto.F08001Dto;
import jp.learningpark.modules.manager.service.F08001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class F08001ServiceImpl implements F08001Service {
    /**
     * F08001Dao
     */
    @Autowired
    F08001Dao f08001Dao;

    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params          条件を調べる
     * @return
     */
    @Override
    public List<F08001Dto> selectAll(Integer limit, Integer page, Map<String, String> params) {
        if (page != null && limit != null) {
            page = (page - 1) * limit;
        }
        if ("NaN/NaN/0NaN ".equals(params.get("cretDatimeStart"))) {
            params.put("cretDatimeStart", null);
        }
        if ("NaN/NaN/0NaN ".equals(params.get("cretDatimeEnd"))) {
            params.put("cretDatimeEnd", null);
        }
        if ("NaN/NaN/0NaN ".equals(params.get("updDatimeStart"))) {
            params.put("updDatimeStart", null);
        }
        if ("NaN/NaN/0NaN ".equals(params.get("updDatimeEnd"))) {
            params.put("updDatimeEnd", null);
        }
        return f08001Dao.selectAll(limit, page, params);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public F08001Dto selectDspItem(String userId, String menuId) {
        return f08001Dao.selectDspItems(userId, menuId);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public Integer getDspCount(String userId, String menuId) {
        return f08001Dao.getDspCount(userId, menuId);
    }

    /**
     * @param f08001DspDto
     * @return
     */
    @Override
    public Integer insertDspItem(F08001DspDto f08001DspDto) {
        return f08001Dao.insertDspItem(f08001DspDto);
    }

    /**
     * @param f08001DspDto
     * @return
     */
    @Override
    public Integer updateDspItem(F08001DspDto f08001DspDto) {
        return f08001Dao.updateDspItem(f08001DspDto);
    }

    /**
     * @return
     */
    @Override
    public List<MstTmplateEntity> getTmplateAll() {
        return f08001Dao.getTmplateAll();
    }
}
