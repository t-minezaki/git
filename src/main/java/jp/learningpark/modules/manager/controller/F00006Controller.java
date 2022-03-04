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
import jp.learningpark.modules.common.entity.MstOrgEntity;
import jp.learningpark.modules.common.service.MstOrgService;
import jp.learningpark.modules.manager.dto.F00006Dto;
import jp.learningpark.modules.manager.service.F00006Service;
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
 * <p>F00006 メンター生徒管理画面 Controller</p >
 *
 * @author NWT : wen <br />
 * 変更履歴 <br />
 * 2018/12/26 : wen: 新規<br />
 * @version 1.0
 */
@RequestMapping("/manager/F00006")
@RestController
public class F00006Controller extends AbstractController {

    /**
     * 組織マスター　Service
     */
    @Autowired
    private MstOrgService mstOrgService;

    /**
     * F00006 メンター生徒管理画面 service
     */
    @Autowired
    private F00006Service f00006Service;

    /**
     * <p>初期表示</p>
     *
     * @return
     */
    @RequestMapping(value = "/Init", method = RequestMethod.GET)
    public R f00006Init() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        MstOrgEntity org = mstOrgService.getOne(new QueryWrapper<MstOrgEntity>().and(w -> w.eq("org_id", orgId).eq("del_flg", 0)));
        return R.ok().put("org", org);
    }

    /**
     * <p>インポート</p>
     *
     * @param file ファイル
     * @param type 新規・修正（エラーとする）、新規・修正（上書き）、削除選択フラッグ
     * @return
     */
    @RequestMapping(value = "/Import", method = RequestMethod.POST)
    public R importExcel(MultipartFile file, Integer type) {
        return f00006Service.importFile(file, type);
    }

    /**
     * DBから⑩で選択した保存先に生徒・保護者の依存関係情報を出力ファイルに出力して保存する。
     *
     * @return
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public R exportExcel() {
        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //出力ファイル名
        String fileNm = orgId;
        FileOutputStream fileOutputStream = null;
        //テンプレート取り込む
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        try {
            inputStream = this.getClass().getResourceAsStream("/templates/excel/01_先生生徒関連情報(エクスポート)_template.xlsx");
            fileNm += "_00006_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            wb = new XSSFWorkbook(inputStream);
            List<F00006Dto> f00006DtoList = f00006Service.getAfterUsrIdAndStuId(orgId);
            String field = "id,mentorId,stuId";
            String[] fieldArray = field.split(",");
            wb = ExcelUtils.createExcelWb(wb, 1, fieldArray, f00006DtoList);
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
     * DBから⑩で選択した保存先にメンター生徒情報を出力ファイルに出力して保存する。
     *
     * @return
     */
    @RequestMapping(value = "/Download", method = RequestMethod.POST)
    public void f00006download(HttpServletResponse response, @RequestParam String fileNm) {

        //組織ID
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        String downFileName = orgId + "_先生生徒関連情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileNm);
        ExcelUtils.excelDownload(response, outPath, downFileName);
    }

    /**
     * テンプレートファイルを出力ファイルに出力して保存する
     *
     */
    @RequestMapping(value = "/getTemplate", method = RequestMethod.POST)
    public void getTemplate(HttpServletResponse response) {
        InputStream inputStream=null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        String fileNm="01_先生生徒関連情報_インポート_template.xlsx";
        inputStream = this.getClass().getResourceAsStream("/templates/excel/01_先生生徒関連情報(インポート)_template.xlsx");
        try{
            wb = new XSSFWorkbook(inputStream);
            ExcelUtils.getTemplate(response,wb,fileNm);
        }catch (Exception e){
            logger.error(e.getMessage());
        }finally {
            try{
                if(wb!=null){
                    wb.close();
                }
                if(inputStream!=null){
                    inputStream.close();
                }
            }catch (Exception e){
                logger.error(e.getMessage());
            }
        }
    }
}
