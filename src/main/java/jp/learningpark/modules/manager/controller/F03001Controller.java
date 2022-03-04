/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.entity.MstUnitEntity;
import jp.learningpark.modules.common.entity.TextbDefTimeInfoEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.common.service.MstUnitService;
import jp.learningpark.modules.common.service.TextbDefTimeInfoService;
import jp.learningpark.modules.manager.dto.F03001Dto;
import jp.learningpark.modules.manager.dto.F03001ExportDto;
import jp.learningpark.modules.manager.service.F03001Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>教科書一覧</p >
 *
 * @author NWT : hujunjie <br />
 * 変更履歴 <br />
 * 2018/12/26 : hujunjie: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F03001")
@RestController
public class F03001Controller {
    /**
     * 教科書一覧Service
     */
    @Autowired
    F03001Service f03001Service;

    /**
     * 教科書デフォルトターム情報
     */
    @Autowired
    TextbDefTimeInfoService textbDefTimeInfoService;

    /**
     * 単元マスタ
     */
    @Autowired
    MstUnitService mstUnitService;

    @Autowired
    MstOrgService mstOrgService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 教科書一覧画面初期表示
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f03001init() {
        // セッション・組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //ブランドコード
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        // セッション・ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        // 検索条件リスト
        List<F03001Dto> schyList = f03001Service.getSchyList();
        List<F03001Dto> subjtList = f03001Service.getSubjtList();
        List<F03001Dto> publisherList = f03001Service.getPublisherList();
        //本組織と全部の上層組織Idを取得
        List<F03001Dto> upLevList = f03001Service.selectAllUpLevOrgByOrgId(orgId, brandCd);

        R info = new R();
        info.put("schyList", schyList);
        info.put("subjtList", subjtList);
        info.put("publisherList", publisherList);
        info.put("orgId", orgId);
        info.put("orgList", upLevList);
        info.put("org", org);
        return info;
    }

    /**
     * 教科書検索
     *
     * @param schyDiv 学年区分
     * @param subjtDiv 教科区分
     * @param publisherDiv 出版社区分
     * @param page ページ
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R selectTextbList(String orgId, String schyDiv, String subjtDiv, String publisherDiv, Integer limit, Integer page, String textbNm) {
        if (StringUtils.equals("null", orgId)) {
            orgId = ShiroUtils.getUserEntity().getOrgId();
        }
        // セッション・組織ID
        //        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // セッション・ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        if ("null".equals(schyDiv)) {
            schyDiv = "";
        }
        if ("null".equals(subjtDiv)) {
            subjtDiv = "";
        }
        if ("null".equals(publisherDiv)) {
            publisherDiv = "";
        }
        if ("null".equals(textbNm)) {
            textbNm = "";
        }
        // 教科書一覧カウント
        Integer total = f03001Service.getTextbCount(orgId, schyDiv, subjtDiv, publisherDiv, brandCd, textbNm, null);
        R info = new R();

        //教科書存在チェック
        if (total != null && total != 0) {
            //下位組織一覧情報
            List<F03001Dto> textbListShow = f03001Service.getTextbListUpLevel(
                    orgId, schyDiv, subjtDiv, publisherDiv, brandCd, limit, (page - 1) * limit, textbNm);
            for (F03001Dto dto : textbListShow) {
                dto.setMtUpdTm(DateUtils.format(dto.mtUpdTmTimestamp(), Constant.DATE_FORMAT_YYYYMMDDHHMMSSSSS));
            }
            info.put("textbList", textbListShow);
            info.put("page", new PageUtils(textbListShow, total, limit, page));
        } else {
            return R.error(500, MessageUtils.getMessage("MSGCOMN0017", "教科書"));
        }
        return info;
    }

    /**
     * 塾学習期間IDの取得
     *
     * @param textbId 教科書ID
     * @return
     */
    @RequestMapping(value = "/selectMstCrmschId", method = RequestMethod.GET)
    public R selectMstCrmschLearnPrdId(Integer textbId) {
        // セッション・組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        // セッション・ブランドコード
        String brandCd = ShiroUtils.getBrandcd();
        // 塾学習期間IDの取得
        Integer mstCrmschLearnPrdId = f03001Service.selectMstCrmschLearnPrdId(textbId, orgId, brandCd);
        if (mstCrmschLearnPrdId != null) {
            ShiroUtils.setSessionAttribute(GakkenConstant.CRM_LEARN_PRD_ID, mstCrmschLearnPrdId);
        } else {
            return R.error(MessageUtils.getMessage("MSGCOMN0123", "塾時期"));
        }
        return R.ok();
    }

    /**
     * 教科書削除
     *
     * @param f03001Dto 教科書一覧Dto
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public R delBook(F03001Dto f03001Dto) {
        return f03001Service.delBook(f03001Dto);
    }

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param schyDiv 学年区分
     * @param subjtDiv 教科書区分
     * @param publisherDiv 出版社区分
     * @param textbNm 教科書名
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public R importExcel(MultipartFile file, String schyDiv, String subjtDiv, String publisherDiv, String textbNm) throws UnsupportedEncodingException {
        textbNm = URLDecoder.decode(textbNm, "UTF-8");
        return f03001Service.importFile(file, schyDiv, subjtDiv, publisherDiv, textbNm);
    }

    /**
     * <p>エクスポート</p>
     *
     * @param textbId 　教科書ID
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public R exportExcel(Integer textbId) {
        //出力ファイル名
        String fileNm = ShiroUtils.getUserEntity().getOrgId();
        //テンプレート取り込む
        File file;
        InputStream inputStream = this.getClass().getResourceAsStream("/templates/excel/01_教科書単元詳細ファイル(エクスポート)_template.xlsx");
        FileOutputStream fileOutputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb;
        try {
            wb = new XSSFWorkbook(inputStream);
            //教科書デフォルトターム情報に教科書ID対応したデータリストを取得
            List<TextbDefTimeInfoEntity> textbDefTimeInfoEntityList = textbDefTimeInfoService.list(
                    new QueryWrapper<TextbDefTimeInfoEntity>().and(w->w.eq("textb_id", textbId).eq("del_flg", 0)).orderByAsc("dispy_order"));
            if (textbDefTimeInfoEntityList == null || textbDefTimeInfoEntityList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科書単元"));
            }
            String[] pageSplit = null;
            //F03001ExportDtoList
            List<F03001ExportDto> f03001ExportDtoList = new ArrayList<>();
            for (int i = 0; i < textbDefTimeInfoEntityList.size(); i++) {
                TextbDefTimeInfoEntity entity = textbDefTimeInfoEntityList.get(i);
                MstUnitEntity mstUnitEntity = mstUnitService.getById(entity.getUnitId());
                if (mstUnitEntity != null) {
                    //F03001ExportDto
                    F03001ExportDto f03001ExportDto = new F03001ExportDto();
                    pageSplit = entity.getTextbPage().split("-");
                    //単元ID
                    f03001ExportDto.setUnitId(entity.getUnitId());
                    //単元NO
                    f03001ExportDto.setUnitNo(entity.getUnitNo());
                    //節NO
                    f03001ExportDto.setSectnNo(entity.getSectnNo());
                    //章NO
                    f03001ExportDto.setChaptNo(entity.getChaptNo());
                    //章名
                    f03001ExportDto.setChaptNm(mstUnitEntity.getChaptNm());
                    //節名
                    f03001ExportDto.setSectnNm(mstUnitEntity.getSectnNm());
                    //項目表示名
                    f03001ExportDto.setUnitDispyNm(entity.getUnitDispyNm());
                    if (entity.getTextbPage().contains("-")) {
                        //ページ（開始）
                        f03001ExportDto.setPageStart(pageSplit[0]);
                        //ページ（終了）
                        f03001ExportDto.setPageEnd(pageSplit[1]);
                    } else {
                        //ページ（開始）
                        f03001ExportDto.setPageStart(entity.getTextbPage());
                        //ページ（終了）
                        f03001ExportDto.setPageEnd("");
                    }

                    //学習時期
                    f03001ExportDto.setPlanLearnSeasn(entity.getPlanLearnSeasn());
                    //目標学習時間
                    f03001ExportDto.setPlanLearnTm(entity.getPlanLearnTm());
                    //F03001ExportDtoList
                    f03001ExportDtoList.add(i, f03001ExportDto);
                } else {
                    return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科書単元"));
                }
            }
            String field = "unitId,chaptNo,sectnNo,unitNo,chaptNm,sectnNm,unitDispyNm,pageStart,pageEnd,planLearnSeasn,planLearnTm";
            String[] fieldArray = field.split(",");
            fileNm += "_F03001_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            wb = ExcelUtils.createExcelWb(wb, 0, fieldArray, f03001ExportDtoList);
            file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
            if (!file.exists()) {
                try {
                    file.getParentFile().mkdirs();
                    file.createNewFile();
                } catch (Exception e) {
                    logger.error(e.getMessage());
                }
            }
            fileOutputStream = new FileOutputStream(file);
            wb.write(fileOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        try {
            inputStream.close();
            if (fileOutputStream != null) {
                fileOutputStream.close();
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
        return R.ok().put("fileNm", fileNm);
    }

    /**
     * 教科書デフォルトターム情報を元に、教科書デフォルトターム情報を取得し、エクスポートファイルに出力する。
     *
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void f03001download(HttpServletResponse response, @RequestParam String fileNm) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String donloadFileName = orgId + "_教科書単元詳細ファイル_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileNm);
        ExcelUtils.excelDownload(response, outPath, donloadFileName);
    }

    /**
     * テンプレートファイルを出力ファイルに出力して保存する
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
    public void getTemplate(HttpServletResponse response) {
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String fileNm = "01_教科書単元詳細ファイル(インポート)_template.xlsx";
        inputStream = this.getClass().getResourceAsStream("/templates/excel/01_教科書単元詳細ファイル(インポート)_template.xlsx");
        try {
            wb = new XSSFWorkbook(inputStream);
            ExcelUtils.getTemplate(response, wb, fileNm);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                }
                if (inputStream != null) {
                    inputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
    }
}
