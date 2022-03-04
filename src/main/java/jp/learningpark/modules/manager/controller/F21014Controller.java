package jp.learningpark.modules.manager.controller;

import com.alibaba.fastjson.JSON;
import jp.learningpark.framework.utils.*;
import jp.learningpark.modules.common.GakkenConstant;
import jp.learningpark.modules.manager.dto.F21014LineDto;
import jp.learningpark.modules.manager.service.F21014Service;
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
import java.util.List;

/**
 * <p>
 * 個人別月状況確認画面
 * </p>
 *
 * @author NWT)hxl
 * @version 1.0
 * <p>
 * 変更履歴:
 * 2019/12/06 ： NWT)hxl ： 新規作成
 * @date 2019/12/06 14:36
 */
@RestController
@RequestMapping("/manager/F21014")
public class F21014Controller {
    /**
     * f21014Service
     */
    @Autowired
    F21014Service f21014Service;

    private Logger logger = LoggerFactory.getLogger(getClass());

    /**
     * 該当生徒の全部指導報告書及出席簿明細内容を取得する
     *
     * @param month 年月
     * @param stuId 生徒ID
     * @return
     */
    @RequestMapping(value = "/getResult", method = RequestMethod.GET)
    public R getResult(String month, String stuId) {
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        return f21014Service.getAttendBookByMonth(month, orgId, stuId);
    }

    /**
     * Excelを生成
     *
     * @param days  最大日数
     * @param data  データ
     * @param month 月
     * @return
     */
    @RequestMapping(value = "/export", method = RequestMethod.POST)
    public R f09001Export(Integer days, String data, String month) {
        //string -> object
        List<F21014LineDto> dataList = JSON.parseArray(data, F21014LineDto.class);
        String orgId = ShiroUtils.getUserEntity().getOrgId();
        //出力ファイル名
        StringBuilder fileNm = new StringBuilder(orgId);
        FileOutputStream fileOutputStream = null;
        //テンプレートのexcel
        XSSFWorkbook wb = null;
        try {
            fileNm.append("_21014_").append(DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDDHHMMSS)).append(".xlsx");
            File file = FileUtils.getStorageFile(MessageUtils.getMessage("path.excel"), fileNm.toString());
            if (!file.exists()) {
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            fileOutputStream = new FileOutputStream(file);
            StringBuilder field = new StringBuilder("項目,教科");
            for (int i = 1; i <= days; i++) {
                field.append(",").append(month).append("月").append(i).append("日");
            }
            String[] fieldArray = field.toString().split(",");
            //Excelを生成
            wb = ExcelUtils.createExcelWbF21014(fieldArray, dataList);
            wb.write(fileOutputStream);

        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            try {
                if (wb != null) {
                    wb.close();
                    fileOutputStream.close();
                }
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        return R.ok().put("fileName", fileNm.toString());
    }

    /**
     * Excelダウンロード
     *
     * @param response
     * @param fileName
     * @param month
     * @param stuId
     */
    @RequestMapping(value = "/download", method = RequestMethod.POST)
    public void download(HttpServletResponse response, @RequestParam String fileName, @RequestParam String month, @RequestParam String stuId) {
        String downFileName = month + "月_" + stuId + "_指導報告書_" + DateUtils.format(DateUtils.getSysDate(), GakkenConstant.DATE_FORMAT_YYYYMMDD) + ".xlsx";
        //出力ファイル名
        String outPath = FileUtils.getStoragePath(MessageUtils.getMessage("path.excel"), fileName);
        ExcelUtils.excelDownload(response, outPath, downFileName);
    }
}
