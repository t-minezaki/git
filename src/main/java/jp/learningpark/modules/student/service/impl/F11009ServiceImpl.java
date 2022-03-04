package jp.learningpark.modules.student.service.impl;

import jp.learningpark.modules.student.dao.F11009Dao;
import jp.learningpark.modules.student.service.F11009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 無
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/04/22 ： NWT)hxl ： 新規作成
 * @date 2020/04/22 10:39
 */
@Service
public class F11009ServiceImpl implements F11009Service {
    /**
     * 生徒ウィークリー計画実績設定 ExtendDAO
     */
    @Autowired
    private F11009Dao f11009Dao;

    @Override
    public List<Map<String, Object>> getDegreeTotal(Map<String, Object> map) {
        List<Map<String, Object>> degreeList = f11009Dao.getDegreeTotal(map);
        if (degreeList == null) {
            degreeList=new ArrayList<>();
            Map<String, Object> degree = new HashMap<>();
            degree.put("lev0", 0);
            degree.put("lev4", 0);
            degree.put("lev1to3", 0);
            degree.put("subjtnm", "未選択");
            degreeList.add(degree);
        }
        return degreeList;
    }

    @Override
    public Map<String, Object> getLearnTimeCircleTotal(Map<String, Object> map) {
        Map<String, Object> timeCircle = f11009Dao.getLearnTimeCircleTotal(map);
        if (timeCircle == null) {
            timeCircle = new HashMap<>();
            timeCircle.put("perftm", 0);
            timeCircle.put("stuplantm", 0);
            timeCircle.put("proportion", 0);
        }
        return timeCircle;
    }
}
