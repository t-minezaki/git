/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F07004Dto;
import jp.learningpark.modules.manager.service.F07004Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

/**
 * <p>教科追加・編集画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/04/04: xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f07004")
@RestController
public class F07004Controller {

    /**
     * 組織マスタService
     */
    @Autowired
    private MstOrgService mstOrgService;

    @Autowired
    private MstCodDService mstCodDService;

    @Autowired
    private F07004Service f07004Service;


    /**
     * <p>初期表示と検索</p>
     *
     * @param subCd 教科CD
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f07004Init(String subCd) {
        R info = new R();

        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity mstOrgEntity =
                mstOrgService.getOne(new QueryWrapper<MstOrgEntity>()
                        .and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        info.put("mstOrgEntity", mstOrgEntity);
        MstCodDEntity subjt = null;
        //学年情報取得
        List<MstCodDEntity> subjtList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd","cod_value").eq("cod_key", "SCHY_DIV").eq("del_flg", 0).orderByAsc("sort"));
        List<MstCodDEntity> checksubjtList = null;
        if (subCd != null) {
            //教科情報取得
            subjt = mstCodDService.getOne(new QueryWrapper<MstCodDEntity>().select("cod_value", "cod_value_2", "cod_cd", "cod_cont", "cod_value_3","cod_value_4","cod_value_5", "sort")
                    .eq("cod_key", "SUBJT_DIV").eq("cod_cd", subCd).eq("del_flg", 0));
            if (subjt == null) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("mstOrgEntity", mstOrgEntity);
            }else{
                //・	コードマスタ（B）．コードキー＝「SCHY_DIV」
                //・	コードマスタ（B）．コードCD IN コードマスタ（A）．コード値５（split(",")）
                //選択された教科情報取得
                if(subjt.getCodValue5() != null){
                    checksubjtList = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd","cod_value")
                            .eq("cod_key", "SCHY_DIV").in("cod_cd", (Object[]) subjt.getCodValue5().split(",")).eq("del_flg", 0).orderByAsc("sort"));
                }
            }
        }
        info.put("subjt", subjt);
        info.put("subjtList", subjtList);
        info.put("checksubjtList", checksubjtList);
        return info;
    }

    /**
     * 登録ボタン押下
     *
     * @param f07004Dto 画面内容
     * @param file1     画面．表示用画像
     * @param file2     画面．ボタン用画像
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/submit", method = RequestMethod.POST)
    public R getSubjtInfo(F07004Dto f07004Dto, MultipartFile file1, MultipartFile file2) throws IOException {
        return f07004Service.setSubjtInfo(f07004Dto, file1, file2);
    }


}
