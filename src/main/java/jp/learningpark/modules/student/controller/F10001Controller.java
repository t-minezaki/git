/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.student.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstSchService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.student.dto.F10001Dto;
import jp.learningpark.modules.student.service.F10001Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * <p>F10001 情報登録変更画面</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/14 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/student/F10001")
public class F10001Controller extends AbstractController {

    /**
     * 学校マスタ Service
     */
    @Autowired
    private MstSchService schMstService;

    /**
     * ユーザ基本マスタ　Service
     */
    @Autowired
    private MstUsrService mstUsrService;

    /**
     * コードマスタ_明細 Service
     */
    @Autowired
    private MstCodDService codMstDService;

    /**
     * F10001 情報登録変更画面 Service
     */
    @Autowired
    private F10001Service f10001Service;

    /**
     * <p>F10002 生徒個人データを表示する</p>
     *
     * @return stuDto, birthFormat
     */
    @RequestMapping(value = "/getStuDataInfo", method = RequestMethod.GET)
    @RequiresPermissions("student")
    public R getStuMyPage() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //1.1性別リストを取得し
        List<MstCodDEntity> gendrList = codMstDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "GENDR_DIV").eq("del_flg", 0)).orderByAsc("sort"));

        //1.2学年リスト
        List<MstCodDEntity> schyList = codMstDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc("sort"));

//        //1.3 学校マスタから取得し、画面処理表示を行う
//        List<MstSchEntity> schList = schMstService.list(new QueryWrapper<MstSchEntity>().select("id", "sch_cd", "sch_nm").and(wrapepr -> wrapepr.eq("del_flg", 0)));

        //生徒ID
        String stuId = getUserCd();

        //登録アカウントエンティティを取得する
        MstUsrEntity mstUsrEntity= mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",stuId));

        // 生徒個人データDto
        F10001Dto stuMst = f10001Service.getByStuId(stuId, orgId);

        if (stuMst == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒基本マスタ"));
        }
        if (stuMst.getBirthd() != null) {
            stuMst.setBirthdayString(DateUtils.format(stuMst.getBirthd(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
        } else {
            stuMst.setBirthdayString(DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS));
        }

        //更新日時
        String updateStr = DateUtils.format(stuMst.getUpdDatime(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);

        return R.ok().put("stuMst", stuMst)
                .put("gendrList", gendrList)
                .put("schyList", schyList)
//                .put("schList", schList)
                .put("updateStr", updateStr)
                .put("orgId", orgId)
                .put("saf_modify_flg",mstUsrEntity.getSafModifyFlg());

    }

    /**
     * <p>変更</p>
     *
     * @param dto 生徒の情報
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @RequiresPermissions("student")
    public R update(@RequestBody F10001Dto dto) {
        //生年月日
        Date birthd = DateUtils.parse(dto.getBirthdayString(), GakkenConstant.DATE_FORMAT_YYYY_MM_DD_BARS);
        if (birthd == null) {
            return R.error(MessageUtils.getMessage("MSGCOMD0013", "生年月日"));
        }
        dto.setBirthd(birthd);
        return f10001Service.update(dto);
    }

}
