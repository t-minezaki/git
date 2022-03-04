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
import jp.learningpark.modules.common.dao.MailSendHstDao;
import jp.learningpark.modules.common.entity.MailSendHstEntity;
import jp.learningpark.modules.common.service.MailSendHstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mailSendHstService")
@Transactional
public class MailSendHstServiceImpl extends ServiceImpl<MailSendHstDao, MailSendHstEntity> implements MailSendHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MailSendHstEntity> page = baseMapper.selectPage(
                new Query<MailSendHstEntity>(params).getPage(),
                new QueryWrapper<MailSendHstEntity>()
        );

        return new PageUtils(page);
    }

}
