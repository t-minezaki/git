package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstGrpDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.manager.dao.F09021Dao;
import jp.learningpark.modules.manager.dto.F09021Dto;
import jp.learningpark.modules.manager.service.F09021Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * F09021ServiceImpl
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/28 ： NWT)hxl ： 新規作成
 * @date 2020/02/28 11:24
 */
@Service
public class F09021ServiceImpl implements F09021Service {

    /**
     * f09021Dao
     */
    @Autowired
    F09021Dao f09021Dao;

    /**
     * mstCodDDao
     */
    @Autowired
    MstCodDDao mstCodDDao;

    /**
     * mstGrpDao
     */
    @Autowired
    MstGrpDao mstGrpDao;

    /**
     * 初期化
     *
     * @param brandCd   ブランドコード
     * @param orgId     組織ID
     * @param orgIdList 組織IDリスト
     * @return
     */
    @Override
    public R init(String brandCd, String orgId, List<String> orgIdList) {
        //配信教室を設定するため、「本組織及び下層組織取得共通部品」を利用して、本組織と下層組織を取得する。
        List<F09021Dto> lowerLevOrgList = f09021Dao.selectLowerOrg(brandCd, orgId);
        //カテゴリ選択肢を取得するため、下記条件とソート順でコードマスタ_明細データを取得する。
        List<MstCodDEntity> classifyCodList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_cd, cod_value").eq("cod_key", "CLASSIFY_LIST").eq("del_flg", 0).orderByAsc("sort"));
        //学年選択肢を取得するため、下記条件とソート順でコードマスタ_明細データを取得する。
        List<MstCodDEntity> schyCodList = mstCodDDao.selectList(new QueryWrapper<MstCodDEntity>().select("cod_cd, cod_value").eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderByAsc("sort"));
        //グループマスタからグループ情報を取得する。
        List<MstGrpEntity> grpList = mstGrpDao.selectList(new QueryWrapper<MstGrpEntity>().select("grp_id, grp_nm").eq("del_flg", 0).in("org_id", orgIdList).orderByAsc("grp_id"));
        return R.ok().put("orgList", lowerLevOrgList).put("classifyList", classifyCodList).put("schyList", schyCodList).put("grpList", grpList);
    }

    /**
     * 生徒情報のクエリ
     *
     * @param orgIdList 編集した組織ID
     * @param grpId     指定したグループID
     * @param schyDiv   指定した学年区分
     * @return
     */
    @Override
    public R getStuList(List<String> orgIdList, Integer grpId, String schyDiv) {
        //生徒情報のクエリ
        List<F09021Dto> f09021Dtos = f09021Dao.selectStuList(orgIdList, grpId, schyDiv);
        if (f09021Dtos.size() == 0){
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        return R.ok().put("stuList", f09021Dtos);
    }
}
