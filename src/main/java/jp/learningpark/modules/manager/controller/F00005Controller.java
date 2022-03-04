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
import jp.learningpark.modules.common.entity.MstGrpEntity;
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstGrpService;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F00005Dto;
import jp.learningpark.modules.manager.service.F00005Service;
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
 * <p>F00005_生徒グループ管理画面 Controller</p >
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00005")
@RestController
public class F00005Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F00005_生徒グループ管理画面 service
     */
    @Autowired
    private F00005Service f00005Service;

    /**
     * グループ Service
     */
    @Autowired
    private MstGrpService mstGrpService;

    /**
     * <p>初期表示</p>
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00005Init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        return R.ok().put("org", org);
    }

    /**
     * <p>グループ情報のインポート</p>
     *
     * @param file ファイル
     * @param type 選択したロール
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public R importExcel(MultipartFile file, Integer type) {
        return f00005Service.importFile(file, type);
    }

    /**
     * <p>生徒・グループ情報のインポート</p>
     *
     * @param file ファイル
     * @param type 選択したロール
     * @return
     */
    @RequestMapping(value = "/importStuGrp", method = RequestMethod.POST)
    public R importExcelStuGrp(MultipartFile file, Integer type) {
        return f00005Service.importFileStuGrp(file, type);
    }

    /**
     * <p>グループ情報のインポート</p>
     *
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public R exportExcel() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //出力ファイル名
        String fileNm = orgId + "_F00005_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
        int index = 1;
        File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        FileOutputStream fileOutputStream = null;
        //テンプレート取り込む
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        inputStream = this.getClass().getResourceAsStream("/templates/excel/01_グループ情報(エクスポート)_template.xlsx");
        try {
            fileOutputStream = new FileOutputStream(file);
            wb = new XSSFWorkbook(inputStream);
            List<MstGrpEntity> mstGrpEntityList = mstGrpService.list(new QueryWrapper<MstGrpEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
            //20200521 NWT文 dayweekDivを追加
            String field = "grpId,grpNm,dayweekDiv";
            String[] fieldArray = field.split(",");
            wb = ExcelUtils.createExcelWb(wb, index, fieldArray, mstGrpEntityList);
            wb.write(fileOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                wb.close();
                fileOutputStream.close();
                inputStream.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("fileNm", fileNm);
    }

    /**
     * 生徒・グループ情報のインポート
     *
     * @return
     */
    @RequestMapping(value = "/exportStuGrp", method = RequestMethod.POST)
    public R exportStuGrp() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //出力ファイル名
        String fileNm = orgId + "_F00005_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
        int index = 1;
        File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }

        FileOutputStream fileOutputStream = null;
        //テンプレート取り込む
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        inputStream = this.getClass().getResourceAsStream("/templates/excel/02_生徒グループ情報(エクスポート)_template.xlsx");
        try {
            fileOutputStream = new FileOutputStream(file);
            wb = new XSSFWorkbook(inputStream);
            List<F00005Dto> stuGrpEntityList = f00005Service.getList(orgId);
            String field = "id,afterUsrId,grpId";
            String[] fieldArray = field.split(",");
            wb = ExcelUtils.createExcelWb(wb, index, fieldArray, stuGrpEntityList);
            wb.write(fileOutputStream);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                fileOutputStream.close();
                wb.close();
                inputStream.close();
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("fileNm", fileNm);
    }

    /**
     * DBから⑩で選択した保存先に生徒・保護者の依存関係情報を出力ファイルに出力して保存する。
     *
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void f00005download(HttpServletResponse response, @RequestParam String fileNm, Integer div) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        if (div == 0) {
            String downFileName = orgId + "_グループ情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            //出力ファイル名
            String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileNm);
            ExcelUtils.excelDownload(response, outPath, downFileName);
        }
        if (div == 1) {
            String downFileName = orgId + "_生徒グループ情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            //出力ファイル名
            String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileNm);
            ExcelUtils.excelDownload(response, outPath, downFileName);
        }
    }

    /**
     * テンプレートファイルを出力ファイルに出力して保存する
     *
     * @param div (0-グループ,1-生徒グループ情報)
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
    public void getTemplate(Integer div, HttpServletResponse response) {
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String fileNm = "";
        switch (div) {
            //グループ
            case 0:
                fileNm = "01_グループ情報_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/01_グループ情報(インポート)_template.xlsx");
                break;
            //生徒グループ情報
            case 1:
                fileNm = "02_生徒・グループ情報_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/02_生徒グループ情報(インポート)_template.xlsx");
                break;
            default:
                break;
        }
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

