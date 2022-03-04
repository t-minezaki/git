/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.dao.MstGuardDao;
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGuardEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.guard.dao.F30001Dao;
import jp.learningpark.modules.guard.dto.F30001Dto;
import jp.learningpark.modules.guard.service.F30001Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F30001_保護者基本情報登録画面 ServiceImpl</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/01/11 : xiong: 新規<br />
 * @version 1.0
 */
@Transactional
@Service
public class F30001ServiceImpl implements F30001Service {

    @Autowired
    private F30001Dao f30001Dao;

    @Autowired
    private MstGuardDao mstGuardDao;

    @Autowired
    private MstUsrDao mstUsrDao;

    /**
     * 保護者基本マスタから取得し
     * @return
     */
    @Override
    public R getGuard() {
        // 保護者ID
        String guardId = ShiroUtils.getUserId();
        /*//セッションデータ．GIDフラグより
        String gidFlg = ShiroUtils.getUserEntity().getGidFlg();*/
        MstUsrEntity mstUsrEntity = mstUsrDao.selectOne(new QueryWrapper<MstUsrEntity>().select("saf_modify_flg").eq("usr_id", guardId).eq("del_flg", 0));
        // 保護者基本マスタから取得し
        MstGuardEntity mstGuardEntity = f30001Dao.getGuard(guardId);
        // コードマスタより、続柄リストを取得し
        List<MstCodDEntity> codList = f30001Dao.getReltnspDiv();
        if (mstGuardEntity == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","保護者基本マスタ")).put("codList",codList);
        }
        // 更新日時
        String nowTime = DateUtils.format(mstGuardEntity.getUpdDatime(),Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        return R.ok().put("guard",mstGuardEntity).put("codList",codList).put("updateTime",nowTime).put("safModifyFlg",mstUsrEntity.getSafModifyFlg());
    }

    /**
     * 住所マスタより、郵便番号を元に、住所情報を取得し、住所エリアに表示される
     * @param addr 住所
     * @return
     */
    @Override
    public R searchAddr(String addr) {
        // 住所マスタより、郵便番号を元に、住所情報を取得し
        List adr1 = f30001Dao.searchAddr(addr);
        if (adr1.isEmpty()){
            return R.error(MessageUtils.getMessage("MSGCOMN0070","入力した郵便番号"));
        }
        return R.ok().put("adr1",adr1.get(0));
    }

    /**
     * 保護者基本マスタへ更新する
     * @param mstGuardEntity 保護者基本マスタ
     * @param updateTime 更新日時
     * @return
     */
    @Override
    public R submit(MstGuardEntity mstGuardEntity,String updateTime) {
        // ID
        Integer id = mstGuardEntity.getId();
        // 排他チェックエラーの場合
        MstGuardEntity mstGuardEntity1 = mstGuardDao.selectById(id);
        String nowTime = DateUtils.format(mstGuardEntity1.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        if (!updateTime.equals(nowTime)){
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }
        Map<String, Object> entity = new HashMap<>(16);
        // 姓名_姓
        entity.put("flnmNm", mstGuardEntity.getFlnmNm());
        // 姓名_名
        entity.put("flnmLnm", mstGuardEntity.getFlnmLnm());
        // 姓名_カナ姓
        entity.put("flnmKnNm", mstGuardEntity.getFlnmKnNm());
        // 姓名_カナ名
        entity.put("flnmKnLnm", mstGuardEntity.getFlnmKnLnm());
        entity.put("postcdMnum", mstGuardEntity.getPostcdMnum());
        entity.put("postcdBnum", mstGuardEntity.getPostcdBnum());
        entity.put("adr1", mstGuardEntity.getAdr1());
        entity.put("adr2", mstGuardEntity.getAdr2());
        // 電話番号
        entity.put("telnum", mstGuardEntity.getTelnum());
        entity.put("urgTelnum", mstGuardEntity.getUrgTelnum());
        // 更新内容
        entity.put("updDatime", DateUtils.getSysTimestamp());
        entity.put("updUsrId", ShiroUtils.getUserId());
        entity.put("afterUsrId", ShiroUtils.getUserEntity().getAfterUsrId());
        f30001Dao.updateGuardInfo(entity);
        // ユーザーID
        String afterUsrId = ShiroUtils.getUserEntity().getAfterUsrId();
        // 子供個数の取得
        F30001Dto f30001Dto = f30001Dao.stuNumber(afterUsrId);
        if (f30001Dto.getCount() == 1){
            String stuId = f30001Dto.getStuId();
            String orgId = f30001Dto.getUsrId();
            Integer crmschId = f30001Dao.getCrmschId(stuId);
            return R.ok().put("f30001Dto",f30001Dto).put("crmschId",crmschId);
        }
        return R.ok().put("f30001Dto",f30001Dto);
    }


}
