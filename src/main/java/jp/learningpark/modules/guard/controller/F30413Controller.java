/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.guard.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.GuardReadingStsEntity;
import jp.learningpark.modules.common.entity.GuidReprApplyStsEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.service.GuardReadingStsService;
import jp.learningpark.modules.common.service.GuidReprApplyStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstNoticeService;
import jp.learningpark.modules.guard.dto.F30112Dto;
import jp.learningpark.modules.guard.dto.F30413Dto;
import jp.learningpark.modules.guard.service.F30413Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * <p>報告書詳細画面</p >
 *
 * @author NWT : zpa <br />
 * 変更履歴 <br />
 * 2019/12/09 : zpa: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping(value = "/guard/F30413")
public class F30413Controller {

    /**
     * 報告書詳細画面 Service
     */
    @Autowired
    F30413Service f30413Service;

    /**
     * コードマスタ Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 保護者閲覧状況 Service
     */
    @Autowired
    GuardReadingStsService guardReadingStsService;

    /**
     * 保護者指導報告書閲覧状況 Service
     */
    @Autowired
    GuidReprApplyStsService guidReprApplyStsService;

    /**
     * お知らせ Service
     */
    @Autowired
    MstNoticeService mstNoticeService;

    /**
     * @param noticeId お知らせID
     * @param deliverCd 指導報告書配信コード
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer noticeId, String deliverCd, Integer guidReprId) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String stuId = ShiroUtils.getSessionAttribute("stuId").toString();

        //閲覧状況を更新
        F30112Dto f30112Dto = f30413Service.updateStatus(noticeId, orgId, stuId, ShiroUtils.getUserId(), guidReprId, deliverCd);
        //        if (f30112Dtos.size() != 0) {
        //            for (F30112Dto dto : f30112Dtos) {
        //                Integer grsId = dto.getGrsId();
        //                Integer grasId = dto.getGrasId();
        //                GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getById(grsId);
        //                GuidReprApplyStsEntity guidReprApplyStsEntity = guidReprApplyStsService.getById(grasId);
        //                guardReadingStsEntity.setReadingStsDiv("1");
        //                guidReprApplyStsEntity.setReadingStsDiv("1");
        //                guardReadingStsService.updateById(guardReadingStsEntity);
        //                guidReprApplyStsService.updateById(guidReprApplyStsEntity);
        //            }
        //        }
        //2020/11/11 huangxinliang modify start
        if (StringUtils.equals("0", f30112Dto.getReadStsDiv())) {
            Integer grsId = f30112Dto.getGrsId();
            GuardReadingStsEntity guardReadingStsEntity = guardReadingStsService.getById(grsId);
            guardReadingStsEntity.setReadingStsDiv("1");
            guardReadingStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            guardReadingStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            guardReadingStsService.updateById(guardReadingStsEntity);
        }
        if (StringUtils.equals("0", f30112Dto.getReadingStsDiv())){
            Integer grasId = f30112Dto.getGrasId();
            GuidReprApplyStsEntity guidReprApplyStsEntity = guidReprApplyStsService.getById(grasId);
            guidReprApplyStsEntity.setReadingStsDiv("1");
            guidReprApplyStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
            guidReprApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
            guidReprApplyStsService.updateById(guidReprApplyStsEntity);
        }
        //2020/11/11 huangxinliang modify end

        //生徒の全部指導報告書及び出席簿内容を抽出して、表示する
        MstNoticeEntity mstNoticeEntity = mstNoticeService.getOne(new QueryWrapper<MstNoticeEntity>().eq("id", noticeId));
        String tgtYmd = "";
        tgtYmd = mstNoticeEntity.getNoticeTitle().substring(mstNoticeEntity.getNoticeTitle().length() - 10).replace("/", "-");
        Date Ymd = DateUtils.parse(tgtYmd, GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        F30413Dto f30413DtoList = null;

        f30413DtoList = f30413Service.init(orgId, guidReprId, deliverCd, Ymd, stuId);

        if (f30413DtoList == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "指導報告書"));
        }
        // 2020/11/25 huangxinliang modify start
        R r = R.ok().put("dto", f30413DtoList).put("noticeLevelDiv", f30112Dto.getNoticeLevDiv()).
                put("grasId", f30112Dto.getGrasId()).put("openedFlg", f30112Dto.getOpenedFlg());
        // 2020/11/25 huangxinliang modify end
        if (f30413DtoList.getLectShapeDiv() != null) {
            List<String> lectList = Arrays.asList(f30413DtoList.getLectShapeDiv().split(","));
            List<MstCodDEntity> lectShapeDivList = mstCodDService.list(
                    new QueryWrapper<MstCodDEntity>().select("cod_value_2").eq("cod_key", "LECT_SHAPE_DIV").in("cod_cd", lectList));
            StringBuffer lsd = new StringBuffer();

            for (int i = 0; i < lectShapeDivList.size(); i++) {
                if (i != 0) {
                    lsd.append(",");
                }
                lsd.append(lectShapeDivList.get(i).getCodValue2());
            }
            r.put("lsd", lsd);
        }
        return r;
    }
//2020/11/11 huangxinliang add start
    /**
     *
     * @return
     */
    @RequestMapping(value = "/confirm", method = RequestMethod.GET)
    public R confirm(Integer grasId) {
        GuidReprApplyStsEntity guidReprApplyStsEntity = guidReprApplyStsService.getById(grasId);
        guidReprApplyStsEntity.setOpenedFlg("1");
        guidReprApplyStsEntity.setUpdUsrId(ShiroUtils.getUserEntity().getUsrId());
        guidReprApplyStsEntity.setUpdDatime(DateUtils.getSysTimestamp());
        guidReprApplyStsService.updateById(guidReprApplyStsEntity);
        return R.ok();
    }
    //2020/11/11 huangxinliang add end
}