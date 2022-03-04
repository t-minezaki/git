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
import jp.learningpark.modules.common.dao.MstMentorDao;
import jp.learningpark.modules.common.entity.MstMentorEntity;
import jp.learningpark.modules.common.service.MstMentorService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstMentorService")
@Transactional
public class MstMentorServiceImpl extends ServiceImpl<MstMentorDao, MstMentorEntity> implements MstMentorService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstMentorEntity> page = baseMapper.selectPage(
                new Query<MstMentorEntity>(params).getPage(),
                new QueryWrapper<MstMentorEntity>()
        );

        return new PageUtils(page);
    }

}
