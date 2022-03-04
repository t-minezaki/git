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
import jp.learningpark.modules.common.dao.OneTimePwdDao;
import jp.learningpark.modules.common.entity.OneTimePwdEntity;
import jp.learningpark.modules.common.service.OneTimePwdService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * ONETIMEパスワード管理
 *
 *@author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/4 : xie: 新規<br />
 * @version 1.0
 * */
@Service("oneTimePwdService")
@Transactional
public class OneTimePwdServiceImpl extends ServiceImpl<OneTimePwdDao, OneTimePwdEntity> implements OneTimePwdService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<OneTimePwdEntity> page = baseMapper.selectPage(
                new Query<OneTimePwdEntity>(params).getPage(),
                new QueryWrapper<OneTimePwdEntity>()
        );

        return new PageUtils(page);
    }

}
