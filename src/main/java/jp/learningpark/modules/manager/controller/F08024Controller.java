package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.manager.dto.F08024DspDto;
import jp.learningpark.modules.manager.dto.F08024Dto;
import jp.learningpark.modules.manager.service.F08024Service;
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
 * <p>F08024_テンプレート一覧画面 Controller</p >
 *
 * @author NWT : li <br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08024")
public class F08024Controller {

    /**
     * mstEventService
     */
    @Autowired
    private MstEventService mstEventService;

    /**
     * MstTmplateService
     */
    @Autowired
    private MstTmplateService mstTmplateService;

    /**
     * F08024Service
     */
    @Autowired
    private F08024Service f08024Service;

    /**
     * commonDao
     */
    @Autowired
    private CommonDao commonDao;

    /**
     * <p>初期表示</p>
     *
     * @param limit 当ページ数
     * @param page 各ページの最大記録数
     * @param params
     * @return R 画面情報
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer limit, Integer page, String params, HttpServletResponse response) {

        Map<String, String> paramsMap = (Map)JSON.parse(params);
        R info = new R();
        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        paramsMap.put("orgId", orgId);
        paramsMap.put("brandCd", ShiroUtils.getBrandcd());
        // クエリーデータセットを取得する
        List<F08024Dto> F08024DtoList = f08024Service.selectAll(null, null, paramsMap);

        if (F08024DtoList.size() <= 0) {
            // アイテムが取得できない場合はメッセージ(MSGCOMN0017)に戻る。
            info = R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "テンプレート"));
            return info;
        }
        info = R.ok();
        // プロジェクトを取得するとページに戻る
        info.put("page", new PageUtils(F08024DtoList, F08024DtoList.size(), limit, page));
        info.put("orgId", orgId);
        Cookie cookie = new Cookie("orgId",orgId);
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
        String menuId = "F08024";
        // アイテム设定セット取得を表示します
        F08024Dto F08024Dto = f08024Service.selectDspItem(userId, menuId);
        F08024DspDto F08024DspDto = new F08024DspDto();
        // 集合転換
        F08024DspDto.setDspItemslist(Arrays.asList(F08024Dto.getDspItems().split(",")));
        F08024DspDto.setMustItemslist(Arrays.asList(F08024Dto.getMustItems().split(",")));
        F08024DspDto.setAllItemslist(Arrays.asList(F08024Dto.getAllItems().split(",")));
        R info = new R();
        // 集合データに戻り
        info.put("F08024DspDto", F08024DspDto);
        return info;
    }

    /**
     * <p>表示項目記憶</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/saveDspItems", method = RequestMethod.POST)
    public R saveDspItems(String dspitems) {

        // F08024DspDto作成
        F08024DspDto F08024DspDto = new F08024DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        F08024DspDto.setUserId(userId);
        // 画面ID設定
        F08024DspDto.setMenuId("F08024");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        F08024DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        F08024DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        F08024DspDto.setCretUsrId(userId);
        // 更新日時設定
        F08024DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        F08024DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        F08024DspDto.setDelFlg(0);
        // 表示項目の内容を保存する
        Integer status = f08024Service.insertDspItem(F08024DspDto);

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

        // F08024DspDto作成
        F08024DspDto F08024DspDto = new F08024DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        F08024DspDto.setUserId(userId);
        // 画面ID設定
        F08024DspDto.setMenuId("F08024");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        F08024DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        F08024DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        F08024DspDto.setCretUsrId(userId);
        // 更新日時設定
        F08024DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        F08024DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        F08024DspDto.setDelFlg(0);
        // 表示項目の内容を更新する
        Integer status = f08024Service.updateDspItem(F08024DspDto);

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
        String menuId = "F08024";
        // 項目数の取得を示す
        Integer dspcount = f08024Service.getDspCount(userId, menuId);

        R info = new R();
        info.put("dspcount", dspcount);
        return info;
    }

    /**
     * <p>テンプレート名id取得</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getTmplate", method = RequestMethod.GET)
    public R getTmplate(String tmplateTypeDiv) {
        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // テンプレート情報を全て取得する
        List<MstTmplateEntity> mstTmplateEntity = f08024Service.getTmplateAll(orgId,tmplateTypeDiv);

        return R.ok().put("tmplates", mstTmplateEntity);
    }
    /**
     * <p>テンプレート名取得</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getOrderTmplate", method = RequestMethod.GET)
    public R getOrderTmplate(String tmplateDiv) {
        // 組織IDを取得する
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // テンプレート情報を全て取得する
        List<MstTmplateEntity> mstTmplateEntity = f08024Service.getOrderTmplateAll(tmplateDiv,orgId);

        return R.ok().put("tmplates", mstTmplateEntity);
    }



    /**
     * @param id
     * @param updateStrForCheck
     * @return R 画面情報
     */
    @RequestMapping(value = "/del", method = RequestMethod.POST)
    public R deleteEvent(Integer id, String updateStrForCheck) {

        //idによってイベントを取得する
        MstTmplateEntity mstTmplateEntity = mstTmplateService.getOne(new QueryWrapper<MstTmplateEntity>().select("id", "upd_datime").eq("id", id));
        // 使用中かどうか
        int event_count = mstEventService.count(new QueryWrapper<MstEventEntity>().eq("temp_id", mstTmplateEntity.getId()));
        if (event_count > 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0092", "テンプレート"));
        }
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // テンプレートマスタから該当データを論理削除する
        if (mstTmplateEntity == null || !StringUtils.equals(mstTmplateEntity.getUpdDatime().toString(), updateStrForCheck)) {
            //排他チェックエラーの場合 MSGCOMN0019に戻る
            return R.error().put("msg", MessageUtils.getMessage("MSGCOMN0019"));
        }
        // テンプレート．削除フラグ＝「無効」
        mstTmplateEntity.setDelFlg(1);
        // テンプレート．更新日時＝システム日時
        mstTmplateEntity.setUpdDatime(DateUtils.getSysTimestamp());
        // テンプレート．更新ユーザＩＤ＝ログインユーザＩＤ
        mstTmplateEntity.setUpdUsrId(userId);
        // テンプレート更新
        mstTmplateService.update(mstTmplateEntity, new QueryWrapper<MstTmplateEntity>().eq("id", id));

        return R.ok();
    }

    @RequestMapping(value = "/getTmplateCont", method = RequestMethod.GET)
    public R getTmplateCont(Integer tmplateId) throws UnsupportedEncodingException {
        R r = R.ok();
        MstTmplateEntity mstTmplateEntity = mstTmplateService.getOne(
                new QueryWrapper<MstTmplateEntity>().select("tmplate_cnt", "attach_file_path").eq("id", tmplateId).eq("del_flg", 0));
        String pathStr = "";
        String cont = "";
        if (!StringUtils.isEmpty(mstTmplateEntity.getTmplateCnt())) {
            cont = mstTmplateEntity.getTmplateCnt();
            r.put("tmplateCnt", cont);
        }
        if (!StringUtils.isEmpty(mstTmplateEntity.getAttachFilePath())) {
            pathStr = URLEncoder.encode(mstTmplateEntity.getAttachFilePath(), "UTF-8");
            r.put("attachFilePath", pathStr);
        }
        return r;
    }
}
