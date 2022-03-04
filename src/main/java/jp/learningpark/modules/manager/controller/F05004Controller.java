/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F05003Dto;
import jp.learningpark.modules.manager.dto.F05004DtoStu;
import jp.learningpark.modules.manager.service.F05004Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F05004_知らせ照会画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * 2019/06/18 : hujiaxing: mod<br />
 * @version 1.0
 */
@RequestMapping("/manager/F05004")
@RestController
public class F05004Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * お知らせ Service
     */
    @Autowired
    private MstNoticeService mstNoticeService;

    /**
     * 知らせ照会画面 Service
     */
    @Autowired
    private F05004Service f05004Service;

    /**
     * <p>初期表示</p>
     *
     * @param noticeId お知らせ．ＩＤ
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f05004Init(Integer noticeId) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));

        //1.1  お知らせを初期表示するため、お知らせマスタから最新の知らせ情報を取得し、画面で表示される。
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getById(noticeId);

        //get student List by noticeId
        List<F05004DtoStu> stuList = f05004Service.getStuListByNoticeId(noticeId,orgId);
        int eachCount = 0;
        if (stuList.size() != 0) {
            if (stuList.size() % 3 != 0) {
                eachCount = stuList.size() / 3 + 1;
            }else{
                eachCount = stuList.size() / 3;
            }

        }
        F05003Dto dto = new F05003Dto();

        //最新更新日時
        dto.setUpdateStr(DateUtils.format(mstNoticeEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));

        //掲載予定開始日時
        dto.setPubPlanStartDtStr(DateUtils.format(mstNoticeEntity.getPubPlanStartDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));

        //掲載予定終了日時
        dto.setPubPlanEndDtStr(DateUtils.format(mstNoticeEntity.getPubPlanEndDt(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_HH_MM));

        return R.ok().put("org", org)
                .put("notice", mstNoticeEntity)
                .put("stuList", stuList)
                .put("eachCount",eachCount)
                .put("dto", dto);
    }

}