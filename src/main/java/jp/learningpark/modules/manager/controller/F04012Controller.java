package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.manager.dto.F04012Dto;
import jp.learningpark.modules.manager.service.F04012Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * マナミルチャンネル新規·編集 Controller
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/02/10 ： NWT)hxl ： 新規作成
 * @date 2020/02/10 13:30
 */
@RequestMapping("/manager/F04012")
@RestController
public class F04012Controller {

    /**
     * f04012Service
     */
    @Autowired
    private F04012Service f04012Service;

    /**
     * 初期化
     *
     * @param id お知らせID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f04012Init(Integer id) {
        return f04012Service.init(id);
    }

    /**
     * データとファイルを保存する
     *
     * @param f04012Dto お知らせ
     * @param files     ファイル
     * @param id        お知らせID
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/saveOrUpdate", method = RequestMethod.POST)
    public R f04012SaveOrUpdate(String f04012Dto, MultipartFile[] files, Integer id, Timestamp lastUpdateTime) throws Exception {
        //String -> JavaBean
        F04012Dto dto = JSON.parseObject(StringUtils.defaultString(f04012Dto), new TypeReference<F04012Dto>() {
        });

        if (dto.getEndDate().compareTo(dto.getStartDate()) < 0) {
            //開始日が終了日より大きい
            return R.error(MessageUtils.getMessage("MSGCOMN0048", "掲載開始日時", "掲載終了日時"));
        }
        Map<String, Object> params = new HashMap<>(8);
        //F04012Dto
        params.put("dto", dto);
        //file
        params.put("files", files);
        //組織ID
        params.put("orgId", ShiroUtils.getUserEntity().getOrgId());
        //ユーザーID
        params.put("userId", ShiroUtils.getUserId());
        //新しいルールと編集ステータスの判定
        if (id == -1) {
            //新しいルール
            return f04012Service.save(params);
        }else {
            //編集
            params.put("id", id);
            params.put("lastUpdateTime", lastUpdateTime);
            return f04012Service.update(params);
        }
    }

}
