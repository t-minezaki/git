/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.dao.MstCodDDao;
import jp.learningpark.modules.common.dao.MstStuDao;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.guard.dto.F30301ContentsDto;
import jp.learningpark.modules.guard.dto.F30301Dto;
import jp.learningpark.modules.guard.service.F30301Service;
import jp.learningpark.modules.xapi.Activitys;
import jp.learningpark.modules.xapi.Extensions;
import jp.learningpark.modules.xapi.Verbs;
import jp.learningpark.modules.xapi.XApiConstant;
import jp.learningpark.modules.xapi.XApiUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>F30301_トップニュース一覧画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/02/27 : xiong: 新規<br />
 */
@RestController
@RequestMapping("guard/F30301/")
public class F30301Controller {
    @Autowired
    F30301Service f30301Service;

    @Autowired
    MstCodDDao mstCodDDao;

    @Autowired
    MstStuDao mstStuDao;

    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期化  ページ
     *
     * @param offset 翻訳ページ
     * @param url 当画面URL
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R f30301init(String url, Integer offset) {

        // セッションデータ．組織ＩＤ
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 保護者ID
        String guardId = ShiroUtils.getUserId();
        // 生徒ID
        String stuId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.STU_ID);
        // お知らせ未読カウント数
        int count = f30301Service.getNewsCount(guardId, stuId, orgId);
        // bigdata newsId
        List newsIdList = new ArrayList();
        List<F30301ContentsDto> contentsDtos;
        if (offset == 0) {
            // API 取得
            contentsDtos = httpRequestUtils(0);
        } else {
            // 記事一覧のオフセット (デフォルト0)
            offset = offset + 30;
            // API 取得
            contentsDtos = httpRequestUtils(offset);
        }

        for (F30301ContentsDto content : contentsDtos) {
            newsIdList.add(content.getId());
        }
        // 画像なしのロゴ文字画像の取得
        // コードマスタ取得
        String imgPath = "";
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "LOCAL_IMG").eq("cod_value", "0").eq("del_flg", 0));
        if (mstCodDEntity != null) {
            //コードマスタ・画像パス
            imgPath = mstCodDEntity.getCodValue2();
        }
        // bigData
        Extensions exts = new Extensions();
        // 利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        // ログイン時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM_SS));
        // 生徒ID
        exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
        // ニュースＩＤリスト
        exts.put(XApiConstant.EXT_KEY_NEWS_ID, newsIdList);
        // 画面URL
        exts.put(XApiConstant.EXT_KEY_URL, url);
        try {
            XApiUtils.saveStatement(Verbs.launched(), Activitys.academy(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok().put("contents", contentsDtos).put("count", count).put("imgPath", imgPath).put("offset", offset);
    }

    /**
     * API
     *
     * @param offset 翻訳ページ
     * @return
     */
    public List<F30301ContentsDto> httpRequestUtils(Integer offset) {

        //API url
        String url = MessageUtils.getMessage("url.kamelio-api");
        // トピックＩＤの取得
        String topic = mstCodDDao.selectOne(
                new QueryWrapper<MstCodDEntity>().select("cod_key", "cod_cd").eq("del_flg", 0).eq("cod_key", "TOPIC_ID")).getCodCd();
        // ソート項目
        String[] array = {"-published_at"};
        // リクエストデータ
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("limit", 30);
        map.put("offset", offset);
        map.put("id", Integer.parseInt(topic));
        map.put("sort_orders", array);
        String msg = JSON.toJSONString(map);

        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder();
        try {
            URL reqUrl = new URL(url);
            // 接続を立てる
            URLConnection conn = reqUrl.openConnection();
            // お願い頭を設置する
            conn.setRequestProperty("Content-Type", "application/json");
            conn.setRequestProperty("X-KML-APP-TOKEN", "ten6k3qohvip49gz");

            conn.setDoOutput(true);
            conn.setDoInput(true);
            // 書き込みパラメータ
            out = new PrintWriter(conn.getOutputStream());
            // パラメータを設定して、直接とパラメータを書くことができます。
            out.print(msg);
            //            out.print("{\"limit\":30,\"offset\":30,\"id\":1162}");"sort_orders":"- published_at",
            // フラッシュ出力の緩衝
            out.flush();

            // Bufered Roler入力フローを定義してURLの応答を読み取る
            in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line);
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        // String to JavaBean
        F30301Dto f30301Dto = JSON.parseObject(result.toString(), new TypeReference<F30301Dto>() {
        });
        // 捕捉内容
        List<F30301ContentsDto> contentsDtos = f30301Dto.getContents();

        return contentsDtos;
    }
}