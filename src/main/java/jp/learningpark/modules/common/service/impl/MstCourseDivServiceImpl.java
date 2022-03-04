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
import jp.learningpark.modules.common.dao.MstCourseDivDao;
import jp.learningpark.modules.common.entity.MstCourseDivEntity;
import jp.learningpark.modules.common.service.MstCourseDivService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>コース区分管理 serviceImpl</p>
 * <p></p>
 * <p></p>
 *
 * @author NWT : xie <br />
 * 変更履歴 <br />
 * 2020/8/26 : xie: 新規<br />
 * @version 1.0
 */
@Service("mstCourseDivService")
@Transactional
public class MstCourseDivServiceImpl extends ServiceImpl<MstCourseDivDao, MstCourseDivEntity> implements MstCourseDivService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MstCourseDivEntity> page = baseMapper.selectPage(
                new Query<MstCourseDivEntity>(params).getPage(),
                new QueryWrapper<MstCourseDivEntity>()
        );

        return new PageUtils(page);
    }
}
