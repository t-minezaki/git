/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstBlockEntity;
import jp.learningpark.modules.common.service.MstBlockService;
import jp.learningpark.modules.student.dao.F11007Dao;
import jp.learningpark.modules.student.dto.F11001Dto;
import jp.learningpark.modules.student.service.F11007Service;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * スマホ_学習情報登録｜タイマー登録２ ServiceImpl
 * </p>
 *
 * @author NWT)zmh
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/11/12 ： NWT)zmh ： 新規作成
 * @date 2020/11/12
 */
@Service
public class F11007ServiceImpl implements F11007Service {

    // 2020/11/12 zhangminghao modify start
    private final F11007Dao f11007Dao;
    private final MstBlockService mstBlockService;
    // 2020/12/4 huangxinliang modify start

    public F11007ServiceImpl(F11007Dao f11007Dao, MstBlockService mstBlockService) {
        this.f11007Dao = f11007Dao;
        this.mstBlockService = mstBlockService;
    }

    @Override
    public List<F11001Dto> getSubjt() {
        return f11007Dao.getSubjt(ShiroUtils.getUserId());
    }

    @Override
    public List<MstBlockEntity> getBlockType(){
        return mstBlockService.list(new QueryWrapper<MstBlockEntity>()
                .in("block_type_div","P1","R1","V1","W1","S1")
                .isNull("upplev_block_id")
                .isNull("stu_id")
                .orderByAsc("block_type_div")
                .eq("del_flg",0));

    }
    // 2020/11/12 zhangminghao modify end
}