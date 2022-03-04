package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.exception.RRException;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.PageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.entity.EventSchePlanDelEntity;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstEventEntity;
import jp.learningpark.modules.common.entity.TalkRecordDEntity;
import jp.learningpark.modules.common.service.EventSchePlanDelService;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstEventService;
import jp.learningpark.modules.manager.dao.F08011Dao;
import jp.learningpark.modules.manager.dto.F08011DspDto;
import jp.learningpark.modules.manager.dto.F08011Dto;
import jp.learningpark.modules.manager.service.F08011Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * <p>F08011_配信設定詳細一覧画面 Controller</p >
 *
 * @author NWT : yang <br />
 * 変更履歴 <br />
 * 2019/08/08: yang: 新規<br />
 * @version 1.0
 */
@RestController
@RequestMapping("/manager/F08011")
public class F08011Controller {
    /**
     * f08011Service
     */
    @Autowired
    F08011Service f08011Service;
    /**
     *
     */
    @Autowired
    EventSchePlanDelService eventSchePlanDelService;
    /**
     *
     */
    @Autowired
    MstEventService mstEventService;
    /**
     *
     */
    @Autowired
    MstCodDService mstCodDService;
    @Autowired
    F08011Dao f08011Dao;


    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * <p>初期表示<p/>
     *
     * @param eventId セッションデータ．イベントID
     * @param page
     * @param limit
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer eventId, Integer page, Integer limit) {
        //データタイプ変換
        Map<String, String> paramsMap = new HashMap<>();
        MstEventEntity mstEventEntity = mstEventService.getById(eventId);
        paramsMap.put("refType",mstEventEntity.getRefType());
        //イベント配信者一覧情報を表示するため
        List<F08011Dto> receiverList = f08011Service.getDeliverList(eventId, ShiroUtils.getUserEntity().getOrgId(), null,null,null);
        for (F08011Dto f08011Dto:receiverList){
            if (!StringUtils.equals("1",f08011Dto.getReplyStatusDiv()) && !StringUtils.equals("2",f08011Dto.getReplyStatusDiv())){
                    f08011Dto.setStatus(f08011Dto.getReadingStatus());
                    f08011Dto.setOrgNm(null);
                    f08011Dto.setReply1(null);
            }else {
                F08011Dto applyDel = f08011Dao.getApplyDel(f08011Dto.getEventScheDelId(),paramsMap);
                f08011Dto.setStatus(f08011Dto.getReplyStatus());
                if (StringUtils.equals(f08011Dto.getRefType(),"1")){
                    f08011Dto.setTeacherName(applyDel.getTeacherName());
                    f08011Dto.setStartTime(applyDel.getStartTime());
                }else if (StringUtils.equals(f08011Dto.getRefType(),"0")){
                    EventSchePlanDelEntity eventSchePlanDelEntity = eventSchePlanDelService.getById(f08011Dto.getEventScheDelId());
                    f08011Dto.setStartTime(eventSchePlanDelEntity.getSgdStartDatime());
                    f08011Dto.setTeacherName(f08011Dto.getOrgNm());
                }
            }
            for (F08011Dto dto:receiverList){
                setUpQuestions(dto);
            }
        }
        //イベント日程(明細)．関連タイプの取得
        String refType = mstEventService.getById(eventId).getRefType();
        //イベント配信者一覧情報取得できない場合
        if (receiverList.size() == 0) {
            //画面上部のエラーメッセージ領域でワーニングメッセージを表示する
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "配信者"));
        }
        return R.ok().put("page", new PageUtils(receiverList, receiverList.size(), limit, page)).put("refType", refType);
    }


    /**
     * 状態集計一覧部情報を表示するため
     *
     * @return
     */
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R info(Integer eventId) {
        //セッションデータ．組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        Integer getInfoAll = f08011Dao.getInfoAll(orgId, eventId);
        Integer getInfoUnread = f08011Dao.getInfoUnread(orgId, eventId);
        Integer getInfoRead = f08011Dao.getInfoRead(orgId, eventId);
        Integer getInfoDate = f08011Dao.getInfoDate(orgId, eventId);
        HashMap<String,Integer> getInfo = new HashMap<>();
        getInfo.put("deleverSum",getInfoAll);
        getInfo.put("notReadingSum",getInfoUnread);
        getInfo.put("readingSum",getInfoRead);
        getInfo.put("replySum",getInfoDate);
        return R.ok().put("getInfo", getInfo);
    }

    /**
     * 検索画面「検索」ボタン押下
     *
     * @param eventId     セッションデータ．イベントID
     * @param data        POP検索画面．検索条件
     * @param searchlimit
     * @param searchpage
     * @return
     */
    @RequestMapping(value = "/search", method = RequestMethod.GET)
    public R search(Integer eventId, String data, Integer searchlimit, Integer searchpage) {
        //データタイプ変換
        Map<String, String> paramsMap = (Map) JSON.parse(data);
        MstEventEntity mstEventEntity = mstEventService.getById(eventId);
        paramsMap.put("refType",mstEventEntity.getRefType());
        //イベント配信者一覧情報を表示するため
        List<F08011Dto> receiverList = f08011Service.getDeliverList(eventId, ShiroUtils.getUserEntity().getOrgId(), paramsMap,null,null);
        List<F08011Dto> copy = new ArrayList<>();
        for (F08011Dto f08011Dto:receiverList){
            if (!StringUtils.equals("1",f08011Dto.getReplyStatusDiv()) && !StringUtils.equals("2",f08011Dto.getReplyStatusDiv())){
                if (StringUtils.isEmpty(paramsMap.get("roleName")) && StringUtils.isEmpty(paramsMap.get("applyDatimeStart")) && StringUtils.isEmpty(paramsMap.get("applyDatimeEnd"))){
                    f08011Dto.setStatus(f08011Dto.getReadingStatus());
                    f08011Dto.setOrgNm(null);
                    f08011Dto.setReply1(null);
                    copy.add(f08011Dto);
                }
            }else {
                if (!StringUtils.equals("2", f08011Dto.getRefType())) {
                    F08011Dto applyDel = f08011Dao.getApplyDel(f08011Dto.getEventScheDelId(), paramsMap);
                    //イベント配信者一覧情報取得できない場合
                    if (applyDel != null) {
                        f08011Dto.setStatus(f08011Dto.getReplyStatus());
                        if (StringUtils.equals(f08011Dto.getRefType(), "1")) {
                            f08011Dto.setTeacherName(applyDel.getTeacherName());
                            f08011Dto.setStartTime(applyDel.getStartTime());
                        } else {
                            EventSchePlanDelEntity eventSchePlanDelEntity = eventSchePlanDelService.getById(f08011Dto.getEventScheDelId());
                            f08011Dto.setStartTime(eventSchePlanDelEntity.getSgdStartDatime());
                            f08011Dto.setTeacherName(f08011Dto.getOrgNm());
                        }
                        copy.add(f08011Dto);
                    }
                }else {
                    f08011Dto.setStatus(f08011Dto.getReplyStatus());
                    copy.add(f08011Dto);
                }
            }
            setUpQuestions(f08011Dto);
        }

        //イベント配信者一覧情報取得できない場合
        if (copy.size() == 0) {
            //画面上部のエラーメッセージ領域でワーニングメッセージを表示する
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "配信者"));
        }
        return R.ok().put("page", new PageUtils(copy, copy.size(), searchlimit, searchpage));
    }
    /**
     *<p>検索画面関連タイプの取得</p>
     * @param eventId セッションデータ．イベントID
     * @return
     */
    @RequestMapping(value = "/getRefType", method = RequestMethod.GET)
    public R getRefType(Integer eventId) {
        List<MstCodDEntity> statusList = f08011Dao.getStatus();
        //イベント日程(明細)．関連タイプの取得
        String refType = mstEventService.getById(eventId).getRefType();
        return R.ok().put("refType", refType).put("statusList",statusList);
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
        String menuId = "F08011";
        // 項目数の取得を示す
        Integer dspcount = f08011Service.getDspCount(userId, menuId);

        R info = new R();
        info.put("dspcount", dspcount);
        return info;
    }

    /**
     * <p>項目取得を表示する</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getDspItems", method = RequestMethod.GET)
    public R getDspItems(Integer eventId) {
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        //イベント日程(明細)．関連タイプの取得
        MstEventEntity mstEventEntity = mstEventService.getById(eventId);
        // 画面ID
        String menuId = "F08011";
        // アイテム设定セット取得を表示します
        F08011Dto dspitems = f08011Service.selectDspItem(userId, menuId);
        F08011DspDto f08011DspDto = new F08011DspDto();
        // 集合転換
        f08011DspDto.setDspItemslist(Arrays.asList(dspitems.getDspItems().split(",")));
        f08011DspDto.setMustItems(Arrays.asList(dspitems.getMustItems().split(",")));
        f08011DspDto.setAllItems(Arrays.asList(dspitems.getAllItems().split(",")));
        R info = new R();
        // 集合データに戻り
        info.put("dspitems", f08011DspDto).put("refType", mstEventEntity.getRefType());
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
        F08011DspDto f08011DspDto = new F08011DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        f08011DspDto.setUserId(userId);
        // 画面ID設定
        f08011DspDto.setMenuId("F08011");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        f08011DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        f08011DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        f08011DspDto.setCretUsrId(userId);
        // 更新日時設定
        f08011DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        f08011DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        f08011DspDto.setDelFlg(0);
        // 表示項目の内容を保存する
        Integer status = f08011Service.insertDspItem(f08011DspDto);

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
        F08011DspDto f08011DspDto = new F08011DspDto();
        // ユーザID取得
        String userId = ShiroUtils.getUserEntity().getUsrId();
        // ユーザID設定
        f08011DspDto.setUserId(userId);
        // 画面ID設定
        f08011DspDto.setMenuId("F08011");
        // 項目文字列を表示する設定
        List<String> dspitemsList = JSON.parseArray(dspitems, String.class);
        f08011DspDto.setDspItems(StringUtils.join(dspitemsList.toArray(), ','));
        // 作成日時設定
        f08011DspDto.setCretDatime(DateUtils.getSysTimestamp());
        // 作成ユーザＩＤ設定
        f08011DspDto.setCretUsrId(userId);
        // 更新日時設定
        f08011DspDto.setUpdDatime(DateUtils.getSysTimestamp());
        // 更新ユーザＩＤ設定
        f08011DspDto.setUpdUsrId(userId);
        // 削除フラグ設定
        f08011DspDto.setDelFlg(0);
        // 表示項目の内容を更新する
        Integer status = f08011Service.updateDspItem(f08011DspDto);

        R info = new R();
        info.put("status", status);
        return info;
    }

    @RequestMapping(value = "/getFileName", method = RequestMethod.GET)
    public R getFileName(String dataStr){
        List<String> stuIdList = (List<String>) JSON.parse(dataStr);
        if (stuIdList != null && stuIdList.size() > 0) {
            return f08011Service.getCsvFileName(stuIdList);
        }else {
            return R.error("少なくとも1人の生徒を選択してください。");
        }
    }

    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public Object download(String fileName){
        String tempPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.temp"), fileName);
        File file = new File(tempPath);
        if (!file.exists() || !file.isFile()){
            logger.info("--------------file not exists--------------");
            throw new RRException("ダウンロードエラー。");
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        try {
            headers.add("Content-Disposition", String.format("attachment; filename=\"%s\"", URLEncoder.encode(file.getName(),"UTF-8")));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        try {
            return ResponseEntity
                    .ok()
                    .headers(headers)
                    .contentLength(file.length())
                    .contentType(MediaType.parseMediaType("application/octet-stream"))
                    .body(new InputStreamResource(new FileInputStream(file)));
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RRException("ダウンロードエラー。");
        }finally {
            file.deleteOnExit();
        }
    }

    /**
     * <p>データを取得する。</p>
     *
     * add at 2021/08/12 for V9.02 by NWT wen
     * @param map
     * @return
     */
    @RequestMapping(value = "/getFile", method = RequestMethod.POST)
    public R dataDownload(@RequestBody Map<String, Object> map){
        List<LinkedHashMap<String, Object>> userData = (List<LinkedHashMap<String, Object>>)map.get("param");
        if (userData == null || userData.isEmpty()) {
            return R.error(MessageUtils.getMessage("MSGCOMN0087", "CSVダウンロード対象","対象"));
        }
        String filename = f08011Service.getFile(userData);
        return R.ok().put("filename", filename);
    }

    /**
     * <p>データをダウンロード</p>
     *
     * add at 2021/08/12 for V9.02 by NWT wen
     * @param fileName ファイル名
     * @param response 响应
     */
    @RequestMapping(value = "/downloadFile", method = RequestMethod.POST)
    private void returnFile(HttpServletResponse response, @RequestParam String fileName) {

        // responseパラメータ
        response.reset();
        response.addHeader("Access-Control-Allow-Origin", "*");
        response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE");
        response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("content-type", "application/octet-stream");
        response.setContentType("application/octet-stream");
        try {
            // headerを設定する
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        // ブラウザにファイルを返す
        byte[] buffer = new byte[1024];
        FileInputStream fis = null;
        BufferedInputStream bis = null;
        File file = null;
        try {
            file = FileUtils.getStorageFile(MessageUtils.getMessage("path.temp"), fileName);
            fis = new FileInputStream(file);
            bis = new BufferedInputStream(fis);
            OutputStream os = response.getOutputStream();
            int i = bis.read(buffer);
            while (i != -1) {
                os.write(buffer, 0, i);
                i = bis.read(buffer);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (file.exists()) {
                file.deleteOnExit();
            }
            if (bis != null) {
                try {
                    bis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * <p>フィールドを設定する</p>
     *
     * modify at 2021/08/12 for V9.02 by NWT wen
     * @param dto
     */
    private void setUpQuestions(F08011Dto dto) {
        List<TalkRecordDEntity> talkRecordDEntityList = dto.getTalkRecordDEntityList();
        for (TalkRecordDEntity talkRecordDEntity : talkRecordDEntityList) {
            // add at 2021/09/02 for V9.02 by NWT wen START
            String content = StringUtils.isNotBlank(talkRecordDEntity.getAnswerTypeDiv()) ? talkRecordDEntity.getAnswerTypeDiv() + "," + Optional.ofNullable(
                    talkRecordDEntity.getAnswerReltCont()).orElse("") : "";
            // add at 2021/09/02 for V9.02 by NWT wen END
            switch (talkRecordDEntity.getAskNum()) {
                // modify at 2021/08/12 for V9.02 by NWT wen START
                case 1:
                    dto.setAnswerReltCont1(content);
                    dto.setQuestionName1(talkRecordDEntity.getQuestionName());
                    break;
                case 2:
                    dto.setAnswerReltCont2(content);
                    dto.setQuestionName2(talkRecordDEntity.getQuestionName());
                    break;
                case 3:
                    dto.setAnswerReltCont3(content);
                    dto.setQuestionName3(talkRecordDEntity.getQuestionName());
                    break;
                case 4:
                    dto.setAnswerReltCont4(content);
                    dto.setQuestionName4(talkRecordDEntity.getQuestionName());
                    break;
                case 5:
                    dto.setAnswerReltCont5(content);
                    dto.setQuestionName5(talkRecordDEntity.getQuestionName());
                    break;
                case 6:
                    dto.setAnswerReltCont6(content);
                    dto.setQuestionName6(talkRecordDEntity.getQuestionName());
                    break;
                case 7:
                    dto.setAnswerReltCont7(content);
                    dto.setQuestionName7(talkRecordDEntity.getQuestionName());
                    break;
                case 8:
                    dto.setAnswerReltCont8(content);
                    dto.setQuestionName8(talkRecordDEntity.getQuestionName());
                    break;
                case 9:
                    dto.setAnswerReltCont9(content);
                    dto.setQuestionName9(talkRecordDEntity.getQuestionName());
                    break;
                case 10:
                    dto.setAnswerReltCont10(content);
                    dto.setQuestionName10(talkRecordDEntity.getQuestionName());
                    break;
                // modify at 2021/08/12 for V9.02 by NWT wen END
                default:
            }
        }
    }
}
