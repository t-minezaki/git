/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;
import jp.learningpark.modules.common.dao.MstUsrFirtPwDao;
import jp.learningpark.modules.common.entity.MstUsrFirtPwEntity;
import jp.learningpark.modules.common.service.MstUsrFirtPwService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstUsrFirtPwService")
@Transactional
public class MstUsrFirtPwServiceImpl extends ServiceImpl<MstUsrFirtPwDao, MstUsrFirtPwEntity> implements MstUsrFirtPwService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstUsrFirtPwEntity> page = baseMapper.selectPage(
                new Query<MstUsrFirtPwEntity>(params).getPage(),
                new QueryWrapper<MstUsrFirtPwEntity>()
        );

        return new PageUtils(page);
    }

}
