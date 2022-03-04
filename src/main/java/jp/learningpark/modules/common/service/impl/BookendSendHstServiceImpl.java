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
import jp.learningpark.modules.common.dao.BookendSendHstDao;
import jp.learningpark.modules.common.entity.BookendSendHstEntity;
import jp.learningpark.modules.common.service.BookendSendHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("bookendSendHstService")
@Transactional
public class BookendSendHstServiceImpl extends ServiceImpl<BookendSendHstDao, BookendSendHstEntity> implements BookendSendHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BookendSendHstEntity> page = baseMapper.selectPage(
                new Query<BookendSendHstEntity>(params).getPage(),
                new QueryWrapper<BookendSendHstEntity>()
        );

        return new PageUtils(page);
    }

}
