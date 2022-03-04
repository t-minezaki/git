package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.manager.service.F21018Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * F21018Controller
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/20 ： NWT)hxl ： 新規作成
 * @date 2020/02/20 17:31
 */
@RestController
@RequestMapping("/manager/F21018")
public class F21018Controller {

    /**
     * f21018Service
     */
    @Autowired
    F21018Service f21018Service;

    /**
     * 情報を取得する
     *
     * @param stuId 生徒ID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R getResult(String stuId) {
        return f21018Service.init(stuId);
    }

    /**
     *更新情報
     *
     * @param file          ファイル
     * @param memo          メモ
     * @param englishName   英語名
     * @param dayweek       曜日
     * @param stuId         生徒ID
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    public R update(String file, String memo, String englishName, String dayweek, String stuId, Timestamp updDatime) throws IOException {
        Map<String, Object> params = new HashMap<>(8);
        //ログインユーザーID
        params.put("userId", ShiroUtils.getUserId());
        //メモ
        params.put("memo", memo);
        //英語名
//        params.put("englishName", englishName);
        //曜日
        params.put("dayweek", dayweek);
        //生徒ID
        params.put("stuId", stuId);
//        if (!file.isEmpty()){
//            //写真交換判定
//            params.put("file", file);
//        }
        params.put("updDatime", updDatime);
        return f21018Service.update(params);
    }
}
