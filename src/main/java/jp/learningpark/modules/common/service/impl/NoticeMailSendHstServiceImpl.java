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
import jp.learningpark.modules.common.dao.NoticeMailSendHstDao;
import jp.learningpark.modules.common.entity.NoticeMailSendHstEntity;
import jp.learningpark.modules.common.service.NoticeMailSendHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("noticeMailSendHstService")
@Transactional
public class NoticeMailSendHstServiceImpl extends ServiceImpl<NoticeMailSendHstDao, NoticeMailSendHstEntity> implements NoticeMailSendHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<NoticeMailSendHstEntity> page = baseMapper.selectPage(
                new Query<NoticeMailSendHstEntity>(params).getPage(),
                new QueryWrapper<NoticeMailSendHstEntity>()
        );

        return new PageUtils(page);
    }

}
