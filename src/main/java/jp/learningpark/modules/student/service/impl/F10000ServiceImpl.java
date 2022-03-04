package jp.learningpark.modules.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.student.dao.F10001Dao;
import jp.learningpark.modules.student.dto.F10001Dto;
import jp.learningpark.modules.student.service.F10000Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/07/08 ： NWT)hxl ： 新規作成
 * @date 2020/07/08 11:34
 */
@Service
public class F10000ServiceImpl implements F10000Service {
    /**
     * f10001Dao
     */
    @Autowired
    F10001Dao f10001Dao;

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * mstStuDao
     */
    @Autowired
    MstStuDao mstStuDao;

    /**
     * 初期化
     *
     * @param orgId セッションデータ．組織ID
     * @param stuId セッションデータ．生徒ID
     * @return
     */
    @Override
    public R init(String orgId, String stuId) {
        F10001Dto stuInfo = f10001Dao.selectByStuId(stuId, orgId);
        if (stuInfo == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒基本マスタ"));
        }
        //誕生日の変換
        if (stuInfo.getBirthd() != null) {
            stuInfo.setBirthdayString(DateUtils.format(stuInfo.getBirthd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
        } else {
            stuInfo.setBirthdayString(DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
        }
        //1.1性別リストを取得し
        List<MstCodDEntity> gendrList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "GENDR_DIV").eq("del_flg", 0)).orderByAsc("sort"));

        //更新日時
        String updateStr = DateUtils.format(stuInfo.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);

        return R.ok().put("stuInfo", stuInfo)
            .put("gendrList", gendrList)
            .put("updateStr", updateStr)
            .put("orgId", orgId);
    }

    /**
     * 生徒基本マスタへ更新
     *
     * @param dto 入力内容
     * @return
     */
    @Override
    public R submit(F10001Dto dto) {
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
