package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.entity.UserDisplayItemSetEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.EventScheduleService;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventDeliverService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.common.service.UserDisplayItemSetService;
import jp.learningpark.modules.manager.dto.F08009DspDto;
import jp.learningpark.modules.manager.dto.F08009Dto;
import jp.learningpark.modules.manager.service.F08009Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * <p>F08009_イベント一覧画面 Controller</p >
 *
 * @author NWT : zhao <br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08009")
public class F08009Controller {

    /**
     *
     */
    @Autowired
    private UserDisplayItemSetService userDisplayItemSetService;

    /**
     * eventScheduleService
     */
    @Autowired
    private EventScheduleService eventScheduleService;

    /**
     * mstCodDService
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * GuardEventApplyStsService
     */
    @Autowired
    private GuardEventApplyStsService guardEventApplyStsService;

    /**
     * EventSchePlanDelService
     */
    @Autowired
    private EventSchePlanDelService eventSchePlanDelService;

    /**
     * MstEventDeliverService
     */
    @Autowired
    private MstEventDeliverService mstEventDeliverService;

    /**
     * MstTmplateService
     */
    @Autowired
    private MstTmplateService mstTmplateService;

    /**
     * MstOrgService
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F08009Service
     */
    @Autowired
    private F08009Service f08009Service;

    /**
     * MstEventService
     */
    @Autowired
    MstEventService mstEventService;

    /**
     * <p>初期表示</p>
     *
     * @param limit 当ページ数
     * @param page  各ページの最大記録数
     * @param params
     * @return R 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String params) {

        Map<String,String>paramsMap = (Map) JSON.parse(params);
        R info = new R();
        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        paramsMap.put("orgId", orgId);
        // クエリーデータセットを取得する
        List<F08009Dto> F08009DtoList = f08009Service.selectAll(limit, page, paramsMap);
        Integer countAll = f08009Service.countAll(paramsMap);

        if (F08009DtoList.size() <= 0) {
            // アイテムが取得できない場合はメッセージ(MSGCOMN0017)に戻る。
            info = R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "イベント"));
            return info;
        }

//        for (F08009Dto f08009Dto : F08009DtoList) {
//            Integer count = eventScheduleService.count(new QueryWrapper<EventScheduleEntity>().eq("event_id", f08009Dto.getId()).eq("del_flg", 0));
//            f08009Dto.setScheduleCount(count);
//        }
        info = R.ok();
        // プロジェクトを取得するとページに戻る
        info.put("page", new PageUtils(F08009DtoList, countAll, limit, page));
        return info;
    }

    /**
     * <p>項目取得を表示する</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getDspItems", method = RequestMethod.GET)
    public R getDspItems() {
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // 画面ID
        String menuId = "F08009";
        // アイテム设定セット取得を表示します
        F08009Dto f08009Dto = f08009Service.selectDspItem(userId, menuId);
        F08009DspDto F08009DspDto = new F08009DspDto();
        // 集合転換
        F08009DspDto.setDspItemslist(Arrays.asList(f08009Dto.getDspItems().split(",")));
        F08009DspDto.setMustItemslist(Arrays.asList(f08009Dto.getMustItems().split(",")));
        F08009DspDto.setAllItemslist(Arrays.asList(f08009Dto.getAllItems().split(",")));
        R info = new R();
        // 集合データに戻り
        info.put("F08009DspDto", F08009DspDto);
        return info;
    }

    /**
     * <p>表示項目記憶</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/saveDspItems", method = RequestMethod.POST)
    public R saveDspItems(String dspitems) {

        // F08009DspDto作成
        F08009DspDto F08009DspDto = new F08009DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        F08009DspDto.setUserId(userId);
        // 画面ID設定
        F08009DspDto.setMenuId("F08009");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        F08009DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        F08009DspDto.setCretDatime(new Date());
        // 作成ユーザＩＤ設定
        F08009DspDto.setCretUsrId(userId);
        // 更新日時設定
        F08009DspDto.setUpdDatime(new Date());
        // 更新ユーザＩＤ設定
        F08009DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        F08009DspDto.setDelFlg(0);
        // 表示項目の内容を保存する
        Integer status = f08009Service.insertDspItem(F08009DspDto);

        R info = new R();
        info.put("status", status);
        return info;
    }

    /**
     * <p>更新项目</p>
     *
     * @param dspitems 項目文字列を表示する
     * @return R 画面情報
     */
    @RequestMapping(value = "/updDspItems", method = RequestMethod.POST)
    public R updDspItems(String dspitems) {

        // F08009DspDto作成
        F08009DspDto F08009DspDto = new F08009DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        F08009DspDto.setUserId(userId);
        // 画面ID設定
        F08009DspDto.setMenuId("F08009");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        F08009DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 更新日時設定
        F08009DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        F08009DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        F08009DspDto.setDelFlg(0);
        // 表示項目の内容を更新する
        Integer status = f08009Service.updateDspItem(F08009DspDto);

        R info = new R();
        info.put("status", status);
        return info;
    }

    /**
     * <p>项目个数取得</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getDspCount", method = RequestMethod.GET)
    public R getDspCount() {

        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // 画面ID
        String menuId = "F08009";
        // 項目数の取得を示す
        Integer dspcount = userDisplayItemSetService.count(new QueryWrapper<UserDisplayItemSetEntity>().eq("user_id", userId).eq("menu_id", menuId).eq("del_flg", 0));

        R info = new R();
        info.put("dspcount", dspcount);
        return info;
    }

    /**
     * <p>テンプレート名id取得</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getTmplateAndStatusAndObject", method = RequestMethod.GET)
    public R getTmplateAndStatusAndObject() {

        // テンプレート情報を全て取得する
        List<MstTmplateEntity> mstTmplateEntity = f08009Service.getTmplateAll();
        // EVENT_STS_DIVの全ての情報を取得する
        List<MstCodDEntity> mstCodDEntities = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "EVENT_STS_DIV"));
        // REF_TYPE_DIVの全ての情報を取得する
        List<MstCodDEntity> mstCodDEntities2 = mstCodDService.list(new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "REF_TYPE_DIV"));

        return R.ok().put("tmplates", mstTmplateEntity).put("status", mstCodDEntities).put("objects", mstCodDEntities2);
    }

}
