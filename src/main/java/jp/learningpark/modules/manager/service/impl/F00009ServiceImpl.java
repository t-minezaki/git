/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstCrmschLearnPrdDao;
import jp.learningpark.modules.common.dao.MstManagerDao;
import jp.learningpark.modules.common.dao.MstOrgDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstCrmschLearnPrdEntity;
import jp.learningpark.modules.common.entity.MstManagerEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.manager.dao.F00009Dao;
import jp.learningpark.modules.manager.dto.F00009Dto;
import jp.learningpark.modules.manager.service.F00009Service;
import jp.learningpark.modules.sys.dao.SysUserRoleDao;
import jp.learningpark.modules.sys.entity.SysUserRoleEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>F00009_組織一覧画面 ServiceImpl</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/20 : gong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F00009ServiceImpl implements F00009Service {

    /**
     * F00009_組織一覧画面 Dao
     */
    @Autowired
    private F00009Dao f00009Dao;

    /**
     * 組織Master　DAO
     */
    @Autowired
    private MstOrgDao mstOrgDao;

    /**
     * ユーザ基本マスタ dao
     */
    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * 管理者基本マスタ Dao
     */
    @Autowired
    private MstManagerDao mstManagerDao;

    /**
     * ユーザ角色 Dao
     */
    @Autowired
    private SysUserRoleDao sysUserRoleDao;

    /**
     * 塾学習期間マスタ Dao
     */
    @Autowired
    MstCrmschLearnPrdDao mstCrmschLearnPrdDao;

    /**
     * <p>下位組織一覧情報</p>
     *
     * @param orgId 条件設定用上位組織
     * @param brandCd ブランドコード
     * @return
     */
    @Override
    public List<F00009Dto> getLowerOrgList(String orgId, String brandCd) {
        return f00009Dao.selectLowerOrgList(orgId, brandCd);
    }

    /**
     * 下位組織一覧取得
     *
     * @param orgId
     * @param brandCd
     * @param orgIdList
     * @param limit
     * @param page
     * @return
     */
    @Override
    public List<F00009Dto> lowerOrgList(String orgId, String brandCd, List<String> orgIdList, String currentOrgId, String orgNm, String upLevOrgId, Integer mgrFlg, Integer level, Integer limit, Integer page) {
        return f00009Dao.lowerOrgList(orgId, brandCd, orgIdList, currentOrgId, orgNm, upLevOrgId, mgrFlg, level, limit, page);
    }

    /**
     * 下位組織一覧取得
     *
     * @param orgId
     * @param brandCd
     * @param orgIdList
     * @return
     */
    @Override
    public Integer lowerOrgCount(String orgId, String brandCd, List<String> orgIdList, String currentOrgId, String orgNm, String upLevOrgId, Integer mgrFlg, Integer level) {
        return f00009Dao.lowerOrgCount(orgId, brandCd, orgIdList, currentOrgId, orgNm, upLevOrgId, mgrFlg, level);
    }

    /**
     * ＱＲログインデータ
     *
     * @param orgIdList
     * @return
     */
    @Override
    public List<F00009Dto> getQrUser(List<String> orgIdList) {
        return f00009Dao.getQrUser(orgIdList);
    }

    @Override
    public List<F00009Dto> getAftUsrIds(List<String> orgIdList) {
        return f00009Dao.getAftUsrIds(orgIdList);
    }

    /**
     * 対応する組織を組織マスタから物理削除処理行う。
     *
     * @param id
     * @return
     */
    @Override
    public R updateById(Integer id) {
        //        MstOrgEntity mstOrgEntity;
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //削除押したら、当レコードの組織IDで当組織に属する下位組織を検索に行く
        MstOrgEntity selectMstOrgEntity = mstOrgDao.selectById(id);
        if (selectMstOrgEntity != null) {
            String delOrgId = selectMstOrgEntity.getOrgId();
            //組織マスタ１件以上の存在チェック
            List<MstOrgEntity> mstOrgEntityList = mstOrgDao.selectList(new QueryWrapper<MstOrgEntity>().eq("upplev_org_id", delOrgId).eq("del_flg", 0));
            if (mstOrgEntityList != null && mstOrgEntityList.size() >= 1) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0092", "組織"));
            }
            //ユーザ基本マスタ2件以上の存在チェック
            List<MstUsrEntity> mstUsrEntityList = mstUsrDao.selectList(new QueryWrapper<MstUsrEntity>().eq("org_id", delOrgId).eq("del_flg", 0));
            if (mstUsrEntityList != null && mstUsrEntityList.size() >= 2) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0092", "組織"));
            }
            //塾学習期間マスタ１件以上の存在チェック
            List<MstCrmschLearnPrdEntity> crmschLearnPrdEntityList = mstCrmschLearnPrdDao.selectList(
                    new QueryWrapper<MstCrmschLearnPrdEntity>().eq("org_id", delOrgId).eq("del_flg", 0));
            if (crmschLearnPrdEntityList != null && crmschLearnPrdEntityList.size() >= 1) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0092", "組織"));
            }
            mstOrgDao.deleteById(id);

            //ユーザ基本マスタ
            MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", selectMstOrgEntity.getOrgId() + brandCd)));
            mstUsrDao.delete(new QueryWrapper<MstUsrEntity>().and(w->w.eq("usr_id", selectMstOrgEntity.getOrgId() + brandCd)));
            //管理者基本マスタ
            mstManagerDao.delete(new QueryWrapper<MstManagerEntity>().and(w->w.eq("mgr_id", selectMstOrgEntity.getOrgId() + brandCd)));
            //管理者基本マスタ
            if (mstUsrEntity != null) {
                sysUserRoleDao.delete(new QueryWrapper<SysUserRoleEntity>().and(w->w.eq("user_id", mstUsrEntity.getId())));
            }

            //管理者基本マスタデータを削除する
            f00009Dao.updateMgr(delOrgId);
            //メンター基本マスタデータを削除する
            f00009Dao.updateMen(delOrgId);
            //保護者基本マスタデータを削除する
            f00009Dao.updateGuard(delOrgId);
            //生徒基本マスタデータを削除する
            f00009Dao.updateStu(delOrgId);
            //ユーザーキャラデータを削除する。　※物理削除
            f00009Dao.deleteSysUserRole(delOrgId);
            //ユーザ基本マスタデータを削除する
            MstUsrEntity mstUsrEntity1 = new MstUsrEntity();
            mstUsrEntity1.setDelFlg(1);
            mstUsrDao.update(mstUsrEntity1,new QueryWrapper<MstUsrEntity>().eq("del_flg",0).eq("org_id",delOrgId));
        }
        return R.ok();
    }
}
