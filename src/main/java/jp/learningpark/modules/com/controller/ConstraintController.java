/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.com.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.modules.com.service.ConstraintService;
import jp.learningpark.modules.common.entity.MstNumassEntity;
import jp.learningpark.modules.common.service.MstNumassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;

/**
 * <p>機能について短い文で「・・・。」とする。</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2020/03/30 : wq: 新規<br />
 * @version 1.0
 */
@RestController
public class ConstraintController {

    /**
     * 採番マスタ
     */
    @Autowired
    private MstNumassService mstNumassService;

    @Autowired
    private ConstraintService constraintService;

    /**
     * 一意制約を解決し
     */
    @PostConstruct
    public void constraint() {
        MstNumassEntity mstStudentNumassEntity = mstNumassService.getOne(new QueryWrapper<MstNumassEntity>().eq("role_div", "4").eq("del_flg", 0));
        MstNumassEntity mstGuardNumassEntity = mstNumassService.getOne(new QueryWrapper<MstNumassEntity>().eq("role_div", "3").eq("del_flg", 0));
        MstNumassEntity mstMentorNumassEntity = mstNumassService.getOne(new QueryWrapper<MstNumassEntity>().eq("role_div", "2").eq("del_flg", 0));
        MstNumassEntity mstManagerNumassEntity = mstNumassService.getOne(new QueryWrapper<MstNumassEntity>().eq("role_div", "1").eq("del_flg", 0));
        if (mstManagerNumassEntity != null) {
            Integer maxManager = constraintService.getMaxManagerId();
            mstManagerNumassEntity.setMaxId("a" + maxManager);
            mstNumassService.update(mstManagerNumassEntity, new QueryWrapper<MstNumassEntity>().eq("role_div", "1").eq("del_flg", 0));
        }
        if (mstMentorNumassEntity != null) {
            Integer maxMentor = constraintService.getMaxMentorId();
            mstMentorNumassEntity.setMaxId("m" + maxMentor);
            mstNumassService.update(mstMentorNumassEntity, new QueryWrapper<MstNumassEntity>().eq("role_div", "2").eq("del_flg", 0));
        }
        if (mstGuardNumassEntity != null) {
            Integer maxGuard = constraintService.getMaxGuardId();
            mstGuardNumassEntity.setMaxId("p" + maxGuard);
            mstNumassService.update(mstGuardNumassEntity, new QueryWrapper<MstNumassEntity>().eq("role_div", "3").eq("del_flg", 0));
        }
        if (mstStudentNumassEntity != null) {
            Integer maxStu = constraintService.getMaxStuId();
            mstStudentNumassEntity.setMaxId("s" + maxStu);
            mstNumassService.update(mstStudentNumassEntity, new QueryWrapper<MstNumassEntity>().eq("role_div", "4").eq("del_flg", 0));
        }
    }
}
