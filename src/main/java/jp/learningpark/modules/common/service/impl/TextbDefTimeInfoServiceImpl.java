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
import jp.learningpark.modules.common.dao.TextbDefTimeInfoDao;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.TextbDefTimeInfoService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("textbDefTimeInfoService")
@Transactional
public class TextbDefTimeInfoServiceImpl extends ServiceImpl<TextbDefTimeInfoDao, TextbDefTimeInfoEntity> implements TextbDefTimeInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<TextbDefTimeInfoEntity> page = baseMapper.selectPage(
                new Query<TextbDefTimeInfoEntity>(params).getPage(),
                new QueryWrapper<TextbDefTimeInfoEntity>()
        );

        return new PageUtils(page);
    }

}
