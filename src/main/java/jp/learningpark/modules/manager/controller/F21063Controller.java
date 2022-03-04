package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstMessageEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstTmplateEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstTmplateService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F21063Dto;
import jp.learningpark.modules.manager.dto.F21063DtoIn;
import jp.learningpark.modules.manager.service.F21063Service;
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
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * <p>F21063_メッセージ作成画面 Controller</p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2020/05/20 ： NWT)hxl ： 新規作成
 * 2020/11/11 ： NWT)文 ： 要件変更
 */
@RequestMapping("/manager/F21063")
@RestController
public class F21063Controller {

    /**
     * F21063 知らせ新規画面　service
     */
    @Autowired
    private F21063Service f21063Service;
    /**
     * テンプレートマスタ　Service
     */
    @Autowired
    MstTmplateService mstTmplateService;
    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;
    /**
     * 共通 Service
     */
    @Autowired
    private CommonService commonService;

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
     * 2021/02/24 liguangxin: mod
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f21063Init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //get org by orgid
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        return R.ok().put("org", org);
    }
    /**
     * @param limit limit
     * @param page page
     * @param selectData stuIdList
     * @return
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.POST)
    public R page(Integer limit, Integer page, String selectData) {
        if (StringUtils.isEmpty(selectData)) {
            return R.ok();
        } else {
            List<String> data = JSON.parseArray(selectData, String.class);
            if (data.size() == 0) {
                return R.ok();
            }
            List<F21063DtoIn> stuList = f21063Service.selectStuByIdList(data, page, limit);
            List<F21063DtoIn> stuListCount = f21063Service.selectStuByIdListCount(data);
            /* 2020/01/07 liguangxin add start*/
            return R.ok().put("page", new PageUtils(stuList, stuListCount.size(), limit, page)).put("total", stuListCount);
            /* 2020/01/07 liguangxin add end*/
        }
    }

    /**
     * <p>登録処理</p>
     *
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public R f21063Submit(String f21063Dto, MultipartFile[] file,boolean pushFlg) throws Exception {
        // String to JavaBean
        F21063Dto dto = JSON.parseObject(StringUtils.defaultString(f21063Dto), new TypeReference<F21063Dto>() {
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
        String messageCont = dto.getMessageCont();
        //新しい画像パスを取得
        if (dto.getTempId() != null){
            String titleImgPath = mstTmplateService.getById(dto.getTempId()).getTitleImgPath();
            dto.setTitleImgPath(titleImgPath);
        }else {
            List<String> imgPathList = getSrcList(URLDecoder.decode(dto.getMessageCont(), "UTF-8"), imgPattern, "/gakken/image");
            //画像
            Map<String, String> map = FileUtils.getRealPath(imgPathList, "/ueditorUpload", "imageUrlPrefix");
            for (Map.Entry<String, String> entry : map.entrySet()) {
                messageCont = messageCont.replace(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
                if (imgPathList.size() == 0) {
                    dto.setTitleImgPath("");
                } else {
                    dto.setTitleImgPath(entry.getValue());
                }
            }
        }
        //添付ファイルパス
        String filePath = null;
        //ファイル
        if ((file == null || file.length == 0) && !StringUtils.isEmpty(dto.getFilePaths())) {
            file = FileUtils.generateMultipartFiles(dto.getFilePaths());
        }
        if (file != null) {
            //添付ファイルパスの保存
            StringBuilder filePathTemp = new StringBuilder();
            for (MultipartFile fileItem : file) {
                String path = getPath(fileItem);
                // パスが空の場合はスキップします
                if (!StringUtils.isEmpty(filePathTemp.toString())){
                    filePathTemp.append(",");
                }
                filePathTemp.append(path);
            }
            filePath = filePathTemp.toString();
        }
        //添付ファイルパスを設定する
        if (!StringUtils.isEmpty(filePath)) {
            dto.setAttachFilePath(filePath);
        }else {
            dto.setAttachFilePath("");
        }
        MstMessageEntity mstMessageEntity = (MstMessageEntity)f21063Service.doInsert(dto, messageCont, orgId).get("mstMessageEntity");
        if (pushFlg) {
            f21063Service.sendMessage(dto, mstMessageEntity);
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
        String realPath = "message" + File.separator + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH).replace(
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
     * <p>テンプレート名取得</p>
     *
     * @return R 画面情報
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.GET)
    public R getTemplate() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        //本組織及び下層組織リストの取得
        List<OrgAndLowerOrgIdDto> upLvOrgList = commonService.getUpLvOrgList(brandCd, orgId);
        //テンプレートリスト初期化
        List<MstTmplateEntity> mstTmplateEntityList = mstTmplateService.list(new QueryWrapper<MstTmplateEntity>().and(
                w->w.in("org_id", upLvOrgList.stream().map(OrgAndLowerOrgIdDto::getOrgId).collect(Collectors.toList())).eq("del_flg", 0)
                        .eq("tmplate_type_div","5")));
        R initInfo = R.ok();
        initInfo.put("mstTmplateEntityList", mstTmplateEntityList);
        return initInfo;
    }
}
