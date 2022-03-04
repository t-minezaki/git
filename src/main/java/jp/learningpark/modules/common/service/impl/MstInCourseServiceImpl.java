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
import jp.learningpark.modules.common.dao.MstInCourseDao;
import jp.learningpark.modules.common.entity.MstInCourseEntity;
import jp.learningpark.modules.common.service.MstInCourseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>入会コース管理 serviceImpl</p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@Service("mstInCourseService")
@Transactional
public class MstInCourseServiceImpl extends ServiceImpl<MstInCourseDao, MstInCourseEntity> implements MstInCourseService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstInCourseEntity> page = baseMapper.selectPage(
                new Query<MstInCourseEntity>(params).getPage(),
                new QueryWrapper<MstInCourseEntity>()
        );

        return new PageUtils(page);
    }
}
