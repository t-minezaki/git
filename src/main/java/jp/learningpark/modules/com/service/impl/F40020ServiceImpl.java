package jp.learningpark.modules.com.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.com.dao.F40020Dao;
import jp.learningpark.modules.com.dto.F40020Dto;
import jp.learningpark.modules.com.service.F40020Service;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
/**
 * <p>ワンタイムパスワード認証結果（成功）画面</p>
 * <p>ServiceImpl</p>
 * @author NWT : wang <br />
 * 変更履歴 <br />
 * 2020/8/6 : wang: 新規<br />
 * @version 8.0(ph1.5)
 */
@Transactional
@Service
public class F40020ServiceImpl implements F40020Service {
    @Autowired
    MstUsrService mstUsrService;
    @Autowired
    F40020Dao f40020Dao;

    @Override
    public R confirmId() {
        //ユーザＩＤ取得
        F40020Dto f40020Dto = getDiv();
        MstUsrEntity mstUsrEntity = getMstUsrEntity();
//        ユーザ基本マスタを元に、初回登録フラグとロール区分を取得する。
        if (mstUsrEntity == null) {
            return R.ok().put("success", false);
        } else {
            // ユーザーＩＤ
            String userId = mstUsrEntity.getUsrId();
            return R.ok().put("userId", userId).put("f40020Dto", f40020Dto);
        }
    }

    @Override
    public R update(){
        //ユーザＩＤ取得
        F40020Dto f40020Dto = getDiv();
        MstUsrEntity mstUsrEntity = getMstUsrEntity();
        //下記条件で保護者基本マスタから初回登録フラグを更新する。
        if (StringUtils.equals(f40020Dto.getWebUseFlg(), "1")) {
            mstUsrEntity.setFstLoginFlg("1");
            mstUsrEntity.setUpdUsrId(mstUsrEntity.getUsrId());
            mstUsrEntity.setUpdDatime(DateUtils.getSysTimestamp());
            mstUsrService.updateById(mstUsrEntity);
        }
        return R.ok();
    }

    private MstUsrEntity getMstUsrEntity() {
        String usrId = ShiroUtils.getUserId();
        // ユーザ基本マスタ情報取得
        return mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id", usrId).eq("del_flg", 0));
    }

    private F40020Dto getDiv() {
        return f40020Dao.getDiv(ShiroUtils.getUserId());
    }

}
