/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * <p>F07002_学年追加・編集画面</p >
 *
 * @author NWT : tan <br />
 * 変更履歴 <br />
 * 2019/04/02 : tan: 新規<br />
 * @version 1.0
 */

@RequestMapping("/pop/F07002")
@RestController
public class F07002Controller {
    /**
     * 学年追加・編集画面
     */
    @Autowired
    MstCodDService mstCodDService;

    @Autowired
    MstOrgService mstOrgService;

    /**
     * 初期化
     *
     * @param codCd セッションデータ．学年CD
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(String codCd) {
        //セッションデータの値
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", ShiroUtils.getUserEntity().getOrgId()).eq("del_flg", 0)));
        //引渡データ．学年CDがない場合
        if (codCd == null) {
            return R.ok().put("org", org);
        } else {
            //引渡データ．学年CDがある場合
            MstCodDEntity mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_key", "cod_cd", "cod_value", "sort").and(w -> w.eq("cod_key", "SCHY_DIV").eq("cod_cd", codCd).eq("del_flg", 0)));
            if (mstCodDEntity == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年")).put("org", org);
            }
            return R.ok().put("schy", mstCodDEntity.getCodValue()).put("sort", mstCodDEntity.getSort()).put("org", org);
        }
    }

    /**
     * 登録ボタン押下時
     *
     * @param schy  学年名
     * @param codCd セッションデータ．学年CD
     * @param sort  ソート
     * @return
     */
    @RequestMapping(value = "/submit", method = RequestMethod.GET)
    public R submit(String schy, String codCd, Integer sort) {
        MstCodDEntity mstCodDEntity;
        //引渡データ．学年CDがない場合
        if (codCd == null) {
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_key", "cod_cd", "cod_value").and(w -> w.eq("cod_key", "SCHY_DIV").eq("cod_value", schy).eq("del_flg", 0)));
            if (mstCodDEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "学年名"));
            }
            //新規登録
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("max(cod_cd) as cod_cd").and(w -> w.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)));
            //連番採番
            Integer maxCodCd = Integer.parseInt(mstCodDEntity.getCodCd());
            maxCodCd++;
            mstCodDEntity = new MstCodDEntity();
            //コードキー
            mstCodDEntity.setCodKey("SCHY_DIV");
            //コードCD
            mstCodDEntity.setCodCd(maxCodCd + "");
            //コード値
            mstCodDEntity.setCodValue(schy);
            //ソート
            mstCodDEntity.setSort(sort);
            //作成日時
            mstCodDEntity.setCretDatime(DateUtils.getSysTimestamp());
            //作成ユーザＩＤ
            mstCodDEntity.setCretUsrId(ShiroUtils.getUserId());
            //更新日時
            mstCodDEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstCodDEntity.setUpdUsrId(ShiroUtils.getUserId());
            //削除フラグ
            mstCodDEntity.setDelFlg(0);
            //新規登録
            mstCodDService.save(mstCodDEntity);
        } else {
            //引渡データ．学年CDがある場合
            mstCodDEntity = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_key", "cod_cd", "cod_value").and(w -> w.eq("cod_key", "SCHY_DIV").eq("cod_value", schy).ne("cod_cd", codCd).eq("del_flg", 0)));
            if (mstCodDEntity != null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0097", "学年名"));
            }
            mstCodDEntity = new MstCodDEntity();
            //コード値
            mstCodDEntity.setCodValue(schy);
            //ソート
            mstCodDEntity.setSort(sort);
            //更新日時
            mstCodDEntity.setUpdDatime(DateUtils.getSysTimestamp());
            //更新ユーザＩＤ
            mstCodDEntity.setUpdUsrId(ShiroUtils.getUserId());
            //更新
            mstCodDService.update(mstCodDEntity, new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_sort", "upd_datime", "upd_usr_id").and(w -> w.eq("cod_key", "SCHY_DIV").eq("cod_cd", codCd).eq("del_flg", 0)));
        }
        return R.ok();
    }
}