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
import jp.learningpark.modules.common.dao.GuardEventScheChoiceDao;
import jp.learningpark.modules.common.entity.GuardEventScheChoiceEntity;
import jp.learningpark.modules.common.service.GuardEventScheChoiceService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("guardEventScheChoiceService")
@Transactional
public class GuardEventScheChoiceServiceImpl extends ServiceImpl<GuardEventScheChoiceDao, GuardEventScheChoiceEntity> implements GuardEventScheChoiceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<GuardEventScheChoiceEntity> page = baseMapper.selectPage(
                new Query<GuardEventScheChoiceEntity>(params).getPage(),
                new QueryWrapper<GuardEventScheChoiceEntity>()
        );

        return new PageUtils(page);
    }

}
