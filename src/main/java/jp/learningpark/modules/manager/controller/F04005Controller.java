/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F04005Dto;
import jp.learningpark.modules.manager.service.F04005Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F04005 塾ニュース照会画面 Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/14 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F04005")
@RestController
public class F04005Controller extends AbstractController {

    /**
     * 共通 Service
     */
    @Autowired
    private CommonService commonService;

    /**
     * 組織マスタ Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * お知らせマスタ Service
     */
    @Autowired
    private MstNoticeService mstNoticeService;

    /**
     * お知らせマスタ Service
     */
    @Autowired
    private F04005Service f04005Service;

    /**
     * <p>初期表示</p>
     *
     * @param id セッションID
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f04005Init(Integer id) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //セッションデータ．組織マスタ
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        //お知らせマスタを元に取得
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getById(id);
        //配信先組織リストを取得
        List<F04005Dto> stuList = f04005Service.getStuList(id,orgId);
        int listLength = 0;
        if (stuList.size() > 0) {
            if (stuList.size() % 3 == 0) {
                listLength = stuList.size() / 3;
            } else {
                listLength = stuList.size() / 3 + 1;
            }
        }
        return R.ok().put("mstOrgEntity", mstOrgEntity).put("mstNoticeEntity", mstNoticeEntity).put("stuList", stuList).put("listLength", listLength);
    }
}
