/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MentorStuEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MentorStuService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F00061Dto;
import jp.learningpark.modules.manager.service.F00061Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F00061_メンター生徒関係検索一覧 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/3/18 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("manager/F00061/")
public class F00061Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * コードマスタ Service
     */
    @Autowired
    private MstCodDService mstCodDService;
    @Autowired
    private CommonService commonService;
    /**
     * F00061_メンター生徒関係検索一覧 Service
     */
    @Autowired
    private F00061Service f00061Service;

    /**
     * メンター生徒管理 Service
     */
    @Autowired
    private MentorStuService mentorStuService;

    @Autowired
    private MstUsrService mstUsrService;

    /**
     * 初期表示
     *
     * @param limit
     * @param page
     * @param dto   (stuId 画面．検索条件．生徒ID,mentorId 画面．検索条件．メンターID,stuNm    画面．検索条件．生徒姓名,mentorNm 画面．検索条件．メンター姓名,schyDiv  画面．検索条件．学年 ,orgId  画面  检索条件  組織ID)
     * @return
     */
    @RequestMapping(value = "init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, F00061Dto dto, String orgId) {
        if (!StringUtils.isEmpty(dto.getOrgIdStringList())) {
            dto.setOrgIdList((List<String>)JSON.parse(dto.getOrgIdStringList()));
        }
        //1.1 下記条件で、コードマスタより、学年区分を取得し画面の学年リストボックスで表示する。
        List<MstCodDEntity> codDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value", "sort").eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderByAsc("sort"));
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, ShiroUtils.getUserEntity().getOrgId());
        if (dto.getOrgId() == null || "".equals(dto.getOrgId())) {
            dto.setOrgId(!StringUtils.isEmpty(orgId)?orgId:ShiroUtils.getUserEntity().getOrgId());
        }
        MstOrgEntity org = mstOrgService.getOne(
                new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0)));
        R r = f00061Service.getList(dto, limit, page);
        return r.put("schyList", codDEntityList).put("org", org).put("orgList", orgIdList);
    }

    /**
     * メンター生徒管理の論理削除
     *
     * @param id メンター生徒管理．ＩＤ
     * @param updDatimeStr
     * @return
     */
    @RequestMapping(value="delete",method = RequestMethod.POST)
    public R delete(Integer id,String updDatimeStr){
        //ユーザＩＤ
        String userId=ShiroUtils.getUserId();

        MentorStuEntity mentorStuEntity=mentorStuService.getById(id);
        //排他チェックエラー
        if (mentorStuEntity == null || !StringUtils.equals(
                updDatimeStr, DateUtils.format(mentorStuEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS))) {
            return R.error(MessageUtils.getMessage("MSGCOMN0019"));
        }

        //更新日時
        mentorStuEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //更新ユーザＩＤ
        mentorStuEntity.setUpdUsrId(userId);
        //削除フラグ
        mentorStuEntity.setDelFlg(1);
        mentorStuService.updateById(mentorStuEntity);
        return R.ok();
    }
}
