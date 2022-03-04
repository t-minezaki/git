package jp.learningpark.modules.pop.service.impl;

import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.pop.dao.F08004Dao;
import jp.learningpark.modules.pop.dto.F08004Dto;
import jp.learningpark.modules.pop.service.F08004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class F08004ServiceImpl implements F08004Service {

    @Autowired
    private F08004Dao f08004Dao;

    @Override
    public F08004Dto getEventEntity(Integer eventId) {

        return f08004Dao.selectEventEntity(eventId);
    }

    @Override
    public List<MstOrgEntity> getOrgList(Integer eventId) {
        return f08004Dao.selectOrgList(eventId);
    }
}