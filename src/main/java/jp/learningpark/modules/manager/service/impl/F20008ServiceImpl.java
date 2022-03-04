/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.service.impl;

import jp.learningpark.modules.manager.dao.F20008Dao;
import jp.learningpark.modules.manager.service.F20008Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>学習状況分析画面（週、月、年）</p >
 *
 * @author NWT : huangyong <br />
 * 変更履歴 <br />
 * 2018/11/13 : huangyong: 新規<br />
 * @version 1.0
 */
@Service
public class F20008ServiceImpl implements F20008Service {
    /**
     * 生徒ウィークリー計画実績設定 ExtendDAO
     */
    @Autowired
    private F20008Dao f20008Dao;

    @Override
    public List<Map<String, Object>> getDegreeTotal(Map<String, Object> map) {
        List<Map<String, Object>> degreeList = f20008Dao.getDegreeTotal(map);
        if (degreeList == null) {
            degreeList=new ArrayList<>();
            Map<String, Object> degree = new HashMap<>();
            degree.put("lev0", 0);
            degree.put("lev1", 0);
            degree.put("lev2", 0);
            degree.put("lev3", 0);
            degree.put("lev4", 0);
            degree.put("subjtdiv", "");
            degree.put("subjtnm", "未選択");
            degreeList.add(degree);
        }
        return degreeList;
    }

    @Override
    public Map<String, Object> getLearnTimeCircleTotal(Map<String, Object> map) {
        Map<String, Object> timeCircle = f20008Dao.getLearnTimeCircleTotal(map);
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
        Map<String, Object> timeHorizontal = f20008Dao.getLearnTimeHorizontalTotal(map);
        if (timeHorizontal == null) {
            timeHorizontal = new HashMap<>();
            timeHorizontal.put("perftm", 0);
            timeHorizontal.put("stuplantm", 0);
            timeHorizontal.put("plantm", 0);
        }
        return timeHorizontal;
    }
}
