package jp.learningpark.modules.guard.service.impl;

import jp.learningpark.modules.guard.dao.F30408Dao;
import jp.learningpark.modules.guard.dto.F30408Dto;
import jp.learningpark.modules.guard.service.F30408Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p>塾からのイベント情報詳細画面</p >
 */
@Transactional
@Service
public class F30408ServiceImpl implements F30408Service {

    @Autowired
    F30408Dao F30408Dao;

    /**
     * 初期化  ページ
     * @param eventId イベントID
     * @return
     */
    @Override
    public List<F30408Dto> getEventNews(Integer eventId, String guardId) {
        return F30408Dao.getEventNews(eventId, guardId);
    }

}
