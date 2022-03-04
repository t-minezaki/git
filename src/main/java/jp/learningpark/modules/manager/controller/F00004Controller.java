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
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F00004GuardDto;
import jp.learningpark.modules.manager.dto.F00004ManagerDto;
import jp.learningpark.modules.manager.dto.F00004MentorDto;
import jp.learningpark.modules.manager.dto.F00004StuDto;
import jp.learningpark.modules.manager.service.F00004Service;
import jp.learningpark.modules.sys.controller.AbstractController;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.List;

/**
 * <p>F00004 利用者基本情報設定・修正 Controller</p>
 *
 * @author NWT : gong <br />
 * 変更履歴 <br />
 * 2018/12/25 : gong: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00004")
@RestController
public class F00004Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F00004 利用者基本情報設定・修正 service
     */
    @Autowired
    private F00004Service f00004Service;

    /**
     * <p>初期表示</p>
     *
     * @return
     */
    @RequestMapping(value = "/init", method = RequestMethod.GET)
    public R f00004Init() {
        String sessionOrgId = (String)ShiroUtils.getSessionAttribute(GakkenConstant.ORG_ID);
        String userOrgId = ShiroUtils.getUserEntity().getOrgId();
        //組織ID
        String orgId = StringUtils.isEmpty(sessionOrgId) ? userOrgId : sessionOrgId;
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w->w.eq("org_id", orgId).eq("del_flg", 0)));
        return R.ok().put("org", org);
    }

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param type 選択したロール
     * @return
     */
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    public R importExcel(MultipartFile file, Integer type, Integer div) {
        return f00004Service.importFile(file, type, div);
    }

    /**
     * <p>インポート</p>
     *
     * @param type 選択したロール
     * @return
     */
    @RequestMapping(value = "/export/{type}", method = RequestMethod.POST)
    public R exportExcel(@PathVariable Integer type) {
        if (StringUtils.equals("0", ShiroUtils.getUserEntity().getOwnerOrgFlg())){
            //画面上部のエラーメッセージ領域でワーニングメッセージ（MSGCOMN0168）を表示する。
            return R.error(MessageUtils.getMessage("MSGCOMN0168", ShiroUtils.getUserEntity().getAfterUsrId()));
        }
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //出力ファイル名
        String fileNm = orgId;
        //テンプレート取り込む
        File file;
        InputStream inputStream = null;
        FileOutputStream fileOutputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String field = "";
        String[] fieldArray;
        int index = 1;
        try {
            fileNm += "_F00004_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            switch (type) {
                //管理者が選択した場合
                case 0:
                    inputStream = this.getClass().getResourceAsStream("/templates/excel/01_管理者基本情報_エクスポート_template.xlsx");
                    wb = new XSSFWorkbook(inputStream);
                    List<F00004ManagerDto> f00004ManagerDtoList = f00004Service.getExcelDataOfManager(orgId);
                    //初期パスワード
                    for (F00004ManagerDto dto : f00004ManagerDtoList) {
                        if (dto.getUsrPassword() != null) {
                            dto.setUsrPassword(ShiroUtils.stringToAscii(dto.getUsrId()));
                        }
                    }
                    field = "usrId,afterUsrId,usrPassword,flnmNm,flnmLnm,flnmKnNm,flnmKnLnm,mailad,telnum,orgId,gidFlg";//0731 wen [gidFlg,gidpk]
                    fieldArray = field.split(",");
                    wb = ExcelUtils.createExcelWb(wb, index, fieldArray, f00004ManagerDtoList);

                    fileOutputStream = new FileOutputStream(file);
                    wb.write(fileOutputStream);
                    break;
                //先生ーが選択した場合
                case 1:
                    inputStream = this.getClass().getResourceAsStream("/templates/excel/02_先生基本情報_エクスポート_template.xlsx");
                    wb = new XSSFWorkbook(inputStream);
                    List<F00004MentorDto> f00004MentorDtoList = f00004Service.getExcelDataOfMentor(orgId);
                    //初期パスワード
                    for (F00004MentorDto dto : f00004MentorDtoList) {
                        if (dto.getUsrPassword() != null) {
                            dto.setUsrPassword(ShiroUtils.stringToAscii(dto.getUsrId()));
                        }
                    }
                    field = "usrId,afterUsrId,usrPassword,flnmNm,flnmLnm,flnmKnNm,flnmKnLnm,mailad,telnum,orgId,gidFlg";//0731 wen [gidFlg,gidpk]
                    fieldArray = field.split(",");
                    wb = ExcelUtils.createExcelWb(wb, index, fieldArray, f00004MentorDtoList);

                    fileOutputStream = new FileOutputStream(file);
                    wb.write(fileOutputStream);
                    break;
                //保護者が選択した場合
                case 2:
                    inputStream = this.getClass().getResourceAsStream("/templates/excel/03_保護者基本情報_エクスポート_template.xlsx");
                    wb = new XSSFWorkbook(inputStream);
                    List<F00004GuardDto> f00004GuardDtoList = f00004Service.getExcelDataOfGuard(orgId);
                    //初期パスワード
                    for (F00004GuardDto dto : f00004GuardDtoList) {
                        if (dto.getUsrPassword() != null) {
                            dto.setUsrPassword(ShiroUtils.stringToAscii(dto.getUsrId()));
                        }
                    }
                    field = "usrId,afterUsrId,usrPassword,flnmNm,flnmLnm,flnmKnNm,flnmKnLnm,reltnspDiv,mailad,telnum,postcd,adr1,adr2,urgTelnum,orgId,gidFlg";//0731 wen [urgTelnum,gidFlg,gidpk]
                    fieldArray = field.split(",");
                    wb = ExcelUtils.createExcelWb(wb, index, fieldArray, f00004GuardDtoList);

                    fileOutputStream = new FileOutputStream(file);
                    wb.write(fileOutputStream);
                    break;
                //生徒が選択した場合
                case 3:
                    inputStream = this.getClass().getResourceAsStream("/templates/excel/04_生徒基本情報_エクスポート_template.xlsx");
                    wb = new XSSFWorkbook(inputStream);
                    List<F00004StuDto> f00004StuDtoList = f00004Service.getExcelDataOfStu(orgId);
                    //初期パスワード
                    for (F00004StuDto dto : f00004StuDtoList) {
                        if (dto.getUsrPassword() != null) {
                            dto.setUsrPassword(ShiroUtils.stringToAscii(dto.getUsrId()));
                        }
                    }
                    field = "usrId,afterUsrId,usrPassword,flnmNm,flnmLnm,flnmKnNm,flnmKnLnm,birthd,gendrDiv,schyDiv,schCd,oriaCd,orgId,gidFlg";//0731 wen [gidFlg,gidpk]
                    fieldArray = field.split(",");
                    wb = ExcelUtils.createExcelWb(wb, index, fieldArray, f00004StuDtoList);

                    fileOutputStream = new FileOutputStream(file);
                    wb.write(fileOutputStream);
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                wb.close();
                inputStream.close();
                fileOutputStream.close();
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
    @RequestMapping(value = "/stuWithGuardCreate", method = RequestMethod.POST)
    public R guardWithStuExprot() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //出力ファイル名
        String fileNm = orgId + "_F00004_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
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
        inputStream = this.getClass().getResourceAsStream("/templates/excel/05_生徒保護者関係_エクスポート_template.xlsx");
        try {
            fileOutputStream = new FileOutputStream(file);
            wb = new XSSFWorkbook(inputStream);
            List<F00004StuDto> f00004ServiceExcelDataOfStuWithGuard = f00004Service.getExcelDataOfStuWithGuard(orgId);
            String field = "afterUsrId,stuNm,gendrDiv,birthd,schyDiv,guardAfterUsrId,guardNm,adr";
            String[] fieldArray = field.split(",");
            int index = 1;
            wb = ExcelUtils.createExcelWb(wb, index, fieldArray, f00004ServiceExcelDataOfStuWithGuard);
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
     * DBから⑩で選択した保存先に生徒・保護者の依存関係情報を出力ファイルに出力して保存する。
     *
     * @param fileNm
     * @param div (0-管理者,1-先生,2-保護者,3-生徒,4-生徒·保護者紐づく関係)
     * @return
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void f00004download1(HttpServletResponse response, @RequestParam String fileNm, Integer div) {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String downFileName = null;
        switch (div) {
            case 0:
                downFileName = orgId + "_管理者基本情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
                break;
            case 1:
                downFileName = orgId + "_先生基本情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
                break;
            case 2:
                downFileName = orgId + "_保護者基本情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
                break;
            case 3:
                downFileName = orgId + "_生徒基本情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
                break;
            case 4:
                downFileName = orgId + "_生徒保護者関係_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
                break;
            default:
                break;
        }
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileNm);
        ExcelUtils.excelDownload(response, outPath, downFileName);

    }

    /**
     * 選択で指定した関係ファイルの内容をDBへ登録処理行う。
     *
     * @param file
     * @return
     */
    @RequestMapping(value = "/stuWithGuardImport", method = RequestMethod.POST)
    public R guardWithStuImprot(MultipartFile file) {
        return f00004Service.guardWithStuImprot(file);
    }

    /**
     * テンプレートファイルを出力ファイルに出力して保存する
     *
     * @param div (0-管理者,1-先生,2-保護者,3-生徒,4-生徒·保護者紐づく関係)
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
    public void getTemplate(Integer div, HttpServletResponse response) {
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String fileNm = "";
        switch (div) {
            case 0:
                fileNm = "01_管理者基本情報_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/01_管理者基本情報_インポート_template.xlsx");
                break;
            case 1:
                fileNm = "02_先生基本情報_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/02_先生基本情報_インポート_template.xlsx");
                break;
            case 2:
                fileNm = "03_保護者基本情報_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/03_保護者基本情報_インポート_template.xlsx");
                break;
            case 3:
                fileNm = "04_生徒基本情報_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/04_生徒基本情報_インポート_template.xlsx");
                break;
            case 4:
                fileNm = "05_生徒保護者関係_インポート_template.xlsx";
                inputStream = this.getClass().getResourceAsStream("/templates/excel/05_生徒保護者関係_インポート_template.xlsx");
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

