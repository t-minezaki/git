package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dao.F08009Dao;
import jp.learningpark.modules.manager.dto.F08009DspDto;
import jp.learningpark.modules.manager.dto.F08009Dto;
import jp.learningpark.modules.manager.service.F08009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class F08009ServiceImpl implements F08009Service {
    /**
     * F08009Dao
     */
    @Autowired
    F08009Dao f08009Dao;

    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params
     * @return
     */
    @Override
    public List<F08009Dto> selectAll(Integer limit, Integer page, Map<String,String> params) {

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

        if ("NaN/NaN/0NaN ".equals(params.get("pubStartDtStart")))
            params.put("pubStartDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("pubStartDtEnd")))
            params.put("pubStartDtEnd", null);
        if ("NaN/NaN/0NaN ".equals(params.get("pubEndDtStart")))
            params.put("pubEndDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("pubEndDtEnd")))
            params.put("pubEndDtEnd", null);

        if ("NaN/NaN/0NaN ".equals(params.get("applyStartDtStart")))
            params.put("applyStartDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("applyStartDtEnd")))
            params.put("applyStartDtEnd", null);
        if ("NaN/NaN/0NaN ".equals(params.get("applyEndDtStart")))
            params.put("applyEndDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("applyEndDtEnd")))
            params.put("applyEndDtEnd", null);

        return f08009Dao.selectAll(limit, page, params);
    }

    @Override
    public Integer countAll(Map<String,String> params) {

        if ("NaN/NaN/0NaN ".equals(params.get("cretDatimeStart")))
            params.put("cretDatimeStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("cretDatimeEnd")))
            params.put("cretDatimeEnd", null);
        if ("NaN/NaN/0NaN ".equals(params.get("updDatimeStart")))
            params.put("updDatimeStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("updDatimeEnd")))
            params.put("updDatimeEnd", null);

        if ("NaN/NaN/0NaN ".equals(params.get("pubStartDtStart")))
            params.put("pubStartDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("pubStartDtEnd")))
            params.put("pubStartDtEnd", null);
        if ("NaN/NaN/0NaN ".equals(params.get("pubEndDtStart")))
            params.put("pubEndDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("pubEndDtEnd")))
            params.put("pubEndDtEnd", null);

        if ("NaN/NaN/0NaN ".equals(params.get("applyStartDtStart")))
            params.put("applyStartDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("applyStartDtEnd")))
            params.put("applyStartDtEnd", null);
        if ("NaN/NaN/0NaN ".equals(params.get("applyEndDtStart")))
            params.put("applyEndDtStart", null);
        if ("NaN/NaN/0NaN ".equals(params.get("applyEndDtEnd")))
            params.put("applyEndDtEnd", null);
        return f08009Dao.countAll(params);
    }
    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public F08009Dto selectDspItem(String userId, String menuId) {
        return f08009Dao.selectDspItems(userId, menuId);
    }

    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public Integer getDspCount(String userId, String menuId) {
        return f08009Dao.getDspCount(userId, menuId);
    }

    /**
     * @param F08009DspDto
     * @return
     */
    @Override
    public Integer insertDspItem(F08009DspDto F08009DspDto) {
        return f08009Dao.insertDspItem(F08009DspDto);
    }

    /**
     * @param F08009DspDto
     * @return
     */
    @Override
    public Integer updateDspItem(F08009DspDto F08009DspDto) {
        return f08009Dao.updateDspItem(F08009DspDto);
    }

    /**
     * @return
     */
    @Override
    public List<MstTmplateEntity> getTmplateAll() {
        return f08009Dao.getTmplateAll();
    }
}
