/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.guard.dto.F30110Dto;
import jp.learningpark.modules.guard.service.F30110Service;
import jp.learningpark.modules.xapi.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>塾ニュース一覧画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/07 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30110/")
public class F30110Controller {
    /**
     * 塾ニュース一覧画面 Service
     */
    @Autowired
    F30110Service f30110Service;

    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     *
     * @param limit limit
     * @param page page
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String url) {
        //セッション・生徒Id
        String stuId = (String)ShiroUtils.getSessionAttribute("stuId");
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = (String)ShiroUtils.getSessionAttribute("orgId");
        // セッションデータ．ブランドコード
//        String brandCd = ShiroUtils.getBrandcd();
        // //コードマスタ・画像パス
        String imgPath = "";
        //コードマスタ取得
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(
                new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "LOCAL_IMG").eq("cod_value", orgId).eq("del_flg", 0));
        if (mstCodDEntity != null) {
            //コードマスタ・画像パス
            imgPath = mstCodDEntity.getCodValue2();
        } else {
            imgPath = "../img/logo2.png";
        }
        //お知らせ未読カウント数
        Integer count = f30110Service.getNewsCount(guardId, stuId, orgId);

        //全部データ取得
        Integer total = f30110Service.selectNewsCount(stuId, guardId, orgId);
        //表示するデータ
        List<F30110Dto> showList = f30110Service.selectNews(stuId, guardId, orgId, limit, (page - 1) * limit);
        //塾ニュースＩＤリスト
        List idList = new ArrayList();
        for (F30110Dto dto : showList) {
            idList.add(dto.getId());
            if (StringUtils.isEmpty(dto.getNoticeImgPath())) {
                dto.setNoticeImgPath(imgPath);
            }
        }
        //ビッグデータ
        Extensions exts = new Extensions();
        //利用者のシステムID
        exts.put(XApiConstant.EXT_KEY_USER_ID, ShiroUtils.getUserId());
        //当画面URL
        exts.put(XApiConstant.EXT_KEY_URL, url);
        //当画面訪問時間
        exts.put(XApiConstant.EXT_KEY_VISIT_TIME, DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS));
        //塾ニュースＩＤリスト
        exts.put(XApiConstant.EXT_KEY_NOTICE_ID, idList);
        //生徒ＩＤ
        exts.put(XApiConstant.EXT_KEY_STU_ID, stuId);
        try {
            XApiUtils.saveStatement(Verbs.launched(), Activitys.academy(), exts);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok().put("showList", showList).put("total", total).put("count", count);

    }
}
