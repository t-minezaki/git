/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.*;
import jp.learningpark.modules.common.service.*;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F08002AskTalkDto;
import jp.learningpark.modules.manager.dto.F08002Dto;
import jp.learningpark.modules.manager.dto.F08017Dto;
import jp.learningpark.modules.manager.service.F08002Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>F08002 イベント新規・編集画面 Controller</p >
 *
 * @author NWT : wq <br />
 * 変更履歴 <br />
 * 2019/07/26 : wq: 新規<br />
 * @version 1.0
 */

@RequestMapping("/manager/F08002")
@RestController
public class F08002Controller {

    /**
     * イベントマスタ　Service
     */
    @Autowired
    F08002Service f08002Service;

    /**
     * テンプレートマスタ　Service
     */
    @Autowired
    MstTmplateService mstTmplateService;

    /**
     * テンプレートマスタ　Service
     */
    @Autowired
    MstEventDeliverService mstEventDeliverService;

    /**
     * 共通 Service
     */
    @Autowired
    private CommonService commonService;

    @Autowired
    private MstCodDService mstCodDService;
    /**
     * 保護者お知らせ閲覧状況 Service
     */
    @Autowired
    private GuardEventApplyStsService guardEventApplyStsService;
    /**
     * 生徒イベント申込状況 Service
     */
    @Autowired
    private StuEventApplyStsService stuEventApplyStsService;

    /**
     * 画像の整合ルール
     */
    private Pattern imgPattern = Pattern.compile(
            "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE);

    /**
     * <p>初期</p>
     *
     * @param id イベントId
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R init(Integer id) {

        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //オブジェクト初期化
        MstEventEntity mstEventEntity = null;
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        // 2020/11/24 huangxinliang modify start
        List<OrgAndLowerOrgIdDto> upLvOrgList = commonService.getUpLvOrgList(brandCd, orgId);
        //テンプレートリスト初期化
        List<MstTmplateEntity> mstTmplateEntityList = mstTmplateService.list(new QueryWrapper<MstTmplateEntity>().and(
                w->w.in("org_id", upLvOrgList.stream().map(OrgAndLowerOrgIdDto::getOrgId).collect(Collectors.toList())).eq("del_flg", 0).eq("tmplate_type_div", "2")));
        List<MstCodDEntity> mstCodDEntityList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().eq("cod_key", "ANSWER_TYPE_DIV").eq("del_flg", 0).orderByAsc("sort"));
        // 2020/11/24 huangxinliang modify end
        R initInfo = R.ok();
        initInfo.put("orgIdList", orgIdList);
        initInfo.put("mstTmplateEntityList", mstTmplateEntityList);
        initInfo.put("mstCodDEntityList", mstCodDEntityList);
        if (id != null) {
            //イベントを取得
            mstEventEntity = f08002Service.getInitInfo(id);
            List<F08017Dto> askList = f08002Service.getEventAskList(id, orgId);
            //配信先指定された組織を取得
            List<MstEventDeliverEntity> mstEventDeliverEntityList = mstEventDeliverService.list(new QueryWrapper<MstEventDeliverEntity>().and(
                    /* 2020/12/14 V9.0 cuikailin modify start */
                    /*w->w.eq("event_id", id).eq("org_id",orgId).eq("del_flg", 0)));*/
                    w->w.eq("event_id", id).eq("del_flg", 0)));
            /* 2020/12/14 V9.0 cuikailin modify end */
            Integer guardReadCount = guardEventApplyStsService.count(
                    new QueryWrapper<GuardEventApplyStsEntity>().eq("event_id", id).eq("reply_sts_div", "1").eq("del_flg", 0));
            Integer stuReadCount = stuEventApplyStsService.count(
                    new QueryWrapper<StuEventApplyStsEntity>().eq("event_id", id).eq("reply_sts_div", "1").eq("del_flg", 0));
            boolean activeFlg = true;
            if (guardReadCount > 0 || stuReadCount > 0) {
                activeFlg = false;
            }
            initInfo.put("mstEventEntity", mstEventEntity);
            initInfo.put("mstEventDeliverEntityList", mstEventDeliverEntityList);
            initInfo.put("updateStrForCheck", StringUtils.defaultString(mstEventEntity.getUpdDatime()));
            initInfo.put("mstAskTalkEntities", askList);
            initInfo.put("activeFlg", activeFlg);
        }

        return initInfo;
    }

    /**
     * <p>適用</p>
     *
     * @param tempId テンプレートID
     * @return
     */
    @RequestMapping(value = "/tempFind", method = RequestMethod.GET)
    public R tempFind(Integer tempId) {
        //テンプレートを取得
        MstTmplateEntity mstTmplateEntity = mstTmplateService.getById(tempId);
        List<F08017Dto> templateAskList = f08002Service.getTemplateAskList(tempId, ShiroUtils.getUserEntity().getOrgId());
        //テンプレートに未選択の場合、エラーとなり、処理を中断し
        if (mstTmplateEntity == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テンプレート"));
        }
        return R.ok().put("mstTmplateEntity", mstTmplateEntity).put("mstAskTalkEntities", templateAskList);
    }

    /**
     * <p>登録</p>
     *
     * @param f08002Dto 　引渡データ
     * @param file 　選択されたファイル
     * @param mstAskTalkList askTalkList
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R f08002edit(String f08002Dto, MultipartFile[] file, String mstAskTalkList, String filePaths) throws Exception {
        // String to JavaBean
        F08002Dto dto = JSON.parseObject(StringUtils.defaultString(f08002Dto), new TypeReference<F08002Dto>() {});
        List<F08002AskTalkDto> askTalkList = JSON.parseObject(StringUtils.defaultString(mstAskTalkList), new TypeReference<List<F08002AskTalkDto>>() {
        });
        String noticeCont = dto.getEventCont();
        //新しい画像パスを取得
        if (dto.getTempId() != null){
            String titleImgPath = mstTmplateService.getById(dto.getTempId()).getTitleImgPath();
            dto.setTitleImgPath(titleImgPath);
        }else {
            List<String> imgPathList = getSrcList(URLDecoder.decode(dto.getEventCont(), "UTF-8"), imgPattern, "/gakken/image");
            //画像
            Map<String, String> map = FileUtils.getRealPath(imgPathList, "/ueditorUpload", "imageUrlPrefix");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                noticeCont = noticeCont.replace(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
                if (imgPathList.size() == 0) {
                    dto.setTitleImgPath("");
                } else {
                    dto.setTitleImgPath(entry.getValue());
                }
            }
        }
        if ((file == null || file.length == 0) && !StringUtils.isEmpty(filePaths)) {
            file = FileUtils.generateMultipartFiles(filePaths);
        }
        //添付ファイルパス
        String filePath = null;
        //ファイル
        if (file != null) {
            //添付ファイルパスの保存
            StringBuilder filePathTemp = new StringBuilder();
            for (MultipartFile fileItem : file) {
                if (fileItem == null){
                    continue;
                }
                String path = getPath(fileItem);
                // パスが空の場合はスキップします
                if (!StringUtils.isEmpty(filePathTemp.toString())){
                    filePathTemp.append(",");
                }
                filePathTemp.append(path);
            }
            filePath = filePathTemp.toString();
        }

        if (!StringUtils.isEmpty(filePath)) {
            dto.setAttachFilePath(filePath);
        }else {
            if ((file == null || file.length == 0 && StringUtils.isEmpty(filePaths))){
                dto.setAttachFilePath("");
            }
        }
        return f08002Service.doInsert(dto, noticeCont, askTalkList);
    }

    /**
     * ファイルパス
     *
     * @param file 　画像
     * @return fileName
     * @throws IOException
     */
    public String getPath(MultipartFile file) throws IOException {
        // ファイル名を生成
        String fileName = file.getOriginalFilename().substring(0, file.getOriginalFilename().lastIndexOf(".")) + DateUtils.format(DateUtils.getSysTimestamp(),
                GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSSSSS) + file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf("."));
        //ローカルパス
        String realPath = ("event/" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH)).replace(
                "/", File.separator) + File.separator + fileName;
        // 生成サーバパス（データベースパス）
        String savePath = GakkenConstant.FILE_PATH_PREFIX + realPath;
        // ファイルを生成
        File destFile = FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath);
        if (!destFile.exists()) {
            destFile.getParentFile().mkdirs();
            destFile.createNewFile();
        }
        InputStream in = file.getInputStream();
        FileOutputStream out = new FileOutputStream(destFile);
        byte buffer[] = new byte[1024];
        int len = 0;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        // データベースパス
        String destFileName = savePath;

        return destFileName;
    }

    /**
     * パスを取得
     *
     * @param htmlCode
     */
    public List<String> getSrcList(String htmlCode, Pattern pattern, String contains) {
        List<String> srcList = new ArrayList<String>();
        Matcher m = pattern.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            srcList.add(src);
        }
        return srcList;
    }
}
