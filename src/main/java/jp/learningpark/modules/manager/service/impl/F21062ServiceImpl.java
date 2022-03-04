package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.manager.dao.F21062Dao;
import jp.learningpark.modules.manager.dto.F21062Dto;
import jp.learningpark.modules.manager.service.F21062Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/19 ： NWT)hxl ： 新規作成
 * @date 2020/05/19 17:59
 */
@Service
public class F21062ServiceImpl implements F21062Service {

    /**
     * f21062Dao
     */
    @Autowired
    F21062Dao f21062Dao;

    /**
     * commonDao
     */
    @Autowired
    CommonDao commonDao;

    /**
     * メッセージ情報を取得する
     *
     * @param sOrgId
     * @param orgId
     * @param id
     * @param messageTitle
     * @param pubPlanStartDt
     * @param pubPlanEndDt
     * @param limit
     * @param page
     * @return
     */
    @Override
    public R getDetail(String sOrgId, String orgId, Integer id, String messageTitle, Date pubPlanStartDt, Date pubPlanEndDt, Integer limit, Integer page) {
        List<OrgAndLowerOrgIdDto> orgAndLowerOrgIdDtos = commonDao.selectThisAndLowerOrgId((String) ShiroUtils.getSessionAttribute(GakkenConstant.SESSION_BRAND_CD), sOrgId);
        List<String> orgIdList = new ArrayList<>();
        for (OrgAndLowerOrgIdDto orgAndLowerOrgIdDto : orgAndLowerOrgIdDtos) {
            orgIdList.add(orgAndLowerOrgIdDto.getOrgId());
        }
        List<F21062Dto> list = f21062Dao.searchOrg(sOrgId, orgIdList, orgId, id, messageTitle, pubPlanStartDt, pubPlanEndDt, limit, (page - 1) * limit);
        //総件数をとる
        Integer total = f21062Dao.totalCount(sOrgId, orgIdList, orgId, id, messageTitle, pubPlanStartDt, pubPlanEndDt);
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "メッセージ"));
        }
        for (F21062Dto dto : list) {
            dto.setUpdDatimeStr(DateUtils.format(dto.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        return R.ok().put("page", new PageUtils(list, total, limit, page));
    }
}
