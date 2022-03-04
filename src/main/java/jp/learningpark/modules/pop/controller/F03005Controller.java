/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.pop.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUnitService;
import jp.learningpark.modules.pop.dto.F03005Dto;
import jp.learningpark.modules.pop.service.F03005Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * <p>F03005_単元追加・編集画面  Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/13 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/pop/F03005")
@RestController
public class F03005Controller extends AbstractController {
    /**
     * <p>単元追加・編集画面</p>
     * <p>
     * F03005Service Service
     */
    @Autowired
    F03005Service f03005Service;

    /**
     * コードマスタ　Service
     */
    @Autowired
    MstCodDService mstCodDService;

    /**
     * 組織マスタ　Service
     */
    @Autowired
    MstOrgService mstOrgService;

    /**
     * 単元マスタ　Service
     */
    @Autowired
    MstUnitService mstUnitService;

    /**
     * @param id 引渡データ．ＩＤ
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f03005Init(Integer id) {
        //学年リスト
        List<MstCodDEntity> mstCodDEntitySchyDivList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        //教科リスト
        String orderRule = "case\n" +
                "            WHEN cod_cd = 'k1' then 1\n" +
                "            WHEN cod_cd = 'm1' or cod_cd = 'm2' then 2\n" +
                "            WHEN cod_cd = 'r1' then 3\n" +
                "            WHEN cod_cd = 's4' then 4\n" +
                "            WHEN cod_cd = 'e1' then 5\n" +
                "            WHEN cod_cd = 's5' then 6\n" +
                "            WHEN cod_cd = 'r2' then 7\n" +
                "            WHEN cod_cd = 'r3' then 8\n" +
                "            WHEN cod_cd = 'r4' then 9\n" +
                "            WHEN cod_cd = 'r5' then 10\n" +
                "            WHEN cod_cd = 's1' then 11\n" +
                "            WHEN cod_cd = 's2' then 12\n" +
                "            WHEN cod_cd = 's3' then 13\n" +
                "            WHEN cod_cd = 't1' then 14\n" +
                "            WHEN cod_cd = 'o1' then 15\n" +
                "            WHEN cod_cd = 'z1' then 16 end";
        List<MstCodDEntity> mstCodDEntitySubjtDivList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "SUBJT_DIV").eq("cod_value_4","0").eq("del_flg", 0)).orderByAsc(orderRule));
        //セッション組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        MstUnitEntity mstUnitEntity = null;
        F03005Dto f03005Dto = new F03005Dto();
        if (id != null) {
            mstUnitEntity = mstUnitService.getById(id);
            if (mstUnitEntity != null) {
                f03005Dto.setUpdateTimeStr(DateUtils.format(mstUnitEntity.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            }
        }
        return R.ok().put("f03005Dto", f03005Dto)
                .put("mstUnitEntity", mstUnitEntity)
                .put("mstOrgEntity", mstOrgEntity)
                .put("mstCodDEntitySchyDivList", mstCodDEntitySchyDivList)
                .put("mstCodDEntitySubjtDivList", mstCodDEntitySubjtDivList);
    }

    /**
     * <p>追加登録</p>
     *
     * @param f03005Dto 単元追加・編集画面 Dto
     * @return
     */
    @RequestMapping(value = "/Add", method = RequestMethod.POST)
    public R f03005add(F03005Dto f03005Dto) {
        return f03005Service.addMstUnitInfo(f03005Dto);
    }
}
