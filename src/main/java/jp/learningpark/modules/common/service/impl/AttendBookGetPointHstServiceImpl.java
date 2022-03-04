/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.common.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import jp.learningpark.modules.common.dao.AttendBookGetPointHstDao;
import jp.learningpark.modules.common.entity.AttendBookGetPointHstEntity;
import jp.learningpark.modules.common.service.AttendBookGetPointHstService;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.Query;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Map;

@Service("attendBookGetPointHstService")
@Transactional
public class AttendBookGetPointHstServiceImpl extends ServiceImpl<AttendBookGetPointHstDao, AttendBookGetPointHstEntity> implements AttendBookGetPointHstService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AttendBookGetPointHstEntity> page = baseMapper.selectPage(
                new Query<AttendBookGetPointHstEntity>(params).getPage(),
                new QueryWrapper<AttendBookGetPointHstEntity>()
        );

        return new PageUtils(page);
    }

}
