package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.manager.dao.F21033Dao;
import jp.learningpark.modules.manager.service.F21033Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class F21033ServiceImpl implements F21033Service {
    /**
     * f21033Dao
     */
    @Autowired
    F21033Dao f21033Dao;

    /**
     * 各対象日をもとに、組織単位で全て生徒の実績個数と計画個数を集計する
     * @param ymdStart
     * @param ymdEnd
     * @param orgId
     * @return
     */
    @Override
    public Double getSchoolData(String ymdStart, String ymdEnd, String orgId) {
        return f21033Dao.getSchoolData(ymdStart,ymdEnd,orgId);
    }

    /**
     * 各対象日をもとに、指定したグループ単位で全て生徒の実績個数と計画個数を集計する
     * @param ymdStart
     * @param ymdEnd
     * @param grpId
     * @return
     */
    @Override
    public Double getGroupData(String ymdStart, String ymdEnd, Integer grpId) {
        return f21033Dao.getGroupData(ymdStart, ymdEnd, grpId);
    }

    @Override
    public Double getMap2(String ymdStart, String ymdEnd, String orgId, String schyDiv, Integer grpId, String lineType) {
        return f21033Dao.getMap2(ymdStart, ymdEnd, orgId, schyDiv, grpId, lineType);
    }

    @Override
    public R getInformation(String usrId, Integer limit, Integer page) {
        List<F30112Dto> informationList = f21033Dao.getInformation(usrId, limit, (page - 1) * limit);
        Integer total = f21033Dao.getInformationCount(usrId);
        return R.ok().put("page", new PageUtils(informationList, total, limit, page));
    }
}
