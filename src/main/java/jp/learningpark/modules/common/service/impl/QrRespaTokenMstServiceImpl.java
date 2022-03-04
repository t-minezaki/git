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
import jp.learningpark.modules.common.dao.QrRespaTokenMstDao;
import jp.learningpark.modules.common.entity.QrRespaTokenMstEntity;
import jp.learningpark.modules.common.service.QrRespaTokenMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("qrRespaTokenMstService")
@Transactional
public class QrRespaTokenMstServiceImpl extends ServiceImpl<QrRespaTokenMstDao, QrRespaTokenMstEntity> implements QrRespaTokenMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<QrRespaTokenMstEntity> page = baseMapper.selectPage(
                new Query<QrRespaTokenMstEntity>(params).getPage(),
                new QueryWrapper<QrRespaTokenMstEntity>()
        );

        return new PageUtils(page);
    }

}
