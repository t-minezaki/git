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
import jp.learningpark.modules.common.dao.MstHolidayDao;
import jp.learningpark.modules.common.entity.MstHolidayEntity;
import jp.learningpark.modules.common.service.MstHolidayService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstHolidayService")
@Transactional
public class MstHolidayServiceImpl extends ServiceImpl<MstHolidayDao, MstHolidayEntity> implements MstHolidayService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstHolidayEntity> page = baseMapper.selectPage(
                new Query<MstHolidayEntity>(params).getPage(),
                new QueryWrapper<MstHolidayEntity>()
        );

        return new PageUtils(page);
    }

}
