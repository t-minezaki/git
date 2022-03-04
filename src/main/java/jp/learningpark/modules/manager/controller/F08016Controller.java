package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.common.utils.dao.CommonDao;
import jp.learningpark.modules.manager.dto.F08016DspDto;
import jp.learningpark.modules.manager.dto.F08016Dto;
import jp.learningpark.modules.manager.service.F08016Service;
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
 * <p>F08016_テンプレート一覧画面 Controller</p >
 *
 * @author NWT : zhao <br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08016")
public class F08016Controller {

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
     * F08016Service
     */
    @Autowired
    private F08016Service f08016Service;

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
        List<F08016Dto> F08016DtoList = f08016Service.selectAll(null, null, paramsMap);

        if (F08016DtoList.size() <= 0) {
            // アイテムが取得できない場合はメッセージ(MSGCOMN0017)に戻る。
            info = R.error().put("msg", MessageUtils.getMessage("MSGCOMN0017", "テンプレート"));
            return info;
        }
        info = R.ok();
        // プロジェクトを取得するとページに戻る
        info.put("page", new PageUtils(F08016DtoList, F08016DtoList.size(), limit, page));
        // 2020/11/24 huangxinliang modify start
        info.put("orgId", orgId);
        Cookie cookie = new Cookie("orgId",orgId);
        response.addCookie(cookie);
        cookie.setPath("/");
        cookie.setMaxAge(-1);
        response.addCookie(cookie);
        // 2020/11/24 huangxinliang modify end
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
        String menuId = "F08016";
        // アイテム设定セット取得を表示します
        F08016Dto f08016Dto = f08016Service.selectDspItem(userId, menuId);
        F08016DspDto F08016DspDto = new F08016DspDto();
        // 集合転換
        F08016DspDto.setDspItemslist(Arrays.asList(f08016Dto.getDspItems().split(",")));
        F08016DspDto.setMustItemslist(Arrays.asList(f08016Dto.getMustItems().split(",")));
        F08016DspDto.setAllItemslist(Arrays.asList(f08016Dto.getAllItems().split(",")));
        R info = new R();
        // 集合データに戻り
        info.put("F08016DspDto", F08016DspDto);
        return info;
    }

    /**
     * <p>表示項目記憶</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/saveDspItems", method = RequestMethod.POST)
    public R saveDspItems(String dspitems) {

        // F08016DspDto作成
        F08016DspDto F08016DspDto = new F08016DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        F08016DspDto.setUserId(userId);
        // 画面ID設定
        F08016DspDto.setMenuId("F08016");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        F08016DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        F08016DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        F08016DspDto.setCretUsrId(userId);
        // 更新日時設定
        F08016DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        F08016DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        F08016DspDto.setDelFlg(0);
        // 表示項目の内容を保存する
        Integer status = f08016Service.insertDspItem(F08016DspDto);

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

        // F08016DspDto作成
        F08016DspDto F08016DspDto = new F08016DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        F08016DspDto.setUserId(userId);
        // 画面ID設定
        F08016DspDto.setMenuId("F08016");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        F08016DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        F08016DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        F08016DspDto.setCretUsrId(userId);
        // 更新日時設定
        F08016DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        F08016DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        F08016DspDto.setDelFlg(0);
        // 表示項目の内容を更新する
        Integer status = f08016Service.updateDspItem(F08016DspDto);

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
        String menuId = "F08016";
        // 項目数の取得を示す
        Integer dspcount = f08016Service.getDspCount(userId, menuId);

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
    public R getTmplate() {

        // テンプレート情報を全て取得する
        List<MstTmplateEntity> mstTmplateEntity = f08016Service.getTmplateAll();

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
