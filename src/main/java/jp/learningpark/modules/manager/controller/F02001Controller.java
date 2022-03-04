/*
 * (C) 2018 LIGHTWORKS CORP.
 * システム名 : 学研アプリ
 * 注意事項 :
 */
package jp.learningpark.modules.manager.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ExcelUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.common.entity.MstCodDEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstCodDService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F02001Dto;
import jp.learningpark.modules.manager.service.F02001Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
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
import java.util.List;

/**
 * <p>F02001 単元情報インポート・エクスポート画面  Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2019/01/03 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F02001")
@RestController
public class F02001Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstCodDService mstCodDService;

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F02001 単元情報インポート・エクスポート画面 service
     */
    @Autowired
    private F02001Service f02001Service;

    /**
     * <p>初期表示</p>
     *
     * @return
     */
    @RequestMapping(value = "/Init", method = RequestMethod.GET)
    public R f02001init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        List<MstCodDEntity> mstCodDEntitySchyDivList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(w->w.eq("cod_key", "SCHY_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        if (mstCodDEntitySchyDivList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "学年")).put("org", org);
        }
        List<MstCodDEntity> mstCodDEntitySubjtDivList = mstCodDService.list(
                new QueryWrapper<MstCodDEntity>().select("cod_cd", "cod_value").and(w->w.eq("cod_key", "SUBJT_DIV").eq("del_flg", 0)).orderByAsc("sort"));
        if (mstCodDEntitySubjtDivList.size() == 0) {
            return R.error(MessageUtils.getMessage("MSGCOMN0017", "教科")).put("f02001SchyDivList", mstCodDEntitySchyDivList).put("org", org);
    }
        return R.ok().put("org", org).put("f02001SchyDivList", mstCodDEntitySchyDivList).put("f02001SubjtDivList", mstCodDEntitySubjtDivList);
    }

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param type
     * @return
     */
    @RequestMapping(value = "/Import", method = RequestMethod.POST)
    public R f02001Import(MultipartFile file, Integer type) {
        return f02001Service.importFile(file, type);
    }

    /**
     * <p>エクスポート</p>
     *
     * @param schyDiv 学年コード
     * @param subjtDiv 教科コード
     * @return
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public R f02001Export(String schyDiv, String subjtDiv) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        List lowerOrgId = f02001Service.getLowerOrg(orgId);
        if (lowerOrgId.size() == 0) {
            lowerOrgId.add("");
        }
        List upOrgId = f02001Service.getUpOrg(orgId);
        if (upOrgId.size() == 0) {
            upOrgId.add("");
        }
        //出力ファイル名
        String fileNm = orgId;
        FileOutputStream fileOutputStream = null;
        //テンプレート取り込む
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        try {
            inputStream = this.getClass().getResourceAsStream("/templates/excel/01_単元情報(エクスポート)_template.xlsx");
            fileNm += "_02001_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            wb = new XSSFWorkbook(inputStream);
            List<F02001Dto> f02001DtoList = f02001Service.getMstUnitInfo(orgId, schyDiv, subjtDiv, upOrgId, lowerOrgId);
            if (f02001DtoList.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "単元"));
            }
            String field = "isOrg,id,orgId,schyDiv,subjtDiv,unitMstCd,chaptNm,sectnNm,unitNm";
            String[] fieldArray = field.split(",");

            wb = ExcelUtils.createExcelWbF02001(wb, 0, fieldArray, f02001DtoList);
            wb.write(fileOutputStream);

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                    inputStream.close();
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("fileNm", fileNm);
    }

    /**
     * <p>DBから⑩で選択した保存先に単元情報を出力ファイルに出力して保存する。</p>
     *
     * @param response
     * @param fileNm ファイル名
     */
    @RequestMapping(value = "/Download", method = RequestMethod.POST)
    public void f02001download(HttpServletResponse response, @RequestParam String fileNm) {

        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String downFileName = orgId + "_単元情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileNm);
        ExcelUtils.excelDownload(response, outPath, downFileName);
    }

    /**
     * <p>テンプレートファイルを出力ファイルに出力して保存する</p>
     *
     * @param response
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
    public void getTemplate(HttpServletResponse response) {
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String fileNm = "01_単元情報_インポート_template.xlsx";
        inputStream = this.getClass().getResourceAsStream("/templates/excel/01_単元情報(インポート)_template.xlsx");
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
