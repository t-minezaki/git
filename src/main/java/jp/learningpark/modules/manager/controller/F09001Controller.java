package jp.learningpark.modules.manager.controller;

import jp.learningpark.framework.utils.Constant;
import jp.learningpark.framework.utils.DateUtils;
import jp.learningpark.framework.utils.ExcelUtils;
import jp.learningpark.framework.utils.FileUtils;
import jp.learningpark.framework.utils.MessageUtils;
import jp.learningpark.framework.utils.R;
import jp.learningpark.framework.utils.ShiroUtils;
import jp.learningpark.framework.utils.StringUtils;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dto.F09001HistoryDto;
import jp.learningpark.modules.manager.service.F09001Service;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

/**
 * <p>
 * F09001コントローラー
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/11/12 ： NWT)hxl ： 新規作成
 */
@RestController
@RequestMapping("/manager/F09001")
public class F09001Controller {
    /**
     * f09001Service
     */
    @Autowired
    F09001Service f09001Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 生徒の入退室履歴を取得する
     *
     * @param day 検索日
     * @param limit 数量
     * @param page ページ数
     * @return
     */
    @RequestMapping(value = "/select", method = RequestMethod.GET)
    public R select(String day, String orgId, Integer limit, Integer page) {
        String roleDiv = StringUtils.trim(ShiroUtils.getUserEntity().getRoleDiv());
        String userId = ShiroUtils.getUserId();
        Date startDay = DateUtils.parse(day, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH);
        if (StringUtils.isEmpty(orgId) || StringUtils.equals("null", orgId)) {
            orgId = ShiroUtils.getUserEntity().getOrgId();
        }
        Timestamp start = DateUtils.toTimestamp(startDay);
        Timestamp end = DateUtils.toTimestamp(DateUtils.addDays(startDay, 1));
        return f09001Service.getEntryHistory(orgId, roleDiv, userId, start, end, limit, page);
    }

    /**
     * Excelを生成
     *
     * @param start 開始日
     * @param end 終了日
     * @return
     */
    @RequestMapping(value = "/Export", method = RequestMethod.GET)
    public R f09001Export(String start, String end, String orgId) {
        String roleDiv = ShiroUtils.getUserEntity().getRoleDiv().trim();
        String userId = ShiroUtils.getUserId();
        Timestamp startDay = DateUtils.toTimestamp(DateUtils.parse(start, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH));
        Date endDate = DateUtils.addDays(DateUtils.parse(end, Constant.DATE_FORMAT_YYYY_MM_DD_SLASH), 1);
        Timestamp endDay = DateUtils.toTimestamp(endDate);
        if (orgId == null || orgId.equals("null")) {
            orgId = ShiroUtils.getUserEntity().getOrgId();
        }
        //出力ファイル名
        String fileNm = orgId;
        FileOutputStream fileOutputStream = null;
        //テンプレート取り込む
        InputStream inputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        try {
            inputStream = this.getClass().getResourceAsStream("/templates/excel/09_生徒入退室情報ファイル(エクスポート)_template.xlsx");
            fileNm += "_09001_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS) + ".xlsx";
            File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm);
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            wb = new XSSFWorkbook(inputStream);
            List<F09001HistoryDto> f09001HistoryDtos = f09001Service.download(orgId, roleDiv, userId, startDay, endDay);
            if (f09001HistoryDtos.size() == 0) {
                return R.error(MessageUtils.getMessage("MSGCOMN0017", "入退室履歴"));
            }
            String field = "orgName,afterUsrId,stuName,schyDiv,entryTime,exitTime";
            String[] fieldArray = field.split(",");

            wb = ExcelUtils.createExcelWbF09001(wb, 0, fieldArray, f09001HistoryDtos);
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
     * Excelダウンロード
     *
     * @param response
     * @param fileName
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(HttpServletResponse response, @RequestParam String fileName) {
        String downFileName = "入退室記録情報_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD) + ".xlsx";
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileName);
        ExcelUtils.excelDownload(response, outPath, downFileName);
    }
}
