/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.entity.MentorStuEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MentorStuService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F00062Dto;
import jp.learningpark.modules.manager.service.F00062Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F00062")
public class F00062Controller {
    /**
     * 生徒保護者関係設定修正画面Service
     */
    @Autowired
    private F00062Service f00062Service;

    @Autowired
    private MentorStuService mentorStuService;

    @Autowired
    private MstOrgService mstOrgService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 組織表示
     *
     * @return
     */
    @RequestMapping(value = "/org", method = RequestMethod.GET)
    public R init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        return R.ok().put("org", org);
    }

    /**
     * 初期表示
     *
     * @param id メンター生徒管理・Id
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id) {
        //生徒、保護者情報を取得
        F00062Dto showData = f00062Service.getInitCont(id);
        if (showData == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "先生生徒関係"));
        }
        //デートフォーマット
        if (showData.getBirthd() != null){
            showData.setBirth(DateUtils.format(showData.getBirthd(), Constant.DATE_FORMAT_YYYYMD));
        }
        showData.setUpdDatm(DateUtils.format(showData.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        return R.ok().put("showData", showData);
    }

    /**
     * ユーザ情報を取得
     *
     * @param usrId ユーザID
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R selectStudent(String usrId, String roleDiv) {
        F00062Dto showData = new F00062Dto();
        if (StringUtils.equals(roleDiv, "4")) {
            try {
                //生徒情報を取得
                showData = f00062Service.selectStuInfo(usrId);
                //デートフォーマット
                if (showData.getBirthd() != null){
                    showData.setBirth(DateUtils.format(showData.getBirthd(), Constant.DATE_FORMAT_YYYYMD));
                }
                showData.setStu(usrId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        } else if (StringUtils.equals(roleDiv, "2")) {
            try {
                //メンター情報を取得
                showData = f00062Service.selectMentorInfo(usrId);
                showData.setMentor(usrId);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("showData", showData);
    }

    /**
     * 登録処理
     *
     * @param dto 更新データ
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public R update(F00062Dto dto, Integer id) {
        String upDtTm = "";
        MentorStuEntity entity = new MentorStuEntity();
        String usrId = ShiroUtils.getUserId();
        String orgId = "";
        if (id != null) {
            //更新
            // メンター生徒の関係重複チェック
            entity = mentorStuService.getOne(new QueryWrapper<MentorStuEntity>().eq("stu_id", dto.getStu())
                    .eq("mentor_id", dto.getMentor()).eq("crmsch_id",ShiroUtils.getUserEntity().getOrgId()).notIn("id", id).eq("del_flg", 0));
            if (entity != null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0034", "先生生徒関係"));
            }
            entity = mentorStuService.getOne(new QueryWrapper<MentorStuEntity>().eq("id", id).eq("del_flg", 0));
            try {
                upDtTm = DateUtils.format(entity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            //排他チェク
            if (!StringUtils.equals(upDtTm, dto.getUpdDatm())) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            } else {
                //生徒Id
                if (!StringUtils.isEmpty(dto.getStu())) {
                    entity.setStuId(dto.getStu());
                } else {
                    entity.setStuId(entity.getStuId());
                }
                //メンターId
                if (!StringUtils.isEmpty(dto.getMentor())) {
                    entity.setMentorId(dto.getMentor());
                } else {
                    entity.setMentorId(entity.getMentorId());
                }
                //更新日時
                entity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザId
                entity.setUpdUsrId(usrId);
                mentorStuService.updateById(entity);
            }
        } else {
            entity = mentorStuService.getOne(new QueryWrapper<MentorStuEntity>().eq("stu_id", dto.getStu())
                    .eq("mentor_id", dto.getMentor()).eq("crmsch_id",ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0));
            if (entity != null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0034", "先生生徒関係"));
            }
            //新規
            try {
                entity = new MentorStuEntity();
                //セッション・組織Id
                orgId = ShiroUtils.getUserEntity().getOrgId();
                //熟Id
                entity.setCrmschId(orgId);
                //生徒Id
                entity.setStuId(dto.getStu());
                //メンターId
                entity.setMentorId(dto.getMentor());
                //作成日時
                entity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザId
                entity.setCretUsrId(usrId);
                //更新日時
                entity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザId
                entity.setUpdUsrId(usrId);
                //削除フラグ
                entity.setDelFlg(0);
                try {
                    mentorStuService.save(entity);
                } catch (Exception e){
                    logger.error(e.getMessage());
                }
                return R.ok().put("id", entity.getId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok();
    }
}
