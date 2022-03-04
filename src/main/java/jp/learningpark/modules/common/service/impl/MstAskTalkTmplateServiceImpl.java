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
import jp.learningpark.modules.common.dao.MstAskTalkTmplateDao;
import jp.learningpark.modules.common.entity.MstAskTalkTmplateEntity;
import jp.learningpark.modules.common.service.MstAskTalkTmplateService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstAskTalkTmplateService")
@Transactional
public class MstAskTalkTmplateServiceImpl extends ServiceImpl<MstAskTalkTmplateDao, MstAskTalkTmplateEntity> implements MstAskTalkTmplateService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstAskTalkTmplateEntity> page = baseMapper.selectPage(
                new Query<MstAskTalkTmplateEntity>(params).getPage(),
                new QueryWrapper<MstAskTalkTmplateEntity>()
        );

        return new PageUtils(page);
    }

}
