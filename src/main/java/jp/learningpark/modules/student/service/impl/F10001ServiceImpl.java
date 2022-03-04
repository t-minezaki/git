/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.student.dao.F10001Dao;
import jp.learningpark.modules.student.dto.F10001Dto;
import jp.learningpark.modules.student.service.F10001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F10001生徒基本情報登録画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/11/16 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F10001ServiceImpl implements F10001Service {

    /**
     * 生徒基本マスタ Dao
     */
    @Autowired
    private MstStuDao mstStuDao;

    /**
     * F10001生徒基本情報登録画面 Dao
     */
    @Autowired
    private F10001Dao f10001Dao;

    /**
     * 生徒タイムプラン
     *
     * @param stuId 生徒ID
     * @param orgId 組織ID
     * @return
     */
    @Override
    public F10001Dto getByStuId(String stuId, String orgId) {
        return f10001Dao.selectByStuId(stuId, orgId);
    }

    /**
     * <p>生徒が選択した教科書情報(stu_textb_choc_mst)</p>
     *
     * @param stuId 生徒id
     * @return
     */
    @Override
    public List<String> getTextbChocList(String stuId) {
        return f10001Dao.selectTextbChocList(stuId);
    }

    /**
     * <p>更新</p>
     *
     * @param dto ページ情報
     * @return
     */
    @Override
    public R update(F10001Dto dto) {
        //生徒id
        String stuId = ShiroUtils.getUserId();
        //生徒基本マスタへ更新する
        MstStuEntity mstStuEntity = mstStuDao.selectById(dto.getId());
        if (mstStuEntity == null || !StringUtils.equals(dto.getUpdateStr(), DateUtils.format(mstStuEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        Map<String, Object> entity = new HashMap<>(16);
        // 姓名_姓
        entity.put("flnmNm", dto.getStuFnm());
        // 姓名_名
        entity.put("flnmLnm", dto.getStuLnm());
        // 姓名_カナ姓
        entity.put("flnmKnNm", dto.getFlnmKnNm());
        // 姓名_カナ名
        entity.put("flnmKnLnm", dto.getFlnmKnLnm());
        entity.put("sch", dto.getSchCd());
        entity.put("gendrDiv", dto.getGendrDiv());
        entity.put("birthd", dto.getBirthd());
        // 更新内容
        entity.put("updDatime", DateUtils.getSysTimestamp());
        entity.put("updUsrId", ShiroUtils.getUserId());
        entity.put("afterUsrId", ShiroUtils.getUserEntity().getAfterUsrId());
        f10001Dao.updateStudentInfo(entity);

        ShiroUtils.setSessionAttribute(GakkenConstant.STU_NM, dto.getStuFnm() + " " + dto.getStuLnm());
        return R.ok();
    }

}
