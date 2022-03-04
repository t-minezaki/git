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
import jp.learningpark.modules.common.dao.VariousScannerTokenMstDao;
import jp.learningpark.modules.common.entity.VariousScannerTokenMstEntity;
import jp.learningpark.modules.common.service.VariousScannerTokenMstService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@Service("variousScannerTokenMstService")
@Transactional
public class VariousScannerTokenMstServiceImpl extends ServiceImpl<VariousScannerTokenMstDao, VariousScannerTokenMstEntity> implements VariousScannerTokenMstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<VariousScannerTokenMstEntity> page = baseMapper.selectPage(
                new Query<VariousScannerTokenMstEntity>(params).getPage(),
                new QueryWrapper<VariousScannerTokenMstEntity>()
        );

        return new PageUtils(page);
    }

}
