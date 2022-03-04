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
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.utils.dto.OrgAndLowerOrgIdDto;
import jp.learningpark.modules.common.utils.service.CommonService;
import jp.learningpark.modules.manager.dto.F04002Dto;
import jp.learningpark.modules.manager.dto.F04002DtoIn;
import jp.learningpark.modules.manager.service.F04002Service;
import jp.learningpark.modules.sys.controller.AbstractController;
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

/**
 * <p>F04002 塾ニュース新規画面 Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/03/06 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F04002")
@RestController
public class F04002Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F04002 塾ニュース新規画面 service
     */
    @Autowired
    private F04002Service f04002Service;

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
     * <p>初期表示</p>
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f04002Init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        List<OrgAndLowerOrgIdDto> orgIdList = commonService.getThisAndLowerOrgId(brandCd, orgId);
        return R.ok().put("org", org).put("orgIdList", orgIdList);
    }

    /**
     * @param f04002Dto
     * @param file
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/edit", method = RequestMethod.POST)
    public R f04002edit(String f04002Dto, MultipartFile file) throws Exception {
        // String to JavaBean
        F04002Dto dto = JSON.parseObject(StringUtils.defaultString(f04002Dto), new TypeReference<F04002Dto>() {
        });
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //画像パスを取得
        List<String> pathList = getSrcList(URLDecoder.decode(dto.getNoticeCont(), "UTF-8"), imgPattern, "/gakken/image");
        //画像
        Map<String, String> map = FileUtils.getRealPath(pathList, "/ueditorUpload", "imageUrlPrefix");
        String noticeCont = dto.getNoticeCont();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            noticeCont = noticeCont.replace(URLEncoder.encode(entry.getKey(), "UTF-8"), URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
        pathList = getSrcList(URLDecoder.decode(noticeCont, "UTF-8"), imgPattern, "/server-image/");
        if (pathList.size() == 0) {
            dto.setTitleImgPath("");
        } else {
            dto.setTitleImgPath(pathList.get(0));
        }

        //ファイル
        if (file != null) {
            String filePath = getPath(file);
            if (!StringUtils.isEmpty(filePath)) {
                dto.setAttachFilePath(filePath);
            } else {
                dto.setAttachFilePath("");
            }
        } else {
            dto.setAttachFilePath("");
        }

        //日付のフォーマットを変更
        dto.setPubPlanStartDtStr(dto.getPubPlanStartDtStr().replace("/", "-"));
        Timestamp pubPlanStartDtNew = new Timestamp(DateUtils.parse(dto.getPubPlanStartDtStr() + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        dto.setPubPlanEndDtStr(dto.getPubPlanEndDtStr().replace("/", "-"));
        Timestamp pubPlanEndDtNew = new Timestamp(DateUtils.parse(dto.getPubPlanEndDtStr() + ":00", GakkenConstant.DATE_FORMAT_YYYYMMDDHHMM_ISO).getTime());
        if (pubPlanEndDtNew.compareTo(pubPlanStartDtNew) < 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0048", "掲載開始日時", "掲載終了日時"));
        }
        dto.setPubPlanStartDt(pubPlanStartDtNew);
        dto.setPubPlanEndDt(pubPlanEndDtNew);

        return f04002Service.doInsert(dto, noticeCont, orgId);
    }

    //学生リストを取得
    @RequestMapping(value = "/getStu", method = RequestMethod.POST)
    public R f04002getStu(Integer limit, Integer page, String selectData) {
        if (StringUtils.isEmpty(selectData)) {
            return R.ok();
        } else {
            List<String> data = JSON.parseArray(selectData, String.class);
            List<F04002DtoIn> stuList = f04002Service.getList(data);
            return R.ok().put("page", new PageUtils(stuList, stuList.size(), limit, page));
        }
    }

    /**
     * @return
     */
    @RequestMapping(value = "/getPage", method = RequestMethod.GET)
    public R page(Integer limit, Integer page, String selectData) {
        if (StringUtils.isEmpty(selectData)) {
            return R.ok();
        } else {
            //            List<F08012Dto> data = (List<F08012Dto>) JSON.parse(selectData);
            List<String> data = JSON.parseArray(selectData, String.class);
            if (data.size() == 0) {
                return R.ok();
            }
            List<F04002DtoIn> stuList = f04002Service.getList(data);
            return R.ok().put("page", new PageUtils(stuList, stuList.size(), limit, page));
        }
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
            if (src.contains(contains)) {
                srcList.add(src);
            }
        }
        return srcList;
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
        String realPath = ("news/" + DateUtils.format(DateUtils.getSysTimestamp(), GakkenConstant.DATE_FORMAT_YYYY_MM_SLASH)).replace(
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
        FileOutputStream out = new FileOutputStream(FileUtils.getStorageFile(MessageUtils.getMessage("path.file"), realPath));
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
}
