/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.learningpark.modules.common.dao.MstNoticeBakDao;
import jp.learningpark.modules.common.entity.MstNoticeBakEntity;
import jp.learningpark.modules.common.service.MstNoticeBakService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service("mstNoticeBakService")
@Transactional
public class MstNoticeBakServiceImpl extends ServiceImpl<MstNoticeBakDao, MstNoticeBakEntity> implements MstNoticeBakService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstNoticeBakEntity> page = baseMapper.selectPage(
                new Query<MstNoticeBakEntity>(params).getPage(),
                new QueryWrapper<MstNoticeBakEntity>()
        );

        return new PageUtils(page);
    }

}
