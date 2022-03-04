package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.manager.dao.F08024Dao;
import jp.learningpark.modules.manager.dto.F08024DspDto;
import jp.learningpark.modules.manager.dto.F08024Dto;
import jp.learningpark.modules.manager.service.F08024Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Map;

@Service
public class F08024ServiceImpl implements F08024Service {
    /**
     * F08001Dao
     */
    @Autowired
    F08024Dao f08024Dao;
    /**
     * @param limit           各ページの最大記録数
     * @param page            現在のページ数
     * @param params
     * @return
     */
    @Override
    public List<F08024Dto> selectAll(Integer limit, Integer page, Map<String,String> params) {
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

        return f08024Dao.selectAll(limit, page, params);
    }
    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public F08024Dto selectDspItem(String userId, String menuId) {
        return f08024Dao.selectDspItems(userId, menuId);
    }
    /**
     * @param userId ユーザID
     * @param menuId 画面ID
     * @return
     */
    @Override
    public Integer getDspCount(String userId, String menuId) {
        return f08024Dao.getDspCount(userId, menuId);
    }

    /**
     * @param f08024DspDto
     * @return
     */
    @Override
    public Integer insertDspItem(F08024DspDto f08024DspDto) {
        return f08024Dao.insertDspItem(f08024DspDto);
    }

    /**
     * @param f08024DspDto
     * @return
     */
    @Override
    public Integer updateDspItem(F08024DspDto f08024DspDto) {
        return f08024Dao.updateDspItem(f08024DspDto);
    }

    /**
     * @return
     */
    @Override
    public List<MstTmplateEntity> getTmplateAll(String orgId,String tmplateTypeDiv) {
        return f08024Dao.getTmplateAll(orgId,tmplateTypeDiv);
    }

    /**
     * @return
     */
    @Override
    public List<MstTmplateEntity> getOrderTmplateAll(String tmplateDiv,String orgId) {
        return f08024Dao.getOrderTmplateAll(tmplateDiv,orgId);
    }
}
