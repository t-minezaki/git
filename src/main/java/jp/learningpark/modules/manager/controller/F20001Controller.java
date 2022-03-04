/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.MstStuEntity;
import jp.learningpark.modules.common.entity.MstUsrEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstStuService;
import jp.learningpark.modules.common.service.MstUsrService;
import jp.learningpark.modules.manager.dto.F20001Dto;
import jp.learningpark.modules.manager.service.F20001Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import jp.learningpark.modules.sys.service.ShiroService;
import jp.learningpark.modules.xapi.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * <p>F20001_生徒一覧画面 コントローラ</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/10/08 : gong: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F20001")
public class F20001Controller extends AbstractController {

    /**
     * F20001_生徒一覧画面  Service
     */
    @Autowired
    F20001Service f20001Service;

    /**
     * コードマスタ Service
     */
    @Autowired
    private MstCodDService codMstDService;

    /**
     * グループマスタ Service
     */
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * 生徒基本マスタ
     */
    @Autowired
    private MstStuService mstStuService;
    /**
     * shiroService
     */
    @Autowired
    ShiroService shiroService;
    /**
     * ユーザ基本マスタService
     */
    @Autowired
    MstUsrService mstUsrService;

    /**
     * <p>初期表示</p>
     *
     * @return 画面パス
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @RequiresPermissions("manager")
    public R getStuList(String orgId) throws Exception {
        String mentorId = getUserCd();
        DateUtils.addDays(DateUtils.getSysDate(), -7);
        //前週の月
        Date startDate = DateUtils.getMonday(DateUtils.addDays(DateUtils.getSysDate(), -7));
        //前週の日
        Date endDate = DateUtils.getSunday(DateUtils.addDays(DateUtils.getSysDate(), -7));
        //メンター名
        String mentorNm = (String) ShiroUtils.getSessionAttribute(GakkenConstant.MENTOR_NM);
        //ユーザーロール
        String userRole = ShiroUtils.getUserEntity().getRoleDiv();
        List<F20001Dto> list = new ArrayList<>();

        Map<String, Object> map = new HashMap<>();
        map.put("mentorId", mentorId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("orgId", !StringUtils.isEmpty(orgId)?orgId:ShiroUtils.getUserEntity().getOrgId());
        if (StringUtils.equals("1", StringUtils.trim(userRole))) {
            list = f20001Service.getManagerStu(map);
        } else {
            list = f20001Service.getInfo(map);
        }
        //学年リスト
        List<MstCodDEntity> schyList = codMstDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc(
                        "sort"));

        //グループリスト
        List<MstGrpEntity> groupsList = mstGrpService.list(new QueryWrapper<MstGrpEntity>().and(wrapper -> wrapper.eq("org_id", orgId).eq("del_flg", 0)));

        Extensions exts = new Extensions();
        JsonArray array = new JsonArray();
        JsonObject jsonObject = new JsonObject();
        for (int i = 0; i < list.size(); i++) {
            jsonObject = new JsonObject();
            jsonObject.addProperty(XApiConstant.EXT_KEY_STU_ID, list.get(i).getStuId());
            array.add(jsonObject);
        }
        //生徒ID
        exts.put(XApiConstant.EXT_KEY_STU_ID_LIST, array);
        //メンターID
        exts.put(XApiConstant.EXT_KEY_MENTOR_ID, mentorId);
        XApiUtils.saveStatement(Verbs.launched(), Activitys.academy(), exts);
        return R.ok().put("list", list).put("schyList", schyList).put("groupsList", groupsList).put("mentorNm", mentorNm);
    }

    /**
     * <p>stuIdの保存する</p>
     *
     * @return
     */
    @RequestMapping(value = "/saveStuId", method = RequestMethod.GET)
    public R saveStuId(String stuId, Integer id, String mentorNm) {
        String orgId = mstUsrService.getOne(new QueryWrapper<MstUsrEntity>().eq("usr_id",stuId).eq("del_flg",0)).getOrgId();
        Integer prdId = shiroService.getLearnPrdIdByUserId(stuId,orgId);
        if (prdId == null){
            return R.error(MessageUtils.getMessage("MSGCOMN0017","塾学習期間"));
        }
        ShiroUtils.setSessionAttribute("stuId", stuId);
        ShiroUtils.setSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID, id);
        ShiroUtils.setSessionAttribute(GakkenConstant.MENTOR_NM, mentorNm);
        MstStuEntity mstStuEntity = mstStuService.getOne(new QueryWrapper<MstStuEntity>().eq("stu_id", stuId).eq("del_flg", 0));
        String stuNm = mstStuEntity.getFlnmNm() + " " + mstStuEntity.getFlnmLnm();
        ShiroUtils.setSessionAttribute(GakkenConstant.STU_NM, stuNm);
        return R.ok();
    }

    /**
     * <p>7.1 絞り込み条件画面の初期表示</p>
     *
     * @return
     */
    @RequestMapping(value = "/pop", method = RequestMethod.GET)
    public R f20001PopInit() {
        //学年リスト
        List<MstCodDEntity> schyList = codMstDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc(
                        "sort"));

        //グループリスト
        List<MstCodDEntity> groupsList = codMstDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(wrapper -> wrapper.eq("cod_key", "GRP_DIV_1").eq("del_flg", 0)));
        return R.ok().put("schyList", schyList).put("groupsList", groupsList);
    }

    /**
     * <p>7.2  「絞り込み」を押すと、条件により、生徒一覧を再表示する。</p>
     *
     * @param dto
     * @return
     */
    @RequestMapping(value = "/likeSearch", method = RequestMethod.POST)
    public R likeSearch(F20001Dto dto) {
        //ユーザーロール
        String userRole = ShiroUtils.getUserEntity().getRoleDiv().trim();
        String mentorId = getUserCd();
        //塾Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();

        DateUtils.addDays(DateUtils.getSysDate(), -7);
        //前週の月
        Date startDate = DateUtils.getMonday(DateUtils.addDays(DateUtils.getSysDate(), -7));
        //前週の日
        Date endDate = DateUtils.getSunday(DateUtils.addDays(DateUtils.getSysDate(), -7));
        List<F20001Dto> list = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("mentorId", mentorId);
        map.put("startDate", startDate);
        map.put("endDate", endDate);
        map.put("orgId", orgId);
        map.put("userId", dto.getUserId());
        map.put("userName", dto.getUserName());
        map.put("schyDiv", dto.getSchyDiv());
        map.put("grpDiv1", dto.getGrpDiv1());
        if (StringUtils.equals("0", dto.getTermPlanCheck())) {
            map.put("termPlanCheck", null);
        } else {
            map.put("termPlanCheck", "termPlanCheck");
        }
        if (StringUtils.equals("1",userRole)){
            list = f20001Service.getManagerStu(map);
        }else {
            list = f20001Service.getInfo(map);
        }
        if (list.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "生徒"));
        }
        return R.ok().put("list", list);
    }

    /**
     * <p>ページを表示する前に許可リストを取得する</p>
     */
//    @Controller
//    public class F20001InitController {

        /**
         * <p>インターセプトページリクエスト</p>
         *
         * @return
         */
//        @RequestMapping(value = "/manager/F20001.html")
//        public ModelAndView initPermission() {
//            String userId = ShiroUtils.getUserId();
//            ShiroUtils.setSessionAttribute("f20001permission", f20001Service.getPermission(userId));
//            Session session = ShiroUtils.getSession();
//            return new ModelAndView("/manager/F20001");
//        }
//    }
}
