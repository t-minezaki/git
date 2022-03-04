/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F10401Dao;
import jp.learningpark.modules.student.service.F10401Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>学習状況分析画面（週、月、年）</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/11/26 : hujunjie: 新規<br />
 * @version 1.0
 */
@Service
public class F10401ServiceImpl implements F10401Service {
    /**
     * 生徒ウィークリー計画実績設定 ExtendDAO
     */
    @Autowired
    private F10401Dao f10401Dao;

    @Override
    public List<Map<String, Object>> getDegreeTotal(Map<String, Object> map) {
        List<Map<String, Object>> degreeList = f10401Dao.getDegreeTotal(map);
        if (degreeList == null) {
            degreeList = new ArrayList<>();
            Map<String, Object> degree = new HashMap<>();
            degree.put("lev0", 0);
            degree.put("lev1", 0);
            degree.put("lev2", 0);
            degree.put("lev3", 0);
            degree.put("lev4", 0);
            degree.put("subjtnm", "未選択");
            degreeList.add(degree);
        }
        return degreeList;
    }

    @Override
    public Map<String, Object> getLearnTimeCircleTotal(Map<String, Object> map) {
        Map<String, Object> timeCircle = f10401Dao.getLearnTimeCircleTotal(map);
        if (timeCircle == null) {
            timeCircle = new HashMap<>();
            timeCircle.put("perftm", 0);
            timeCircle.put("stuplantm", 0);
            timeCircle.put("proportion", 0);
        }
        return timeCircle;
    }

    @Override
    public Map<String, Object> getLearnTimeHorizontalTotal(Map<String, Object> map) {
        Map<String, Object> timeHorizontal = f10401Dao.getLearnTimeHorizontalTotal(map);
        if (timeHorizontal == null) {
            timeHorizontal = new HashMap<>();
            timeHorizontal.put("perftm", 0);
            timeHorizontal.put("stuplantm", 0);
            timeHorizontal.put("plantm", 0);
        }
        return timeHorizontal;
    }
}
