package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.EventScheduleEntity;
import jp.learningpark.modules.common.entity.GuardEventApplyStsEntity;
import jp.learningpark.modules.common.entity.MstAskTalkEventEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstEventDeliverEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.entity.StuEventApplyStsEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.EventScheduleService;
import jp.learningpark.modules.common.service.GuardEventApplyStsService;
import jp.learningpark.modules.common.service.MstAskTalkEventService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventDeliverService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.StuEventApplyStsService;
import jp.learningpark.modules.manager.dto.F08001DspDto;
import jp.learningpark.modules.manager.dto.F08001Dto;
import jp.learningpark.modules.manager.service.F08001Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * <p>F08001_イベント一覧画面 Controller</p >
 *
 * @author NWT : zhao <br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08001")
public class F08001Controller {
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
     * 生徒イベント申込状況　Service
     */
    @Autowired
    private StuEventApplyStsService stuEventApplyStsService;

    /**
     * EventSchePlanDelService
     */
    @Autowired
    private EventSchePlanDelService eventSchePlanDelService;

    /**
     * EventScheduleService
     */
    @Autowired
    private EventScheduleService eventScheduleService;

    /**
     * MstEventDeliverService
     */
    @Autowired
    private MstEventDeliverService mstEventDeliverService;

    /**
     * 質問面談イベント  Service
     */
    @Autowired
    private MstAskTalkEventService mstAskTalkEventService;

    /**
     * F08001Service
     */
    @Autowired
    private F08001Service f08001Service;

    /**
     * MstEventService
     */
    @Autowired
    MstEventService mstEventService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>初期表示</p>
     *
     * @param limit 各ページの最大記録数
     * @param page 現在のページ数
     * @param params 条件を調べる
     * @return R 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String params, HttpServletResponse response) {
        Map<String, String> paramsMap = (Map)JSON.parse(params);
        R info = new R();
        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        paramsMap.put("orgId", orgId);
        // クエリーデータセットを取得する
        List<F08001Dto> F08001DtoList = f08001Service.selectAll(null, null, paramsMap);
        //        List<F08001Dto> count = f08001Service.selectAll(null, null, paramsMap);

        if (F08001DtoList.size() <= 0) {
            // アイテムが取得できない場合はメッセージ(MSGCOMN0017)に戻る。
            info = R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "イベント"));
            return info;
        }
        info = R.ok();
        // プロジェクトを取得するとページに戻る
        info.put("page", new PageUtils(F08001DtoList, F08001DtoList.size(), limit, page));
        // セッションデータ．組織ID戻る
        Cookie cookie = new Cookie("orgId", orgId);
        response.addCookie(cookie);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
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
        String menuId = "F08001";
        // アイテム设定セット取得を表示します
        F08001Dto f08001Dto = f08001Service.selectDspItem(userId, menuId);
        F08001DspDto f08001DspDto = new F08001DspDto();
        f08001DspDto.setDspItemslist(Arrays.asList(f08001Dto.getDspItems().split(",")));
        f08001DspDto.setMustItemslist(Arrays.asList(f08001Dto.getMustItems().split(",")));
        f08001DspDto.setAllItemslist(Arrays.asList(f08001Dto.getAllItems().split(",")));
        R info = new R();
        // 集合データに戻り
        info.put("f08001DspDto", f08001DspDto);
        return info;
    }

    /**
     * <p>表示項目記憶</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/saveDspItems", method = RequestMethod.POST)
    public R saveDspItems(String dspitems) {

        // F08001DspDto作成
        F08001DspDto f08001DspDto = new F08001DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        f08001DspDto.setUserId(userId);
        // 画面ID設定
        f08001DspDto.setMenuId("F08001");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        f08001DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        f08001DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        f08001DspDto.setCretUsrId(userId);
        // 更新日時設定
        f08001DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        f08001DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        f08001DspDto.setDelFlg(0);
        // 表示項目の内容を保存する
        Integer status = f08001Service.insertDspItem(f08001DspDto);

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

        // F08001DspDto作成
        F08001DspDto f08001DspDto = new F08001DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        f08001DspDto.setUserId(userId);
        // 画面ID設定
        f08001DspDto.setMenuId("F08001");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        f08001DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        f08001DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        f08001DspDto.setCretUsrId(userId);
        // 更新日時設定
        f08001DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        f08001DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        f08001DspDto.setDelFlg(0);
        // 表示項目の内容を更新する
        Integer status = f08001Service.updateDspItem(f08001DspDto);

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
        String menuId = "F08001";
        // 項目数の取得を示す
        Integer dspcount = f08001Service.getDspCount(userId, menuId);

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
        List<MstTmplateEntity> mstTmplateEntity = f08001Service.getTmplateAll();
        // EVENT_STS_DIVの全ての情報を取得する
        List<MstCodDEntity> mstCodDEntities = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "EVENT_STS_DIV"));
        // REF_TYPE_DIVの全ての情報を取得する
        List<MstCodDEntity> mstCodDEntities2 = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").eq("cod_key", "REF_TYPE_DIV"));

        return R.ok().put("tmplates", mstTmplateEntity).put("status", mstCodDEntities).put("objects", mstCodDEntities2);
    }

    /**
     * @param id
     * @param updateStrForCheck
     * @return R 画面情報
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public R deleteEvent(Integer id, String updateStrForCheck) {

        //idによってイベントを取得する
        MstEventEntity mstEventEntity = mstEventService.getOne(new QueryWrapper<MstEventEntity>().select("event_sts_div", "id", "upd_datime").eq("id", id));
        if (mstEventEntity == null || StringUtils.equals("0", mstEventEntity.getEventStsDiv())) {
            // EventStsDivフィールドが0に等しい場合は、メッセージMSGCOMN0109へ戻る
            return R.error(MessageUtils.getMessage("MSGCOMN0109", "イベント"));
        }

        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();

        // イベントマスタから該当データを論理削除する
        if (!StringUtils.equals(mstEventEntity.getUpdDatime().toString(), updateStrForCheck)) {
            //排他チェックエラーの場合 MSGCOMN0019に戻る
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0019", "対象データを再取得してから更新処理を行ってください。"));
        }

        // イベント．削除フラグ＝「無効」
        mstEventEntity.setDelFlg(1);
        // イベント．更新日時＝システム日時
        mstEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // イベント．更新ユーザＩＤ＝ログインユーザＩＤ
        mstEventEntity.setUpdUsrId(userId);
        // イベント更新
        mstEventService.update(mstEventEntity, new QueryWrapper<MstEventEntity>().eq("id", id));

        // イベント配送先から論理削除する
        MstEventDeliverEntity mstEventDeliverEntity = new MstEventDeliverEntity();
        //イベント配送先．削除フラグ＝「無効」
        mstEventDeliverEntity.setDelFlg(1);
        // イベント配送先．更新日時 ＝ システム日時
        mstEventDeliverEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // イベント配送先．更新ユーザＩＤ ＝ ログインユーザＩＤ
        mstEventDeliverEntity.setUpdUsrId(userId);
        // イベント配送先更新
        mstEventDeliverService.update(mstEventDeliverEntity, new QueryWrapper<MstEventDeliverEntity>().eq("event_id", id));

        // イベント日程から論理削除する
        EventScheduleEntity eventScheduleEntity = new EventScheduleEntity();
        // イベント日程．削除フラグ＝「無効」
        eventScheduleEntity.setDelFlg(1);
        // イベント日程．更新日時 ＝ システム日時
        eventScheduleEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // イベント日程．更新ユーザＩＤ ＝ ログインユーザＩＤ
        eventScheduleEntity.setUpdUsrId(userId);
        // イベント日程更新
        eventScheduleService.update(eventScheduleEntity, new QueryWrapper<EventScheduleEntity>().eq("event_id", id));

        // イベント日程（詳細）から論理削除する
        EventSchePlanDelEntity eventSchePlanDelEntity = new EventSchePlanDelEntity();
        // イベント日程(詳細)．削除フラグ＝「無効」
        eventSchePlanDelEntity.setDelFlg(1);
        // イベント日程(詳細)．更新日時 ＝ システム日時
        eventSchePlanDelEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // イベント日程(詳細)．更新ユーザＩＤ ＝ ログインユーザＩＤ
        eventSchePlanDelEntity.setUpdUsrId(userId);
        // イベント日程(詳細)更新
        eventSchePlanDelService.update(eventSchePlanDelEntity, new QueryWrapper<EventSchePlanDelEntity>().eq("event_id", id));

        // 保護者イベント申込状況から該当データを論理削除する
        List<GuardEventApplyStsEntity> guardEventApplyStsEntity = guardEventApplyStsService.list(
                new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", id));
        if (guardEventApplyStsEntity.size() == 0) {
            // 保護者イベント申込状況から該当データを論理削除する
            StuEventApplyStsEntity entity = new StuEventApplyStsEntity();
            // 保護者イベント申込状況．削除フラグ＝「無効」
            entity.setDelFlg(1);
            // 保護者イベント申込状況．更新日時＝システム日時
            entity.setUpdDatime(DateUtils.getSysTimestamp());
            // 保護者イベント申込状況．更新ユーザＩＤ ＝ ログインユーザＩＤ
            entity.setUpdUsrId(userId);
            // 保護者イベント申込状況更新
            stuEventApplyStsService.update(entity, new QueryWrapper<StuEventApplyStsEntity>().eq("event_id", id));
        } else {
            GuardEventApplyStsEntity entity = new GuardEventApplyStsEntity();
            // 保護者イベント申込状況．削除フラグ＝「無効」
            entity.setDelFlg(1);
            // 保護者イベント申込状況．更新日時＝システム日時
            entity.setUpdDatime(DateUtils.getSysTimestamp());
            // 保護者イベント申込状況．更新ユーザＩＤ ＝ ログインユーザＩＤ
            entity.setUpdUsrId(userId);
            // 保護者イベント申込状況更新
            guardEventApplyStsService.update(entity, new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", id));
        }

        MstAskTalkEventEntity mstAskTalkEventEntity = new MstAskTalkEventEntity();
        //更新ユーザーID
        mstAskTalkEventEntity.setUpdUsrId(userId);
        //更新日時
        mstAskTalkEventEntity.setUpdDatime(DateUtils.getSysTimestamp());
        //削除フラグ
        mstAskTalkEventEntity.setDelFlg(1);
        mstAskTalkEventService.update(mstAskTalkEventEntity, new QueryWrapper<MstAskTalkEventEntity>().eq("event_id", id));
        return R.ok();
    }

    @RequestMapping(value = "/getEventCont", method = RequestMethod.GET)
    public R getEventCont(Integer eventId) {
        R r = R.ok();
        MstEventEntity mstEventEntity = mstEventService.getOne(
                new QueryWrapper<MstEventEntity>().select("event_cont", "attach_file_path").eq("id", eventId).eq("del_flg", 0));
        r.put("event", mstEventEntity);
        if (!StringUtils.isEmpty(mstEventEntity.getAttachFilePath())) {
            try {
                r.put("attachFilePath", URLEncoder.encode(mstEventEntity.getAttachFilePath(), "UTF-8"));
            } catch (UnsupportedEncodingException e) {
                logger.error(e.getMessage());
                return R.error(e.getMessage());
            }
        }
        return r;
    }
}
