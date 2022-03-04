package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstUnitDao;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.TextbDefTimeInfoService;
import jp.learningpark.modules.manager.dao.F02002Dao;
import jp.learningpark.modules.manager.service.F02002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * F00002 F02002_単元情報検索一覧画面
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/20 : xiong: 新規<br />
 * @version 1.0
 */
@Service
@Transactional
public class F02002ServiceImpl implements F02002Service {

    @Autowired
    private TextbDefTimeInfoService textbDefTimeInfoService;

    @Autowired
    private MstUnitDao mstUnitDao;

    @Autowired
    private F02002Dao f02002Dao;
    /**
     * 単元マスタから論理削除
     * @param unitId     単元マスタ．ＩＤ
     * @param updateTime 排他
     * @return
     */
    @Override
    public R deleteUnit(Integer unitId,String updateTime) {
        // 教科書デフォルトターム情報の存在チェック
        TextbDefTimeInfoEntity textbDefTimeInfoEntity = textbDefTimeInfoService.getOne(new QueryWrapper<TextbDefTimeInfoEntity>()
                .eq("unit_id",unitId).eq("del_flg",0));
        if (textbDefTimeInfoEntity != null){
            return R.error(MessageUtils.getMessage("MSGCOMN0081","単元"));
        }
            MstUnitEntity mstUnitEntity = mstUnitDao.selectById(unitId);
            // 排他
            String dbUpdateTime = DateUtils.format(mstUnitEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            if (updateTime.equals(dbUpdateTime)){
                // 更新日時　＝　システム日時
                mstUnitEntity.setUpdDatime(DateUtils.getSysTimestamp());
                // 更新ユーザＩＤ　＝　ログインユーザＩＤ
                mstUnitEntity.setUpdUsrId(ShiroUtils.getUserId());
                // 削除フラグ　＝　「1：無効」
                mstUnitEntity.setDelFlg(1);
                mstUnitDao.updateById(mstUnitEntity);
            }else {
                return R.error(MessageUtils.getMessage("MSGCOMN0019"));
            }
        return R.ok();
    }

    @Override
    public List<MstOrgEntity> getLowerOrg(String orgId) {
        return f02002Dao.selectLowerOrg(orgId);
    }

    @Override
    public List<MstOrgEntity> getUpOrg(String orgId) {
        return f02002Dao.selectUpOrg(orgId);
    }
}
