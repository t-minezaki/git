/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.guard.dto.F30401Dto;
import jp.learningpark.modules.guard.service.F30112Service;
import jp.learningpark.modules.guard.service.F30401Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>塾からのイベント情報一覧画面</p >
 *
 * @author NWT : hujiaxing <br />
 * 変更履歴 <br />
 * 2019/07/29 : hujiaxing: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("guard/F30401/")
public class F30401Controller {
    /**
     * 塾からのイベント情報一覧画面 Service
     */
    @Autowired
    F30401Service f30401Service;
    /**
     * 塾からの連絡通知一覧画面 Service
     */
    @Autowired
    F30112Service f30112Service;

    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 初期表示
     *
     * @param limit limit
     * @param page  page
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page) {
        //セッション・生徒Id
        String stuId = (String) ShiroUtils.getSessionAttribute("stuId");
        //セッション・保護者Id
        String guardId = ShiroUtils.getUserId();
        //セッション・生徒組織Id
        String orgId = (String) ShiroUtils.getSessionAttribute("orgId");
        // セッションデータ．ブランドコード
//        String brandCd = ShiroUtils.getBrandcd();
        //コードマスタ取得
        MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value_2")
                .eq("cod_key", "LOCAL_IMG").eq("cod_value", orgId).eq("del_flg", 0));
        String imgPath = "";
        //コードマスタ・画像パス
        if (mstCodDEntity != null){
            imgPath = mstCodDEntity.getCodValue2();
        } else {
            imgPath = "../img/logo2.png";
        }
        //イベント情報未読件数
        Integer count = f30401Service.getNewsCount(guardId, stuId);

        //お知らせ未読カウント数
        Integer count1 = f30112Service.selectNoticeUnreadCount(guardId, stuId, orgId,null);

        //全部データ取得
        Integer total = f30401Service.selectNewsCount(stuId, guardId);
        //表示するデータ
        List<F30401Dto> showList = f30401Service.selectNews(stuId, guardId,limit, (page - 1) * limit);
        //コード最適化
////        for (F30401Dto dto : showList) {
////            if (StringUtils.isEmpty(dto.getImgPath())) {
////                dto.setImgPath(imgPath);
////            }
////        }
        return R.ok().put("showList", showList).put("total", total).put("count", count).put("count1", count1).put("imgPath",imgPath);
    }
}
