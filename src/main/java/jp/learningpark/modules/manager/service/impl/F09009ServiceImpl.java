package jp.learningpark.modules.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstVariousSetDao;
import jp.learningpark.modules.common.entity.MstVariousSetEntity;
import jp.learningpark.modules.manager.dto.F09009Dto;
import jp.learningpark.modules.manager.service.F09009Service;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = Exception.class)
public class F09009ServiceImpl implements F09009Service {
    // 2020/11/9 zhangminghao modify start
    private final MstVariousSetDao mstVariousSetDao;

    public F09009ServiceImpl(MstVariousSetDao mstVariousSetDao) {
        this.mstVariousSetDao = mstVariousSetDao;
    }

    @Override
    public R init() {
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstVariousSetEntity variousSet = mstVariousSetDao.selectOne(new QueryWrapper<MstVariousSetEntity>()
                .eq("org_id", orgId)
                .eq("del_flg",0));

        if (variousSet == null) {
            return R.error();
        }

        String updateStrCheck = DateUtils.format(variousSet.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        return R.ok().put("org" ,variousSet).put("updateStrCheck",updateStrCheck);
    }

    @Override
    public R edit(F09009Dto dto) {
        //排他チェックエラーの場合、処理を中断し、エラー内容を画面の上部に表示する
        MstVariousSetEntity variousSet = mstVariousSetDao.selectById(dto.getId());
        if (variousSet != null && !StringUtils.equals(dto.getUpdateStrCheck(),
                DateUtils.format(variousSet.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }

        if (variousSet == null) {
            variousSet = new MstVariousSetEntity();
        }
        //ログインユーザＩＤ
        String userId = ShiroUtils.getUserId();
        variousSet.setId(dto.getId());
        variousSet.setOrgId(dto.getOrgId());
        variousSet.setReRecoTime(dto.getReRecoTime());
        variousSet.setGoSchMsg("");
        variousSet.setGoSchErrMsg("");
        variousSet.setDouGoSchMsg("");
        // 登校自動ポイント
        variousSet.setGoSchPoint(dto.getGoSchPoint());
        // 実行登録時自動ポイント
        variousSet.setDoLoginPoint(dto.getDoLoginPoint());
        // 実行時間累計自動ポイント
        variousSet.setDoTotalPoint(dto.getDoTotalPoint());
        // 入室時自動ポイント
        variousSet.setInRoomPoint(dto.getInRoomPoint());
        // 2020/11/13 zhangminghao modify start
        // 合格時自動ポイント
        variousSet.setPaddPoint(dto.getPaddPoint());
        // 満点時自動ポイント
        variousSet.setFullMarksPoint(dto.getFullMarksPoint());
        // 宿題提出時自動ポイント
        variousSet.setWorkOutPoint(dto.getWorkOutPoint());
        // 出席登録時自動ポイント
        variousSet.setAttentOutPoint(dto.getAttentOutPoint());
        // 誕生日時自動ポイント
        variousSet.setBirthdayTimePoint(dto.getBirthdayTimePoint());
        // 2020/11/13 zhangminghao modify end
        variousSet.setCretDatime(dto.getCretDatime());
        variousSet.setCretUsrId(userId);
        variousSet.setUpdDatime(DateUtils.getSysTimestamp());
        variousSet.setUpdUsrId(userId);
        variousSet.setDelFlg(0);

        if (variousSet.getId() == null) {
            String orgId = ShiroUtils.getUserEntity().getOrgId();
            variousSet.setOrgId(orgId);
            variousSet.setCretDatime(DateUtils.getSysTimestamp());
            mstVariousSetDao.insert(variousSet);
        } else {
            mstVariousSetDao.updateById(variousSet);
        }

        return R.ok();
    }
    // 2020/11/9 zhangminghao modify end
}
