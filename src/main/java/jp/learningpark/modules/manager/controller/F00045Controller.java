/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.manager.dto.F00045Dto;
import jp.learningpark.modules.manager.service.F00045Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>生徒保護者関係設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/18 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F00045")
public class F00045Controller {
    /**
     * 生徒保護者関係設定修正画面Service
     */
    @Autowired
    private F00045Service f00045Service;

    @Autowired
    private MstStuService mstStuService;

    @Autowired
    private MstOrgService mstOrgService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 初期表示
     *
     * @param stuId 生徒Id
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String stuId) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));

        //生徒、保護者情報を取得
        List<F00045Dto> showData = f00045Service.getInitCont(stuId);
        for (F00045Dto dto:showData) {
            //デートフォーマット
            if (dto.getBirthd() != null && !StringUtils.isEmpty(dto.getBirthd().toString())) {
                dto.setBirth(DateUtils.format(dto.getBirthd(), Constant.DATE_FORMAT_YYYYMD));
            }
            dto.setUpdDatm(DateUtils.format(dto.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
        }
        return R.ok().put("showData", showData).put("org", org);
    }

    /**
     * 保護者情報を取得
     *
     * @param guardId 保護者Id
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R select(String guardId) {
        F00045Dto showData = new F00045Dto();
        try {
            //保護者情報を取得
            showData = f00045Service.selectGuardInfo(guardId);
        } catch (Exception e) {
            logger.error(e.getMessage());
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
    public R update(F00045Dto dto) {
        String upDtTm = "";
        MstStuEntity entity = new MstStuEntity();
        String usrId = ShiroUtils.getUserId();
        String guardId = "";
        List<String> guardIdList = new ArrayList<>();
        try {
            entity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", dto.getStuId()).eq("del_flg", 0));
            upDtTm = DateUtils.format(entity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        //排他チェク
        if (!StringUtils.equals(upDtTm, dto.getUpdDatm())) {
            throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
        } else {
            guardId = dto.getGuardId();
            guardIdList = (List<String>) JSON.parse(guardId);
            while (guardIdList.size()<5){
                guardIdList.add(null);
            }
            entity.setGuardId(guardIdList.get(0));
            entity.setGuard1Id(guardIdList.get(1));
            entity.setGuard2Id(guardIdList.get(2));
            entity.setGuard3Id(guardIdList.get(3));
            entity.setGuard4Id(guardIdList.get(4));
            entity.setUpdDatime(DateUtils.getSysTimestamp());
            entity.setUpdUsrId(usrId);
            mstStuService.update(entity,new QueryWrapper<MstStuEntity>().eq("stu_id", dto.getStuId()).eq("del_flg", 0));
        }
        return R.ok();
    }
}
