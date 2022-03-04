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
import jp.learningpark.modules.common.dao.MstUsrDao;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstUsrService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("mstUsrService")
@Transactional
public class MstUsrServiceImpl extends ServiceImpl<MstUsrDao, MstUsrEntity> implements MstUsrService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstUsrEntity> page = baseMapper.selectPage(
                new Query<MstUsrEntity>(params).getPage(),
                new QueryWrapper<MstUsrEntity>()
        );

        return new PageUtils(page);
    }

}
