package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dao.F08016Dao;
import jp.learningpark.modules.manager.dto.F08016DspDto;
import jp.learningpark.modules.manager.dto.F08016Dto;
import jp.learningpark.modules.manager.service.F08016Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class F08016ServiceImpl implements F08016Service {
    /**
     * F08001Dao
     */
    @Autowired
    F08016Dao f08016Dao;

    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params
     * @return
     */
    @Override
    public List<F08016Dto> selectAll(Integer limit, Integer page, Map<String,String> params) {
        if (page != null && limit != null)
            page = (page - 1) * limit;

        if ("NaN/NaN/0NaN ".equals(params.get("cretDatimeStart")))
            params.put("cretDatimeStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("cretDatimeEnd")))
            params.put("cretDatimeEnd", null);
        if ("NaN/NaN/0NaN ".equals(params.get("updDatimeStart")))
            params.put("updDatimeStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("updDatimeEnd")))
            params.put("updDatimeEnd", null);

        return f08016Dao.selectAll(limit, page, params);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public F08016Dto selectDspItem(String userId, String menuId) {
        return f08016Dao.selectDspItems(userId, menuId);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public Integer getDspCount(String userId, String menuId) {
        return f08016Dao.getDspCount(userId, menuId);
    }

    /**
     * @param f08016DspDto
     * @return
     */
    @Override
    public Integer insertDspItem(F08016DspDto f08016DspDto) {
        return f08016Dao.insertDspItem(f08016DspDto);
    }

    /**
     * @param f08016DspDto
     * @return
     */
    @Override
    public Integer updateDspItem(F08016DspDto f08016DspDto) {
        return f08016Dao.updateDspItem(f08016DspDto);
    }

    /**
     * @return
     */
    @Override
    public List<MstTmplateEntity> getTmplateAll() {
        return f08016Dao.getTmplateAll();
    }
}
