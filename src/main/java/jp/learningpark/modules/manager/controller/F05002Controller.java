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
import jp.learningpark.modules.common.entity.MstNoticeEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F05002Dto;
import jp.learningpark.modules.manager.dto.F05002DtoIn;
import jp.learningpark.modules.manager.dto.pushMsgReceives;
import jp.learningpark.modules.manager.service.F05002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>F05002 知らせ新規画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2019/02/26 : gong: 新規<br />
 * @version 1.0
 * 2019/06/14 : hujiaxing : mod
 */
@RequestMapping("/manager/F05002")
@RestController
public class F05002Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F05002 知らせ新規画面　service
     */
    @Autowired
    private F05002Service f05002Service;

    /**
     * 共通処理 Service
     */
    @Autowired
    CommonService commonService;

    /**
     * テンプレートマスタ　Service
     */
    @Autowired
    MstTmplateService mstTmplateService;

    /**
     * 画像の整合ルール
     */
    private Pattern imgPattern = Pattern.compile(
            "<img\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.jpg|\\.bmp|\\.eps|\\.gif|\\.mif|\\.miff|\\.png|\\.tif|\\.tiff|\\.svg|\\.wmf|\\.jpe|\\.jpeg|\\.dib|\\.ico|\\.tga|\\.cut|\\.pic)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE);

    /**
     * 添付ファイルの整合ルール
     */
    private Pattern filePattern = Pattern.compile(
            "<a\\b[^>]*\\bhref\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.docx|\\.pptx|\\.pdf)\\b)[^>]*>", Pattern.CASE_INSENSITIVE);

    /**
     * 動画の整合ルール
     */
    private Pattern videoPattern = Pattern.compile(
            "<video\\b[^>]*\\bsrc\\b\\s*=\\s*('|\")?([^'\"\n\r\f>]+(\\.ogg|\\.ogv|\\.mov|\\.wmv|\\.mp4|\\.webm|\\.mp3|\\.wav|\\.mid|\\.flv|\\.swf|\\.mkv|\\.avi|\\.rm|\\.rmvb|\\.rmvb|\\.mpg)\\b)[^>]*>",
            Pattern.CASE_INSENSITIVE);

    /**
     * <p>初期表示</p>
     * <p>
     * 2019/06/14 hujiaxing: mod
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f05002Init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //get org by orgid
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        //テンプレートリスト初期化
        List<MstTmplateEntity> mstTmplateEntityList = getMstTemp(ShiroUtils.getBrandcd(),orgId,"4");
        return R.ok().put("org", org).put("mstTmplateEntityList", mstTmplateEntityList);
    }

    /**
     * <p>生徒エンティのリストを生徒IDリストから取得する</p>
     *
     * @param stuIdList 生徒IDリスト
     * @return
     */
    @RequestMapping(value = "/getStuList", method = RequestMethod.POST)
    public R getStuList(@RequestParam(value = "stuIdList") List<String> stuIdList) {
        //  生徒エンティのリストを生徒IDリストから取得する
        List<F05002DtoIn> dtoList = f05002Service.selectStuByIdList(stuIdList);
        return R.ok().put("dtoList", dtoList);
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public R page(Integer limit, Integer page, String selectData) {
        if (StringUtils.isEmpty(selectData)) {
            return R.ok();
        } else {
            List<String> data = JSON.parseArray(selectData, String.class);
            if (data.size() == 0) {
                return R.ok();
            }
            List<F05002DtoIn> stuList = f05002Service.selectStuByIdList(data);
            return R.ok().put("page", new PageUtils(stuList, stuList.size(), limit, page));
        }
    }

    /**
     * <p>登録処理</p>
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R f05002Submit(String f05002Dto, MultipartFile[] file, boolean pushFlg) throws Exception {
        // String to JavaBean
        F05002Dto dto = JSON.parseObject(StringUtils.defaultString(f05002Dto), new TypeReference<F05002Dto>() {
        });
        //組織Id
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //日付チェック
        dto.setPubPlanStartDtStr(dto.getPubPlanStartDtStr().replace("/", "-"));
        Timestamp pubPlanStartDtNew = new Timestamp(DateUtils.parse(dto.getPubPlanStartDtStr() + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        dto.setPubPlanEndDtStr(dto.getPubPlanEndDtStr().replace("/", "-"));
        Timestamp pubPlanEndDtNew = new Timestamp(DateUtils.parse(dto.getPubPlanEndDtStr() + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        if (pubPlanEndDtNew.compareTo(pubPlanStartDtNew) < 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0048", "掲載開始日時", "掲載終了日時"));
        }
        dto.setPubPlanStartDt(pubPlanStartDtNew);
        dto.setPubPlanEndDt(pubPlanEndDtNew);

        String noticeCont = dto.getNoticeCont();
        //新しい画像パスを取得
        if (dto.getTempId() != null){
            String titleImgPath = mstTmplateService.getById(dto.getTempId()).getTitleImgPath();
            dto.setTitleImgPath(titleImgPath);
        }else {
            List<String> imgPathList = getSrcList(URLDecoder.decode(dto.getNoticeCont(), "UTF-8"), imgPattern, "/gakken/image");
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
        //ファイル
        StringBuilder filePath = new StringBuilder();
        if ((file == null || file.length == 0) && !StringUtils.isEmpty(dto.getFilePaths())) {
            file = FileUtils.generateMultipartFiles(dto.getFilePaths());
        }
        if (file != null) {
            for (MultipartFile fileItem : file) {
                String path = getPath(fileItem);
                // パスが空の場合はスキップします

                if (!StringUtils.isEmpty(filePath.toString())){
                    filePath.append(",");
                }
                filePath.append(path);
            }
        }

        if (!StringUtils.isEmpty(filePath.toString())) {
            //添付ファイルパスを設定する
            dto.setAttachFilePath(filePath.toString());
        } else {
            //添付ファイルパスを設定する
            dto.setAttachFilePath("");
        }
        MstNoticeEntity mstNoticeEntity = f05002Service.doInsert(dto, noticeCont, orgId);
        if (pushFlg) {
            List<String> stuIdList = new ArrayList<>();
            List<String> guardIdList = new ArrayList<>();
            for (int i = 0; i < dto.getStuList().size(); i++) {
                stuIdList.add(dto.getStuList().get(i).getStuId());
                guardIdList.add(dto.getStuList().get(i).getGuardId());
            }
            pushMsgReceives receiver = new pushMsgReceives();
            //生徒マナミル一意ID
            receiver.setStuId(stuIdList);
            //保護者ID
            receiver.setReceiverId(guardIdList);
            //comment
            receiver.setComment("ここはcomment");
            //message
            receiver.setMessageCode("0");
            //優先
            receiver.setPriority(1);
            //予定送信開始時間
            receiver.setSendTime(mstNoticeEntity.getPubPlanStartDt().getTime());
            receiver.setMsgType(Constant.sendMsgTypeNotice);
            f05002Service.sendMessage(receiver, mstNoticeEntity);
        }
        return R.ok();
    }

    /**
     * パスリストを取得
     *
     * @param htmlCode htmlテキスト
     * @param pattern 整合ルール
     * @param contains 判断用文字列
     */
    public List<String> getSrcList(String htmlCode, Pattern pattern, String contains) {
        List<String> imageSrcList = new ArrayList<String>();
        Matcher m = pattern.matcher(htmlCode);
        String quote = null;
        String src = null;
        while (m.find()) {
            quote = m.group(1);
            src = (quote == null || quote.trim().length() == 0) ? m.group(2).split("\\s+")[0] : m.group(2);
            if (src.contains(contains)) {
                imageSrcList.add(src);
            }
        }
        return imageSrcList;
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
        String realPath = "notice" + File.separator + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH).replace(
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
        byte[] buffer = new byte[1024];
        int len;
        while ((len = in.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        in.close();
        out.close();
        // データベースパス
        return savePath;
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
        //テンプレートに未選択の場合、エラーとなり、処理を中断し
        if (mstTmplateEntity == null) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "テンプレート"));
        }
        return R.ok().put("mstTmplateEntity", mstTmplateEntity);
    }

    public List<MstTmplateEntity> getMstTemp(String brandCd,String orgId,String type){
        List<OrgAndLowerOrgIdDto> upLvOrgList = commonService.getUpLvOrgList(brandCd, orgId);
        //テンプレートリスト初期化
        List<MstTmplateEntity> mstTmplateEntityList = mstTmplateService.list(new QueryWrapper<MstTmplateEntity>().and(
                w->w.in("org_id", upLvOrgList.stream().map(OrgAndLowerOrgIdDto::getOrgId).collect(Collectors.toList()))
                        .eq("tmplate_type_div",type)
                        .eq("del_flg", 0)));
        return mstTmplateEntityList;
    }
}
