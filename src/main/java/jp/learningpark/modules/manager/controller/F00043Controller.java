/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.guard.dao.F30001Dao;
import jp.learningpark.modules.manager.dto.F00042Dto;
import jp.learningpark.modules.manager.dto.F00043Dto;
import jp.learningpark.modules.manager.service.F00042Service;
import jp.learningpark.modules.manager.service.F00043Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedList;
import java.util.List;

/**
 * <p>ユーザー基本情報修正画面</p >
 *
 * @author NWT : xiong <br />
 * 変更履歴 <br />
 * 2019/03/15 : xiong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/f00043")
@RestController
public class F00043Controller extends AbstractController {

    /**
     * F30001保護者基本情報登録画面
     */
    @Autowired
    private F30001Dao f30001Dao;

    /**
     * ユーザー基本情報修正画面
     */
    @Autowired
    private F00043Service f00043Service;

    /**
     * ユーザー初期基本情報＆新規発番画面
     */
    @Autowired
    private F00042Service f00042Service;

    /**
     * 組織マスタ
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * ユーザ基本マスタ
     */
    @Autowired
    private MstUsrService mstUsrService;

    /**
     * コードマスタ_明細
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * 管理者基本マスタ
     */
    @Autowired
    private MstManagerService mstManagerService;

    /**
     * メンター基本マスタ
     */
    @Autowired
    private MstMentorService mstMentorService;

    /**
     * 保護者基本マスタ
     */
    @Autowired
    private MstGuardService mstGuardService;

    /**
     * 生徒基本マスタ
     */
    @Autowired
    private MstStuService mstStuService;

    /**
     * 生徒教科書選択管理
     */
    @Autowired
    private StuTextbChocService stuTextbChocService;

    @Autowired
    private CommonService commonService;

    /**
     * 初期化
     *
     * @param usrId 引渡データ．ユーザＩＤ
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R initial(String usrId) {
        R info = new R();
        // 組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // 組織
        MstOrgEntity mstOrgEntity = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>()
                .eq("org_id", orgId)
                .eq("del_flg", 0));
        info.put("mstOrgEntity", mstOrgEntity);
        // ユーザー基本情報
        F00043Dto f00043Dto = f00043Service.getAfterUserId(usrId);
        info.put("f00043Dto", f00043Dto);
        //ユーザ基本マスタ 管理者修正可否
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().select("mgr_modify_flg").eq("usr_id", usrId).eq("del_flg", 0));
        info.put("mgrModifyFlg", mstUsrEntity.getMgrModifyFlg());
        // 更新日時
        String updDatime = DateUtils.format(f00043Dto.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS);
        info.put("updDatime", updDatime);
        switch (f00043Dto.getCodCd()) {
            case "1":
                MstManagerEntity mstManagerEntity = mstManagerService.getOne(new QueryWrapper<MstManagerEntity>().eq("del_flg", 0).eq("mgr_id", usrId));
                info.put("userInformation", mstManagerEntity);
                // ユーザー更新日時
                info.put("usrUpdDatime", DateUtils.format(mstManagerEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                break;
            case "2":
                MstMentorEntity mstMentorEntity = mstMentorService.getOne(new QueryWrapper<MstMentorEntity>().eq("del_flg", 0).eq("mentor_id", usrId));
                info.put("userInformation", mstMentorEntity);
                // ユーザー更新日時
                info.put("usrUpdDatime", DateUtils.format(mstMentorEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                break;
            case "3":
                MstGuardEntity mstGuardEntity = mstGuardService.getOne(new QueryWrapper<MstGuardEntity>().eq("del_flg", 0).eq("guard_id", usrId));
                info.put("userInformation", mstGuardEntity);
                // ユーザー更新日時
                info.put("usrUpdDatime", DateUtils.format(mstGuardEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                // 続柄を取得
                List<MstCodDEntity> reltnsp_div = mstCodDService.list(
                        new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("del_flg", 0).eq("cod_key", "RELTNSP_DIV").orderBy(true, true,
                                "sort"));
                info.put("reltnsp_div", reltnsp_div);
                break;
            case "4":
                MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("del_flg", 0).eq("stu_id", usrId));
                info.put("userInformation", mstStuEntity);
                if (mstStuEntity.getBirthd() != null) {
                    String birth = (DateUtils.format(mstStuEntity.getBirthd(), Constant.DATE_FORMAT_YYYY_MM_DD_SLASH));
                    info.put("birth", birth);
                }
                // ユーザー更新日時
                info.put("usrUpdDatime", DateUtils.format(mstStuEntity.getUpdDatime(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
                // 学年区分
                List<MstCodDEntity> schy_div = mstCodDService.list(
                        new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("del_flg", 0).eq("cod_key", "SCHY_DIV").orderBy(true, true, "sort"));
                // 性別を取得
                List<MstCodDEntity> gender_div = mstCodDService.list(
                        new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("del_flg", 0).eq("cod_key", "GENDR_DIV").orderBy(true, true,
                                "sort"));
                info.put("schy_div", schy_div);
                info.put("gender_div", gender_div);
                break;
            default:
                break;
        }


        // ユーザー組織レベルでログインして、同等または下位の組織のリストを取得します
        List<MstOrgEntity> orgList = f00043Service.getUserOrgListByLoginUserOrgLevel(f00043Dto.getAfterUsrId());
        info.put("orgList", orgList);
        return info;
    }

    /**
     * @param newUserId 新変更後ユーザーID
     * @param userId ユーザーID
     * @return
     */
    @RequestMapping(value = "/checkAfterUserId", method = RequestMethod.POST)
    public R checkAfterUserId(String userId, String newUserId, Integer flg) {
        // 変更後ユーザーID
        MstUsrEntity mstUsrEntity = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().select("after_usr_id").eq("del_flg", 0).eq("usr_id", userId));
        String oldUserId = mstUsrEntity.getAfterUsrId();
        // 利用者ID重複チェック
        if (!newUserId.equals(oldUserId)) {
            if (f00042Service.getAfterUserId(newUserId) != 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0090", "利用者ＩＤ"));
            }
        }
        // 5.3.1 画面．ロールが生徒、且つ、画面．学年≠「1.2」で初期取得した学年区分
        if (flg == 1) {
            List<StuTextbChocEntity> stuTextbChocEntity = stuTextbChocService.list(
                    new QueryWrapper<StuTextbChocEntity>().eq("del_flg", 0).eq("stu_id", userId));
            if (stuTextbChocEntity.size() != 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0101", "学年"));
            }
        }
        return R.ok();
    }

    /**
     * ユーザー画面基本情報
     *
     * @param f00042Dto ユーザー画面基本情報
     * @return
     */
    @PostMapping("/setInformation")
    public R updateManager(@RequestBody F00042Dto f00042Dto) {
        if (f00042Dto.getPostCd() != null) {
            // 住所マスタより、郵便番号を元に、住所情報を取得し
            List adr1 = f30001Dao.searchAddr(f00042Dto.getPostCd());
            if (adr1.isEmpty()) {
                return R.error(MessageUtils.getMessage("MSGCOMN0070", "入力した郵便番号"));
            }
        }
        return f00043Service.setInformation(f00042Dto);
    }

    /**
     * <p>取得したの最上位組織保留する</p>
     *
     * @param selectedOrg ページで選択した組織
     * @return 選ばれたトップ組織
     */
    @PostMapping("/retainTopOrg")
    public R retainTopOrg(@RequestBody(required = false) LinkedList<MstOrgEntity> selectedOrg) {
        List<MstOrgEntity> topOrg = commonService.retainTopOrgList(selectedOrg);
        return R.ok().put("orgList", topOrg);
    }
}
