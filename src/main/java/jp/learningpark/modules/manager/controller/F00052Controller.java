/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstOrgService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>グループ設定修正画面</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2019/03/20 : hujunjie: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F00052")
public class F00052Controller {
    @Autowired
    private MstOrgService mstOrgService;

    @Autowired
    private MstGrpService mstGrpService;

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
     * 組織表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id) {
        String grpNm = "";
        String upDtTm = "";
        MstGrpEntity mstGrpEntity = null;
        //グループデータを取得
        if (id != null){
            mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("grp_id", id).eq("del_flg", 0));
            //存在チェック
            if (mstGrpEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "グループ"));
            }
            try {
                grpNm = mstGrpEntity.getGrpNm();
                //更新時間取得
                upDtTm = DateUtils.format(mstGrpEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("grpNm", grpNm).put("upDtTm", upDtTm).put("mstGrpEntity", mstGrpEntity);
    }

    /**
     * 登録処理
     *
     * @param entity
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.GET)
    public R update(MstGrpEntity entity, String updDtTm) {
        //セッション・組織Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //セッション・ユーザId
        String usrId = ShiroUtils.getUserId();
        MstGrpEntity mstGrpEntity = new MstGrpEntity();
        String upDtime = "";
        if (entity.getGrpId() != null) {
            //更新
            mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("org_id", orgId)
                    .eq("grp_nm", entity.getGrpNm()).notIn("grp_id", entity.getGrpId()).eq("del_flg", 0));
            //重複チェク
            if (mstGrpEntity != null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0034", "グループ名"));
            }

            //当データ取得
            mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("grp_id", entity.getGrpId()).eq("del_flg", 0));
            try {
                upDtime = DateUtils.format(mstGrpEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
            //排他チェク
            if (!StringUtils.equals(upDtime, updDtTm)) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0019"));
            } else {
                //グループ名
                mstGrpEntity.setGrpNm(entity.getGrpNm());
                //曜日区分
                mstGrpEntity.setDayweekDiv(entity.getDayweekDiv());
                //作成日時
                mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstGrpEntity.setUpdUsrId(usrId);
                mstGrpService.updateById(mstGrpEntity);
            }
        } else {
            //新規
            mstGrpEntity = mstGrpService.getOne(new QueryWrapper<MstGrpEntity>().eq("org_id", orgId)
                    .eq("grp_nm", entity.getGrpNm()).eq("del_flg", 0));
            //重複チェク
            if (mstGrpEntity != null) {
                throw new RRException(MessageUtils.getMessage("MSGCOMN0034", "グループ名"));
            }
            try {
                mstGrpEntity = new MstGrpEntity();
                //組織Id
                mstGrpEntity.setOrgId(orgId);
                //学年区分
                mstGrpEntity.setDayweekDiv(entity.getDayweekDiv());
                //グループ名
                mstGrpEntity.setGrpNm(entity.getGrpNm());
                //作成日時
                mstGrpEntity.setCretDatime(DateUtils.getSysTimestamp());
                //作成ユーザＩＤ
                mstGrpEntity.setCretUsrId(usrId);
                //更新日時
                mstGrpEntity.setUpdDatime(DateUtils.getSysTimestamp());
                //更新ユーザＩＤ
                mstGrpEntity.setUpdUsrId(usrId);
                //削除フラグ
                mstGrpEntity.setDelFlg(0);
                mstGrpService.save(mstGrpEntity);
                return R.ok().put("id", mstGrpEntity.getGrpId());
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok();
    }
}
